package a.com.moon.deokjung.deokjung;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class Provider extends ContentProvider {
    static final Uri CONTENT_URI = Uri.parse("content://com.moon/get");
    static final int ALLWORD = 1;
    static final int ONEWORD = 2;

    // content://exam. Data.EnglishWord/word : 모든 레코드 리턴
    // content://exam.Data.EnglishWordc/word/boy : boy 레코드만 리턴
    static final UriMatcher Matcher;
    static{
        Matcher = new UriMatcher(UriMatcher.NO_MATCH);
        Matcher.addURI("com.moon", "word", ALLWORD);
    }

    SQLiteDatabase db;
    public boolean onCreate(){
        DayHelper helper = new DayHelper(getContext());
        db = helper.getWritableDatabase();
        return true;
    }

    public String getType(Uri uri){
        if ( Matcher.match(uri) == ALLWORD ){
            return "vnd.EnglishWord.Data.cursor.item/word";
        }

        if (Matcher.match(uri) == ONEWORD){
            return "vnd.EnglishWord.Data.cursor.dir/words";
        }
        return null;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        String sql = "SELECT * FROM a";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public Uri insert (Uri uri, ContentValues values){
        long row = db.insert("dic", null, values);
        if (row > 0){
            Uri notiuri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(notiuri, null);
            return notiuri;
        }
        return null;
    }

    public int delete(Uri uti, String selection, String[] selectionArgs){
        return 0;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){

        return 0;
    }

} // end class


