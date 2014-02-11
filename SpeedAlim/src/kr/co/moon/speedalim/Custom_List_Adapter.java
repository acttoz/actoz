package kr.co.moon.speedalim;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_List_Adapter extends BaseAdapter {

	private ArrayList<Custom_List_Data> mItems;
	private Context mContext;
	int mLayout;
	LayoutInflater mInflater;

	public Custom_List_Adapter(Context context, int layout,
			ArrayList<Custom_List_Data> items) {
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

			v = mInflater.inflate(R.layout.customlist, null);
		}
		Custom_List_Data Custom_List_Data = mItems.get(position);

		if (Custom_List_Data != null) {
			if (mItems.get(position).Data != null) {
				int month = (Integer.parseInt(mItems.get(position).Data) / 10000) / 100;
				int day = (Integer.parseInt(mItems.get(position).Data) / 10000)
						- (month * 100);
				int hour = (Integer.parseInt(mItems.get(position).Data) % 10000) / 100;
				int minutes = (Integer.parseInt(mItems.get(position).Data) % 10000)
						- (hour * 100);

				TextView textView = (TextView) v.findViewById(R.id.textView);
				TextView timeView = (TextView) v.findViewById(R.id.timeView);
				ImageView img = (ImageView) v.findViewById(R.id.newNote);
				if (mItems.get(position).newNote > 0) {
					img.setVisibility(img.VISIBLE);
				} else {
					img.setVisibility(img.INVISIBLE);

				}
				textView.setTypeface(SpeedAlimActivity.mTypeface);
				timeView.setTypeface(SpeedAlimActivity.mTypeface);
				// textView.setText(Custom_List_Data.getData());
				textView.setText(month + "월  " + day + "일");
				timeView.setText(hour + "시" + minutes + "분");
			}
		}

		return v;

	}
}
