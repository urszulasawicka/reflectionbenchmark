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

	@Before
	public void initTest() {
		tc = new TestClass(90, "Ula");
		for (int i = 0; i < counter; i++) {
			tc.stringValue = "Kasia";
			tc.intValue = 89;
			tc.setIntPrivateValue(89);
			tc.setStringPrivateValue("Kasia");
			tc.sayHello();
		}
	}

	@After
	public void destroyTest() {
		tc = null;
	}

	@Test
	public void publicFieldTest() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		Long start, stop;
		List<Double> data1 = new ArrayList<Double>();
		List<Double> data2 = new ArrayList<Double>();

		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				tc.stringValue = "Kasia";
				tc.intValue = 89;
			}
			stop = System.currentTimeMillis() - start;
			data1.add((double) stop);
		}

		Field stringFld = TestClass.class.getDeclaredField("stringValue");
		Field intFld = TestClass.class.getDeclaredField("intValue");
		for (int j = 0; j < smallCounter; j++) {
			start = System.currentTimeMillis();
			for (int i = 0; i < counter; i++) {
				stringFld.set(tc, "Zosia");
				intFld.set(tc, 87);
			}
			stop = System.currentTimeMillis() - start;
			data2.add((double) stop);
		}
		final XYChartData publicValues = new XYChartData("Public values",
				data1, data2);
		publicValues.pack();
		RefineryUtilities.centerFrameOnScreen(publicValues);
		publicValues.setVisible(true);

		int intFldValue = 0;
		String stringFldValue = new String();
		start = System.currentTimeMillis();
		for (int i = 0; i < counter; i++) {
			intFldValue = (int) intFld.get(tc);
			stringFldValue = (String) stringFld.get(tc);
		}
		stop = System.currentTimeMillis() - start;
	}

	@Test
	public void privateFieldTest() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		Long start = System.currentTimeMillis();
		for (int i = 0; i < counter; i++) {
			tc.setIntPrivateValue(89);
			tc.setStringPrivateValue("Kasia");
		}
		Long stop = System.currentTimeMillis() - start;

		Field stringFld = TestClass.class
				.getDeclaredField("stringPrivateValue");
		Field intFld = TestClass.class.getDeclaredField("intPrivateValue");
		stringFld.setAccessible(true);
		intFld.setAccessible(true);
		start = System.currentTimeMillis();
		for (int i = 0; i < counter; i++) {
			stringFld.set(tc, "Zosia");
			intFld.set(tc, 87);
		}
		stop = System.currentTimeMillis() - start;

		int intFldValue = 0;
		String stringFldValue = new String();
		start = System.currentTimeMillis();
		for (int i = 0; i < counter; i++) {
			intFldValue = (int) intFld.get(tc);
			stringFldValue = (String) stringFld.get(tc);
		}
		stop = System.currentTimeMillis() - start;
	}

	@Test
	public void methodTest() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		tc.stringValue = "Ula";
		Long start = System.currentTimeMillis();
		String hey = new String();
		for (int i = 0; i < counter; i++) {
			hey = tc.sayHello();
		}
		Long stop = System.currentTimeMillis() - start;

		start = System.currentTimeMillis();
		hey = new String();
		for (int i = 0; i < counter; i++) {
			hey = (String) TestClass.class.getMethod("sayHello", null).invoke(
					tc, null);
		}
		stop = System.currentTimeMillis() - start;
	}
}
