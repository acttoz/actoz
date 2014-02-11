package kr.co.moon.speedalim.noad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DayHelper extends SQLiteOpenHelper {

	public DayHelper(Context context) {
		super(context, "daydb.db", null, 89);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE daytable (	_id INTEGER PRIMARY KEY AUTOINCREMENT, LOGIN TEXT, DAY INTEGER, newNote INTEGER, CONTENT1 TEXT, CONTENT2 TEXT, CONTENT3 TEXT, CONTENT4 TEXT, CONTENT5 TEXT, CONTENT6 TEXT, CONTENT7 TEXT, CONTENT8 TEXT, CONTENT9 TEXT, CONTENT10 TEXT, school TEXT, grade TEXT, ban TEXT);");
		db.execSQL("CREATE TABLE daytemp (	_id INTEGER PRIMARY KEY AUTOINCREMENT, LOGIN TEXT, DAY INTEGER, newNote INTEGER, CONTENT1 TEXT, CONTENT2 TEXT, CONTENT3 TEXT, CONTENT4 TEXT, CONTENT5 TEXT, CONTENT6 TEXT, CONTENT7 TEXT, CONTENT8 TEXT, CONTENT9 TEXT, CONTENT10 TEXT, school TEXT, grade TEXT, ban TEXT);");
		db.execSQL("CREATE TABLE idtable (	_id INTEGER PRIMARY KEY AUTOINCREMENT, school TEXT, grade TEXT, ban TEXT);");
		db.execSQL("CREATE TABLE notice (	_id INTEGER PRIMARY KEY AUTOINCREMENT, newnotice INTEGER);");
		db.execSQL("CREATE TABLE prevsum (	_id INTEGER PRIMARY KEY AUTOINCREMENT, daysum INTEGER);");
		db.execSQL("CREATE TABLE dayId (	_id INTEGER PRIMARY KEY AUTOINCREMENT, dayId INTEGER);");
		db.execSQL("CREATE TABLE NEWNOTE (	_id INTEGER PRIMARY KEY AUTOINCREMENT, newNote INTEGER);");
		db.execSQL("CREATE TABLE GCMWORK (	_id INTEGER PRIMARY KEY AUTOINCREMENT, work INTEGER);");
		db.execSQL("insert into prevsum(daysum) values ('0');");
		db.execSQL("insert into NEWNOTE(newNote) values ('0');");
		db.execSQL("insert into GCMWORK(work) values ('0');");
		db.execSQL("insert into idtable(school,grade,ban) values ('0','0','0');");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS daytable");
		db.execSQL("DROP TABLE IF EXISTS daytemp");
		db.execSQL("DROP TABLE IF EXISTS idtable");
		db.execSQL("DROP TABLE IF EXISTS notice");
		db.execSQL("DROP TABLE IF EXISTS onsync");
		db.execSQL("DROP TABLE IF EXISTS websum");
		db.execSQL("DROP TABLE IF EXISTS prevsum");
		db.execSQL("DROP TABLE IF EXISTS dayId");
		db.execSQL("DROP TABLE IF EXISTS NEWNOTE");
		db.execSQL("DROP TABLE IF EXISTS GCMWORK");
		onCreate(db);
	}

}
