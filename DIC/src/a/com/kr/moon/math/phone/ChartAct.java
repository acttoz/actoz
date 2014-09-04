package a.com.kr.moon.math.phone;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChartAct extends Activity {
	public AlertDialog mDialog = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart);
		mDialog = resultDialog();
		mDialog.show();
		mDialog.getWindow().getAttributes();
		TextView textView = (TextView) mDialog
				.findViewById(android.R.id.message);

		// textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

		Button btn1 = mDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

		Button btn2 = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);

		btn1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		btn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

	}

	public AlertDialog resultDialog() {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);

		ab.setTitle("학기 선택");
		ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		ab.setPositiveButton("1학기", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				showChart(1);
			}

		});

		ab.setNegativeButton("2학기", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				showChart(2);
				
			}
		});

		return ab.create();
	}

	public void showChart(int period) {// 표시할 수치값

		List<ArrayList<Double>> values = new ArrayList<ArrayList<Double>>();
		String chapterList[][] = {
				{ "1.약수와 배수", "2.약분과 통분", "3.분수의 덧셈과 뺄셈", "4.분수의 곱셈",
						"5.도형의 합동", "6.직육면체와 정육면체", "7.평면도형의 넓이", "8.여러 가지 단위" },
				{ "1.분수와 소수", "2.분수의 나눗셈", "3.도형의 대칭", "4.소수의 곱셈", "5.소수의 나눗셈",
						"6.자료의 표현과 해석", "7.비와 비율", "8.여러가지 문제" } };
		ArrayList<Double> whole = new ArrayList<Double>();
		ArrayList<Double> part = new ArrayList<Double>();

		/** 그래프 출력을 위한 그래픽 속성 지정객체 */
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

		// 레이아웃

		int marginT = 100;
		int marginL = 50;
		int marginB = 220;
		int marginR = 5;
		int[] margins = { marginT, marginL, marginB, marginR };
		renderer.setMargins(margins);

		// 상단 표시 제목과 글자 크기
		
		renderer.setChartTitleTextSize(20);
		renderer.setChartTitle(Integer.toString(period)+"학기");
		// 분류에 대한 이름
		String[] titles = new String[] { "진도", "정답" };

		// 항목을 표시하는데 사용될 색상값
		int[] colors = new int[] { Color.WHITE, Color.YELLOW };

		// 분류명 글자 크기 및 각 색상 지정
		renderer.setLegendTextSize(25);
		renderer.setLegendHeight(80);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}

		// X,Y축 항목이름과 글자 크기
		renderer.setXTitle("");
		renderer.setYTitle("");
		renderer.setAxisTitleTextSize(25);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.parseColor("#4c7e7f"));
		renderer.setMarginsColor(Color.parseColor("#4c7e7f"));
		renderer.setOrientation(Orientation.VERTICAL);

		// 수치값 글자 크기 / X축 최소,최대값 / Y축 최소,최대값
		renderer.setLabelsTextSize(20);
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(8.5);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(30);
		// X,Y축 라인 색상
		renderer.setAxesColor(Color.WHITE);
		// 상단제목, X,Y축 제목, 수치값의 글자 색상
		renderer.setLabelsColor(Color.WHITE);

		// X축의 표시 간격
		renderer.setXLabels(0);
		// Y축의 표시 간격
		renderer.setYLabels(10);
		// X,Y축 정렬방향
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setYLabelsAlign(Align.LEFT);
		// X,Y축 스크롤 여부 ON/OFF
		renderer.setPanEnabled(false, false);
		// renderer.setPanLimits(new double[] { 0, 50, 0, 0 });
		// ZOOM기능 ON/OFF
		renderer.setZoomEnabled(false, false);
		// ZOOM 비율
		renderer.setZoomRate(0.5f);
		// 막대간 간격
		renderer.setBarSpacing(0.5f);
		// renderer.setXLabelsPadding(200);
		renderer.setYLabelsPadding(15);
		// renderer.setXLabelsColor(0, Color.parseColor("#4c7e7f"));
		// renderer.setXLabelsColor(Color.parseColor("#4c7e7f"));

		// 데이터 세팅----------------
		// CategorySeries seriesWhole = new CategorySeries(titles[0]);
		// CategorySeries seriesPart = new CategorySeries(titles[1]);
		DayHelper mHelper;
		SQLiteDatabase db;
		mHelper = new DayHelper(ChartAct.this);
		db = mHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT distinct chapter FROM result where period='"
						+ period + "' ORDER BY chapter ASC;", null);
		if (cursor.moveToFirst()) {
			// whole = new double[cursor.getInt(0)];
			int i = 0;
			do {
				i++;
				String chapterTitle = chapterList[period - 1][cursor.getInt(0) - 1];
				renderer.addXTextLabel((double) i, chapterTitle);

				Cursor cursor2 = db.rawQuery(
						"SELECT Count(num1),sum(num1),sum(num2),sum(num3) FROM result where period='"
								+ period + "' and chapter='"
								+ cursor.getString(0) + "' ORDER BY time ASC;",
						null);

				if (cursor2.moveToFirst()) {
					whole.add((double) (cursor2.getInt(0) * 3));
					part.add((double) (cursor2.getInt(1) + cursor2.getInt(2) + cursor2
							.getInt(3)));
					// seriesWhole.add(cursor2.getInt(0));
					// seriesPart.add(cursor2.getInt(1) + cursor2.getInt(2)
					// + cursor2.getInt(3));
					// do {
					// } while (cursor2.moveToNext());
				}
				cursor2.close();
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		// 데이터 입력
		values.add(whole);
		values.add(part);

		// 설정 정보 설정-----------------
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		for (int i = 0; i < titles.length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			ArrayList<Double> v = (ArrayList<Double>) values.get(i);
			int seriesLength = v.size();
			for (int k = 0; k < seriesLength; k++) {
				series.add(v.get(k));
			}
			dataset.addSeries(series.toXYSeries());
		}

		// 그래프 객체 생성
		GraphicalView gv = ChartFactory.getBarChartView(this, dataset,
				renderer, Type.DEFAULT);

		// 그래프를 LinearLayout에 추가
		LinearLayout llBody = (LinearLayout) findViewById(R.id.llBody);
		llBody.addView(gv);
	}
}