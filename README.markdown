Chart and Graph Library for Android
====================================

<h3>Comment by OttoES</h3>
I have done some modifications to the excelent original code to increase the performance of realtime graphs as well as very large data sets.
The following change was made specifically for fast updating of realtime graphs with high sampling rates (10Hz or faster).
Graphs with static data will only benifit much from these changes if you have a very large data set (10 000's of points)

Data used to be stored as x,y coordinates in an object and an array of objects was used to store all data points. It was changed to store data in float arrays.
Float arrays have the following advantages compared to the previous implementation:
* uses significantly less storage space
* should be faster (not sure how much without benchmarking)
* less garbage collection to be done
* less memory fragmentation
* faster to add data points to the existing data

The new structure also changes the way the data storage is allocated for appending data to the existing data.
The data arrays will now be 'oversized' when single points are added/appended.
The number of values to display in the array will therefore differ from the size.
The result is that there might already be space in the array when new values are added, the new value can be added directly without copying all the elements in the array to a newly allocated array of the new size. If there is no space a new array object is created with extra space for future use.
Unfortunately these changes are not compatable with the previous implementation. Maybe I can add a convertion constructor?

<h2>What is GraphView</h2>

GraphView is a library for Android to programmatically create flexible and nice-looking diagramms. It is easy to understand, to integrate and to customize it.
At the moment there are two different types:
<ul>
<li>Line Charts</li>
<li>Bar Charts</li>
</ul>

Tested on Android 1.6, 2.2, 2.3 and 3.0 (honeycomb, tablet).

<img src="https://github.com/jjoe64/GraphView/raw/master/GVLine.jpg" />
<img src="https://github.com/jjoe64/GraphView/raw/master/GVBar.png" />

<h2>Features</h2>

* Two chart types
Line Chart and Bar Chart.
* Draw multiple series of data
Let the diagram show more that one series in a graph. You can set a color and a description for every series.
* Show legend
A legend can be displayed inline the chart. You can set the width and the vertical align (top, middle, bottom).
* Custom labels
The labels for the x- and y-axis are generated automatically. But you can set your own labels, Strings are possible.
* Handle incomplete data
It's possible to give the data in different frequency.
* Viewport
You can limit the viewport so that only a part of the data will be displayed.
* Scrolling
You can scroll with a finger touch move gesture.
* Scaling / Zooming
Since Android 2.3! With two-fingers touch scale gesture (Multi-touch), the viewport can be changed.
* Background (line graph)
Optionally draws a light background under the diagram stroke.

<h2>How to use</h2>
<a href="http://www.jjoe64.com/p/graphview-library.html">View GraphView page http://www.jjoe64.com/p/graphview-library.html</a>

Very simple example:
<pre>
// init example series data
GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
	      new GraphViewData(1, 2.0d)
	      , new GraphViewData(2, 1.5d)
	      , new GraphViewData(3, 2.5d)
	      , new GraphViewData(4, 1.0d)
});

GraphView graphView = new LineGraphView(
      this // context
      , "GraphViewDemo" // heading
);
graphView.addSeries(exampleSeries); // data

LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
layout.addView(graphView);
</pre>

