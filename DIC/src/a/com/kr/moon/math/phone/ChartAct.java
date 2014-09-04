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

		ab.setTitle("�б� ����");
		ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		ab.setPositiveButton("1�б�", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				showChart(1);
			}

		});

		ab.setNegativeButton("2�б�", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				showChart(2);
				
			}
		});

		return ab.create();
	}

	public void showChart(int period) {// ǥ���� ��ġ��

		List<ArrayList<Double>> values = new ArrayList<ArrayList<Double>>();
		String chapterList[][] = {
				{ "1.����� ���", "2.��а� ���", "3.�м��� ������ ����", "4.�м��� ����",
						"5.������ �յ�", "6.������ü�� ������ü", "7.��鵵���� ����", "8.���� ���� ����" },
				{ "1.�м��� �Ҽ�", "2.�м��� ������", "3.������ ��Ī", "4.�Ҽ��� ����", "5.�Ҽ��� ������",
						"6.�ڷ��� ǥ���� �ؼ�", "7.��� ����", "8.�������� ����" } };
		ArrayList<Double> whole = new ArrayList<Double>();
		ArrayList<Double> part = new ArrayList<Double>();

		/** �׷��� ����� ���� �׷��� �Ӽ� ������ü */
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

		// ���̾ƿ�

		int marginT = 100;
		int marginL = 50;
		int marginB = 220;
		int marginR = 5;
		int[] margins = { marginT, marginL, marginB, marginR };
		renderer.setMargins(margins);

		// ��� ǥ�� ����� ���� ũ��
		
		renderer.setChartTitleTextSize(20);
		renderer.setChartTitle(Integer.toString(period)+"�б�");
		// �з��� ���� �̸�
		String[] titles = new String[] { "����", "����" };

		// �׸��� ǥ���ϴµ� ���� ����
		int[] colors = new int[] { Color.WHITE, Color.YELLOW };

		// �з��� ���� ũ�� �� �� ���� ����
		renderer.setLegendTextSize(25);
		renderer.setLegendHeight(80);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}

		// X,Y�� �׸��̸��� ���� ũ��
		renderer.setXTitle("");
		renderer.setYTitle("");
		renderer.setAxisTitleTextSize(25);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.parseColor("#4c7e7f"));
		renderer.setMarginsColor(Color.parseColor("#4c7e7f"));
		renderer.setOrientation(Orientation.VERTICAL);

		// ��ġ�� ���� ũ�� / X�� �ּ�,�ִ밪 / Y�� �ּ�,�ִ밪
		renderer.setLabelsTextSize(20);
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(8.5);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(30);
		// X,Y�� ���� ����
		renderer.setAxesColor(Color.WHITE);
		// �������, X,Y�� ����, ��ġ���� ���� ����
		renderer.setLabelsColor(Color.WHITE);

		// X���� ǥ�� ����
		renderer.setXLabels(0);
		// Y���� ǥ�� ����
		renderer.setYLabels(10);
		// X,Y�� ���Ĺ���
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setYLabelsAlign(Align.LEFT);
		// X,Y�� ��ũ�� ���� ON/OFF
		renderer.setPanEnabled(false, false);
		// renderer.setPanLimits(new double[] { 0, 50, 0, 0 });
		// ZOOM��� ON/OFF
		renderer.setZoomEnabled(false, false);
		// ZOOM ����
		renderer.setZoomRate(0.5f);
		// ���밣 ����
		renderer.setBarSpacing(0.5f);
		// renderer.setXLabelsPadding(200);
		renderer.setYLabelsPadding(15);
		// renderer.setXLabelsColor(0, Color.parseColor("#4c7e7f"));
		// renderer.setXLabelsColor(Color.parseColor("#4c7e7f"));

		// ������ ����----------------
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

		// ������ �Է�
		values.add(whole);
		values.add(part);

		// ���� ���� ����-----------------
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

		// �׷��� ��ü ����
		GraphicalView gv = ChartFactory.getBarChartView(this, dataset,
				renderer, Type.DEFAULT);

		// �׷����� LinearLayout�� �߰�
		LinearLayout llBody = (LinearLayout) findViewById(R.id.llBody);
		llBody.addView(gv);
	}
}