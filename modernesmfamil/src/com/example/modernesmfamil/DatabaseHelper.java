package com.example.esmfamil;

import java.util.StringTokenizer;

import android.R.bool;
import android.R.color;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import android.view.ViewDebug.IntToString;

public class DatabaseHelper{
	public static final int dbversion = 14;
	public static final String dbname = "esmfamil";
//	public static final String[] colInt={"",""};

	public static final String settingsTable = "settings";
	public static final String colSettingsVal = "val";

	public static final String fnameTable = "fname";
	public static final String colFnameVal = "val";
	public static final int fnameInt = 1;

	public static final String lnameTable = "lname";
	public static final String colLnameVal = "val";
	public static final int lnameInt = 2;

	public static final String fruitTable = "fruit";
	public static final String colFruitVal = "val";
	public static final int fruitInt = 3;

	public static final String flowerTable = "flower";
	public static final String colFlowerVal = "val";
	public static final int flowerInt = 4;

	public static final String colorTable = "color";
	public static final String colColorVal = "val";
	public static final int colorInt = 5;

	public static final String cityTable = "city";
	public static final String colCityVal = "val";
	public static final int cityInt = 6;

	public static final String countryTable = "country";
	public static final String colCountryVal = "val";
	public static final int countryInt = 7;

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
	
	public int[] getSettingsValues(){
		Cursor c;
		int[] gsv=new int[3];
		c=sd.rawQuery("select * from "+settingsTable,null );

		c.moveToFirst();
		gsv[0]=c.getInt(0);
		c.moveToNext();
		gsv[1]=c.getInt(0);
		c.moveToNext();
		gsv[2]=c.getInt(0);
		return gsv;
	}
	public void setSettingsValues(int v, int m, int s){
		sd.rawQuery("truncate table "+settingsTable,null);
		sd.rawQuery("insert into "+settingsTable+" ("+v+")",null);
		sd.rawQuery("insert into "+settingsTable+" ("+m+")",null);
		sd.rawQuery("insert into "+settingsTable+" ("+s+")",null);
	}
	
	public boolean isItThere(int fieldInt, String inVal){
		Cursor c;
		switch (fieldInt) {
		case fnameInt:
			c=sd.rawQuery("select * from "+fnameTable, null);
			c.moveToFirst();
			if (c.getString(0)!=inVal)
				return false;
			else
				return true;
		case lnameInt:
			c=sd.rawQuery("select * from "+lnameTable, null);
			c.moveToFirst();
			if (c.getString(0)!=inVal)
				return false;
			else
				return true;
		case fruitInt:
			c=sd.rawQuery("select * from "+fruitTable, null);
			c.moveToFirst();
			if (c.getString(0)!=inVal)
				return false;
			else
				return true;
		case flowerInt:
			c=sd.rawQuery("select * from "+flowerTable, null);
			c.moveToFirst();
			if (c.getString(0)!=inVal)
				return false;
			else
				return true;
		case colorInt:
			c=sd.rawQuery("select * from "+colorTable, null);
			c.moveToFirst();
			if (c.getString(0)!=inVal)
				return false;
			else
				return true;
		case cityInt:
			c=sd.rawQuery("select * from "+cityTable, null);
			c.moveToFirst();
			if (c.getString(0)!=inVal)
				return false;
			else
				return true;
		case countryInt:
			c=sd.rawQuery("select * from "+countryTable, null);
			c.moveToFirst();
			if (c.getString(0)!=inVal)
				return false;
			else
				return true;
		default:
			return false;
		}
	}

	public void insertRecord(int recType, String recValue){
		ContentValues cv=new ContentValues();
		cv.put("val", recValue);
		switch (recType) {
		case fnameInt:
			sd.insert(fnameTable, null, cv);
			break;
		case lnameInt:
			sd.insert(lnameTable, null, cv);
			break;
		case fruitInt:
			sd.insert(fruitTable, null, cv);
			break;
		case flowerInt:
			sd.insert(flowerTable, null, cv);
			break;
		case colorInt:
			sd.insert(colorTable, null, cv);
			break;
		case cityInt:
			sd.insert(cityTable, null, cv);
			break;
		case countryInt:
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
			db.execSQL("CREATE TABLE " + settingsTable + "( " 
					+ colSettingsVal + " INT )");
			db.execSQL("insert into "+settingsTable+" values(1)");
			db.execSQL("insert into "+settingsTable+" values(1)");
			db.execSQL("insert into "+settingsTable+" values(1)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			db.execSQL("DROP TABLE " + fnameTable); 
			db.execSQL("DROP TABLE " + lnameTable);
			db.execSQL("DROP TABLE " + fruitTable); 
			db.execSQL("DROP TABLE " + flowerTable); 
			db.execSQL("DROP TABLE " + colorTable); 
			db.execSQL("DROP TABLE " + cityTable);
			db.execSQL("DROP TABLE " + settingsTable);
			onCreate(db);
		}
	}

}
