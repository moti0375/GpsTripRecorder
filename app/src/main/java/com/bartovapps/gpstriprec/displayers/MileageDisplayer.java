package com.bartovapps.gpstriprec.displayers;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

public class MileageDisplayer implements DataDisplayer{

	StringBuilder distanceBuilder = new StringBuilder();
	StringBuilder unitBuilder = new StringBuilder("Mi");

	@Override
	public void displayData(TextView view, double distance) {

//		mi =m / 1609.34
		double miles = distance / 1609.34;
		 distanceBuilder.replace(0, distanceBuilder.length(), String.format("%.1f", miles));
		 SpannableString ss1 =  new SpannableString(distanceBuilder.toString() + unitBuilder.toString());
		 ss1.setSpan(new RelativeSizeSpan(0.5f), distanceBuilder.length(), ss1.length(), 0); // set size
		 ss1.setSpan(new ForegroundColorSpan(Color.RED), distanceBuilder.length(),ss1.length(), 0);// set color

		 view.setText(ss1);
		
	}

}
