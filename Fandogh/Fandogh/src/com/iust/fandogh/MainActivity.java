package com.iust.fandogh;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import static com.iust.fandogh.entity.AlphabetView.*;

import com.iust.fandogh.controllers.DatabaseController;
import com.iust.fandogh.controllers.MusicController;
import com.iust.fandogh.controllers.ServerNetworkController;
import com.iust.fandogh.controllers.SettingsController;
import com.iust.fandogh.controllers.SoundController;
import com.iust.fandogh.dialogs.*;
import com.iust.fandogh.entity.AlphabetView;

import com.newrelic.agent.android.NewRelic;

/**
 * First page shown in program
 * Have icons to navigate other parts of program
 * Dynamically set alphabet icons in screen
 * TODO: algorithm is not good to set position of alphabet icons in page
 * TODO: nickname can't be empty!!!
 * @author FERi
 */
public class MainActivity extends FragmentActivity 
	implements JoinDialog.NoticeDialogListener, 
	JoinErrorDialog.NoticeDialogListener, 
	ServerErrorDialog.NoticeDialogListener {
	
	public final static String tag = "FANDOGH";
	public final static String NICKNAME = "nickname";
	public final static String IP = "ip";
	
	TextView nickname;
	/**
	 * Set all components position and size in screen
	 * Add functionality to components
	 * @author FERi
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		NewRelic.withApplicationToken("AA50c26040e9691af72f7b41325b33dd4dda29ed7a").start(getApplication());
		 
//		INIT
		new MusicController(this);
		new SoundController();
		
		new DatabaseController();
		DatabaseController.dbc.open(this);			
		long duration=0;
		for (int i=0; i<100; i++){
			long startTime=System.nanoTime();
			DatabaseController.dbc.getAllItems();
			long endTime = System.nanoTime();
			duration=endTime-startTime;
			Log.d("potato2", String.valueOf(duration));
		}
		SettingsController.init(DatabaseController.dbc);
		if (SettingsController.getMusicStatus()==1)
			MusicController.mc.setMusicOn();		
		if (SettingsController.getDbSaveStatus()!=1){
			Scanner s = new Scanner(getResources().openRawResource(R.raw.fruitdb));
		    while (s.hasNext()) {
		        String word = s.next();
		        DatabaseController.dbc.insertRecord(DatabaseController.fruitInt, word);
		    }
			s = new Scanner(getResources().openRawResource(R.raw.colordb));
		    while (s.hasNext()) {
		        String word = s.next();
		        DatabaseController.dbc.insertRecord(DatabaseController.colorInt, word);
		    }
			s = new Scanner(getResources().openRawResource(R.raw.tempnames));
		    while (s.hasNext()) {
		        String word = s.next();
		        DatabaseController.dbc.insertRecord(DatabaseController.fnameInt, word);
		    }
		    SettingsController.setDbSavedStatus(1);
		    SettingsController.saveSettings(DatabaseController.dbc);
		}
//		
		
		
//		TODO: its trash
		AbsoluteLayout layout = (AbsoluteLayout)findViewById(R.id.mainLayout);
		
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int densityDpi = (int)(metrics.density);
		
		switch (densityDpi) {
		case 4:
			ImageSize = 80;
			break;
		case 3:
			ImageSize = 60;
			break;
		default:
			ImageSize = 40;
			break;
		}
		
		int offsetX = (width - 4*ImageSize)/5;
		int offsetY = (height- 10*ImageSize -40)/9;
		while(offsetX > 15 && offsetY > 30) {
			ImageSize += 5;
			
			offsetX = (width - 4*ImageSize)/5;
			offsetY = (height- 10*ImageSize -40)/9;
		}
		
		
		Random rand = new Random();
		int random;
		AlphabetView tmpAlphabetView;
		ImageView tmpImageView;
		LayoutParams tmpLayoutParams;
		ArrayList<Integer> tmpImages = new ArrayList<Integer>();
		ArrayList<Character> tmpCharacters = new ArrayList<Character>();
		for (int i=0; i<AlphabetChars.length; i++) {
			tmpImages.add(AlphabetImages[i]);
			tmpCharacters.add(AlphabetChars[i]);
		}
		
		for (int i=0; i< 4; i++)
			for (int j = 0; j < 8; j++) {
				try {
					random = rand.nextInt(tmpImages.size());
					tmpAlphabetView = new AlphabetView(this, tmpImages.remove(random), tmpCharacters.remove(random), 10, 10);
//					tmpAlphabetView.setRotation(rand.nextInt(360));
					tmpLayoutParams = new LayoutParams(ImageSize, ImageSize, 
							(i*(ImageSize+offsetX)+offsetX)+rand.nextInt(offsetX/2), 
							(j*(ImageSize+offsetY)+rand.nextInt(offsetY/2))+(ImageSize+10));
					tmpAlphabetView.setOnClickListener(new alphabetListener());
					layout.addView(tmpAlphabetView, tmpLayoutParams);
				} catch (Exception e) {
					break;
				}
			}
		
//		Nickname
		nickname = new TextView(this);
		nickname.setBackgroundResource(R.drawable.paper);
		nickname.setText("جعفر");
		tmpLayoutParams = new LayoutParams(ImageSize*3, ImageSize, width/2-(3*ImageSize/2), 10);
		nickname.setLayoutParams(tmpLayoutParams);
		nickname.setTextColor(Color.BLUE);
		nickname.setGravity(Gravity.CENTER);
		nickname.setTextSize(20f);
		nickname.setOnClickListener(new NicknameReset());
		layout.addView(nickname,tmpLayoutParams);
		
//	Server
		tmpImageView = new ImageView(this);
		tmpImageView.setImageResource(R.drawable.server);
		tmpLayoutParams = new LayoutParams(ImageSize, ImageSize, 
				width/2- 2*ImageSize, height-ImageSize-30);
		tmpImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(nickname.getText().equals(""))
					return;
				
				if(ServerNetworkController.getIPv4Address() == null) {
					ServerErrorDialog sed = new ServerErrorDialog();
					sed.show(getSupportFragmentManager(), "Error");
					return;
				}
				
				Intent intent = new Intent(MainActivity.this, ServerActivity.class);
				intent.putExtra(MainActivity.NICKNAME, ""+nickname.getText());
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
		layout.addView(tmpImageView, tmpLayoutParams);
		
//	Client
		tmpImageView = new ImageView(this);
		tmpImageView.setImageResource(R.drawable.join);
		tmpLayoutParams = new LayoutParams(ImageSize, ImageSize, 
				width/2- ImageSize, height- ImageSize- 30);
		tmpImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(nickname.getText().equals(""))
					return;
				
				JoinDialog jd = new JoinDialog();
				jd.show(MainActivity.this.getSupportFragmentManager(), "ServerAddress");
			}
		});
		layout.addView(tmpImageView, tmpLayoutParams);
		
//	Help
		tmpImageView = new ImageView(this);
		tmpImageView.setImageResource(R.drawable.help);
		tmpLayoutParams = new LayoutParams(ImageSize, ImageSize, 
				width/2 , height- ImageSize- 30);
		tmpImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				nickname.setText("help");
			}
		});
		layout.addView(tmpImageView, tmpLayoutParams);
		
//	Setting
		tmpImageView = new ImageView(this);
		tmpImageView.setImageResource(R.drawable.settings);
		tmpLayoutParams = new LayoutParams(ImageSize, ImageSize, 
				width/2 + ImageSize , height- ImageSize- 30);
		tmpImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(intent);
			}
		});
		layout.addView(tmpImageView, tmpLayoutParams);
//		
	}
	
	/**
	 * handler for nickname text view
	 * @author Sina Piri,qmarse razmi
	 */
	class NicknameReset implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			nickname.setText("");
		}
	}
	
	/**
	 * handler for alphabetviews
	 * @author Sina Piri,qmarse razmi
	 */
	class alphabetListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			nickname.append(String.valueOf(((AlphabetView)arg0).getCharacter()));
		}
	}
	
	/**
	 * Called when JoinDialog buttons clicked
	 * @author FERi
	 * TODO: should finish this page!?
	 */
	@Override
	public void onDialogPositiveClick(String ip) {
//		String[] tmpIP = ip.split(".");
//		System.out.println(Integer.toString(tmpIP.length));
//		if(tmpIP.length != 4) {
//			JoinErrorDialog jed = new  JoinErrorDialog();
//			jed.show(MainActivity.this.getSupportFragmentManager(), "IP problem");
//			return;
//		}
		
		Intent intent = new Intent(MainActivity.this, JoinActivity.class);
		intent.putExtra(MainActivity.NICKNAME, ""+nickname.getText());
		intent.putExtra(MainActivity.IP, ""+ip);
		startActivity(intent);
		
		MainActivity.this.finish();
	}
	@Override
	public void onDialogNegativeClick() {
	}
	
	/**
	 * Called when ErrorDialog button clicked
	 * @author FERi
	 */
	@Override
	public void onErrorDialogClick() {
	}

}
