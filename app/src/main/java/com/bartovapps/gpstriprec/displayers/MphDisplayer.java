package com.bartovapps.gpstriprec.displayers;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

public class MphDisplayer implements DataDisplayer {

	StringBuilder speedBuilder = new StringBuilder();
	StringBuilder unitBuilder = new StringBuilder("Mi/H");

	@Override
	public void displayData(TextView view, double value) {
		// 1mph = 1 m/sec * 2.23694
		double mph = value * 2.23694;

		speedBuilder.replace(0, speedBuilder.length(),
				String.format(String.format("%.1f", mph)));
		SpannableString ss1 = new SpannableString(speedBuilder.toString()
				+ unitBuilder.toString());
		ss1.setSpan(new RelativeSizeSpan(0.5f), speedBuilder.length(),
				ss1.length(), 0); // set size
		ss1.setSpan(new ForegroundColorSpan(Color.RED), speedBuilder.length(),
				ss1.length(), 0);// set color

		view.setText( ss1 );
	}
}
