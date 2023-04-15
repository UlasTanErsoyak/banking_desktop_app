package UserProfileScreenPackage;

import javax.swing.*;

import BalanceScreenPackage.BalanceScreenBackend;
import HelperMethodsPackage.HelperMethods;
import LoginScreenPackage.LoginScreen;
import RegisterScreenPackage.RegisterScreenBackend;
import UserObjectPackage.UserObject;
import BalanceScreenPackage.BalanceScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserProfileScreen {
    public static final JFrame userProfileScreen = new JFrame("Profile Screen");
    public static boolean isExecuted = false;
    public static int ID;
    public UserProfileScreen(){
        JLabel user_name_label = new JLabel("Name : ");
        user_name_label.setBounds(10,10,100,30);
        userProfileScreen.add(user_name_label);

        JLabel user_surname_label = new JLabel("Surname : ");
        user_surname_label.setBounds(10,40,100,30);
        userProfileScreen.add(user_surname_label);

        JLabel user_phone_number_label = new JLabel("Phone number : ");
        user_phone_number_label.setBounds(10,70,100,30);
        userProfileScreen.add(user_phone_number_label);

        JLabel user_email_address_label = new JLabel("Email address : ");
        user_email_address_label.setBounds(10,100,100,30);
        userProfileScreen.add(user_email_address_label);

        JLabel user_IBAN_label = new JLabel("IBAN : ");
        user_IBAN_label.setBounds(10,130,100,30);
        userProfileScreen.add(user_IBAN_label);


        JButton logoutButton = new JButton("log out");
        logoutButton.setBounds(10,200,100,30);
        userProfileScreen.add(logoutButton);




        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelperMethods.changeScreens(userProfileScreen, LoginScreen.loginScreen);
            }
        });

        userProfileScreen.setSize(500,700);
        userProfileScreen.setLayout(null);
        userProfileScreen.setVisible(false);
        userProfileScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     *  takes user object and shows objects name surname phonenumber and email parameters to the user
     * @param user UserObject type variable
     */
    public static void getUsersDataToScreen(UserObject user){
        JLabel user_name = new JLabel(user.getName());
        user_name.setBounds(110,10,300,30);

        JLabel user_surname = new JLabel(user.getSurname());
        user_surname.setBounds(110,40,300,30);


        JLabel user_phone_number = new JLabel(user.getPhone_number());
        user_phone_number.setBounds(110,70,300,30);

        JLabel user_email_address = new JLabel(user.getEmail());
        user_email_address.setBounds(110,100,300,30);

        JLabel IBAN = new JLabel(user.getIBAN());
        IBAN.setBounds(110,130,300,30);


        JTextField user_name_edit = new JTextField(user.getName());
        user_name_edit.setBounds(110,10,300,30);
        user_name_edit.setVisible(false);

        JTextField user_surname_edit = new JTextField(user.getSurname());
        user_surname_edit.setBounds(110,40,300,30);
        user_surname_edit.setVisible(false);

        JTextField user_phone_number_edit = new JTextField(user.getPhone_number());
        user_phone_number_edit.setBounds(110,70,300,30);
        user_phone_number_edit.setVisible(false);

        JTextField user_email_address_edit = new JTextField(user.getEmail());
        user_email_address_edit.setBounds(110,100,300,30);
        user_email_address_edit.setVisible(false);





        JButton toBalanceButton = new JButton("assets and operations");
        toBalanceButton.setBounds(10,170,220,30);
        userProfileScreen.add(toBalanceButton);
        toBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = user.getID();
                BalanceScreenBackend.getUsersDataToScreen(id,BalanceScreen.user_lira,BalanceScreen.user_dollar,BalanceScreen.user_euro,BalanceScreen.user_gold);
                HelperMethods.changeScreens(userProfileScreen,BalanceScreen.userBalanceScreen);
            }
        });

        JButton editButton = new JButton("Edit profile");
        editButton.setBounds(380,10,120,30);


        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(410,10,90,30);
        cancelButton.setVisible(false);


        JButton saveButton = new JButton("save");
        saveButton.setBounds(410,40,90,30);
        saveButton.setVisible(false);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isNameOK = RegisterScreenBackend.isNameandSurnameisInCorrectFormat(user_name_edit.getText().trim(), user_surname_edit.getText().trim());
                boolean isEmailAvailable = RegisterScreenBackend.doesEmailAvailable(user_email_address_edit.getText().trim(),"edit", user.getID());
                boolean isEmailFormatOK = RegisterScreenBackend.isEmailinValidFormat(user_email_address_edit.getText().trim());
                boolean isPhoneNumberAvailable = RegisterScreenBackend.doesPhoneNumberAvailable(user_phone_number_edit.getText().trim(),"edit",user.getID());
                boolean isPhoneNumberFormatOK = RegisterScreenBackend.isPhoneNumberinValidFormat(user_phone_number_edit.getText().trim());
                if(isNameOK){
                    if(isEmailFormatOK){
                        if(isEmailAvailable){
                            if(isPhoneNumberFormatOK){
                                if(isPhoneNumberAvailable){
                                    UserProfileScreenBackend.editProfile(
                                            user.getID(),
                                            user_name_edit.getText().trim(),
                                            user_surname_edit.getText().trim(),
                                            user_email_address_edit.getText().trim(),
                                            user_phone_number_edit.getText().trim()
                                            );
                                    JOptionPane.showMessageDialog(null,"edited successfully!");
                                    user_name.setText(user_name_edit.getText().trim());
                                    user_surname.setText(user_surname_edit.getText().trim());
                                    user_email_address.setText(user_email_address_edit.getText().trim());
                                    user_phone_number.setText(user_phone_number_edit.getText().trim());

                                    user_name_edit.setVisible(false);
                                    user_surname_edit.setVisible(false);
                                    user_phone_number_edit.setVisible(false);
                                    user_email_address_edit.setVisible(false);

                                    user_name.setVisible(true);
                                    user_surname.setVisible(true);
                                    user_phone_number.setVisible(true);
                                    user_email_address.setVisible(true);

                                    editButton.setVisible(true);
                                    cancelButton.setVisible(false);
                                    saveButton.setVisible(false);
                                }
                                else{
                                    JOptionPane.showMessageDialog(null,"This phone number is already in use!");
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(null,"Invalid phone number format!");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"This email address is already in use!");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Invalid email address format!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Invalid name or surname!");
                }
            }
        });



        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user_name_edit.setVisible(false);
                user_surname_edit.setVisible(false);
                user_phone_number_edit.setVisible(false);
                user_email_address_edit.setVisible(false);

                user_name.setVisible(true);
                user_surname.setVisible(true);
                user_phone_number.setVisible(true);
                user_email_address.setVisible(true);

                editButton.setVisible(true);
                cancelButton.setVisible(false);
                saveButton.setVisible(false);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user_name_edit.setVisible(true);
                user_surname_edit.setVisible(true);
                user_phone_number_edit.setVisible(true);
                user_email_address_edit.setVisible(true);

                user_name.setVisible(false);
                user_surname.setVisible(false);
                user_phone_number.setVisible(false);
                user_email_address.setVisible(false);

                editButton.setVisible(false);
                cancelButton.setVisible(true);
                saveButton.setVisible(true);
            }
        });
        if(!isExecuted) {
            userProfileScreen.add(editButton);
            userProfileScreen.add(user_surname);
            userProfileScreen.add(user_name);
            userProfileScreen.add(user_phone_number);
            userProfileScreen.add(user_email_address);
            userProfileScreen.add(IBAN);
            userProfileScreen.add(user_name_edit);
            userProfileScreen.add(user_surname_edit);
            userProfileScreen.add(user_phone_number_edit);
            userProfileScreen.add(user_email_address_edit);
            userProfileScreen.add(saveButton);
            userProfileScreen.add(cancelButton);
            isExecuted= true;
        }
    }
}
