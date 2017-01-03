package com.example.cogo.mcstumeet.database;

/**
 * Created by Gamze on 02.01.2017.
 */

public class QueryBuilder {

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
        return "users";
    }

    public String buildContactsSaveURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    public String buildContactsGetURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    public String createContact(DatabaseSchema contact)
    {
        return String
                .format("{\"document\" : {\"username\": \"%s\", "
                                + "\"password\": \"%s\", \"email\": \"%s\", "
                                + "\"birthday\": \"%s\", \"gender\" : \"%s\", \"interests\" : \"%s\", "
                                +"\"hobbies\" : \"%s\", \"description\" : \"%s\", "
                                +"\"image\" : \"%s\", \"education\" : \"%s\", "
                                +"\"languages\" : \"%s\", \"uploadedImaged\" : \"%s\","
                                +"\"numberOfDates\" : \"%s\", \"dates\" : \"%s\"}, \"safe\" : true}",
                        contact.username, contact.password, contact.email, contact.birthday, contact.gender,
                        contact.interests, contact.hobbies, contact.description, contact.image, contact.education,
                        contact.languages, contact.uploadedImages, contact.numberOfDates, contact.dates);
    }
}
