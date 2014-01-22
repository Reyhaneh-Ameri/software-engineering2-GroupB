package com.iust.fandogh.entity;


import com.iust.fandogh.R;

import android.content.Context;
import android.widget.ImageView;
import android.widget.AbsoluteLayout.LayoutParams;

public class AlphabetView extends ImageView {
	public static int ImageSize;
	public final static char[] AlphabetChars = {'ا','ب','پ','ت','ث','ج','چ','ح','خ',
		'د','ذ','ر','ز','ژ','س','ش','ص','ض','ط','ظ','ع','غ','ف','ق','ک','گ','ل',
		'م','ن','و','ه','ی'};
	public final static int[] AlphabetImages = {R.drawable.m1, R.drawable.m2, R.drawable.m3, 
		R.drawable.m4, R.drawable.m5, R.drawable.m6, R.drawable.m7, R.drawable.m8, R.drawable.m9, 
		R.drawable.m10, R.drawable.m11, R.drawable.m12, R.drawable.m13, R.drawable.m14, R.drawable.m15, 
		R.drawable.m16, R.drawable.m17, R.drawable.m18, R.drawable.m19, R.drawable.m20, R.drawable.m21, 
		R.drawable.m22, R.drawable.m23, R.drawable.m24, R.drawable.m25, R.drawable.m26, R.drawable.m27, 
		R.drawable.m28, R.drawable.m29, R.drawable.m30, R.drawable.m31, R.drawable.m32};
	
	char character;
	
	public AlphabetView(Context c, int imageResource, char character, int x, int y) {
		super(c);
		this.character = character;
		this.setImageResource(imageResource);
		this.setLayoutParams(new LayoutParams(ImageSize, ImageSize, x, y));
	}
	
	public char getCharacter() {
		return character;
	}
}
