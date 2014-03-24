package com.example.reflbench;

import java.util.List;

import org.jfree.chart.ChartFactory;
import java.awt.Color;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class XYChartData extends ApplicationFrame {

	private static final long serialVersionUID = -3547996484614364280L;

	public XYChartData(final String title, List<Double> data1,
			List<Double> data2) {

		super(title);

		final XYSeries s1 = new XYSeries("Direct");
		final XYSeries s2 = new XYSeries("Reflection");

		for (int i = 1; i < data1.size(); i++) {
			s1.add(i, data1.get(i));
			s2.add(i, data2.get(i));
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(s1);
		dataset.addSeries(s2);

		final JFreeChart chart = ChartFactory.createXYLineChart(title,
				"Iteration", "Value", dataset, PlotOrientation.VERTICAL, true,
				true, false);

		final XYPlot plot = chart.getXYPlot();
		final NumberAxis domainAxis = new NumberAxis("x");
		final NumberAxis rangeAxis = new NumberAxis("y");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(rangeAxis);
		chart.setBackgroundPaint(Color.white);
		plot.setOutlinePaint(Color.black);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);

	}
}
