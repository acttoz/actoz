package a.kr.co.moon.result;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import a.kr.co.moon.result.R;
import a.kr.co.moon.result.Math_Select_Main.MyThread;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Math_Quiz_Main extends Activity implements OnClickListener {
	ImageView num1, num2, num3;
	Cursor cursor;

	int tAnswer[] = new int[15];
	String tTemp[] = new String[15];

	TextView tHint1, tHint2, tHint3;
	TextView tHints[] = { tHint1, tHint2, tHint3 };
	int tHintId[] = { R.id.h1, R.id.h2, R.id.h3 };

	ImageView pQuiz1, pQuiz2, pQuiz3;
	ImageView pQuizs[] = { pQuiz1, pQuiz2, pQuiz3 };
	int pQuizId[] = { R.id.p1, R.id.p2, R.id.p3 };

	LinearLayout plQuiz1, plQuiz2, plQuiz3;
	LinearLayout plQuizs[] = { plQuiz1, plQuiz2, plQuiz3 };
	int plQuizId[] = { R.id.pl1, R.id.pl2, R.id.pl3 };

	private ProgressDialog progressDialog;
	ProgressDialog mProgressDialog;
	TextView tQuiz1, tQuiz2, tQuiz3, quizTitle;
	TextView tQuizs[] = { tQuiz1, tQuiz2, tQuiz3 };
	int tQuizId[] = { R.id.q1, R.id.q2, R.id.q3 };
	LinearLayout tlQuiz1, tlQuiz2, tlQuiz3;
	LinearLayout tlQuizs[] = { tlQuiz1, tlQuiz2, tlQuiz3 };
	int tlQuizId[] = { R.id.ql1, R.id.ql2, R.id.ql3 };

	ImageView tResult1, tResult2, tResult3;
	ImageView tResults[] = { tResult1, tResult2, tResult3 };
	int tResultId[] = { R.id.result1, R.id.result2, R.id.result3 };

	TextView tExam1, tExam2, tExam3, tExam4, tExam5, tExam6, tExam7, tExam8,
			tExam9, tExam10, tExam11, tExam12, tExam13, tExam14, tExam15;
	TextView tExams[] = { tExam1, tExam2, tExam3, tExam4, tExam5, tExam6,
			tExam7, tExam8, tExam9, tExam10, tExam11, tExam12, tExam13,
			tExam14, tExam15 };
	int tExamId[] = { R.id.e1, R.id.e2, R.id.e3, R.id.e4, R.id.e5, R.id.e6,
			R.id.e7, R.id.e8, R.id.e9, R.id.e10, R.id.e11, R.id.e12, R.id.e13,
			R.id.e14, R.id.e15 };

	Drawable dr1, dr2, dr3, dr4, dr5, dr6, dr7, dr8, dr9, dr10, dr11, dr12,
			dr13, dr14, dr15;
	Drawable drs[] = { dr1, dr2, dr3, dr4, dr5, dr6, dr7, dr8, dr9, dr10, dr11,
			dr12, dr13, dr14, dr15 };
	ImageView aExam1, aExam2, aExam3, aExam4, aExam5, aExam6, aExam7, aExam8,
			aExam9, aExam10, aExam11, aExam12, aExam13, aExam14, aExam15;
	ImageView aExams[] = { aExam1, aExam2, aExam3, aExam4, aExam5, aExam6,
			aExam7, aExam8, aExam9, aExam10, aExam11, aExam12, aExam13,
			aExam14, aExam15 };
	int aExamId[] = { R.id.a1, R.id.a2, R.id.a3, R.id.a4, R.id.a5, R.id.a6,
			R.id.a7, R.id.a8, R.id.a9, R.id.a10, R.id.a11, R.id.a12, R.id.a13,
			R.id.a14, R.id.a15 };
	// R.id.a6,
	// R.id.a7, R.id.a8, R.id.a9, R.id.a10, R.id.a11, R.id.a12, R.id.a13,
	// R.id.a14, R.id.a15 };
	int solvedQ = 0;
	int chooseA;
	private AlertDialog mDialog = null;
	View quiz1, quiz2, quiz3;
	String mPeriod, mChapter, mTime;
	boolean dbCopied;
	DayHelper mHelper;
	SQLiteDatabase db;
	SharedPreferences idPrefs;
	SharedPreferences firstRun = null;
	SharedPreferences.Editor editor;
	Custom_List_Adapter listAdapter1, listAdapter2, listAdapter3;
	Custom_List_Data data;
	Custom_List_Data data2;
	Custom_List_Data data3;
	ArrayList<Custom_List_Data> dateList1, dateList2, dateList3;
	AlarmManager mManager;
	NotificationManager mNotification;
	Intent intent, popIntent;
	Drawable alpha1;
	Drawable alpha2;
	Drawable alpha3;

	int isSolved1;
	int isSolved2;
	int isSolved3;
	int isRight1 = 0;
	int isRight2 = 0;
	int isRight3 = 0;
	String tempPrd;
	String tempCht;
	String tempTim;
	String subject[] = { "korean", "society", "math", "science", "english",
			"ethic", "practical", "physic", "music", "art" };
	int average[] = new int[14];

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.math_quiz_main);
		copySQLiteDB(this);

		openDb();
		// 과목별 평균구하기
		Cursor cursorAverage = db
				.rawQuery(
						"SELECT sum(korean2),sum(math2),sum(society2),sum(science2),sum(english2)"
								+ ",sum(ethic2),sum(practical2),sum(physic2),sum(music2),sum(art2)"
								+ ",sum(korean1),sum(society1),sum(math1),sum(science1),Count(korean2)"
								+ " FROM result", null);
		if (cursorAverage.moveToFirst()) {
			for (int i = 0; i < average.length; i++) {
				average[i] = cursorAverage.getInt(i) / cursorAverage.getInt(14);
				Log.d("평균",
						Integer.toString(cursorAverage.getInt(i)
								/ cursorAverage.getInt(14)));
			}
		}

		Cursor cursor = db.rawQuery(
				"SELECT korean2,math2,society2,science2,english2"
						+ ",ethic2,practical2,physic2,music2,art2"
						+ ",korean1,society1,math1,science1" + " FROM result",
				null);
		if (cursor.moveToFirst()) {
			ContentValues row = new ContentValues();
			do {
				// 과목 루프
				for (int i = 0; i < 10; i++) {
					// 영역 루프
					for (int j = 0; j < 5; j++) {
						int sub_result = cursor.getInt(i);
						int sub_average = average[i];
						if (i < 4) {
							// ************ 필수
							int temp1 = 1 + (int) (Math.random() * 2);
							if (temp1 > 1) {
								sub_result = cursor.getInt(i + 10);
								sub_average = average[i + 10];
							}
						}
						int temp2 = 1 + (int) (Math.random() * 10);

						// 오차 수치
						int minus = 4;
						int sub_grade = 0;
						if (sub_result > sub_average) {
							// 상

							sub_grade = 3;

						} else if (sub_result > sub_average - 15) {
							// 중

							sub_grade = 2;
							if (temp2 < minus) {
								sub_grade = 3;
							}
						} else {
							// 하

							sub_grade = 1;
							if (temp2 < minus) {
								sub_grade = 2;
							}
						}
						row.put(subject[i] + Integer.toString(j), sub_grade);
					}

				}
				// 학생 1명의 모든 성적 입력
				db.insert("result_out", null, row);

			} while (cursor.moveToNext());
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.num1:
			initView1();
			break;
		case R.id.num2:
			initView2();
			break;
		case R.id.num3:
			initView3();
			break;
		case R.id.backbtn:
			finish();
			break;

		default:
			for (int i = 0; i < 15; i++) {
				if (v.getId() == aExamId[i]) {
					drs[i].setColorFilter(0xffffff55, Mode.MULTIPLY);
					mDialog = createDialog((String) tExams[i].getText());
					mDialog.show();
					mDialog.getWindow().getAttributes();
					TextView textView = (TextView) mDialog
							.findViewById(android.R.id.message);
					// TextView text2View = (TextView) mDialog
					// .findViewById(android.R.id.title);
					textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
					// text2View.setTextSize(40);
					Button btn1 = mDialog
							.getButton(DialogInterface.BUTTON_NEGATIVE);
					Button btn2 = mDialog
							.getButton(DialogInterface.BUTTON_POSITIVE);
					btn1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
					btn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
					chooseA = i;

				}
			}
			break;

		}

	}

	public void openDb() {

		mHelper = new DayHelper(Math_Quiz_Main.this);
		db = mHelper.getReadableDatabase();
	}

	private void copySQLiteDB(Context context) {

		AssetManager manager = context.getAssets();
		String filePath = "/data/data/" + "a.kr.co.moon.result" + "/databases/"
				+ "quiz.db";
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

	public void initQuiz() {
		openDb();
		int i = 0;
		Cursor cursorSolved = db.rawQuery(
				"SELECT col_9 FROM math_test where col_1='" + mChapter
						+ "' and col_2='" + mTime + "'", null);
		cursorSolved.moveToFirst();

		do {
			i++;

			if (i < 6) {
				isSolved1 += cursorSolved.getInt(0);
			} else if (i < 11) {
				isSolved2 += cursorSolved.getInt(0);
			} else {
				isSolved3 += cursorSolved.getInt(0);
			}

		} while (cursorSolved.moveToNext());
		cursorSolved.close();

		Cursor cursor = db.rawQuery(
				"SELECT col_3,col_4,col_5,col_6,col_7,col_8,col_9 FROM math_test where col_1='"
						+ mChapter + "' and col_2='" + mTime + "'", null);
		cursor.moveToFirst();
		i = 0;
		do {
			tAnswer[i] = cursor.getInt(4);
			tTemp[i] = cursor.getString(5);
			if ((i + 1) % 5 == 1)
				tExams[i].setText(" ① " + cursor.getString(3));
			if ((i + 1) % 5 == 2)
				tExams[i].setText(" ② " + cursor.getString(3));
			if ((i + 1) % 5 == 3)
				tExams[i].setText(" ③ " + cursor.getString(3));
			if ((i + 1) % 5 == 4)
				tExams[i].setText(" ④ " + cursor.getString(3));
			if ((i + 1) % 5 == 0)
				tExams[i].setText(" ⑤ " + cursor.getString(3));

			if (isSolved1 == 1 && i < 5) {
				tExams[i].setTextColor(Color.parseColor("#bfbfbf"));
				tExams[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				solvedCheck(i, cursor);
			} else if (isSolved2 == 1 && i < 10) {
				tExams[i].setTextColor(Color.parseColor("#bfbfbf"));
				tExams[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				solvedCheck(i, cursor);
			} else if (isSolved3 == 1 && i < 15) {
				tExams[i].setTextColor(Color.parseColor("#bfbfbf"));
				tExams[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				solvedCheck(i, cursor);
			}

			i++;
		} while (cursor.moveToNext());

		cursor.close();

		Cursor cursor2 = db.rawQuery(
				"SELECT DISTINCT col_4,col_8 FROM math_test where col_1='"
						+ mChapter + "' and col_2='" + mTime + "'", null);
		cursor2.moveToFirst();

		i = 0;
		do {
			tQuizs[i].setText(Integer.toString(i + 1) + ". "
					+ cursor2.getString(0));
			tHints[i].setText(cursor2.getString(1));

			String tmpSign = "q" + tempPrd + tempCht + tempTim
					+ Integer.toString(i + 1);

			Log.d("dd", "이미지" + tmpSign);
			int lid = this.getResources().getIdentifier(tmpSign, "drawable",
					this.getPackageName());

			// 이미지가 없을 경우
			if (lid == 0) {
				// plQuizs[i].setLayoutParams(new LinearLayout.LayoutParams(
				// LayoutParams.WRAP_CONTENT, 0, 0));
				// tlQuizs[i].setLayoutParams(new LinearLayout.LayoutParams(
				// LayoutParams.FILL_PARENT, 0, 4));
				// tQuizs[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 2);
			}

			Log.d("dd", "이미지아이디" + lid);
			pQuizs[i].setImageResource(lid);

			i++;
		} while (cursor2.moveToNext());
		cursor2.close();
		db.close();
	}

	private void solvedCheck(int i, Cursor cursor) {
		// TODO Auto-generated method stub
		if (cursor.getInt(6) == 1) {
			solvedQ++;
			// 오답
			tHints[((int) i / 5)].setVisibility(View.VISIBLE);
			tHints[((int) i / 5)].setClickable(true);
			tExams[i].setTextColor(Color.parseColor("#ff0000"));
			tExams[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			if (tAnswer[i] == 1) {
				tResults[((int) i / 5)].setImageResource(R.drawable.right);
			} else {
				tResults[((int) i / 5)].setImageResource(R.drawable.wrong);
			}

		}

		if (cursor.getInt(4) == 1) {
			// 정답
			tExams[i].setTextColor(Color.parseColor("#0000ff"));
			tExams[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		}
	}

	private void initView1() {

		quiz1.setVisibility(View.VISIBLE);
		quiz2.setVisibility(View.GONE);
		quiz3.setVisibility(View.GONE);

		alpha1.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffffff, Mode.MULTIPLY);
	}

	private void initView2() {

		quiz1.setVisibility(View.GONE);
		quiz2.setVisibility(View.VISIBLE);
		quiz3.setVisibility(View.GONE);

		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffffff, Mode.MULTIPLY);
	}

	private void initView3() {

		quiz1.setVisibility(View.GONE);
		quiz2.setVisibility(View.GONE);
		quiz3.setVisibility(View.VISIBLE);

		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffff55, Mode.MULTIPLY);
	}

	private AlertDialog createDialog(final String text) {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("확인");
		ab.setMessage("선택한 것이  '" + text + "' 이 맞습니까?");

		ab.setCancelable(false);
		ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));

		ab.setPositiveButton("예", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				setDismiss(mDialog);

				tHints[(int) chooseA / 5].setVisibility(View.VISIBLE);
				tHints[(int) chooseA / 5].setClickable(true);
				openDb();
				Log.d("dd", "update math_test set col_9='1' where col_8='"
						+ tTemp[chooseA] + "' and col_6='"
						+ text.toString().substring(3) + "'");
				db.execSQL("update math_test set col_9='1' where col_8='"
						+ tTemp[chooseA] + "' and col_6='" + text.substring(3)
						+ "'");
				db.close();
				if (tAnswer[chooseA] == 1) {

					Log.d("dd", "정답");
					if (chooseA < 5) {
						isRight1 = 1;
					} else if (chooseA < 10) {
						isRight2 = 1;
					} else {
						isRight3 = 1;
					}

				}

				solvedQ++;

				new DownloadJSON().execute();
				for (int i = 0; i < 15; i++)
					drs[i].setColorFilter(0xffffffff, Mode.MULTIPLY);
				Animation ani = AnimationUtils.loadAnimation(
						Math_Quiz_Main.this, R.anim.fadein);
				tResults[((int) chooseA / 5)].startAnimation(ani);
			}

		});

		ab.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				setDismiss(mDialog);
				for (int i = 0; i < 15; i++)
					drs[i].setColorFilter(0xffffffff, Mode.MULTIPLY);
			}
		});

		return ab.create();
	}

	public void callUrl(String webUrl) {
		try {
			URL url = new URL(webUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn != null) {
				conn.setConnectTimeout(1000); // 1초 동안 인터넷 연결을 실패할경우 Fall 처리
				conn.setUseCaches(false);
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					Log.d("dd", "호출 성공");

				} else {
					Log.d("dd", "호출 실패");

				}
				conn.disconnect();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void setDismiss(Dialog dialog) {
		if (dialog != null && dialog.isShowing())
			dialog.dismiss();
	}

	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(Math_Quiz_Main.this);
			// Set progressdialog title
			mProgressDialog.setTitle("결과 제출");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			Log.d("dd", "제출");
			openDb();
			db.execSQL("INSERT INTO result (period,chapter,time,num1,num2,num3) VALUES "
					+ "('"
					+ tempPrd
					+ "','"
					+ tempCht
					+ "','"
					+ tempTim
					+ "','"
					+ isRight1
					+ "','"
					+ isRight2
					+ "','"
					+ isRight3
					+ "' );");
			db.close();
			String submitUrl = String
					.format(

					"http://actoz.dothome.co.kr/math/math.php?select=submit&class=%s&no=%s&name=%s&period=%s&chapter=%s&time=%s&num1=%s&num2=%s&num3=%s",
							URLEncoder.encode(idPrefs.getString("SCHOOL", ""))
									+ idPrefs.getString("BAN", ""),
							idPrefs.getString("NUM", ""),
							URLEncoder.encode(idPrefs.getString("NAME", "")),
							tempPrd, tempCht, tempTim, isRight1, isRight2,
							isRight3);
			Log.d("dd", submitUrl);
			if (solvedQ > 2)
				callUrl(submitUrl);
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {
			// Locate the listview in listview_main.xml
			// listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			// adapter = new ListViewAdapter(MainActivity.this, arraylist);
			// Binds the Adapter to the ListView
			// listview.setAdapter(adapter);

			// Close the progressdialog
			initQuiz();
			mProgressDialog.dismiss();
		}
	}

}