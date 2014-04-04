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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class List extends Activity implements OnClickListener {
	ListView listView1;
	boolean popup = true;
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	Custom_List_Data data;
	Custom_List_Adapter listAdapter1;
	ArrayList<Custom_List_Data> dateList1;
	String[] chapter_list = new String[] { "Chapter1-1", "Chapter1-2",
			"Chapter1-3", "Chapter1-4", "Chapter1-5", "Chapter2-1",
			"Chapter2-2", "Chapter2-3", "Chapter2-4", "Chapter2-5",
			"Chapter2-6" };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		// TODO Auto-generated method stub

		listView1 = (ListView) findViewById(R.id.listview);
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		popup = idPrefs.getBoolean("POPUP", true);
		dateList1 = new ArrayList<Custom_List_Data>();
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
			// �б�
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
			checkNotice();
		}
	}

	private void checkNotice() {
		// TODO Auto-generated method stub
		NoticeThread noticeCheck = new NoticeThread();
		noticeCheck.start();

	}

	private class NoticeThread extends Thread {
		final Handler mHandler = new Handler();
		StringBuilder text;

		public void run() {
			try {
				text = new StringBuilder();
				text.append("");

				URL url = new URL("http://actoz.dothome.co.kr/13chunk/notice.txt");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				if (conn != null) {
					conn.setConnectTimeout(1000); // 1�� ���� ���ͳ� ������ �����Ұ�� Fall ó��
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
							// AlertDialog.Builder dlg = new
							// AlertDialog.Builder(
							// SpeedAlimActivity.this);
							// dlg.setTitle("����");
							// dlg.setMessage(text);
							// dlg.setIcon(R.drawable.search);
							// dlg.setNegativeButton("Ȯ��",
							// new DialogInterface.OnClickListener() {
							// public void onClick(
							// DialogInterface dialog,
							// int whichButton) {
							// editor.putInt("noticeConfirm",
							// text.length());
							// editor.commit();
							// }
							// });
							// dlg.setCancelable(true);
							// dlg.show();

							// ���̾�α� ����
							final Dialog dialog = new Dialog(
									List.this);
							// ���̾�α��� ������ ���
							Window window = dialog.getWindow();
							// ���̾�αװ� ������ �����츦 �帴�ϰ� �����
							window.setFlags(
									WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
									WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
							// Ÿ��Ʋ�� �����Ѵ�
							dialog.setTitle("��������");
							/*
							 * setContentView() �ϱ����������� �������� ���ʿ� �߰��ϱ� ���ؼ�������
							 * ���Ȯ���� Ȱ��ȭ�Ѵ�
							 */

							dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
							// ���̾ƿ��� ���÷���Ʈ ��Ų��
							dialog.setContentView(R.layout.webnotice_dialog);
							/*
							 * ������ ���ʿ� ���ҽ� ID ��ġ�� �̹����� �׸����� �Լ� ȣ������
							 * requestWindowFeature()�ϰ� setContentView()�� �Ѵ�
							 */
							window.setFeatureDrawableResource(
									Window.FEATURE_LEFT_ICON, R.drawable.ic_launcher);
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

	public void showNotice() {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.notice_dialog,
				(ViewGroup) findViewById(R.id.layout_root));
		AlertDialog.Builder aDialog = new AlertDialog.Builder(List.this);// ���⼭buttontest��
		// ��Ű���̸�
		aDialog.setTitle("");
		aDialog.setView(layout);

		aDialog.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				checkNotice();
			}
		});
		aDialog.setNegativeButton("�ٽ� �Ⱥ���",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						editor.putBoolean("POPUP", false);
						editor.commit();
						checkNotice();
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

			msg.putExtra(Intent.EXTRA_SUBJECT, "ûũ�� ����� �Ǳ�");

			msg.putExtra(Intent.EXTRA_TEXT,
					this.getResources().getString(R.string.link));

			msg.putExtra(Intent.EXTRA_TITLE, "����");

			msg.setType("text/plain");

			startActivity(Intent.createChooser(msg, "����"));
			break;
		}
	}

	public void showDialog() {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.book_dialog,
				(ViewGroup) findViewById(R.id.layout_root));
		AlertDialog.Builder aDialog = new AlertDialog.Builder(List.this);// ���⼭buttontest��
																			// ��Ű���̸�
		aDialog.setTitle("���� in ûũ ������");
		aDialog.setView(layout);

		aDialog.setPositiveButton("å �����ϱ�",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("http://m.book.naver.com/bookdb/book_detail.nhn?biblio.bid=6735393"));
						startActivity(intent);
					}
				});
		aDialog.setNegativeButton("�ݱ�", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog ad = aDialog.create();
		ad.show();
	}
}
