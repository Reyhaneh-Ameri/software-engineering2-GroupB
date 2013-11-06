package com.example.esmfamil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DatabaseHelper{
	public static final int dbversion = 11;
	public static final String dbname = "esmfamil";

	public static final String fnameTable = "fname";
	public static final String colFnameVal = "val";

	public static final String lnameTable = "lname";
	public static final String colLnameVal = "val";

	public static final String fruitTable = "fruit";
	public static final String colFruitVal = "val";

	public static final String flowerTable = "flower";
	public static final String colFlowerVal = "val";

	public static final String colorTable = "color";
	public static final String colColorVal = "val";

	public static final String cityTable = "city";
	public static final String colCityVal = "val";

	public static final String countryTable = "country";
	public static final String colCountryVal = "val";

	private SqliteHelper sh;
	private SQLiteDatabase sd;
	
	public DatabaseHelper open(Context context) {
		sh = new SqliteHelper(context, dbname, null, dbversion);
		sd = sh.getWritableDatabase();
		return this;
	}

	public Cursor getAllItems() {
		return sd.rawQuery("select * from " + fnameTable, null);
	}

	public void insertRecord(int recType, String recValue){
		ContentValues cv=new ContentValues();
		cv.put("val", recValue);
		switch (recType) {
		case 1:
			sd.insert(fnameTable, null, cv);
			break;
		case 2:
			sd.insert(lnameTable, null, cv);
			break;
		case 3:
			sd.insert(fruitTable, null, cv);
			break;
		case 4:
			sd.insert(flowerTable, null, cv);
			break;
		case 5:
			sd.insert(colorTable, null, cv);
			break;
		case 6:
			sd.insert(cityTable, null, cv);
			break;
		case 7:
			sd.insert(countryTable, null, cv);
			break;
		default:
			break;
		}
	}

	private static class SqliteHelper extends SQLiteOpenHelper {
		public SqliteHelper(Context context, String name, CursorFactory factory,int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + fnameTable + "( " 
					+ colFnameVal + " VARCHAR (30)) "); 
			db.execSQL("CREATE TABLE " + lnameTable + "( " 
					+ colLnameVal + " VARCHAR (30)) ");
			db.execSQL("CREATE TABLE " + fruitTable + "( " 
					+ colFruitVal + " VARCHAR (30)) "); 
			db.execSQL("CREATE TABLE " + flowerTable + "( " 
					+ colFlowerVal + " VARCHAR (30)) "); 
			db.execSQL("CREATE TABLE " + colorTable + "( " 
					+ colColorVal + " VARCHAR (30)) "); 
			db.execSQL("CREATE TABLE " + cityTable + "( " 
					+ colCityVal + " VARCHAR (30)) ");
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		}
	}

}
