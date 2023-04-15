package BalanceScreenPackage;

import HelperMethodsPackage.HelperMethods;
import UserObjectPackage.UserObject;
import UserProfileScreenPackage.UserProfileScreen;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BalanceScreen {
    public static final JFrame userBalanceScreen = new JFrame("Balance Screen");

    public static JLabel user_lira = new JLabel();
    public static JLabel user_dollar = new JLabel();
    public static JLabel user_euro = new JLabel();
    public static JLabel user_gold = new JLabel();

    private static int ID;
    public BalanceScreen(){
        JLabel lira_label = new JLabel("Lira : ");
        lira_label.setBounds(10,10,100,30);
        userBalanceScreen.add(lira_label);

        JLabel dollarlabel = new JLabel("Dollar : ");
        dollarlabel.setBounds(10,40,100,30);
        userBalanceScreen.add(dollarlabel);

        JLabel euro_label = new JLabel("Euro : ");
        euro_label.setBounds(10,70,100,30);
        userBalanceScreen.add(euro_label);

        JLabel gold_label = new JLabel("Gold(gr) : ");
        gold_label.setBounds(10,100,100,30);
        userBalanceScreen.add(gold_label);

        user_lira.setBounds(110,10,200,30);
        BalanceScreen.userBalanceScreen.add(user_lira);
        user_lira.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                float debt = BalanceScreenBackend.getUserLoan(BalanceScreenBackend.getID(),"lira");
                user_lira.setToolTipText(debt+" of the " +user_lira.getText()+" lira is a loan");
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        user_dollar.setBounds(110,40,200,30);
        BalanceScreen.userBalanceScreen.add(user_dollar);
        user_dollar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                float debt = BalanceScreenBackend.getUserLoan(BalanceScreenBackend.getID(),"dollar");
                user_dollar.setToolTipText(debt+" of the " +user_dollar.getText()+" dollar is a loan");
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        user_euro.setBounds(110,70,200,30);
        BalanceScreen.userBalanceScreen.add(user_euro);
        user_euro.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                float debt = BalanceScreenBackend.getUserLoan(BalanceScreenBackend.getID(),"euro");
                user_euro.setToolTipText(debt+" of the " +user_euro.getText()+" euro is a loan");
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        user_gold.setBounds(110,100,200,30);
        BalanceScreen.userBalanceScreen.add(user_gold);

        JLabel currentMarketLabel = new JLabel("Market Prices("+BalanceScreenBackend.date+")");
        currentMarketLabel.setBounds(310,0,190,30);
        userBalanceScreen.add(currentMarketLabel);
        JLabel x = new JLabel("         BUY              |     SELL");
        x .setBounds(310,30,190,30);
        userBalanceScreen.add(x);

        JLabel current_dollarlabel = new JLabel(("Dollar : "+BalanceScreenBackend.liraToDollar+" | "+ BalanceScreenBackend.dollarToLira));
        current_dollarlabel.setBounds(310,50,190,30);
        userBalanceScreen.add(current_dollarlabel);

        JLabel current_euro_label = new JLabel(("Euro : "+BalanceScreenBackend.liraToEuro+" | "+ BalanceScreenBackend.euroToLira));
        current_euro_label.setBounds(310,80,190,30);
        userBalanceScreen.add(current_euro_label);

        JLabel current_gold_label = new JLabel(("Gold(gr) : "+BalanceScreenBackend.liraToGold+" | "+ BalanceScreenBackend.goldToLira));
        current_gold_label.setBounds(310,110,190,30);
        userBalanceScreen.add(current_gold_label);



        JComboBox sellcurrencyCombobox = new JComboBox();
        sellcurrencyCombobox.addItem("Dollar");
        sellcurrencyCombobox.addItem("Euro");
        sellcurrencyCombobox.addItem("Gold(gr)");
        sellcurrencyCombobox.setBounds(70,160,100,30);
        userBalanceScreen.add(sellcurrencyCombobox);










        JLabel sellLabel = new JLabel();
        sellLabel.setBounds(280,160,200,30);
        userBalanceScreen.add(sellLabel);


        SpinnerModel sellSpinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE,10);
        JSpinner sellSpinner = new JSpinner(sellSpinnerModel);
        sellSpinner.setBounds(170,160,100,30);
        userBalanceScreen.add(sellSpinner);
        sellSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float converted_value = BalanceScreenBackend.changeCurrencies(sellcurrencyCombobox.getSelectedItem().toString(),0,Integer.parseInt(sellSpinner.getValue().toString()));
                sellLabel.setText("You get : "+converted_value+" lira");
            }
        });
        JLabel header = new JLabel("Buy and sell currencies");
        header.setBounds(0,130,150,30);
        userBalanceScreen.add(header);

        JButton sellButton = new JButton("Sell");
        sellButton.setBounds(0,160,70,30);
        userBalanceScreen.add(sellButton);
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float converted_value = BalanceScreenBackend.changeCurrencies(sellcurrencyCombobox.getSelectedItem().toString(),1,Float.parseFloat(sellSpinner.getValue().toString()));
                if(BalanceScreenBackend.canUserMakeThisTransaction(sellcurrencyCombobox.getSelectedItem().toString(),Float.parseFloat(sellSpinner.getValue().toString())))
                {
                    int dialogResult = JOptionPane.showConfirmDialog(null, ("Are you sure you want to sell "+sellSpinner.getValue().toString()+" "+ sellcurrencyCombobox.getSelectedItem().toString()+" for "+ converted_value +" lira"), "Confirmation" , JOptionPane.INFORMATION_MESSAGE);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        String message = BalanceScreenBackend.makeTheTransaction(sellcurrencyCombobox.getSelectedItem().toString(),1,Float.parseFloat(sellSpinner.getValue().toString()),converted_value);
                        JOptionPane.showMessageDialog(null,message);
                        BalanceScreenBackend.getUsersDataToScreen(BalanceScreenBackend.getID(),user_lira,user_dollar,user_euro,user_gold);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"You dont have enough "+ sellcurrencyCombobox.getSelectedItem().toString()+" to make this transaction." );
                }
            }
        });

        JComboBox buycurrencyCombobox = new JComboBox();
        buycurrencyCombobox.addItem("Dollar");
        buycurrencyCombobox.addItem("Euro");
        buycurrencyCombobox.addItem("Gold(gr)");
        buycurrencyCombobox.setBounds(70,200,100,30);
        userBalanceScreen.add(buycurrencyCombobox);

        JLabel buyLabel = new JLabel();
        buyLabel.setBounds(280,200,300,30);
        userBalanceScreen.add(buyLabel);

        SpinnerModel buySpinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE,10);
        JSpinner buySpinner = new JSpinner(buySpinnerModel);
        buySpinner.setBounds(170,200,100,30);
        userBalanceScreen.add(buySpinner);
        buySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float converted_value = BalanceScreenBackend.changeCurrencies(buycurrencyCombobox.getSelectedItem().toString(),1,Float.parseFloat(buySpinner.getValue().toString()));
                buyLabel.setText("You need : "+converted_value+" lira");
            }
        });


        JButton buyButton = new JButton("Buy");
        buyButton.setBounds(0,200,70,30);
        userBalanceScreen.add(buyButton);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float converted_value = BalanceScreenBackend.changeCurrencies(buycurrencyCombobox.getSelectedItem().toString(),1,Float.parseFloat(buySpinner.getValue().toString()));
                if(BalanceScreenBackend.canUserMakeThisTransaction("lira",converted_value))
                {
                    int dialogResult = JOptionPane.showConfirmDialog(null, ("Are you sure you want to buy "+buySpinner.getValue().toString()+" "+ buycurrencyCombobox.getSelectedItem().toString()+" for "+ converted_value +" lira"), "Confirmation" , JOptionPane.INFORMATION_MESSAGE);
                    if(dialogResult == JOptionPane.YES_OPTION){
                       String message = BalanceScreenBackend.makeTheTransaction(buycurrencyCombobox.getSelectedItem().toString(),0,Float.parseFloat(buySpinner.getValue().toString()),converted_value);
                       JOptionPane.showMessageDialog(null,message);
                       BalanceScreenBackend.getUsersDataToScreen(BalanceScreenBackend.getID(),user_lira,user_dollar,user_euro,user_gold);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"You dont have enough turkish lira to make this transaction." );
                }
            }
        });

        JButton toProfileButton = new JButton("back to profile");
        toProfileButton.setBounds(0,550,150,30);
        userBalanceScreen.add(toProfileButton);
        toProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelperMethods.changeScreens(userBalanceScreen,UserProfileScreen.userProfileScreen);
            }
        });

        JLabel sendMoneyLabel = new JLabel("Send an asset to another account");
        sendMoneyLabel.setBounds(0,260,300,30);
        userBalanceScreen.add(sendMoneyLabel);

        JLabel TR = new JLabel("TR");
        TR.setBounds(0,290,20,30);
        userBalanceScreen.add(TR);

        JTextField IBANField = new JTextField("Enter the IBAN of the account");
        IBANField.setBounds(20,290,300,30);
        userBalanceScreen.add(IBANField);
        final int[] counter = {0};
        IBANField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(counter[0] ==0){
                    IBANField.setText("");
                }
                counter[0]++;
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        JLabel ibanMessageLabel = new JLabel();
        ibanMessageLabel.setBounds(325,290,130,30);
        userBalanceScreen.add(ibanMessageLabel);
        IBANField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String IBAN = IBANField.getText();
                String result = BalanceScreenBackend.isIBANValid(IBAN);
                ibanMessageLabel.setText(result);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });


        JComboBox sendCurrencyCombobox = new JComboBox();
        sendCurrencyCombobox.setBounds(300,320,80,30);
        userBalanceScreen.add(sendCurrencyCombobox);
        sendCurrencyCombobox.addItem("Lira");
        sendCurrencyCombobox.addItem("Dollar");
        sendCurrencyCombobox.addItem("Euro");
        sendCurrencyCombobox.addItem("Gold(gr)");

        JLabel amountLabel = new JLabel("Amount of asset");
        amountLabel.setBounds(0,320,100,30);
        userBalanceScreen.add(amountLabel);
        SpinnerModel sendSpinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE,10);
        JSpinner amountSpinner = new JSpinner(sendSpinnerModel);
        amountSpinner.setBounds(100,320,200,30);
        userBalanceScreen.add(amountSpinner);

        JButton sendAssetButton = new JButton("Confirm");
        sendAssetButton.setBounds(380,320   ,100,30);
        userBalanceScreen.add(sendAssetButton);
        sendAssetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(BalanceScreenBackend.canUserMakeThisTransaction(sendCurrencyCombobox.getSelectedItem().toString(),Float.parseFloat(amountSpinner.getValue().toString()))){
                    String IBAN = IBANField.getText();
                    int dialogResult = JOptionPane.showConfirmDialog(null, ("Are you sure you want to send "+amountSpinner.getValue().toString()+" "+ sendCurrencyCombobox.getSelectedItem().toString()+" to "+ "TR"+IBAN  +" address?"), "Confirmation" , JOptionPane.INFORMATION_MESSAGE);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        if((BalanceScreenBackend.isIBANValid(IBAN).equals("Valid IBAN address!"))){
                            BalanceScreenBackend.sendAsset("TR"+IBAN,BalanceScreenBackend.getID(),sendCurrencyCombobox.getSelectedItem().toString(),Float.parseFloat(amountSpinner.getValue().toString()));
                            JOptionPane.showMessageDialog(null,"Successfully sent "+amountSpinner.getValue().toString()+" "+sendCurrencyCombobox.getSelectedItem()+" "+ "to "+"TR"+IBAN+" address!");
                            BalanceScreenBackend.getUsersDataToScreen(BalanceScreenBackend.getID(),user_lira,user_dollar,user_euro,user_gold);
                        }
                        else if((BalanceScreenBackend.isIBANValid(IBAN).equals("IBAN belongs to you!"))){
                            JOptionPane.showMessageDialog(null,"This IBAN address belongs to you!");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Invalid IBAN address!");
                        }

                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"You dont have enough "+sendCurrencyCombobox.getSelectedItem().toString()+" for this transaction.");
                }
            }
        });
        JLabel loanheader = new JLabel("Take a loan");
        loanheader.setBounds(0,360,150,30);
        userBalanceScreen.add(loanheader);


        JLabel loanSpinnerLabel = new JLabel("Enter the amount you want to borrow");
        loanSpinnerLabel.setBounds(0,380,215,30);
        userBalanceScreen.add(loanSpinnerLabel);

        SpinnerModel loanSpinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE,10);
        JSpinner loanSpinnner = new JSpinner(loanSpinnerModel);
        loanSpinnner.setBounds(215,380,100,30);
        userBalanceScreen.add(loanSpinnner);


        JComboBox loanCombobox = new JComboBox();
        loanCombobox.setBounds(315,380,100,30);
        userBalanceScreen.add(loanCombobox);
        loanCombobox.addItem("Lira");
        loanCombobox.addItem("Dollar");
        loanCombobox.addItem("Euro");

        JButton confirmButon = new JButton("Confirm");
        confirmButon.setBounds(415,380,80,30);
        userBalanceScreen.add(confirmButon);
        confirmButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(BalanceScreenBackend.canTakeALoan(Float.parseFloat(loanSpinnner.getValue().toString()),BalanceScreenBackend.getID(),loanCombobox.getSelectedItem().toString())){
                    float debt = BalanceScreenBackend.getUserLoan(BalanceScreenBackend.getID(),loanCombobox.getSelectedItem().toString().toLowerCase());
                    JOptionPane.showMessageDialog(null,"Successfully took a loan worth of "+debt+" "+loanCombobox.getSelectedItem().toString().toLowerCase() );
                    BalanceScreenBackend.takeLoanorPay(Float.parseFloat(loanSpinnner.getValue().toString()),BalanceScreenBackend.getID(),loanCombobox.getSelectedItem().toString(),"loan");
                    BalanceScreenBackend.getUsersDataToScreen(BalanceScreenBackend.getID(),user_lira,user_dollar,user_euro,user_gold);
                }
                else{
                    JOptionPane.showMessageDialog(null,"you are not eligible to take a loan. you cant borrow assets worth more than 10.000 turkish lira.");
                }
            }
        });



        JLabel paymentheader = new JLabel("Pay a loan");
        paymentheader.setBounds(0,420,150,30);
        userBalanceScreen.add(paymentheader);


        JLabel paymentSpinnerLabel = new JLabel("Enter the amount you want to pay");
        paymentSpinnerLabel.setBounds(0,450,215,30);
        userBalanceScreen.add(paymentSpinnerLabel);

        SpinnerModel paymentSpinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE,10);
        JSpinner paymentSpinnner = new JSpinner(paymentSpinnerModel);
        paymentSpinnner.setBounds(215,450,100,30);
        userBalanceScreen.add(paymentSpinnner);


        JComboBox paymentCombobox = new JComboBox();
        paymentCombobox.setBounds(315,450,100,30);
        userBalanceScreen.add(paymentCombobox);
        paymentCombobox.addItem("Lira");
        paymentCombobox.addItem("Dollar");
        paymentCombobox.addItem("Euro");

        JButton confirmButonPay = new JButton("Confirm");
        confirmButonPay.setBounds(415,450,80,30);
        userBalanceScreen.add(confirmButonPay);
        confirmButonPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(BalanceScreenBackend.doesHavaLoan(BalanceScreenBackend.getID(),paymentCombobox.getSelectedItem().toString())){
                    if(BalanceScreenBackend.canPayLoan(BalanceScreenBackend.getID(),paymentCombobox.getSelectedItem().toString(),Float.parseFloat(paymentSpinnner.getValue().toString()))){
                        float debt = BalanceScreenBackend.getUserLoan(BalanceScreenBackend.getID(),paymentCombobox.getSelectedItem().toString().toLowerCase());
                        JOptionPane.showMessageDialog(null,"Successfully paid your debt of "+debt+" "+paymentCombobox.getSelectedItem().toString().toLowerCase());
                        BalanceScreenBackend.takeLoanorPay(Float.parseFloat(paymentSpinnner.getValue().toString()),BalanceScreenBackend.getID(),paymentCombobox.getSelectedItem().toString(),"payment");
                        BalanceScreenBackend.getUsersDataToScreen(BalanceScreenBackend.getID(),user_lira,user_dollar,user_euro,user_gold);
                    }
                }
            }
        });
        userBalanceScreen.setSize(500,700);
        userBalanceScreen.setLayout(null);
        userBalanceScreen.setVisible(false);
        userBalanceScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
