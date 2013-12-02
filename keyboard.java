package com.iust.modernesmfamil2;

import java.util.ArrayList;
import java.util.Random;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout.LayoutParams;

import com.iust.modernesmfamil2.alphabetVeiw.AlphabetView;
import static com.iust.modernesmfamil2.alphabetVeiw.AlphabetView.*;

/**
 * First page userInterface handller
 * @author FERi
 * @version 1.0
 */
public class MainActivity extends FragmentActivity 
	implements JoinDialog.NoticeDialogListener,ServerErrorDialog.NoticeDialogListener{
	
	public final static int buttonsSize = 80;
	public final static int nicknameHeight = 50;
	public final static int nicknameWidth = 130;
	public final static int alphabetSize = 80;
	
	public final static String NICKNAME = "nickname";
	public final static String tag = "modernEsmFamil";
	public final static String IP = "ip";
	
	
	
	TextView nickname;
	AbsoluteLayout lay;

	/**
	 * Handle Alphabet views in page
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		
		lay = (AbsoluteLayout)findViewById(R.id.lay);
		
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int densityDpi = (int)(metrics.density);
		int cols = width/(alphabetSize*densityDpi);
		int offsetX = width%(alphabetSize*densityDpi);
		int rows = (height- buttonsSize- nicknameHeight)/(alphabetSize*densityDpi);
		int offsetY = (height- buttonsSize- nicknameHeight)%(alphabetSize*densityDpi);
		if(cols*rows <32) {
			densityDpi--;
			
			cols = width/(alphabetSize*densityDpi);
			offsetX = (width-cols*alphabetSize*densityDpi)/cols;
			rows = (height- buttonsSize- nicknameHeight)/(alphabetSize*densityDpi);
			offsetY = ((height- buttonsSize- nicknameHeight)-rows*alphabetSize*densityDpi)/rows;
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
		
		TableLayout tl=new TableLayout(this);
		int[] y=new int[32];
		for(int u=0;u<32;u++){
			y[u]=0;
		}
		int count=0;
		
		for (int i=0; i< 4; i++){
			TableRow tr=new TableRow(this);
			for (int j = 0; j < 8; j++) {
				try {
					int h=0;
					while(h==0){
					
					random = rand.nextInt(tmpImages.size());
					
					if(y[random]==0){
					tmpAlphabetView = new AlphabetView(this, tmpImages.remove(random), tmpCharacters.remove(random), 10, 10);
					tmpAlphabetView.setRotation(rand.nextInt(360));
					ImageView imageView=new ImageView(this);
					imageView.setImageResource(tmpImages.remove(random));
					
					tmpAlphabetView.setOnClickListener(new alphabetListener());
					tr.addView(imageView);
					
					y[random]=1;
					count++;
					h=1;}
					}
				} catch (Exception e) {
					break;
				}
			}
			tl.addView(tr);
			}
		
		lay.addView(tl);
		nickname = new TextView(this);
		nickname.setText("???");
		tmpLayoutParams = new LayoutParams(nicknameWidth*densityDpi, nicknameHeight*densityDpi, 
				width/2-(nicknameWidth/2)*densityDpi, 10);
		nickname.setLayoutParams(tmpLayoutParams);
		nickname.setTextColor(Color.BLUE);
		nickname.setGravity(Gravity.CENTER);
		nickname.setBackgroundColor(Color.YELLOW);
		nickname.setTextSize(20f);
		nickname.setOnClickListener(new NicknameReset());
		lay.addView(nickname,tmpLayoutParams);
		
//	Server
		tmpImageView = new ImageView(this);
		tmpImageView.setImageResource(R.drawable.server);
		tmpLayoutParams = new LayoutParams(buttonsSize*densityDpi, buttonsSize*densityDpi, 
				width/2- (buttonsSize+buttonsSize)*densityDpi, height-buttonsSize*densityDpi);
//		tmpImageView.setLayoutParams(tmpLayoutParams);
		tmpImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(ServerActivity.getIPv4Address().equals("")) {
					ServerErrorDialog sed = new ServerErrorDialog();
					sed.show(getSupportFragmentManager(), "Error");
					return;
				}
				
				Intent intent = new Intent(MainActivity.this, ServerActivity.class);
				intent.putExtra(MainActivity.NICKNAME, nickname.getText());
//				intent.putExtra(MainActivity.NOTE, ((EditText)findViewById(R.id.addNote)).getText().toString());
				startActivity(intent);
				
				MainActivity.this.finish();
			}
		});
		lay.addView(tmpImageView, tmpLayoutParams);
		
//	Client
		tmpImageView = new ImageView(this);
		tmpImageView.setImageResource(R.drawable.join);
		tmpLayoutParams = new LayoutParams(buttonsSize*densityDpi, buttonsSize*densityDpi, 
				width/2- buttonsSize*densityDpi, height- buttonsSize*densityDpi);
//		tmpImageView.setLayoutParams(tmpLayoutParams);
		tmpImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				JoinDialog jd = new JoinDialog();
				jd.show(MainActivity.this.getSupportFragmentManager(), "ServerAddress");
			}
		});
		lay.addView(tmpImageView, tmpLayoutParams);
		
//	Help
		tmpImageView = new ImageView(this);
		tmpImageView.setImageResource(R.drawable.help);
		tmpLayoutParams = new LayoutParams(buttonsSize*densityDpi, buttonsSize*densityDpi, 
				width/2 , height- buttonsSize*densityDpi);
//		tmpImageView.setLayoutParams(tmpLayoutParams);
		tmpImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				nickname.setText("help");
			}
		});
		lay.addView(tmpImageView, tmpLayoutParams);
		
//	Setting
		tmpImageView.setImageResource(R.drawable.settings);
		tmpImageView = new ImageView(this);
		tmpLayoutParams = new LayoutParams(buttonsSize*densityDpi, buttonsSize*densityDpi, 
				width/2 +buttonsSize*densityDpi, height- buttonsSize*densityDpi);
//		tmpImageView.setLayoutParams(tmpLayoutParams);
		tmpImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				nickname.setText("settings go");
			}
		});
		lay.addView(tmpImageView, tmpLayoutParams);

		
		
		
		
		
//		for (int i = 0; i < AlphabetImages.length; i++) {
//			random = rand.nextInt(tmpImages.size());
//			tmpAlphabetView = new AlphabetView(this, tmpImages.remove(random), tmpCharacters.remove(random), 10, 10);
//			tmpAlphabetView.setRotation(rand.nextInt(360));
//			tmpLayoutParams = new LayoutParams(alphabetSize*densityDpi, alphabetSize*densityDpi, 
//					((i%cols)*(buttonsSize*densityDpi+offsetX))+rand.nextInt(30), 
//					((i/cols)*(buttonsSize*densityDpi+offsetY)+rand.nextInt(30))+(nicknameHeight*densityDpi+20));
//			Log.d(tag, tmpAlphabetView.getCharacter()+"  "+
//					Integer.toString(((i%cols)*(buttonsSize*densityDpi+offsetX))+rand.nextInt(30))+"     "+ 
//					Integer.toString(((i/cols)*(buttonsSize*densityDpi+offsetY)+rand.nextInt(30))));
////			tmpAlphabetView.setLayoutParams(tmpLayoutParams);
//			tmpAlphabetView.setOnClickListener(new alphabetListener());
//			lay.addView(tmpAlphabetView, tmpLayoutParams);
//		}
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
	 * these functions are override for dialog listeners
	 */
	@Override
	public void onDialogNegativeClick() {
		return;
	}
	@Override
	public void onDialogPositiveClick(String ip) {
		Intent intent = new Intent(MainActivity.this, JoinActivity.class);
		intent.putExtra(MainActivity.IP, ip);
		startActivity(intent);
		
		MainActivity.this.finish();
	}
	
	@Override
	public void onErrorDialogClick() {
		return;
	}
}
