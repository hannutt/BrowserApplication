package com.browser.browserapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
        public Connection DBLink;

        public Connection getConnection() {
            String DBname="BrowserDB";
            String DBuser="root";
            String DBpsw="Root512!";
            String url="jdbc:mysql://localhost/"+DBname;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                DBLink= DriverManager.getConnection(url,DBuser,DBpsw);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return DBLink;
        }



}
