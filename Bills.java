package com.MulStoresBilling;

import java.sql.*;
import java.util.Scanner;

public class Bills {


    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MULTISTORE","root","Sakthi@9235");
    Statement smt=con.createStatement();

    Scanner in=new Scanner(System.in);

    String sql;
    int billNumber=0;

    public Bills() throws SQLException {
    }


    void printBillDetails(int storeId) throws SQLException {

        float GrandTotal = 0;
        int sNumber = 1;
        int cusId = 0,cusdis=0;
        String cusName ="",DateTime="";
        int flag = 0;

        int Discount=0;

        System.out.println("Enter the BillID:");
        billNumber = in.nextInt();

        sql = ("SELECT BILLNO FROM  BILLS WHERE BILLNO=? AND STOREID=?");
        PreparedStatement smt=con.prepareStatement(sql);

        smt.setInt(1,billNumber);
        smt.setInt(2,storeId);
        ResultSet resultSet = smt.executeQuery();

        while (resultSet.next()) {
            flag = 1;
        }

        if (flag == 1) {


            sql = ("SELECT CUSID FROM  BILLS WHERE BILLNO=? AND STOREID=?");
            PreparedStatement smt1=con.prepareStatement(sql);

            smt1.setInt(1,billNumber);
            smt1.setInt(2,storeId);
            resultSet = smt1.executeQuery();

            while (resultSet.next()) {
                cusId = resultSet.getInt("CUSID");
            }

            sql = ("SELECT CUSNAME FROM  CUSTOMERDETAILS WHERE CUSID=? AND STOREID=?");
            PreparedStatement s1=con.prepareStatement(sql);

            s1.setInt(1,cusId);
            s1.setInt(2,storeId);
            resultSet = s1.executeQuery();

            while (resultSet.next()) {
                cusName = resultSet.getString("CUSNAME");
            }

            sql=("SELECT GRANDTOTAL,BILLTIME,BILLNO,CUSTOMDIS FROM  BILLS WHERE BILLNO=? AND STOREID=?");
            PreparedStatement s2=con.prepareStatement(sql);
            s2.setInt(1,billNumber);
            s2.setInt(2,storeId);
            resultSet=s2.executeQuery();

            while (resultSet.next()){
                GrandTotal=resultSet.getFloat("GRANDTOTAL");
                DateTime=resultSet.getString("BILLTIME");
                cusdis=resultSet.getInt("CUSTOMDIS");
            }

            sql =("SELECT *FROM  ORDERS WHERE BILLNO=?");

            PreparedStatement s3=con.prepareStatement(sql);
            s3.setInt(1,billNumber);
            resultSet = s3.executeQuery();


            if(storeId==1)
                System.out.println("------------------------------ELECTROICS SUPER MARKETS--------------------------------------------");
            else  if(storeId == 2)
                System.out.println("---------------------------------STATIONARY SUPER MARKETS-----------------------------------------");

            System.out.println("                               KARAIKUDI-600300,NORTH STREET         \n");
            System.out.println("CUSTOMER_NAME:" + cusName +"          "+"BILL_NO:"+billNumber+"               "+"DATE_TIME:"+DateTime+"\n"
                    + "CUSTOMER_ID:" + cusId+"                  "+"STORE_ID:"+storeId+"\n");
            System.out.println("=====================================================================================================");
            System.out.println("S.NO:        PRODUCS_NAME:       PRODUCTS_QUNTITY:     PRODUCTS_COST:          PRODUCTS_DISCOUNT(%):");



            while (resultSet.next()) {
                System.out.print(sNumber + "             ");
                sNumber++;
                System.out.printf("%-21s%-21dRS-%-21d%d",
                         resultSet.getString("PRONAME") ,
                         resultSet.getInt("PROQUANTITY"),
                         resultSet.getInt("COST"),
                         resultSet.getInt("DISCOUNTS"));
                         System.out.print("%");
                         System.out.println();

            }
            System.out.println("....................................................................................................");



            sql=("SELECT SUM(COST) FROM ORDERS WHERE BILLNO=?");
            PreparedStatement s4=con.prepareStatement(sql);
            s4.setInt(1,billNumber);
            ResultSet rs3=s4.executeQuery();

            while (rs3.next()){
                System.out.println("SubTotal:                                              RS.  "+rs3.getInt("SUM(COST)"));
            }

            System.out.println(".....................................................................................................");
            System.out.println("Other Discounts:"+cusdis+"%");
            System.out.println("GrandTotal:                                             RS-  "+GrandTotal);

            System.out.println("                                          WELCOME:)        ");
            System.out.println("=====================================================================================================\n");
        }

        else System.out.println("Invalid BIL_ID!!");


    }


    void billLists(int storeId) throws SQLException {

        sql=("SELECT BILLNO,CUSID,BILLTIME FROM BILLS WHERE STOREID=?");
        PreparedStatement smt=con.prepareStatement(sql);

        smt.setInt(1,storeId);
        ResultSet resultSet=smt.executeQuery();

        System.out.println("BILL_NO:        CUS_ID               DATE_TIME:");

        while(resultSet.next()){
            System.out.printf("%-17d%-17d%-17s",
                    resultSet.getInt("BILLNO"),
                    resultSet.getInt("CUSID"),
                    resultSet.getString("BILLTIME"));
            System.out.println();
        }


    }


    void maxProductsSales() throws SQLException {

          sql="SELECT SUM(PROQUANTITY) AS TOTAL ,PRONAME FROM ORDERS GROUP BY  PRONAME ORDER BY TOTAL DESC";
          PreparedStatement smt=con.prepareStatement(sql);
          ResultSet resultSet=smt.executeQuery();

        System.out.println("MAXSALES_PRODUCTS:               PRO_NAME");
        while (resultSet.next()){
            System.out.println(resultSet.getInt("TOTAL")+"                         "+resultSet.getString("PRONAME"));
        }

    }










}
