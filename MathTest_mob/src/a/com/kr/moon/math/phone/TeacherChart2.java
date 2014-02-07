package a.com.kr.moon.math.phone;

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
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class TeacherChart2 extends Activity {
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
	Student_Adapter listAdapter5;
	Custom_List_Data data;
	Custom_List_Data data2;
	Custom_List_Data data3;
	Custom_List_Data data4;
	Student_Data data5;
	ArrayList<Custom_List_Data> dateList;
	AlarmManager mManager;
	NotificationManager mNotification;
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
	public AlertDialog mDialog = null;
	int limit;
	int period;
	ArrayList<String> name;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_view2);
		name = new ArrayList<String>();
		word = null;
		syncTime = 10000;
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		dbCopied = idPrefs.getBoolean("DBCOPY", false);
		if (!dbCopied)
			copySQLiteDB(this);
		isTeacher = idPrefs.getBoolean("ISTEACHER", false);
		finishToast = Toast.makeText(TeacherChart2.this,
				"'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
		if (isTeacher) {
			tableName = "result_class";
		} else {
			tableName = "result";
		}
		Toast toast = Toast.makeText(this, "견본:1학기 1번 김결", 5000);
		LinearLayout linearLayout = null;
		linearLayout = (LinearLayout) toast.getView();
		View child = linearLayout.getChildAt(0);
		TextView messageTextView = null;
		messageTextView = (TextView) child;
		messageTextView.setTextSize(20);
		toast.show();
		openDb();
		mPeriod = idPrefs.getString("PERIOD", "");
		mChapter = idPrefs.getString("CHAPTER", "");
		mTime = idPrefs.getString("TIME", "");
		// 상단 버튼 등록

		listView = (ListView) findViewById(R.id.listview3);

		dateList = new ArrayList<Custom_List_Data>();

		listAdapter = new Adapter_QuizView(this, R.layout.customlist, dateList);
		// dateListView.setCacheColorHint(Color.rgb(255,2555,255));
		listView.setAdapter(listAdapter);

		listAdapter.notifyDataSetChanged();
		setClickItem();
		mDialog = resultDialog();
		mDialog.show();
		mDialog.getWindow().getAttributes();
		TextView textView = (TextView) mDialog
				.findViewById(android.R.id.message);

		// textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

		Button btn1 = mDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

		Button btn2 = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);

		btn1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		btn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

	}

	public AlertDialog resultDialog() {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);

		ab.setTitle("학기 선택");
		ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		ab.setPositiveButton("1학기", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				initTime(1);
			}

		});

		ab.setNegativeButton("2학기", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				initTime(2);

			}
		});

		return ab.create();
	}

	public void initTime(int period) {
		// openDb();
		this.period = period;
		String mPeriod = Integer.toString(period);
		openDb();
		Cursor cursor = db.rawQuery(
				"SELECT distinct no,name FROM result_class where period='"
						+ period + "' ORDER BY no ASC;", null);
		dateList.clear();
		if (cursor.moveToFirst()) {

			do {
				data = new Custom_List_Data();
				data.Data = cursor.getString(0) + ". " + cursor.getString(1);
				dateList.add(data);
				name.add(cursor.getString(1));

			} while (cursor.moveToNext());
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
				Intent menu4Intent = new Intent(TeacherChart2.this,
						TeacherChart3.class);
				menu4Intent.putExtra("PERIOD", period);
				menu4Intent.putExtra("NAME", name.get(position));
				Log.d("차트", period + name.get(position));
				startActivity(menu4Intent);
			}
		});
	}

	public void openDb() {

		mHelper = new DayHelper(TeacherChart2.this);
		db = mHelper.getReadableDatabase();
		dbWrite = mHelper.getWritableDatabase();
	}

	private void copySQLiteDB(Context context) {

		editor.putBoolean("DBCOPY", true);
		editor.commit();

		AssetManager manager = context.getAssets();
		String filePath = "/data/data/" + "a.com.kr.moon.math.phone"
				+ "/databases/" + "quiz.db";
		File file = new File(filePath);

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			InputStream is = manager.open("quiz.db");
			BufferedInputStream bis = new BufferedInputStream(is);

			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);

			int read = -1;
			byte[] buffer = new byte[1024];
			while ((read = bis.read(buffer, 0, 1024)) != -1) {
				bos.write(buffer, 0, read);
			}
			bos.flush();

			bos.close();
			fos.close();
			bis.close();
			is.close();

		} catch (IOException e) {
			Log.e("ErrorMessage : ", e.getMessage());
		}
	}

}