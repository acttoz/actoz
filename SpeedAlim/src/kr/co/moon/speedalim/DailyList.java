package kr.co.moon.speedalim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdClickedListener;
import net.daum.adam.publisher.AdView.OnAdClosedListener;
import net.daum.adam.publisher.AdView.OnAdFailedListener;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import net.daum.adam.publisher.AdView.OnAdWillLoadListener;
import net.daum.adam.publisher.impl.AdError;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DailyList extends Activity {

	/** Called when the activity is first created. */
	private static final String LOGTAG = "BannerTypeXML1";
	private AdView adView = null;
	private ArrayList<DailyNoticeData> arrayList;
	// private boolean isCheck[];
	DailyNoticeAdapter adapter;
	private DailyNoticeData data;
	ListView lv_dailynotice;
	CheckBox chk;
	String mDay;
	// 인텐트 관련 변수 추가
	String mGrade;
	String mBan;
	// 인텐트 관련 변수 추가 끝
	String dayTag;
	TextView title1;
	TextView title2;
	TextView title3;
	TextView title4;
	SoundPool mPool;
	int sRight;
	DayHelper mDailyHelper;
	SQLiteDatabase dailyDb;
	ContentValues dailyRow;
	LinearLayout container;
	public static int fontSize;
	SharedPreferences idPrefs;
	SharedPreferences firstRun = null;
	SharedPreferences.Editor editor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dailylist);
		initAdam();
		dailyRow = new ContentValues();
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		fontSize = idPrefs.getInt("FONT", 2);
		// isCheck = new boolean[100];
		mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		sRight = mPool.load(this, R.raw.shutter, 1);
		// 날짜 받아오기
		Intent intent = getIntent();
		mDay = intent.getStringExtra("day");
		// mDay = intent.getStringExtra("day");

		// 인텐트 추가
		mBan = intent.getStringExtra("ban");
		mGrade = intent.getStringExtra("grade");
		// 인텐트 추가 끝
		container = (LinearLayout) findViewById(R.id.linear);
		// dayTag = String.format("a%s", mDay);
		int month = ((Integer.parseInt(mDay)) / 10000) / 100;
		int day = (Integer.parseInt(mDay) / 10000) - (month * 100);

		title1 = (TextView) findViewById(R.id.title1);
		title2 = (TextView) findViewById(R.id.title2);
		title3 = (TextView) findViewById(R.id.title3);
		title4 = (TextView) findViewById(R.id.title4);

		title1.setTypeface(SpeedAlimActivity.mTypeface);
		title1.setText(Integer.toString(month));

		title2.setTypeface(SpeedAlimActivity.mTypeface);
		title2.setText("월");

		title3.setTypeface(SpeedAlimActivity.mTypeface);
		title3.setText(Integer.toString(day));

		title4.setTypeface(SpeedAlimActivity.mTypeface);
		title4.setText("일");

		arrayList = new ArrayList<DailyNoticeData>();

		// threadParse();

		lv_dailynotice = (ListView) findViewById(R.id.itemlist);
		lv_dailynotice.setClickable(false);
		lv_dailynotice.setFocusable(false);

		adapter = new DailyNoticeAdapter(this, R.layout.itemstyle, arrayList);
		lv_dailynotice.setAdapter(adapter);

		// 위치 이동!!!
		/*
		 * SharedPreferences pref = getSharedPreferences("pref",
		 * Activity.MODE_PRIVATE); for (int i = 0; i < arrayList.size(); i++) {
		 * arrayList.get(i).boo = pref.getBoolean("" + mGrade + mBan + mDay + i,
		 * arrayList.get(i).boo); }
		 */

		lv_dailynotice
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long arg3) {
						arrayList.get(position).changeBoolean();
						adapter.notifyDataSetChanged();
					}
				});

		ImageButton changeId = (ImageButton) findViewById(R.id.firstLogin);
		changeId.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				mPool.play(sRight, 1, 1, 0, 0, 1);

				final FrameLayout shotBack = (FrameLayout) findViewById(R.id.shot_back);
				shotBack.setBackgroundColor(Color.parseColor("#bbbbbb"));
				new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
							@Override
							public void run() {
								// 실행할 동작 코딩
								shotBack.setBackgroundColor(Color
										.parseColor("#ffffff"));
								capture();
							}
						}, 200);

			}
		});

		loadDailyDB();

	}

	public void capture() {
		String folder = "Test_Directory"; // 폴더 이름

		try {
			// 현재 날짜로 파일을 저장하기
			// 년월일시분초
			File sdCardPath = Environment.getExternalStorageDirectory();
			File dirs = new File(Environment.getExternalStorageDirectory(),
					folder);

			if (!dirs.exists()) { // 원하는 경로에 폴더가 있는지 확인
				dirs.mkdirs(); // Test 폴더 생성
				Log.d("CAMERA_TEST", "Directory Created");
			}
			container.buildDrawingCache();
			Bitmap captureView = container.getDrawingCache();
			FileOutputStream fos;
			String save;

			try {
				save = sdCardPath.getPath() + "/" + folder + "/" + "shot"
						+ ".jpg";
				// 저장 경로
				fos = new FileOutputStream(save);
				captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos); // 캡쳐

				// 미디어 스캐너를 통해 모든 미디어 리스트를 갱신시킨다.
				// sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
				// Uri.parse("file://"
				// + Environment.getExternalStorageDirectory())));
				Intent intent = new Intent(Intent.ACTION_SEND);

				MimeTypeMap type = MimeTypeMap.getSingleton();
				intent.setType(type.getMimeTypeFromExtension(MimeTypeMap
						.getFileExtensionFromUrl(save)));
				File file1 = new File(save);
				intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file1));
				intent.putExtra(Intent.EXTRA_TEXT, "스피드 알림장");
				startActivity(intent);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// Toast.makeText(getApplicationContext(), "shot" + ".jpg 저장",
			// Toast.LENGTH_SHORT).show();

			// 보내기

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Screen", "" + e.toString());
		}
	}

	// cursor = db.rawQuery("SELECT title, memo  FROM library", null);
	// startManagingCursor(cursor);
	//
	// ArrayList<String> bookArr = new ArrayList<String>();
	//
	// while (cursor.moveToNext()) {
	// String title = cursor.getString(0);
	// String memo = cursor.getString(1);
	// bookArr.add(title);

	private void loadDailyDB() {
		// TODO Auto-generated method stub
		arrayList.clear();
		try {
			mDailyHelper = new DayHelper(DailyList.this);
			dailyDb = mDailyHelper.getReadableDatabase();
			Cursor cursor = dailyDb
					.rawQuery(
							"SELECT "
									+ "content1, content2, content3, content4, content5, content6, content7, content8, content9, content10 "
									+ "FROM daytable" + " WHERE DAY" + " LIKE"
									+ " '%" + mDay + "%'", null);
			cursor.moveToFirst();

			for (int i = 0; i < 10; i++) {

				if (cursor.getString(i).equals("0")) {

				} else {

					data = new DailyNoticeData(cursor.getString(i));

					arrayList.add(data);
				}

			}
			SharedPreferences pref = getSharedPreferences("pref",
					Activity.MODE_PRIVATE);
			for (int i = 0; i < arrayList.size(); i++) {
				arrayList.get(i).boo = pref.getBoolean("" + mGrade + mBan
						+ mDay + i, arrayList.get(i).boo);
			}
			cursor.close();
			dailyDb.close();
			adapter.notifyDataSetChanged();
			lv_dailynotice.invalidate();
		} catch (Exception e) {
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

		// progressDialog.dismiss();

	}

	public void onStop() { // 어플리케이션이 화면에서 사라질때
		super.onStop();
		SharedPreferences pref = getSharedPreferences("pref",
				Activity.MODE_PRIVATE); // UI 상태를 저장합니다.
		SharedPreferences.Editor editor = pref.edit(); // Editor를 불러옵니다.
		for (int i = 0; i < arrayList.size(); i++) {
			editor.putBoolean("" + mGrade + mBan + mDay + i,
					arrayList.get(i).boo);
		}
		editor.commit(); // 저장합니다.
	}

	private void initAdam() {
		// Ad@m sdk 초기화 시작
		adView = (AdView) findViewById(R.id.adview);
		// 광고 리스너 설정
		// 1. 광고 클릭시 실행할 리스너
		adView.setOnAdClickedListener(new OnAdClickedListener() {
			@Override
			public void OnAdClicked() {
				Log.i(LOGTAG, "광고를 클릭했습니다.");
			}
		});
		// 2. 광고 내려받기 실패했을 경우에 실행할 리스너
		adView.setOnAdFailedListener(new OnAdFailedListener() {
			@Override
			public void OnAdFailed(AdError error, String message) {
				Log.w(LOGTAG, message);
			}
		});
		// 3. 광고를 정상적으로 내려받았을 경우에 실행할 리스너
		adView.setOnAdLoadedListener(new OnAdLoadedListener() {
			@Override
			public void OnAdLoaded() {
				Log.i(LOGTAG, "광고가 정상적으로 로딩되었습니다.");
			}
		});
		// 4. 광고를 불러올때 실행할 리스너
		adView.setOnAdWillLoadListener(new OnAdWillLoadListener() {
			@Override
			public void OnAdWillLoad(String url) {
				Log.i(LOGTAG, "광고를 불러옵니다. : " + url);
			}
		});
		// 5. 전면형 광고를 닫았을때 실행할 리스너
		adView.setOnAdClosedListener(new OnAdClosedListener() {
			@Override
			public void OnAdClosed() {
				Log.i(LOGTAG, "광고를 닫았습니다.");
			}
		});
		// 할당 받은 clientId 설정
		// adView.setClientId(“TestClientId”);

		// 광고 갱신 주기를 12초로 설정
		// adView.setRequestInterval(12);
		// 광고 영역에 캐시 사용 여부 : 기본 값은 true
		adView.setAdCache(false);
		// Animation 효과 : 기본 값은 AnimationType.NONE
		adView.setAnimationType(AnimationType.FLIP_HORIZONTAL);
		adView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (adView != null) {
			adView.destroy();
			adView = null;
		}
	}
}
