package com.browser.browserapplication;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Zoom {

    public void IncreaseZoom(WebView webView, CheckBox switchTxt, TextArea txtView) {
        //haetaan RssFeed luokan Rss booleann arvo.
        boolean rss = RssFeeds.setRssTrue();
        System.out.println(rss);
        //jos checkbox on valittu tai rss on true
        if (switchTxt.isSelected() || rss) {
            //nykyisen fontttikoo tallennus muuttujaan
            double currentFontSize = txtView.getFont().getSize();
            //fonttikoon kasvatus 1.0 / klikkaus ja kasvatetun fonttikoon
            //lisäys tekstilaatikkoon
            currentFontSize+=1.0;
            txtView.setFont(Font.font(currentFontSize));
            System.out.println(txtView.getFont());

        }
        else {
            double current = webView.getZoom();
            webView.setZoom(current + 1.0);

        }

    }

    public void DecreaseZoom(WebView webView, CheckBox switchTxt, TextArea txtView) {
        boolean rss = RssFeeds.setRssTrue();
        if (switchTxt.isSelected()|| rss)
        {
            double currentFontSize = txtView.getFont().getSize();
            currentFontSize-=1.0;
            txtView.setFont(Font.font(currentFontSize));
            System.out.println(txtView.getFont());
        }
        else{
            double current = webView.getZoom();
            webView.setZoom(current - 1.0);

        }
    }



}





