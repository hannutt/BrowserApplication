package com.browser.browserapplication;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Helpers {





    public void UrlChecker(TextField addField, Label helperLbl, boolean helpersOn) {
        addField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (helpersOn && newValue.startsWith("www"))
            {
                helperLbl.setOpacity(1.0);
                helperLbl.setStyle("-fx-background-color:red");
                helperLbl.setText("www or http no needed");
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                helperLbl.setOpacity(0.0);
                            }
                        },
                        3000
                );

            }


        });
    }
    }