package com.MulStoresBilling;

import java.sql.*;
import java.util.Scanner;


 interface ModifyRecords
 {
     void  addDetails(int storeId)      throws SQLException;
     void  updateDetails(int storeId)   throws SQLException;
     void  deleteDetails(int storeId)   throws  SQLException;
     void  readDetails(int storeId)     throws SQLException;
     void  viewAllDetails(int storeId)  throws SQLException;
}



public class MultiStoreBilling {


    public static void main(String[] args) throws SQLException {

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MULTISTORE", "root", "Sakthi@9235");
        Statement smt = con.createStatement();



        Scanner in = new Scanner(System.in);
        Scanner in1 = new Scanner(System.in);
        int choice, choice1,ch;
        String sql,sName="";
        int storeId = 0;

        boolean a = true;
        boolean b=true;
        boolean c=true;

        CustomerDetails customerDetails = new CustomerDetails();
        ProductsDetails productsDetails=new ProductsDetails();
        Orders orders=new Orders();
        Bills bills=new Bills();

        while (c) {


            System.out.println("..WELCOME TO SUPERMARKETS..");
            System.out.println("............................");
            sql=String.format("SELECT * FROM STORES");

            PreparedStatement smt1=con.prepareStatement(sql);
            ResultSet r=smt1.executeQuery();

            while (r.next()){
                System.out.println(r.getInt("STOREID")+"."+r.getString("STORENAME"));
            }
            System.out.println("-1.ADD STORES\n0.EXIT");
            System.out.println("............................");
            System.out.println("Select Your Choice:");
            choice = in.nextInt();

            if (choice <=10 && choice !=0 && choice!=-1) {


                sql = ("SELECT * FROM STORES WHERE STOREID=?");
                PreparedStatement smt2=con.prepareStatement(sql);
                smt2.setInt(1,choice);

                ResultSet re=smt2.executeQuery();

                while (re.next()) {
                    storeId= re.getInt("STOREID");
                    sName = re.getString("STORENAME");

                }

                a=true;
                while (a) {

                    System.out.println(" ..WELCOMETO " + sName + "..");
                    System.out.println("1.CUSTOMER PROPERTIES\n2.PRODUCTS PROPERTIES\n3.ORDERS\n4.BILLDETAILS\n5.REPORTS\n6.EXIT");
                    System.out.println("--------------------------");

                    System.out.println("Select your choice:");
                    choice1 = in.nextInt();

                    if (choice1 == 1) {
                        b = true;
                        while (b) {


                            System.out.println("---CUSTOMER DETAILS OPTIONS---");
                            System.out.println("1.ADD CUSTOMERS\n2.UPDATE CUSTOMERS\n3.DELETE CUSTOMERS\n4.READ CUSTOMER DETAILS\n5.VIEW ALL CUSTOMERSLIST\n6.EXIT");
                            System.out.println("------------------------------");
                            System.out.println("Select Your choice:");
                            ch = in.nextInt();

                            if (ch == 1) {
                                customerDetails.addDetails(storeId);
                            } else if (ch == 2) {
                                customerDetails.updateDetails(storeId);
                            } else if (ch == 3) {
                                customerDetails.deleteDetails(storeId);
                            } else if (ch == 4) {
                                customerDetails.readDetails(storeId);
                            } else if (ch == 5) {
                                customerDetails.viewAllDetails(storeId);
                            } else if (ch == 6) b = false;

                        }

                    } else if (choice1 == 2) {

                        b = true;
                        while (b)
                        {


                            System.out.println("---PRODUCTS DETAILS OPTIONS---");
                            System.out.println("1.ADD PRODUCTS\n2.UPDATE PRODUCTS\n3.DELETE PRODUCTS\n4.READ PRODUCTS DETAILS\n5.VIEW ALL PRODUCTSlIST\n6.EXIT");
                            System.out.println("-------------------------------");
                            System.out.println("Select Your choice:");
                            ch = in.nextInt();

                            if (ch == 1) {
                                productsDetails.addDetails(storeId);
                            } else if (ch == 2) {
                                productsDetails.updateDetails(storeId);
                            } else if (ch == 3) {
                                productsDetails.deleteDetails(storeId);
                            } else if (ch == 4) {
                                productsDetails.readDetails(storeId);
                            } else if (ch == 5) {
                                System.out.println("STORE_ID      PRO_ID           PRO_NAME            PRO_STACKS       PRO_PRICE        PRO_DISCOUNT(%)");
                                productsDetails.viewAllDetails(storeId);
                            } else if (ch == 6) b = false;
                        }

                    } else if (choice1 == 3) {
                        System.out.println("Welcome add bills!!");
                        orders.addBillDetails(storeId);
                    } else if (choice1 == 4) {
                        b = true;
                        while (b)
                        {
                            System.out.println("..........................");
                            System.out.println("1.PRINT BILLS\n2.VIEW ALL BILL LISTS\n3.EXIT");
                            System.out.println("..........................");

                            System.out.println("Select Your Choice:");
                            int ch2 = in.nextInt();

                            if (ch2 == 1) bills.printBillDetails(storeId);
                            else if (ch2 == 2) bills.billLists(storeId);
                            else if (ch2 == 3) b = false;
                        }
                    } else if (choice1 == 5) {
                        System.out.println("Reports Are:");
                        bills.maxProductsSales();

                    } else if (choice1 == 6) a = false;

                }

            }
           else if(choice  == -1){
                System.out.println("Enter Store Name:");
                String storeName=in1.nextLine();

                sql=("INSERT INTO STORES(STORENAME) VALUES(?)");
                PreparedStatement smt3=con.prepareStatement(sql);
                smt3.setString(1,storeName);

                smt3.executeUpdate();

                sql=("SELECT STOREID FROM STORES WHERE STORENAME=?");
                PreparedStatement smt4=con.prepareStatement(sql);
                smt4.setString(1,storeName);

                ResultSet re=smt4.executeQuery();
                while (re.next()){
                    System.out.println("StoreID is:"+ re.getInt("STOREID"));
                }
            }
           else if(choice  == 0) c=false;
        }



    }
}
