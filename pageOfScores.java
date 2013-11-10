package com.example.myactivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity{

	public void getPlayerResponse(String [] listOfPlayers2,String [][] listOfPlayersResponse2,boolean [][] listOfResponseCorrectnesse2)
	{
		//build layout parameter objects which will set up size of elements
		LayoutParams fpfp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1);
		LayoutParams wcfp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, 1);
		LayoutParams fpwc = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1);		
		LayoutParams fpfpWH = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 80);
		LayoutParams fpfpWL = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 20);

	//---------------Row of the Table
		int row=(listOfPlayers2.length)+1;
	//---------------Column of the Table
		int column=3+1;
		String [] fieldsOfTable={"«”„","„ÌÊÂ","—‰ê"};
	//create main layout
		LinearLayout main = new LinearLayout(this);
		main.setOrientation(LinearLayout.VERTICAL);
		main.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//		main.setBackgroundColor(Color.rgb(243, 254, 245));
		
		/*
		LinearLayout horizontalLayout = new LinearLayout(this);
		horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
		horizontalLayout.setLayoutParams(fpfp);
	
		TextView red = new TextView(this);
		red.setLayoutParams(wcfp);
		red.setGravity(Gravity.CENTER_HORIZONTAL);
		red.setBackgroundColor(Color.rgb(170, 00, 00));
		red.setText("red");
	
		TextView green = new TextView(this);
		green.setLayoutParams(wcfp);
		green.setGravity(Gravity.CENTER_HORIZONTAL);
		green.setBackgroundColor(Color.rgb(00, 170, 00));
		green.setText("green");
	
		TextView blue = new TextView(this);
		blue.setLayoutParams(wcfp);
		blue.setGravity(Gravity.CENTER_HORIZONTAL);
		blue.setBackgroundColor(Color.rgb(00, 00, 170));
		blue.setText("blue");
	
		TextView yellow = new TextView(this);
		yellow.setLayoutParams(wcfp);
		yellow.setGravity(Gravity.CENTER_HORIZONTAL);
		yellow.setBackgroundColor(Color.rgb(170, 170, 00));
		yellow.setText("yellow");
	*/
		LinearLayout horizontalLayout = new LinearLayout(this);
		horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
		horizontalLayout.setLayoutParams(fpfpWH);
		
		TextView  [] colors= new TextView[50];

		for(int i=0;i<colors.length;i++)
		{
	///		temp=colors[i];
			colors[i] = new TextView(this);
			colors[i].setLayoutParams(wcfp);
			colors[i].setGravity(Gravity.CENTER_HORIZONTAL);
			colors[i].setBackgroundColor(Color.rgb(203-i*3,184-i*3,241-i*3));
			colors[i].setText("");

			///		colors[i]=temp;
		}
//----------------implementing inner layout-------------------//

		TableLayout tbLay= new TableLayout(this);
		tbLay.setLayoutParams(fpfpWL);
		
		TableRow [] listOflaysRow=new TableRow[column];
		
		for(int i=0;i<listOflaysRow.length;i++)
		{
			listOflaysRow[i] = new TableRow(this);
		}

		
		for(int i=0;i<row;i++)
		{
	//		listOflaysRow[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

			for(int j=0;j<column;j++)
			{
				TextView tv= new TextView(this);
				tv.setTextSize(20);
				if(i==0 && j==0)
				{
					tv.setText("");
					listOflaysRow[i].addView(tv);
					TextView texv= new TextView(this);
					texv.setTextSize(20);
					texv.setText(" ");
					listOflaysRow[i].addView(texv);

				}
				else if(i==0 && j!=0)
				{
					tv.setText(fieldsOfTable[j-1]);
					listOflaysRow[i].addView(tv);
					TextView texv= new TextView(this);
					texv.setTextSize(20);
					texv.setText(" ");
					listOflaysRow[i].addView(texv);

				}
				else if(j==0 && i!=0)
				{
					tv.setText(listOfPlayers2[i-1]);
					listOflaysRow[i].addView(tv);
					TextView texv= new TextView(this);
					texv.setTextSize(20);
					texv.setText(" ");
					listOflaysRow[i].addView(texv);

				}
				else
				{
					tv.setText(listOfPlayersResponse2[i-1][j-1]);
					if(listOfResponseCorrectnesse2[i-1][j-1])
					{
						tv.setBackgroundColor(Color.rgb(108, 253, 173));
					}
					else
					{
						tv.setBackgroundColor(Color.rgb(252, 137, 131));
					}
					listOflaysRow[i].addView(tv);
					TextView texv= new TextView(this);
					texv.setTextSize(20);
					texv.setText(" ");
					listOflaysRow[i].addView(texv);
				}
		}
	}

		for(int i=0;i<listOflaysRow.length;i++)
		{
			TableRow enterRow=new TableRow(this);
			TextView spaceRow= new TextView(this);
			spaceRow.setText("                  ");
			spaceRow.setTextSize(20);
			enterRow.addView(spaceRow);
			tbLay.addView(listOflaysRow[i]);
			tbLay.addView(enterRow);
		}
	
		for(int i=0;i<colors.length;i++)
		{
			horizontalLayout.addView(colors[i]);
		}
		main.addView(horizontalLayout);
		main.addView(tbLay);
	
	//set the main content view that holds all elements
		setContentView(main);
		
	}
		
	public void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	//---------------------------------------FARHAD MUST ENTER HIS VALUES-----------------//
	//-----------------------------------------------------------------------------------//
	String [] listOfPlayers=new String[2];//<------------------THis is dynamic----------------<<<<<<<<<<<<<//
	String [][] listOfPlayersResponse = new String[listOfPlayers.length][3];
	boolean [][] listOfResponseCorrectnesse= new boolean [listOfPlayers.length][3];
	listOfPlayers[0]=new String("Sina");
	listOfPlayers[1]=new String("Q");
	
	listOfPlayersResponse[0][0]="”Ì‰«";
	listOfPlayersResponse[0][1]="Å— €«·";
	listOfPlayersResponse[0][2]="ê·»—ê";
	listOfPlayersResponse[1][0]="Å—‰œÂ";
	listOfPlayersResponse[1][1]="‘”Ì";
	listOfPlayersResponse[1][2]="‘Ì";
	
	listOfResponseCorrectnesse[0][0]=true;
	listOfResponseCorrectnesse[0][1]=true;
	listOfResponseCorrectnesse[0][2]=true;
	listOfResponseCorrectnesse[1][0]=false;
	listOfResponseCorrectnesse[1][1]=false;
	listOfResponseCorrectnesse[1][2]=false;
	
	//--------------END -------------//
	getPlayerResponse(listOfPlayers,listOfPlayersResponse,listOfResponseCorrectnesse);
	}
	
}