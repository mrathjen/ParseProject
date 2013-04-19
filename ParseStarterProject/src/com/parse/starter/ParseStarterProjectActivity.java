package com.parse.starter;

import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.PushService;

/**
 * {"author":"Vaughn","msg":"Man"}","action":"com.parse.starter.UPDATE_STATUS
 * @author Jordan
 *
 */

public class ParseStarterProjectActivity extends Activity {
	private final String TAG = this.getClass().getSimpleName();
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//ParseAnalytics.trackAppOpened(this.getIntent());
		MyCustomReceiver rec = new MyCustomReceiver((TextView)this.findViewById(R.id.taskList));
		IntentFilter iff = new IntentFilter("com.parse.starter.UPDATE_STATUS");
		PushService.subscribe(this, "push", ParseStarterProjectActivity.class);
		this.registerReceiver(rec, iff);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}
	
	/** Called when the user clicks the add task button */
	public void addTask(View view) {
		EditText taskText = (EditText) findViewById(R.id.newTaskText);
		String message = taskText.getText().toString();
		
		String user = "Jordan";//ParseUser.getCurrentUser().getUsername();
		
		ParseObject testObject = new ParseObject("Task");
		testObject.put("message", message);
		testObject.put("author", user);
		testObject.saveInBackground();
	}
	
	/** Called when the user clicks the add task button */
	public void getTasks(View view) {
		final TextView list = (TextView) findViewById(R.id.taskList);
		
		ParseQuery query = new ParseQuery("Task");
		query.findInBackground(new FindCallback() {
		    public void done(List<ParseObject> taskList, ParseException e) {
		        if (e == null) {
		        	String newList = "";
		        	for (int i = 0; i < taskList.size(); i++) {
		        		newList += taskList.get(i).getString("author") + " - " + taskList.get(i).getString("message") + "\n";
		        	}
		        	if (!newList.equals("")) list.setText(newList);
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
		    }
		});
	}
	
	public void share(View v){
		
		try{
			EditText taskText = (EditText) findViewById(R.id.newTaskText);
			String message = taskText.getText().toString();
			
			String user = "Jordan";//ParseUser.getCurrentUser().getUsername();

			JSONObject data = new JSONObject();
	        data.put("action", "com.parse.starter.UPDATE_STATUS");
	        data.put("author", user);
	        data.put("message", message);
	
			Log.d(TAG, data.toString());
			ParsePush push = new ParsePush();
			push.setChannel("push");
			push.setData(data);
			push.sendInBackground();
		}
		catch(Exception e){
			Log.d(TAG, e.getMessage());
		}
	}
}
