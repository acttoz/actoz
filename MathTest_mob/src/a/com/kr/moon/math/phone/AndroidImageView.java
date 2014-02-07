package a.com.kr.moon.math.phone;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class AndroidImageView extends Activity {
	DayHelper mHelper;
	SQLiteDatabase db;

	private static final String LOG_TAG = "AndroidImageView";
	ArrayList<String> IMAGES = new ArrayList<String>();

	public AndroidImageView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.androidimageview);

		File path = new File("/sdcard/math/review");

		if (!path.isDirectory()) {
			Toast.makeText(getBaseContext(), "����� ������ �����ϴ�.",
					Toast.LENGTH_SHORT).show();
		} else {
			mHelper = new DayHelper(AndroidImageView.this);
			db = mHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(
					"SELECT file_name FROM review ORDER BY file_name ASC;",
					null);
			if (cursor.moveToFirst()) {
				do {
					try {
						String imgpath = "/sdcard/math/review/"
								+ cursor.getString(0) + ".jpg";
						IMAGES.add(imgpath);

					} catch (Exception e) {
						Toast.makeText(getApplicationContext(),
								"������ �߻��߽��ϴ�. �ٽ� �õ����ּ���.", Toast.LENGTH_SHORT)
								.show();
						finish();
					}

				} while (cursor.moveToNext());
			}
			cursor.close();
			// String temp = "/review/";
			// temp = temp + fileName;
			// temp = temp + ".jpg";
		}

		// Gallery view ����
		ImageView imageView = (ImageView) findViewById(R.id.imageview);
		Gallery galleryImage = (Gallery) findViewById(R.id.gallery);

		// / adapter�� ������ �Ѵ�.
		galleryImage.setAdapter(new ImageAdapter(this));

		// / ������ �ϸ� Toast�� �����ֱ� ���ؼ�
		galleryImage.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {
				Toast.makeText(
						getBaseContext(),
						IMAGES.get(position).substring(20, 21) + " �б� "
								+ IMAGES.get(position).substring(21, 22)
								+ " �ܿ�", Toast.LENGTH_SHORT).show();
			}
		});

	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;
		private int iBackGround;

		public ImageAdapter(Context context) {
			// TODO Auto-generated constructor stub
			mContext = context;
			Log.v(LOG_TAG, "ImageAdapter");
			TypedArray typeArray = obtainStyledAttributes(R.styleable.Gallery);
			iBackGround = typeArray.getResourceId(
					R.styleable.Gallery_android_galleryItemBackground, 0);
			typeArray.recycle();

		}

		public int getCount() {
			// TODO Auto-generated method stub
			// return 0;
			return IMAGES.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			// return null;
			return position;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			// return 0;
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView imageView;
			imageView = new ImageView(mContext);
			Bitmap bm = BitmapFactory.decodeFile(IMAGES.get(position));
			imageView.setImageBitmap(bm);
			// imageView.setImageResource(IMAGES[position]);
			imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageView.setLayoutParams(new Gallery.LayoutParams(
					Gallery.LayoutParams.FILL_PARENT,
					Gallery.LayoutParams.FILL_PARENT));
			// imageView.setBackgroundResource(iBackGround);
			// return null;
			return imageView;
		}
	}

}