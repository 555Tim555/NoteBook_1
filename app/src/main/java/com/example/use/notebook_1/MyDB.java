package com.example.use.notebook_1;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MyDB {
    public SQLiteDatabase db = null;  //資料庫類別
    private final static String DATABASE_NAME = "mydb.db";  //資料庫名稱
    private final static String TABLE_NAME = "table01"; //資料表名稱

    /* 資料表欄位 */ //如果修改欄位的名稱，而資料庫已經存在，那就會出錯，必須修改資料庫名稱才可以更改
    private final static String _ID = "_id";
    private final static String TITLE = "title";
    private final static String CONTENT = "content";
    /* 建立資料表的欄位 */
    private final static String create_table = "CREATE TABLE "+TABLE_NAME+" (" + _ID +" INTEGER PRIMARY KEY," + TITLE + " TEXT," + CONTENT + " TEXT)";

    private Context mCtx = null;
    public MyDB(Context ctx) //建構式
    {
        this.mCtx = ctx; //傳入建立物件的 Activity
    }

    public void open()throws SQLException { //開啟已經存在的資料庫
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
        try {
            db.execSQL(create_table); //建立資料表
        }catch (Exception e){}
    }

    public void close(){
        db.close();
    }

    /*public Cursor getAll(){
        return db.rawQuery("SELECT * FROM" + TABLE_NAME,null);
    }*/

    public Cursor getAll(){ //查詢所有資料，只取出三個欄位
        Cursor cursor = db.query(TABLE_NAME, new String[]{_ID,TITLE,CONTENT}, null,null,null,null,null,null);
        return cursor;
    }

    public Cursor get(long rowId) throws SQLException{ //查詢指定 ID 的資料，只取出三個欄位
        Cursor mCursor = db.query(TABLE_NAME,
                new String[]{_ID,"TITLE","CONTENT"},
                _ID + "=" + rowId, null,null,null,null,null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public long append(String title,String text){ //新增一筆資料
        ContentValues args = new ContentValues();
        args.put(TITLE,title);
        args.put(CONTENT,text);
        return db.insert(TABLE_NAME,null,args);
    }

    public boolean delete(long rowId){ //刪除指定的資料
        return db.delete(TABLE_NAME,_ID + "=" + rowId,null) > 0;
    }

    public boolean update(long rowId,String title,String content){
        //更新指定的資料
        ContentValues args = new ContentValues();
        args.put(TITLE,title);
        args.put(CONTENT,content);
        return db.update(TABLE_NAME,args,_ID + "=" + rowId, null) > 0;
    }
}
