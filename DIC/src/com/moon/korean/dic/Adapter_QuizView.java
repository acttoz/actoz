package com.moon.korean.dic;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_QuizView extends BaseAdapter {

	private ArrayList<Custom_List_Data> mItems;
	private Context mContext;
	String word;
	int mLayout;
	LayoutInflater mInflater;

	public Adapter_QuizView(Context context, int layout,
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
				LinearLayout score = (LinearLayout) v.findViewById(R.id.score);
				LinearLayout lscore = (LinearLayout) v
						.findViewById(R.id.lscore);
				LinearLayout sum = (LinearLayout) v.findViewById(R.id.sum);
				word = Find.word;
				if (word != null) {
					String str2 = mItems.get(position).Data;
					int start = str2.indexOf(word);
					int end = str2.indexOf(word) + word.length();
					Log.d("≈ÿΩ∫∆Æ∫‰", str2);
					Log.d("word", word);
					final SpannableStringBuilder sp = new SpannableStringBuilder(
							str2);
					sp.setSpan(
							new ForegroundColorSpan(Color.parseColor("#dae124")),
							start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					textView.setText("");
					textView.append(sp);
				} else {
					textView.setText(mItems.get(position).Data);
				}
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
