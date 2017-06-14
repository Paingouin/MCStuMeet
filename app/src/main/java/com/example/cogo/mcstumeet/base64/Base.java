package com.example.cogo.mcstumeet.base64;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Base {
    //test
    public Bitmap convertStringToBitmap(String dbImage){

        //NOTE(jordan)  :  test
        byte[] encodeByte= Base64.decode(dbImage,Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }

//test Ben kmjuhji

//NOTE(Lex) : cette note est inutile
    public String convertBitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);

        byte[] b = baos.toByteArray();

        String temp= Base64.encodeToString(b, Base64.DEFAULT);
       return temp;
    }
}
