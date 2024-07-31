package com.browser.browserapplication;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Themes {

    @FXML
    public CheckBox switchTxt;
    @FXML
    public  CheckBox startPageCB;


    public void useDarkTheme (AnchorPane anchorPane) throws IOException {
        File settingsFile = new File("settings.txt");
        //tarkistetaan onko settigs.txt jo olemassa
        if (settingsFile.isFile())
        {
            FileWriter writer = new FileWriter("settings.txt");
            writer.write("");
            writer.write("-fx-background-color: #4c4c4c;");
            writer.close();
            anchorPane.setStyle("-fx-background-color: #4c4c4c;");

        }
        else {
            File fname = new File("settings.txt");
            //settings.txt tiedostoon tallennetaan valittu tyyliarvo
            FileWriter writer = new FileWriter(fname);
            writer.write("");
            writer.write("-fx-background-color: #4c4c4c;");
            writer.close();
            anchorPane.setStyle("-fx-background-color: #4c4c4c;");
        }
    }
    public void useLightTheme(AnchorPane anchorPane) throws IOException {
        File settingsFile = new File("settings.txt");
        if (settingsFile.isFile())
        {

            FileWriter writer = new FileWriter("settings.txt");
            writer.write("");
            writer.write("-fx-background-color: #c4dada;\n");
            writer.write("-fx-text-fill:black");
            writer.close();
            anchorPane.setStyle("-fx-background-color: #c4dada ;");
            switchTxt.setStyle("-fx-text-fill:black");
            startPageCB.setStyle("-fx-text-fill:black");

        }
        //jos settings.txt.tä ei ole olemassa, luodaan se ensin.
        else {
            File fname = new File("settings.txt");
            //settings.txt tiedostoon tallennetaan valittu tyyliarvo
            FileWriter writer = new FileWriter(fname);
            writer.write("");
            writer.write("-fx-background-color: #c4dada;");
            writer.close();
            anchorPane.setStyle("-fx-background-color: #c4dada ;");
        }

    }
    public void setSavedStyle(AnchorPane anchorPane, String bgcolor) {
        System.out.println(bgcolor);
        anchorPane.setStyle(bgcolor);
    }

    public void setCBtextColor(CheckBox startPageCB, CheckBox switchTxt, String txtFIll) {
        startPageCB.setStyle(txtFIll);
        switchTxt.setStyle(txtFIll);
    }

    public void setCustomTheme(KeyEvent keyEvent, TextField colCode, AnchorPane anchorPane) throws IOException {
        if ((keyEvent.getCode() == KeyCode.ENTER)) {
            String colorCode = STR."-fx-background-color:\{colCode.getText()};";
            anchorPane.setStyle(colorCode);
            FileWriter writer = new FileWriter("settings.txt");
            writer.write("");
            writer.write(colorCode);
            writer.close();

        }
    }
}
