package com.ming.bean2;


import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateEditProperties extends PropertyEditorSupport {


    private String formatting = "yyyy-MM-dd";


    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        System.out.println("text = " + text);
        SimpleDateFormat sdf = new SimpleDateFormat(formatting);
        try{
            Date date = sdf.parse(text);
            this.setValue(date);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
