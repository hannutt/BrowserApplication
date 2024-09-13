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

        if (switchTxt.isSelected()) {
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
        if (switchTxt.isSelected())
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





