package com.browser.browserapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrowserController {

    Zoom zoom = new Zoom();


    public int clicks;
    BrowserMethods bm = new BrowserMethods();



    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Slider slider;
    @FXML
    public WebView webView;

    @FXML
    public TextField addField, colCode,searchField;

    @FXML
    public Stage stage;

    @FXML
    public Menu bookmarks;

    @FXML
    public VBox Vb;

    @FXML
    public  ProgressBar loadingBar;

    @FXML
    public TextArea txtView;

    @FXML
    public CheckBox switchTxt, startPageCB,disablejs;

    @FXML
    public Button debugBtn,findBtn,undoBtn;

    @FXML
    public ImageView sourceImg;

    @FXML
    public Label helperLbl;
    @FXML
    public Circle connShape;

    @FXML
    public ButtonBar bookmarkbar;

    public String startPage;


    WebEngine webEngine = new WebEngine();
    WebHistory history = webEngine.getHistory();
    Helpers helpers = new Helpers();

    TextMethods tm = new TextMethods();

    RssFeeds rf = new RssFeeds();




    Bookmarks bom = new Bookmarks();


    Themes themes = new Themes();
    List<String> addreslist = new ArrayList<String>();

    public Menu getMenuItem() {
        return bookmarks;
    }
    public void setMenuItem(Menu me){
        this.bookmarks=me;
    }


    public boolean helpersOn=false;
    public void initialize() throws IOException, SQLException, InterruptedException {
        slider.setMin(0);
        slider.setMax(10);
        //slider komponentin koodi, jolla kasvatetaan ja pienennetään sliderin arvoa.
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {

            webView.setFontScale((Double) newValue);

        });

        getSavedStartPage();
        helpers.runConnCheck(connShape);

        bom.ShowBookmarks(bookmarks, webView);
        bom.showBookmarkBarLinks(bookmarkbar,webView);

        StyleConfiguration();


        //textpropertyyn voidaan liittää kuuntelija, eli tässä tapauksessa jos
        //merkkijono loppuu pisteeseen, kutsutaan endoptions metodia
        addField.textProperty().addListener((observable, oldValue, newValue) -> {
            if ( helpersOn && newValue.endsWith("."))
            {
                helpers.endOptions(addField);

            }
        });
    }

    public void StyleConfiguration() throws IOException {
        //luetaan tiedoston settings.txt sisältö
        String filename = "settings.txt";
        File file = new File(filename);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String Bgcolor;
        String txtFIll;

        //jos tiedosto sisältää allaolevan lauseen, lähetetään se setsavedstyles metodille
        //savedstyle metodi muuttaa ohjelman taustavärin parametrina saamansa värikoodin mukaisesksi.
        while ((Bgcolor = br.readLine()) != null) {
            themes.setSavedStyle(anchorPane, Bgcolor);
            //tässä luetaan tiedoston toinen rivi, eli checkboxin tekstiväri ja lähetetään se metodille
            //joka muuttaa cb:n tekstin mustaksi
            txtFIll = br.readLine();
            if (txtFIll != null) {
                themes.setCBtextColor(startPageCB, switchTxt, txtFIll);
                System.out.println(txtFIll);
            }


        }
    }

    public void goPage(ActionEvent actionEvent) {

        bm.startBrowsing(addreslist, addField, switchTxt, txtView, webView,loadingBar);

    }

    public void getPageContent(ActionEvent actionEvent) throws IOException {
        bm.ScrapeContent(webView, stage);
    }

    public void getPageLinks(ActionEvent actionEvent) {

        String html = (String) webView.getEngine().executeScript(("document.documentElement.outerHTML"));
        System.out.println(html);
    }

    public void PlusZoom(ActionEvent actionEvent) {
        zoom.IncreaseZoom(webView,switchTxt,txtView);
    }

    public void MinusZoom(ActionEvent actionEvent) {
        zoom.DecreaseZoom(webView,switchTxt,txtView);
    }


    //funtio suoritetaan jos osoitekentän ollessa aktiivisena painetaan enteriä.
    public void goPageEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            loadingBar.setProgress(0.0);
            //parametrillista funktiota voi kutsua myös ilman parametria antmalla arvoksi null
            goPage(null);
        }

    }

    public void setDarkTheme(ActionEvent actionEvent) throws IOException {
        themes.useDarkTheme(anchorPane);


    }

    public void setLightTheme(ActionEvent actionEvent) throws IOException {

        themes.useLightTheme(anchorPane);
    }

    public void goPreviousPage(ActionEvent actionEvent) {
        bm.executePrevious(addreslist, webView);

    }

    public void goNextPage(ActionEvent actionEvent) {
        bm.executeNext(webView, addreslist);

    }


    public void switchTxt(ActionEvent actionEvent) {
        if (switchTxt.isSelected()) {
            webView.setOpacity(0.0);
            txtView.setOpacity(1.0);
            //undoBtn.setOpacity(1.0);

            //muutetaan teksilaatikon minimi korkeutta,
            txtView.setMinHeight(350);


        } else {
            webView.setOpacity(1.0);
            txtView.setOpacity(0.0);
            searchField.setOpacity(0.0);
            undoBtn.setOpacity(0.0);

        }

    }

    public void customTheme(KeyEvent keyEvent) throws IOException {
        themes.setCustomTheme(keyEvent, colCode, anchorPane);

    }

    public void saveStartPage(ActionEvent actionEvent) throws SQLException {
        if (startPageCB.isSelected()) {
            startPage = addField.getText();
            String DBquery;
            DBconnection conn = new DBconnection();
            Connection connDB = conn.getConnection();
            Statement stmt = connDB.createStatement();

            //tarkistus. löytyykö pages taulusta 1 rivi
            ResultSet rs = stmt.executeQuery("SELECT * FROM pages ORDER BY id DESC LIMIT 1");
            //isBeforeFirst palauttaa false, jos rs on tyhjä true jos löytyy tuloksia.
            System.out.println(rs.isBeforeFirst());
            //jos rs on tyhjä eli taulusta pages ei löydy yhtään riviä

            if (!rs.isBeforeFirst()) {
                DBquery = "INSERT INTO pages (url) VALUES (?)";
                PreparedStatement pst = connDB.prepareStatement(DBquery);
                pst.setString(1, startPage);
                pst.executeUpdate();


            } else {
                DBquery = "UPDATE pages SET url = (?)";
                PreparedStatement pst = connDB.prepareStatement(DBquery);
                pst.setString(1, startPage);
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
            if (url == null) {
                webView.getEngine().load("http://yahoo.com");

            } else {
                webView.getEngine().load("http://" + url);

            }


            System.out.println(rs);
            connDB.close();
        } catch (SQLException e) {
            System.out.println(e);

        }
    }


    public void saveBookmark(ActionEvent actionEvent) throws SQLException {
        bom.ChoiseBookmarkPlace(addField);

    }

    public void resetScale(ActionEvent event) {
        slider.setValue(0.00);
        webView.setFontScale(1.00);

    }

    public void debugger(ActionEvent event) {
        clicks+=1;
        bm.htmlStructure(webEngine,txtView,webView,addField,clicks,debugBtn,sourceImg,searchField,findBtn);

    }
    public void disableJS(ActionEvent event) {
        if(disablejs.isSelected())
        {
            webView.getEngine().setJavaScriptEnabled(false);
        }
        else{
            webView.getEngine().setJavaScriptEnabled(true);

        }
    }

    public void findText(ActionEvent event) {

        String find = searchField.getText();
        String text = txtView.getText();
        System.out.println(text.indexOf(find));


    }

    public void turnHelpersOn(ActionEvent event) {
        helpersOn=true;
        helpers.UrlChecker(addField,helperLbl,helpersOn);
    }

    public void boldText(ActionEvent event) {
        tm.DoBolding(txtView);
    }


    public void undoLatest(ActionEvent event) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        tm.cancelLatestMethod(txtView);
    }

    public void readRss(ActionEvent event) throws IOException {

        rf.showRSS(webView,txtView,loadingBar);

    }
}


