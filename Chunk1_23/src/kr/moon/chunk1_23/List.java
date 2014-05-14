package kr.moon.chunk1_23;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class List extends Activity implements OnClickListener {
	private ImageView login;
	private TextView tId;
	private TextView tPoint;
	LinearLayout sub_menu;
	LinearLayout lay_signIn;
	TextView sub_back;
	TextView call_login;
	TextView sign_submit;
	boolean isSigning = false;
	boolean isLogining = false;
	public static Point pointSetter;
	private EditText edtid;
	private EditText edtid2;
	private EditText edtid2_2;
	private EditText edtpass;
	private EditText edtpass2;
	private EditText edtpass2_2;
	public static String myId;
	private static Boolean wasLogin = false;
	public static int point = 0;
	private ImageView mem_regi;
	private ImageView share;
	private static final String SERVER_ADDRESS = "http://chunk.dothome.co.kr/php";
	private static final int SUCESS_ADMIT = 0;
	private static final int FAIL_ADMIT = 1;
	public static String USER_ID;
	private CheckBox checkBoxID;
	private SharedPreferences mPref;
	private SharedPreferences.Editor mPrefEdit;
	Animation slideInT;
	Animation slideOutT;
	ListView listView1;
	boolean popup = true;
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	Custom_List_Data data;
	Custom_List_Adapter listAdapter1;
	ArrayList<Custom_List_Data> dateList1;
	String[] chapter_list = new String[] { "2장 1.May I Speak to..",
			"2장 2.Let me introduce..", "2장 3.Throughout the country",
			"2장 4.The Statue of Liberty", "2장 5.Three days off",
			"3장 1.Can you swim?", "3장 2.Cleaning the classroom alone",
			"3장 3.Are you free?", "3장 4.School auditorium",
			"3장 5.See the movie", "3장 6.To study for the exam" };

	/** Called when the activity is first created. */

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case SUCESS_ADMIT:
				List.myId = edtid.getText().toString();

				if (checkBoxID.isChecked() == true) {
					mPrefEdit.putString("IDKEY", edtid.getText().toString());
					mPrefEdit.putBoolean("check", checkBoxID.isChecked());
				} else {
					mPrefEdit.putString("IDKEY", "");
					mPrefEdit.putBoolean("check", false);
				}
				mPrefEdit.commit();
				hide_login();
				wasLogin = true;
				pointSetter = new Point(List.this, myId);
				pointSetter.setPoint(5);
				tId.setText(myId + " 님");
				tPoint.setText(pointSetter.getPoint());
				Toast.makeText(List.this, "로그인 성공. 5 points Up!",
						Toast.LENGTH_SHORT).show();
				break;
			case FAIL_ADMIT:
				new AlertDialog.Builder(List.this)
						.setTitle("로그인 실패")
						.setMessage("아이디 패스워드를 확인해주세요.")
						.setNeutralButton(
								"OK",
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										edtid.setText("");
										edtpass.setText("");
									}
								}).show();
				break;
			}
		}
	};

	private Handler handler2 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case SUCESS_ADMIT:

				hide_sign();
				Toast.makeText(List.this, "회원가입 완료.", Toast.LENGTH_SHORT)
						.show();
				show_login();
				// finish();
				// Intent intent = new Intent(Login.this, Licence.class);
				// startActivity(intent);
				break;
			case FAIL_ADMIT:
				new AlertDialog.Builder(List.this)
						.setTitle("가입 실패")
						.setMessage("이미 사용중인 아이디입니다.")
						.setNeutralButton(
								"OK",
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										edtid.setText("");
										edtpass.setText("");
									}
								}).show();
				break;
			}
		}
	};

	private void checkNotice() {
		// TODO Auto-generated method stub
		NoticeThread noticeCheck = new NoticeThread();
		noticeCheck.start();

	}

	// 공지사항 체크///////////////////////////////////////////////////////
	private class NoticeThread extends Thread {
		final Handler mHandler = new Handler();
		StringBuilder text;

		public void run() {
			try {
				text = new StringBuilder();
				text.append("");

				URL url = new URL(
						"http://actoz.dothome.co.kr/13chunk/notice.txt");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				if (conn != null) {
					conn.setConnectTimeout(1000); // 1초 동안 인터넷 연결을 실패할경우 Fall 처리
					conn.setUseCaches(false);
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(conn.getInputStream()));
						for (;;) {
							String line = br.readLine();
							if (line == null)
								break;
							text.append(line);
						}
						br.close();
					}
					conn.disconnect();
				}

				mHandler.post(new Runnable() {
					public void run() {

						if (text.length() != idPrefs.getInt("noticeConfirm", 0)
								&& text.length() > 0) {

							// 다이얼로그 생성
							final Dialog dialog = new Dialog(List.this);
							// 다이얼로그의 윈도우 얻기
							Window window = dialog.getWindow();
							// 다이얼로그가 가리는 윈도우를 흐릿하게 만든다
							window.setFlags(
									WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
									WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
							// 타이틀을 설정한다
							dialog.setTitle("공지사항");
							/*
							 * setContentView() 하기전에윈도우 아이콘을 왼쪽에 추가하기 위해서윈도우
							 * 기능확장을 활성화한다
							 */

							dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
							// 레이아웃을 인플레이트 시킨다
							dialog.setContentView(R.layout.webnotice_dialog);
							/*
							 * 윈도우 왼쪽에 리소스 ID 위치의 이미지를 그린다이 함수 호출전에
							 * requestWindowFeature()하고 setContentView()을 한다
							 */
							window.setFeatureDrawableResource(
									Window.FEATURE_LEFT_ICON,
									R.drawable.ic_launcher);
							TextView notice_tv = (TextView) dialog
									.findViewById(R.id.server_textView);
							notice_tv.setText(text);
							Button sb = (Button) dialog
									.findViewById(R.id.server_search_button);
							sb.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Log.e("test", "bb");
									editor.putInt("noticeConfirm",
											text.length());
									editor.commit();
									dialog.cancel();
								}
							});

							dialog.show();

						}

					}
				});
			} catch (Exception ex) {
			}

		}
	}

	// 안내
	public void showNotice() {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.notice_dialog,
				(ViewGroup) findViewById(R.id.layout_root));
		AlertDialog.Builder aDialog = new AlertDialog.Builder(List.this);// 여기서buttontest는
		// 패키지이름
		aDialog.setTitle("");
		aDialog.setView(layout);

		aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				show_login();
			}
		});
		aDialog.setNegativeButton("다시 안보기",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						editor.putBoolean("POPUP", false);
						editor.commit();
						show_login();
					}
				});
		AlertDialog ad = aDialog.create();
		ad.show();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.book:
			showDialog();
			break;
		case R.id.sns_send:
			Intent msg = new Intent(Intent.ACTION_SEND);

			msg.addCategory(Intent.CATEGORY_DEFAULT);

			msg.putExtra(Intent.EXTRA_SUBJECT, "청크로 원어민 되기");
			msg.putExtra(Intent.EXTRA_TEXT, "http://m.site.naver.com/0a5iP");

			msg.putExtra(Intent.EXTRA_TITLE, "제목");

			msg.setType("text/plain");
			point = 10;
			startActivity(Intent.createChooser(msg, "공유"));

			break;
		}
	}

	public void showDialog() {

		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.book_dialog,
				(ViewGroup) findViewById(R.id.layout_root));
		AlertDialog.Builder aDialog = new AlertDialog.Builder(List.this);// 여기서buttontest는
																			// 패키지이름
		aDialog.setTitle("아임 in 청크 리스닝");
		aDialog.setView(layout);

		aDialog.setPositiveButton("책 구입하기",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri
								.parse(getResources().getString(R.string.link)));
						startActivity(intent);
					}
				});
		aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog ad = aDialog.create();
		ad.show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		// TODO Auto-generated method stub
		sub_menu = (LinearLayout) findViewById(R.id.sub_menu);
		lay_signIn = (LinearLayout) findViewById(R.id.lay_signIn);
		sub_menu.setDrawingCacheEnabled(true);
		lay_signIn.setDrawingCacheEnabled(true);
		sub_back = (TextView) findViewById(R.id.sub_back);
		call_login = (TextView) findViewById(R.id.call_login);
		slideInT = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
		slideOutT = AnimationUtils.loadAnimation(this, R.anim.slide_out_top);
		listView1 = (ListView) findViewById(R.id.listview);
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		popup = idPrefs.getBoolean("POPUP", true);
		dateList1 = new ArrayList<Custom_List_Data>();
		tId = (TextView) findViewById(R.id.myId);
		tPoint = (TextView) findViewById(R.id.myPoint);
		for (int i = 0; i < chapter_list.length; i++) {
			data = new Custom_List_Data();
			data.Data = chapter_list[i];
			dateList1.add(data);
		}
		ImageView share = (ImageView) findViewById(R.id.sns_send);
		ImageView book = (ImageView) findViewById(R.id.book);
		share.setOnClickListener(this);
		book.setOnClickListener(this);

		listAdapter1 = new Custom_List_Adapter(this, R.layout.customlist,
				dateList1);
		listView1.setAdapter(listAdapter1);
		listView1.setOnItemClickListener(new OnItemClickListener() {
			// 학기
			public void onItemClick(AdapterView<?> arg0, View arg1,

			int position, long arg3) {

				Intent quizIntent = new Intent(List.this, Main_Study.class);
				quizIntent.putExtra("CHAPTER", String.valueOf(position + 1));
				quizIntent.putExtra("TITLE", dateList1.get(position).Data);
				startActivity(quizIntent);

			}
		});

		listAdapter1.notifyDataSetChanged();
		listView1.invalidate();
		if (popup) {
			showNotice();
		} else {
			show_login();

		}

		// Login////////////////////////////////////////
		mPref = getSharedPreferences("Pref1", 0);
		mPrefEdit = mPref.edit();

		login = (ImageView) findViewById(R.id.button3);
		edtid = (EditText) findViewById(R.id.edtid);
		edtid2 = (EditText) findViewById(R.id.edtid2);
		edtid2_2 = (EditText) findViewById(R.id.edtid2_2);
		edtpass = (EditText) findViewById(R.id.etdpwd);
		edtpass2 = (EditText) findViewById(R.id.etdpwd2);
		edtpass2_2 = (EditText) findViewById(R.id.etdpwd2_2);
		sign_submit = (TextView) findViewById(R.id.sign_submit);

		mem_regi = (ImageView) findViewById(R.id.button1);
		share = (ImageView) findViewById(R.id.ImageView01);

		checkBoxID = (CheckBox) findViewById(R.id.checkBox1);
		edtid.setText(mPref.getString("IDKEY", ""));
		checkBoxID.setChecked(mPref.getBoolean("check", false));

		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edtid.getText().toString().equals("")
						|| edtpass.getText().toString().equals("")) {
					new AlertDialog.Builder(List.this).setTitle("입력 오류")
							.setMessage("아이디와 비밀번호를 확인해주세요")
							.setNeutralButton("OK", null).show();
					edtid.setText("");
					edtpass.setText("");
				} else {
					ConnectivityManager connect = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
					if (connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
							.getState() == NetworkInfo.State.CONNECTED
							|| connect.getNetworkInfo(
									ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
						HttpBuilder hb = new HttpBuilder();
						hb.loginResponse(edtid.getText().toString(), edtpass
								.getText().toString(), SERVER_ADDRESS
								+ "/check_login.php", handler);
					} else {
						new AlertDialog.Builder(List.this)
								.setMessage(
										"데이터 네트워크 차단 상태입니다. Wi-Fi를 연결해 접속하거나, 데이터 네트워크 설정을 '접속 허용'으로 변경하신 후 사용해 주십시오.")
								.setNeutralButton("OK", null).show();
					}

				}
			}
		});
		mem_regi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hide_login();
				show_sign();
			}
		});
		sign_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				if (edtid2.getText().toString().equals("")
						|| edtid2_2.getText().toString().equals("")
						|| edtpass2.getText().toString().equals("")
						|| edtpass2_2.getText().toString().equals("")
						|| !edtid2.getText().toString()
								.equals(edtid2_2.getText().toString())
						|| !edtpass2.getText().toString()
								.equals(edtpass2_2.getText().toString())) {
					new AlertDialog.Builder(List.this).setTitle("입력 오류")
							.setMessage("아이디와 비밀번호를 확인해주세요")
							.setNeutralButton("OK", null).show();
					edtid.setText("");
					edtpass.setText("");
				} else {
					ConnectivityManager connect = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
					if (connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
							.getState() == NetworkInfo.State.CONNECTED
							|| connect.getNetworkInfo(
									ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
						HttpBuilder hb = new HttpBuilder();
						hb.loginResponse(edtid2.getText().toString(), edtpass2
								.getText().toString(), SERVER_ADDRESS
								+ "/register.php", handler2);
					} else {
						new AlertDialog.Builder(List.this)
								.setMessage(
										"데이터 네트워크 차단 상태입니다. Wi-Fi를 연결해 접속하거나, 데이터 네트워크 설정을 '접속 허용'으로 변경하신 후 사용해 주십시오.")
								.setNeutralButton("OK", null).show();
					}

				}
			}
		});
		call_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show_login();
			}
		});
		sub_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void show_login() {
		isLogining = true;
		sub_menu.setVisibility(View.VISIBLE);
		sub_menu.startAnimation(slideInT);
		sub_back.setVisibility(View.VISIBLE);
	}

	public void hide_login() {
		sub_menu.startAnimation(slideOutT);
		sub_menu.setVisibility(View.GONE);
		sub_back.setVisibility(View.GONE);
		checkNotice();
	}

	public void show_sign() {
		isSigning = true;
		lay_signIn.setVisibility(View.VISIBLE);
		lay_signIn.startAnimation(slideInT);
		sub_back.setVisibility(View.VISIBLE);
	}

	public void hide_sign() {
		lay_signIn.startAnimation(slideOutT);
		lay_signIn.setVisibility(View.GONE);
		sub_back.setVisibility(View.GONE);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (wasLogin) {
			tPoint.setText(pointSetter.getPoint());
		}

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if (point > 0) {
			point = 0;
			pointSetter.setPoint(10);
			tPoint.setText(pointSetter.getPoint());
			Toast.makeText(List.this, "앱 초대하기. 10 points Up!",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isSigning) {
				hide_sign();
				show_login();
			} 
			// else if (isLogining) {
			//
			// }
			else {
				startActivity(new Intent(List.this, MoreApp.class));
				finish();
			}
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
}
