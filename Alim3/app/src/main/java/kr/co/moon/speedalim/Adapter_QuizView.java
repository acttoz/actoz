package kr.co.moon.speedalim;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class Adapter_QuizView extends BaseAdapter {

	private ArrayList<Custom_List_Data> mItems;
	private ArrayList<Custom_List_Data> oldItems;
	String SELECT;
	private Context mContext;
	String word;
	int mLayout;
	LayoutInflater mInflater;

	public Adapter_QuizView(Context context, int layout,
							ArrayList<Custom_List_Data> items, String temp) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mLayout = layout;
		mItems = items;
		this.SELECT = temp;
		this.oldItems = new ArrayList<Custom_List_Data>();
		oldItems.addAll(items);
		mInflater = (LayoutInflater) mContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		Log.d("filter_size", oldItems.size() + "");
		Log.d("filter_size", mItems.size() + "");

	}

	public class PersonViewHolder {
		public TextView text;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		PersonViewHolder viewHolder;
		View v = convertView;
		if (v == null) {

			v = mInflater.inflate(R.layout.customlist, null);
			viewHolder = new PersonViewHolder();
			viewHolder.text = (TextView) v.findViewById(R.id.textView);
			v.setTag(viewHolder);
		} else {
			viewHolder = (PersonViewHolder) v.getTag();
		}
		// Custom_List_Data Custom_List_Data = mItems.get(position);

		// if (Custom_List_Data != null) {
		if (mItems.get(position).getData() != null) {

			viewHolder.text.setText(mItems.get(position).getData());
		}

		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Log.d("onclick", mItems.get(position).getData());
				Intent teacherIntent = new Intent(mContext,FirstLogin.class);
				teacherIntent.putExtra("WORD", mItems.get(position).getData());
				teacherIntent.putExtra("SELECT", SELECT);
				mContext.startActivity(teacherIntent);
			}
		});
		return v;

	}

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		mItems.clear();
		if (charText.length() == 0) {
			Log.d("filter", "0");
			// mItems.addAll(oldItems);
			// mItems.add(oldItems.);
			mItems.addAll(oldItems);
			Log.d("filter_size", oldItems.size() + "");
			// Log.d("filter", oldItems.get(0).Data);
		} else {
			for (Custom_List_Data data : oldItems) {
				if (data.getData().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					Log.d("filter", "searching");
					mItems.add(data);
				}
			}
		}
		notifyDataSetChanged();
	}

}
