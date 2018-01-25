package com.bartovapps.gpstriprec.displayers;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

public class KmhDisplayer implements DataDisplayer{

	StringBuilder speedBuilder = new StringBuilder();
	StringBuilder unitBuilder = new StringBuilder("KM/H");
	
	
	
	@Override
	public void displayData(TextView view, double value) {
		// km\h = m\sec * 3.6
		 speedBuilder.replace(0, speedBuilder.length(), String.format("%.1f", (value * 3.6)));
		 SpannableString ss1 =  new SpannableString(speedBuilder.toString() + unitBuilder.toString());
		 ss1.setSpan(new RelativeSizeSpan(0.5f), speedBuilder.length(), ss1.length(), 0); // set size
		 ss1.setSpan(new ForegroundColorSpan(Color.RED), speedBuilder.length(),ss1.length(), 0);// set color
		 
//		view.setText( String.format("%.1f", (value * 3.6)) + ss1);
		view.setText( ss1 );

	}

}
