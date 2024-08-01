package com.browser.browserapplication;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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




    public void startBrowsing(List<String> addreslist, TextField addField, CheckBox switchTxt, TextArea txtView, WebView webView) {
        String address = "http://www."+addField.getText();

        if (switchTxt.isSelected())
        {
            if (address.contains("www."))
            {
                address= address.replace("www.","");
            }
            txtView.clear();
            webView.getEngine().load(address);
            String pageText = (String) webView.getEngine().executeScript("document.documentElement.innerText");



            txtView.appendText(pageText);
            addreslist.add(address);
            listI = listI +1;
        }

        else if (!switchTxt.isSelected())
        {
            if (address.contains("www."))
            {
                address= address.replace("www.","");
            }

            //getPageTitle(webView);

            addreslist.add(address);
            webView.getEngine().load(address);
            listI+=1;



            System.out.println(addreslist);



        }
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

    public void htmlStructure(WebEngine webEngine, TextArea txtView, WebView webView, TextField addField, int clicks, Button debugBtn) {
        System.out.println(clicks);


        if (clicks%1==0)
        {
            txtView.setOpacity(1.0);
            webView.setOpacity(0.0);
            txtView.setMinHeight(250);
            debugBtn.setText("Hide source code");
            String url = "http://"+addField.getText();
            webEngine.load(url);
            String pageText = (String) webEngine.executeScript("document.documentElement.outerHTML");
            txtView.appendText(pageText);

        }
        if (clicks %2==0)
        {
            txtView.setOpacity(0.0);
            webView.setOpacity(1.0);
            debugBtn.setText("Show source code");

        }


    }



    }

