package com.moon.korean.dic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class Find extends Activity implements OnClickListener {
	ListView listView;
	String mPeriod, mChapter, mTime;
	boolean dbCopied;
	DayHelper mHelper;
	Handler mHandler;
	private boolean mFlag = false;
	Toast finishToast;
	SQLiteDatabase db;
	SQLiteDatabase dbWrite;
	SharedPreferences idPrefs;
	SharedPreferences firstRun = null;
	SharedPreferences.Editor editor;
	Adapter_QuizView listAdapter;
	Custom_List_Data data;
	Custom_List_Data data2;
	Custom_List_Data data3;
	Custom_List_Data data4;
	ArrayList<Custom_List_Data> dateList;
	Intent intent, popIntent;
	Drawable alpha1;
	Drawable alpha2;
	Drawable alpha3;
	public static String viewFlag;
	ProgressDialog mProgressDialog;
	String tempPrd;
	String tempCht;
	String tempTim;
	String tableName = "";
	Button searchBtn;
	Boolean isTeacher;
	int syncTime;
	String jsonUrl;
	static ArrayAdapter<String> schoolAdapter;
	JSONObject jsonobject = null;
	JSONArray jsonarray = null;
	ArrayList<HashMap<String, String>> arraylist;
	TextView temp;
	int tempNum = 0;
	boolean stop = false;
	EditText input;
	public static String word;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_view);
		word = null;
		syncTime = 10000;
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		 
		isTeacher = idPrefs.getBoolean("ISTEACHER", false);
		finishToast = Toast.makeText(Find.this,
				"'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
		if (isTeacher) {
			tableName = "result_class";
		} else {
			tableName = "result";
		}
		input = (EditText) findViewById(R.id.input);

		input.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event)

			{
				// TODO Auto-generated method stub
				switch (actionId) {
				case EditorInfo.IME_ACTION_SEARCH:
					word = input.getText().toString();
					initTime(word);
					editor.putString("SEARCH", word);
					editor.commit();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
					return true;
				default:
					return false;
				}

				// TODO Auto-generated method stub

			}

		});

		openDb();
		mPeriod = idPrefs.getString("PERIOD", "");
		mChapter = idPrefs.getString("CHAPTER", "");
		mTime = idPrefs.getString("TIME", "");
		// 상단 버튼 등록
		searchBtn = (Button) findViewById(R.id.search_btn);
		searchBtn.setOnClickListener(this);

		listView = (ListView) findViewById(R.id.listview3);

		dateList = new ArrayList<Custom_List_Data>();

		listAdapter = new Adapter_QuizView(this, R.layout.customlist, dateList);
		// dateListView.setCacheColorHint(Color.rgb(255,2555,255));
		listView.setAdapter(listAdapter);

		listAdapter.notifyDataSetChanged();
		setClickItem();
		initTime("");
	}

	public void initTime(String word) {
		// openDb();
		Log.d("word", word);
		openDb();
		Cursor cursor = db.rawQuery(
				"SELECT DISTINCT col_4 FROM math_test WHERE col_4 LIKE '%"
						+ word + "%'", null);
		dateList.clear();
		if (cursor.moveToFirst()) {
			if (cursor.getString(0).equals("")) {
				data = new Custom_List_Data();
				data.Data = "일치하는 단어가 없습니다.";
				dateList.add(data);
			} else {
				do {
					data = new Custom_List_Data();
					data.Data = cursor.getString(0);
					dateList.add(data);

				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		db.close();
		listAdapter.notifyDataSetChanged();
		listView.invalidate();
	}

	public void setClickItem() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			// 차시
			public void onItemClick(AdapterView<?> arg0, View arg1,

			int position, long arg3) {
				openDb();
				Cursor cursor = db.rawQuery(
						"SELECT DISTINCT col_0,col_1,col_2,col_3 FROM math_test WHERE col_4 LIKE '%"
								+ dateList.get(position).Data + "%'", null);
				if (cursor.moveToFirst()) {
					 
				}
				cursor.close();
				db.close();
			}
		});
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.search_btn:

			word = input.getText().toString();
			initTime(word);
			editor.putString("SEARCH", word);
			editor.commit();
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
			break;

		}

	}

	public void openDb() {

		mHelper = new DayHelper(Find.this);
		db = mHelper.getReadableDatabase();
		dbWrite = mHelper.getWritableDatabase();
	}

	

}