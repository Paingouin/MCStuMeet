package com.example.cogo.mcstumeet.mail;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Jordan on 21/06/2017.
 */

public class AsyncMail extends AsyncTask<Void,Integer, Void>
{
    @Override
    protected Void doInBackground(Void... arg0)
    {
        try {
            GMailSender sender = new GMailSender("stumeetapp@gmail.com", "ironm@iden21!");
            sender.sendMail("This is Subject",
                    "This is Body",
                    "jordan.bussiere21@gmail.com",
                    "jordan.bussiere21@gmail.com");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
        return null;
    }
}
