package RegisterScreenPackage;

import HelperMethodsPackage.HelperMethods;
import LoginScreenPackage.LoginScreen;
import UserObjectPackage.UserObject;
import UserProfileScreenPackage.UserProfileScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegisterScreen {
    public static final JFrame registerScreen = new JFrame("Register Screen");

    public RegisterScreen(){
        //labels for textfield and password field.
        JLabel usernamelabel = new JLabel("Name");
        usernamelabel.setBounds(10,50,200,35);
        registerScreen.add(usernamelabel);

        JLabel usersurnamenamelabel = new JLabel("Surname");
        usersurnamenamelabel.setBounds(10,85,200,35);
        registerScreen.add(usersurnamenamelabel);

        JLabel emaillabel = new JLabel("Email address");
        emaillabel.setBounds(10,120,200,35);
        registerScreen.add(emaillabel);

        JLabel phonenumberlabel = new JLabel("Phone number");
        phonenumberlabel.setBounds(10,155,200,35);
        registerScreen.add(phonenumberlabel);

        JLabel passwordlabel = new JLabel("Password");
        passwordlabel.setBounds(10,190,200,35);
        registerScreen.add(passwordlabel);

        JLabel passwordconfirmlabel = new JLabel("Confirm password");
        passwordconfirmlabel.setBounds(10,225,200,35);
        registerScreen.add(passwordconfirmlabel);

        //textfields and password field

        JTextField namefield = new JTextField();
        namefield.setBounds(210,50,200,25);
        registerScreen.add(namefield);

        JTextField surnamefield = new JTextField();
        surnamefield.setBounds(210,85,200,25);
        registerScreen.add(surnamefield);

        JTextField emailfield = new JTextField();
        emailfield.setBounds(210,120,200,25);
        registerScreen.add(emailfield);

        JTextField phonenumberfield = new JTextField();
        phonenumberfield.setBounds(210,155,200,25);
        registerScreen.add(phonenumberfield);

        JPasswordField passwordfield = new JPasswordField();
        passwordfield.setBounds(210,190,200,25);
        registerScreen.add(passwordfield);

        JPasswordField confirmpasswordfield = new JPasswordField();
        confirmpasswordfield.setBounds(210,225,200,25);
        registerScreen.add(confirmpasswordfield);

        //Register button and go to login screen button
        JButton registerbutton = new JButton("Register");
        registerbutton.setBounds(310,260,100,40);
        registerScreen.add(registerbutton);
        registerbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //https://stackoverflow.com/questions/983964/why-does-jpasswordfield-getpassword-create-a-string-with-the-password-in-it

                String result = RegisterScreenBackend.registerUserToDatabase(
                        namefield.getText().trim(),
                        surnamefield.getText().trim(),
                        emailfield.getText().trim(),
                        phonenumberfield.getText().trim(),
                        passwordfield.getPassword(),
                        confirmpasswordfield.getPassword());
                JOptionPane.showMessageDialog(null,result);
                if(result.equals("Registered successfully. Logging you in automatically.")){
                    ArrayList<Object> data =HelperMethods.getUserData(phonenumberfield.getText().trim());
                    UserObject x = new UserObject(data);
                    UserProfileScreen.getUsersDataToScreen(x);
                    HelperMethods.changeScreens(registerScreen,UserProfileScreen.userProfileScreen);
                }
           }
        });

        //button and label for those user that already has an account
        JLabel alreadyregisteredlabel = new JLabel("You already have an account?");
        alreadyregisteredlabel.setBounds(10,300,300,40);
        registerScreen.add(alreadyregisteredlabel);

        JButton tologinscreenbutton = new JButton("Click Here!");
        tologinscreenbutton.setBounds(10,340,130,40);
        registerScreen.add(tologinscreenbutton);
        tologinscreenbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelperMethods.changeScreens(registerScreen, LoginScreen.loginScreen);
            }
        });

        registerScreen.setSize(500,700);
        registerScreen.setLayout(null);
        registerScreen.setVisible(false);
        registerScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
