package a.com.moon.baedalmal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class LockScreenAppActivity extends Activity {

	/** Called when the activity is first created. */
	KeyguardManager.KeyguardLock k1;
	boolean inDragMode;
	int selectedImageViewX;
	int selectedImageViewY;
	int imageW;
	int imageH;
	String level = "1";
	String newLevel;
	DayHelper mHelper;
	SQLiteDatabase db;
	int windowwidth;
	int windowheight;
	ImageView droid, phone;
	String answer;
	TextView home;
	TextView left;
	TextView right;
	TextView T_quizNum;
	// int phone_x,phone_y;
	int home_x, home_y;
	int[] droidpos;
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	private LayoutParams layoutParams;
	JSONObject jsonobject = null;
	JSONArray jsonarray = null;
	ArrayList<HashMap<String, String>> arraylist;
	ImageView quiz;
	boolean popup = true;
	boolean dbCopied = false;
	boolean playing; // play or stop
	Toast toast;
	int score = 0;
	int tryNum = 0;
	int[] quizList = new int[75];
	int quizNum = 1;
	int endNum = 20;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		quizList = getRandomArray(75);
		Log.d("this is my array", "arr: " + Arrays.toString(quizList));
		dbCopied = idPrefs.getBoolean("DBCOPY", false);

		if (!dbCopied) {
			openDb();
			db.close();
			copySQLiteDB(this);
		}

		level = idPrefs.getString("LEVEL", "1");
		popup = idPrefs.getBoolean("POPUP", true);
		T_quizNum = (TextView) findViewById(R.id.quizNum);
		// level = "1"; // 테스트
		toast = Toast.makeText(LockScreenAppActivity.this, "딩동댕",
				Toast.LENGTH_SHORT);
		// 날짜

		// MyThread threadparse = new MyThread();
		// threadparse.setDaemon(true);
		// threadparse.start(); // 테스트
		droid = (ImageView) findViewById(R.id.droid);
		try {
			// initialize receiver

			windowwidth = getWindowManager().getDefaultDisplay().getWidth();
			System.out.println("windowwidth" + windowwidth);
			windowheight = getWindowManager().getDefaultDisplay().getHeight();
			System.out.println("windowheight" + windowheight);
			// home = (TextView) findViewById(R.id.home);
			BitmapDrawable bd = (BitmapDrawable) this.getResources()
					.getDrawable(R.drawable.button);
			final int imageW = bd.getBitmap().getWidth();

			MarginLayoutParams marginParams2 = new MarginLayoutParams(
					droid.getLayoutParams());

			// marginParams2.setMargins((windowwidth / 24) * 10,
			// ((windowheight / 32) * 25), 0, 0);

			marginParams2.setMargins(((windowwidth) / 2 - imageW / 2), 0, 0, 0);
			RelativeLayout.LayoutParams layoutdroid = new RelativeLayout.LayoutParams(
					marginParams2);

			droid.setLayoutParams(layoutdroid);

			droid.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					layoutParams = (LayoutParams) v.getLayoutParams();

					switch (event.getAction()) {

					case MotionEvent.ACTION_DOWN:
						playing = true;
						// int[] phonepos=new int[2];
						droidpos = new int[2];
						// phone.getLocationOnScreen(phonepos);
						// phone_x=phonepos[0];
						// phone_y=phonepos[1];

						break;
					case MotionEvent.ACTION_MOVE:
						if (playing) {

							int x_cord = (int) event.getRawX();
							int y_cord = (int) event.getRawY();

							if (x_cord < windowwidth - imageW / 4
									&& x_cord > imageW / 4) {
								layoutParams.leftMargin = x_cord - imageW / 2;
							}
							droid.getLocationOnScreen(droidpos);
							v.setLayoutParams(layoutParams);
							Log.d("answer", answer);
							if (answer.equals("left")
									&& x_cord <= (windowwidth / 4)) {
								// 정답
								// v.setVisibility(View.GONE);
								playing = false;
								scoreSet(true);
								toast.setText("딩동댕");
								toast.show();
								layoutParams.leftMargin = ((windowwidth) / 2 - imageW / 2);
								v.setLayoutParams(layoutParams);
								if (quizNum > endNum) {
									gameEnd();
									break;
								}
								initQuiz();
							} else if (answer.equals("right")
									&& x_cord >= (windowwidth / 4) * 3) {
								playing = false;
								// 정답
								// v.setVisibility(View.GONE);
								scoreSet(true);
								toast.setText("딩동댕");
								toast.show();
								layoutParams.leftMargin = ((windowwidth) / 2 - imageW / 2);
								v.setLayoutParams(layoutParams);
								if (quizNum > endNum) {
									gameEnd();
									break;
								}
								initQuiz();
								// finish();
							} else if (answer.equals("right")
									&& x_cord <= (windowwidth / 4)) {
								playing = false;
								scoreSet(false);
								// 오답
								// v.setVisibility(View.GONE);
								toast.setText("땡~!");
								toast.show();
								layoutParams.leftMargin = ((windowwidth) / 2 - imageW / 2);
								v.setLayoutParams(layoutParams);
								if (quizNum > endNum) {
									gameEnd();
									break;
								}
								initQuiz();
							} else if (answer.equals("left")
									&& x_cord >= (windowwidth / 4) * 3) {
								playing = false;
								scoreSet(false);
								// 오답
								// v.setVisibility(View.GONE);
								toast.setText("땡~!");
								toast.show();
								layoutParams.leftMargin = ((windowwidth) / 2 - imageW / 2);
								v.setLayoutParams(layoutParams);
								if (quizNum > endNum) {
									gameEnd();
									break;
								}
								initQuiz();
								// finish();
							}
						}
						break;
					case MotionEvent.ACTION_UP:

						layoutParams.leftMargin = ((windowwidth) / 2 - imageW / 2);

						v.setLayoutParams(layoutParams);

					}

					return true;
				}

				private void gameEnd() {
					// TODO Auto-generated method stub
					AlertDialog.Builder alertdlg = new AlertDialog.Builder(
							LockScreenAppActivity.this);
					alertdlg.setIcon(R.drawable.trophy);
					alertdlg.setTitle("놀이 끝");
					alertdlg.setMessage("짝짝짝~~~ 점수는 " + score + "점입니다!!");
					alertdlg.setPositiveButton("확인",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.cancel();
									finish();
								}
							});
					alertdlg.show();

				}
			});

		} catch (Exception e) {
			// TODO: handle exception
		}
		initQuiz();

	}

	public void scoreSet(boolean right) {
		TextView scoreText = (TextView) findViewById(R.id.score);
		if (right) {
			tryNum++;
			score++;
		} else {
			tryNum++;
		}
		scoreText.setText("정답 : " + score);
	}

	public void showDialog() {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.book_dialog,
				(ViewGroup) findViewById(R.id.layout_root));
		AlertDialog.Builder aDialog = new AlertDialog.Builder(
				LockScreenAppActivity.this);// 여기서buttontest는
		// 패키지이름
		aDialog.setTitle("아임 in 청크 리스닝");
		aDialog.setView(layout);

		aDialog.setPositiveButton("게임 다운",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("market://details?id=kr.moon.words800game")));
					}
				});
		aDialog.setNeutralButton("다시 안보기",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						editor.putBoolean("POPUP", false);
						editor.commit();
					}
				});
		aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog ad = aDialog.create();
		ad.show();
	}

	public void initQuiz() {
		int n = (int) (Math.random() * 10) + 1;
		T_quizNum.setText("돌:" + quizNum + "/"+endNum);
		if (n > 5) {
			answer = "left";
		} else {
			answer = "right";
		}

		quiz = (ImageView) findViewById(R.id.quiz);
		left = (TextView) findViewById(R.id.left);
		right = (TextView) findViewById(R.id.right);

		Random rnd = new Random();
		int r = quizList[quizNum - 1] + 1; // 정답 난수
		int w = rnd.nextInt(75) + 1; // 오답 난수

		if (r == w) {
			if (w == 1) {
				w++;
			} else {
				w--;
			}
		}

		Log.d("initquiz", "num=" + quizNum + " " + answer + " " + r + " " + w);
		mHelper = new DayHelper(LockScreenAppActivity.this);

		db = mHelper.getReadableDatabase();

		// 정답 가져오기
		Cursor cursor = db.rawQuery("SELECT word FROM quiz where rowid='"
				+ String.valueOf(r) + "'", null);
		cursor.moveToFirst();

		// 오답 가져오기
		Cursor cursor2 = db.rawQuery("SELECT word FROM quiz where rowid='"
				+ String.valueOf(w) + "'", null);
		cursor2.moveToFirst();

		if (answer.equals("left")) {
			left.setText(cursor.getString(0));
			right.setText(cursor2.getString(0));

		} else {
			right.setText(cursor.getString(0));
			left.setText(cursor2.getString(0));

		}
		cursor.close();
		cursor2.close();
		db.close();
		String tmpSign3 = "i" + r;
		int lid3 = this.getResources().getIdentifier(tmpSign3, "drawable",
				this.getPackageName());

		quiz.setImageResource(lid3);

		quizNum++;

	}

	public int[] getRandomArray(int length) {
		int[] randArray = new int[length];

		for (int i = 0; i < length; i++)
			// 순서대로 숫자를 집어넣고
			randArray[i] = i;

		for (int i = 0; i < length; i++) {
			int rnd = (int) (Math.random() * length); // 배열 범위 안의 난수를 두개 뽑아서..
			int rndT = (int) (Math.random() * length);
			swap(randArray, rnd, rndT); // 두 주소에 있는 값을 바꾼다.
		}
		return randArray;
	}

	public void swap(int[] arr, int x, int y) {
		int temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
	}

	class MyThread extends Thread {
		final Handler mHandler = new Handler();

		// 쓰레드 ( 로딩바 )
		public synchronized void run() {
			// openDb();
			// db.close();
			Log.d("json", "다운시작");
			Log.d("json", "다운시작");
			Log.d("dd", idPrefs.getString("SCHOOL", ""));
			String resultUrl = String
					.format("http://actoz.dothome.co.kr/13words800/math.php?select=result&school=%s&grade=%s&ban=%s&name=%s",
							idPrefs.getString("SCHOOL", ""),
							idPrefs.getString("GRADE", ""),
							idPrefs.getString("BAN", ""),
							idPrefs.getString("NAME", ""));
			Log.d("dd", resultUrl);
			// callUrl(resultUrl);

			// Create the array
			arraylist = new ArrayList<HashMap<String, String>>();
			// Retrive JSON Objects from the given website URL in
			// JSONfunctions.class

			try {
				jsonobject = JSONfunctions.getJSONfromURL(resultUrl);
				if (jsonobject != null) {
					// Locate the array name
					jsonarray = jsonobject.getJSONArray("result");
					Log.d("dd", "여기");
					for (int i = 0; i < jsonarray.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						jsonobject = jsonarray.getJSONObject(i);
						// Retrive JSON Objects
						Log.d("json", "레벨" + jsonobject.getString("level"));
						// Set the JSON Objects into the array
						// openDb();
						newLevel = jsonobject.getString("level");

					}

				}

				mHandler.post(new Runnable() {

					public void run() {
						if (!level.equals(newLevel)) {
							// 레벨 업
							level = newLevel;
							editor.putString("LEVEL", newLevel);
							editor.commit();
							initQuiz();
							// Toast.makeText(LockScreenAppActivity.this,
							// "" + "레벨 업!!", Toast.LENGTH_SHORT).show();
						}
					}
				});
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			Log.d("json", "다운끝");

		}// while
	}

	public void openDb() {

		mHelper = new DayHelper(LockScreenAppActivity.this);
		db = mHelper.getReadableDatabase();
	}

	private void copySQLiteDB(Context context) {

		editor.putBoolean("DBCOPY", true);
		editor.commit();

		AssetManager manager = context.getAssets();
		String filePath = "/data/data/" + "a.com.moon.baedalmal"
				+ "/databases/" + "quiz.db";
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
}