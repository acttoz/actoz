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

public class FindK extends Activity implements OnClickListener {
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
	String SELECT;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_view);
		Intent i = getIntent();
		// Get the results of rank
		SELECT = i.getStringExtra("SELECT");
		if (SELECT.equals("KOREAN")) {
			Log.d("select", "korean");
			url = "http://actoze.dothome.co.kr/dic/math.php?select=korean";
			TAG = TAG_KOREAN;
		} else {
			Log.d("select", "tobak");
			url = "http://actoze.dothome.co.kr/dic/math.php?select=tobak";
			TAG = TAG_TOBAK;
		}

		finishToast = Toast.makeText(FindK.this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.",
				Toast.LENGTH_SHORT);
		// input.setOnEditorActionListener(new OnEditorActionListener() {
		//
		// @Override
		// public boolean onEditorAction(TextView v, int actionId,
		// KeyEvent event)
		//
		// {
		// // TODO Auto-generated method stub
		// switch (actionId) {
		// case EditorInfo.IME_ACTION_SEARCH:
		// word = input.getText().toString();
		// initTime(word);
		// editor.putString("SEARCH", word);
		// editor.commit();
		// InputMethodManager imm = (InputMethodManager)
		// getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
		// return true;
		// default:
		// return false;
		// }
		//
		// // TODO Auto-generated method stub
		//
		// }
		//
		// });

		// 상단 버튼 등록
		// searchBtn = (Button) findViewById(R.id.search_btn);
		// searchBtn.setOnClickListener(this);

		listView = (ListView) findViewById(R.id.listview3);

		dateList = new ArrayList<Custom_List_Data>();

		input = (EditText) findViewById(R.id.input);
		input.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String text = input.getText().toString()
						.toLowerCase(Locale.getDefault());

				listAdapter.filter(text);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			}
		});
		new JSONParse().execute();
	}

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(FindK.this);
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
					listAdapter = new Adapter_QuizView(FindK.this,
							R.layout.customlist, dateList, SELECT);
					// dateListView.setCacheColorHint(Color.rgb(255,2555,255));
					listView.setAdapter(listAdapter);
					listAdapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	// public void initTime(String word) {
	// // openDb();
	// Log.d("word", word);
	// openDb();
	// Cursor cursor = db.rawQuery(
	// "SELECT DISTINCT col_4 FROM math_test WHERE col_4 LIKE '%"
	// + word + "%'", null);
	// dateList.clear();
	// if (cursor.moveToFirst()) {
	// if (cursor.getString(0).equals("")) {
	// data = new Custom_List_Data();
	// data.Data = "일치하는 단어가 없습니다.";
	// dateList.add(data);
	// } else {
	// do {
	// data = new Custom_List_Data();
	// data.Data = cursor.getString(0);
	// dateList.add(data);
	//
	// } while (cursor.moveToNext());
	// }
	// }
	// cursor.close();
	// db.close();
	// listAdapter.notifyDataSetChanged();
	// listView.invalidate();
	// }

	// public void setClickItem() {
	// listView.setOnItemClickListener(new OnItemClickListener() {
	// // 차시
	// public void onItemClick(AdapterView<?> arg0, View arg1,
	//
	// int position, long arg3) {
	// openDb();
	// Cursor cursor = db.rawQuery(
	// "SELECT DISTINCT col_0,col_1,col_2,col_3 FROM math_test WHERE col_4 LIKE '%"
	// + dateList.get(position).Data + "%'", null);
	// if (cursor.moveToFirst()) {
	//
	// }
	// cursor.close();
	// db.close();
	// }
	// });
	// }

	// @Override
	// public void onClick(View v) {
	//
	// switch (v.getId()) {
	//
	// case R.id.search_btn:
	//
	// word = input.getText().toString();
	// // initTime(word);
	// editor.putString("SEARCH", word);
	// editor.commit();
	// InputMethodManager imm = (InputMethodManager)
	// getSystemService(Context.INPUT_METHOD_SERVICE);
	// imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
	// break;
	//
	// }
	//
	// }

	// public void openDb() {
	//
	// mHelper = new DayHelper(FindK.this);
	// db = mHelper.getReadableDatabase();
	// dbWrite = mHelper.getWritableDatabase();
	// }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}