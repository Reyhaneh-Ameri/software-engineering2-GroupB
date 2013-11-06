/**
 * This is the main activity file, which all the main functions of the
 * settings page is handled in this file.
 * @author Amin Fallahi, Mahsa Asadi
 */
package com.example.esmfamil;

import android.app.Activity;
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
	int appSound=1;
	int appVibra=1;

	MusicHandler bgMusic;
	SoundHandler sound;
	protected DatabaseHelper db;
	public void pplayWin(){
		sound=new SoundHandler();
		sound.playWin(this);
	}
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

		bgMusic=new MusicHandler(this);
		bgMusic.setMusicOn();
		sound=new SoundHandler();
		

		
		db=new DatabaseHelper();
		Log.d("ssss","ghable open");
		db.open(this);
		Log.d("ssss","bade open");
		db.insertRecord(1,"Mahsa");
		
		VibrationHandler.longVibra(this);
		
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
		
		
		
		
		
//		buttonLoadImage.setText();
		//buttonLoadImage.setText(db.getAllItems().getString(db.getAllItems().getColumnIndex(db.colFnameVal)));
		//Log.d("ssss",getString(db.getAllItems().getColumnIndex(DatabaseHelper.colFnameVal)));
		Log.d("ssss","1");
		int a=db.getAllItems().getColumnIndex(DatabaseHelper.colFnameVal);
		Log.d("ssss","2");
		Log.d("ssss",getString(a));
/*		Log.d("ssss","1");
		String qu="select * from "+DatabaseHelper.fnameTable;
		Log.d("ssss","2");
		Cursor c=db.getAllItems();
		Log.d("ssss","3");
		int iii=c.getColumnIndex(DatabaseHelper.colFnameVal);
		Log.d("ssss","4");
		String sag=c.getString(iii);
		Log.d("ssss","5");*/
		
		
		
		
		
		
		ToggleButton toggleSound = (ToggleButton) findViewById(R.id.togSound);
		toggleSound.setChecked(true);
		toggleSound.setOnClickListener(new View.OnClickListener() {
			/**
			 * when toggleButton is clicked we change the status of the 
			 * application Music by changing appMusic variable.
			 * @author Amin Fallahi, Mahsa Asadi
			 */
			@Override
			public void onClick(View arg0) {
				if (SoundHandler.appSound==1){
					pplayWin();
				}
				else{

				}

			}
		});

		
		ToggleButton toggleMusic = (ToggleButton) findViewById(R.id.togMusic);
		toggleMusic.setChecked(true);
		toggleMusic.setOnClickListener(new View.OnClickListener() {
			/**
			 * when toggleButton is clicked we change the status of the 
			 * application Music by changing appMusic variable.
			 * @author Amin Fallahi, Mahsa Asadi
			 */
			@Override
			public void onClick(View arg0) {
				if (MusicHandler.appMusic==1){
					MusicHandler.appMusic=0;
					bgMusic.setMusicOff();
				}
				else{
					MusicHandler.appMusic=1;
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
		SettingsHandler.writeFiles();
		super.onDestroy();
	}
}
