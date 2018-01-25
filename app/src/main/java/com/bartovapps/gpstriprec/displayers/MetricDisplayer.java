package com.bartovapps.gpstriprec.displayers;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class MetricDisplayer implements DataDisplayer{

	StringBuilder distanceBuilder = new StringBuilder();
	StringBuilder unitBuilder = new StringBuilder("M");
	
	@Override
	public void displayData(TextView view, double distance) {
		
		if(distance < 1000){
			unitBuilder.replace(0, unitBuilder.length(), "M");
		}else{
			distance/=1000;
			unitBuilder.replace(0, unitBuilder.length(), "KM");
		}
		
		 distanceBuilder.replace(0, distanceBuilder.length(), String.format("%.1f", distance));
		 SpannableString ss1 =  new SpannableString(distanceBuilder.toString() + unitBuilder.toString());
		 ss1.setSpan(new RelativeSizeSpan(0.5f), distanceBuilder.length(), ss1.length(), 0); // set size
		 ss1.setSpan(new ForegroundColorSpan(Color.RED), distanceBuilder.length(),ss1.length(), 0);// set color
		

//		String text = distance > 1000 ? (String.format("%.1f", distance/1000) + " KM") : String.format("%.1f", distance) + " M";
		view.setText(ss1);
		
	}

}
