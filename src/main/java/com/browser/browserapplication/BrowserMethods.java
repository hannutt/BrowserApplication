package com.browser.browserapplication;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BrowserMethods {
    WebEngine webEngine = new WebEngine();


    public  int listI;
    public boolean BannedSite=false;






    public void startBrowsing(List<String> addreslist, TextField addField, CheckBox switchTxt, TextArea txtView, WebView webView, ProgressBar loadingBar) {
        String address = "http://"+addField.getText();

        if (switchTxt.isSelected())
        {
            if (address.contains("www."))
            {
                address= address.replace("www.","");
            }
            txtView.clear();
            webView.getEngine().load(address);
            String pageText = (String) webView.getEngine().executeScript("document.documentElement.innerText");


            txtView.setFont(Font.font("Verdana",14));
            txtView.appendText(pageText);
            addreslist.add(address);
            listI = listI +1;
            checkIsFullyLoaded(webView,loadingBar);

        }

        else if (!switchTxt.isSelected())
        {
            if (address.contains("www."))
            {
                address= address.replace("www.","");
            }

            checkIsFullyLoaded(webView,loadingBar);


            //getPageTitle(webView);

            addreslist.add(address);
            webView.getEngine().load(address);

            listI+=1;



        }
    }


    //metodi tarkastaa, että sivu on kokonaan ladattua ja vasta sitten antaa progressbarin arvon 1.0
    //eli porgress bar on "päässyt perille"
public void checkIsFullyLoaded(WebView webView, ProgressBar loadingBar) {


        webView.getEngine().getLoadWorker().stateProperty().addListener(

                new ChangeListener<Worker.State>() {

                    @Override

                    public void changed(
                            ObservableValue<? extends Worker.State> observable,
                            Worker.State oldValue, Worker.State newValue ) {

                        //succeeded tarkoittaa sivun latautuneen.
                        if( newValue != Worker.State.SUCCEEDED ) {

                            return;
                    }


                        loadingBar.setProgress(1.0);
                }
            } );


}

    public void executePrevious(List<String> addreslist, WebView webView) {
        listI-=1;
        System.out.println(listI);

        webView.getEngine().load(addreslist.get(listI));
    }

    public void executeNext(WebView webView, List<String> addreslist) {
        listI+=1;
        System.out.println(listI);
        webView.getEngine().load(addreslist.get(listI));
    }

    public void getPageTitle(WebView webView) {
        Document doc = webView.getEngine().getDocument();
        NodeList heads = doc.getElementsByTagName("head");
        String titleText = webEngine.getLocation() ; // use location if page does not define a title
        if (heads.getLength() > 0) {
            Element head = (Element)heads.item(0);
            NodeList titles = head.getElementsByTagName("title");
            if (titles.getLength() > 0) {
                Node title = titles.item(0);
                titleText = title.getTextContent();
                String contains = null;

                if(titleText.contains("Iltalehti"))
                {

                    //goBlank(contains);


                }
                System.out.println(titleText);

            }
        }

    }


    public void ScrapeContent(WebView webView, Stage stage) throws IOException {
        //näytetään sivun teksti (innerhtml)
        String pageText = (String) webView.getEngine().executeScript("document.documentElement.innerText");
        FileChooser fileChooser = new FileChooser();
        //stage on pääikkuna eli hello-view.fxml
        //tähän talletetaan tiedoston polku ja sille syötetty nimi
        File selectedFile= fileChooser.showSaveDialog(stage);

        FileWriter writer = new FileWriter(selectedFile);
        //writerille kerrotaan, mitä kirjoitetaan, eli tässä tapauksessa
        //muuttujan sisältö
        writer.write(pageText);
        System.out.println(pageText);
        writer.close();
    }

    public void htmlStructure(WebEngine webEngine, TextArea txtView, WebView webView, TextField addField, int clicks, Button debugBtn, ImageView sourceImg, TextField searchField, Button findBtn) {
        System.out.println(clicks);


        if (clicks%2==0)
        {
            txtView.clear();
            txtView.setOpacity(0.0);
            webView.setOpacity(1.0);
            searchField.setOpacity(0.0);
            findBtn.setOpacity(0.0);


        }
        else {
            txtView.clear();
            //String url = "http://"+addField.getText();
            //webEngine.load(url);
            String pageText = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
            txtView.setOpacity(1.0);
            webView.setOpacity(0.0);
            txtView.setMinHeight(250);
            //searchField.setOpacity(1.0);
            //findBtn.setOpacity(1.0);
            //sourceImg.setImage(new Image("..\\..\\..\\icons\\undo.png"));
            txtView.setText(pageText);


        }



    }



    }

