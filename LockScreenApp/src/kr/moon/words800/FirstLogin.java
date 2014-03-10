package kr.moon.words800;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FirstLogin extends Activity implements OnClickListener {
	private ProgressDialog progressDialog;
	ProgressDialog mProgressDialog;
	private AlertDialog mDialog = null;
	String ban = null;
	String num = null;
	String school = null;
	String grade = null;
	String name = null;
	Button banEdit;
	Button gradeEdit;
	Button numEdit;
	Button idSaveBtn;
	Button schoolBtn;
	Button newBtn;
	TextView title1, title2;
	boolean dbCopied = false;
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	File idXml, dayDb;
	DayHelper mHelper;
	SQLiteDatabase db;
	EditText schoolSearch;
	EditText nameInput;
	int newVer;
	private static final String LOGTAG = "BannerTypeXML1";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstlogin);
		// checkNetwokState();
		openDb();
		db.close();
		// TODO Auto-generated method stub
		schoolSearch = (EditText) findViewById(R.id.schoolInput);
		nameInput = (EditText) findViewById(R.id.nameInput);

		idSaveBtn = (Button) findViewById(R.id.idSaveBtn);
		idSaveBtn.setOnClickListener(this);


		schoolBtn = (Button) findViewById(R.id.schoolBtn);
		schoolBtn.setOnClickListener(this);

		gradeEdit = (Button) findViewById(R.id.gradeBtn);
		gradeEdit.setOnClickListener(this);
		
		banEdit = (Button) findViewById(R.id.banBtn);
		banEdit.setOnClickListener(this);

		 
		title1 = (TextView) findViewById(R.id.title1);

		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		dbCopied = idPrefs.getBoolean("DBCOPY", false);

		if (!dbCopied)
			copySQLiteDB(this);

	}

	public void openDb() {

		mHelper = new DayHelper(FirstLogin.this);
		db = mHelper.getReadableDatabase();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		String mName;
		switch (v.getId()) {
		case R.id.schoolBtn:
			// firstLoginIntent.putExtra("BAN", ban);

			String mSchoolSearch = schoolSearch.getText().toString();
			if (mSchoolSearch.equals("")) {
				Toast toast = Toast.makeText(this, "검색어를 입력해주세요.",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				schoolSearch.setText("");
				mSchoolSearch = mSchoolSearch.replace("초등학교", "");
				mSchoolSearch = mSchoolSearch.replace("초등", "");
				Log.d("dd", "학교이름" + mSchoolSearch);

				Intent schoolIntent = new Intent(this, SchoolPicker.class);
				schoolIntent.putExtra("SCHOOL", mSchoolSearch);
				startActivity(schoolIntent);
			}
			break;

		case R.id.banBtn:
			Intent banIntent = new Intent(this, NumberPicker.class);
			banIntent.putExtra("MAX", 12);
			startActivity(banIntent);
			break;
		case R.id.gradeBtn:
			Intent gradeIntent = new Intent(this, NumberPicker.class);
			gradeIntent.putExtra("MAX", 6);
			startActivity(gradeIntent);
			break;

		 

		case R.id.idSaveBtn:
			mName = nameInput.getText().toString();
			if (idPrefs.getString("BAN", "") == ""
					| idPrefs.getString("GRADE", "") == ""
					| idPrefs.getString("SCHOOL", "") == ""
					| mName.equals("")) {
				Log.d("dd",
						idPrefs.getString("GRADE", "")
								+ idPrefs.getString("BAN", "")
								+ idPrefs.getString("SCHOOL", "학교") + mName);
				Toast toast = Toast.makeText(this, "학교와 학반을 정확히 입력해주세요!!",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				break;

			} else {
				editor.putString("NAME", mName);
				editor.commit();
				Intent firstLoginIntent = new Intent(this,
						LockScreenAppActivity.class);
				startActivity(firstLoginIntent);
				finish();
				break;
			}

		 

		}

	}

	 

	private void copySQLiteDB(Context context) {

		editor.putBoolean("DBCOPY", true);
		editor.commit();

		AssetManager manager = context.getAssets();
		String filePath = "/data/data/" + "kr.moon.words800" + "/databases/"
				+ "quiz.db";
		File file = new File(filePath);
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			Log.d("db", "복사");
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (idPrefs.getString("SCHOOL", "") == "") {
			school = "학교이름을 검색";
		} else {
			school = idPrefs.getString("SCHOOL", "");
		}

		name = idPrefs.getString("NAME", "");
		nameInput.setText(name);

		grade = idPrefs.getString("GRADE", "");
		if (grade.equals("")) {
			grade = "몇 학년인가요?";
		} else {
			grade = grade + "학년";
		}
		ban = idPrefs.getString("BAN", "");
		if (ban.equals("")) {
			ban = "몇 반인가요?";
		} else {
			ban = ban + "반";
		}

		 

		schoolSearch.setHint(school);
		banEdit.setText(ban);
		gradeEdit.setText(grade);

	}
}// 클래스
