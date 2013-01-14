package com.jjoe64.graphviewdemos;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.ValueDependentColor;

public class StylesGraph extends Activity {
	private final Handler mHandler = new Handler();
	private Runnable mTimer;
	private GraphView graphView;
	private GraphViewSeries exampleSeries2;

	private double getRandom() {
		double high = 3;
		double low = 0.5;
		return Math.random() * (high - low) + low;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graphs);

		/*
		 * Graph 1: individual label colors
		 */
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(new float[] { 1f,2f,3f,4f,5f } ,
				new float[] { 3.1f,1.2f,1.3f,1.4f,2.5f } );
/*		
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
				new GraphViewData(1, 2.0d)
				, new GraphViewData(2, 1.5d)
				, new GraphViewData(2.5, 3.0d) // another frequency
				, new GraphViewData(3, 2.5d)
				, new GraphViewData(4, 1.0d)
				, new GraphViewData(5, 3.0d)
		});
*/
		// graph with dynamically genereated horizontal and vertical labels
		GraphView graphView;
		if (getIntent().getStringExtra("type").equals("bar")) {
			graphView = new BarGraphView(
					this // context
					, "GraphViewDemo" // heading
			);
		} else {
			graphView = new LineGraphView(
					this // context
					, "GraphViewDemo" // heading
			);
		}

		// set styles
		graphView.getGraphViewStyle().setGridColor(Color.GREEN);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.YELLOW);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);

		graphView.addSeries(exampleSeries); // data

		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);


		/*
		 * Graph 2: colors bar rectangles are value dependent
		 */

		// init example series data
		GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
		seriesStyle.setValueDependentColor(new ValueDependentColor() {
			@Override
			public int get(float x, float y) {
				// the higher the more red
				return Color.rgb((int)(150+((y/3)*100)), (int)(150-((y/3)*150)), (int)(150-((y/3)*150)));
			}
		});
		exampleSeries2 = new GraphViewSeries("aaa", seriesStyle, 
				new float[] { 1f,2f,3f,4f,5f } ,
				new float[] { 2.1f,1.2f,3.3f,1.4f,2.5f } );
/*
		exampleSeries2 = new GraphViewSeries("aaa", seriesStyle, new GraphViewData[] {
				new GraphViewData(1, 2.0d)
				, new GraphViewData(2, 1.5d)
				, new GraphViewData(2.5, 3.0d) // another frequency
				, new GraphViewData(3, 2.5d)
				, new GraphViewData(4, 1.0d)
				, new GraphViewData(5, 3.0d)
		});
*/
		// graph with dynamically genereated horizontal and vertical labels
		// EFFECT ONLY VISIBLE AS BAR GRAPH !!!!!!
		graphView = new BarGraphView(
				this // context
				, "GraphViewDemo" // heading
		);
		graphView.addSeries(exampleSeries2); // data

		layout = (LinearLayout) findViewById(R.id.graph2);
		layout.addView(graphView);

	}

	@Override
	protected void onPause() {
		mHandler.removeCallbacks(mTimer);
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mTimer = new Runnable() {
			@Override
			public void run() {
				exampleSeries2.resetData(
						new float[] { 1f,2f,3f,4f,5f } ,
				        new float[] { (float)getRandom(),(float)getRandom(),0.3f,(float)getRandom(),(float)getRandom() },
				        5 );
/*				
				exampleSeries2.resetData(new GraphViewData[] {
						new GraphViewData(1, getRandom())
						, new GraphViewData(2, getRandom())
						, new GraphViewData(2.5, getRandom()) // another frequency
						, new GraphViewData(3, getRandom())
						, new GraphViewData(4, getRandom())
						, new GraphViewData(5, getRandom())
				});
				*/
				mHandler.postDelayed(this, 300);
			}
		};
		mHandler.postDelayed(mTimer, 300);
	}

}
