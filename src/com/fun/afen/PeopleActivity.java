package com.fun.afen;

import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PeopleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_people);
		ParseAnalytics.trackAppOpened(getIntent());

		String myname = ParseInstallation.getCurrentInstallation().getString("username");

		final EditText sat = (EditText) findViewById(R.id.destination);


		TextView me = (TextView) findViewById(R.id.myname);
		if(myname != null)
			me.setText("aFen a " + myname);

		Button push = (Button) findViewById(R.id.push);
		push.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(sat.getText().toString().length() > 0){
					ParsePush push = new ParsePush();
					String yourMessage = "aFen a " + sat.getText().toString() ;//I want to get this message from other device?
					push.setChannel(sat.getText().toString());
					push.setMessage(yourMessage);
					//push.setData("exampledata"); if I use this can I get this data from other device?
					push.sendInBackground();
				}
				else{
					Toast.makeText(getApplicationContext(), "Username not valid!!", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
