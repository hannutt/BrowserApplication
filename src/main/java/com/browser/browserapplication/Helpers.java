package com.browser.browserapplication;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class Helpers {


    public void UrlChecker(TextField addField, Label helperLbl, boolean helpersOn) {
        addField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (helpersOn && newValue.startsWith("www"))
            {
                String replacedVal;
                //replacedVal= newValue.replace("www","");
                //addField.setText(replacedVal);
                helperLbl.setOpacity(1.0);
                helperLbl.setStyle("-fx-background-color:red");
                helperLbl.setText("no www or http needed");
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
    public void endOptions(TextField addField) {
        EventHandler<MouseEvent> getBtnTxt=new EventHandler<MouseEvent>() {
            @Override
            //tämä metodi selvittää painetun buttonin id-arvon
            public void handle(MouseEvent mouseEvent) {
                String previousTxt=addField.getText();
                String btnId=((Control)mouseEvent.getSource()).getId();
                addField.setText(previousTxt+btnId);


            }
        };
        Stage optionStage = new Stage();
        HBox hb = new HBox();
        Button comBtn = new Button("com");
        comBtn.setId("com");
        //lisätään buttoniin tapahtumankäsittelijä, joka suoritetaan kun painketta on klikattu hiirellä.
        //eli ensin tulee tapahtuman tyyppi = hiiren klikkaus ja sen jälkeen kerrotaan, mikä metodi suoritetaan
        //itse tapahtumankäsittelijä funktio määritellään alempana, tässä se vain liitetään buttoniin.
        comBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,getBtnTxt);

        Button fiBtn = new Button("fi");
        fiBtn.setId("fi");
        fiBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,getBtnTxt);
        Button orgBtn = new Button("org");
        orgBtn.setId("org");
        orgBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,getBtnTxt);
        Button infoBtn = new Button("info");
        infoBtn.setId("info");
        infoBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,getBtnTxt);

        hb.getChildren().addAll(comBtn,fiBtn,orgBtn,infoBtn);
        Scene scene = new Scene(hb, 100, 100);
        optionStage.setScene(scene);
        optionStage.show();
    }

    public void runConnCheck(Circle connShape) throws IOException, InterruptedException {
        Process process = java.lang.Runtime.getRuntime().exec("ping www.google.com");
        int x = process.waitFor();
        if (x == 0) {
            connShape.setStyle("-fx-fill:green");

        }
        else {
            connShape.setStyle("-fx-fill:red");
        }
    }

    }