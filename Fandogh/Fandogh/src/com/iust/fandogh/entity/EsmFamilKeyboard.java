package com.iust.fandogh.entity;

import java.util.ArrayList;
import java.util.Random;

import com.iust.fandogh.GameActivity;
import com.iust.fandogh.MainActivity;
import com.iust.fandogh.controllers.GameController;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.AbsoluteLayout.LayoutParams;


import static com.iust.fandogh.entity.AlphabetView.*;

public class EsmFamilKeyboard extends AbsoluteLayout {
	int w,h;
	GameActivity mga;
	int mod = GameController.MODE_KEYBOARD_1;
	ArrayList<AlphabetView> alphabets = new ArrayList<AlphabetView>();
	
	public EsmFamilKeyboard(Context context, int mod){
		super(context);
		this.mod = mod;
		
		for (int i=0; i<AlphabetChars.length; i++)
			alphabets.add(new AlphabetView(this.getContext(), AlphabetImages[i], AlphabetChars[i], 0, 0));
	}
//	public EsmFamilKeyboard(Context context, AttributeSet attrs){
//		super(context, attrs, 0);
//	}
//	public EsmFamilKeyboard(Context context, AttributeSet attrs, int defaultStyle){
//		super(context, attrs, defaultStyle);
//	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);		
		
		if(widthMeasureSpec!=0 && heightMeasureSpec!=0)
			refreshKeyboard(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
	}
	
	public void setActivity(GameActivity mga) {
		this.mga = mga;
	}
	
	public void setMode(int mod) {
		this.mod = mod;
		
		requestLayout();
		invalidate();
	}
	
	protected void refreshKeyboard(int height, int width) {
		ImageSize = 30;
		
		int offsetX = (width - 7*ImageSize)/5;
		int offsetY = (height- 7*ImageSize)/5;
		while(offsetX > 10 && offsetY > 10) {
			ImageSize += 3;
			
			offsetX = (width - 7*ImageSize)/6;
			offsetY = (height- 7*ImageSize)/6;
		}
		
		
		Random rand = new Random();
		int random;
		AlphabetView tmpAlphabetView;
//		ImageView tmpImageView;
		LayoutParams tmpLayoutParams;
//		ArrayList<Integer> tmpImages = new ArrayList<Integer>();
//		ArrayList<Character> tmpCharacters = new ArrayList<Character>();
//		for (int i=0; i<AlphabetChars.length; i++) {
//			tmpImages.add(AlphabetImages[i]);
//			tmpCharacters.add(AlphabetChars[i]);
//		} 
		
		ArrayList<AlphabetView> tmpAlphabets = new ArrayList<AlphabetView>(alphabets);
		for (int i=0; i< 6; i++)
			for (int j = 0; j < 6; j++) {
				try {
					if(mod==GameController.MODE_KEYBOARD_2) {
						random = rand.nextInt(tmpAlphabets.size());
						tmpAlphabetView = tmpAlphabets.remove(random);
					} else {
						tmpAlphabetView = tmpAlphabets.remove(0);
					}
					tmpLayoutParams = new LayoutParams(ImageSize, ImageSize, 
							ImageSize + i*(ImageSize+offsetX)+rand.nextInt(offsetX/2), 
							j*(ImageSize+offsetY)+rand.nextInt(offsetY/2));
					tmpAlphabetView.setOnClickListener(new alphabetListener());
					this.addView(tmpAlphabetView, tmpLayoutParams);
					
					if(i==6 && j==2)
						return;
				} catch (Exception e) {
					break;
				}
			}
	}
	
	class alphabetListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			mga.insertCharacter(((AlphabetView)arg0).getCharacter());
		}
	}
}
