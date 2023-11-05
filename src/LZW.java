import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class LZW {
    // Function to read the uncompressed file and return its contents as a string
    public String readUncompressedFile(String fileName) {
        String txt = "";
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                txt += sc.nextLine();
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return txt;
    }

    // Function to compress the input file using the LZW algorithm
    public void Compression(String fileName) {
        try (FileOutputStream out = new FileOutputStream("compressed.txt")) {
            String text = readUncompressedFile(fileName);
            Dictionary<String, Integer> dictionary = new Hashtable<>();
            String current = "";
            int nextCode = 128;
            int cursor = 0;
            String writtenText = "";
            Boolean isFound = false;

            outerLoop: for (int i = 0; i < text.length(); i++) {
                cursor = i;
                current = text.charAt(i) + "";

                if (i < text.length() - 1) {
                    cursor++;
                    current += text.charAt(cursor);

                    // Check if the current substring is in the dictionary
                    while (dictionary.get(current) != null) {
                        if (cursor == text.length() - 1) {
                            // Write the code for the current substring to the output file
                            out.write(dictionary.get(current));
                            break outerLoop;
                        }
                        cursor++;
                        current += text.charAt(cursor);
                        isFound = true;
                    }

                    if (isFound) {
                        // The longest matching substring found in the dictionary
                        writtenText = current.substring(0, current.length() - 1);
                        out.write(dictionary.get(writtenText));
                        i = cursor - 1;
                    } else {
                        // Write the character as its ASCII code
                        out.write((int) text.charAt(i));
                    }

                    // Add the current substring to the dictionary with a new code
                    dictionary.put(current, nextCode);
                    nextCode++;
                    isFound = false;
                } else {
                    // Write the character as its ASCII code
                    out.write((int) text.charAt(i));
                }
            }
            System.out.println(dictionary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to read the compressed data from a file
    public byte[] readCompressedFile(String fileName) {
        try {
            File file = new File(fileName);
            FileInputStream in = new FileInputStream(file);
            byte[] compressedBytes = new byte[in.available()];
            in.read(compressedBytes);
            in.close();
            return compressedBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Function to decompress the compressed data and write the result to a file
    public void Decompression(String fileName) {
        try (FileOutputStream out = new FileOutputStream("decompressed.txt")) {
            byte[] compressedBytes = readCompressedFile(fileName);
            Dictionary<Integer, String> dictionary = new Hashtable<>();
            int nextCode = 128;
            int currentCode = 0;
            String result = "";
            String beforeCurrent = "";
            String current = "";

            // Initialize with the first character from the compressed data
            beforeCurrent += (char) compressedBytes[0];
            result += beforeCurrent;

            for (int i = 1; i < compressedBytes.length; i++) {
                currentCode = compressedBytes[i] & 0xff;

                // If the code is less than 128, it represents a single character
                if (currentCode < 128) {
                    current = (char) compressedBytes[i] + "";
                } else {
                    // Code represents a sequence in the dictionary
                    if (dictionary.get(currentCode) != null) {
                        current = dictionary.get(currentCode);
                    } else {
                        // Special case where the code is not found in the dictionary
                        current = beforeCurrent + beforeCurrent.charAt(0);
                    }
                }

                // Append the current sequence to the result
                result += current;

                // Add the new sequence to the dictionary
                if (beforeCurrent != "") {
                    dictionary.put(nextCode, beforeCurrent + current.charAt(0));
                    nextCode++;
                }
                beforeCurrent = current;
            }

            // Write the decompressed result to the output file
            out.write(result.getBytes());

            System.out.println(dictionary);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
