package kr.co.moon.speedalim.noad;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Notice_List_Adapter extends BaseAdapter {

	private ArrayList<Notice_List_Data> mItems;
	private Context mContext;
	int mLayout;
	LayoutInflater mInflater;

	public Notice_List_Adapter(Context context, int layout,
			ArrayList<Notice_List_Data> items) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mLayout = layout;
		mItems = items;

		mInflater = (LayoutInflater) mContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null) {

			v = mInflater.inflate(R.layout.notice_list, null);
		}
		Notice_List_Data Notice_List_Data = mItems.get(position);

		if (Notice_List_Data != null) {

			TextView textView = (TextView) v.findViewById(R.id.textView);
			TextView dateView = (TextView) v.findViewById(R.id.dateView);

			textView.setTypeface(Notice.mTypeface);
			textView.setText(mItems.get(position).Data);
			
			String date1 = Integer.toString(mItems.get(position).date/100);  
			String date2 = Integer.toString(mItems.get(position).date - (mItems.get(position).date/100)*100);  
			dateView.setText(date1 + "/" + date2);
		}

		return v;

	}

}
