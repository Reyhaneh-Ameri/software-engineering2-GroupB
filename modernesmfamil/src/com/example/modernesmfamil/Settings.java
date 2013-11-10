/**
 * This is the main activity file, which all the main functions of the
 * settings page is handled in this file.
 * @author Amin Fallahi, Mahsa Asadi
 */
package com.example.esmfamil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import android.app.Activity;
import android.database.Cursor;
import com.example.esmfamil.SoundHandler;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug.IntToString;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;
import com.example.esmfamil.MusicHandler;
import android.util.Log;
import com.example.esmfamil.DatabaseHelper;
/**
 * Settings class which extends android activity class
 * @author Amin Fallahi, Mahsa Asadi
 */
public class Settings extends Activity {


	MusicHandler bgMusic;
	SoundHandler sound;
	protected DatabaseHelper db;
	/*	public void pplayWin(){
		sound=new SoundHandler();
		sound.playWin(this);
	}*/
	private static int RESULT_LOAD_IMAGE = 1;
	/**
	 * onCreate is overloaded for all the functions that are done after
	 * an instance of Settings has been created and the settings page is 
	 * launched on the device.
	 * @author Amin Fallahi, Mahsa Asadi
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		//int a=SettingsHandler.getSoundStatus();
		//Log.d("ssss",IntToString(a));
		bgMusic=new MusicHandler(this);
		bgMusic.setMusicOn();
		sound=new SoundHandler();


		db=new DatabaseHelper();
		db.open(this);
		//db.insertRecord(1,"Mahsa");
		Log.d("ssss","Mahsa >:P");

		Log.d("ssss","android.resource://" + getPackageName() + "/"+R.raw.boynames);

		
		Scanner s = new Scanner(getResources().openRawResource(R.raw.fruitdb));
	    while (s.hasNext()) {
	        String word = s.next();
	        Log.d("ssss",word);
	        db.insertRecord(DatabaseHelper.fruitInt, word);
	    }

		
		
		//SettingsHandler.init(db);
		//Log.d("ssss",IntToString(SettingsHandler.getSoundStatus()));
		//VibrationHandler.longVibra(this);

		Button buttonLoadImage = (Button)findViewById(R.id.bbb);
		buttonLoadImage.setOnClickListener(new View.OnClickListener() {
			/**
			 * onClick for buttonLoadImage is overrided in order to let the user
			 * choose an avatar from gallery.
			 * @author Amin Fallahi, Mahsa Asadi
			 */
			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});





/*		Log.d("ssss","1");
		Cursor c=db.getAllItems();
		Log.d("ssss","2");
		if (c.moveToLast())
			Log.d("ssss","3");
			Log.d("ssss",c.getString(0));
			Log.d("ssss","4");
	*/
		




		ToggleButton toggleSound = (ToggleButton) findViewById(R.id.togSound);
		if (SettingsHandler.getSoundStatus()==1)
			toggleSound.setChecked(true);
		else
			toggleSound.setChecked(false);			
		toggleSound.setOnClickListener(new View.OnClickListener() {
			/**
			 * when toggleButton is clicked we change the status of the 
			 * application Music by changing appMusic variable.
			 * @author Amin Fallahi, Mahsa Asadi
			 */
			@Override
			public void onClick(View arg0) {
				if (SettingsHandler.getSoundStatus()==1){
					//pplayWin();
				}
				else{
					SettingsHandler.setSoundStatus(1);			
				}

			}
		});


		ToggleButton toggleMusic = (ToggleButton) findViewById(R.id.togMusic);
		if (SettingsHandler.getMusicStatus()==1)
			toggleMusic.setChecked(true);
		else
			toggleMusic.setChecked(false);			
		toggleMusic.setOnClickListener(new View.OnClickListener() {
			/**
			 * when toggleButton is clicked we change the status of the 
			 * application Music by changing appMusic variable.
			 * @author Amin Fallahi, Mahsa Asadi
			 */
			@Override
			public void onClick(View arg0) {
				if (SettingsHandler.getMusicStatus()==1){
					SettingsHandler.setMusicStatus(0);
					bgMusic.setMusicOff();
				}
				else{
					SettingsHandler.setMusicStatus(1);
					bgMusic.setMusicOn();
				}

			}
		});

	}




	/**
	 * onActivityResult for settings activity is overloaded in order to let
	 * the user avatar be shown in the imageview after it has been chosen by
	 * user from gallery
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();


			ImageView imageView = (ImageView) findViewById(R.id.showAvatar);
			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

			SettingsHandler.setAvatarImg(imageView);

		}


	}
	/**
	 * onDestroy is overloaded and set to call writeFiles method from SettingsHandler
	 * class in order for the settings to be saved into appropriate files on the device
	 * @author Amin Fallahi, Mahsa Asadi
	 */
	@Override
	protected void onDestroy() {
		//		SettingsHandler.writeFiles();
		super.onDestroy();
	}
}
