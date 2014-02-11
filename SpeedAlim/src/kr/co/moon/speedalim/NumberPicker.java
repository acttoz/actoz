package kr.co.moon.speedalim;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdClickedListener;
import net.daum.adam.publisher.AdView.OnAdClosedListener;
import net.daum.adam.publisher.AdView.OnAdFailedListener;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import net.daum.adam.publisher.AdView.OnAdWillLoadListener;
import net.daum.adam.publisher.impl.AdError;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NumberPicker extends Activity {
	private static final String LOGTAG = "BannerTypeXML1";
	private AdView adView = null;
	private Button upButton, downButton;
	private EditText numberEdit;
	private int maxrange, minrange = 1, value = 3;
	SharedPreferences numberPref;
	SharedPreferences.Editor editor;
	Intent intent;
	boolean gradeChoice;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numberpicker);
		initAdam();
		upButton = (Button) findViewById(R.id.upButton);
		numberEdit = (EditText) findViewById(R.id.numberEditText);
		downButton = (Button) findViewById(R.id.downButton);

		intent = getIntent();
		maxrange = intent.getIntExtra("MAX", 0);
		gradeChoice = intent.getBooleanExtra("GRADECHOICE", true);

		numberPref = getSharedPreferences("id", MODE_PRIVATE);
		editor = numberPref.edit();

		// TODO Auto-generated method stub

		numberEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				// if (Integer.valueOf(s.toString()) > maxrange)
				// led_dan.setText(maxrange + "");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	public void pickerClick(View v) {
		switch (v.getId()) {
		case R.id.upButton:
			if (value >= minrange && value <= maxrange)
				value++;
			if (value > maxrange)
				value = minrange;
			numberEdit.setText(value + "");
			break;
		case R.id.downButton:
			if (value >= minrange && value <= maxrange)
				value--;
			if (value < minrange)
				value = maxrange;
			numberEdit.setText(value + "");
			break;
		case R.id.submitButton:
			if (gradeChoice) {
				String grade = numberEdit.getText().toString();
				editor.putString("GRADE", grade);
				editor.commit();

				finish();
			} else {

				String ban = numberEdit.getText().toString();
				editor.putString("BAN", ban);
				editor.commit();
				finish();

			}

		}
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

