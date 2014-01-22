package com.example.layoutfinal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams ;
import android.graphics.Color;
import android.graphics.Typeface;
@SuppressLint("DrawAllocation")
public class MainActivity extends Activity {
    /** Called when the activity is first created. */

	String fields[] = {"اسم","رنگ","میوه" , "گل"};
            
    //String os[] =  {"Android","Mango", "Android","Mango","iOS","Symbian","Bada"};
    TableLayout tl;
    TableRow tr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //tl = (TableLayout) findViewById(R.id.table);
        //
        TableLayout tl = (TableLayout) findViewById(R.id.table);
        //tl.setGravity(Gravity.RIGHT);
        //addHeaders();
    
  /*  public void addHeaders(){
    	 tr = new TableRow(this);
         tr.setLayoutParams(new LayoutParams(
                 LayoutParams.FILL_PARENT,
                 LayoutParams.FILL_PARENT)); 
         tr.setGravity(Gravity.CENTER);
         TextView companyTV = new TextView(this);
         companyTV.setText("گل");
         //companyTV.setgravity;
         companyTV.setTextColor(Color.GRAY);
         companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
         companyTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
         companyTV.setPadding(5, 5, 5, 0);
         tr.addView(companyTV);  // Adding textView to tablerow.
  
         // Creating another textview 
         TextView valueTV = new TextView(this);
         valueTV.setText("اسم");
         valueTV.setTextColor(Color.GRAY);
         valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
         valueTV.setPadding(5, 5, 5, 0);
         valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
         tr.addView(valueTV); // Adding textView to tablerow.
  
         // Add the TableRow to the TableLayout
         tl.addView(tr, new TableLayout.LayoutParams(
                 LayoutParams.FILL_PARENT,
                 LayoutParams.WRAP_CONTENT));
  
         // we are adding two textviews for the <span class="IL_AD" id="IL_AD3">divider</span> because we have two columns
         tr = new TableRow(this);
         tr.setLayoutParams(new LayoutParams(
                 LayoutParams.FILL_PARENT,
                 LayoutParams.WRAP_CONTENT));
  
         // Creating another textview 
         TextView divider = new TextView(this);
         divider.setText("-----------------");
         divider.setTextColor(Color.GREEN);
         divider.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
         divider.setPadding(5, 0, 0, 0);
         divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
         tr.addView(divider); // Adding textView to tablerow.
  
         TextView divider2 = new TextView(this);
         divider2.setText("-------------------------");
         divider2.setTextColor(Color.GREEN);
         divider2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
         divider2.setPadding(5, 0, 0, 0);
         divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
         tr.addView(divider2); // Adding textView to tablerow.
  
         // Add the TableRow to the TableLayout
         tl.addView(tr, new TableLayout.LayoutParams(
                 LayoutParams.FILL_PARENT,
                 LayoutParams.WRAP_CONTENT));}}
                 */
        
/*      for (int x = 1; x < 2; x++) {

 

            TableRow newRow = new TableRow(this);

            TextView column1 = new TextView(this);
            TextView column2 = new TextView(this);
            TextView column3 = new TextView(this);
            TextView column4 = new TextView(this);
            column1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            column2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            column3.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            column4.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            column1.setPadding(5, 5, 5, 0);
            //column1.setText(col1);
            //tr.setGravity(Gravity.CENTER);
            newRow.addView(column1);
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
     
           
            newRow.addView(column2);
            newRow.addView(column3);
            newRow.addView(column4);
            column1.setText(fields[0]); 
            column2.setText(fields[1]); 
            column3.setText(fields[2]); 
            column4.setText(fields[3]); 
            tl.addView(newRow, new TableLayout.LayoutParams());
        
    }}*/
    LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout1);

	TableLayout table = new TableLayout(this);
	
	
	for (int i = 0; i < 2; i++) {
		//int i = 1;
		TableRow row = new TableRow(this);
		
		for (int j = 0; j < 4; j++) {
			
		TextView cell = new TextView(this) {
		    @SuppressLint("DrawAllocation")
			@Override
		    protected void onDraw(Canvas canvas) {
		        super.onDraw(canvas);
		        Rect rect = new Rect();
		        Paint paint = new Paint();
		        paint.setStyle(Paint.Style.STROKE);
		        paint.setColor(Color.BLUE);
		        paint.setStrokeWidth(2);
		        getLocalVisibleRect(rect);
		        canvas.drawRect(rect, paint);       
		    }

		};
		cell.setGravity(Gravity.RIGHT);
		if(i == 0 ){
		cell.setText(fields[j]);}
		cell.setPadding(30, 20, 30, 20);
		row.addView(cell);
		
		
		}
		
		table.addView(row);
		//View f = new View (this) ;
/*TableRow row2 = new TableRow(this);
		
		for (int j = 0; j < 4; j++) {
			
		TextView cell = new TextView(this) {
		    @SuppressLint("DrawAllocation")
			@Override
		    protected void onDraw(Canvas canvas) {
		        super.onDraw(canvas);
		        Rect rect = new Rect();
		        Paint paint = new Paint();
		        paint.setStyle(Paint.Style.STROKE);
		        paint.setColor(Color.BLACK);
		        paint.setStrokeWidth(2);
		        getLocalVisibleRect(rect);
		        canvas.drawRect(rect, paint);       
		    }

		};
		cell.setGravity(Gravity.RIGHT);
		//cell.setText(fields[j]);
		cell.setPadding(30, 20, 30, 20);
		row.addView(cell);
		
		}
		
		table.addView(row2);*/
		
	}
	
	layout.addView(table);
	
}


}