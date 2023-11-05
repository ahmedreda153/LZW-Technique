import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LZWGUI {
    private JFrame frame;
    private JButton browseButton;
    private JButton compressButton;
    private JButton decompressButton;
    private JLabel outputLabel;
    private File selectedFile;

    public LZWGUI() {
        frame = new JFrame("LZW Compression/Decompression");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200); // Set a larger window size
        frame.setLayout(new FlowLayout());
        
        // Center the window on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) (screenSize.getWidth() - frame.getWidth()) / 2;
        int centerY = (int) (screenSize.getHeight() - frame.getHeight()) / 2;
        frame.setLocation(centerX, centerY);

        browseButton = new JButton("Browse");
        compressButton = new JButton("Compress");
        decompressButton = new JButton("Decompress");
        outputLabel = new JLabel();

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    outputLabel.setText("Selected file: " + selectedFile.getName());
                }
            }
        });

        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    LZW lzw = new LZW();
                    lzw.Compression(selectedFile.getAbsolutePath());
                    outputLabel.setText("Compression completed. Check 'compressed.txt'.");
                } else {
                    outputLabel.setText("Please select a file to compress.");
                }
            }
        });

        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    LZW lzw = new LZW();
                    lzw.Decompression(selectedFile.getAbsolutePath());
                    outputLabel.setText("Decompression completed. Check 'decompressed.txt'.");
                } else {
                    outputLabel.setText("Please select a file to decompress.");
                }
            }
        });

        frame.add(browseButton);
        frame.add(compressButton);
        frame.add(decompressButton);
        frame.add(outputLabel);

        frame.setVisible(true);
    }
}
