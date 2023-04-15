package RegisterScreenPackage;
import DatabasePackage.DatabaseOperationsClass;
import HelperMethodsPackage.HelperMethods;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterScreenBackend {


    /**
     *
     * @param name string type variable
     * @param surname string type variable
     * @return returns true if name and surname doesn't contain any digits else returns false
     */
    public static boolean isNameandSurnameisInCorrectFormat(String name,String surname){
        char[] nameChar = name.toCharArray();
        char[] surnameChar = surname.toCharArray();
        for(char c : nameChar){
            if(Character.isDigit(c)){
                return false;
            }
        }
        for(char c : surnameChar){
            if(Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
    /***
     *
     * @param email takes user's email as a string type variable and checks if it exists in database
     * @return  true if email does not exist in database, returns false if it exists.
     */
    public static boolean doesEmailAvailable(String email,String mode,int ID){
        if(mode.equals("register")){
            String query = "SELECT COUNT(user_email_address) FROM user_info_table WHERE user_email_address = ?";
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(email);
            ArrayList<Object> emailcount = DatabaseOperationsClass.getData(query,parameters);
            Object temp = emailcount.get(0);
            if(Integer.parseInt(temp.toString())==0){
                return true;
            }
            return false;
        }
        if(mode.equals("edit")){
            String query = "SELECT COUNT(user_email_address) FROM user_info_table WHERE user_email_address = ? AND user_ID != ?";
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(email);
            parameters.add(ID);
            ArrayList<Object> emailcount = DatabaseOperationsClass.getData(query,parameters);
            Object temp = emailcount.get(0);
            if(Integer.parseInt(temp.toString())==0){
                return true;
            }
            return false;
        }
        return false;
    }
    //https://stackoverflow.com/questions/8204680/java-regex-email

    /**
     *
     * @param email string type variable
     * @return returns true if given email is in valid form else returns false
     */
    public static boolean isEmailinValidFormat(String email){
        final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
        }

    /***
     *
     * @param phoneNumber takes user's phone number as a string type variable and checks if it exists in database
     * @return  true if email does not exist in database, returns false if it exists
     */
    public static boolean doesPhoneNumberAvailable(String phoneNumber,String mode,int ID){
        if(mode.equals("register")){
            String query = "SELECT COUNT(user_phone_number) FROM user_info_table WHERE user_phone_number = ?";
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(phoneNumber);
            ArrayList<Object> phonenumbercount = DatabaseOperationsClass.getData(query,parameters);
            Object temp = phonenumbercount.get(0);
            if(Integer.parseInt(temp.toString())==0){
                return true;
            }
            return false;
        }
        if(mode.equals("edit")){
            String query = "SELECT COUNT(user_phone_number) FROM user_info_table WHERE user_phone_number = ? AND user_ID != ?";
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(phoneNumber);
            parameters.add(ID);
            ArrayList<Object> phonenumbercount = DatabaseOperationsClass.getData(query,parameters);
            Object temp = phonenumbercount.get(0);
            if(Integer.parseInt(temp.toString())==0){
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * checks if the given phone number is in correct format or not
     * @param number string type variable
     * @return returns true if number is in valid format else returns false
     */
    public static boolean isPhoneNumberinValidFormat(String number){
        if(number.length()<10 && number.length()>13){
            return false;
        }
        String x = number.substring(0,1);
        if(!x.equals("0")){
            return false;
        }
        for (int i = 0; i < number.length(); i++) {
            if ((Character.isLetter(number.charAt(i)))) {
                return false;}
        }
        return true;
    }


    //https://stackoverflow.com/questions/32928715/java-checking-if-a-phone-number-fits-a-valid-format


    /***
     *
     * @param password string type variable
     * @return retuns true if password is longer than 10 characters, contains both upper
     * and lower case chracters and digits
     * else returns false
     */
    public static boolean isPasswordStrongEnough(String password){
        if(password.length()<10){
            return false;
        }
        else if(password.toLowerCase() == password){
            return false;
        }
        else if(password.toUpperCase() == password){
            return false;
        }
        else if(!password.matches(".*\\d+.*")){
            return false;
        }
        return true;
    }

    /**
     * takes phone number and creates dollar,euro,gold and lira accounts for the given account
     * @param phoneNumber string type variable
     */
    public static void initializeusersAccount(String phoneNumber){
        String IDquery = "SELECT user_ID FROM user_info_table WHERE user_phone_number = ?";
        ArrayList<Object> IDparameters = new ArrayList<>();
        IDparameters.add(phoneNumber);
        ArrayList<Object> IDarray = DatabaseOperationsClass.getData(IDquery,IDparameters);
        Object tempID = IDarray.get(0);
        String ID = tempID.toString();

        String initializeQuery = "INSERT INTO user_balance_table" +
                " (ID,lira,dollar,euro,gold)" +
                " VALUES (?,?,?,?,?,?)";
        ArrayList<Object> initializeparameters = new ArrayList<>();
        initializeparameters.add(ID);
        initializeparameters.add(0);
        initializeparameters.add(0);
        initializeparameters.add(0);
        initializeparameters.add(0);
        DatabaseOperationsClass.updateDeleteInsert(initializeQuery,initializeparameters);

        String loanAccountQuerry ="INSERT INTO loan_table (ID,lira,dollar,euro) VALUES (?,?,?,?,?)";
        ArrayList<Object> initializeparameters1 = new ArrayList<>();
        initializeparameters.add(ID);
        initializeparameters.add(0);
        initializeparameters.add(0);
        initializeparameters.add(0);
        DatabaseOperationsClass.updateDeleteInsert(loanAccountQuerry,initializeparameters1);

    }
    /***
     *takes information provided by user at register screen, registers user to database if phone number and email are available, else sends a warning message to user
     * adds a random integer between 1-8192 to the end of the user's password and encrypts it with SHA256
     * generates a random IBAN number for user if register is successful
     * @param name user's name from usernametextfield
     * @param surname user's surname from usersurnametextfield
     * @param emailAddress user's email address from emailddresstextfield
     * @param phoneNumber user's phone from phonenumbertextfield
     * @param passChar user's password from passwordfield
     * @param passConfirmChar user's password from confirmpasswordfield
     */
    public static String registerUserToDatabase(String name,String surname, String emailAddress, String phoneNumber,char[] passChar ,char[] passConfirmChar){
        if(!isNameandSurnameisInCorrectFormat(name,surname)){
            return "Your name or surname cant contain digits.";
        }
        if(!(Arrays.equals(passChar, passConfirmChar))){
            return "Passwords does not match";
        }
        String password = new String(passChar);

        if(!isPasswordStrongEnough(password.trim())){
            return "Password must contain at least 10 characters,upper and lower case characters and at least one digit.";
        }
        if(!doesEmailAvailable(emailAddress.trim(),"register",0)){
            return "This email address already exists";
        }
        if(!isEmailinValidFormat(emailAddress.trim())){
            return "This email is invalid";
        }
        if(!doesPhoneNumberAvailable(phoneNumber.trim(),"register",0)){
            return "This phone number already exists";
        }
        if(!isPhoneNumberinValidFormat(phoneNumber)){
            return "This phone number is invalid.Try adding 0 at the start of the number.";
        }
        Random rand = new Random();
        int randomSalt = rand.nextInt(1,8192);
        String hashesPassword = HelperMethods.hashPassword(password.trim(),randomSalt);

        String IBAN = HelperMethods.generateRandomIBAN();

        String query = "INSERT INTO user_info_table" +
                " (user_name,user_surname,user_password,user_password_salt,user_IBAN,user_email_address,user_phone_number)" +
                " VALUES (?,?,?,?,?,?,?)";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(name.substring(0, 1).toUpperCase().trim() + name.substring(1).trim());
        parameters.add(surname.substring(0, 1).toUpperCase().trim() + surname.substring(1).trim());
        parameters.add(hashesPassword);
        parameters.add(randomSalt);
        parameters.add(IBAN);
        parameters.add(emailAddress.trim());
        parameters.add(phoneNumber.trim());
        DatabaseOperationsClass.updateDeleteInsert(query,parameters);
        initializeusersAccount(phoneNumber);
         return "Registered successfully. Logging you in automatically.";
    }
}