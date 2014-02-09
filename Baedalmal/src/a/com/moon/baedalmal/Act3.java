package a.com.moon.baedalmal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Act3 extends Activity {
	private static int ROW_COUNT = -1;
	private static int COL_COUNT = -1;
	private Context context;
	private Drawable backImage;
	private int[][] cards;
	private List<Drawable> images;
	private Card firstCard;
	private Card seconedCard;
	private ButtonListener buttonListener;
	Display display;

	private SoundPool mPool;
	private int mTouch;
	private int mIntro;
	private int mSuccess;

	int goal= 0;
	int count = 0;

	private static Object lock = new Object();

	int turns;
	private TableLayout mainTable;
	private UpdateCardsHandler handler;
	int mDisplay_width;
	int mDisplay_height;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		handler = new UpdateCardsHandler();
		loadImages();
		setContentView(R.layout.act3);

		TextView url = ((TextView) findViewById(R.id.myWebSite));
		Linkify.addLinks(url, Linkify.WEB_URLS);

		backImage = getResources().getDrawable(R.drawable.card_back);

		buttonListener = new ButtonListener();

		mainTable = (TableLayout) findViewById(R.id.TableLayout03);

		context = mainTable.getContext();

		// Button mNewbtn = (Button)findViewById(R.id.new_btn);
		// mNewbtn.setOnClickListener(this);

		// ((Button) findViewById(R.id.new_btn))
		// .setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// newGame(4, 6);
		//
		// }
		//
		// });
		display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		mDisplay_width = display.getWidth();
		mDisplay_height = display.getHeight();
		Log.d("height", ""+mDisplay_height);

		mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mTouch = mPool.load(this, R.raw.touch, 1);
		mIntro = mPool.load(this, R.raw.intro, 1);
		mSuccess = mPool.load(this, R.raw.success, 1);

		Spinner s = (Spinner) findViewById(R.id.new_btn);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.type, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);

		s.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(android.widget.AdapterView<?> arg0,
					View arg1, int pos, long arg3) {

				((Spinner) findViewById(R.id.new_btn)).setSelection(0);

				int x, y;

				switch (pos) {
				case 1:
					x = 4;
					y = 4;
					break;
				case 2:
					x = 5;
					y = 4;
					break;
				case 3:
					x = 6;
					y = 4;
					break;

				default:
					return;
				}
				newGame(x, y);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// switch (v.getId()) {
	// case R.id.new_btn:
	// // newGame(4,6);
	// break;
	// case R.id.btn2:
	// startActivity(new Intent(this, Act2.class));
	// break;
	// case R.id.btn3:
	// startActivity(new Intent(this, Act3.class));
	// break;
	// case R.id.btn4:
	// startActivity(new Intent(this, Act4.class));
	// break;
	// }
	//
	// }

	private void newGame(int c, int r) {
		goal = (c*2);
		ROW_COUNT = r;
		COL_COUNT = c;
		Log.d("newgame", c+" "+r);

		cards = new int[COL_COUNT][ROW_COUNT];

		mainTable.removeView(findViewById(R.id.TableRow01));
		mainTable.removeView(findViewById(R.id.TableRow02));

		TableRow tr = ((TableRow) findViewById(R.id.TableRow03));
		tr.removeAllViews();

		mainTable = new TableLayout(context);
		tr.addView(mainTable);

		for (int y = 0; y < ROW_COUNT; y++) {
			mainTable.addView(createRow(y));
		}

		firstCard = null;
		loadCards();

		turns = 0;
		((TextView) findViewById(R.id.tv1)).setText(turns+"돌");

	}

	private void loadImages() {
		images = new ArrayList<Drawable>();

		images.add(getResources().getDrawable(R.drawable.card1));
		images.add(getResources().getDrawable(R.drawable.card2));
		images.add(getResources().getDrawable(R.drawable.card3));
		images.add(getResources().getDrawable(R.drawable.card4));
		images.add(getResources().getDrawable(R.drawable.card5));
		images.add(getResources().getDrawable(R.drawable.card6));
		images.add(getResources().getDrawable(R.drawable.card7));
		images.add(getResources().getDrawable(R.drawable.card8));
		images.add(getResources().getDrawable(R.drawable.card9));
		images.add(getResources().getDrawable(R.drawable.card10));
		images.add(getResources().getDrawable(R.drawable.card11));
		images.add(getResources().getDrawable(R.drawable.card12));
		images.add(getResources().getDrawable(R.drawable.card13));
		images.add(getResources().getDrawable(R.drawable.card14));

	}

	private void loadCards() {
		try {
			int size = ROW_COUNT * COL_COUNT;

			Log.i("loadCards()", "size=" + size);

			ArrayList<Integer> list = new ArrayList<Integer>();

			for (int i = 0; i < size; i++) {
				list.add(new Integer(i));
			}

			Random r = new Random();

			for (int i = size - 1; i >= 0; i--) {
				int t = 0;

				if (i > 0) {
					t = r.nextInt(i);
				}

				t = list.remove(t).intValue();
				cards[i % COL_COUNT][i / COL_COUNT] = t % (size / 2);

				Log.i("loadCards()", "card[" + (i % COL_COUNT) + "]["
						+ (i / COL_COUNT) + "]="
						+ cards[i % COL_COUNT][i / COL_COUNT]);
			}
		} catch (Exception e) {
			Log.e("loadCards()", e + "");
		}

	}

	private TableRow createRow(int y) {
		TableRow row = new TableRow(context);

		row.setHorizontalGravity(Gravity.CENTER);

		for (int x = 0; x < COL_COUNT; x++) {
			row.addView(createImageButton(x, y));
		}
		return row;
	}

	private View createImageButton(int x, int y) {

		Button button = new Button(context);
		button.setMinWidth(0);
		button.setMinHeight(0);
		button.setWidth(mDisplay_width/6);
		button.setWidth(mDisplay_height/6);
		button.setBackgroundDrawable(backImage);
		button.setId(100 * x + y);

		button.setOnClickListener(buttonListener);
		return button;
	}

	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			synchronized (lock) {
				if (firstCard != null && seconedCard != null) {
					return;
				}
				int id = v.getId();
				int x = id / 100;
				int y = id % 100;
				turnCard((Button) v, x, y);
			}

		}

		private void turnCard(Button button, int x, int y) {
			mPool.play(mTouch, 1, 1, 0, 0, 1);

			button.setBackgroundDrawable(images.get(cards[x][y]));

			if (firstCard == null) {
				firstCard = new Card(button, x, y);
			} else {

				if (firstCard.x == x && firstCard.y == y) {
					return; // the user pressed the same card
				}

				seconedCard = new Card(button, x, y);

				turns++;
				((TextView) findViewById(R.id.tv1)).setText("Tries: " + turns);

				TimerTask tt = new TimerTask() {

					@Override
					public void run() {
						try {
							synchronized (lock) {
								handler.sendEmptyMessage(0);
							}
						} catch (Exception e) {
							Log.e("E1", e.getMessage());
						}
					}
				};

				Timer t = new Timer(false);
				t.schedule(tt, 500);
			}

		}

	}

	class UpdateCardsHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			synchronized (lock) {
				checkCards();
			}
		}

		public void checkCards() {

			if (cards[seconedCard.x][seconedCard.y] == cards[firstCard.x][firstCard.y]) {

				
				
				firstCard.button.setVisibility(View.INVISIBLE);
				seconedCard.button.setVisibility(View.INVISIBLE);
				count = count + 1;

				if (count == goal) {
					mPool.play(mSuccess, 1, 1, 0, 0, 1);
					int temp;
					Random rand = new Random();
					String[] message = {
							" 무명저작물 - 저작자의 실제 이름이나 필명 등을 밝히지 않고 공표된 저작물.",
							" 사적 이용 - 개인적으로 이용하는 경우를 말하며, 이 경우 권리자의 허락 없이 이용할 수 있습니다. ",
							" 2차적 저작물 - 번역, 각색, 편곡 등과 같이 기존의 다른 저작물을 기초로 하여 만들어진 저작물.",
							" 공표 - 방송이나 공연, 전시 등 어떤 방법이 되었든 일반 사람들이 저작물을 접할 수 있도록 하는 행위를 말합니다.",
							" 편집물 - 기존의 저작물이나 자료 등을 수집하고 선정, 배열, 조합, 편집 등의 작업을 거쳐 만들어지는 저작물.",
							" 배포 - 일반 사람들이 그 저작물을 감상할 수 있도록 유통과정을 통해 제공하는 것.",
							" 개작 - 기존의 저작물을 바꾸는 모든 활동.",
							" 각색 - 기존 저작물을 특정 장르에서 다른 장르로 바꾸는 것을 의미.",
							" 저작권의 제한 - 공공의 이익을 위한 특정한 경우에는 저작권의 행사를 법으로써 제한하는 것.",
							" 자유사용 - 법에서 정한 특별한 경우 저작권자의 허락 없이 무상으로 이용할 수 있도록 하는 법.",
							" 원저작물 - 2차적 저작물의 각색, 번역, 수집 등의 대상이 되는 기존 저작물을 말합니다.",
							" 수집 저작물 - 백과사전, 선집, 연감, 정기간행물 등 여러 명의 저작자가 참여하거나 기여하여 작성된 저작물.",
							" 선집물 - 문학작품이나 구절을 수집하는 것.",
							" 공유 - 보호기간이 끝난 저작물은 누구든지 자유롭게 이용할 수 있는 상태." };

					temp = rand.nextInt(14);
					AlertDialog.Builder alertdlg = new AlertDialog.Builder(
							Act3.this);
					alertdlg.setIcon(R.drawable.trophy);
					alertdlg.setTitle("놀이 끝");
					alertdlg.setMessage("짝짝짝~~~ "+turns+"돌 만에 다 맞췄습니다.");
					alertdlg.setPositiveButton("확인",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.cancel();
								}
							});
					alertdlg.show();
					count= 0;
					goal= 0;

				} else {
					mPool.play(mIntro, 1, 1, 0, 0, 1);
					Toast.makeText(Act3.this, "딩동댕", Toast.LENGTH_SHORT)
							.show();
				}

			} else {
				seconedCard.button.setBackgroundDrawable(backImage);
				firstCard.button.setBackgroundDrawable(backImage);
			}

			firstCard = null;
			seconedCard = null;
		}
	}

}