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
	// ������ ��û�ϴ� URI �ϰ� �������� URI�ϰ� ��Ī�� ��Ű�°� �ʿ��ϴ�
	private Uri contentUri = Uri.parse(URI);
	private static UriMatcher uriMatcher; // �ٸ������� ���� �������ִ� ����Ʈ�� �����Ҷ� ���� ������ �ִ�
											// URI�ϰ� �´��� �ȸ´��� ��Ī�ϱ�����
	static { // ���α׷��� ����Ǳ���, ���κ��� ���� ����Ǵ� ���� static ���̶��Ѵ�. �̸� �����۾��� ����
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);// NO_MATCH -> �URI��
															// ��������𸣴� �ʱ⿡ ����
		uriMatcher.addURI("kr.co.moon.speedalim.noad", "db", 1);// URI�� �����Ҷ� �ڵ尪�� ����
															// ��Ī code -> 1
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
																			// ���Թٲ�
																			// uri��
																			// �˷��ش�.
			} else {
				System.out.println("DB���� ����");
				Log.d("dd", "DB���� ���� ");
			}
		}
		return id;
	}

	@Override
	public String getType(Uri uri) {

		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) { // ���� ���� ���̺� ����Ÿ��
														// �����ϱ����ؼ��� ��Ŭ������
														// ����ؾ��Ѵ�.
		Uri muri = null;
		if (uriMatcher.match(uri) == 1) {
			long id = db.insert("daytable", null, values);// db.insert ->
															// ���� db�� ����
			if (id > 0) {
				muri = ContentUris.withAppendedId(contentUri, id);
				getContext().getContentResolver().notifyChange(muri, null); // ContentResolver
																			// ���Թٲ�
																			// uri��
																			// �˷��ش�.
			} else {
				System.out.println("DB�Է� ����");
			}
		}
		return muri;
	}

	@Override
	public boolean onCreate() { // contentprovider �� ȣ���ϸ� ����Ǿ����� �κ�
		// �����ͺ��̽� ����

		Context context = getContext(); // ���� ���α׷� ȯ�濡 ���� �۷ι� ���� �������̽�
		mHelper = new DayHelper(context);
		db = mHelper.getWritableDatabase();
		row = new ContentValues();

		return (db == null) ? false : true; // db�� null�̸� false �ƴϸ� true
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {// �ٸ����ÿ��� ������ ����Ҽ��ֵ��� ����

		Cursor c;
		Cursor c2;
		Cursor c3;
		Cursor c4;
		Cursor c5;

		// Cursor c = db.query("daytable", null, selection, selectionArgs,
		// null, null, sortOrder); // ���̺��� �Ѱ��ָ� ���ʿ��� ó���ϱ⿡ �������� null
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

		// Cursor ������� ����ɰ�� �˸��� ���� ������������ ��ü�� ������ش�.
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
																		// ����
																		// db��
																		// ����
			if (id > 0) {
				muri = ContentUris.withAppendedId(contentUri, id);
				getContext().getContentResolver().notifyChange(muri, null); // ContentResolver
																			// ���Թٲ�
																			// uri��
																			// �˷��ش�.
			} else {
				System.out.println("DB���� ����");
			}
		}
		return id;
	}

}
