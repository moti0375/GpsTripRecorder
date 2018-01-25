package com.bartovapps.gpstriprec.displayers;

import java.text.DecimalFormat;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

public class MetricAltDisplayer implements DataDisplayer {
	StringBuilder distanceBuilder = new StringBuilder();
	StringBuilder unitBuilder = new StringBuilder("M");
	DecimalFormat numFormat;

	public MetricAltDisplayer(){
		numFormat = new DecimalFormat("#,###.##");		
	}
	
	@Override
	public void displayData(TextView view, double altitude) {
		
		 distanceBuilder.replace(0, distanceBuilder.length(), numFormat.format(altitude));
		 SpannableString ss1 =  new SpannableString(distanceBuilder.toString() + unitBuilder.toString());
		 ss1.setSpan(new RelativeSizeSpan(0.5f), distanceBuilder.length(), ss1.length(), 0); // set size
		 ss1.setSpan(new ForegroundColorSpan(Color.RED), distanceBuilder.length(),ss1.length(), 0);// set color
		 
//		view.setText( String.format("%.1f", (value * 3.6)) + ss1);
		view.setText( ss1 );		
	}

}
