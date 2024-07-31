package com.browser.browserapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrowserController {

    Zoom zoom = new Zoom();

    BrowserMethods bm = new BrowserMethods();


    @FXML
    public MenuButton mb;
    @FXML
    public WebView webView;

    @FXML
    public TextField addField,colCode;

    @FXML
    public Stage stage;

    @FXML
    public Menu bookmarks;

    @FXML
    public VBox Vb;

    @FXML
    public TextArea txtView;

    @FXML
    public CheckBox switchTxt,blankCB,startPageCB;


    public int listIndex;

    public String startPage;

    WebEngine webEngine = new WebEngine();
    WebHistory history = webEngine.getHistory();

    Bookmarks bom = new Bookmarks();


    Themes themes = new Themes();
    List<String> addreslist=new ArrayList<String>();


    public void initialize() throws IOException, SQLException {
        getSavedStartPage();
        bom.ShowBookmarks(bookmarks,webView);

        //webView.getEngine().load("http://google.com");

        //luetaan tiedoston settings.txt sisältö
        String filename="settings.txt";
        File file = new File(filename);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        //jos tiedosto sisältää allaolevan lauseen, lähetetään se setsavedstyles metodille
        //savedstyle metodi muuttaa ohjelman taustavärin parametrina saamansa värikoodin mukaisesksi.
        while((line = br.readLine()) != null){
            themes.setSavedStyle(Vb,line);

            }
    }

    public void goPage(ActionEvent actionEvent) {
        bm.startBrowsing(addreslist,addField,switchTxt,listIndex,txtView,webView);

    }
    public void getPageContent(ActionEvent actionEvent) throws IOException {
       bm.ScrapeContent(webView,stage);
    }

    public void getPageLinks(ActionEvent actionEvent) {

        String html = (String) webView.getEngine().executeScript(("document.documentElement.outerHTML"));
        System.out.println(html);
    }

    public void PlusZoom(ActionEvent actionEvent) {
        zoom.IncreaseZoom(webView);
    }

    public void MinusZoom(ActionEvent actionEvent) {
        zoom.DecreaseZoom(webView);
    }


    //funtio suoritetaan jos osoitekentän ollessa aktiivisena painetaan enteriä.
    public void goPageEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            //parametrillista funktiota voi kutsua myös ilman parametria antmalla arvoksi null
            goPage(null);

        }

    }

    public void setDarkTheme(ActionEvent actionEvent) throws IOException {
        themes.useDarkTheme(Vb);


    }

    public void setLightTheme(ActionEvent actionEvent) throws IOException {

      themes.useLightTheme(Vb);
    }
    public void goPreviousPage(ActionEvent actionEvent) {
        listIndex=listIndex-1;

        webView.getEngine().load(addreslist.get(listIndex));

    }

    public void goNextPage(ActionEvent actionEvent) {
        listIndex+=1;
        webView.getEngine().load(addreslist.get(listIndex));

    }


    public void openTextMode(ActionEvent actionEvent) {

        VBox textVb = new VBox();
        Stage txtStage = new Stage();
        TextArea ta = new TextArea();
        String pageText = (String) webView.getEngine().executeScript("document.documentElement.innerText");
        ta.appendText(pageText);
        textVb.getChildren().add(ta);
        Scene scene = new Scene(textVb, 400, 400);
        txtStage.setScene(scene);
        txtStage.show();
    }



    public void switchTxt(ActionEvent actionEvent) {
        if (switchTxt.isSelected())
        {
            webView.setOpacity(0.0);
            txtView.setOpacity(1.0);

        } else {
            webView.setOpacity(1.0);
            txtView.setOpacity(0.0);
        }

    }


    public void customTheme(KeyEvent keyEvent) throws IOException {
        themes.setCustomTheme(keyEvent,colCode,Vb);


    }


    public void saveStartPage(ActionEvent actionEvent) throws SQLException {
        if (startPageCB.isSelected())
        {
            startPage=addField.getText();
            String DBquery;
            DBconnection conn = new DBconnection();
            Connection connDB = conn.getConnection();
            Statement stmt = connDB.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT url FROM pages WHERE id=1");
            //tarkistus löytyykö id 1:llä kannasta talletettua tietoa
            if (rs.next()){
                DBquery = "UPDATE pages SET url = (?) WHERE id=1";
                PreparedStatement pst = connDB.prepareStatement(DBquery);
                pst.setString(1,startPage);
                pst.executeUpdate();
            }
            else {
                DBquery = "INSERT INTO pages (url) VALUES (?)";
                PreparedStatement pst = connDB.prepareStatement(DBquery);
                pst.setString(1,startPage);
                pst.executeUpdate();
            }

        }

    }
    public void getSavedStartPage() throws SQLException {

       try {
           DBconnection conn = new DBconnection();
           Connection connDB = conn.getConnection();
           Statement stmt = connDB.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT url FROM pages");
           String url = null;
           while (rs.next()) {
               url = rs.getString("url");



           }
           webView.getEngine().load("http://"+url);


           System.out.println(rs);
           connDB.close();
       } catch (SQLException e) {
           System.out.println(e);

       }
    }


    public void saveBookmark(ActionEvent actionEvent) throws SQLException {

        bom.executeSave(addField);

    }
}




