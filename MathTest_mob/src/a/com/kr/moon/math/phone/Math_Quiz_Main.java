package a.com.kr.moon.math.phone;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.R.bool;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Math_Quiz_Main extends Activity implements OnClickListener {
	ImageView num1, num2, num3;
	Cursor cursor;
	SoundPool mPool;
	int sRight;
	int sWrong;
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

	String tmpSign;

	int isSolved1;
	int isSolved2;
	int isSolved3;
	int isRight1 = 0;
	int isRight2 = 0;
	int isRight3 = 0;
	String tempPrd;
	String tempCht;
	String tempTim;
	int solvedNum = 0;
	boolean resultRight;
	int quizView;
	String mId2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.math_quiz_main);
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();

		mChapter = idPrefs.getString("CHAPTER", "");
		mTime = idPrefs.getString("TIME", "");

		tempPrd = idPrefs.getString("PERIOD", "");
		tempCht = idPrefs.getString("CHAPTER", "").substring(0, 1);
		tempTim = idPrefs.getString("TIME", "").substring(0, 1);
		tmpSign = "q" + tempPrd + tempCht + tempTim;

		solvedQ = 0;

		for (int i = 0; i < 15; i++) {
			tExams[i] = (TextView) findViewById(tExamId[i]);
			aExams[i] = (ImageView) findViewById(aExamId[i]);
			String tmpSign = "a" + Integer.toString((i + 1) % 5);
			int lid = this.getResources().getIdentifier(tmpSign, "drawable",
					this.getPackageName());
			aExams[i].setImageResource(lid);
			drs[i] = aExams[i].getDrawable();
			aExams[i].setOnClickListener(this);
		}

		for (int j = 0; j < 3; j++) {
			tQuizs[j] = (TextView) findViewById(tQuizId[j]);
			tlQuizs[j] = (LinearLayout) findViewById(tlQuizId[j]);
			tHints[j] = (TextView) findViewById(tHintId[j]);
			pQuizs[j] = (ImageView) findViewById(pQuizId[j]);
			plQuizs[j] = (LinearLayout) findViewById(plQuizId[j]);
			tResults[j] = (ImageView) findViewById(tResultId[j]);

		}

		mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		sRight = mPool.load(this, R.raw.right, 1);
		sWrong = mPool.load(this, R.raw.wrong, 1);

		ImageView backbtn = (ImageView) findViewById(R.id.backbtn);
		TextView quizTitle = (TextView) findViewById(R.id.quiz_title);
		quizTitle.setText(mTime);
		backbtn.setOnClickListener(this);
		// 차시만 따로 temp 테이블에 저장
		// db.delete("math_temp", null, null);
		// db.execSQL("INSERT INTO math_temp SELECT * FROM math_test where col_1='"
		// + mChapter + "' and col_2='" + mTime + "'");

		// 상단 버튼 등록
		num1 = (ImageView) findViewById(R.id.num1);
		num2 = (ImageView) findViewById(R.id.num2);
		num3 = (ImageView) findViewById(R.id.num3);
		num1.setOnClickListener(this);
		num2.setOnClickListener(this);
		num3.setOnClickListener(this);
		num1.setImageResource(R.drawable.q1);
		num2.setImageResource(R.drawable.q2);
		num3.setImageResource(R.drawable.q3);
		alpha1 = num1.getDrawable();
		alpha2 = num2.getDrawable();
		alpha3 = num3.getDrawable();

		// 각 그룹 뷰 등록
		quiz1 = (View) findViewById(R.id.quiz1);
		quiz2 = (View) findViewById(R.id.quiz2);
		quiz3 = (View) findViewById(R.id.quiz3);

		Intent i = getIntent();
		quizView = i.getIntExtra("QUIZ", 0);
		Log.d("dd", Integer.toString(quizView));

		if (quizView > 0) {
			switch (i.getIntExtra("NUM", 1)) {
			case 1:
				onClick(num1);
				Log.d("dd", "1");
				break;
			case 2:
				Log.d("dd", "2");
				initView2();
				setQuizImage(0, "off");
				setQuizImage(2, "off");
				setQuizImage(1, "on");
				break;
			case 3:
				Log.d("dd", "3");
				initView3();
				setQuizImage(1, "off");
				setQuizImage(0, "off");
				setQuizImage(2, "on");
				break;

			default:
				break;
			}
		} else {
			Log.d("dd", "4");
			onClick(num1);
		}
		initQuiz();
		// initChoice("SELECT DISTINCT col_0 FROM math_test ORDER BY col_0 asc",
		// listView1, 1);
	}

	public void setQuizImage(int i, String onOff) {
		if (onOff.equals("off")) {
			pQuizs[i].setImageResource(0);
		} else {
			int lid = this.getResources().getIdentifier(
					tmpSign + Integer.toString(i + 1), "drawable",
					this.getPackageName());
			// 이미지가 없을 경우
			if (lid != 0) {
				Log.d("dd", "이미지아이디" + lid);
				pQuizs[i].setImageResource(lid);
			}
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.num1:
			initView1();
			setQuizImage(1, "off");
			setQuizImage(2, "off");
			setQuizImage(0, "on");

			break;
		case R.id.num2:
			initView2();
			setQuizImage(0, "off");
			setQuizImage(2, "off");
			setQuizImage(1, "on");
			break;
		case R.id.num3:
			setQuizImage(1, "off");
			setQuizImage(0, "off");
			setQuizImage(2, "on");
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

	public void initQuiz() {
		openDb();
		solvedQ = 0;
		isSolved1 = 0;
		isSolved2 = 0;
		isSolved3 = 0;
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
				tExams[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				solvedCheck(i, cursor);
			} else if (isSolved2 == 1 && i > 4 && i < 10) {
				tExams[i].setTextColor(Color.parseColor("#bfbfbf"));
				tExams[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				solvedCheck(i, cursor);
			} else if (isSolved3 == 1 && i > 9 && i < 15) {
				tExams[i].setTextColor(Color.parseColor("#bfbfbf"));
				tExams[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
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
			tExams[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
			if (tAnswer[i] == 1) {
				tResults[((int) i / 5)].setImageResource(R.drawable.right);
				if (i / 5 == 0) {
					isRight1 = 1;
				} else if (i / 5 == 1) {
					isRight2 = 1;
				} else if (i / 5 == 2) {
					isRight3 = 1;
				}
			} else {
				tResults[((int) i / 5)].setImageResource(R.drawable.wrong);
			}

		}

		if (cursor.getInt(4) == 1) {
			// 정답
			tExams[i].setTextColor(Color.parseColor("#0000ff"));
			tExams[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
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

	public void screenshot(View view, String fileName) throws Exception {

		view.setDrawingCacheEnabled(true);

		Bitmap screenshot = view.getDrawingCache();

		try {

			File path = new File("/sdcard/math/review");

			if (!path.isDirectory()) {
				path.mkdirs();
			}
			String temp = "/sdcard/math/review/";
			temp = temp + fileName;
			temp = temp + ".jpg";

			FileOutputStream out = new FileOutputStream(temp);

			screenshot.compress(Bitmap.CompressFormat.JPEG, 100, out);

			out.close();

		} catch (IOException e) {

			e.printStackTrace();

		}

		view.setDrawingCacheEnabled(false);

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

				mId2 = tempPrd + tempCht + tempTim;
				// 정답 체크
				if (tAnswer[chooseA] == 1) {
					resultRight = true;
					mPool.play(sRight, 1, 1, 0, 0, 1);
					Log.d("dd", "정답");
					if (chooseA < 5) {
						solvedNum = 1;
						isRight1 = 1;
					} else if (chooseA < 10) {
						solvedNum = 2;
						isRight2 = 1;
					} else {
						solvedNum = 3;
						isRight3 = 1;
					}

				} else {
					resultRight = false;
					mPool.play(sWrong, 1, 1, 0, 0, 1);
					if (chooseA < 5) {
						solvedNum = 1;
						mId2 = mId2 + "1";

					} else if (chooseA < 10) {
						solvedNum = 2;
						mId2 = mId2 + "2";
					} else {
						solvedNum = 3;
						mId2 = mId2 + "3";
					}
				}
				db.close();
				solvedQ++;

				new DownloadJSON().execute();

				for (int i = 0; i < 15; i++)
					drs[i].setColorFilter(0xffffffff, Mode.MULTIPLY);

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

	private AlertDialog resultDialog(int solveNum, boolean resultRight) {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		if (resultRight) {

			ab.setTitle("정답");

			if (solveNum != 3)
				ab.setMessage("정답입니다. 다음 문제로 넘어갑니다!");
			if (solveNum == 3)
				ab.setMessage("이번 시간 문제를 모두 풀었습니다.");
			ab.setCancelable(false);
			ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));

			ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					if (solvedNum == 1) {
						initView2();
						setQuizImage(0, "off");
						setQuizImage(2, "off");
						setQuizImage(1, "on");
					} else if (solvedNum == 2) {
						setQuizImage(1, "off");
						setQuizImage(0, "off");
						setQuizImage(2, "on");
						initView3();
					} else if (solvedNum == 3) {
					}
				}

			});

			// ab.setNegativeButton("닫기", new DialogInterface.OnClickListener()
			// {
			// @Override
			// public void onClick(DialogInterface arg0, int arg1) {
			// }
			// });
		} else {
			ab.setTitle("오답");

			ab.setMessage("틀렸습니다. 이 문제를 오답노트에 저장하시겠습니까?");
			ab.setCancelable(false);
			ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));

			ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					try {
						screenshot(Math_Quiz_Main.this.getWindow()
								.getDecorView(), mId2);
						openDb();
						db.execSQL("insert or replace into review (file_name) VALUES "
								+ "('" + mId2 + "' )");
						db.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (solvedNum == 1) {
						initView2();
						setQuizImage(0, "off");
						setQuizImage(2, "off");
						setQuizImage(1, "on");
					} else if (solvedNum == 2) {
						setQuizImage(1, "off");
						setQuizImage(0, "off");
						setQuizImage(2, "on");
						initView3();
					} else if (solvedNum == 3) {
					}
				}

			});

			ab.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					if (solvedNum == 1) {
						initView2();
						setQuizImage(0, "off");
						setQuizImage(2, "off");
						setQuizImage(1, "on");
					} else if (solvedNum == 2) {
						setQuizImage(1, "off");
						setQuizImage(0, "off");
						setQuizImage(2, "on");
						initView3();
					} else if (solvedNum == 3) {
					}
				}
			});
		}
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
			mProgressDialog.setTitle("알림");
			// Set progressdialog message
			mProgressDialog.setMessage("채점 중..");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			Log.d("dd", "제출");
			Log.d("채점결과1", Integer.toString(isSolved1));
			Log.d("채점결과2", Integer.toString(isSolved2));
			Log.d("채점결과3", Integer.toString(isSolved3));
			openDb();
			String mId = tempPrd + tempCht + tempTim;
			db.execSQL("insert or replace into result (period,chapter,time,num1,num2,num3,id) VALUES "
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
					+ "','" + mId + "' )");
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
			Log.d("dd", "initQuiz();");
			initQuiz();
			mProgressDialog.dismiss();
			mDialog = resultDialog(solvedNum, resultRight);
			mDialog.show();
			mDialog.getWindow().getAttributes();
			TextView textView = (TextView) mDialog
					.findViewById(android.R.id.message);
			// TextView text2View = (TextView) mDialog
			// .findViewById(android.R.id.title);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			// text2View.setTextSize(40);
			Button btn1 = mDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
			Button btn2 = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);
			btn1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			btn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (quizView == 0) {
				startActivity(new Intent(this, Math_Select_Main.class));
				finish();
			} else {
				finish();
			}
			break;
		}
		return true;
	}

}