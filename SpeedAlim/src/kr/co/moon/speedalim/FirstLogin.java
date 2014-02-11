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

			// ����� �� ��Ʈ��ũ ���� üũ�� ������� ������ �� �ȵɶ� ��� ��� ����
			AlertDialog.Builder dlg = new AlertDialog.Builder(FirstLogin.this);
			dlg.setTitle("��Ʈ��ũ ����");
			dlg.setMessage("'���ǵ� �˸���'�� ��Ʈ��ũ ������ �ʼ��Դϴ�. ��Ʈ��ũ ������ �ٽ� ���� ���ּ���.^^");
			dlg.setIcon(R.drawable.search);
			dlg.setNegativeButton("����", new DialogInterface.OnClickListener() {
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
					| idPrefs.getString("SCHOOL", "�б�") == "�б�") {
				Toast toast = Toast.makeText(this, "�б��� �й��� ��Ȯ�� �Է����ּ���!!",
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
		grade = idPrefs.getString("GRADE", "") + "�г�";
		ban = idPrefs.getString("BAN", "") + "��";
		school = idPrefs.getString("SCHOOL", "�б�");

		gradeEdit.setText(grade);
		banEdit.setText(ban);

		schoolBtn.setText(school);
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
}// Ŭ����
