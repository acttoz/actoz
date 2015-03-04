package kr.co.moon.speedalim;

import java.net.URL;
import java.util.ArrayList;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdClickedListener;
import net.daum.adam.publisher.AdView.OnAdClosedListener;
import net.daum.adam.publisher.AdView.OnAdFailedListener;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import net.daum.adam.publisher.AdView.OnAdWillLoadListener;
import net.daum.adam.publisher.impl.AdError;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SchoolPicker extends Activity {
	private static final String LOGTAG = "BannerTypeXML1";
	private AdView adView = null;
	private ProgressDialog progressDialog;
	SharedPreferences schoolPrefs;
	SharedPreferences.Editor editor;
	ArrayList<String> schoolList;
	TextView title;
	ArrayList<String> schoolID;
	static ArrayAdapter<String> schoolAdapter;
	Button btn_help;

	ListView schoolListView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_list);
		title = (TextView) findViewById(R.id.textView1);
		schoolPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = schoolPrefs.edit();
		btn_help = (Button) findViewById(R.id.btn_help);
		btn_help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(SchoolPicker.this, "우리 학교가 보이지 않는 이유는\n알림장 개설이 되지 않았기 때문입니다.\n담임선생님께 '스피드 알림장'을 추천해보세요.",
						Toast.LENGTH_LONG).show();
			}
		});

		schoolList = new ArrayList<String>();
		schoolID = new ArrayList<String>();

		threadParse();
		title.setText("학교목록 다운중..");

		// TODO Auto-generated method stub
	}

	public void threadParse() {
		final Handler mHandler = new Handler();
		// 쓰레드 ( 로딩바 )

		new Thread() {
			public void run() {
				try {

					URL url = new URL(
							"http://clicknote.cafe24.com/cc/school_list_xml.jsp");
					XmlPullParserFactory parserCreator = XmlPullParserFactory
							.newInstance();
					XmlPullParser parser = parserCreator.newPullParser();

					parser.setInput(url.openStream(), null);

					int parserEvent = parser.getEventType();
					String tag;

					boolean checkDate = false;
					boolean checkId = false;

					while (parserEvent != XmlPullParser.END_DOCUMENT) {

						switch (parserEvent) {

						case XmlPullParser.START_TAG:
							tag = parser.getName();
							if (tag.compareTo("name") == 0) {
								checkDate = true;

							} else if (tag.compareTo("id") == 0) {
								checkId = true;
							}
							break;

						case XmlPullParser.TEXT:
							if (checkDate) {
								schoolList.add(parser.getText());
							} else if (checkId) {

								schoolID.add(parser.getText());
							}
							break;

						case XmlPullParser.END_TAG:
							tag = parser.getName();
							if (tag.compareTo("name") == 0) {
								checkDate = false;
							} else if (tag.compareTo("id") == 0) {
								checkId = false;
							}
							break;

						}

						parserEvent = parser.next();

					}

					mHandler.post(new Runnable() {
						public void run() {

							schoolAdapter = new ArrayAdapter<String>(
									SchoolPicker.this,
									android.R.layout.simple_list_item_1,
									schoolList);
							schoolListView = (ListView) findViewById(R.id.listView1);

							schoolListView.setAdapter(schoolAdapter);

							schoolListView
									.setOnItemClickListener(new OnItemClickListener() {
										public void onItemClick(
												AdapterView<?> arg0, View arg1,
												int position, long arg3) {
											// TODO Auto-generated method stub
											// Toast.makeText(SpeedAlimActivity.this,
											// dateList.get(position),
											// Toast.LENGTH_SHORT).show();

											editor.putString("SCHOOL",
													schoolList.get(position));
											editor.putString("SCHOOLID",
													schoolID.get(position));
											editor.commit();

											finish();

										}
									});
							schoolAdapter.notifyDataSetChanged();
							schoolListView.invalidate();
							title.setText("학교를 고르시오.");
							// if (progressDialog.isShowing()) {
							// try {
							// progressDialog.dismiss();
							// } catch (Exception e) {// nothing }
							//
							// }
							// }
							// progressDialog = null;
						}
					});

				}

				catch (Exception e) {

					e.printStackTrace();

				}

			}

		}.start();

	}// 파서 스레드

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (adView != null) {
			adView.destroy();
			adView = null;
		}
	}
}
