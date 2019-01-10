package io.kirikcoders.bitcse.database;

import android.content.Context;
import android.database.Cursor;
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
public class TimeTableDataBaseHelper extends SQLiteOpenHelper {
    private final String FILE_PATH;
    private final String DB_NAME = Constants.LOCAL_DATABASE_FILE;
    private SQLiteDatabase database;
    private Context context;

    public TimeTableDataBaseHelper(@Nullable Context context) {
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

    private boolean databaseExists() {
        try {
            database = SQLiteDatabase.openDatabase(FILE_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e){
            return false;
        }
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
        if (newVersion > oldVersion)
            try {
                copyDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

/**
 * Created by SharathBhargav on 23-10-2016.
 * Legacy functions. Avoid modification if you do not know SQL.
 */
    public Cursor getdataName(String lName, String day, int slot)
    {
        //Statement s=
        Cursor c= database.rawQuery("select c.room,s.sub,s.sem from classes c,faculty f,subjects s where\n" +
                "c.day='"+day+"' and\n" +
                "c.slot='"+slot+"' and\n" +
                "f.name='"+lName+"' and\n" +
                "s.fid=f.fid and\n" +
                "s.mapid=c.mapid;",null);

        return c;
    }

    public Cursor getDataNameInitials(String lName,String day,int slot)
    {
        //Statement s=
        Cursor c= database.rawQuery("select c.room,s.sub,s.sem from classes c,faculty f,subjects s where\n" +
                "c.day='"+day+"' and\n" +
                "c.slot='"+slot+"' and\n" +
                "f.tag='"+lName+"' and\n" +
                "s.fid=f.fid and\n" +
                "s.mapid=c.mapid;",null);

        return c;
    }


    public Cursor getdataRoom(String lName,String day,int slot)
    {
        //Statement s=
        Cursor c= database.rawQuery("SELECT f.name,s.sem,s.sub from classes c,faculty f,subjects s WHERE\n" +
                "c.room like'"+lName+"%' AND\n" +
                "c.day='"+day+"' AND\n" +
                "c.slot='"+slot+"' AND\n" +
                "f.fid=s.fid and\n" +
                "c.mapid=s.mapid;",null);
        return c;
    }
    public Cursor getnames()
    {

        Cursor c =database.rawQuery("select NAME,TAG,fid from faculty",null);
        return c;
    }

    public Cursor getroomno()
    {
        Cursor c =database.rawQuery("select distinct room from classes",null);
        return  c;
    }
    public Cursor getDayFaculty(String lname,String day)
    {
        Cursor c=database.rawQuery("select c.slot,c.room,s.sem,s.sub from classes c,faculty f,subjects s where\n" +
                "f.name='"+lname+"' and\n" +
                "s.fid=f.fid and\n" +
                "c.day='"+day+"' and\n" +
                "c.mapid=s.mapid order by CAST(c.slot AS INTEGER)",null);
        return c;
    }
    public Cursor getDayFacultyInitials(String lname,String day)
    {
        Cursor c=database.rawQuery("select c.slot,c.room,s.sem,s.sub from classes c,faculty f,subjects s where\n" +
                "f.tag='"+lname+"' and\n" +
                "s.fid=f.fid and\n" +
                "c.day='"+day+"' and\n" +
                "c.mapid=s.mapid order by CAST(c.slot AS INTEGER)",null);
        return c;
    }
    public Cursor getSem()
    {
        Cursor c=database.rawQuery("select distinct sem from subjects group by sem;",null);
        return  c;
    }
    public Cursor getDaySem(String sem,String day)
    {
        Cursor c=database.rawQuery("select a.timings,c.room,f.name,s.sub from classes c,faculty f,subjects s,slot a where \n" +
                "c.day='"+day+"' and\n" +
                "s.sem='"+sem+"' and\n" +
                "f.fid=s.fid and\n" +
                "c.mapid=s.mapid and a.slotnumber = c.slot order by CAST(c.slot AS INTEGER),c.room",null);
        return c;
    }
    public Cursor getRoom()
    {
        Cursor c=database.rawQuery("select distinct room from classes",null);
        return c;
    }
    public Cursor getDayRoom(String room,String day)
    {
        Cursor c=database.rawQuery("select c.slot,s.sem,f.name,s.sub from classes c,faculty f,subjects s where \n"+
                "c.room like '"+room+"%' and\n"+
                "c.day='"+day+"' and\n"+
                "f.fid=s.fid and\n" +
                "s.mapid=c.mapid order by CAST(c.slot AS INTEGER)",null);
        return c;
    }

    public String getDbName(){return DB_NAME;}


    public Cursor getSlot()
    {
        Cursor c=database.rawQuery("select * from slot",null);
        return c;
    }


    public Cursor getFacultyInfo()
    {
        Cursor c =database.rawQuery("select NAME,TAG,fid,designation,qualification,emailid,phoneno from faculty",null);
        return c;
    }
}
