package com.iust.fandogh;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.iust.fandogh.controllers.ClientNetworkController;
import com.iust.fandogh.controllers.DatabaseController;
import com.iust.fandogh.controllers.GameController;
import com.iust.fandogh.controllers.ServerNetworkController;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ResultActivity extends Activity {
	
	/**
	 * insert fields views in page
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		
		if(ClientNetworkController.cnc != null) {
			ClientNetworkController.cnc.setActivity(this);
			ClientNetworkController.cnc.requestAllFields();
		} else {  
			ServerNetworkController.snc.setActivity(this);
			importScores(GameController.gc.getAllFields());
		}
	}
	
	public void importScores(String fields) {
		TableLayout hh = (TableLayout)findViewById(R.id.injaEzaf);
		for (String player : fields.split("#")) {
			TableRow pl = new TableRow(this);
			LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0);
			pl.setLayoutParams(rowParams);
			
			Log.d("adasd", player.split(",")[0]+ "dasdasdasd");
			Log.d("adasd", player.split(",")[1]+ "dasdasdasd");
			Log.d("adasd", player.split(",")[2]+ "dasdasdasd");
			Log.d("adasd", player.split(",")[3]+ "dasdasdasd");
			
			TextView name;
			TableRow.LayoutParams nameParams;
			
			name = new TextView(this);
			name.setText(player.split(",")[2]);
			name.setTextColor(Color.BLUE);
			name.setGravity(Gravity.RIGHT);
			nameParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 60f);
			if(DatabaseController.dbc.isItThere(DatabaseController.colorInt, player.split(",")[2]))
				name.setBackgroundColor(Color.GREEN);
			else
				name.setBackgroundColor(Color.RED);
			pl.addView(name, nameParams);
			
			name = new TextView(this);
			name.setText(player.split(",")[3]);
			name.setTextColor(Color.BLUE);
			name.setGravity(Gravity.RIGHT);
			nameParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 60f);
			if(DatabaseController.dbc.isItThere(DatabaseController.fruitInt, player.split(",")[3]))
				name.setBackgroundColor(Color.GREEN);
			else
				name.setBackgroundColor(Color.RED);
			pl.addView(name, nameParams);

			
			name = new TextView(this);
			name.setText(player.split(",")[1]);
			name.setTextColor(Color.BLUE);
			name.setGravity(Gravity.RIGHT);
			nameParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 60f);
			if(DatabaseController.dbc.isItThere(DatabaseController.fnameInt, player.split(",")[1]))
				name.setBackgroundColor(Color.GREEN);
			else
				name.setBackgroundColor(Color.RED);
			pl.addView(name, nameParams);
			
			
			name = new TextView(this);
			name.setText(player.split(",")[0]);
			name.setTextColor(Color.BLUE);
			name.setGravity(Gravity.RIGHT);
			nameParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 60f);
			pl.addView(name, nameParams);
			
			
			hh.addView(pl);
//		for (String player : fields.split("#")) {
//			LinearLayout pl = new LinearLayout(this.getApplicationContext());
//			TextView tmpTextView;
//			
//			tmpTextView = new TextView(getApplicationContext());
//			tmpTextView.setText(player.split(",")[0]);
//			tmpTextView.setTextSize(40f);
//			pl.addView(tmpTextView);
//			
//			tmpTextView = new TextView(getApplicationContext());
//			tmpTextView.setText(player.split(",")[0]);
//			tmpTextView.setTextSize(40f);
//			if(DatabaseController.dbc.isItThere(DatabaseController.fnameInt, player.split(",")[0]))
//				tmpTextView.setBackgroundColor(Color.GREEN);
//			else
//				tmpTextView.setBackgroundColor(Color.GREEN);
//			pl.addView(tmpTextView);
//			
//			tmpTextView = new TextView(getApplicationContext());
//			tmpTextView.setText(player.split(",")[0]);
//			tmpTextView.setTextSize(40f);
//			if(DatabaseController.dbc.isItThere(DatabaseController.fnameInt, player.split(",")[0]))
//				tmpTextView.setBackgroundColor(Color.GREEN);
//			else
//				tmpTextView.setBackgroundColor(Color.GREEN);
//			pl.addView(tmpTextView);
//			
//			tmpTextView = new TextView(getApplicationContext());
//			tmpTextView.setText(player.split(",")[0]);
//			tmpTextView.setTextSize(40f);
//			if(DatabaseController.dbc.isItThere(DatabaseController.fnameInt, player.split(",")[0]))
//				tmpTextView.setBackgroundColor(Color.GREEN);
//			else
//				tmpTextView.setBackgroundColor(Color.GREEN);
//			pl.addView(tmpTextView);
//			((LinearLayout)findViewById(R.id.table)).addView(pl);
//			}
		}
	}
}
