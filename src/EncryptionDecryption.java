import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Scanner;


public class EncryptionDecryption {

    public static final String ALGORITHM = "AES";Object scanner;

    public static void main(String[] args) throws Exception {

        //add scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        //Generate a Random Secret Key using chosen algorithm (AES)
        //The key will be used for both Encryption and decryption
        SecretKey secretKey = KeyGenerator.getInstance(ALGORITHM).generateKey();

        //This will start with an infinite loop until the user selects a choice.
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Encrypt a string");
            System.out.println("2. Decrypt a string");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); //consume new line

            switch (choice) {
                case 1:
                    encryptString(scanner, secretKey);
                    break;
                case 2:
                    decryptString(scanner, secretKey);
                    break;
                case 3:
                    System.out.println("Exiting Program. GOODBYE!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid response. TRY AGAIN!");


            }
        }
    }
    //encryption method
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    //decryption method
    //declare public static method named 'decrypt' which takes 2 parameters
    //'encryptedData', and 'key'
    //add throw exception for error handling.
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {

        //creates cypher object for specified algorithm. (AES)
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        //this initializes the Cipher object for decryption mode.
        //it uses the key provided for the decrypt process.
        cipher.init(Cipher.DECRYPT_MODE, key);

        //this performs the actual decryption in two phases.
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }




    private static void decryptString(Scanner scanner, SecretKey key) throws Exception {
        System.out.print("Enter the string to decrypt: ");
        String encryptedString = scanner.nextLine();
        try {
            String decryptedString = decrypt(encryptedString, key);
            System.out.println("Decrypted string: " + decryptedString);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid input for decryption. Make sure you enter a valid encrypted string.");
        }
    }

    /*declares a private static method named 'encryptString' which takes 2 parameters
    a 'Scanner' object for input, and a 'SecretKey' for encryption.
    added error handling exception for crypto issues.*/
    private static void encryptString(Scanner scanner, SecretKey secretKey) throws Exception {
        System.out.println("Enter the String to encrypt");

        //this line reads the user input using the 'scanner' object.
        //the input is stored in the originalString variable.
        String originalString = scanner.nextLine();

        //calls the encrypt method (which was defined above)
        String encryptedString = encrypt(originalString, secretKey);

        //prints strings to the console.
        System.out.println("Encrypted string: " + encryptedString);
    }
}



