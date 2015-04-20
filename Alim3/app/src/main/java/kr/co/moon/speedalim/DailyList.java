package kr.co.moon.speedalim;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DailyList extends Activity {

    /**
     * Called when the activity is first created.
     */
    private ArrayList<DailyNoticeData> arrayList;
    // private boolean isCheck[];
    DailyNoticeAdapter adapter;
    private DailyNoticeData data;
    ListView lv_dailynotice;
    CheckBox chk;
    SoundPool mPool;
    int sRight;
    DayHelper mDailyHelper;
    SQLiteDatabase dailyDb;
    ContentValues dailyRow;
    LinearLayout container;
    public static int fontSize;
    SharedPreferences idPrefs;
    SharedPreferences firstRun = null;
    SharedPreferences.Editor editor;
    ImageView btnMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailylist);
        dailyRow = new ContentValues();
        idPrefs = getSharedPreferences("id", MODE_PRIVATE);
        editor = idPrefs.edit();
        fontSize = idPrefs.getInt("FONT", 2);
        // isCheck = new boolean[100];
        mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sRight = mPool.load(this, R.raw.shutter, 1);

        btnMenu = (ImageView) findViewById(R.id.btn_menu);
        btnMenu.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                openOptionsMenu();
            }
        });


        // 인텐트 추가 끝
        container = (LinearLayout) findViewById(R.id.linear);
        // dayTag = String.format("a%s", mDay);

        arrayList = new ArrayList<DailyNoticeData>();

        // threadParse();

        lv_dailynotice = (ListView) findViewById(R.id.itemlist);
        lv_dailynotice.setClickable(false);
        lv_dailynotice.setFocusable(false);

        adapter = new DailyNoticeAdapter(this, R.layout.itemstyle, arrayList);
        lv_dailynotice.setAdapter(adapter);

        lv_dailynotice
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long arg3) {
                        arrayList.get(position).changeBoolean();
                        adapter.notifyDataSetChanged();
                    }
                });



//        loadDailyDB();

        //////////////////////////////
        for (int i = 0; i < 10; i++) {
            data = new DailyNoticeData("test" + i);
            arrayList.add(data);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 0, 0, "학반 새로 고르기").setIcon(
                android.R.drawable.ic_menu_agenda);

        menu.add(0, 1, 0, "커뮤니티").setIcon(android.R.drawable.ic_menu_help);
        menu.add(0, 2, 0, "별점주기").setIcon(android.R.drawable.star_off);
        menu.add(0, 3, 0, "동생용 설치").setIcon(
                android.R.drawable.stat_sys_download);
        menu.add(0, 4, 0, "글자 크기 조정").setIcon(R.drawable.icon_font);

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:

//                openDb();
//                db.execSQL("update idtable set school='0',grade='0',ban='0' where _id like '1';");
//                db.execSQL("update prevsum set daysum='0' where _id like '1';");
//                db.delete("daytable", null, null);
//                db.close();
//                startActivity(new Intent(this, FirstLogin.class));
//                finish();
                break;

            case 1:
                // 처리할 이벤트

                Uri uri = Uri.parse("http://clicknote.cafe24.com/xe/board4");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                startActivity(intent);

                break;

            case 2:
                // 처리할 이벤트
                Uri uri2 = Uri.parse("market://details?id=kr.co.moon.speedalim");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                startActivity(intent2);
                break;
            case 3:
                // 처리할 이벤트
                Uri uri3 = Uri.parse("market://details?id=kr.co.moon.speedalim.yb");
                Intent intent3 = new Intent(Intent.ACTION_VIEW, uri3);
                intent3.addCategory(Intent.CATEGORY_BROWSABLE);
                startActivity(intent3);
                break;
            case 4:
                // 처리할 이벤트
                if (idPrefs.getInt("FONT", 2) == 2) {
                    Toast.makeText(DailyList.this, "알림장 내용 글자가 작아집니다.",
                            Toast.LENGTH_LONG).show();
                    editor.putInt("FONT", 1);
                    editor.commit();

                } else {
                    Toast.makeText(DailyList.this, "알림장 내용 글자가 커집니다.",
                            Toast.LENGTH_LONG).show();
                    editor.putInt("FONT", 2);
                    editor.commit();
                }
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void capture() {
        String folder = "Test_Directory"; // 폴더 이름

        try {
            // 현재 날짜로 파일을 저장하기
            // 년월일시분초
            File sdCardPath = Environment.getExternalStorageDirectory();
            File dirs = new File(Environment.getExternalStorageDirectory(),
                    folder);

            if (!dirs.exists()) { // 원하는 경로에 폴더가 있는지 확인
                dirs.mkdirs(); // Test 폴더 생성
                Log.d("CAMERA_TEST", "Directory Created");
            }
            container.buildDrawingCache();
            Bitmap captureView = container.getDrawingCache();
            FileOutputStream fos;
            String save;

            try {
                save = sdCardPath.getPath() + "/" + folder + "/" + "shot"
                        + ".jpg";
                // 저장 경로
                fos = new FileOutputStream(save);
                captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos); // 캡쳐

                // 미디어 스캐너를 통해 모든 미디어 리스트를 갱신시킨다.
                // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                // Uri.parse("file://"
                // + Environment.getExternalStorageDirectory())));
                Intent intent = new Intent(Intent.ACTION_SEND);

                MimeTypeMap type = MimeTypeMap.getSingleton();
                intent.setType(type.getMimeTypeFromExtension(MimeTypeMap
                        .getFileExtensionFromUrl(save)));
                File file1 = new File(save);
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file1));
                intent.putExtra(Intent.EXTRA_TEXT, "스피드 알림장");
                startActivity(intent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // Toast.makeText(getApplicationContext(), "shot" + ".jpg 저장",
            // Toast.LENGTH_SHORT).show();

            // 보내기

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Screen", "" + e.toString());
        }
    }

    // cursor = db.rawQuery("SELECT title, memo  FROM library", null);
    // startManagingCursor(cursor);
    //
    // ArrayList<String> bookArr = new ArrayList<String>();
    //
    // while (cursor.moveToNext()) {
    // String title = cursor.getString(0);
    // String memo = cursor.getString(1);
    // bookArr.add(title);

//    private void loadDailyDB() {
//        // TODO Auto-generated method stub
//        arrayList.clear();
//        try {
//            mDailyHelper = new DayHelper(DailyList.this);
//            dailyDb = mDailyHelper.getReadableDatabase();
//            Cursor cursor = dailyDb
//                    .rawQuery(
//                            "SELECT "
//                                    + "content1, content2, content3, content4, content5, content6, content7, content8, content9, content10 "
//                                    + "FROM daytable" + " WHERE DAY" + " LIKE"
//                                    + " '%" + mDay + "%'", null);
//            cursor.moveToFirst();
//
//            for (int i = 0; i < 10; i++) {
//
//                if (cursor.getString(i).equals("0")) {
//
//                } else {
//
//                    data = new DailyNoticeData(cursor.getString(i));
//
//                    arrayList.add(data);
//                }
//
//            }
//            SharedPreferences pref = getSharedPreferences("pref",
//                    Activity.MODE_PRIVATE);
//            for (int i = 0; i < arrayList.size(); i++) {
//                arrayList.get(i).boo = pref.getBoolean("" + mGrade + mBan
//                        + mDay + i, arrayList.get(i).boo);
//            }
//            cursor.close();
//            dailyDb.close();
//            adapter.notifyDataSetChanged();
//            lv_dailynotice.invalidate();
//        } catch (Exception e) {
//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
//        }
//
//        // progressDialog.dismiss();
//
//    }


}
