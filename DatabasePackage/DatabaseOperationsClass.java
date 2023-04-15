package DatabasePackage;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseOperationsClass {

    //initializing values required for connecting to mysql.
    final static private String databaseURL = "jdbc:mysql://localhost:3306/bankingappdatabase";
    final static private String databaseUsername = "root";
    final static private String databasePassword = "password";

    DatabaseOperationsClass(){
        //a constructor to register the driver. normally driver is automatically registered but just in case  it is manually registered.
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver successfully loaded!");
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException("Cannot find the driver in the classpath!\n", exception);
        }
}
/*
sql columns are indexed starting with 1 so statement.setObject()
method takes indexes starting from 1 but arraylist's indexing is starting from 0, so that is why i is initialized as 1 not 0.
*/

    /***
     *
     * @param query string type query that will be executed on sql
     * @param parameters ArrayList<Object> type parameters for the query
     *                   DOES NOT RETURN ANYTHING
     */
    public static void updateDeleteInsert(String query, ArrayList<Object> parameters) {

    try {
        Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
        PreparedStatement statement = connection.prepareStatement(query);
        int parameterLength = parameters.size();
        for(int i=1;i<parameterLength+1;i++){
            statement.setObject(i,parameters.get(i-1));
        }
        statement.executeUpdate();
        connection.close();
    }
    catch(java.sql.SQLException exception){
        System.out.println("something went wrong on SQL!\n"+exception);
    }
    catch(Exception exception){
        System.out.println("something went wrong and idk what it is :(\n"+exception);
    }
}


    /***
     *
     * @param query string type query that will be executed on sql
     * @param parameters ArrayList<Object> type parameters for the query
     * @return returns results from execution as an ArrayList<Object>
     */
    public static ArrayList<Object> getData(String query,ArrayList<Object> parameters){

    try {
        Connection connection = DriverManager.getConnection(databaseURL,databaseUsername,databasePassword);
        ArrayList<Object> returnResultsList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(query);
        int parameterLength = parameters.size();
        for(int i=1;i<parameterLength+1;i++){
            statement.setObject(i,parameters.get(i-1));
        }
        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();

        while(resultSet.next()){
            for(int i=1;i<columnsNumber+1;i++){
                returnResultsList.add(resultSet.getObject(i));
            }
        }
        return returnResultsList;
    }
    catch(java.sql.SQLException exception){
        System.out.println("something went wrong on SQL!\n"+exception);
        return null;
    }
    catch(Exception exception){
        System.out.println("something went wrong and idk what it is :(\n"+exception);
        return null;
    }
}
}