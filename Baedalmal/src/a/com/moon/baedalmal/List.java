package a.com.moon.baedalmal;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class List extends Activity {
	ListView listView1;
	Custom_List_Data data;
	Custom_List_Adapter listAdapter1;
	ArrayList<Custom_List_Data> dateList1;
	String[] chapter_list = new String[] { "1. I'm Looking for...",
			"2. Mi-na's birthday.", "3. My math homework.",
			"4. Guess what animal it is.", "5. Class year book cover.",
			"6. We're looking for a boy." };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		// TODO Auto-generated method stub

		listView1 = (ListView) findViewById(R.id.listview);

		dateList1 = new ArrayList<Custom_List_Data>();
		for (int i = 0; i < chapter_list.length; i++) {
			data = new Custom_List_Data();
			data.Data = chapter_list[i];
			dateList1.add(data);
		}

		listAdapter1 = new Custom_List_Adapter(this, R.layout.customlist,
				dateList1);
		listView1.setAdapter(listAdapter1);
		listView1.setOnItemClickListener(new OnItemClickListener() {
			// �б�
			public void onItemClick(AdapterView<?> arg0, View arg1,

			int position, long arg3) {

				Intent quizIntent = new Intent(List.this, Main_Study.class);
				quizIntent.putExtra("CHAPTER", String.valueOf(position+1));
				quizIntent.putExtra("TITLE", dateList1.get(position).Data);
				startActivity(quizIntent);

			}
		});
		listAdapter1.notifyDataSetChanged();
		listView1.invalidate();
	}
}
