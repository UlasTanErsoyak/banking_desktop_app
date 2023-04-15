package LoginScreenPackage;
import DatabasePackage.DatabaseOperationsClass;
import HelperMethodsPackage.HelperMethods;
import java.util.ArrayList;

public class LoginScreenBackend {
    /**
     *
     * @param numberOrEmail user's input on login screen. it can be phone number or email address
     * @return returns true if the input from the user is a valid phone number or a valid email address else returns false
     */
    public static boolean isNumberorEmailCorrect(String numberOrEmail){
        String query = "SELECT COUNT(user_phone_number) FROM user_info_table WHERE user_phone_number = ? OR user_email_address = ?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(numberOrEmail);
        parameters.add(numberOrEmail);
        ArrayList<Object> emailorphonecount = DatabaseOperationsClass.getData(query,parameters);
        Object temp = emailorphonecount.get(0);
        if(Integer.parseInt(temp.toString())==0){
            return false;
        }
        return true;
    }

    /**
     *
     * @param numberOrEmail user's input on login screen. it can be phone number or email address
     * @param passChar user's password input on login screen as a char[] variable type
     * @return returns true if password is correct else returns false
     */
    public static boolean isPasswordCorrect(String numberOrEmail,char[] passChar){
        String query = "SELECT user_password_salt,user_password FROM user_info_table WHERE user_phone_number = ? OR user_email_address = ?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(numberOrEmail);
        parameters.add(numberOrEmail);
        ArrayList<Object> passSaltArray = DatabaseOperationsClass.getData(query,parameters);

        Object tempSalt = passSaltArray.get(0);
        String salt = tempSalt.toString();

        Object tempPass = passSaltArray.get(1);
        String password = tempPass.toString();

        String rawPass = new String(passChar);

        String hashedPassword = HelperMethods.hashPassword(rawPass+salt);

        if(!hashedPassword.equals(password)){
            return false;
        }
        return true;
    }

    /**
     *
     * @param numberOrEmail  user's input on login screen. it can be phone number or email address
     * @param password user's password input on login screen as a char[] variable type
     * @return returns true if inputs are correct else returns false
     */
    public static boolean isUserLoggingIn(String numberOrEmail,char[] password){
        if(isNumberorEmailCorrect(numberOrEmail) && isPasswordCorrect(numberOrEmail,password)){
            return true;
        }
        return false;
    }
    public static void remember_me_checked(String phone,int checkedORnot){
        String IDquery = "SELECT user_ID FROM user_info_table WHERE user_phone_number = ? OR user_email_address = ?";
        ArrayList<Object> IDparameters = new ArrayList<>();
        IDparameters.add(phone);
        IDparameters.add(phone);
        ArrayList<Object> IDarray = DatabaseOperationsClass.getData(IDquery,IDparameters);
        int ID = Integer.parseInt(IDarray.get(0).toString());

        String update_remember_me_query = "UPDATE remember_me_table SET is_remember_me_checked = ? , ID = ? , input = ?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(checkedORnot);
        parameters.add(ID);
        parameters.add(phone);
        DatabaseOperationsClass.updateDeleteInsert(update_remember_me_query,parameters);
    }

    public static String user_info_remember(){
        String query = "SELECT * FROM remember_me_table;";
        ArrayList<Object> IDparameters = new ArrayList<>();
        ArrayList<Object> array = DatabaseOperationsClass.getData(query,IDparameters);
        int ID = Integer.parseInt(array.get(0).toString());
        int is_checked = Integer.parseInt(array.get(1).toString());
        String user_input = array.get(2).toString();

        if(is_checked==1){
            return user_input;
        }
        return null;
    }
}
