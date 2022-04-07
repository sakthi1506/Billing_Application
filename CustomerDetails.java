package com.MulStoresBilling;

import java.sql.*;
import java.util.Scanner;

public class CustomerDetails implements ModifyRecords  {

    Scanner in=new Scanner(System.in);
    Scanner in1=new Scanner(System.in);
    Scanner strin=new Scanner(System.in);

    String  sql;
    int flag=0;

    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MULTISTORE","root","Sakthi@9235");
    Statement smt=con.createStatement();


    public CustomerDetails() throws SQLException {
    }


    public  void addDetails(int storeId) throws SQLException {

        System.out.println("Enter your name:");
        String cusName=in1.nextLine();

        System.out.println("Enter your Address:");
        String cusAddress=strin.nextLine();

        System.out.println("Enter your phone number:");
        String ph=strin.next();

        sql=("INSERT INTO CUSTOMERDETAILS(STOREID,CUSNAME,CUSADDRESS,CUSPHONE)VALUES(?,?,?,?)");
        PreparedStatement smt5=con.prepareStatement(sql);
        smt5.setInt(1,storeId);
        smt5.setString(2,cusName);
        smt5.setString(3,cusAddress);
        smt5.setString(4,ph);

        smt5.executeUpdate();


        sql=String.format("SELECT *FROM CUSTOMERDETAILS where cusphone=?");
        PreparedStatement smt6=con.prepareStatement(sql);
        smt6.setString(1,ph);

        ResultSet re=smt6.executeQuery();

        while (re.next()){
            System.out.print("Your Customer Id is:");
            System.out.println(re.getInt("cusid") );
        }

        System.out.println("Customer Details Adding sucessfull!!");

    }

    public void viewAllDetails(int storeId) throws SQLException
    {
        sql=("SELECT *FROM CUSTOMERDETAILS WHERE STOREID=?");
        PreparedStatement smt6=con.prepareStatement(sql);
        smt6.setInt(1,storeId);

        ResultSet re=smt6.executeQuery();

        System.out.println("STORE_ID       CUS_ID        CUS_NAME           CUS_ADDRESS       CUS_PHONENO");

        while (re.next())
        {
            System.out.printf("%-17d%-17d%-17s%-17s%-17s",storeId,re.getInt("cusid"),re.getString("cusName"),
                    re.getString("cusAddress"),re.getString("cusphone"));
            System.out.println();
        }


    }


    public  void  updateDetails(int storeId) throws SQLException
    {

        int choice;
        System.out.println("Enter your Customer ID:");
        int cid=in1.nextInt();

        sql=("SELECT cusid FROM  CUSTOMERDETAILS  WHERE cusid=? AND STOREID=?");
        PreparedStatement smt7=con.prepareStatement(sql);

        smt7.setInt(1,cid);
        smt7.setInt(2,storeId);
        ResultSet re=smt7.executeQuery();

         flag=0;

        while (re.next()){
            flag=1;
        }
        if(flag == 1) {
            System.out.println("-------------------------");
            System.out.println("1.NAME UPDATE\n2.ADDRESS UPDATE\n3.PHONE NUMBER UPDATE");
            System.out.println("-------------------------");
            System.out.println("Select your choice:");
            choice = in1.nextInt();

            if (choice == 1) {

                System.out.println("Enter your New Name:");
                String name = strin.nextLine();

                sql =("UPDATE CUSTOMERDETAILS SET cusName=? WHERE cusid=? AND STOREID=?");
                PreparedStatement smt8=con.prepareStatement(sql);
                smt8.setString(1,name);
                smt8.setInt(2,cid);
                smt8.setInt(3,storeId);
                smt8.executeUpdate();
                System.out.println("Sucessfully Name Updated!!");


            }

            else if (choice == 2)
            {


                System.out.println("Enter your New Address:");
                String address = strin.nextLine();

                sql =("UPDATE CUSTOMERDETAILS SET cusAddress=? WHERE cusid=? AND STOREID=?");
                PreparedStatement smt9=con.prepareStatement(sql);
                smt9.setString(1,address);
                smt9.setInt(2,cid);
                smt9.setInt(3,storeId);
                smt9.executeUpdate();

                System.out.println("Sucessfully Address Updated!!");


            }

            else if (choice == 3)
            {

                System.out.println("Enter your New Phone number:");
                String phone =strin.nextLine();

                sql = ("UPDATE CUSTOMERDETAILS SET cusphone=? WHERE cusid=? AND STOREID=?");
                PreparedStatement smt=con.prepareStatement(sql);
                smt.setString(1,phone);
                smt.setInt(2,cid);
                smt.setInt(3,storeId);
                smt.executeUpdate();

                System.out.println("Sucessfully PhoneNumber Updated!!");
            }


        }

        else System.out.println("Sorry! Please check your Id!!");


    }


    public  void deleteDetails(int storeId) throws SQLException
    {

        System.out.println("Enter the customer ID:");
        int cid=in1.nextInt();

        String sql=("DELETE FROM CUSTOMERDETAILS WHERE cusid=? AND STOREID=?");
        PreparedStatement smt=con.prepareStatement(sql);
         smt.setInt(1,cid);
         smt.setInt(2,storeId);
        int flag=smt.executeUpdate();

        if(flag!=0)
            System.out.println("Customer Details Deleted Sucessfully!!");

            else System.out.println("Ivalid Customer Id!!");

    }

    public void readDetails(int storeId) throws SQLException
    {

        flag=0;
        int ch=0;
        ResultSet re = null;
        String cName="";
        String p1num="";

        System.out.println("..........................");
        System.out.println("1.ID SELECT\n2.NAME SELECT\n3.PHONO SELECT");
        System.out.println("..........................");
        System.out.println("Select Your Choice:");
        ch=in.nextInt();

        if(ch ==1 ){
            System.out.println("Enter the Customer ID:");
            int cid=in1.nextInt();

            sql=("SELECT * FROM CUSTOMERDETAILS WHERE  cusid=? AND STOREID=?");
            PreparedStatement smt=con.prepareStatement(sql);
            smt.setInt(1,cid);
            smt.setInt(2,storeId);
            re=smt.executeQuery();
        }

        else if(ch == 2){
            System.out.println("Enter the Customer NAME:");
            cName=strin.nextLine();

            sql=("SELECT * FROM CUSTOMERDETAILS WHERE  cusname=? AND STOREID=?");
            PreparedStatement smt=con.prepareStatement(sql);
            smt.setString(1,cName);
            smt.setInt(2,storeId);
            re=smt.executeQuery();
        }

        else if(ch == 3){
            System.out.println("Enter the Customer PHONE NUMBER:");
            String pnum1=strin.next();

            sql=("SELECT * FROM CUSTOMERDETAILS WHERE  cusphone=? AND STOREID=?");
            PreparedStatement smt=con.prepareStatement(sql);
            smt.setString(1,pnum1);
            smt.setInt(2,storeId);

            re=smt.executeQuery();
        }

        System.out.println("STORE_ID     CUS_ID        CUS_NAME           CUS_ADDRESS           CUS_PHONENO");

        while (re.next()){
            flag=1;
            System.out.println(storeId+"          "+
                    re.getInt("cusid")+"             "+
                    re.getString("cusName")+"                  "+
                    re.getString("cusAddress")+"               "+
                    re.getString("cusphone"));
        }

        if(flag==0) System.out.println("Not found in Records!!");

    }





}
