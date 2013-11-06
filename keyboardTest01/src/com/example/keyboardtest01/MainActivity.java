package com.example.keyboardtest01;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private  static int editTexNum;
	TextView textArr[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_page);
		
		((Keyboard1)findViewById(R.id.keyboard)).setActivity(this);
		
		textArr=new TextView[2];
		
		textArr[0]=(TextView)findViewById(R.id.esm);
		textArr[1]=(TextView)findViewById(R.id.famil);
		for(int i=0;i<2;i++)
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
	public void insertCharacter(char character)
	{
		textArr[editTexNum].setText(textArr[editTexNum].getText().toString()+character);
	}
	
	public void clearText(View arg0) {
		if(textArr[editTexNum].getText().toString().length()>0)
			textArr[editTexNum].setText(textArr[editTexNum].getText().toString().
				substring(0, textArr[editTexNum].getText().toString().length()-1));
	}
}
