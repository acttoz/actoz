package kr.co.moon.speedalim;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdClickedListener;
import net.daum.adam.publisher.AdView.OnAdClosedListener;
import net.daum.adam.publisher.AdView.OnAdFailedListener;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import net.daum.adam.publisher.AdView.OnAdWillLoadListener;
import net.daum.adam.publisher.impl.AdError;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FirstLogin extends Activity implements OnClickListener {
	String ban = null;
	String grade = null;
	String school = null;
	Button gradeEdit;
	Button banEdit;
	Button idSaveBtn;
	Button schoolBtn;
	TextView title1, title2;
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	File idXml, dayDb;
	DayHelper mHelper;
	SQLiteDatabase db;
	int newVer;
	private static final String LOGTAG = "BannerTypeXML1";
	private AdView adView = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstlogin);
		// checkNetwokState();
		initAdam();

		// TODO Auto-generated method stub
		idSaveBtn = (Button) findViewById(R.id.idSaveBtn);
		idSaveBtn.setOnClickListener(this);

		schoolBtn = (Button) findViewById(R.id.schoolBtn);
		schoolBtn.setOnClickListener(this);

		gradeEdit = (Button) findViewById(R.id.gradeBtn);
		gradeEdit.setOnClickListener(this);

		banEdit = (Button) findViewById(R.id.banBtn);
		banEdit.setOnClickListener(this);

		title1 = (TextView) findViewById(R.id.title1);

		idSaveBtn.setTypeface(SpeedAlimActivity.mTypeface);
		schoolBtn.setTypeface(SpeedAlimActivity.mTypeface);
		gradeEdit.setTypeface(SpeedAlimActivity.mTypeface);
		banEdit.setTypeface(SpeedAlimActivity.mTypeface);
		title1.setTypeface(SpeedAlimActivity.mTypeface);

		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();

	}

	public boolean checkNetwokState() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo lte_4g = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
		boolean blte_4g = false;
		if (lte_4g != null)
			blte_4g = lte_4g.isConnected();
		if (mobile.isConnected() || wifi.isConnected() || blte_4g)
			return true;
		else {

			// 여기는 걍 네트워크 상태 체크와 상관없는 연결이 잘 안될때 경고문 띄움 ㅋㅋ
			AlertDialog.Builder dlg = new AlertDialog.Builder(FirstLogin.this);
			dlg.setTitle("네트워크 오류");
			dlg.setMessage("'스피드 알림장'은 네트워크 연결이 필수입니다. 네트워크 연결후 다시 실행 해주세요.^^");
			dlg.setIcon(R.drawable.search);
			dlg.setNegativeButton("종료", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {

				}
			});
			dlg.setCancelable(false);
			dlg.show();
			return false;
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.gradeBtn:
			Intent gradeIntent = new Intent(this, NumberPicker.class);
			gradeIntent.putExtra("MAX", 6);
			gradeIntent.putExtra("GRADECHOICE", true);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(gradeIntent);
			break;

		case R.id.schoolBtn:
			// firstLoginIntent.putExtra("BAN", ban);
			Intent schoolIntent = new Intent(this, SchoolPicker.class);
			startActivity(schoolIntent);
			break;

		case R.id.banBtn:
			Intent banIntent = new Intent(this, NumberPicker.class);
			banIntent.putExtra("MAX", 10);
			banIntent.putExtra("GRADECHOICE", false);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(banIntent);
			break;

		case R.id.idSaveBtn:

			if (idPrefs.getString("GRADE", "") == ""
					| idPrefs.getString("BAN", "") == ""
					| idPrefs.getString("SCHOOL", "학교") == "학교") {
				Toast toast = Toast.makeText(this, "학교와 학반을 정확히 입력해주세요!!",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				break;

			} else {
				Intent firstLoginIntent = new Intent(this,
						SpeedAlimActivity.class);
				startActivity(firstLoginIntent);
				finish();
				break;
			}

		}

	}

	void onSync() {
		mHelper = new DayHelper(this);
		db = mHelper.getWritableDatabase();
		db.execSQL("update onsync set sync='1' where _id like '1'");
		db.close();

	}

	void offSync() {
		mHelper = new DayHelper(this);
		db = mHelper.getWritableDatabase();
		db.execSQL("update onsync set sync='0' where _id like '1'");
		db.close();
	}

	void onThread() {
		mHelper = new DayHelper(this);
		db = mHelper.getWritableDatabase();
		db.execSQL("update onsync set notice='1' where _id like '1'");
		db.close();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		grade = idPrefs.getString("GRADE", "") + "학년";
		ban = idPrefs.getString("BAN", "") + "반";
		school = idPrefs.getString("SCHOOL", "학교");

		gradeEdit.setText(grade);
		banEdit.setText(ban);

		schoolBtn.setText(school);
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
}// 클래스
