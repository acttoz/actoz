package kr.moon.chunk2;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

				TextView textView = (TextView) v.findViewById(R.id.textView);

				// textView.setText(Custom_List_Data.getData());
				textView.setText(mItems.get(position).Data);
				Log.d("dd",
						"최종점수" + mItems.get(position).Data + " "
								+ mItems.get(position).score + " "
								+ mItems.get(position).sum);

			}
		}
		return v;

	}

	public static int dipToPixels(Context context, int dipValue) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dipValue, metrics);
	}
}
