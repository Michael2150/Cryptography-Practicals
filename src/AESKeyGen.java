import java.io.*;
import javax.crypto.*;

public class AESKeyGen
{
    /**
     * Generates AES key and writes to file
     * N.B. This is just for illustration.
     * Private keys should not be stored in an unprotected form like this.
     * Better to use Keystore
     * @param args
     */
    public static void main(String args[])
    {
        try
        {
            // File for writing output
            FileOutputStream keyFOS = new FileOutputStream("AESKeyFile");
            ObjectOutputStream keyOOS = new ObjectOutputStream(keyFOS);
            
            // Generate random AES key
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecretKey aesKey = keygen.generateKey();
            keyOOS.writeObject(aesKey);
            
            System.out.println("AES key generated and written to file: AESKeyFile");
             
            keyOOS.close();
            keyFOS.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
