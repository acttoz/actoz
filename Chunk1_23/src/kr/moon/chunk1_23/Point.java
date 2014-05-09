package kr.moon.chunk1_23;

import android.content.Context;
import android.content.SharedPreferences;

public class Point {

	/** Called when the activity is first created. */
	String POINT;
	String myId;
	// SharedPreferences idPrefs;
	// SharedPreferences.Editor editor;
	Responser localResponser;

	public Point(Context con, String myId) {
		// idPrefs = con.getSharedPreferences("id", con.MODE_PRIVATE);
		// editor = idPrefs.edit();
		this.myId = myId;
		// POINT = idPrefs.getInt("POINT", 0);
		localResponser = new Responser();
	}

	public void setPoint(int point) {

		// editor.putInt("POINT", POINT);
		// editor.commit();
		localResponser
				.getResponse("http://chunk.dothome.co.kr/php/point.php?flag=1&id="
						+ myId + "&point=" + point);
	}

	public String getPoint() {
		localResponser
				.getResponse("http://chunk.dothome.co.kr/php/point.php?flag=0&id="
						+ myId);
		POINT = localResponser.getResult()+" Á¡";
		// POINT = idPrefs.getInt("POINT", 0);
		return POINT;
	}

}
