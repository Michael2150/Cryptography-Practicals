package DES.CBC;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class DESCBCDecrypt
{
    /**
     * Decrypts message provided in file and writes to standard output
     * @param args
     */
    public static void main(String args[])
    {
        try
        {
            // File containing secret AES key
            FileInputStream keyFIS = new FileInputStream("DESKeyFile");
            ObjectInputStream keyOIS = new ObjectInputStream(keyFIS);

            // Read in the AES key
            SecretKey aesKey = (SecretKey) keyOIS.readObject();
            keyOIS.close();
            keyFIS.close();

            // set IV (required for AES.CBC)
            byte[] iv = new byte[] {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
            IvParameterSpec ips = new IvParameterSpec(iv);

            // Create AES cipher instance
            Cipher aesCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

            // Initialize the cipher for decryption
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ips);

            // Read ciphertext from file and decrypt it
            FileInputStream fis = new FileInputStream("des_cbc_scrambled");
            BufferedInputStream bis = new BufferedInputStream(fis);
            CipherInputStream cis = new CipherInputStream(bis, aesCipher);

            StringBuffer plaintext = new StringBuffer();
            int c;
            while ((c = cis.read()) != -1)
                plaintext.append((char) c);
            cis.close();
            bis.close();
            fis.close();

            System.out.println("Plaintext: " + plaintext.toString());

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}