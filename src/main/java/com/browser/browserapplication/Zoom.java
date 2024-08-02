package com.browser.browserapplication;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

public class Zoom {
    @FXML
    public WebView webView;

    public void IncreaseZoom(WebView webView) {
        double current = webView.getZoom();
        webView.setZoom(current + 1.0);

    }

    public void DecreaseZoom(WebView webView) {
        double current = webView.getZoom();
        webView.setZoom(current - 1.0);

    }


}





