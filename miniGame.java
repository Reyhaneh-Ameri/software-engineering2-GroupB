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
	public final boolean isInCurrentpairs(pair p) {
		
		for (int i = 0; i < pairs.size(); i++) {
			if((pairs.get(i).c==p.c)&&(pairs.get(i).x==p.x)&&(pairs.get(i).y==p.y)){
				return true;
			}
		}
		return false;		
	}
	public final boolean isInWinnedpairs(pair p) {
		Log.d("cvcvcvc",winnedChar.size()+"");
		for (int i = 0; i < winnedChar.size(); i++) {
			if((winnedChar.get(i).c==p.c)&&(winnedChar.get(i).x==p.x)&&(winnedChar.get(i).y==p.y)){
				return true;
			}
		}
		return false;		
	}
	
	public boolean addPair(pair p) {					//ro har harfi k click mishe in tabe farakhani mishe
		
		for(int z=0;z<32;z++){
			if(AlphabetView.AlphabetChars[z]==p.c){
																			//index e harfe click shode ro peyda mikone
				bb=z;
				
			}
		}

		if(((startId-bb==1) ||(bb-endId==1) || (startId==bb) || (bb==endId))&& !(isInCurrentpairs(p)) && !(isInWinnedpairs(p))){
			pairs.add(p);
			
			if((startId-bb==1 || bb==startId)){
				startChar=p.c;											//ba start end moqayese mikone tebeqe qavanini k gofte bodi neveshtam
				
				startId=bb;
			}
			if((bb-endId==1 || bb==endId)){
				endChar=p.c;
				
				endId=bb;
			}
			
			b1.setOnClickListener(new OnClickListener() {						//in button ham k bara ferestdane 4 ta harfe doroste zade shode b server neveshtam 
				
				@Override
				public void onClick(View arg0) {
					
					ArrayList<pair> p2=new ArrayList<pair>();
					
					ArrayList<Boolean> p3=new ArrayList<Boolean>();    //inja karaye server ro shabih sazi kardam
					p3.add(false);
					p3.add(false);								//inja alaki goftam k har dafe az 4 ta pair k az server miad faghat sevvomi ghabele ghabole
					p3.add(true);								//va in khatta ro baade test pak kon damet garm
					p3.add(false);
					                                             // inja  bayad pairs ro b server befrestim , farhad nemishe pairs ro to khode kelas save koni
																// chon activity hey az bein mire doros mishe bara hamin sari arraylist khali mishe
																// bara hamin natonestam ye func benvisam k pair ro az oon begiri bara hamin func server ro inja faakhani kon va pair ro b in ravesh b server enteghal bede
					win(pairs, p2, p3);							//in khatam baade test bayad pak she
					
					while(pairs.size()>0){						//inam bara inke pair ro har marhale bayad khali mikardam
						pairs.remove(0);
						
					}
					
				}
			});
			
			
			
			
			return true;
		}

		return false;
	
	}
	public void win(ArrayList<pair> myPairs,ArrayList<pair> allWinnedPairs,ArrayList<Boolean> pairsState) {      //********
		
		
		TableRow tr2;
		ImageView im;
		for (int i = 0; i < myPairs.size(); i++) {
			
			if(pairsState.get(i)==false){
				
				for (int j = 0; j <32; j++) {
					if(AlphabetView.AlphabetChars[j]==myPairs.get(i).c){
						tr2=trs.get(myPairs.get(i).x);
						((ImageView) tr2.getChildAt(myPairs.get(i).y)).setImageResource(R.drawable.paper2);		//age harf ghabele ghabol nabod dobare to jadval bemone
						
					}
				}
	
			}else{
				winnedChar.add(myPairs.get(i));						//age harfe ghabele ghabol bood b arayeye harfaye borde ezafe she
				tv3.setText(tv3.getText()+" "+myPairs.get(i).c);
			}
		}
		tv.setText("");
		}

	public void start(char[] chn) {
		ArrayList<Character> ch=new ArrayList<Character>();
		for(int u=0;u<chn.length;u++) ch.add(chn[u]); 
		
		tLayout1=(TableLayout) findViewById(R.id.maintablelayout);
		TableRow tr1=new TableRow(this);
		tr1.addView(tv);
		tLayout1.addView(tr1);
		tr1.addView(b1);
		
		tLayout2=new TableLayout(this);
		int j = 0;
		for(int z=0;z<ch.size();z++){
			for(int o=0;o<32;o++){
				if(AlphabetView.AlphabetChars[o]==chn[z]) j=o; 
			}
			
			tmpImages.add(AlphabetView.AlphabetImages[j]);
			tmpCharacters.add(AlphabetView.AlphabetChars[j]);
			
		}
	
		
	
		int row=tmpImages.size()/8+1;
		trs=new ArrayList<TableRow>();
		
		for(int i=0;i<row;i++){
			TableRow r=new TableRow(this);
			r.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			trs.add(r);
		}
		int count=0;
	}