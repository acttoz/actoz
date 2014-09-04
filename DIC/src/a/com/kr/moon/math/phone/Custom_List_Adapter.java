package a.com.kr.moon.math.phone;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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
			if (Math_Select_Main.viewFlag.equals("initNum")) {
				v = mInflater.inflate(R.layout.numlist, null);

			} else {
				v = mInflater.inflate(R.layout.customlist, null);
			}
		}
		Custom_List_Data Custom_List_Data = mItems.get(position);

		if (Custom_List_Data != null) {
			if (mItems.get(position).Data != null) {

				TextView textView = (TextView) v.findViewById(R.id.textView);
				if (Math_Select_Main.viewFlag.equals("initNum")) {
					LinearLayout score = (LinearLayout) v
							.findViewById(R.id.score);
					LinearLayout lscore = (LinearLayout) v
							.findViewById(R.id.lscore);
					LinearLayout sum = (LinearLayout) v.findViewById(R.id.sum);

					// textView.setText(Custom_List_Data.getData());
					textView.setText(mItems.get(position).Data);
					Log.d("dd",
							"최종점수" + mItems.get(position).Data + " "
									+ mItems.get(position).score + " "
									+ mItems.get(position).sum);
					if (mItems.get(position).sum > 0) {
						Log.d("dd",
								"최종점수" + mItems.get(position).Data + " "
										+ mItems.get(position).score + " "
										+ mItems.get(position).sum);
						float scorePer = (float) mItems.get(position).score
								/ (float) mItems.get(position).sum;
						float sumPer = 1 - scorePer;
						Log.d("dd", "퍼센트" + scorePer + " " + sumPer);
						// score.setLayoutParams(new
						// LinearLayout.LayoutParams(0,
						// LayoutParams.FILL_PARENT,2));
						// sum.setLayoutParams(new LinearLayout.LayoutParams(0,
						// LayoutParams.FILL_PARENT,3));

						lscore.setLayoutParams(new LinearLayout.LayoutParams(
								LayoutParams.FILL_PARENT, dipToPixels(mContext,
										40)));

						score.setLayoutParams(new LinearLayout.LayoutParams(0,
								LayoutParams.FILL_PARENT, scorePer));
						sum.setLayoutParams(new LinearLayout.LayoutParams(0,
								LayoutParams.FILL_PARENT, sumPer));
					}

				} else {

					// textView.setText(Custom_List_Data.getData());
					textView.setText(mItems.get(position).Data);
					Log.d("dd",
							"최종점수" + mItems.get(position).Data + " "
									+ mItems.get(position).score + " "
									+ mItems.get(position).sum);

					if (Math_Select_Main.viewFlag.equals("time")) {
						if (mItems.get(position).score == 1) {
							// 푼거
							textView.setTextColor(Color.GRAY);
						}
					}
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
