package com.iust.fandogh;

import java.util.ArrayList;
import java.util.HashMap;

import com.iust.fandogh.controllers.ClientNetworkController;
import com.iust.fandogh.controllers.GameController;
import com.iust.fandogh.entity.Player;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The class for representing join page and show network events in userinterface
 * @author Farhad hosseinkhani,reyhane ameri
 *
 */
public class JoinActivity extends Activity{
	LinearLayout usersList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
		usersList = (LinearLayout)findViewById(R.id.UsersList);
		
		new ClientNetworkController(getIntent().getStringExtra(MainActivity.IP), 
				getIntent().getStringExtra(MainActivity.NICKNAME));
		ClientNetworkController.cnc.setActivity(this);
		
		getIntent().removeExtra(MainActivity.NICKNAME);
		getIntent().removeExtra(MainActivity.IP);
	}
	
	public void refreshPlayers(String[] playersName) {
		usersList.removeAllViews();
		for (String name : playersName) {
			TextView nn = new TextView(this);
	    	nn.setText(name);
	    	nn.setTextColor(Color.RED);
	    	usersList.addView(nn);
		}
	}
	
	public void startGame(HashMap<Integer, Integer> modes, int rounds) {
		Log.d(MainActivity.tag, "start the fucking game");
		
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(Integer.toString(GameController.MODE_KEYBOARD), modes.get(GameController.MODE_KEYBOARD));
		intent.putExtra(Integer.toString(GameController.MODE_ALPHABETS), modes.get(GameController.MODE_ALPHABETS));
		intent.putExtra(Integer.toString(GameController.MODE_START_CHARACTER), modes.get(GameController.MODE_START_CHARACTER));
		intent.putExtra(Integer.toString(GameController.MODE_END_CHARACTER), modes.get(GameController.MODE_END_CHARACTER));
		intent.putExtra("rounds", rounds);
		
		startActivity(intent);
		this.finish();
	}
	
//	@Override
//	protected void onDestroy() {
//		ClientNetworkController.cnc.exitGame();
//		
//		super.onPause();
//	}
}
