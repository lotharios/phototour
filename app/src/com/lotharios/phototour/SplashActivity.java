package com.lotharios.phototour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        searchRandomButtonListener();
        searchByUserButtonListener();
        inviteContactButtonListener();
        settingButtonListener();
    }

	private void searchRandomButtonListener() {
		Button searchRandomBtn = (Button)findViewById(R.id.randomSearchButton);
		searchRandomBtn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v) {
				Intent i = new Intent(SplashActivity.this, SearchRandomActivity.class);
				startActivity(i);
			}
		});
		
	}

	private void searchByUserButtonListener() {
		Button searchByUserBtn = (Button)findViewById(R.id.searchByUserButton);
		searchByUserBtn.setOnClickListener(new View.OnClickListener(){

			public void onClick(View v) {
				Intent i = new Intent(SplashActivity.this, SearchByUserActivity.class);
				startActivity(i);
			}
			
		});
		
	}

	private void inviteContactButtonListener() {
		Button inviteContactBtn = (Button)findViewById(R.id.inviteContactButton);
		inviteContactBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(SplashActivity.this, InviteContactActivity.class);
				startActivity(i);
			}
		});
		
	}

	private void settingButtonListener() {
		Button settingBtn = (Button)findViewById(R.id.settingButton);
		settingBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(SplashActivity.this, SettingActivity.class);
				startActivity(i);
			}
		});
		
	}
}