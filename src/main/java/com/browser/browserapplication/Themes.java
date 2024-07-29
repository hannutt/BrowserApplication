package com.browser.browserapplication;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Themes {


    public void useDarkTheme (VBox vb) throws IOException {
        File settingsFile = new File("settings.txt");
        //tarkistetaan onko settigs.txt jo olemassa
        if (settingsFile.isFile())
        {
            FileWriter writer = new FileWriter("settings.txt");
            writer.write("");
            writer.write("-fx-background-color: #4c4c4c;");
            writer.close();
            vb.setStyle("-fx-background-color: #4c4c4c;");

        }
        else {
            File fname = new File("settings.txt");
            //settings.txt tiedostoon tallennetaan valittu tyyliarvo
            FileWriter writer = new FileWriter(fname);
            writer.write("");
            writer.write("-fx-background-color: #4c4c4c;");
            writer.close();
            vb.setStyle("-fx-background-color: #4c4c4c;");
        }
    }
    public void useLightTheme(VBox vb) throws IOException {
        File settingsFile = new File("settings.txt");
        if (settingsFile.isFile())
        {
            FileWriter writer = new FileWriter("settings.txt");
            writer.write("");
            writer.write("-fx-background-color:  #f7f9f9;");
            writer.close();
            vb.setStyle("-fx-background-color:  #f7f9f9;");

        }
        else {
            File fname = new File("settings.txt");
            //settings.txt tiedostoon tallennetaan valittu tyyliarvo
            FileWriter writer = new FileWriter(fname);
            writer.write("");
            writer.write("-fx-background-color: #f7f9f9;");
            writer.close();
            vb.setStyle("-fx-background-color: #f7f9f9;");
        }

    }
    public void setSavedStyle(VBox vb, String line) {
        System.out.println(line);
        vb.setStyle(line);
    }

    public void setCustomTheme(KeyEvent keyEvent, TextField colCode, VBox vb) throws IOException {
        if ((keyEvent.getCode() == KeyCode.ENTER)) {
            String colorCode = STR."-fx-background-color:\{colCode.getText()};";
            vb.setStyle(colorCode);
            FileWriter writer = new FileWriter("settings.txt");
            writer.write("");
            writer.write(colorCode);
            writer.close();

        }
    }
}
