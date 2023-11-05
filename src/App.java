import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // Scanner sc=new Scanner(System.in);
        // while(true){
        //     System.out.println("1- Enter 1 to compress");
        //     System.out.println("2- Enter 2 to decompress");
        //     System.out.println("3- Enter 0 to exit");
        //     int choice = sc.nextInt();
        //     LZW lzw = new LZW();
        //     if(choice==1){
        //         System.out.println("Enter the file name");
        //         String filename=sc.next();
        //         filename = filename + ".txt";
        //         // String filename = "original.txt";
        //         lzw.Compression(filename);
        //     }
        //     else if(choice==2){
        //         System.out.println("Enter the file name");
        //         String filename=sc.next();
        //         filename=filename+".txt";
        //         // String filename = "compressed.txt";
        //         lzw.Decompression(filename);
        //     }
        //     else if(choice==0){
        //         break;
        //     }
        //     else{
        //         System.out.println("Enter a valid choice");
        //     }
        // }
        // sc.close();
        // new LZWGUI();
        new LZWApp();
    }
}
