package com.fun.afen;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.parse.PushService;

public class Application extends android.app.Application {
	public static String APPLICATION_ID = "zy7fHjM1rWItRmM1zL5zgonu7Ole5U0SyCTR38ss";
	public static String CLIENT_KEY = "BCtLxryANEV2aKCSusMavS6Sd0qEZ60MlL3bcs0I";
	public Application() {
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// Initialize the Parse SDK.
		Parse.initialize(this, APPLICATION_ID, CLIENT_KEY); 

		// Specify an Activity to handle all pushes by default.
		PushService.setDefaultPushCallback(this, MainActivity.class);


		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);
	}
}