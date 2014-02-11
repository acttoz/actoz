package kr.co.moon.speedalim.noad;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MyContent extends ContentProvider {

	DayHelper mHelper;
	SQLiteDatabase db;
	ContentValues row;
	SharedPreferences idPrefs;
	SharedPreferences firstRun = null;
	SharedPreferences.Editor editor;

	private String URI = "content://kr.co.moon.speedalim.noad/db";
	// 상대방이 요청하는 URI 하고 내가만든 URI하고 매칭을 시키는게 필요하다
	private Uri contentUri = Uri.parse(URI);
	private static UriMatcher uriMatcher; // 다른웹에서 내가 가지고있는 컨텐트에 접근할때 내가 가지고 있는
											// URI하고 맞는지 안맞는지 매칭하기위함
	static { // 프로그램이 실행되기전, 메인보다 먼저 실행되는 블럭을 static 블럭이라한다. 미리 설정작업을 해줌
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);// NO_MATCH -> 어떤URI를
															// 사용할지모르니 초기에 설정
		uriMatcher.addURI("kr.co.moon.speedalim.noad", "db", 1);// URI에 접속할때 코드값에 따라
															// 매칭 code -> 1
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Uri muri = null;
		int id = 0;
		if (uriMatcher.match(uri) == 1) {
			id = db.delete("daytable", selection, selectionArgs);
			if (id > 0) {
				muri = ContentUris.withAppendedId(contentUri, id);
				getContext().getContentResolver().notifyChange(muri, null); // ContentResolver
																			// 에게바뀐
																			// uri를
																			// 알려준다.
			} else {
				System.out.println("DB삭제 실패");
				Log.d("dd", "DB삭제 실패 ");
			}
		}
		return id;
	}

	@Override
	public String getType(Uri uri) {

		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) { // 내가 만든 테이블에 데이타를
														// 저장하기위해서는 이클래스를
														// 사용해야한다.
		Uri muri = null;
		if (uriMatcher.match(uri) == 1) {
			long id = db.insert("daytable", null, values);// db.insert ->
															// 실제 db에 실행
			if (id > 0) {
				muri = ContentUris.withAppendedId(contentUri, id);
				getContext().getContentResolver().notifyChange(muri, null); // ContentResolver
																			// 에게바뀐
																			// uri를
																			// 알려준다.
			} else {
				System.out.println("DB입력 실패");
			}
		}
		return muri;
	}

	@Override
	public boolean onCreate() { // contentprovider 를 호출하면 실행되어지는 부분
		// 데이터베이스 설정

		Context context = getContext(); // 응용 프로그램 환경에 관한 글로벌 정보 인터페이스
		mHelper = new DayHelper(context);
		db = mHelper.getWritableDatabase();
		row = new ContentValues();

		return (db == null) ? false : true; // db가 null이면 false 아니면 true
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {// 다른어플에서 쿼리를 사용할수있도록 지정

		Cursor c;
		Cursor c2;
		Cursor c3;
		Cursor c4;
		Cursor c5;

		// Cursor c = db.query("daytable", null, selection, selectionArgs,
		// null, null, sortOrder); // 테이블을 넘겨주면 저쪽에서 처리하기에 나머지는 null
		// if (selection == "day") {
		c = db.rawQuery("SELECT school, grade, ban FROM idtable;", null);
		c2 = db.rawQuery("SELECT daysum FROM prevsum;", null);
		c3 = db.rawQuery("SELECT sum(day) FROM daytable;", null);
		c4 = db.rawQuery("SELECT work FROM GCMWORK;", null);
		c5 = db.rawQuery("SELECT newNote FROM NEWNOTE;", null);

		// }
		// else {
		// c = db.rawQuery("SELECT school, grade, ban FROM daytable ", null);
		// }

		// Cursor 결과셋이 변경될경우 알림을 받을 컨텐츠리졸버 객체를 등록해준다.
		c.setNotificationUri(getContext().getContentResolver(), uri);// uri
																		// ->
																		// content://kr.co.bitgo.provider.mycontentprovider/friends
		if (selection == "day") {
			return c2;
		} else if (selection == "websum") {
			return c3;
		} else if (selection == "daytemp") {
			return c4;
		} else if (selection == "newnote") {
			return c5;
		} else {
			return c;

		}

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		Uri muri = null;
		int id = 0;
		if (uriMatcher.match(uri) == 1) {
			id = db.update("prevsum", values, selection, selectionArgs);// db.insert
																		// ->
																		// 실제
																		// db에
																		// 실행
			if (id > 0) {
				muri = ContentUris.withAppendedId(contentUri, id);
				getContext().getContentResolver().notifyChange(muri, null); // ContentResolver
																			// 에게바뀐
																			// uri를
																			// 알려준다.
			} else {
				System.out.println("DB수정 실패");
			}
		}
		return id;
	}

}
