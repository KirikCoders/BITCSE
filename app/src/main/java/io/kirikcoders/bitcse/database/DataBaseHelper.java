package io.kirikcoders.bitcse.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.Nullable;
import io.kirikcoders.bitcse.utils.Constants;

/**
 * Created by Kartik on 17-Oct-18.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private final String FILE_PATH;
    private final String DB_NAME = Constants.LOCAL_DATABASE_FILE;
    private SQLiteDatabase database;
    private Context context;
    public DataBaseHelper(@Nullable Context context) {
        super(context, Constants.LOCAL_DATABASE_FILE, null, Constants.DB_VERSION);
        this.context = context;
        FILE_PATH = "/data/data/"+context.getPackageName()+"/databases/";
    }

    public void createDatabase() throws IOException {
        if (!databaseExists()){
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private void copyDatabase() throws IOException {
        InputStream inputStream = context.getAssets().open(DB_NAME);
        OutputStream outputStream = new FileOutputStream(FILE_PATH+DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer))>0){
            outputStream.write(buffer, 0, length);
        }

        //Close the streams
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    private boolean databaseExists() throws SQLiteException {
        database = SQLiteDatabase.openDatabase(FILE_PATH+DB_NAME,null,SQLiteDatabase.OPEN_READONLY);
        if (database != null){
            database.close();
            return true;
        }
        return false;
    }

    public void openDatabase() throws IOException {
        database = SQLiteDatabase.openDatabase(FILE_PATH+DB_NAME,null,SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (database != null){
            database.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
