package AES;

import AES.CBC.AESCBCEncrypt;
import AES.ECB.AESECBEncrypt;
import java.io.*;

public class CBCvECB {
    //a method to encrypt a string using AESECBEncrypt and AESCBCEncrypt classes I have already created
    public static void main(String[] args) {
        //A 16 character long string to encrypt
        String plaintext = "qwertyuiopasdfgh";
        //Print string length
        System.out.println("Plaintext: " + plaintext.length() + " characters");

        System.out.println("CBC");
        AESCBCEncrypt.main(new String[]{plaintext});
        System.out.println("ECB");
        AESECBEncrypt.main(new String[]{plaintext});

        readAndCompareCiphertexts();
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
        String cbc_similar = cbc_hex.substring(0, cbc_similarity-1);
        String ecb_similar = ecb_hex.substring(0, cbc_similarity-1);

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
