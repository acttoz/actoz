package a.kr.co.moon.result;

import java.io.File;

import net.daum.adam.publisher.AdView;
import a.kr.co.moon.result.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FirstLogin extends Activity implements OnClickListener {
	private ProgressDialog progressDialog;
	ProgressDialog mProgressDialog;
	private AlertDialog mDialog = null;
	String ban = null;
	String num = null;
	String school = null;
	String name = null;
	Button banEdit;
	Button numEdit;
	Button idSaveBtn;
	Button schoolBtn;
	Button newBtn;
	TextView title1, title2;
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	File idXml, dayDb;
	DayHelper mHelper;
	SQLiteDatabase db;
	EditText schoolSearch;
	EditText nameInput;
	int newVer;
	private static final String LOGTAG = "BannerTypeXML1";
	private AdView adView = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstlogin);
		// checkNetwokState();

		// TODO Auto-generated method stub
		schoolSearch = (EditText) findViewById(R.id.schoolInput);
		nameInput = (EditText) findViewById(R.id.nameInput);

		idSaveBtn = (Button) findViewById(R.id.idSaveBtn);
		idSaveBtn.setOnClickListener(this);

		newBtn = (Button) findViewById(R.id.idNewBtn);
		newBtn.setOnClickListener(this);

		schoolBtn = (Button) findViewById(R.id.schoolBtn);
		schoolBtn.setOnClickListener(this);

		banEdit = (Button) findViewById(R.id.banBtn);
		banEdit.setOnClickListener(this);

		numEdit = (Button) findViewById(R.id.numBtn);
		numEdit.setOnClickListener(this);

		title1 = (TextView) findViewById(R.id.title1);

		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		String mName;
		switch (v.getId()) {
		case R.id.schoolBtn:
			// firstLoginIntent.putExtra("BAN", ban);

			String mSchoolSearch = schoolSearch.getText().toString();
			if (mSchoolSearch.equals("")) {
				Toast toast = Toast.makeText(this, "�˻�� �Է����ּ���.",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				schoolSearch.setText("");
				mSchoolSearch = mSchoolSearch.replace("�ʵ��б�", "");
				mSchoolSearch = mSchoolSearch.replace("�ʵ�", "");
				Log.d("dd", "�б��̸�" + mSchoolSearch);

				Intent schoolIntent = new Intent(this, SchoolPicker.class);
				schoolIntent.putExtra("SCHOOL", mSchoolSearch);
				startActivity(schoolIntent);
			}
			break;

		case R.id.banBtn:
			Intent banIntent = new Intent(this, NumberPicker.class);
			banIntent.putExtra("MAX", 12);
			banIntent.putExtra("GRADECHOICE", false);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(banIntent);
			break;

		case R.id.numBtn:
			Intent numIntent = new Intent(this, NumberPicker.class);
			numIntent.putExtra("MAX", 36);
			numIntent.putExtra("GRADECHOICE", true);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(numIntent);
			break;

		case R.id.idSaveBtn:
			mName = nameInput.getText().toString();
			if (idPrefs.getString("BAN", "") == ""
					| idPrefs.getString("NUM", "") == ""
					| idPrefs.getString("SCHOOL", "�б�") == "�б�"
					| mName.equals("")) {
				Log.d("dd",
						idPrefs.getString("GRADE", "")
								+ idPrefs.getString("BAN", "")
								+ idPrefs.getString("SCHOOL", "�б�") + mName);
				Toast toast = Toast.makeText(this, "�б��� �й��� ��Ȯ�� �Է����ּ���!!",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				break;

			} else {
				editor.putString("NAME", mName);
				editor.commit();
				Intent firstLoginIntent = new Intent(this,
						Math_Select_Main.class);
				startActivity(firstLoginIntent);
				finish();
				break;
			}

		case R.id.idNewBtn:
			mName = nameInput.getText().toString();
			if (idPrefs.getString("BAN", "") == ""
					| idPrefs.getString("NUM", "") == ""
					| idPrefs.getString("SCHOOL", "�б�") == "�б�"
					| mName.equals("")) {
				Log.d("dd",
						idPrefs.getString("GRADE", "")
								+ idPrefs.getString("BAN", "")
								+ idPrefs.getString("SCHOOL", "�б�") + mName);
				Toast toast = Toast.makeText(this, "�б��� �й��� ��Ȯ�� �Է����ּ���!!",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				break;

			} else {
				mDialog = createDialog(mName);
				mDialog.show();
				mDialog.getWindow().getAttributes();
				TextView textView = (TextView) mDialog
						.findViewById(android.R.id.message);
				// TextView text2View = (TextView) mDialog
				// .findViewById(android.R.id.title);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
				// text2View.setTextSize(40);
				Button btn1 = mDialog
						.getButton(DialogInterface.BUTTON_NEGATIVE);
				Button btn2 = mDialog
						.getButton(DialogInterface.BUTTON_POSITIVE);
				btn1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
				btn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
				editor.putString("NAME", mName);
				editor.commit();
				break;
			}

		}

	}

	private AlertDialog createDialog(final String text) {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("Ȯ��");
		ab.setMessage("���� ������ ��� ������ Ǯ�� ���Ҵ� ��� ����� ���� �˴ϴ�. \n ����Ͻðڽ��ϱ�?");

		ab.setCancelable(false);
		ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));

		ab.setPositiveButton("��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {

				mHelper = new DayHelper(FirstLogin.this);
				db = mHelper.getReadableDatabase();
				db.delete("result", null, null);
				db.execSQL("update math_test set col_9=replace(col_9,1,0)");
				db.close();
				Intent firstLoginIntent = new Intent(FirstLogin.this,
						Math_Select_Main.class);
				startActivity(firstLoginIntent);
				finish();
			}

		});

		ab.setNegativeButton("�ƴϿ�", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});

		return ab.create();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (idPrefs.getString("SCHOOL", "") == "") {
			school = "�б��̸��� �˻�";
		} else {
			school = idPrefs.getString("SCHOOL", "");
		}

		name = idPrefs.getString("NAME", "");
		nameInput.setText(name);

		ban = idPrefs.getString("BAN", "");
		if (ban.equals("")) {
			ban = "�� ���ΰ���?";
		} else {
			ban = ban + "��";
		}

		num = idPrefs.getString("NUM", "");
		if (num.equals("")) {
			num = "�� ���ΰ���?";
		} else {
			num = num + "��";
		}

		schoolSearch.setHint(school);
		banEdit.setText(ban);
		numEdit.setText(num);

	}
}// Ŭ����
