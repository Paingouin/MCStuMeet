package com.example.cogo.mcstumeet.mail;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Jordan on 21/06/2017.
 */

public class AsyncMail extends AsyncTask<String,Integer, Void>
{
    @Override
    protected Void doInBackground(String... arg0)
    {
        try {
            String mail= arg0[0];
            GMailSender sender = new GMailSender("stumeetapp@gmail.com", "ironm@iden21!");
            sender.sendMail("This is Subject",
                    "This is Body",
                    mail,
                    mail);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
        return null;
    }
}
