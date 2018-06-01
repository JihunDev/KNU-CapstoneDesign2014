package com.example.amigo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite_ExInfo extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1; 				
	
	private static final String DATABASE_NAME = "ExInfo_DB";
	
	
	/* define ExInfo Table */
	private static final String PRIMARY_KEY_ID = "id";			

	private static final String TABLE_ExInfo =  "ExInfo";		
    private static final String EXINfO_NAME =   "name";
	private static final String EXINfO_SPIN =   "spin";
	private static final String EXINfO_SPEED =  "speed";
	private static final String EXINfO_HEIGHT = "height";
	private static final String EXINfO_TECHNIQUE= "technique";
	private static final String EXINfO_SCORE = "score";

	/*  Table End */
	public static Cursor cursor;
	public static String sName;
	
	public SQLite_ExInfo(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	
	@Override

	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// createTableQuery = CREATE TABLE Friend(id INTEGER PRIMARY KEY, name TEXT, email TEXT, phone TEXT)
		
		String createTableQuery_ExInfo = "CREATE TABLE " + TABLE_ExInfo 
                + "(" 
				
                + PRIMARY_KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ EXINfO_NAME     + " TEXT UNIQUE," 
				+ EXINfO_SPIN     + " TEXT,"
				+ EXINfO_SPEED    + " TEXT," 
				+ EXINfO_HEIGHT   + " TEXT,"
				+ EXINfO_TECHNIQUE+ " TEXT,"
				+ EXINfO_SCORE+ " TEXT"
				+ ")";

		
		
		db.execSQL(createTableQuery_ExInfo);

	}

	/**
	 * 
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// dropOldVersionQuery = DROP TABLE IF EXISTS FRIEND
		
		String dropOldVersionQuery_ExInfo = "DROP TABLE IF EXISTS " + TABLE_ExInfo;

        db.execSQL(dropOldVersionQuery_ExInfo);
		// create new database
		onCreate(db);
	}
	

	public void insertExInfo(ExInfo exinfo){

		SQLiteDatabase db = this.getWritableDatabase();


		ContentValues values = new ContentValues();

		values.put(EXINfO_NAME,exinfo.getName());
		values.put(EXINfO_SPIN,exinfo.getSpin());
		values.put(EXINfO_SPEED,exinfo.getSpeed()); 
		values.put(EXINfO_HEIGHT,exinfo.getHeight());
		values.put(EXINfO_TECHNIQUE,exinfo.getTechnique());
		values.put(EXINfO_SCORE,exinfo.getScore());

		// insert! 

	db.insert(TABLE_ExInfo, null, values);

		db.close();
	}
	public List<ExInfo> selectAll(){
		// 모든 정보를 저장할 리스트를 하나 만들어준다.
		List<ExInfo> exinfoList = new ArrayList<ExInfo>();
		
		// SelectAllQuery = SELECT * FROM FRIEND
		String selectAllQuery = "SELECT * FROM " + TABLE_ExInfo+" DESC";
		// 읽기와 쓰기가 가능한 데이터베이스를 참조
		SQLiteDatabase db = this.getWritableDatabase();
		// Cursor 객체 생성
		cursor = db.rawQuery(selectAllQuery, null);
		
		// cursor를 이용하여 가져온 데이터를 읽어온다.
		if ( cursor.moveToFirst() ){	// 데이터베이스에 데이터가 존재한다면...
			do {
				// 현재 데이터가 가르키고 있는 데이터를 이용하여 데이터 객체를 생성하고 리스트에 추가를 한다.
				exinfoList.add(new ExInfo(
								Integer.parseInt(cursor.getString(0)), 
								cursor.getString(1), 
								cursor.getString(2),
								cursor.getString(3),
								cursor.getString(4),
								cursor.getString(5),
								cursor.getString(6)

								));
			} while ( cursor.moveToNext() );
		}
		cursor.close();

		return exinfoList;
	}
	public List<ExInfo> selectExInfo(ExInfo info){
		
		List<ExInfo> exinfoList = new ArrayList<ExInfo>();
		// SelectAllQuery = SELECT * FROM FRIEND
	
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery( "select * from "+TABLE_ExInfo+" where "+PRIMARY_KEY_ID+"=" + "'" + info.getId() + "'" , null);
		


		if ( cursor.moveToFirst() ){	
			
			exinfoList.add(new ExInfo(
								Integer.parseInt(cursor.getString(0)), 
								cursor.getString(1), 
								cursor.getString(2),
								cursor.getString(3),
								cursor.getString(4),
								cursor.getString(5),
								cursor.getString(6)

								));
			
		}
		cursor.close();

		return exinfoList;
	}
	
	// update : update return �� : the number of rows affected
	public int updateExInfo(ExInfo exinfo){
		int updateRow;		// return value
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
	    values.put(EXINfO_NAME, exinfo.getName());
		values.put(EXINfO_SPEED, exinfo.getSpeed()); 
		values.put(EXINfO_HEIGHT, exinfo.getHeight());
		values.put(EXINfO_TECHNIQUE, exinfo.getTechnique());
		values.put(EXINfO_SCORE, exinfo.getScore());

		// update = ... WHERE id = "getId()"
		updateRow = db.update(TABLE_ExInfo, values, PRIMARY_KEY_ID + " = ?", new String[]{String.valueOf(exinfo.getId())});
		
		//  String sql = "update " + TABLE_USER + " set voca = '" + voca +"' where id = "+index +";";
	  //      db.execSQL(sql);
		db.close();
		return updateRow;
	}
	
	// delete
	
}
