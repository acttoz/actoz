package kr.co.moon.unesco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;


public class Unesco1 extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.unesco1);
    Intent intent = getIntent();
	String menu = intent.getStringExtra("MENU");
    ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
    ImageAdapter adapter = new ImageAdapter(this,menu);
    viewPager.setAdapter(adapter);
  }
 
}