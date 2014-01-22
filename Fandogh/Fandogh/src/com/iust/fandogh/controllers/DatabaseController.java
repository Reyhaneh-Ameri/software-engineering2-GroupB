/**
 * @author Mahsa Asadi, Amin Fallahi
 * This is the file to handle all the things with database, including
 * open, connect, insert and read data from database
 */
package com.iust.fandogh.controllers;

import java.util.StringTokenizer;

import com.iust.fandogh.MainActivity;

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

public class DatabaseController{
	public static DatabaseController dbc = null; 
	
	public static final int dbversion = 20;
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
	
	public DatabaseController() {
		DatabaseController.dbc = this;
	}
	
/**
 * @author Mahsa Asadi, Amin Fallahi
 * Opens database for sending queries and working with, a context parameter
 * is passed to say what activity is opening the database.	
 * @param context
 * @return
 */
	public DatabaseController open(Context context) {
		sh = new SqliteHelper(context, dbname, null, dbversion);
		sd = sh.getWritableDatabase();
		
		return this;
	}
/**
 * @author Mahsa Asadi, Amin Fallahi
 * Select everything from a specified database, this is a test query
 * function and is not functional in the main program.
 * @return
 */
	public Cursor getAllItems() {
		return sd.rawQuery("select * from " + colorTable, null);
	}
	/**
	 * @author Mahsa Asadi, Amin Fallahi
	 * Get vibra, music and sound status from settings table in order to
	 * toggle them on and off in settings activity.
	 * @return
	 */
	public int[] getSettingsValues(){
		Cursor c;
		int[] gsv=new int[4];
		c=sd.rawQuery("select * from "+settingsTable,null );

		c.moveToFirst();
		gsv[0]=c.getInt(0);
		c.moveToNext();
		gsv[1]=c.getInt(0);
		c.moveToNext();
		gsv[2]=c.getInt(0);
		c.moveToNext();
		gsv[3]=c.getInt(0);
		return gsv;
	}
	/**
	 * @author Mahsa Asadi, Amin Fallahi
	 * This sets the value for vibra, music and sound and saves them in
	 * settings table when the toggle button is tapped.
	 * @param v
	 * @param m
	 * @param s
	 */
	public void setSettingsValues(int v, int m, int s, int d){
		sd.delete(settingsTable, null, null);
		ContentValues cv=new ContentValues();
		cv.put("val", v);
		sd.insert(settingsTable, null, cv);
		cv.clear();
		cv.put("val", m);
		sd.insert(settingsTable, null, cv);
		cv.clear();
		cv.put("val", s);
		sd.insert(settingsTable, null, cv);	
		cv.clear();
		cv.put("val", d);
		sd.insert(settingsTable, null, cv);	
	}
	/**
	 * @author Mahsa Asadi, Amin Fallahi
	 * Gets a specific table and an string and checks if the string
	 * is in the table or not.
	 * @param fieldInt
	 * @param inVal
	 * @return
	 */
	public boolean isItThere(int fieldInt, String inVal){
		Cursor c;
		switch (fieldInt) {
		case fnameInt:
			c=sd.rawQuery("select val from "+fnameTable+" WHERE val='"+inVal+"'", null);
			if (c.getCount()==0)
				return false;
			else{
				c.moveToFirst();
				Log.d(MainActivity.tag, c.getString(0));
				return true;
			}
		case lnameInt:
			c=sd.rawQuery("select val from "+lnameTable+" WHERE val='"+inVal+"'", null);
			if (c.getCount()==0)
				return false;
			else{
				c.moveToFirst();
				Log.d(MainActivity.tag, c.getString(0));
				return true;
			}
		case fruitInt:
			c=sd.rawQuery("select val from "+fruitTable+" WHERE val='"+inVal+"'", null);
			if (c.getCount()==0)
				return false;
			else{
				c.moveToFirst();
				Log.d(MainActivity.tag, c.getString(0));
				return true;
			}
		case flowerInt:
			c=sd.rawQuery("select val from "+flowerTable+" WHERE val='"+inVal+"'", null);
			if (c.getCount()==0)
				return false;
			else{
				c.moveToFirst();
				Log.d(MainActivity.tag, c.getString(0));
				return true;
			}
		case colorInt:
			c=sd.rawQuery("select val from "+colorTable+" WHERE val='"+inVal+"'", null);
			if (c.getCount()==0)
				return false;
			else{
				c.moveToFirst();
				Log.d(MainActivity.tag, c.getString(0));
				return true;
			}
		case cityInt:
			c=sd.rawQuery("select val from "+cityTable+" WHERE val='"+inVal+"'", null);
			if (c.getCount()==0)
				return false;
			else{
				c.moveToFirst();
				Log.d(MainActivity.tag, c.getString(0));
				return true;
			}
		case countryInt:
			c=sd.rawQuery("select val from "+countryTable+" WHERE val='"+inVal+"'", null);
			if (c.getCount()==0)
				return false;
			else{
				c.moveToFirst();
				Log.d(MainActivity.tag, c.getString(0));
				return true;
			}
		default:
			return false;
		}
	}
	/**
	 * @author Mahsa Asadi, Amin Fallahi
	 * Initializes the settings table by inserting vibra, music and sound
	 * values as 1, meaning ON, to the database.
	 */
	public void initSettingsTable(){
		ContentValues cv=new ContentValues();
		cv.put("val", "1");
		sd.insert(settingsTable, null, cv);
		sd.insert(settingsTable, null, cv);
		sd.insert(settingsTable, null, cv);		
	}
	/**
	 * @author Mahsa Asadi, Amin Fallahi
	 * Inserts a given string in the given table, used for inserting 
	 * data in tables which are used in the game.
	 * @param recType
	 * @param recValue
	 */
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
/**
 * @author Mahsa Asadi, Amin Fallahi
 * Creates all the tables when the program is launched for the first time.
 */
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
			ContentValues cv=new ContentValues();
			cv.put("val", "0");
			db.insert(settingsTable, null, cv);
			db.insert(settingsTable, null, cv);
			db.insert(settingsTable, null, cv);	
			db.insert(settingsTable, null, cv);
			/*db.execSQL("insert into "+settingsTable+" values(1)");
			db.execSQL("insert into "+settingsTable+" values(1)");
			db.execSQL("insert into "+settingsTable+" values(1)");*/
			
		}
/**
 * @author Mahsa Asadi, Amin Fallahi
 * Upgrades the database by removing all the existing tables and
 * recreating them by calling OnCreate function.
 */
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
