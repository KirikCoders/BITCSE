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
import java.util.ArrayList;

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
//            this.getReadableDatabase();
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
    /**
    * Check if database exists before copying it into the phone
     */
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
    /**
     * Call openDatabase() with the adapter to create a reference to the db
     */
    public void openDatabase() throws IOException {
        database = SQLiteDatabase.openDatabase(FILE_PATH+DB_NAME,null,SQLiteDatabase.OPEN_READWRITE);
    }
    /**
     * Create the registered events database if it does not exist
     * still required to call openDatabase() before this
     * */
    public void addToRegisteredEventsTable(String eventName,String imageUrl,String eventTime,
                                           String eventDate,String eventContact){
        database.execSQL("CREATE TABLE IF NOT EXISTS registered_events(" +
                "event_name VARCHAR(200)," +
                "event_image VARCHAR(200)," +
                "event_time VARCHAR(30)," +
                "event_date VARCHAR(30)," +
                "event_contact VARCHAR(30)," +
                "CONSTRAINT RG_PK PRIMARY KEY(event_name,event_image,event_contact))");
        database.execSQL("INSERT INTO registered_events VALUES('" +
                eventName+"','"+imageUrl+"','"+eventTime+"','"+eventDate+"','"+eventContact+"')");

    }
    /**
     * delete multiple events at the end of the day whose date has expired using
     * a service or scheduler
     * */
    public void deleteRegisteredEvents(String[] eventNames){
        database.delete("registered_events","event_name=?",eventNames);
    }
    /**
     * Allow the user to delete a single event
     * */
    public void deleteRegisteredEvent(String eventName){
        database.execSQL("DELETE FROM registered_events WHERE event_name='"+eventName+"'");
    }
    /**
     * retrieve data from the registered events table
     * */
    public Cursor getRegisteredEvents(){
        return database.rawQuery("SELECT * FROM registered_events",null);
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
    /**
     * returns a cursor containing room number,subject and semester if
     * provided with faculty name, day and slot timing (refer database)
     * */
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
    /**
     * get all faculty by tags
     * */
    public Cursor getnames()
    {

        Cursor c =database.rawQuery("select NAME,TAG,fid from faculty",null);
        return c;
    }
    /**
     * get all available room numbers
     * */
    public ArrayList<String> getRoomNumbers()
    {
        Cursor c =database.rawQuery("select distinct room from classes",null);
        ArrayList<String> rooms = new ArrayList<>(c.getCount());
        while (c.moveToNext()){
            rooms.add(c.getString(c.getColumnIndex("room")));
        }
        return rooms;
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
    /**
     * Get all semesters recorded in the database
     * */
    public Cursor getSem()
    {
        Cursor c=database.rawQuery("select distinct sem from subjects group by sem;",null);
        return  c;
    }
    /**
     * get the time table information given a semester and class in @args sem and a
     * day in @args day
     * */
    public Cursor getDaySem(String sem,String day)
    {
        Cursor c=database.rawQuery("select a.timings,c.room,f.name,s.sub from classes c,faculty f,subjects s,slot a where \n" +
                "c.day='"+day+"' and\n" +
                "s.sem='"+sem+"' and\n" +
                "f.fid=s.fid and\n" +
                "c.mapid=s.mapid and a.slotnumber = c.slot order by CAST(c.slot AS INTEGER),c.room",null);
        return c;
    }
    /**
     * get all rooms
     * */
    public Cursor getRoom()
    {
        Cursor c=database.rawQuery("select distinct room from classes",null);
        return c;
    }
    /**
     * check if anyone is using @args room given a @args day
     * */
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

    /**
     * get all slot timings
     * */
    public Cursor getSlot()
    {
        Cursor c=database.rawQuery("select * from slot",null);
        return c;
    }

    /**
     * get all faculty info
     * */
    public Cursor getFacultyInfo()
    {
        Cursor c =database.rawQuery("select NAME,TAG,fid,designation,qualification,emailid,phoneno from faculty",null);
        return c;
    }

    public ArrayList<String> getFullRooms(int beginTime, int finalTime,String day) {
        Cursor c = database.rawQuery("SELECT c.room FROM classes as c WHERE c.day = '"+day+"' AND c.slot = (SELECT slotnumber FROM slot WHERE timings LIKE '"+beginTime+"%'" +
                " UNION" +
                " SELECT slotnumber FROM slot WHERE timings LIKE '% - "+finalTime+"%')",null);
        ArrayList<String> rooms = new ArrayList<>(c.getCount());
        while (c.moveToNext())
            rooms.add(c.getString(c.getColumnIndex("room")));
        c.close();
        return rooms;
    }
}
