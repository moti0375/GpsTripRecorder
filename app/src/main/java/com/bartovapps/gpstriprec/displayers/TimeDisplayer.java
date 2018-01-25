package com.bartovapps.gpstriprec.displayers;

import android.widget.TextView;

public interface TimeDisplayer {

	public abstract void displayTime(TextView view, long millis);
}
