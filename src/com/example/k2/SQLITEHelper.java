package com.example.k2;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author Nishanth Prasad
 *
 */
public class SQLITEHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "kaveri.db";
	private static final int DB_VERSION =1;
	private Context myContext;
	SQLITEHelper dbHelper;
	private static final String CREATE_TABLE_1 = "create table book(id  text, title text,authors text,price text,description);";
	private static final String CREATE_TABLE_2 = "create table music(title  text, album text, artist text,genre text);";
	private static final String CREATE_TABLE_3= "create table camera(model  text, make text, price text,picture text);";
	
	public SQLITEHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		myContext = context;
	}

	/**
	 * This is used to create tables
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		/********************** book table ********************************************/
		db.execSQL(CREATE_TABLE_1);
		/********************** music table ********************************************/
		db.execSQL(CREATE_TABLE_2);
		/********************** camera table ********************************************/
		db.execSQL(CREATE_TABLE_3);
	}

	/**
	 * This method is used to upgrade the table
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	public void deleteTable(){
		try{
		SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
		sqlDB.delete("camera", null, null);
		sqlDB.delete("music", null, null);
		sqlDB.delete("book", null, null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//id  text, title text,authors text,price text,description
	public void insertbook(ArrayList<HashMap<String,String>> hmaplist) {
		dbHelper = new SQLITEHelper(myContext);
		SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
		for(int i=0;i<hmaplist.size();i++){
			ContentValues values = new ContentValues();
			values.put("id", hmaplist.get(i).get("id"));
			values.put("title", hmaplist.get(i).get("title"));
			values.put("authors", hmaplist.get(i).get("authors"));
			values.put("price", hmaplist.get(i).get("price"));
			values.put("description", hmaplist.get(i).get("description"));
			long retVal=sqlDB.insert("book", null, values);
		}
		if (sqlDB != null){
			sqlDB.close();
		}
	}
	//title  text, album text, artist text,genre text
	public void insertmusic(ArrayList<HashMap<String,String>> hmaplist) {
		dbHelper = new SQLITEHelper(myContext);
		SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
		for(int i=0;i<hmaplist.size();i++){
			ContentValues values = new ContentValues();
			values.put("title", hmaplist.get(i).get("title"));
			values.put("album", hmaplist.get(i).get("album"));
			values.put("artist", hmaplist.get(i).get("artist"));
			values.put("genre", hmaplist.get(i).get("genre"));
			long retVal=sqlDB.insert("music", null, values);
		}
		if (sqlDB != null){
			sqlDB.close();
		}
	}
	//model  text, make text, price text,picture text
	public void insertcamera(ArrayList<HashMap<String,String>> hmaplist) {
		dbHelper = new SQLITEHelper(myContext);
		SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
		for(int i=0;i<hmaplist.size();i++){
			ContentValues values = new ContentValues();
			values.put("model", hmaplist.get(i).get("model"));
			values.put("make", hmaplist.get(i).get("make"));
			values.put("price", hmaplist.get(i).get("price"));
			values.put("picture", hmaplist.get(i).get("picture"));
			long retVal=sqlDB.insert("camera", null, values);
		}
		if (sqlDB != null){
			sqlDB.close();
		}
	}
	public ArrayList<HashMap<String,String>> getBooks() {
		dbHelper = new SQLITEHelper(myContext);
		ArrayList<HashMap<String,String>> hmaplist=null;
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor=db.rawQuery("select id,title,authors,price,description  from book",null);
			if (cursor != null && cursor.moveToFirst()) {
				hmaplist=new ArrayList<HashMap<String,String>>();
				do{
					HashMap<String,String> map=new HashMap<String, String>();
					map.put("id",cursor.getString(0));//id
					map.put("title",cursor.getString(1));//title
					map.put("authors",cursor.getString(2));//authors
					map.put("price",cursor.getString(3));//price
					map.put("description",cursor.getString(4));//description
					hmaplist.add(map);
				} while (cursor.moveToNext());
				
			}
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (db != null)
				db.close();
		} catch (Exception e) {
			e.printStackTrace();
			return hmaplist;
		}
		return hmaplist;
	}
	
	public ArrayList<HashMap<String,String>> getMusics() {
		dbHelper = new SQLITEHelper(myContext);
		ArrayList<HashMap<String,String>> hmaplist=null;
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor=db.rawQuery("select title,album,artist,genre  from music",null);
			if (cursor != null && cursor.moveToFirst()) {
				hmaplist=new ArrayList<HashMap<String,String>>();
				do{
					HashMap<String,String> map=new HashMap<String, String>();
					map.put("title",cursor.getString(0));//title
					map.put("album",cursor.getString(1));//album
					map.put("artist",cursor.getString(2));//artist
					map.put("genre",cursor.getString(3));//genre
					hmaplist.add(map);
				} while (cursor.moveToNext());
				
			}
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (db != null)
				db.close();
		} catch (Exception e) {
			e.printStackTrace();
			return hmaplist;
		}
		return hmaplist;
	}
	
	public ArrayList<HashMap<String,String>> getCameras() {
		dbHelper = new SQLITEHelper(myContext);
		ArrayList<HashMap<String,String>> hmaplist=null;
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor=db.rawQuery("select model,make,price,picture from camera",null);
			if (cursor != null && cursor.moveToFirst()) {
				hmaplist=new ArrayList<HashMap<String,String>>();
				do{
					HashMap<String,String> map=new HashMap<String, String>();
					map.put("model",cursor.getString(0));//model
					map.put("make",cursor.getString(1));//make
					map.put("price",cursor.getString(2));//price
					map.put("picture",cursor.getString(3));//picture
					hmaplist.add(map);
				} while (cursor.moveToNext());
				
			}
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (db != null)
				db.close();
		} catch (Exception e) {
			e.printStackTrace();
			return hmaplist;
		}
		return hmaplist;
	}
}
