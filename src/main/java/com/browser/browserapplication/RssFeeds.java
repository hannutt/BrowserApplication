package com.browser.browserapplication;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

public class RssFeeds {


    public void showRSS(WebView webView, TextArea txtView, ProgressBar loadingBar) throws IOException {
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Enter address");
        Optional<String> result = td.showAndWait();
        if (result.isPresent()){
            webView.setOpacity(0.0);
            txtView.setOpacity(1.0);
            loadingBar.setOpacity(0.0);
            txtView.setMinHeight(350);
            URL rssUrl = new URL (result.get());
            BufferedReader br = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
            String feedline;
            String newLine = "\n";
            while ((feedline = br.readLine())!=null){

                feedline=feedline.replace("<title>","").replace("<link>","").replace("</link>","").replace("<item>","")
                        .replace("</item>","").replace("<pubDate>","").replace("</pubDate>","")
                        .replace("<description>","").replace("</description>","")
                        .replace("<category>","").replace("</category","").replace("<guid>","").replace("</guid>","");
                        txtView.appendText(feedline);
                        txtView.appendText(newLine);
            }


        }
        else{
            td.close();
        }




    }

}
