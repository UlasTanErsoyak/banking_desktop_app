package BalanceScreenPackage;

import DatabasePackage.DatabaseOperationsClass;
import HelperMethodsPackage.HelperMethods;
import UserObjectPackage.UserObject;

import javax.management.Query;
import javax.swing.*;
import javax.xml.crypto.Data;

import BalanceScreenPackage.BalanceScreen;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class BalanceScreenBackend {
    private static int ID;
    public static float dollarToLira;
    public static float euroToLira;
    public static float goldToLira;
    public static float liraToDollar;
    public static float liraToEuro;
    public static float liraToGold;
    public static String date;


    /**
     *
     * @param convertedCurrency currency name
     * @param buyORsell 1 if buying , 0 if selling
     */
    public static float changeCurrencies(String convertedCurrency,int buyORsell,float spinnerNumber){
        if(buyORsell == 1){
            if(convertedCurrency.equals("Dollar")){
                return spinnerNumber*liraToDollar;
            }
            else if (convertedCurrency.equals("Euro")) {
                return spinnerNumber*liraToEuro;
            }
            else{
                return spinnerNumber*liraToGold;
            }
        }
        if(buyORsell == 0){
            if(convertedCurrency.equals("Dollar")){
                return spinnerNumber*dollarToLira;
            }
            else if (convertedCurrency.equals("Euro")) {
                return spinnerNumber*euroToLira;
            }
            else{
                return spinnerNumber*goldToLira;
            }
        }
        return 0;
    }
    public static void getUsersDataToScreen(int id,JLabel lira,JLabel dollar,JLabel euro,JLabel gold){
        ArrayList userData  = HelperMethods.getUserData(id);
        UserObject user = new UserObject(userData);

        lira.setText(""+user.getLira());
        dollar.setText(""+user.getDollar());
        euro.setText(""+user.getEuro());
        gold.setText(""+user.getGold());

        ID= id;
    }
    public static boolean canUserMakeThisTransaction(String currency,float requiredAmount){
        String query = null;
        if(currency.toLowerCase().equals("dollar")){
            query = "SELECT dollar FROM user_balance_table WHERE ID=?";
        }
        else if(currency.toLowerCase().equals("euro")){
            query = "SELECT euro FROM user_balance_table WHERE ID=?";
        }
        else if(currency.toLowerCase().equals("gold(gr)")){
            query = "SELECT gold FROM user_balance_table WHERE ID=?";
        }
        else if(currency.toLowerCase().equals("lira")){
            query = "SELECT lira FROM user_balance_table WHERE ID=?";
        }
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(ID);
        ArrayList<Object> dataArray = DatabaseOperationsClass.getData(query,parameters);
        float usersMoney = Float.parseFloat(dataArray.get(0).toString());
        if(usersMoney<requiredAmount){
            return false;
        }
        return true;
    }
    public static int getID(){
        return ID;
    }
    public static String makeTheTransaction(String currency,int buyORsell,float currencyAmount,float liraAmount){
        String query = "Select lira,";
        String query2= "UPDATE user_balance_table SET lira= ?,";
        if(currency.toLowerCase().equals("dollar")){
            query += "dollar";
            query2 += "dollar";

        }
        else if(currency.toLowerCase().equals("euro")){
            query += "euro";
            query2 += "euro";

        }
        else if(currency.toLowerCase().equals("gold(gr)")){
            query += "gold";
            query2 += "gold";

        }
        query+=" FROM user_balance_table WHERE ID=?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(ID);
        ArrayList<Object> dataArray = DatabaseOperationsClass.getData(query,parameters);

        float lira = Float.parseFloat(dataArray.get(0).toString());
        float targetCurrency = Float.parseFloat(dataArray.get(1).toString());
        //buy
        if(buyORsell == 0){
            lira -=liraAmount;
            targetCurrency+=currencyAmount;
        }
        //sell
        else{
            lira+=liraAmount;
            targetCurrency-=currencyAmount;
        }
        if(lira<0){
            return "You dont have enough turkish lira for this transaction";
        }
        if(targetCurrency<0){
            return "You dont have enough"+currency+" for this transaction";
        }
        query2+="= ? WHERE ID = ?";
        ArrayList<Object> parameters1 = new ArrayList<>();
        parameters1.add(lira);
        parameters1.add(targetCurrency);
        parameters1.add(ID);
        DatabaseOperationsClass.updateDeleteInsert(query2,parameters1);
        return "Transaction completed";
    }
    public static String isIBANValid(String IBAN){
        String selfIBANQuery = "SELECT user_ID from user_info_table where user_IBAN=?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add("TR"+IBAN);
        int userID;
        try{
            ArrayList<Object> data = DatabaseOperationsClass.getData(selfIBANQuery,parameters);
            userID=Integer.parseInt(data.get(0).toString());
        }
        catch(Exception e){
            return "Invalid IBAN address";
        }
        if(userID==ID){
            return "IBAN belongs to you!";
        }
        else{
            return "Valid IBAN address!";
        }
    }
    public static void sendAsset(String receiverIBAN,int senderID,String currency,float amount){
        String receiverIDQuery = "SELECT user_ID from user_info_table where user_IBAN = ?";
        ArrayList<Object> receiverParameters = new ArrayList<>();
        receiverParameters.add(receiverIBAN);
        ArrayList<Object> receiverIDArray = DatabaseOperationsClass.getData(receiverIDQuery,receiverParameters);
        int receiverID = Integer.parseInt(receiverIDArray.get(0).toString());

        String senderQuery = "SELECT ";
        String receiverQuery = "SELECT ";

        switch (currency.toLowerCase()) {
            case "lira" -> {
                senderQuery += "lira";
                receiverQuery += "lira";
            }
            case "dollar" -> {
                senderQuery += "dollar";
                receiverQuery += "dollar";
            }
            case "euro" -> {
                senderQuery += "euro";
                receiverQuery += "euro";
            }
            case "gold(gr)" -> {
                senderQuery += "gold";
                receiverQuery += "gold";
            }
        }
        senderQuery+=" from user_balance_table where ID=?";
        receiverQuery+=" from user_balance_table where ID=?";
        ArrayList<Object> senderParameters1 = new ArrayList<>();
        ArrayList<Object> receiverParameters1 = new ArrayList<>();
        senderParameters1.add(senderID);
        receiverParameters1.add(receiverID);
        ArrayList<Object> senderMoney = DatabaseOperationsClass.getData(senderQuery,senderParameters1);
        ArrayList<Object> receiverMoney =DatabaseOperationsClass.getData(receiverQuery,receiverParameters1);

        float senderMoneyfloat = Float.parseFloat(senderMoney.get(0).toString());
        float receiverMoneyfloat = Float.parseFloat(receiverMoney.get(0).toString());


        float senderMoneyFinal = senderMoneyfloat - amount;
        float receiverMoneyFinal = receiverMoneyfloat + amount;

        String senderMoneyUpdateQuery ="UPDATE user_balance_table SET";
        String receiverMoneyUpdateQuery ="UPDATE user_balance_table SET";

        switch (currency.toLowerCase()) {
            case "lira" -> {
                senderMoneyUpdateQuery += " lira = ?";
                receiverMoneyUpdateQuery += " lira = ?";
            }
            case "dollar" -> {
                senderMoneyUpdateQuery += " dollar = ?";
                receiverMoneyUpdateQuery += " dollar = ?";
            }
            case "euro" -> {
                senderMoneyUpdateQuery += " euro = ?";
                receiverMoneyUpdateQuery += " euro = ?";
            }
            case "gold(gr)" -> {
                senderMoneyUpdateQuery += " gold = ?";
                receiverMoneyUpdateQuery += " gold = ?";
            }
        }
        senderMoneyUpdateQuery+=" WHERE ID= ?";
        receiverMoneyUpdateQuery+=" WHERE ID= ?";
        ArrayList<Object> senderParameters2 = new ArrayList<>();
        ArrayList<Object> receiverParameters2 = new ArrayList<>();
        senderParameters2.add(senderMoneyFinal);
        receiverParameters2.add(receiverMoneyFinal);
        senderParameters2.add(senderID);
        receiverParameters2.add(receiverID);
        DatabaseOperationsClass.updateDeleteInsert(senderMoneyUpdateQuery,senderParameters2);
        DatabaseOperationsClass.updateDeleteInsert(receiverMoneyUpdateQuery,receiverParameters2);
    }

    public static boolean canTakeALoan(float amount,int ID,String currency) {
        String query = "SELECT lira,dollar,euro FROM loan_table WHERE ID = ?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(ID);
        ArrayList<Object> data = DatabaseOperationsClass.getData(query, parameters);
        float lira = Float.parseFloat(data.get(0).toString());
        float dollar = Float.parseFloat(data.get(1).toString());
        float euro = Float.parseFloat(data.get(2).toString());

        switch (currency.toLowerCase()) {
            case "lira" -> {
                amount = amount;
            }
            case "dollar" -> {
                amount = amount * dollarToLira;

            }
            case "euro" -> {
                amount = amount * euroToLira;
            }
        }
        float finalAmount = lira + dollar*dollarToLira + euro*euroToLira + amount;
        if(finalAmount<10_000){
            return true;
        }
        return false;
    }

    public static void takeLoanorPay(float amount,int ID,String currency,String operation){
        currency = currency.toLowerCase();

        String currentLoanQuery = "SELECT "+currency+" FROM loan_table WHERE ID = ?";
        ArrayList<Object> parameter = new ArrayList<>();
        parameter.add(ID);
        ArrayList<Object> array = DatabaseOperationsClass.getData(currentLoanQuery, parameter);
        float currentLoan = Float.parseFloat(array.get(0).toString());

        String currentMoneyQuery = "SELECT "+currency+" FROM user_balance_table WHERE ID = ?";
        ArrayList<Object> parameterss = new ArrayList<>();
        parameterss.add(ID);
        ArrayList<Object> array1 = DatabaseOperationsClass.getData(currentMoneyQuery, parameterss);
        float currentMoney = Float.parseFloat(array1.get(0).toString());
        float finalLoan=0;
        float finalMoney=0;

        if(operation.equals("loan")){
            finalLoan = currentLoan + amount;
            finalMoney = currentMoney + amount;
        }
        else if (operation.equals("payment")) {
            finalLoan = currentLoan - amount;
            finalMoney = currentMoney - amount;
        }
        if(finalLoan<0){
            finalMoney += finalLoan*-1;
            finalLoan=0;
        }


        String loan_query = "UPDATE loan_table SET "+currency.toLowerCase()+ " = ? WHERE ID = ?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(finalLoan);
        parameters.add(ID);
        DatabaseOperationsClass.updateDeleteInsert(loan_query,parameters);
        String updateBalanceQuery = "UPDATE user_balance_table SET "+currency+" = ? WHERE ID = ?";
        ArrayList<Object> parameters1 = new ArrayList<>();
        parameters1.add(finalMoney);
        parameters1.add(ID);
        DatabaseOperationsClass.updateDeleteInsert(updateBalanceQuery,parameters1);
    }
    public static float getUserLoan(int ID,String currency){
        String currentLoanQuery = "SELECT "+currency+" FROM loan_table WHERE ID = ?";
        ArrayList<Object> parameter = new ArrayList<>();
        parameter.add(ID);
        ArrayList<Object> array = DatabaseOperationsClass.getData(currentLoanQuery, parameter);
        return Float.parseFloat(array.get(0).toString());
    }

    public static boolean doesHavaLoan(int ID,String currency){
        String currentLoanQuery = "SELECT "+currency+" FROM loan_table WHERE ID = ?";
        ArrayList<Object> parameter = new ArrayList<>();
        parameter.add(ID);
        ArrayList<Object> array = DatabaseOperationsClass.getData(currentLoanQuery, parameter);
        float loan = Float.parseFloat(array.get(0).toString());
        if(loan==0){
            return false;
        }
        return true;
    }
    public static boolean canPayLoan(int ID,String currency,float amount){
        String currentLoanQuery = "SELECT "+currency+" FROM loan_table WHERE ID = ?";
        ArrayList<Object> parameter = new ArrayList<>();
        parameter.add(ID);
        ArrayList<Object> array = DatabaseOperationsClass.getData(currentLoanQuery, parameter);
        float loan = Float.parseFloat(array.get(0).toString());

        String currentMoneyQuery = "SELECT "+currency+" FROM user_balance_table WHERE ID = ?";
        ArrayList<Object> parameterss = new ArrayList<>();
        parameterss.add(ID);
        ArrayList<Object> array1 = DatabaseOperationsClass.getData(currentMoneyQuery, parameterss);
        float currentMoney = Float.parseFloat(array1.get(0).toString());
        if(currentMoney>=loan){
            return true;
        }
        return false;
    }

}
