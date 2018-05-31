package a.com.moon.deokjung.deokjung;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ContactList extends ActionBarActivity {
    DayHelper mHelper;
    SQLiteDatabase db;
    private ListView m_ListView;
    private ArrayAdapter<String> m_Adapter;
    ArrayList<String> names;
    ArrayList<String> numbers;
    ArrayList<String> positions;
    int grade;
    private AlertDialog mDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Intent intent = getIntent();
        m_Adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_text);
        names = new ArrayList<String>();
        numbers = new ArrayList<String>();
        positions = new ArrayList<String>();
        grade = getIntent().getExtras().getInt("GRADE");
        String str = grade + "";
        openDb();
        Log.d("GRADE", str);

        Cursor cursor = db.rawQuery(
                "SELECT * FROM a where grade='" + str + "' order by position asc", null);
        cursor.moveToFirst();
        do {
            Log.d("이름", cursor.getString(1) + "  " + cursor.getString(2) + " \n  " + cursor.getString(3));
            if (grade == 0 || grade == 9)
                positions.add(cursor.getString(1).replaceAll("[0-9]", ""));
            else
                positions.add(cursor.getString(1));
            names.add(cursor.getString(2));
            numbers.add(cursor.getString(3));


            m_Adapter.add(cursor.getString(1) + "  " + cursor.getString(2) + " \n " + cursor.getString(3));
        } while (cursor.moveToNext());
        cursor.close();
        // Android에서 제공하는 string 문자열 하나를 출력 가능한 layout으로 어댑터 생성

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listview);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);

        // ListView 아이템 터치 시 이벤트 추가
        m_ListView.setOnItemClickListener(onClickListItem);

        // ListView에 아이템 추가

    }

    // 아이템 터치 이벤트
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
//            Toast.makeText(getApplicationContext(), m_Adapter.getItem(arg2), Toast.LENGTH_SHORT).show();
            mDialog = resultDialog(names.get(arg2), numbers.get(arg2));
            mDialog.show();
            mDialog.getWindow().getAttributes();
            TextView textView = (TextView) mDialog
                    .findViewById(android.R.id.message);
            // TextView text2View = (TextView) mDialog
            // .findViewById(android.R.id.title);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            textView.setGravity(Gravity.CENTER);
            // text2View.setTextSize(40);
//            Button btn1 = mDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
//            Button btn2 = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);
//            btn1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            btn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
    };

    public void openDb() {

        mHelper = new DayHelper(ContactList.this);
        db = mHelper.getReadableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog resultDialog(String
                                             name, String number) {
        final String mNumber=number;
        final String mName=name;

        AlertDialog.Builder ab = new AlertDialog.Builder(this);

        ab.setTitle("알림");
        ab.setMessage(name+"에게(를)..");


        ab.setCancelable(true);
        ab.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));

        ab.setPositiveButton("연락처에 추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                Bundle bundle = new Bundle();
                bundle.putString(ContactsContract.Intents.Insert.PHONE, mNumber);
                bundle.putString(ContactsContract.Intents.Insert.NAME, mName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ab.setNeutralButton("문자보내기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Uri uri = Uri.parse("smsto:"+mNumber);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(it);
            }
        });
        ab.setNegativeButton("전화걸기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                startActivity(new Intent("android.intent.action.CALL",
                        Uri.parse("tel:"+mNumber)));
            }
        });

        return ab.create();
    }


}
