package a.com.moon.deokjung.deokjung;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Contact extends Activity implements View.OnClickListener {
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn9;
    Button buttons[]= {btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn9};
int buttonIds[]={R.id.btn_0,R.id.btn_1,R.id.btn_2,R.id.btn_3,R.id.btn_4,R.id.btn_5,R.id.btn_6,R.id.btn_9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        for (int i = 0; i < 8; i++) {
            buttons[i] = (Button) findViewById(buttonIds[i]);
            buttons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ContactList.class);
        switch (v.getId()) {
            case R.id.btn_0:
                intent.putExtra("GRADE", 0);
                break;
            case R.id.btn_1:
                intent.putExtra("GRADE", 1);
                break;
            case R.id.btn_2:
                intent.putExtra("GRADE", 2);
                break;
            case R.id.btn_3:
                intent.putExtra("GRADE", 3);
                break;
            case R.id.btn_4:
                intent.putExtra("GRADE", 4);
                break;
            case R.id.btn_5:
                intent.putExtra("GRADE", 5);
                break;
            case R.id.btn_6:
                intent.putExtra("GRADE", 6);
                break;
            case R.id.btn_9:
                intent.putExtra("GRADE", 9);
                break;
        }
        startActivity(intent);


    }
}
