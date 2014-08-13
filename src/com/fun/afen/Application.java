package com.fun.afen;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
		PushService.setDefaultPushCallback(this, PeopleActivity.class);


		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);

		Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
	}

	public static void saveUsers(String usernames, Context context){
		SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("users", usernames);
		editor.commit();

	}

	public static ArrayList<String> getUsers(Context context){
		SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
		String users = sp.getString("users", "");
		ArrayList<String> people = new ArrayList<String>();
		if(users.length() > 0){
			Log.d("afen", "The users: " + users);
			people = new ArrayList<String>(Arrays.asList(users.substring(1, users.length()-1).split(", ")));
			Log.d("afen", "The users from array: " + people.toString());
		}
		return people;	
	}
}