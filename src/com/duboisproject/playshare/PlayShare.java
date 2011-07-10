package com.duboisproject.playshare;

import android.app.Activity;
import android.os.Bundle;

import com.duboisproject.playshare.model.Game;

public class PlayShare extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Game g = new Game();
		g.begin();
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.end();
	}
}