package a.com.moon.deokjung.deokjung;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AnswerCallBroadcastReceiver extends BroadcastReceiver {
    String number;
    @Override
    public void onReceive(Context context, Intent arg1) {

        if (arg1.getAction().equals("android.intent.action.PHONE_STATE")) {
            String TAG = "SS";
            String state = arg1.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                try {
                } catch (Exception e) {
                }
            } else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                final String AUTH = "content://com.moon/get";
                ContentResolver cr = context.getContentResolver();

                Log.e(TAG, "Inside EXTRA_STATE_RINGING");
                number = arg1.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.e(TAG, "incoming number : " + number);
                Cursor cursor = cr.query(Uri.parse(AUTH), null, null, null, null);
                cursor.moveToFirst();
                do {
                    Log.d(TAG, cursor.getString(1) + "  " + cursor.getString(2) + " \n  " + cursor.getString(3));

                  if(number.equals(cursor.getString(3).replace("-",""))){
                      Toast.makeText(context,cursor.getString(1)+" "+cursor.getString(2)+"\n"+cursor.getString(3)+"님의 전화입니다.",Toast.LENGTH_LONG).show();
                  }
                } while (cursor.moveToNext());

            } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                try {
                } catch (Exception e) {
                }
            }


        }
    }




}
