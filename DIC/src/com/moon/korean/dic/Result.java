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

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		Intent i = getIntent();
		// Get the results of rank
		SELECT = i.getStringExtra("SELECT");
		WORD = i.getStringExtra("WORD");
		if (SELECT.equals("KOREAN")) {
			Log.d("select", "korean");
			url = "http://actoze.dothome.co.kr/dic/math.php?select=korean_result&word="+WORD;
			TAG = TAG_KOREAN;
		} else {
			Log.d("select", "tobak");
			url = "http://actoze.dothome.co.kr/dic/math.php?select=tobak_result&word="+WORD;
			TAG = TAG_TOBAK;
		}
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
					dateList.clear();

					for (int i = 0; i < jsonResult.length(); i++) {
						jsonobject = jsonResult.getJSONObject(i);
						// Retrive JSON Objects
						if (!jsonobject.getString(TAG).equals("")) {
							data = new Custom_List_Data(
									jsonobject.getString(TAG));
							dateList.add(data);
						}

						Log.d("json", data.getData());

					}
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