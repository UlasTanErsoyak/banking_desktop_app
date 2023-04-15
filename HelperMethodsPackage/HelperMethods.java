package HelperMethodsPackage;

import DatabasePackage.DatabaseOperationsClass;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;



public class HelperMethods {


    /***
     * takes user's passwordand encrypts the final password with SHA256 resulting
     * with a byte[] type variable,converts byte[] type variable to an usable String type
     * variable and returns it
     * @param rawPassword string type password variable
     * @param Salt random int type variable
     * @return String type hashed and salted password
     */
    //https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha-256-in-java
    public static String hashPassword (String rawPassword,int Salt) {
        String salt=String.valueOf(Salt);
        String saltedPassword = rawPassword+salt;
        try{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] hash = digest.digest(saltedPassword.getBytes("UTF-8"));
        final StringBuilder hashedPassword = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            final String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1)
                hashedPassword.append('0');
            hashedPassword.append(hex);
        }
        return hashedPassword.toString();
    } catch(Exception ex){
        throw new RuntimeException(ex);
    }
    }

    /**
     * overloaded version of the hashPassword method
     * @param saltedPassword salted version of password as string type variable input
     * @return String type hashed password
     */
    public static String hashPassword (String saltedPassword) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(saltedPassword.getBytes("UTF-8"));
            final StringBuilder hashedPassword = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hashedPassword.append('0');
                hashedPassword.append(hex);
            }
            return hashedPassword.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }


    //https://stackoverflow.com/questions/50457159/generate-bank-account-number-with-random

    /**
     * recursive method that creates a random IBAN number that starts with TR
     * if that random IBAN already exists in database calls itself again until
     * it creates a unique one
     * @return string type IBAN
     */
    public static String generateRandomIBAN(){
        String IBAN="TR";
        Random value = new Random();

        //Generate two values to append to 'BE'
        int r1 = value.nextInt(10);
        int r2 = value.nextInt(10);
        IBAN += Integer.toString(r1) + Integer.toString(r2) + " ";

        int count = 0;
        int n = 0;
        for(int i =0; i < 20;i++)
        {
            if(count == 4)
            {
                IBAN += " ";
                count =0;
            }
            else
                n = value.nextInt(10);
            IBAN += Integer.toString(n);
            count++;
        }
        int last2Number = value.nextInt(10,99);
        IBAN += " ";
        IBAN+=Integer.toString(last2Number);

        String query = "SELECT COUNT(user_IBAN) FROM user_info_table WHERE user_IBAN = ?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(IBAN);
        ArrayList<Object> IBANcount = DatabaseOperationsClass.getData(query,parameters);
        Object temp = IBANcount.get(0);
        if(Integer.parseInt(temp.toString())>0){
            generateRandomIBAN();
        }

        return IBAN;
    }

    /***
     *
     * @param currentScreen takes current screen as JFrame type variable sets its visibility parameter to false
     * @param nextScreen takes current screen as JFrame type variable sets its visibility parameter to true
     */
    public static void changeScreens(JFrame currentScreen,JFrame nextScreen){
        currentScreen.setVisible(false);
        nextScreen.setVisible(true);
    }

    /**
     *
     * @param phoneOrMail string type variable
     * @return returns an array list containing name,surname,IBAN,email address,phone number and lira,dollar,euro,gold balances and user's credit score.
     */
    public static ArrayList getUserData(String phoneOrMail){
        String user_ID_query = "SELECT user_ID from user_info_table where user_phone_number = ? OR user_email_address = ?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(phoneOrMail);
        parameters.add(phoneOrMail);
        ArrayList<Object> ID_array = DatabaseOperationsClass.getData(user_ID_query,parameters);
        int ID = Integer.parseInt(ID_array.get(0).toString());

        String user_info_query = "SELECT user_ID,user_name,user_surname,user_IBAN,user_email_address,user_phone_number FROM user_info_table where user_ID = ?";
        ArrayList<Object> parameters1 = new ArrayList<>();
        parameters1.add(ID);
        ArrayList<Object> info_array = DatabaseOperationsClass.getData(user_info_query,parameters1);

        String balance_info_query = "SELECT lira,dollar,euro,gold FROM bankingappdatabase.user_balance_table where ID = ?";
        ArrayList<Object> parameters2 = new ArrayList<>();
        parameters2.add(ID);
        ArrayList<Object> balanceArray = DatabaseOperationsClass.getData(balance_info_query,parameters2);
        ArrayList<Object> returnArray = new ArrayList<Object>();

        returnArray.addAll(info_array);
        returnArray.addAll(balanceArray);

        return returnArray;
    }
    public static ArrayList getUserData(int ID){
        String user_info_query = "SELECT user_ID,user_name,user_surname,user_IBAN,user_email_address,user_phone_number FROM user_info_table where user_ID = ?";
        ArrayList<Object> parameters1 = new ArrayList<>();
        parameters1.add(ID);
        ArrayList<Object> info_array = DatabaseOperationsClass.getData(user_info_query,parameters1);
        String balance_info_query = "SELECT lira,dollar,euro,gold FROM bankingappdatabase.user_balance_table where ID = ?";
        ArrayList<Object> parameters2 = new ArrayList<>();
        parameters2.add(ID);
        ArrayList<Object> balanceArray = DatabaseOperationsClass.getData(balance_info_query,parameters2);
        ArrayList<Object> returnArray = new ArrayList<Object>();
        returnArray.addAll(info_array);
        returnArray.addAll(balanceArray);
        return returnArray;
    }
}
