package com.example.cogo.mcstumeet.mail;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Jordan on 21/06/2017.
 */

public class AsyncMail extends AsyncTask<Void,Integer, Void>
{

    String mail;
    String pwd;

    public AsyncMail(String mail, String pwd){
        this.mail = mail;
        this.pwd = pwd;
    }

    @Override
    protected Void doInBackground(Void... arg0)
    {
        try {

            GMailSender sender = new GMailSender("stumeetapp@gmail.com", "ironm@iden21!");
            sender.sendMail("This is Subject",
                    "This is your new password :" +pwd,
                    mail,
                    mail);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
        return null;
    }
}
