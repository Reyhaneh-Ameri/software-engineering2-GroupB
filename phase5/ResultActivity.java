package com.iust.fandogh;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.iust.fandogh.controllers.ClientNetworkController;
import com.iust.fandogh.controllers.DatabaseController;
import com.iust.fandogh.controllers.GameController;
import com.iust.fandogh.controllers.ServerNetworkController;
import com.iust.fandogh.messages.GameMSG;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
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
		} else {  
			ServerNetworkController.snc.setActivity(this);
		}
		
		HashMap<String, HashMap<Integer, String>> fields = 
				(HashMap<String, HashMap<Integer,String>>)getIntent().getSerializableExtra("results");
		HashMap<String, HashMap<Integer, Integer>> points = 
				(HashMap<String, HashMap<Integer,Integer>>)getIntent().getSerializableExtra("scores");
		
		int ind = 1;
		int score;
		for (String name : fields.keySet()) {
			score = 0;
			for (Integer fnum : fields.get(name).keySet()) {
				switch (ind) {
				case 1:
					if(fnum==DatabaseController.fnameInt) {
						((TextView)findViewById(R.id.esm1)).setText(fields.get(name).get(fnum));
						if(points.get(name).get(fnum)>0)
							((TextView)findViewById(R.id.esm1)).setTextColor(Color.GREEN);
					} else if(fnum==DatabaseController.flowerInt) {
						((TextView)findViewById(R.id.gol1)).setText(fields.get(name).get(fnum));
						if(points.get(name).get(fnum)>0)
							((TextView)findViewById(R.id.gol1)).setTextColor(Color.GREEN);
					} else if(fnum==DatabaseController.fruitInt) {
						((TextView)findViewById(R.id.mive1)).setText(fields.get(name).get(fnum));
						if(points.get(name).get(fnum)>0)
							((TextView)findViewById(R.id.mive1)).setTextColor(Color.GREEN);
					} else if(fnum==DatabaseController.colorInt) {
						((TextView)findViewById(R.id.rang1)).setText(fields.get(name).get(fnum));
						if(points.get(name).get(fnum)>0)
							((TextView)findViewById(R.id.rang1)).setTextColor(Color.GREEN);
					}
					break;
				case 2:
					if(fnum==DatabaseController.fnameInt) {
						((TextView)findViewById(R.id.esm2)).setText(fields.get(name).get(fnum));
						if(points.get(name).get(fnum)>0)
							((TextView)findViewById(R.id.esm2)).setTextColor(Color.GREEN);
					} else if(fnum==DatabaseController.flowerInt) {
						((TextView)findViewById(R.id.gol2)).setText(fields.get(name).get(fnum));
						if(points.get(name).get(fnum)>0)
							((TextView)findViewById(R.id.gol2)).setTextColor(Color.GREEN);
					} else if(fnum==DatabaseController.fruitInt) {
						((TextView)findViewById(R.id.mive2)).setText(fields.get(name).get(fnum));
						if(points.get(name).get(fnum)>0)
							((TextView)findViewById(R.id.mive2)).setTextColor(Color.GREEN);
					} else if(fnum==DatabaseController.colorInt) {
						((TextView)findViewById(R.id.rang2)).setText(fields.get(name).get(fnum));
						if(points.get(name).get(fnum)>0)
							((TextView)findViewById(R.id.rang2)).setTextColor(Color.GREEN);
					}
					break;
				case 3:
					if(fnum==DatabaseController.fnameInt) {
						((TextView)findViewById(R.id.esm3)).setText(fields.get(name).get(fnum));
						if(points.get(name).get(fnum)>0)
							((TextView)findViewById(R.id.esm3)).setTextColor(Color.GREEN);
					} else if(fnum==DatabaseController.flowerInt) {
						((TextView)findViewById(R.id.gol3)).setText(fields.get(name).get(fnum));
						if(points.get(name).get(fnum)>0)
							((TextView)findViewById(R.id.gol3)).setTextColor(Color.GREEN);
					} else if(fnum==DatabaseController.fruitInt) {
						((TextView)findViewById(R.id.mive3)).setText(fields.get(name).get(fnum));
						if(points.get(name).get(fnum)>0)
							((TextView)findViewById(R.id.mive3)).setTextColor(Color.GREEN);
					} else if(fnum==DatabaseController.colorInt) {
						((TextView)findViewById(R.id.rang3)).setText(fields.get(name).get(fnum));
						if(points.get(name).get(fnum)>0)
							((TextView)findViewById(R.id.rang3)).setTextColor(Color.GREEN);
					}
					break;
				}
				score += points.get(name).get(fnum);
			}
			
			switch (ind) {
			case 1:
				((TextView)findViewById(R.id.user1)).setText(name);
				((TextView)findViewById(R.id.emtiaz1)).setText(Integer.toString(score));
				break;
			case 2:
				((TextView)findViewById(R.id.user2)).setText(name);
				((TextView)findViewById(R.id.emtiaz3)).setText(Integer.toString(score));
				break;
			case 3:
				((TextView)findViewById(R.id.user3)).setText(name);
				((TextView)findViewById(R.id.emtiaz3)).setText(Integer.toString(score));
				break;
			}
			
			ind++;
		}
		
	}
	
//	TableLayout hh = (TableLayout)findViewById(R.id.injaEzaf);
//	for (String player : fields.split("#")) {
//		TableRow pl = new TableRow(this);
//		LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0);
//		pl.setLayoutParams(rowParams);
//		
//		Log.d("adasd", player.split(",")[0]+ "dasdasdasd");
//		Log.d("adasd", player.split(",")[1]+ "dasdasdasd");
//		Log.d("adasd", player.split(",")[2]+ "dasdasdasd");
//		Log.d("adasd", player.split(",")[3]+ "dasdasdasd");
//		
//		TextView name;
//		TableRow.LayoutParams nameParams;
//		
//		name = new TextView(this);
//		name.setText(player.split(",")[2]);
//		name.setTextColor(Color.BLUE);
//		name.setGravity(Gravity.RIGHT);
//		nameParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 60f);
//		if(DatabaseController.dbc.isItThere(DatabaseController.colorInt, player.split(",")[2]))
//			name.setTextColor(Color.GREEN);
//		else
//			name.setTextColor(Color.RED);
//		pl.addView(name, nameParams);
//		
//		name = new TextView(this);
//		name.setText(player.split(",")[3]);
//		name.setTextColor(Color.BLUE);
//		name.setGravity(Gravity.RIGHT);
//		nameParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 60f);
//		if(DatabaseController.dbc.isItThere(DatabaseController.fruitInt, player.split(",")[3]))
//			name.setTextColor(Color.GREEN);
//		else
//			name.setTextColor(Color.RED);
//		pl.addView(name, nameParams);
//
//		
//		name = new TextView(this);
//		name.setText(player.split(",")[1]);
//		name.setTextColor(Color.BLUE);
//		name.setGravity(Gravity.RIGHT);
//		nameParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 60f);
//		if(DatabaseController.dbc.isItThere(DatabaseController.fnameInt, player.split(",")[1]))
//			name.setTextColor(Color.GREEN);
//		else
//			name.setTextColor(Color.RED);
//		pl.addView(name, nameParams);
//		
//		
//		name = new TextView(this);
//		name.setText(player.split(",")[0]);
//		name.setTextColor(Color.BLUE);
//		name.setGravity(Gravity.RIGHT);
//		nameParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 60f);
//		pl.addView(name, nameParams);
//		
//		
//		hh.addView(pl);
//////////////////////////////////////////////////////////////
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
//				tmpTextView.setTextColor(Color.GREEN);
//			else
//				tmpTextView.setTextColor(Color.GREEN);
//			pl.addView(tmpTextView);
//			
//			tmpTextView = new TextView(getApplicationContext());
//			tmpTextView.setText(player.split(",")[0]);
//			tmpTextView.setTextSize(40f);
//			if(DatabaseController.dbc.isItThere(DatabaseController.fnameInt, player.split(",")[0]))
//				tmpTextView.setTextColor(Color.GREEN);
//			else
//				tmpTextView.setTextColor(Color.GREEN);
//			pl.addView(tmpTextView);
//			
//			tmpTextView = new TextView(getApplicationContext());
//			tmpTextView.setText(player.split(",")[0]);
//			tmpTextView.setTextSize(40f);
//			if(DatabaseController.dbc.isItThere(DatabaseController.fnameInt, player.split(",")[0]))
//				tmpTextView.setTextColor(Color.GREEN);
//			else
//				tmpTextView.setTextColor(Color.GREEN);
//			pl.addView(tmpTextView);
//			((LinearLayout)findViewById(R.id.table)).addView(pl);
//			}
	
	public void nextRound(View arg0) {
		if(ClientNetworkController.cnc != null) {
//			U cant
		} else {
			if(GameController.gc.getRound()>0) {
				GameController.gc.nextRound();
				
				Intent intent = new Intent(this, GameActivity.class);
				intent.putExtra("modes", GameController.gc.getModes());
				intent.putExtra("rounds", GameController.gc.getRound());
				intent.putExtra("alphabets", GameController.gc.getPlayer(0).getAllCharsCount());
				intent.putExtra("score", GameController.gc.getPlayer(0).getScore());
				
				startActivity(intent);
				this.finish();
			} else {
				Intent intent = new Intent(this, FinalActivity.class);
		    	intent.putExtra("final",GameController.gc.finalPage());
				
				startActivity(intent);
				this.finish();
			}
		}
	}
	
	public void startNextOne(GameMSG msg) {
		Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("modes", msg.getModes());
    	intent.putExtra("rounds", msg.getRounds());
    	intent.putExtra("alphabets", msg.getDashbordAlphabets());
    	intent.putExtra("score", msg.getScore());
		
		startActivity(intent);
		this.finish();
	}
	
	public void finalPage(ArrayList<String> name) {
		Intent intent = new Intent(this, FinalActivity.class);
    	intent.putExtra("final", name);
		
		startActivity(intent);
		this.finish();
	}
}
