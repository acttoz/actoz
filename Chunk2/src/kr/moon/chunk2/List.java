package kr.moon.chunk2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class List extends Activity implements OnClickListener {
	ListView listView1;
	boolean popup = true;
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	Custom_List_Data data;
	Custom_List_Adapter listAdapter1;
	ArrayList<Custom_List_Data> dateList1;
	String[] chapter_list = new String[] { "Chapter 1", "Chapter 2",
			"Chapter 3", "Chapter 4", "Chapter 5" };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		// TODO Auto-generated method stub
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		popup = idPrefs.getBoolean("POPUP", true);
		listView1 = (ListView) findViewById(R.id.listview);

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
		if (popup)
			showNotice();
	}

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
			}
		});
		aDialog.setNegativeButton("다시 안보기",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						editor.putBoolean("POPUP", false);
						editor.commit();
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

			msg.putExtra(Intent.EXTRA_TEXT, this.getResources().getString(R.string.link));

			msg.putExtra(Intent.EXTRA_TITLE, "제목");

			msg.setType("text/plain");

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
						Intent intent = new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("http://m.book.naver.com/bookdb/book_detail.nhn?biblio.bid=6735394"));
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
}
