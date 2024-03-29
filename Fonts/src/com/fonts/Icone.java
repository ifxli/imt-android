package com.fonts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

public abstract class Icone extends TextView {
	private int mIconeReference;

	public Icone(Context context) {
		this(context, null);
	}

	public Icone(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Icone(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initialiseTypeFace();
	
		//int padding = (int) (10 * scaledDensity);
		//setPadding(padding,padding,padding,padding);
		setDrawingCacheEnabled(true);
	}

	public void setIconeRes(int iconeReference) {
		mIconeReference = iconeReference;
		setText(Html.fromHtml(Character.toString((char)mIconeReference)));
	}
	
	public BitmapDrawable getIconeDrawable() {
		int size = (int) getTextSize();
		Bitmap b = Bitmap.createBitmap( size, size, Bitmap.Config.ARGB_8888);                
		Canvas c = new Canvas(b);
		measure(size, size); //Change from original post
		layout(0, 0, size, size);
		draw(c);
		return new BitmapDrawable(b);
	}

	protected abstract void initialiseTypeFace();
}
