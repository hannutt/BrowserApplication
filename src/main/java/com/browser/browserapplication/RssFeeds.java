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
        String protocol = "https://";
        Optional<String> result = td.showAndWait();
        String res = String.valueOf(result);


        if (result.isPresent()) {
            try {
                System.out.println(res);
                webView.setOpacity(0.0);
                txtView.setOpacity(1.0);
                loadingBar.setOpacity(0.0);
                txtView.setMinHeight(350);
                URL rssUrl = new URL(result.get());
                BufferedReader br = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
                String feedline;
                String newLine = "\n";
                String[] l = {"<title>", "<item>", "<category>", "</title>", " <description>", " </description>", "</category>", "<guid>", "</guid>", "<pubDate>", "</pubDate>", "<language>", "</language>", "</item>", "<link>", "</link>"};
                //while käy läpi rss syötteen
                while ((feedline = br.readLine()) != null) {
                    //for käy läpi l-listan ja rss-syötteen ja replacen + i-muuttujan avulla korvaa tyhjällä tekstistä
                    //l-listassa olevat tagit jos niitä tekstistä löytyy. for on while silmukan sisällä, joten feedline
                    //txtview.append voidaan tehdä forin ulkopuolella.
                    for (var i = 0; i < l.length; i++) {
                        feedline = feedline.replace(l[i], " ");
                    }

                    txtView.appendText(feedline);
                    txtView.appendText(newLine);
                }
                //jos käyttäjä unohtaa https:// etuliitteen, näytetään siitä iloitus textareaas
            } catch (Exception e) {
                txtView.setText("USE https://");
            }


        }

    }
}
