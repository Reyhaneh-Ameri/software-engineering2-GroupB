package com.example.keyboardtest01;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;


import static com.example.keyboardtest01.AlphabetView.*;

public class Keyboard1 extends AbsoluteLayout{
	int w,h;
	MainActivity ma;
	
	public Keyboard1(Context context){
	   super(context, null);
	}
	public Keyboard1(Context context, AttributeSet attrs){
	   super(context, attrs, 0);
	}
	public Keyboard1(Context context, AttributeSet attrs, int defaultStyle){
		super(context, attrs, defaultStyle);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);		
		
		if(widthMeasureSpec!=0 && heightMeasureSpec!=0)
			refreshKeyboard(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
	}
	
	protected void refreshKeyboard(int h, int w) {
		int offset = 80;
		if(h*w < 80*80)
			offset -= 20;
		if(h*w < 60*60)
			offset -= 20;
		
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

		for(int i=0; i< 32; i++) {			
			try {
				random = rand.nextInt(tmpImages.size());
				tmpAlphabetView = new AlphabetView(this.getContext(), tmpImages.remove(random),
						tmpCharacters.remove(random), 10, 10);
				tmpLayoutParams = new LayoutParams(offset, offset, 100,100);
				tmpAlphabetView.setOnClickListener(new alphabetListener());
				
				this.addView(tmpAlphabetView, tmpLayoutParams);
			} catch (Exception e) {
				break;
			}			
		}
	}
	
	class alphabetListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			ma.printChar(((AlphabetView)arg0).getCharacter());
		}
	}
}
