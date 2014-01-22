package com.iust.fandogh;

import java.util.ArrayList;

import com.iust.fandogh.controllers.ClientNetworkController;
import com.iust.fandogh.controllers.GameController;
import com.iust.fandogh.controllers.ServerNetworkController;
import com.iust.fandogh.entity.EsmFamilKeyboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

public class GameActivity extends Activity {
	private static int editTexNum;
	TextView textArr[];
	
	/**
	 * set textviews active for edditing
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maingame);
		
		if(ClientNetworkController.cnc != null)
			ClientNetworkController.cnc.setActivity(this);
		else {
			ServerNetworkController.snc.setActivity(this);
		} 
		
		EsmFamilKeyboard k = new EsmFamilKeyboard(this.getApplicationContext(), 
				getIntent().getIntExtra(Integer.toString(GameController.MODE_KEYBOARD), GameController.MODE_KEYBOARD_1));
		((FrameLayout)findViewById(R.id.keyboardLay)).addView(k);
		k.setActivity(this);
//		Log.d(MainActivity.tag, Integer.toString(getIntent().getIntExtra("Round", 0)));
		
		textArr=new TextView[3];
		textArr[0]=(TextView)findViewById(R.id.esm);
		textArr[1]=(TextView)findViewById(R.id.rang);
		textArr[2]=(TextView)findViewById(R.id.mive);
		
		for(int i=0;i<textArr.length;i++)
		{
			final int index=i;
			textArr[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					editTexNum=index;
				}
			});
		}
	}
	
	/**
	 * insert char to active textview
	 * @param character
	 */
	public void insertCharacter(char character)
	{
		textArr[editTexNum].setText(textArr[editTexNum].getText().toString()+character);
	}
	
	/**
	 * backspace active textview
	 * @param arg0
	 */
	public void clearText(View arg0) {
		if(textArr[editTexNum].getText().toString().length()>0)
			textArr[editTexNum].setText(textArr[editTexNum].getText().toString().
				substring(0, textArr[editTexNum].getText().toString().length()-1));
	}
	
	public void endGameRequest(View arg0) {
		Log.d(MainActivity.tag, "End Round");
		for (TextView field : textArr)
			if(field.getText().length()==0) 
				return;
		
		if(ClientNetworkController.cnc != null)
			ClientNetworkController.cnc.sendEndGameRequest();
		else {
			ServerNetworkController.snc.sendEndRoundMSG();
			GameController.gc.endGame(GameController.gc.getPlayer(0).getNickname(), getFields());
		}
	}
	
	/**
	 * get all textviews fields to send to server
	 * @return
	 */
	public ArrayList<String> getFields() {
		ArrayList<String> ans = new ArrayList<String>();
		for (TextView tv : textArr) {
			ans.add(""+tv.getText());
		}
		
		return ans;
	}
	
	/**
	 * change activity and view result activity
	 */
	public void endGame() {
		Intent intent = new Intent(this, ResultActivity.class);
		startActivity(intent);
		this.finish();
	}

}
