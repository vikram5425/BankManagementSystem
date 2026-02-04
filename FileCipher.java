import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileCipher {

    private static final int SHIFT = 3; 
    
    public static String caesarCipher(String text, int shift, boolean decrypt) {
        StringBuilder result = new StringBuilder();

        if (decrypt) {
            shift = -shift;
        }

        for (char ch : text.toCharArray()) {
            if (ch >= 'a' && ch <= 'z') {
                ch = (char) ('a' + (ch - 'a' + shift + 26) % 26);
            } else if (ch >= 'A' && ch <= 'Z') {
                ch = (char) ('A' + (ch - 'A' + shift + 26) % 26);
            }
            result.append(ch);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("File Encryption / Decryption Tool  Untitled1:33 - encrypted.txt:33");
        System.out.print("Choose (e)ncrypt or (d)ecrypt:  Untitled1:34 - encrypted.txt:34");
        char choice = scanner.next().toLowerCase().charAt(0);
        scanner.nextLine(); 

        System.out.print("Enter file path:  Untitled1:38 - encrypted.txt:38");
        String filePath = scanner.nextLine();

        try {
            Path inputPath = Paths.get(filePath);
            String content = Files.readString(inputPath, StandardCharsets.UTF_8);

            boolean decrypt = (choice == 'd');
            String result = caesarCipher(content, SHIFT, decrypt);

            String outputFileName = decrypt ? "decrypted.txt" : "encrypted.txt";
            Path outputPath = inputPath.getParent().resolve(outputFileName);

            Files.writeString(outputPath, result, StandardCharsets.UTF_8);

            System.out.println("Operation successful!  Untitled1:53 - encrypted.txt:53");
            System.out.println("Output file saved as:  Untitled1:54 - encrypted.txt:54" + outputPath);

        } catch (IOException e) {
            System.out.println("Error reading or writing file:  Untitled1:57 - encrypted.txt:57" + e.getMessage());
        }

        scanner.close();
    }
}

