package com.browser.browserapplication;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TextMethods {

    //Dictionary<String, Integer> dict= new Hashtable<>();
    public Dictionary<String,String> methodsRun=new Hashtable<>();


    public void DoBolding(TextArea txtView) {
        double currentFontSize = txtView.getFont().getSize();
        String currentFontName = txtView.getFont().getName();
        txtView.setFont(Font.font(currentFontName, FontWeight.BOLD,currentFontSize));
        methodsRun.put("command","UnBold(txtView)");
    }
    public void UnBold(TextArea txtView) {
        double currentFontSize = txtView.getFont().getSize();
        String currentFontName = txtView.getFont().getName();
        txtView.setFont(Font.font(currentFontName,FontWeight.NORMAL,currentFontSize));


    }


    public void cancelLatestMethod(TextArea txtView) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        {

            String command = methodsRun.get("command");
            Class c = Class.forName("com.browser.browserapplication.TextMethods");
            Object obj = c.newInstance();

            Method method = c.getDeclaredMethod(command, null);
            method.setAccessible(true);
            method.invoke(obj, null);


            //UnBold(txtView);



        }

    }

}
