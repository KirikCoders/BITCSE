package com.bit.cse;

/**
 * Created by SharathBhargav on 23-10-2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@android.support.annotation.Keep
@KeepClassMembers
public class DatabaseHelper extends SQLiteOpenHelper {

    String DB_PATH = null;

    public static String DB_NAME = "2017e";
    private SQLiteDatabase myDataBase;
    private  Context myContext=null;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 10);
        this.myContext = context;
        File databasesDir = new File("/data/data/" + myContext.getPackageName(), "DBFILES");
        if (!databasesDir.exists()) {
            if (!databasesDir.mkdirs()) {
                Log.d("Tag1", "Oops! Failed create Testing  directory");
            }

        }
        this.DB_PATH=databasesDir.getPath()+File.separator;
    }

public DatabaseHelper()
 {

     super(null,DB_NAME,null,10);
     File databasesDir = new File("/data/data/com.bit.cse", "DBFILES");
     this.DB_PATH=databasesDir.getPath()+File.separator;
 }
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.v("Exist", "Exist");
        } else {
            this.getReadableDatabase();
            Log.v("getReadable", "getReadable");
            try {
                copyDataBase();
                Log.v("Starting copy", "Starting copy");
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }


    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        Log.v("Entering copydatabase","IN copy");
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        Log.v("IN1 COPY","FIle copy done");

        //File file=new File("/sdcard/Dowlnoad/");
        //File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+DB_NAME);
        //file.setReadable(true);
       // Log.v("idiot123","FIle copy done"+file.getName()+"  path "+file.getAbsolutePath()+"  e"+file.exists()+" "+file.canRead());
        //Log.v("suhas","Before file reader"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+"suhas");

        //FileReader fis = new FileReader(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+"suhas");
        //Log.v("suhas","After file reader"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+"suhas");
        //CharBuffer buff = CharBuffer.allocate(10000);
        //fis.read(buff);

        //file.setReadable()
        //Uri uri=Uri.fromFile(file);
        //File file1=new File(uri.getPath());
        //FileInputStream myInput =new FileInputStream(file);

       // InputStream myInput=new FileInputStream(file);
        Log.v("suhas","after fis.read reader");

        //  InputStream myInput= new I(fis);

        Log.v("suhas","inputStram");
        String outFileName = DB_PATH + DB_NAME;
        Log.v("suhas DBPATh",DB_PATH);
        Log.v("suhas DB NAME",DB_NAME);
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //FileWriter myOutput = new FileWriter(outFileName);
        Log.v("suhas","after file writer");
        //myOutput.write(buff.array());
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.v("copy","copying database completed");

    }

    public void openDataBase() throws SQLException {

        String myPath = DB_PATH + DB_NAME;
        //String myPath ="/sdcard/Download/suhas.db";
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        //File file=new File("/sdcard/suhas.sqlite");
        //myDataBase = SQLiteDatabase.openOrCreateDatabase(file,null);

        //myDataBase = SQLiteDatabase.openDatabase(
        // "/storage/emulated/0/Download/suhas.db",
        //null,
        //SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    public Cursor getdataName(String lName,String day,int slot)
    {
        //Statement s=
        Cursor c= myDataBase.rawQuery("select c.room,s.sub,s.sem from classes c,faculty f,subjects s where\n" +
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
        Cursor c= myDataBase.rawQuery("select c.room,s.sub,s.sem from classes c,faculty f,subjects s where\n" +
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
        Cursor c= myDataBase.rawQuery("SELECT f.name,s.sem,s.sub from classes c,faculty f,subjects s WHERE\n" +
                "c.room like'"+lName+"%' AND\n" +
                "c.day='"+day+"' AND\n" +
                "c.slot='"+slot+"' AND\n" +
                "f.fid=s.fid and\n" +
                "c.mapid=s.mapid;",null);
        return c;
    }
    public Cursor getnames()
    {

        Cursor c =myDataBase.rawQuery("select NAME,TAG,fid from faculty",null);
        return c;
    }

    public Cursor getroomno()
    {
        Cursor c =myDataBase.rawQuery("select distinct room from classes",null);
        return  c;
    }
    Cursor getDayFaculty(String lname,String day)
    {
        Cursor c=myDataBase.rawQuery("select c.slot,c.room,s.sem,s.sub from classes c,faculty f,subjects s where\n" +
                "f.name='"+lname+"' and\n" +
                "s.fid=f.fid and\n" +
                "c.day='"+day+"' and\n" +
                "c.mapid=s.mapid order by CAST(c.slot AS INTEGER)",null);
        return c;
    }
    Cursor getDayFacultyInitials(String lname,String day)
    {
        Cursor c=myDataBase.rawQuery("select c.slot,c.room,s.sem,s.sub from classes c,faculty f,subjects s where\n" +
                "f.tag='"+lname+"' and\n" +
                "s.fid=f.fid and\n" +
                "c.day='"+day+"' and\n" +
                "c.mapid=s.mapid order by CAST(c.slot AS INTEGER)",null);
        return c;
    }
    Cursor getSem()
    {
        Cursor c=myDataBase.rawQuery("select distinct sem from subjects group by sem;",null);
        return  c;
    }
    Cursor getDaySem(String sem,String day)
    {
        Cursor c=myDataBase.rawQuery("select c.slot,c.room,f.name,s.sub from classes c,faculty f,subjects s where \n" +
                "c.day='"+day+"' and\n" +
                "s.sem='"+sem+"' and\n" +
                "f.fid=s.fid and\n" +
                "c.mapid=s.mapid order by CAST(c.slot AS INTEGER),c.room",null);
        return c;
    }
    Cursor getRoom()
    {
        Cursor c=myDataBase.rawQuery("select distinct room from classes",null);
        return c;
    }
    Cursor getDayRoom(String room,String day)
    {
        Cursor c=myDataBase.rawQuery("select c.slot,s.sem,f.name,s.sub from classes c,faculty f,subjects s where \n"+
                "c.room like '"+room+"%' and\n"+
                "c.day='"+day+"' and\n"+
                "f.fid=s.fid and\n" +
                "s.mapid=c.mapid order by CAST(c.slot AS INTEGER)",null);
        return c;
    }
    void setDbName(String s)
    {
        DB_NAME=s;
    }
    String getDbName(){return DB_NAME;}


   public Cursor getSlot()
    {
        Cursor c=myDataBase.rawQuery("select * from slot",null);
        return c;
    }


    public Cursor getFacultyInfo()
    {
        Cursor c =myDataBase.rawQuery("select NAME,TAG,fid,designation,qualification,emailid,phoneno from faculty",null);
        return c;
    }
}
