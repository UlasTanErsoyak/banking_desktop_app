package LoginScreenPackage;

import HelperMethodsPackage.HelperMethods;
import RegisterScreenPackage.RegisterScreen;
import UserObjectPackage.UserObject;
import UserProfileScreenPackage.UserProfileScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginScreen {
    public static final JFrame loginScreen = new JFrame("Login Screen");

    public LoginScreen(){
        JLabel emailorphonelabel = new JLabel("Email Address or Phone Number");
        emailorphonelabel.setBounds(10,50,200,35);
        loginScreen.add(emailorphonelabel);

        JLabel passwordlabel= new JLabel("Password ");
        passwordlabel.setBounds(30,85,200,35);
        loginScreen.add(passwordlabel);

        JTextField emailorphonenumbertextfield = new JTextField();
        emailorphonenumbertextfield.setBounds(210,50,200,35);
        loginScreen.add(emailorphonenumbertextfield);

        JPasswordField passwordfield = new JPasswordField();
        passwordfield.setBounds(210,85,200,35);
        loginScreen.add(passwordfield);

        JButton loginbutton = new JButton("Login");
        loginbutton.setBounds(340,120,70,40);
        loginScreen.add(loginbutton);

        JCheckBox rememberMe = new JCheckBox("remember me",false);
        rememberMe.setBounds(200,120,140,40);
        loginScreen.add(rememberMe);



        //login button click event
        loginbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailorphone = emailorphonenumbertextfield.getText().trim();
                char[] password = passwordfield.getPassword();
                boolean result = LoginScreenBackend.isUserLoggingIn(emailorphone,password);
                if(result){
                    JOptionPane.showMessageDialog(null,"Login Successful!");
                    ArrayList<Object> data =HelperMethods.getUserData(emailorphone);
                    UserObject x = new UserObject(data);
                    UserProfileScreen.getUsersDataToScreen(x);
                    if(rememberMe.isSelected()){
                        LoginScreenBackend.remember_me_checked(emailorphone,1);
                        emailorphonenumbertextfield.setText(emailorphone);
                    }
                    else{
                        LoginScreenBackend.remember_me_checked(emailorphone,0);
                        emailorphonenumbertextfield.setText("");
                    }
                    HelperMethods.changeScreens(loginScreen,UserProfileScreen.userProfileScreen);
                    passwordfield.setText("");

                }
                else{
                    JOptionPane.showMessageDialog(null,"Email, phone number or password is wrong. Check them and try again. If you dont have an account click the button down below.");
                }
            }
        });

        JLabel toregisterlabel = new JLabel("Don't have an account?");
        toregisterlabel.setBounds(10,150,200,35);
        loginScreen.add(toregisterlabel);

        JButton toregisterscreenbutton = new JButton("Click Here!");
        toregisterscreenbutton.setBounds(10,190,130,40);
        loginScreen.add(toregisterscreenbutton);

        //to register screen button event
        toregisterscreenbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelperMethods.changeScreens(loginScreen, RegisterScreen.registerScreen);
            }
        });
        String rs = LoginScreenBackend.user_info_remember();
        if(rs != null){
            emailorphonenumbertextfield.setText(rs);
            rememberMe.setSelected(true);
        }
        loginScreen.setSize(500,700);
        loginScreen.setLayout(null);
        loginScreen.setVisible(true);
        loginScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
