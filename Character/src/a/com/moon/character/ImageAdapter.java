package a.com.moon.character;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ImageAdapter extends PagerAdapter {
	Context context;
	String menu = "";

	private ArrayList<Integer> Images = new ArrayList<Integer>();
	private ArrayList<Integer> movie_index;

	ImageAdapter(Context context, String menu) {
		this.context = context;
		this.menu = menu;
		movie_index = new ArrayList<Integer>();
		movie_index.add(2);
		movie_index.add(4);
		movie_index.add(5);
		movie_index.add(9);
		movie_index.add(15);
		// for (int i = 1; i < 19; i++) {
		// String temp = "img" + i;
		// int lid = context.getResources().getIdentifier(temp, "drawable",
		// context.getPackageName());
		// Images.add(lid);
		// }
		int lid = 1;
		int i = 0;
		while (lid > 0) {
			i++;

			String temp = menu + "_" + i;
			lid = context.getResources().getIdentifier(temp, "drawable",
					context.getPackageName());
			Log.d("이미지", " " + lid);
			if (lid > 0) {
				Images.add(lid);
			}
		}
	}

	@Override
	public int getCount() {
		return Images.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View v = new View(context.getApplicationContext());
		final LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (menu.equals("menu3")) {

			v = inflater.inflate(R.layout.pager2, (ViewGroup) null, false);
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			imageView.setImageResource(Images.get(position));

			final ImageButton movie_btn = (ImageButton) v
					.findViewById(R.id.movie_btn);

			if (movie_index.contains(position)) {
				// 영상 포함
				movie_btn.setVisibility(View.VISIBLE);
				movie_btn.setOnTouchListener(new OnTouchListener() {

					public boolean onTouch(View v, MotionEvent event) {
						if (MotionEvent.ACTION_DOWN == event.getAction()) {
							movie_btn.setColorFilter(0xffffff55, Mode.MULTIPLY);

						} else if (MotionEvent.ACTION_UP == event.getAction()) {
							movie_btn.setColorFilter(0xffffffff, Mode.MULTIPLY);
							Intent intent = new Intent(context, M1.class);
							intent.putExtra("BTNID", String.valueOf(position));
							Log.d("BTNID", String.valueOf(position));
							context.startActivity(intent);
						}

						return true;
					}
				});
			} else {
				movie_btn.setVisibility(View.INVISIBLE);
			}
			((ViewPager) container).addView(v, 0);
			return v;
		} else {

			v = inflater.inflate(R.layout.pager, (ViewGroup) null, false);
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			imageView.setImageResource(Images.get(position));

			((ViewPager) container).addView(v, 0);
			return v;
		}

		// ImageView imageView = new ImageView(context);
		// int padding = context.getResources().getDimensionPixelSize(
		// R.dimen.padding_medium);
		// imageView.setPadding(padding, padding, padding, padding);
		// imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		// imageView.setImageResource(Images.get(position));
		// ((ViewPager) container).addView(imageView, 0);
		// return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}
}