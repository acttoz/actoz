package a.com.kr.moon.math.phone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DayHelper extends SQLiteOpenHelper {

	public DayHelper(Context context) {
		super(context, "quiz.db", null, 6);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE result ( _id INTEGER PRIMARY KEY AUTOINCREMENT, period INTEGER, chapter INTEGER, time INTEGER , num1 INTEGER, num2 INTEGER, num3 INTEGER, id TEXT ,UNIQUE (id));");
		db.execSQL("CREATE TABLE result_class ( _id INTEGER PRIMARY KEY AUTOINCREMENT, no INTEGER, name TEXT, period INTEGER, chapter INTEGER, time INTEGER , num1 INTEGER, num2 INTEGER, num3 INTEGER);");
		db.execSQL("CREATE TABLE review ( _id INTEGER PRIMARY KEY AUTOINCREMENT,  file_name TEXT);");
		// db.execSQL("CREATE TABLE math_temp (col_1 TEXT, col_2 TEXT, col_3 TEXT, col_4 TEXT, col_5 TEXT, col_6 TEXT, col_7 TEXT, col_8 TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS result");
		db.execSQL("DROP TABLE IF EXISTS review");
		db.execSQL("DROP TABLE IF EXISTS result_class");
		onCreate(db);
	}

}
