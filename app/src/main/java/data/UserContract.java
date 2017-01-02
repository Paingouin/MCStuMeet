package data;

import android.provider.BaseColumns;

public class UserContract {
    public static abstract class UserEntry implements BaseColumns {

        public static final String TABLE_USERS = "Users";

        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_BIRTHDAY = "birthday";
        public static final String KEY_EMAIL = "email";
        public static final String KEY_GENDER = "gender";
        public static final String KEY_GENDERINTERESTEDIN = "genderInterested";
        public static final String KEY_PASSWORD = "password";
    }
}