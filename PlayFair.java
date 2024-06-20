import java.util.Scanner;

class PlayFair {
    private char[][] matrix;

    public PlayFair(String key) {
        matrix = new char[5][5];
        generateKeyMatrix(key);
    }

    private void generateKeyMatrix(String key) {
        key = key.toUpperCase();
        String keyWithoutDuplicates = removeDuplicateChars(key + "ABCDEFGHIKLMNOPQRSTUVWXYZ"); // 'J' is excluded

        int row = 0, col = 0;

        for (char ch : keyWithoutDuplicates.toCharArray()) {
            if (findPosition(ch) == null) {
                matrix[row][col] = ch;
                col++;

                if (col == 5) {
                    col = 0;
                    row++;
                }
            }
        }
    }

    private String removeDuplicateChars(String str) {
        StringBuilder result = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (result.indexOf(String.valueOf(ch)) == -1) {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private int[] findPosition(char ch) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == ch) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }

    public String encrypt(String plaintext) {
        StringBuilder encryptedText = new StringBuilder();
        plaintext = plaintext.toUpperCase().replaceAll("J", "I").replaceAll("[^A-Z]", "");

        for (int i = 0; i < plaintext.length(); i += 2) {
            if (i + 1 == plaintext.length() || plaintext.charAt(i) == plaintext.charAt(i + 1)) {
                plaintext = new StringBuilder(plaintext).insert(i + 1, 'X').toString();
            }
        }

        for (int i = 0; i < plaintext.length(); i += 2) {
            char ch1 = plaintext.charAt(i);
            char ch2 = plaintext.charAt(i + 1);
            encryptedText.append(encryptPair(ch1, ch2));
        }

        return encryptedText.toString();
    }

    public String decrypt(String ciphertext) {
        StringBuilder decryptedText = new StringBuilder();

        
        for (int i = 0; i < ciphertext.length(); i += 2) {
            char ch1 = ciphertext.charAt(i);
            char ch2 = ciphertext.charAt(i + 1);
            decryptedText.append(decryptPair(ch1, ch2));
        }

        return decryptedText.toString();
    }

    private String encryptPair(char ch1, char ch2) {
        int[] pos1 = findPosition(ch1);
        int[] pos2 = findPosition(ch2);

        if (pos1[0] == pos2[0]) {
            return "" + matrix[pos1[0]][(pos1[1] + 1) % 5] + matrix[pos2[0]][(pos2[1] + 1) % 5];
        } 
        else if (pos1[1] == pos2[1]) {
            return "" + matrix[(pos1[0] + 1) % 5][pos1[1]] + matrix[(pos2[0] + 1) % 5][pos2[1]];
        } 
        else {
            return "" + matrix[pos1[0]][pos2[1]] + matrix[pos2[0]][pos1[1]];
        }
    }

    private String decryptPair(char ch1, char ch2) {
        int[] pos1 = findPosition(ch1);
        int[] pos2 = findPosition(ch2);

        if (pos1[0] == pos2[0]) {
            return "" + matrix[pos1[0]][(pos1[1] + 4) % 5] + matrix[pos2[0]][(pos2[1] + 4) % 5];
        } 
        else if (pos1[1] == pos2[1]) {
            return "" + matrix[(pos1[0] + 4) % 5][pos1[1]] + matrix[(pos2[0] + 4) % 5][pos2[1]];
        } 
        else {
            return "" + matrix[pos1[0]][pos2[1]] + matrix[pos2[0]][pos1[1]];
        }
    }

    public void printMatrix() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}

public class PlayFairCipher {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the plain text: ");
        String plaintext = sc.nextLine();
        System.out.print("Enter the key: ");
        String key = sc.nextLine();

        PlayFair pf = new PlayFair(key);

        System.out.println("Key Matrix:");
        pf.printMatrix();

        String encryptedString = pf.encrypt(plaintext);
        System.out.println("Encrypted Text: " + encryptedString);

        String decryptedString = pf.decrypt(encryptedString);
        System.out.println("Decrypted Text: " + decryptedString);
        sc.close();
    }
  }
