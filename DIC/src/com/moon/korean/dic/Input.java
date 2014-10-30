package com.moon.korean.dic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Input extends Activity implements OnClickListener {
	// URL to get JSON Array
	private static String url;
	// JSON Node Names
	private static final String TAG_DIC = "dic";
	private static final String TAG_KOREAN = "korean";
	private static final String TAG_TOBAK = "tobak";
	private static final String TAG_MEAN = "mean";

	private static String TAG;
	private AlertDialog mDialog = null;
	JSONArray jsonResult = null;
	ListView listView;
	Toast finishToast;
	Adapter_QuizView listAdapter;
	Custom_List_Data data;
	ArrayList<Custom_List_Data> dateList;
	Drawable alpha1;
	Drawable alpha2;
	Drawable alpha3;
	ProgressDialog mProgressDialog;
	JSONObject jsonobject = null;
	EditText input;
	String WORD;
	String SELECT;
	EditText text_tobak;
	EditText text_korean;
	EditText text_mean;
	Button btn_save;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input);
		// Intent i = getIntent();
		// // Get the results of rank
		// SELECT = i.getStringExtra("SELECT");
		// SELECT = SELECT.replaceAll(" ", "%20");
		// WORD = i.getStringExtra("WORD");
		// WORD = WORD.replaceAll(" ", "%20");
		//
		// if (SELECT.equals("korean")) {
		// TAG = TAG_KOREAN;
		// } else {
		// TAG = TAG_TOBAK;
		// }
		url = "http://actoze.dothome.co.kr/dic/math.php?select=" + SELECT
				+ "_result&word=" + WORD;
		text_korean = (EditText) findViewById(R.id.korean);
		text_tobak = (EditText) findViewById(R.id.tobak);
		text_mean = (EditText) findViewById(R.id.mean);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);

		text_korean.setText(Temp.korean);
		text_tobak.setText(Temp.tobak);
		text_mean.setText(Temp.mean);

	}

	private class Submit extends AsyncTask<String, String, String> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Input.this);
			pDialog.setMessage("Getting Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://actoze.dothome.co.kr/dic/math.php?select=input");
			String response;
			// This is the data to send

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						1);
				nameValuePairs.add(new BasicNameValuePair("tobak", Temp.tobak));
				nameValuePairs
						.add(new BasicNameValuePair("korean", Temp.korean));
				nameValuePairs.add(new BasicNameValuePair("mean", Temp.mean));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request

				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				response = httpclient.execute(httppost, responseHandler);

				// This is the response from a php application

			} catch (ClientProtocolException e) {
				response = "error";
			} catch (IOException e) {
				response = "error";
			}
			return response;
		}

		@Override
		protected void onPostExecute(String reverseString) {
			pDialog.dismiss();

			if (!reverseString.equals("error"))
				Toast.makeText(Input.this, "저장이 완료되었습니다.", Toast.LENGTH_LONG)
						.show();
			else
				Toast.makeText(Input.this, "저장이 실패하였습니다.", Toast.LENGTH_LONG)
						.show();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_save:
			mDialog = createDialog();
			mDialog.show();
			mDialog.getWindow().getAttributes();
			TextView textView = (TextView) mDialog
					.findViewById(android.R.id.message);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

			break;
		}
	}

	public void postData(String toPost) {

	}// end postData()

	private AlertDialog createDialog() {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("알림");
		ab.setMessage("수정한 내용을 저장하시겠습니까?");

		ab.setCancelable(false);
		ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));

		ab.setPositiveButton("예", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Temp.tobak = text_tobak.getText().toString()
						.toLowerCase(Locale.getDefault());
				Temp.korean = text_korean.getText().toString()
						.toLowerCase(Locale.getDefault());
				Temp.mean = text_mean.getText().toString()
						.toLowerCase(Locale.getDefault());
				new Submit().execute();
			}

		});

		ab.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});

		return ab.create();
	}
}
