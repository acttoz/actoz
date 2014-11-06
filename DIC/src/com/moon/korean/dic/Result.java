package com.moon.korean.dic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends Activity implements OnClickListener {
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
	TextView text_tobak;
	TextView text_korean;
	TextView text_mean;
	ImageView btn_input;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
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
		text_korean = (TextView) findViewById(R.id.korean);
		text_tobak = (TextView) findViewById(R.id.tobak);
		text_mean = (TextView) findViewById(R.id.mean);
		btn_input = (ImageView) findViewById(R.id.btn_input);
		btn_input.setOnClickListener(this);

		new JSONParse().execute();
	}

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Result.this);
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
					Temp.id = jsonobject.getString("id");
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
		switch (arg0.getId()) {

		case R.id.btn_input:
			Intent teacherIntent = new Intent(this, Input.class);
			teacherIntent.putExtra("SELECT", "update");
			startActivity(teacherIntent);
			Temp.saved = false;
			break;

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (Temp.saved)
			new JSONParse().execute();
	}

}