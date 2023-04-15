package UserObjectPackage;

import DatabasePackage.DatabaseOperationsClass;

import java.util.ArrayList;

public class UserObject {
    private String name;
    private String surname;
    private String IBAN;
    private String email;
    private String phone_number;
    private Float lira;
    private Float dollar;
    private Float euro;
    private Float gold;
    private int ID;
    public UserObject(ArrayList<Object> user_data){
        this.ID = Integer.parseInt(user_data.get(0).toString());
        this.name = user_data.get(1).toString();
        this.surname = user_data.get(2).toString();
        this.IBAN = user_data.get(3).toString();
        this.email = user_data.get(4).toString();
        this.phone_number = user_data.get(5).toString();

        this.lira = Float.parseFloat(user_data.get(6).toString());
        this.dollar = Float.parseFloat(user_data.get(7).toString());
        this.euro = Float.parseFloat(user_data.get(8).toString());
        this.gold = Float.parseFloat(user_data.get(9).toString());

    }
    public String getName(){
        return this.name;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getIBAN(){
        return this.IBAN;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPhone_number(){
        return this.phone_number;
    }
    public float getLira(){
        return this.lira;
    }
    public float getDollar(){
        return this.dollar;
    }
    public float getEuro(){
        return this.euro;
    }
    public float getGold(){
        return this.gold;
    }
    public int getID(){
        return this.ID;
    }
    public void updateLira(float lira){
        String query = "UPDATE user_balance_table SET lira = ? WHERE ID=?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(lira);
        parameters.add(this.ID);
        DatabaseOperationsClass.updateDeleteInsert(query,parameters);
    }
    public void updateDollar(float Dollar){
        String query = "UPDATE user_balance_table SET dollar = ? WHERE ID=?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(Dollar);
        parameters.add(this.ID);
        DatabaseOperationsClass.updateDeleteInsert(query,parameters);
    }
    public void updateEuro(float Euro){
        String query = "UPDATE user_balance_table SET lira = ? WHERE ID=?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(Euro);
        parameters.add(this.ID);
        DatabaseOperationsClass.updateDeleteInsert(query,parameters);
    }
    public void updateGold(float lira){
        String query = "UPDATE user_balance_table SET lira = ? WHERE ID=?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(lira);
        parameters.add(this.ID);
        DatabaseOperationsClass.updateDeleteInsert(query,parameters);
    }
    public ArrayList<Object> getUpdatedBalance(){
        String query = "SELECT lira,dollar,euro,gold FROM user_balance_table WHERE ID=?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(this.ID);
        ArrayList<Object> dataArray = DatabaseOperationsClass.getData(query,parameters);
        return dataArray;
    }
}
