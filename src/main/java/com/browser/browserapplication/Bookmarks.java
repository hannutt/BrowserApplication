package com.browser.browserapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;

import java.sql.*;
import java.util.Optional;


public class Bookmarks {


    public Menu  bookmarks;

    @FXML
    public WebView webView;


    public String BookmarkToSave;


    public void ChoiseBookmarkPlace(TextField addField) throws SQLException {

        String[] options={"Bookmark menu","Bookmark bar"};
        ChoiceDialog<String> cd = new ChoiceDialog<>(options[0],options[1]);
        Optional<String> result = cd.showAndWait();
        //result.isPresent() palauttaa false, jos käyttäjä ei valinnut mitään tai peruuttaa valintaikkunan.
        if (result.isPresent() && result.get().equals("Bookmark menu")){
            executeSave(addField);


        }
        else if (result.isPresent() && result.get().equals("Bookmark bar")) {
            saveToBookmarkBar(addField);
        }

        else {
            cd.close();
        }
    }

    public void showBookmarkBarLinks(ButtonBar bookmarkbar) throws SQLException {
        DBconnection conn = new DBconnection();
        Connection connDB = conn.getConnection();
        Statement stmt=connDB.createStatement();
        ResultSet rs=stmt.executeQuery("select address FROM Bookmarkbar");
        while (rs.next())
        {
            Button barBtn = new Button();
            barBtn.setText(rs.getString(1));
            bookmarkbar.getButtons().addAll(barBtn);
        }


    }
    public void ShowBookmarks(Menu bookmarks, WebView webView) throws SQLException {
        DBconnection conn = new DBconnection();
        Connection connDB = conn.getConnection();
        Statement stmt=connDB.createStatement();
        ResultSet rs=stmt.executeQuery("select address FROM Bookmarks");

        //silmukalla haetaan kaikki kirjanmerkit taulusta ja luodaan uusi menuitemi jokaiselle linkille.
        while(rs.next())
        {

            MenuItem mi = new MenuItem();
            Menu subMenu = new Menu();
            MenuItem subMi = new MenuItem();

            mi.setText(rs.getString(1));
            //alivalikon menuitemit saa tekstin delete
            //luodaan alivalikko


            subMi.setText("Delete "+rs.getString(1));
            //lisätääb menuitemit alivalikkoon.
            subMenu.getItems().addAll(subMi);
            bookmarks.getItems().addAll(mi,subMenu);

            EventHandler<ActionEvent>getLink = new EventHandler<ActionEvent>() {
                @Override
                //lähetetään klikattu linkki eli menuitemin teksti webview:lle.
                public void handle(ActionEvent e) {


                    String url ="http://"+((MenuItem)e.getSource()).getText();
                    webView.getEngine().load(url);
                    System.out.println(url);


                }

            };
            EventHandler<ActionEvent>deleteLink = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    String url =((MenuItem)event.getSource()).getText();
                    String finaUrl = url.replace("Delete ","");
                    System.out.println(finaUrl);
                    Alert a = new Alert(Alert.AlertType.NONE);

                    a.setContentText("Confirm the removal of the "+finaUrl+ " bookmark");
                    a.setAlertType(Alert.AlertType.CONFIRMATION);
                    Optional<ButtonType>result=a.showAndWait();
                    if (result.get() == ButtonType.OK){
                        try {
                            DeleteBookmark(finaUrl);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        a.close();
                    }

                }
            };

           mi.setOnAction(getLink);
           subMi.setOnAction(deleteLink);


        }

        connDB.close();
    }

    public void DeleteBookmark(String finaUrl) throws SQLException {
        DBconnection conn = new DBconnection();
        Connection connDB = conn.getConnection();
        String DBquery=" DELETE FROM Bookmarks WHERE (address) = (?)";
        PreparedStatement pst = connDB.prepareStatement(DBquery);
        pst.setString(1,finaUrl);
        pst.execute();

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

    public void saveToBookmarkBar(TextField addField) throws SQLException {
        BookmarkToSave=addField.getText();
        DBconnection conn = new DBconnection();
        Connection connDB = conn.getConnection();
        String DBquery="INSERT INTO Bookmarkbar (address) VALUES (?)";
        PreparedStatement pst = connDB.prepareStatement(DBquery);
        pst.setString(1,BookmarkToSave);
        pst.executeUpdate();

    }

    }