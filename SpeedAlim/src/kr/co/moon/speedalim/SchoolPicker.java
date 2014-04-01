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
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

	ListView schoolListView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_list);
		initAdam();
		title = (TextView) findViewById(R.id.textView1);
		schoolPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = schoolPrefs.edit();

		schoolList = new ArrayList<String>();
		schoolID = new ArrayList<String>();

		threadParse();
		title.setText("�б���� �ٿ���..");

		// TODO Auto-generated method stub
	}

	public void threadParse() {
		final Handler mHandler = new Handler();
		// ������ ( �ε��� )

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
							title.setText("�б��� ���ÿ�.");
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

	}// �ļ� ������

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
