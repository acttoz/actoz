package kr.co.moon.speedalim.noad;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class DailyNoticeAdapter extends BaseAdapter {

	private ArrayList<DailyNoticeData> arrayList;
	private Context mContext;
	int mLayout;
	LayoutInflater mInflater;

	public DailyNoticeAdapter(Context context, int layout,
			ArrayList<DailyNoticeData> items) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mLayout = layout;
		arrayList = items;

		mInflater = (LayoutInflater) mContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arrayList.get(arg0);
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

			v = mInflater.inflate(R.layout.itemstyle, null);
		}

		if (arrayList.get(position) != null) {
			TextView tv_contents = (TextView) v.findViewById(R.id.contents);
			tv_contents.setTypeface(SpeedAlimActivity.mTypeface);
			tv_contents.setText(arrayList.get(position).getContents());
			CheckBox ckb = (CheckBox) v.findViewById(R.id.ckb);
			ckb.setChecked(arrayList.get(position).getBoolean());
			ckb.setFocusable(false);
			ckb.setClickable(false);
			if (arrayList.get(position).getBoolean()) {
				tv_contents.setPaintFlags(tv_contents.getPaintFlags()
						| Paint.STRIKE_THRU_TEXT_FLAG);
				tv_contents.setTextColor(Color.LTGRAY);
			}
			if (!arrayList.get(position).getBoolean()) {
				tv_contents.setPaintFlags(tv_contents.getPaintFlags()
						& ~Paint.STRIKE_THRU_TEXT_FLAG);
				tv_contents.setTextColor(Color.rgb(61, 28, 0));
			}

		}
		return v;

	}

}

// /////////////------------------------------------------------------

/*
 * ArrayList<DailyNoticeData> arrayList = new ArrayList<DailyNoticeData>();
 * 
 * public DailyNoticeAdapter(Context context, int textViewResourceId,
 * ArrayList<DailyNoticeData> arrayList) { super(context, textViewResourceId,
 * arrayList); this.arrayList = arrayList; }
 * 
 * 
 * @Override public View getView(int position, View convertView, ViewGroup
 * parent) { View v = convertView; if (v == null) {
 * 
 * LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
 * Context.LAYOUT_INFLATER_SERVICE); v = vi.inflate(R.layout.itemstyle, null); }
 * 
 * 
 * if (arrayList.get(position) != null) { TextView tv_contents = (TextView)
 * v.findViewById(R.id.contents);
 * tv_contents.setTypeface(SpeedAlimActivity.mTypeface);
 * tv_contents.setText(arrayList.get(position).getContents()); CheckBox ckb =
 * (CheckBox) v.findViewById(R.id.ckb);
 * ckb.setChecked(arrayList.get(position).getBoolean());
 * ckb.setFocusable(false); ckb.setClickable(false);
 * if(arrayList.get(position).getBoolean()) {
 * tv_contents.setPaintFlags(tv_contents.getPaintFlags()
 * |Paint.STRIKE_THRU_TEXT_FLAG); tv_contents.setTextColor(Color.LTGRAY); }
 * if(!arrayList.get(position).getBoolean()) {
 * tv_contents.setPaintFlags(tv_contents.getPaintFlags()
 * &~Paint.STRIKE_THRU_TEXT_FLAG); tv_contents.setTextColor(Color.rgb(61, 28,
 * 0)); }
 * 
 * } return v;
 * 
 * } }
 */