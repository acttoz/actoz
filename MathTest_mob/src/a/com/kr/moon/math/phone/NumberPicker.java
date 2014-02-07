package a.com.kr.moon.math.phone;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
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

		OnKeyListener on_KeyEvent = new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						if (gradeChoice) {
							String grade = numberEdit.getText().toString();
							editor.putString("NUM", grade);
							editor.commit();

							finish();
						} else {

							String ban = numberEdit.getText().toString();
							editor.putString("BAN", ban);
							editor.commit();
							finish();

						}
						return true;
					}

				}
				return false;
			}

		};
		numberEdit.setOnKeyListener(on_KeyEvent);

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
				editor.putString("NUM", grade);
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

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (adView != null) {
			adView.destroy();
			adView = null;
		}
	}
}// Å¬·¡½º

