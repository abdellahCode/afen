package com.fun.afen;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.parse.SaveCallback;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText usernameEditText;
	public static String TAG = "aFen";
	public static String APPLICATION_ID = "zy7fHjM1rWItRmM1zL5zgonu7Ole5U0SyCTR38ss";
	public static String CLIENT_KEY = "BCtLxryANEV2aKCSusMavS6Sd0qEZ60MlL3bcs0I";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		try{
//			Parse.initialize(this, APPLICATION_ID, CLIENT_KEY); 
//		}
//		catch(Exception e){
//
//		}
		// Specify an Activity to handle all pushes by default.
		PushService.setDefaultPushCallback(this, PeopleActivity.class);

		usernameEditText = (EditText) findViewById(R.id.username);

		String username = ParseInstallation.getCurrentInstallation().getString("username");
		if(username != null)
			if(username.length() > 0){
				startActivity(new Intent(this, PeopleActivity.class));
				this.finish();
			}


	}

	public void onStart(){
		super.onStart();


	}
	public void saveUserProfile(View view) {	
		Log.d(TAG, "saving..");
		String usernameTextString = usernameEditText.getText().toString();

		if (usernameTextString.length() > 0) {
			ParseInstallation.getCurrentInstallation().put("username", usernameTextString);

			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(usernameEditText.getWindowToken(), 0);

			ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
				@Override
				public void done(ParseException e) {
					if (e == null) {
						Toast toast = Toast.makeText(getApplicationContext(), R.string.success_message, Toast.LENGTH_SHORT);
						toast.show();
						MainActivity.this.startActivity(new Intent(MainActivity.this, PeopleActivity.class));
						MainActivity.this.finish();
					} else {
						e.printStackTrace();

						Toast toast = Toast.makeText(getApplicationContext(), R.string.failure_message, Toast.LENGTH_SHORT);
						toast.show();
					}
				}
			});
		}
		else
			Toast.makeText(getApplicationContext(), "Username not valid!!", Toast.LENGTH_SHORT).show();

	}
}
