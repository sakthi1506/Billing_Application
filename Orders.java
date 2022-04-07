package com.MulStoresBilling;

import java.sql.*;
import java.util.*;

public class Orders {


    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MULTISTORE","root","Sakthi@9235");
    Statement smt=con.createStatement();

    Scanner in=new Scanner(System.in);
    Scanner strin=new Scanner(System.in);

    String sql;
    int pId,pQuantity;

    public Orders() throws SQLException {

    }


    void addBillDetails(int stroeId) throws SQLException
    {
        int billid=0;
        int proStacks=0;
        int sid=0;

        boolean a=true;
        float GrandTotal=0;
        int Discount=10;
        int flag=0;

        HashMap<Integer,Integer>map=new HashMap<>();
        map.clear();


        System.out.println("Enter your customers ID:");
        int cid=in.nextInt();

        sql=("SELECT CUSID FROM CUSTOMERDETAILS WHERE STOREID=? AND CUSID=?");
        PreparedStatement smt=con.prepareStatement(sql);
        smt.setInt(1,stroeId);
        smt.setInt(2,cid);
        ResultSet re1=smt.executeQuery();

        while (re1.next()){
            flag=1;
        }

        if(flag  == 0) {
            System.out.println("Wrong user ID!!!");
        }

         else{


        sql=("INSERT INTO BILLS(CUSID,STOREID) VALUES(?,?)");
        PreparedStatement smt1=con.prepareStatement(sql);
        smt1.setInt(1,cid);
        smt1.setInt(2,stroeId);
        smt1.executeUpdate();

        sql=("SELECT BILLNO FROM BILLS WHERE STOREID=? ORDER BY BILLNO DESC LIMIT 1");
        PreparedStatement smt2=con.prepareStatement(sql);
        smt2.setInt(1,stroeId);
        ResultSet re=smt2.executeQuery();

        while (re.next()){
            billid=re.getInt("BILLNO");
        }


        int f1=0;

        while (a) {
            int cost = 0;
            String pname = "";
            int f = 0,quan=0;
            int discount=0;

            System.out.println("Enter the Products ID:");
            pId = in.nextInt();

            sql =("SELECT PROID FROM PRODUCTS WHERE PROID=? AND STOREID=?");
            PreparedStatement smt3=con.prepareStatement(sql);
            smt3.setInt(1,pId);
            smt3.setInt(2,stroeId);

            ResultSet re2 = smt3.executeQuery();

            while (re2.next()) {
                f = 1;
            }

            if (f == 1 || pId == 0) {


                if (pId == 0 && f1 == 1 ) {


                     Set s=map.entrySet();
                     Iterator i=s.iterator();

                    System.out.println("PrductId:          Quantity:");

                     while (i.hasNext())
                     {
                         Object o=i.next();
                         Map.Entry e=(Map.Entry)o;
                         System.out.println(
                                 e.getKey()+"                "+
                                 e.getValue());
                     }

                     boolean b=true;
                     while (b){


                     System.out.println("if any products removed pls enter:[Y/N]");
                     String c=strin.next();

                      if(c.equalsIgnoreCase("y")) {

                          System.out.println("Enter the above product Id:");
                          int did=in.nextInt();
                          map.remove(did);

                          System.out.println("Successfully removed products!!");
                      }
                      else b=false;
                     }

                       i=s.iterator();
                       while (i.hasNext()){

                        Object o=i.next();
                        Map.Entry e=(Map.Entry)o;

                        pId=(int)e.getKey();
                        pQuantity=(int)e.getValue();

                        sql =("SELECT STOREID,PRONAME,PROPRICE,PROSTACKS,PRODISCOUNT FROM PRODUCTS WHERE PROID=? AND STOREID=?");
                        PreparedStatement smt6=con.prepareStatement(sql);
                        smt6.setInt(1,pId);
                        smt6.setInt(2,stroeId);
                        ResultSet resultSet = smt6.executeQuery();

                        while (resultSet.next()) {
                            pname = resultSet.getString("PRONAME");
                            cost = resultSet.getInt("PROPRICE");
                            proStacks = resultSet.getInt("PROSTACKS");
                            sid=resultSet.getInt("STOREID");
                            discount=resultSet.getInt("PRODISCOUNT");
;                        }



                          float dis=(cost*pQuantity)-(cost*pQuantity*discount/100);  // after discount
                          GrandTotal=GrandTotal+dis;

                           String input = ("INSERT INTO ORDERS (BILLNO,PROID,PRONAME,PROPRICE,PROQUANTITY,COST,DISCOUNTS,DISCOUNTPRICE) VALUES(?,?,?,?,?,?,?,?)");
                           PreparedStatement smt7=con.prepareStatement(input);
                           smt7.setInt(1,billid);
                           smt7.setInt(2,pId);
                           smt7.setString(3,pname);
                           smt7.setInt(4,cost);
                           smt7.setInt(5,pQuantity);
                           smt7.setInt(6,cost * pQuantity);
                           smt7.setInt(7,discount);
                           smt7.setFloat(8,dis);
                           smt7.executeUpdate();

                          sql = String.format("UPDATE PRODUCTS  SET PROSTACKS=?-? WHERE PROID=? AND STOREID=?");
                          PreparedStatement smt8=con.prepareStatement(sql);
                          smt8.setInt(1,proStacks);
                          smt8.setInt(2,pQuantity);
                          smt8.setInt(3,pId);
                          smt8.setInt(4,stroeId);
                          smt8.executeUpdate();

                        //System.out.println(pId+"   "+pQuantity);    after removed products


                    }



             System.out.println("If give any other discounts:[Y/N]");
             String c=in.next();
             int dis=0;

            if(c.equalsIgnoreCase("y"))
            {
                System.out.println("Enter the Discount[%]:");
                 dis=in.nextInt();
                GrandTotal=GrandTotal-(GrandTotal*dis/100);
            }


                    sql =("UPDATE  BILLS SET  CUSTOMDIS=(?),GRANDTOTAL=(?) WHERE BILLNO=? AND STOREID=?");
                     PreparedStatement smt9=con.prepareStatement(sql);
                     smt9.setInt(1,dis);
                     smt9.setFloat(2,GrandTotal);
                     smt9.setInt(3,billid);
                     smt9.setInt(4,stroeId);
                     smt9.executeUpdate();

                    System.out.println("BILL ID:" + billid);
                    System.out.println("Add bill done!!");

                    break;
                }

                else if(f == 0 && f1 == 0){

                    System.out.println("Product Not Declered!!");
                    break;
                }

                System.out.println("Enter the products Quantity:");
                pQuantity = in.nextInt();


                sql = ("SELECT STOREID,PRONAME,PROPRICE,PROSTACKS FROM PRODUCTS WHERE PROID=? AND STOREID=?");
                PreparedStatement s=con.prepareStatement(sql);
                s.setInt(1,pId);
                s.setInt(2,stroeId);
                ResultSet resultSet = s.executeQuery();

                while (resultSet.next()) {
                    pname = resultSet.getString("PRONAME");
                    cost = resultSet.getInt("PROPRICE");
                    proStacks = resultSet.getInt("PROSTACKS");
                    sid=resultSet.getInt("STOREID");
                }


                if (proStacks >= pQuantity ) {

                    map.put(pId,pQuantity);   // HASH MAP ADDING

                    f1=1;

                    sql = ("SELECT PROSTACKS,PRONAME FROM PRODUCTS WHERE PROID=? AND STOREID=?");
                     PreparedStatement s1=con.prepareStatement(sql);
                     s1.setInt(1,pId);
                     s1.setInt(2,stroeId);
                     resultSet = s1.executeQuery();

                    while (resultSet.next()) {
                        proStacks = resultSet.getInt("PROSTACKS");
                        pname = resultSet.getString("PRONAME");
                    }

                    if (proStacks == 0) {
                        sql = ("INSERT INTO STACKSOVERFLOW (STOREID,PROID,PRONAME) VALUES(?,?,?)");
                        PreparedStatement s3=con.prepareStatement(sql);
                        s3.setInt(1,stroeId);
                        s3.setInt(2,pId);
                        s3.setString(3,pname);
                        s3.executeUpdate(sql);

                        sql = ("DELETE  FROM PRODUCTS WHERE  PROID=? AND STOREID=?");
                        PreparedStatement s4=con.prepareStatement(sql);
                        s4.setInt(1,pId);
                        s4.setInt(2,stroeId);
                        s4.executeUpdate();
                        f1=1;
                    }


                } else if (proStacks < pQuantity) {

                        sql = ("SELECT PROSTACKS FROM PRODUCTS WHERE PROID=? AND STOREID=?");
                        PreparedStatement s5=con.prepareStatement(sql);
                        s5.setInt(1,pId);
                        s5.setInt(2,stroeId);
                        resultSet = s5.executeQuery();

                    while (resultSet.next()) {
                        proStacks = resultSet.getInt("PROSTACKS");
                    }


                    System.out.println("Sorry!! " + proStacks + "  stacks available!!");

                    if (proStacks == 0) {
                        System.out.println("Stacks Empty!!");
                    }
                }


            }


            else System.out.println("Product Id MisMatch!!");
        }










    }


    }

}
