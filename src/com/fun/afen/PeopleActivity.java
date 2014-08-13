package com.fun.afen;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SendCallback;

public class PeopleActivity extends Activity {
	ListView lv = null;
	ArrayList<String> people = null;
	ArrayAdapter<String> adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_people);

		ParseAnalytics.trackAppOpened(getIntent());

		final String myname = ParseInstallation.getCurrentInstallation().getString("username");

		final EditText sat = (EditText) findViewById(R.id.destination);

		TextView me = (TextView) findViewById(R.id.myname);
		if(myname != null)
			me.setText("aFen a " + myname);




		lv = (ListView) findViewById(android.R.id.list);
		people = new ArrayList<>();
		//people.add("abdellah");

		adapter = new ArrayAdapter<>(getApplicationContext(), 
				android.R.layout.simple_list_item_1, people);
		lv.setAdapter(adapter);
		lv.setEmptyView(findViewById(android.R.id.empty));
		lv.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final ProgressDialog dialog = ProgressDialog.show(PeopleActivity.this, "aFeen!", 
						"Sending..", true);
				dialog.show();
				ParseQuery query = ParseInstallation.getQuery();
				query.whereEqualTo("username", people.get(position));

				ParsePush push = new ParsePush();
				String yourMessage = "From: " + myname +" , aFen a " + people.get(position) ;//I want to get this message from other device?
				push.setQuery(query);
				push.setMessage(yourMessage);

				push.sendInBackground(new SendCallback() {

					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						if(e == null)
							Toast.makeText(getApplicationContext(), R.string.success_message, Toast.LENGTH_SHORT).show();
						else{
							Toast.makeText(getApplicationContext(), R.string.failure_message, Toast.LENGTH_SHORT).show();
							Log.d("afen", "The exception: " + e.getMessage());
						}
					}
				});

			}
		});

		Button push = (Button) findViewById(R.id.push);
		push.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(sat.getText().toString().length() > 0){
					people.add(sat.getText().toString());
					Log.d("afen", "people: " + people.toString());

					Application.saveUsers(people.toString(), getApplicationContext());
					people = Application.getUsers(getApplicationContext());
					adapter.notifyDataSetChanged();

					sat.setText("");
				}
				else{
					Toast.makeText(getApplicationContext(), "Username not valid!!", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	public void onResume(){
		super.onResume();
		Log.d("afen", "resume..");
		people = Application.getUsers(getApplicationContext());
		Log.d("afen", "The people in onresume: " + people.toString() + " count: " + people.size());
		adapter = new ArrayAdapter<>(getApplicationContext(), 
				android.R.layout.simple_list_item_1, people);
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
}
