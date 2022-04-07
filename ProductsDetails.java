package com.MulStoresBilling;

import java.sql.*;
import java.util.Scanner;

public class ProductsDetails implements ModifyRecords {



    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MULTISTORE","root","Sakthi@9235");
    Statement smt=con.createStatement();

    Scanner in=new Scanner(System.in);
    Scanner strin=new Scanner(System.in);

    String  sql;
    int flag=0;
    int proId,proStacks,proPrice;
    String proName;

    public ProductsDetails() throws SQLException {
    }



    public  void addDetails(int storeId) throws SQLException {

        int flag=0;
        System.out.println("Enter the Products ID:");
        proId=in.nextInt();

        System.out.println("Enter the products Name:");
        proName=strin.nextLine();

        System.out.println("Enter the product Stacks:");
        proStacks=in.nextInt();
        System.out.println("Enter the Each product Price:");
        proPrice=in.nextInt();
        System.out.println("Enter the Each product Discount(%):");
        int proDis=in.nextInt();

        sql=("INSERT INTO PRODUCTS(STOREID,PROID,PRONAME,PROSTACKS,PROPRICE,PRODISCOUNT) VALUES(?,?,?,?,?,?)");

        PreparedStatement smt=con.prepareStatement(sql);
        smt.setInt(1,storeId);
        smt.setInt(2,proId);
        smt.setString(3,proName);
        smt.setInt(4,proStacks);
        smt.setInt(5,proPrice);
        smt.setInt(6,proDis);
        smt.executeUpdate();

        System.out.println("Products Added Sucessfully!!");


    }



    public void viewAllDetails(int storeId) throws SQLException
    {

        sql=("SELECT * FROM  PRODUCTS WHERE STOREID=?");
        PreparedStatement smt=con.prepareStatement(sql);
        smt.setInt(1,storeId);

        ResultSet re=smt.executeQuery();

        while (re.next())
        {
            System.out.printf("%-17d%-17d%-17s%-17d%-17d%-17d",storeId,re.getInt("PROID"),
                    re.getString("PRONAME"),
                    re.getInt("PROSTACKS"),
                    re.getInt("PROPRICE"),
                    re.getInt("PRODISCOUNT"));
                    System.out.println();

        }

    }




    public void deleteDetails(int storeId) throws SQLException
    {

        System.out.println("Enter the product ID:");
        proId=in.nextInt();

        sql=("SELECT  PROID FROM PRODUCTS  WHERE PROID=? AND STOREID=?");
        PreparedStatement smt=con.prepareStatement(sql);
        smt.setInt(1,proId);
        smt.setInt(2,storeId);
        ResultSet re=smt.executeQuery();

        flag=0;
        while (re.next()){
            flag=1;
        }

        if (flag==1){

            sql=("DELETE FROM PRODUCTS WHERE PROID=? AND STOREID=?");
            PreparedStatement smt1=con.prepareStatement(sql);
            smt1.setInt(1,proId);
            smt1.setInt(2,storeId);
            smt1.executeUpdate();

            System.out.println("Product Deleted Sucessfully!!!");
        }

        else System.out.println("Products ID doesn't match!!");


    }




    public void readDetails(int storeId) throws SQLException
    {

        System.out.println("Enter the Products Id:");
        proId=in.nextInt();

        sql=("SELECT PROID FROM  PRODUCTS WHERE PROID=? AND STOREID=?");
        PreparedStatement smt=con.prepareStatement(sql);
        smt.setInt(1,proId);
        smt.setInt(2,storeId);
        ResultSet re=smt.executeQuery();

        flag=0;
        while (re.next()){
            flag=1;
        }

        if (flag==1){
            sql=("SELECT * FROM  PRODUCTS WHERE PROID=? AND STOREID=?");
            PreparedStatement smt2=con.prepareStatement(sql);
            smt2.setInt(1,proId);
            smt2.setInt(2,storeId);
            re=smt2.executeQuery();

            System.out.println("STORE_ID        PRO_ID        PRO_NAME            PRO_STACKS       PRO_PRICE           PRO_DISCOUNT(%)");

            while (re.next()){
                System.out.println(storeId+"                "+
                        re.getInt("PROID")+"           "+
                        re.getString("PRONAME")+"           "+
                        re.getInt("PROSTACKS") +"                   "+
                        re.getInt("PROPRICE")+"                 "+
                        re.getInt("PRODISCOUNT"));
            }
        }

        else System.out.println("No Records Found in Table!");

    }



    public void updateDetails(int storeId) throws SQLException {
        System.out.println("-------------------------");
        System.out.println("Enetr the Products ID:");
        proId=in.nextInt();
        sql=("SELECT * FROM PRODUCTS WHERE PROID=? AND STOREID=?");
        PreparedStatement smt=con.prepareStatement(sql);
        smt.setInt(1,proId);
        smt.setInt(2,storeId);
        ResultSet re=smt.executeQuery();

        flag=0;
        while (re.next()){
            flag=1;
        }

        if(flag==1){


            System.out.println("1.PRONAME UPDATE\n2.PROSTACKS UPDATE\n3.PROPRICE UPDATE");
            System.out.println("-------------------------");
            System.out.println("Select your choice:");
            int choice = in.nextInt();


            if(choice==1){
                System.out.println("Enter the products Name:");
                String proName=strin.nextLine();
                sql=("UPDATE PRODUCTS SET PRONAME=? WHERE PROID=? AND STOREID=?");
                PreparedStatement smt4=con.prepareStatement(sql);
                smt4.setString(1,proName);
                smt4.setInt(2,proId);
                smt4.setInt(3,storeId);
                smt4.executeUpdate();
                System.out.println("Products Name Updated SuccessFully!!");

            }
            else if(choice == 2){
                System.out.println("Enter the product NewStacks:");
                int  proStack=in.nextInt();
                sql=("UPDATE PRODUCTS SET PROSTACKS=? WHERE PROID=? AND STOREID=?");
                PreparedStatement smt5=con.prepareStatement(sql);
                smt5.setInt(1,proStack);
                smt5.setInt(2,proId);
                smt5.setInt(3,storeId);
                smt5.executeUpdate();
                System.out.println("Products Stacks Updated SuccessFully!!");
            }

            else  if(choice == 3){
                System.out.println("Enter the product NewPrice:");
                int  proPrice=in.nextInt();
                sql=("UPDATE PRODUCTS SET PROPRICE=? WHERE PROID=? AND STOREID=?");
                PreparedStatement smt6=con.prepareStatement(sql);
                smt6.setInt(1,proPrice);
                smt6.setInt(2,proId);
                smt6.setInt(3,storeId);
                smt6.executeUpdate();
                System.out.println("Products Price Updated SuccessFully!!");
            }



        }

        else System.out.println("Product Id mismatch!!");
    }




}
