package kr.co.moon.unesco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Right1 extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.right);
	    // TODO Auto-generated method stub
	    Button btn = (Button)findViewById(R.id.blankBtn);
	    btn.setOnClickListener(this);
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		startActivity(new Intent(this,Quiz2.class));

		finish();
	}

}
