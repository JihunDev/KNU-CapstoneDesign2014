package com.example.amigo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteUserListHelper extends SQLiteOpenHelper {

	// 테이블에 관련된 변수들을 상수로 미리 선언해줍니다.
	private static final int DATABASE_VERSION = 1; 				// 어플리케이션 데이터베이스 버전관리
	
	private static final String DATABASE_NAME = "User_DB";	// 데이터베이스 이름
	
	/* define Friend Table */
	private static final String TABLE_USER = "User";		// 테이블 이름
	
	private static final String PRIMARY_KEY_ID = "id";			// Friend 테이블 Columns 정의 시작
	private static final String USER_NAME =   "name";
	private static final String USER_AGE =    "age";
	private static final String USER_SEX =    "sex";
	private static final String USER_HEIGHT = "height";
	private static final String USER_WEIGHT = "weight";// Friend 테이블 Columns 정의 끝
	/* Friend Table End */
	public static Cursor cursor;
	public static String sName;
	public SQLiteUserListHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/**
	 * 데이터베이스를 생성하는 함수입니다.
	 *  - 이 함수는 어플리케이션이 설치되고 데이터베이스가 불릴때 한번만 실행됩니다.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// createTableQuery = CREATE TABLE Friend(id INTEGER PRIMARY KEY, name TEXT, email TEXT, phone TEXT)
		String createTableQuery = "CREATE TABLE " + TABLE_USER 
				                    + "(" 
									
				                    + PRIMARY_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
									+ USER_NAME   + " TEXT UNIQUE," 
									+ USER_AGE    + " TEXT,"
									+ USER_SEX    + " TEXT," 
									+ USER_HEIGHT + " TEXT,"
									+ USER_WEIGHT + " TEXT"
									
									+ ")";
		db.execSQL(createTableQuery);
	}

	/**
	 * 데이터베이스의 형태가 변경되면 실행하여 데이터베이스 구조를 변경합니다.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// dropOldVersionQuery = DROP TABLE IF EXISTS FRIEND
		String dropOldVersionQuery = "DROP TABLE IF EXISTS " + TABLE_USER;
		db.execSQL(dropOldVersionQuery);
		// create new database
		onCreate(db);
	}
	
	/**
	 * 데이터베이스 관리를 위해 필요한 함수들을 정의해준다.
	 */
	
	// insert  
	public void insertUser(User user){
		// 읽기와 쓰기가 가능한 데이터베이스를 참조
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 데이터베이스에 넣을 데이터를 Key-Value 형식으로 정의
		ContentValues values = new ContentValues();
		
		values.put(USER_NAME,user.getName());
		values.put(USER_AGE,user.getAge());
		values.put(USER_SEX,user.getSex()); 
		values.put(USER_HEIGHT,user.getHeight());
		values.put(USER_WEIGHT,user.getWeight());
		// insert! 
		
		db.insert(TABLE_USER, null, values);
		db.close();
	}
	 
	// select all
	public List<User> selectAllUser(){
		// 모든 정보를 저장할 리스트를 하나 만들어준다.
		List<User> userList = new ArrayList<User>();
		
		// SelectAllQuery = SELECT * FROM FRIEND
		String selectAllQuery = "SELECT * FROM " + TABLE_USER+" DESC";
		// 읽기와 쓰기가 가능한 데이터베이스를 참조
		SQLiteDatabase db = this.getWritableDatabase();
		// Cursor 객체 생성
		cursor = db.rawQuery(selectAllQuery, null);
		
		// cursor를 이용하여 가져온 데이터를 읽어온다.
		if ( cursor.moveToFirst() ){	// 데이터베이스에 데이터가 존재한다면...
			do {
				// 현재 데이터가 가르키고 있는 데이터를 이용하여 데이터 객체를 생성하고 리스트에 추가를 한다.
				userList.add(new User(
								Integer.parseInt(cursor.getString(0)), 
								cursor.getString(1), 
								cursor.getString(2),
								cursor.getString(3),
								cursor.getString(4),
								cursor.getString(5)
								));
			} while ( cursor.moveToNext() );
		}
		cursor.close();

		return userList;
	}
	
	public List<User> selectFindUser(User user){
		// 모든 정보를 저장할 리스트를 하나 만들어준다.
		List<User> userList = new ArrayList<User>();
		// SelectAllQuery = SELECT * FROM FRIEND
	
		// 읽기와 쓰기가 가능한 데이터베이스를 참조
		SQLiteDatabase db = this.getWritableDatabase();
		// Cursor 객체 생성
		Cursor cursor = db.rawQuery( "select * from "+TABLE_USER+" where "+PRIMARY_KEY_ID+"=" + "'" + user.getId() + "'" , null);
		
		// cursor를 이용하여 가져온 데이터를 읽어온다.
		if ( cursor.moveToFirst() ){	// 데이터베이스에 데이터가 존재한다면...
			//do {
				// 현재 데이터가 가르키고 있는 데이터를 이용하여 데이터 객체를 생성하고 리스트에 추가를 한다.
				userList.add(new User(
								Integer.parseInt(cursor.getString(0)), 
								cursor.getString(1), 
								cursor.getString(2),
								cursor.getString(3),
								cursor.getString(4),
								cursor.getString(5)
								));
			//} while ( cursor.moveToNext() );
		}
		cursor.close();

		return userList;
	}
	
	// update : update return 값 : the number of rows affected
	public int updateUser(User user){
		int updateRow;		// return value
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		//values.put(USER_NAME, user.getName());
		values.put(USER_AGE, user.getAge());
		values.put(USER_SEX, user.getSex()); 
		values.put(USER_HEIGHT, user.getHeight());
		values.put(USER_WEIGHT, user.getWeight());
		// update = ... WHERE id = "getId()"
		updateRow = db.update(TABLE_USER, values, PRIMARY_KEY_ID + " = ?", new String[]{String.valueOf(user.getId())});
		
		//  String sql = "update " + TABLE_USER + " set voca = '" + voca +"' where id = "+index +";";
	  //      db.execSQL(sql);
		db.close();
		return updateRow;
	}
	
	// delete
	public void deleteUser(User user){
			SQLiteDatabase db = this.getWritableDatabase();
		
		// delete = ... WHERE id = "getId()"
		 db.delete(TABLE_USER, PRIMARY_KEY_ID + " = ?", new String[]{String.valueOf(user.getId())});
		//	String sql = "delete from " + TABLE_USER + " where NAME = "+user.getName()+";";
	    //   db.execSQL(sql);
		db.close();
	}

	    
 
}