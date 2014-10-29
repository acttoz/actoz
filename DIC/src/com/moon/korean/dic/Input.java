package com.moon.korean.dic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Input extends Activity implements OnClickListener {
	// URL to get JSON Array
	private static String url;
	// JSON Node Names
	private static final String TAG_DIC = "dic";
	private static final String TAG_KOREAN = "korean";
	private static final String TAG_TOBAK = "tobak";
	private static final String TAG_MEAN = "mean";

	private static String TAG;
	JSONArray jsonResult = null;
	ListView listView;
	Toast finishToast;
	Adapter_QuizView listAdapter;
	Custom_List_Data data;
	ArrayList<Custom_List_Data> dateList;
	Drawable alpha1;
	Drawable alpha2;
	Drawable alpha3;
	ProgressDialog mProgressDialog;
	JSONObject jsonobject = null;
	EditText input;
	String WORD;
	String SELECT;
	EditText text_tobak;
	EditText text_korean;
	EditText text_mean;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input);
		Intent i = getIntent();
		// Get the results of rank
		SELECT = i.getStringExtra("SELECT");
		SELECT = SELECT.replaceAll(" ", "%20");
		WORD = i.getStringExtra("WORD");
		WORD = WORD.replaceAll(" ", "%20");

		if (SELECT.equals("korean")) {
			TAG = TAG_KOREAN;
		} else {
			TAG = TAG_TOBAK;
		}
		url = "http://actoze.dothome.co.kr/dic/math.php?select=" + SELECT
				+ "_result&word=" + WORD;
		text_korean = (EditText) findViewById(R.id.korean);
		text_tobak = (EditText) findViewById(R.id.tobak);
		text_mean = (EditText) findViewById(R.id.mean);

		new JSONParse().execute();
	}

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Input.this);
			pDialog.setMessage("Getting Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			JSONfunctions jParser = new JSONfunctions();
			// Getting JSON from URL
			JSONObject json = jParser.getJSONfromURL(url);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			pDialog.dismiss();
			try {
				// Getting JSON Array
				Log.d("json", "before");
				if (json != null) {
					jsonResult = json.getJSONArray(TAG_DIC);
					// Locate the array name

					jsonobject = jsonResult.getJSONObject(0);
					// Retrive JSON Objects
					Temp.tobak = jsonobject.getString(TAG_TOBAK);
					Temp.korean = jsonobject.getString(TAG_KOREAN);
					Temp.mean = jsonobject.getString(TAG_MEAN);

					text_tobak.setText(Temp.tobak);
					text_korean.setText(Temp.korean);
					text_mean.setText(Temp.mean);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}