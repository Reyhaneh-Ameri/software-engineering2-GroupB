package com.iust.fandogh;

import java.util.ArrayList;
import java.util.Random;

import com.iust.fandogh.entity.AlphabetView;

import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.FragmentActivity;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import static com.iust.fandogh.entity.AlphabetView.*;

public class MainActivity extends FragmentActivity 
	 {

	ArrayList<Integer> tmpImages;
	ArrayList<Integer> myIds;
	ArrayList<Character>  tmpCharacters;
	ArrayList<pair> pairs;
	ArrayList<pair> winnedChar;

	TextView tv;
	TextView tv2;
	TextView tv3;
	Button b1;
	
	
	
	public char startChar;
	public char endChar;
	public int startId;
	public int endId;
	
	int bb=0;
	int tt=0;
	
	TableLayout tLayout1;
	TableLayout tLayout2;
	ArrayList<TableRow> trs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		myIds=new ArrayList<Integer>();
		tmpImages=new ArrayList<Integer>();
		tmpCharacters=new ArrayList<Character>();
		pairs=new ArrayList<pair>();
		winnedChar=new ArrayList<pair>();

		b1=new Button(this);
		
		tv=new TextView(this);
		
		tv2=new TextView(this);
		tv2.setText("????   ????? ");
		
		tv3=new TextView(this);
		
		char[] qw={'?','?','?','?','?','?','?','?',
					'?',',','?','?','?','?','?','?',				//ina horofe farzie server hastan k to table rikhte mishe
					'?','?','?','?','?','?','?','?',
					'?','?','?','?','?','?','?','?',
					'?','?','?','?','?','?','?','?',
					'?','?','?','?','?','?','?','?',
					'?','?','?'};
		
			
		
		start(qw);				// *****
		
	}
	}