package com.example.reflbench;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.jfree.ui.RefineryUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BenchmarkTest {

	private TestClass tc;
	private static final int counter = 10000;
	private static final int smallCounter = 100;
	private Long start, stop;
	private List<Double> data1 = new ArrayList<Double>();
	private List<Double> data2 = new ArrayList<Double>();
	private Field stringFld;
	private Field intFld;

	@Before
	public void initTest() throws NoSuchFieldException, SecurityException {
		tc = new TestClass(90, "Ula");
		for (int i = 0; i < counter; i++) {
			tc.stringValue = "Kasia";
			tc.intValue = 89;
			tc.setIntPrivateValue(89);
			tc.setStringPrivateValue("Kasia");
			tc.sayHello();
		}
		data1.clear();
		data2.clear();
	}

	@After
	public void destroyTest() {
		tc = null;
	}

	private void getChart(final String title) {
		final XYChartData publicValuesSet = new XYChartData(title, data1, data2);
		publicValuesSet.pack();
		RefineryUtilities.centerFrameOnScreen(publicValuesSet);
		publicValuesSet.setVisible(true);
	}

	@Test
	public void publicFieldSetTest() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {

		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				tc.stringValue = "Kasia";
				tc.intValue = 89;
			}
			stop = System.currentTimeMillis() - start;
			data1.add((double) stop);
		}

		stringFld = TestClass.class.getDeclaredField("stringValue");
		intFld = TestClass.class.getDeclaredField("intValue");
		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				stringFld.set(tc, "Zosia");
				intFld.set(tc, 87);
			}
			stop = System.currentTimeMillis() - start;
			data2.add((double) stop);
		}

		getChart("Public values - set");
	}

	@Test
	public void publicFieldGetTest() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {

		int temp;
		String temp1;
		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				temp = tc.intValue;
				temp1 = tc.stringValue;
			}
			stop = System.currentTimeMillis() - start;
			data1.add((double) stop);
		}

		stringFld = TestClass.class.getDeclaredField("stringValue");
		intFld = TestClass.class.getDeclaredField("intValue");
		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				intFld.get(tc);
				stringFld.get(tc);
			}
			stop = System.currentTimeMillis() - start;
			data2.add((double) stop);
		}

		getChart("Public values - get");
	}

	@Test
	public void privateFieldSetTest() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {

		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				tc.setIntPrivateValue(89);
				tc.setStringPrivateValue("Kasia");
			}
			stop = System.currentTimeMillis() - start;
			data1.add((double) stop);
		}

		stringFld = TestClass.class.getDeclaredField("stringPrivateValue");
		intFld = TestClass.class.getDeclaredField("intPrivateValue");
		stringFld.setAccessible(true);
		intFld.setAccessible(true);
		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				stringFld.set(tc, "Zosia");
				intFld.set(tc, 87);
			}
			stop = System.currentTimeMillis() - start;
			data2.add((double) stop);
		}
		getChart("Private values - set");
	}

	@Test
	public void privateFieldGetTest() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {

		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				tc.getIntPrivateValue();
				tc.getStringPrivateValue();
			}
			stop = System.currentTimeMillis() - start;
			data1.add((double) stop);
		}

		stringFld = TestClass.class.getDeclaredField("stringPrivateValue");
		intFld = TestClass.class.getDeclaredField("intPrivateValue");
		stringFld.setAccessible(true);
		intFld.setAccessible(true);

		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				intFld.get(tc);
				stringFld.get(tc);
			}
			stop = System.currentTimeMillis() - start;
			data2.add((double) stop);
		}
		getChart("Private values - get");
	}

	@Test
	public void methodTest() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				tc.sayHello();
			}

			stop = System.currentTimeMillis() - start;
			data1.add((double) stop);
		}

		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				TestClass.class.getMethod("sayHello", null).invoke(tc, null);
			}
			stop = System.currentTimeMillis() - start;
			data2.add((double) stop);
		}
		getChart("Method");
	}

	@Test
	public void methodInterfaceTest() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		TestInterface ti = new TestClass(90, "Ula");
		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				ti.sayHello();
			}

			stop = System.currentTimeMillis() - start;
			data1.add((double) stop);
		}

		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				TestClass.class.getMethod("sayHello", null).invoke(tc, null);
			}
			stop = System.currentTimeMillis() - start;
			data2.add((double) stop);
		}
		
		getChart("Method - interface");

	}
}
