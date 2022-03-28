package AES.CBC;

import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;


public class AESCBCEncrypt
{
    public static String lastCipherText = "";

    /**
     * Encrypts message provided on command line and writes to file
     * @param args
     */
    public static void main(String args[])
    {
        try
        {
            if (args.length != 1)
                args = new String[]{"12345678901234567890123456"};

            // File containing secret AES key
            FileInputStream keyFIS = new FileInputStream("AESKeyFile");
            ObjectInputStream keyOIS = new ObjectInputStream(keyFIS);

            // Read in the AES key
            SecretKey aesKey = (SecretKey) keyOIS.readObject();
            keyOIS.close();
            keyFIS.close();

            // set IV (required for AES.CBC)
            byte[] iv ={0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
            IvParameterSpec ips = new IvParameterSpec(iv);

            // Create AES cipher instance
            Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Initialize the cipher for encryption
            aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, ips);

            // File for writing output
            FileOutputStream fos = new FileOutputStream("cbc_scrambled");

            // Read first command-line arg into a buffer.
            // This is the message to be encrypted
            byte plaintext[] = args[0].getBytes();

            // Encrypt the plaintext
            byte[] ciphertext = aesCipher.doFinal(plaintext);

            // Write ciphertext to file
            fos.write(ciphertext);
            fos.close();

            // Also display it in Hex on stdout
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < ciphertext.length; i++)
            {
                hexString.append(Integer.toHexString(0xF & ciphertext[i]>>4));
                hexString.append(Integer.toHexString(0xF & ciphertext[i]));
                hexString.append(" ");
            }
            System.out.println("Plaintext: " + args[0]);
            System.out.println("Ciphertext: " + hexString.toString());
            lastCipherText = hexString.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}