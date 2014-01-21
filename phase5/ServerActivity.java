package com.iust.fandogh;

import java.util.HashMap;

import com.iust.fandogh.controllers.GameController;
import com.iust.fandogh.controllers.ServerNetworkController;
import com.iust.fandogh.entity.AlphabetView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ServerActivity extends Activity {
	LinearLayout clientsLay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server2);
		
		clientsLay = (LinearLayout)findViewById(R.id.UsersList);
		 
		String nickname = getIntent().getStringExtra(MainActivity.NICKNAME);
		getIntent().removeExtra(MainActivity.NICKNAME);
		TextView nn = new TextView(this);
		nn.setText(nickname);
		nn.setTextColor(Color.RED);
		nn.setTextSize(20f);
		clientsLay.addView(nn);
		
		String ip = ServerNetworkController.getIPv4Address();
		((TextView)findViewById(R.id.IP)).setTextColor(Color.RED);
		((TextView)findViewById(R.id.IP)).setText(ip);
		
		new GameController(nickname);
		new ServerNetworkController();
		ServerNetworkController.snc.setActivity(this);
	}
    
    /**
     * Start game for all users 
     */
    public void startGame(View arg0) {
    	HashMap<Integer, Integer> modes = new HashMap<Integer, Integer>();
    	
    	if(((ToggleButton)findViewById(R.id.Value1)).isChecked())
    		modes.put(GameController.MODE_KEYBOARD, GameController.MODE_KEYBOARD_2);
    	else
    		modes.put(GameController.MODE_KEYBOARD, GameController.MODE_KEYBOARD_1);
    	
    	if(((ToggleButton)findViewById(R.id.Value2)).isChecked())
    		modes.put(GameController.MODE_ALPHABETS, GameController.MODE_ALPHABETS_2);
    	else
    		modes.put(GameController.MODE_ALPHABETS, GameController.MODE_ALPHABETS_1);
    	
    	char tmpChar = (((Spinner)findViewById(R.id.Value3)).getSelectedItem().toString()).charAt(0);
    	modes.put(GameController.MODE_START_CHARACTER, -1);
    	for (int i = 0; i < 32; i++)
			if(AlphabetView.AlphabetChars[i] == tmpChar) {
				modes.put(GameController.MODE_START_CHARACTER, i);
				break;
			}
    	
//    	tmpChar = (((Spinner)findViewById(R.id.Value4)).getSelectedItem().toString()).charAt(0);
//    	modes.put(GameController.MODE_END_CHARACTER, -1);
//    	for (int i = 0; i < 32; i++)
//			if(AlphabetView.AlphabetChars[i] == tmpChar) {
//				modes.put(GameController.MODE_END_CHARACTER, i);
//				break;
//			}
//    	
    	GameController.gc.startGame(modes, 
    			Integer.valueOf(((Spinner)findViewById(R.id.Value5)).getSelectedItem().toString()));
		
    	Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("modes", modes);
    	intent.putExtra("rounds", Integer.valueOf(((Spinner)findViewById(R.id.Value5)).getSelectedItem().toString()));
    	intent.putExtra("alphabets", GameController.gc.getPlayer(0).getAllCharsCount());
    	intent.putExtra("score", GameController.gc.getPlayer(0).getScore());
    	
    	startActivity(intent);
		this.finish();
    }
	
	public void refreshPlayers(String[] playersName) {
		clientsLay.removeAllViews();
		for (String name : playersName) {
			TextView nn = new TextView(this);
	    	nn.setText(name);
	    	nn.setTextColor(Color.RED);
	    	nn.setTextSize(20f);
	    	clientsLay.addView(nn);
		}
	}
}
