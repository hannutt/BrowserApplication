package com.browser.browserapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.sql.*;

public class Bookmarks {


    public String BookmarkToSave;


    public void ShowBookmarks(Menu bookmarks, WebView webView) throws SQLException {
        DBconnection conn = new DBconnection();
        Connection connDB = conn.getConnection();
        Statement stmt=connDB.createStatement();
        ResultSet rs=stmt.executeQuery("select address FROM Bookmarks");

        //silmukalla haetaan kaikki kirjanmerkit taulusta ja luodaan uusi menuitemi jokaiselle linkille.
        while(rs.next())
        {   MenuItem mi = new MenuItem();
            mi.setText(rs.getString(1));
            bookmarks.getItems().add(mi);
            EventHandler<ActionEvent>getLink = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    String url ="http://"+((MenuItem)e.getSource()).getText();
                    webView.getEngine().load(url);
                    System.out.println(url);


                }
            };
            mi.setOnAction(getLink);
        }

        connDB.close();
    }
    public void executeSave(TextField addField) throws SQLException {
        BookmarkToSave=addField.getText();
        DBconnection conn = new DBconnection();
        Connection connDB = conn.getConnection();
        String DBquery="INSERT INTO Bookmarks (address) VALUES (?)";
        PreparedStatement pst = connDB.prepareStatement(DBquery);
        pst.setString(1,BookmarkToSave);
        pst.executeUpdate();
    }

    }