package a.kr.co.moon.result;

import java.util.ArrayList;



import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Student_Adapter extends BaseAdapter {

	private ArrayList<Student_Data> mItems;
	private Context mContext;
	int mLayout;
	LayoutInflater mInflater;

	public Student_Adapter(Context context, int layout,
			ArrayList<Student_Data> items) {
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

			v = mInflater.inflate(R.layout.student_item, null);
		}
		Student_Data Student_Data = mItems.get(position);

		if (Student_Data != null) {
			if (mItems.get(position).no != null) {

				TextView no = (TextView) v.findViewById(R.id.no);
				TextView name = (TextView) v.findViewById(R.id.name);
				ImageView num1 = (ImageView) v.findViewById(R.id.num1);
				ImageView num2 = (ImageView) v.findViewById(R.id.num2);
				ImageView num3 = (ImageView) v.findViewById(R.id.num3);

				// textView.setText(Student_Data.getData());
				no.setText(mItems.get(position).no);
				name.setText(mItems.get(position).name);
				if (mItems.get(position).num1 == 0) {
					num1.setImageResource(R.drawable.wrong);
				}
				if (mItems.get(position).num2 == 0) {
					num2.setImageResource(R.drawable.wrong);
				}
				if (mItems.get(position).num3 == 0) {
					num3.setImageResource(R.drawable.wrong);
				}
			}
		}
		return v;

	}
}
