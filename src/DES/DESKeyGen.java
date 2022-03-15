package DES;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class DESKeyGen
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
            FileOutputStream keyFOS = new FileOutputStream("DESKeyFile");
            ObjectOutputStream keyOOS = new ObjectOutputStream(keyFOS);
            
            // Generate random DES key
            KeyGenerator keygen = KeyGenerator.getInstance("DES");
            SecretKey aesKey = keygen.generateKey();
            keyOOS.writeObject(aesKey);
            
            System.out.println("DES key generated and written to file: DESKeyFile");
             
            keyOOS.close();
            keyFOS.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
