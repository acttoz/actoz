package a.kr.co.moon.result;

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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
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
	// ����Ʈ ���� ���� �߰�
	String mGrade;
	String mBan;
	// ����Ʈ ���� ���� �߰� ��
	String dayTag;
	TextView title1;
	TextView title2;
	TextView title3;
	TextView title4;

	DayHelper mDailyHelper;
	SQLiteDatabase dailyDb;
	ContentValues dailyRow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dailylist);
		initAdam();
		dailyRow = new ContentValues();

		// isCheck = new boolean[100];

		// ��¥ �޾ƿ���
		Intent intent = getIntent();
		mDay = intent.getStringExtra("day");
		// mDay = intent.getStringExtra("day");

		// ����Ʈ �߰�
		mBan = intent.getStringExtra("ban");
		mGrade = intent.getStringExtra("grade");
		// ����Ʈ �߰� ��

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
		title2.setText("��");

		title3.setTypeface(SpeedAlimActivity.mTypeface);
		title3.setText(Integer.toString(day));

		title4.setTypeface(SpeedAlimActivity.mTypeface);
		title4.setText("��");

		arrayList = new ArrayList<DailyNoticeData>();

		// threadParse();

		lv_dailynotice = (ListView) findViewById(R.id.itemlist);
		lv_dailynotice.setClickable(false);
		lv_dailynotice.setFocusable(false);

		adapter = new DailyNoticeAdapter(this, R.layout.itemstyle, arrayList);
		lv_dailynotice.setAdapter(adapter);

		// ��ġ �̵�!!!
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

				finish();
			}
		});

		loadDailyDB();

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

	public void onStop() { // ���ø����̼��� ȭ�鿡�� �������
		super.onStop();
		SharedPreferences pref = getSharedPreferences("pref",
				Activity.MODE_PRIVATE); // UI ���¸� �����մϴ�.
		SharedPreferences.Editor editor = pref.edit(); // Editor�� �ҷ��ɴϴ�.
		for (int i = 0; i < arrayList.size(); i++) {
			editor.putBoolean("" + mGrade + mBan + mDay + i,
					arrayList.get(i).boo);
		}
		editor.commit(); // �����մϴ�.
	}

	private void initAdam() {
		// Ad@m sdk �ʱ�ȭ ����
		adView = (AdView) findViewById(R.id.adview);
		// ���� ������ ����
		// 1. ���� Ŭ���� ������ ������
		adView.setOnAdClickedListener(new OnAdClickedListener() {
			@Override
			public void OnAdClicked() {
				Log.i(LOGTAG, "���� Ŭ���߽��ϴ�.");
			}
		});
		// 2. ���� �����ޱ� �������� ��쿡 ������ ������
		adView.setOnAdFailedListener(new OnAdFailedListener() {
			@Override
			public void OnAdFailed(AdError error, String message) {
				Log.w(LOGTAG, message);
			}
		});
		// 3. ���� ���������� �����޾��� ��쿡 ������ ������
		adView.setOnAdLoadedListener(new OnAdLoadedListener() {
			@Override
			public void OnAdLoaded() {
				Log.i(LOGTAG, "���� ���������� �ε��Ǿ����ϴ�.");
			}
		});
		// 4. ���� �ҷ��ö� ������ ������
		adView.setOnAdWillLoadListener(new OnAdWillLoadListener() {
			@Override
			public void OnAdWillLoad(String url) {
				Log.i(LOGTAG, "���� �ҷ��ɴϴ�. : " + url);
			}
		});
		// 5. ������ ���� �ݾ����� ������ ������
		adView.setOnAdClosedListener(new OnAdClosedListener() {
			@Override
			public void OnAdClosed() {
				Log.i(LOGTAG, "���� �ݾҽ��ϴ�.");
			}
		});
		// �Ҵ� ���� clientId ����
		// adView.setClientId(��TestClientId��);

		// ���� ���� �ֱ⸦ 12�ʷ� ����
		// adView.setRequestInterval(12);
		// ���� ������ ĳ�� ��� ���� : �⺻ ���� true
		adView.setAdCache(false);
		// Animation ȿ�� : �⺻ ���� AnimationType.NONE
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
