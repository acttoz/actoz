package kr.moon.words800;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SchoolPicker extends Activity {
	private static final String LOGTAG = "BannerTypeXML1";
	private ProgressDialog progressDialog;
	SharedPreferences schoolPrefs;
	SharedPreferences.Editor editor;
	ArrayList<String> schoolList;
	Intent intent;
	String schoolName;

	String jsonUrl;
	static ArrayAdapter<String> schoolAdapter;
	JSONObject jsonobject;
	JSONArray jsonarray;
	ArrayList<HashMap<String, String>> arraylist;
	ListView listview;
	ProgressDialog mProgressDialog;
//	static String RANK = "rank";
//	static String COUNTRY = "country";
//	static String POPULATION = "population";
//	static String FLAG = "flag";

	ListView schoolListView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_list);
		// initAdam();
		intent = getIntent();
		schoolName = intent.getStringExtra("SCHOOL");
		schoolPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = schoolPrefs.edit();

		jsonUrl = String.format(

		"http://actoz.dothome.co.kr/math/math.php?select=school&keyword=%s",
				schoolName);

		schoolList = new ArrayList<String>();

		new DownloadJSON().execute();

		// TODO Auto-generated method stub
	}

	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(SchoolPicker.this);
			// Set progressdialog title
			mProgressDialog.setTitle("학교 검색");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
			Log.d("schoolpicker", "pre");
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create the array
			Log.d("schoolpicker", "do1");
			arraylist = new ArrayList<HashMap<String, String>>();
			// Retrive JSON Objects from the given website URL in
			// JSONfunctions.class
			jsonobject = JSONfunctions.getJSONfromURL(jsonUrl);

			try {
				// Locate the array name
				Log.d("schoolpicker", "do2");
				jsonarray = jsonobject.getJSONArray("school_list");
				Log.d("schoolpicker", "do3");

				for (int i = 0; i < jsonarray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					jsonobject = jsonarray.getJSONObject(i);
					// Retrive JSON Objects
					// map.put("rank", jsonobject.getString("rank"));
					// map.put("country", jsonobject.getString("country"));
					// map.put("population",
					// jsonobject.getString("population"));
					// map.put("flag", jsonobject.getString("flag"));
					// Set the JSON Objects into the array
					String mLocal = jsonobject.getString("local");
					String mName = jsonobject.getString("name");

					mName = mName.replace(mLocal, "");
					schoolList.add(mLocal + "_" + mName);

				}
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {
			// Locate the listview in listview_main.xml
			// listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			// adapter = new ListViewAdapter(MainActivity.this, arraylist);
			// Binds the Adapter to the ListView
			// listview.setAdapter(adapter);

			schoolAdapter = new ArrayAdapter<String>(SchoolPicker.this,
					android.R.layout.simple_list_item_1, schoolList);
			schoolListView = (ListView) findViewById(R.id.listView);

			schoolListView.setAdapter(schoolAdapter);

			schoolListView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					// Toast.makeText(SpeedAlimActivity.this,
					// dateList.get(position),
					// Toast.LENGTH_SHORT).show();

					editor.putString("SCHOOL", schoolList.get(position));
					editor.commit();

					finish();

				}
			});

			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}


}
