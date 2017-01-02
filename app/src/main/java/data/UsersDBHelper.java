package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static data.UserContract.UserEntry.KEY_BIRTHDAY;
import static data.UserContract.UserEntry.KEY_EMAIL;
import static data.UserContract.UserEntry.KEY_GENDER;
import static data.UserContract.UserEntry.KEY_GENDERINTERESTEDIN;
import static data.UserContract.UserEntry.KEY_ID;
import static data.UserContract.UserEntry.KEY_NAME;
import static data.UserContract.UserEntry.KEY_PASSWORD;

public class UsersDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Users";


    public UsersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    String CREATE_TABLE_USERS = "CREATE TABLE " + UserContract.UserEntry.TABLE_USERS + "("
            + UserContract.UserEntry.KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_BIRTHDAY + " DATE" + KEY_EMAIL + "TEXT" +
            KEY_GENDER + "GENDER" + KEY_GENDERINTERESTEDIN + "GENDER" +  KEY_PASSWORD+ "PASSWORD"+")";

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL(CREATE_TABLE_USERS);
    }

    public void addUser(User u) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, u.getId());
        values.put(KEY_NAME, u.getName());
        values.put(KEY_BIRTHDAY, u.getBirthday());
        values.put(KEY_EMAIL, u.getEmail());
        values.put(KEY_GENDER, u.getGender().toString());
        values.put(KEY_GENDERINTERESTEDIN, u.getGenderInterested().toString());
        values.put(KEY_PASSWORD, u.getPassword());
    }

    public long saveUser(User user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                UserContract.UserEntry.TABLE_USERS,
                null,
                user.toContentValues());
    }

    public boolean isUser(User user) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM Users WHERE id = '1'", null);

        return c.moveToFirst();
    }
}
