package com.parse.starter;

import com.parse.Parse;
import com.parse.ParseACL;

import com.parse.ParseUser;
import com.parse.ParseObject;

import android.app.Application;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this, "PvbFwCBHdbzIUEZ6X4As2jWcJBc9cI8i10vWGbOj", "HNbd40YHVSGmNbqBNP9ySySM4QaeSrYeFxsqY8Gx");


		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
	    
		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);
		
		ParseACL.setDefaultACL(defaultACL, true);
		
		/*
		ParseObject testObject = new ParseObject("MarksObject");
		testObject.put("class", "cse403");
		testObject.put("project", "DineOn");
		testObject.saveInBackground();
		*/
	}

}
