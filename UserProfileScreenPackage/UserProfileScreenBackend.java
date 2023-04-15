package UserProfileScreenPackage;

import DatabasePackage.DatabaseOperationsClass;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class UserProfileScreenBackend {
    public static void editProfile(int ID,String name, String surname,String email, String phoneNumber){
        String editQuery = "UPDATE user_info_table SET user_name = ? , user_surname = ? , user_email_address = ? , user_phone_number = ? WHERE user_ID = ? ";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(name);
        parameters.add(surname);
        parameters.add(email);
        parameters.add(phoneNumber);
        parameters.add(ID);
        DatabaseOperationsClass.updateDeleteInsert(editQuery,parameters);

    }


}
