package AES;

import AES.CBC.AESCBCEncrypt;
import AES.ECB.AESECBEncrypt;
import java.io.*;
import java.util.HashMap;

public class CBCvECB {
    //a method to encrypt a string using AESECBEncrypt and AESCBCEncrypt classes I have already created
    public static void main(String[] args) {
        boolean single = false;

        if (single)
            singleText();
        else
            multipleTexts();
    }

    public static void singleText(){
        //read and compare ciphertexts
        //A 16 character long string to encrypt
        String plaintext = "qwertyuiopasdfghijklmnop";
        //Print string length
        System.out.println("Plaintext: " + plaintext.length() + " characters");

        System.out.println("CBC");
        AESCBCEncrypt.main(new String[]{plaintext});
        System.out.println("ECB");
        AESECBEncrypt.main(new String[]{plaintext});

        readAndCompareCiphertexts();
    }

    public static void multipleTexts() {
        //ArrayList of plaintexts
        String[] plaintexts = new String[64];
        //Loop from 1 to 65
        for (int i = 1; i < 65; i++) {
            //Create a string the length of i
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                //Append a random character to the string
                sb.append((char) (Math.random() * 26 + 97));
            }
            //Add the string to the array
            plaintexts[i-1] = sb.toString();
        }

        //Hashmap to store the similarities per plaintext
        //Key: plaintext
        //Value: similarity
        HashMap<String, Integer> similarities = new HashMap<>();

        //For each plaintext in plaintexts
        for (String plaintext : plaintexts) {
            //Encrypt the plaintext using CBC
            AESCBCEncrypt.main(new String[]{plaintext});
            //Encrypt the plaintext using ECB
            AESECBEncrypt.main(new String[]{plaintext});

            //Read the ciphertexts
            String cbc_hex = readFile("cbc_scrambled");
            String ecb_hex = readFile("ecb_scrambled");

            //Get the bytes similar
            int similarity = getSimilarity(cbc_hex, ecb_hex);

            //Add the plaintext to the hashmap
            similarities.put(plaintext, similarity);
        }

        //Print the plaintext's length and their similarities from the hashmap in descending order
        //Get the keys from the hashmap
        String[] keys = similarities.keySet().toArray(new String[0]);
        //Sort the keys based on their size in descending order
        java.util.Arrays.sort(keys, (o1, o2) -> o2.length() - o1.length());
        for (String key : keys) {
            System.out.println("[" + key.length() + "]: " + similarities.get(key) + " bytes similar");
        }
    }

    private static void readAndCompareCiphertexts(){
        String filename1 = "cbc_scrambled";
        String filename2 = "ecb_scrambled";

        //Read files as hex strings
        String cbc_hex = readFile(filename1);
        String ecb_hex = readFile(filename2);

        //Get similarities between the two ciphertexts
        int cbc_similarity = getSimilarity(cbc_hex, ecb_hex);

        //Print out the similarity
        System.out.println("Similarity: " + cbc_similarity/2 + " bytes");

        //get the similar parts of the two ciphertexts
        String cbc_similar = cbc_hex.substring(0, cbc_similarity);
        String ecb_similar = ecb_hex.substring(0, cbc_similarity);

        //Make a list of numbers from 01 to the similarity/2 seperated by spaces
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cbc_similarity/2; i++) {
            //Make sure the number is 2 digits long
            if (i < 10) {   sb.append("0");   }
            sb.append(i);
            sb.append(" ");
        }
        String numbers = sb.toString();

        //Print the number for the bytes that are similar
        System.out.println("Bytes: " + numbers);

        //Add a space after every 2 characters in the similar strings
        cbc_similar = cbc_similar.replaceAll("(.{2})", "$1 ");
        ecb_similar = ecb_similar.replaceAll("(.{2})", "$1 ");

        //Print out the similar parts
        System.out.println("CBC:   " + cbc_similar);
        System.out.println("ECB:   " + ecb_similar);
    }

    private static int getSimilarity(String cbc_hex, String ecb_hex) {
        //Get the length of the shorter string
        int length = cbc_hex.length();
        if (ecb_hex.length() < length) {
            length = ecb_hex.length();
        }

        //Compare the two strings
        int similarity = 0;
        for (int i = 0; i < length; i++) {
            if (cbc_hex.charAt(i) == ecb_hex.charAt(i)) {
                similarity++;
            } else {
                break;
            }
        }
        return similarity;
    }


    private static String readFile(String filename1) {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(filename1);
            int i;
            while ((i = fis.read()) != -1) {
                sb.append(Integer.toHexString(i));
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
