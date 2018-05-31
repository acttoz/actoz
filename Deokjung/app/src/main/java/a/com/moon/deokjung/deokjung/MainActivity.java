package a.com.moon.deokjung.deokjung;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity {
    Button btn_login;
    EditText edit_password;
    boolean dbCopied;
    SharedPreferences idPrefs;
    SharedPreferences.Editor editor;
    DayHelper mHelper;
    SQLiteDatabase db;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                checkVer();
            }
        });
        idPrefs = getSharedPreferences("id", MODE_PRIVATE);
        editText = (EditText) findViewById(R.id.password);

        editor = idPrefs.edit();
        if (!idPrefs.getBoolean("DBCOPY2018",false))
            copySQLiteDB(this);

        editText.setText(idPrefs.getString("PASS",""));
    }

    public void checkVer() {

        DownloadText checkVer = new DownloadText();
        checkVer.start();

    }

    private class DownloadText extends Thread {
        final Handler mHandler = new Handler();
        StringBuilder text;

        public void run() {
            try {
                text = new StringBuilder();
                text.append("");
                URL url = new URL(
                        "http://actoz.dothome.co.kr/deokjung.txt");
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(1000); // 1초 동안 인터넷 연결을 실패할경우 Fall 처리
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(conn.getInputStream()));
                        // for (;;) {
                        String line = br.readLine();
                        // if (line == null)
                        // break;
                        text.append(line);
                        // }
                        br.close();
                    }
                    conn.disconnect();
                }

                mHandler.post(new Runnable() {
                    public void run() {
                        try {
//                            password = Integer.parseInt((text.toString()));
                            EditText editText = (EditText) findViewById(R.id.password);

                            try {

                                String inputText = editText.getText()
                                        .toString();
                                if (inputText.equals((text.toString()))) {
                                    editor.putString("PASS",text.toString());
                                    editor.commit();
                                    Intent firstLoginIntent = new Intent(
                                            MainActivity.this, Contact.class);
                                    startActivity(firstLoginIntent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "인증번호가 맞지 않습니다.",
                                            Toast.LENGTH_SHORT).show();
                                    editText.setText("");
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                        } catch (NumberFormatException nfe) {
                            // TODO: handle exception
                        }
                    }
                });
            } catch (Exception ex) {
            }

        }
    }

    private void copySQLiteDB(Context context) {

        mHelper = new DayHelper(MainActivity.this);
        db = mHelper.getReadableDatabase();


        Log.d("math", "db복사");

        AssetManager manager = context.getAssets();
        String filePath = "data/data/" + getApplicationContext().getPackageName() + "/databases/"
                + "contacts.db";
        File file = new File(filePath);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            InputStream is = manager.open("contacts.db");
            BufferedInputStream bis = new BufferedInputStream(is);

            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bis.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, read);
            }
            bos.flush();

            bos.close();
            fos.close();
            bis.close();
            is.close();
            editor.putBoolean("DBCOPY2018", true);
            editor.commit();

        } catch (IOException e) {
            Log.e("ErrorMessage : ", e.getMessage());
        }

        db.close();
    }

}
