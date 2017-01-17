package com.example.cogo.mcstumeet.database_date;

import com.example.cogo.mcstumeet.database.DatabaseSchema;

/**
 * Created by Gamze on 17.01.2017.
 */

public class QueryBuilderDate {
    public String getDatabaseName() {
        return "stumeetmc";
    }

    public String getApiKey() {
        return "IdSlaV9OzH42lKfL_P8jTVR7OBfgvD-U";
    }

    public String getBaseUrl()
    {
        return "https://api.mongolab.com/api/1/databases/"+getDatabaseName()+"/collections/";
    }

    public String docApiKeyUrl()
    {
        return "?apiKey="+getApiKey();
    }

    public String documentRequest()
    {
        return "request";
    }

    public String buildContactsSaveURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    public String buildContactsGetURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    public String createRequest(DatabaseSchemaDate request)
    {
        return String
                .format("{\"sender\": \"%s\", \"receiver\": \"%s\", "
                                + "\"time\": \"%s\", \"location\" : \"%s\", \"accepted\" : \"%s\"}, \"safe\" : true}",
                        request.sender, request.receiver, request.time, request.location, request.accepted);
    }
}
