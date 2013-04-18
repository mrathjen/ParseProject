package com.parse.starter;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.PushService;

public class ParseStarterProjectActivity extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//ParseAnalytics.trackAppOpened(this.getIntent());
		
		PushService.setDefaultPushCallback(this, ParseStarterProjectActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}
	
	/** Called when the user clicks the add task button */
	public void addTask(View view) {
		EditText taskText = (EditText) findViewById(R.id.newTaskText);
		String message = taskText.getText().toString();
		
		String user = "mark";//ParseUser.getCurrentUser().getUsername();
		
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
}
