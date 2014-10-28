package com.moon.korean.dic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {
	SharedPreferences idPrefs;
	SharedPreferences firstRun = null;
	SharedPreferences.Editor editor;
	static String grade = "null";
	static String ban = "null";
	static String mSchool;
	ImageButton bFind;
	ImageButton bInput;
	boolean dbCopied;
	Drawable alpha1, alpha2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.math_main);
		// TODO Auto-generated method stub
		bFind = (ImageButton) findViewById(R.id.find);
		bInput = (ImageButton) findViewById(R.id.input);
		bFind.setOnClickListener(this);
		bInput.setOnClickListener(this);

		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();

		dbCopied = idPrefs.getBoolean("DBCOPY", false);
		grade = idPrefs.getString("NAME", "null");
		ban = idPrefs.getString("BAN", "null");
		mSchool = idPrefs.getString("SCHOOL", "null");
		DayHelper mHelper = new DayHelper(MainActivity.this);
		SQLiteDatabase db = mHelper.getReadableDatabase();
		db.close();
		if (!dbCopied)
			copySQLiteDB(this);

		if (grade.equals("null") | ban.equals("null") | mSchool.equals("null")) {
			// C2dm_BroadcastReceiver.c2dmIdCreate(this);

			// Intent firstLogin = new Intent(this, FirstLogin.class);
			// startActivity(firstLogin);
			// finish();
		}
		// '
		// onClick(bInput);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.find:
			alpha1 = bFind.getBackground();

			alpha1.setColorFilter(0x88FF0000, Mode.SRC_ATOP);
			Intent teacherIntent = new Intent(this, Find.class);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(teacherIntent);
			break;
		case R.id.input:
			alpha2 = bInput.getBackground();
			alpha2.setColorFilter(0x88FF0000, Mode.SRC_ATOP);
			Intent gradeIntent = new Intent(this, Find.class);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(gradeIntent);
			break;

		}
	}

	private void copySQLiteDB(Context context) {

		editor.putBoolean("DBCOPY", true);
		editor.commit();

		Log.d("math", "db����");

		AssetManager manager = context.getAssets();
		String filePath = "/data/data/" + "com.moon.korean.dic"
				+ "/databases/" + "dic.db";
		File file = new File(filePath);

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			InputStream is = manager.open("dic.db");
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
		alpha1 = bFind.getBackground();
		alpha2 = bInput.getBackground();
		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);
	}
}