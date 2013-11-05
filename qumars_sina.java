package com.example.as;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

//coded by Cna Piri and Qmars razmi
 
public class MainActivity extends Activity {

	
	private  static int editTexNum;
	 EditText textArr[];
	
	public void insertWord(char character)
	{
		textArr[editTexNum].setText(textArr[editTexNum].getText().toString()+character);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_page);

		 textArr=new EditText[2];
		
		textArr[0]=(EditText) findViewById(R.id.t1);
		textArr[1]=(EditText) findViewById(R.id.t2);
		for(int i=0;i<2;i++)
		{
			final int index=i;
			textArr[i].setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					editTexNum=index;
					
					return false;
				}
	
					
			
		});
		}


		ImageView imgv=(ImageView) findViewById(R.id.helpicon);
		imgv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				insertWord('a');
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
