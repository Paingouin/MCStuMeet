package com.example.cogo.mcstumeet.database_response;


import com.example.cogo.mcstumeet.database_date.DatabaseSchemaDate;

public class QueryBuilderResponse {
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

    public String docApiKeyUrl(String docid)
    {
        return "/"+docid+"?apiKey="+getApiKey();
    }

    public String documentRequest()
    {
        return "responseDate";
    }

    public String buildContactsSaveURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    public String buildContactsGetURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    public String buildContactsUpdateURL(String doc_id)
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl(doc_id);
    }

    public String createResponse(DatabaseSchemaResponse request)
    {
        return String
                .format("{\"sender\": \"%s\", \"receiver\": \"%s\", "
                                + "\"accepted\" : \"%s\"}, \"safe\" : true}",
                        request.sender, request.receiver, request.accepted);
    }
}
