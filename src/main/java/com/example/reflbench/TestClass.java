package com.example.reflbench;

public class TestClass implements TestInterface {
	public int intValue;
	public String stringValue;
	
	private int intPrivateValue = 56;
	private String stringPrivateValue = "Basia";
		
	public TestClass() {
	}

	public TestClass(int intValue, String stringValue) {
		super();
		this.intValue = intValue;
		this.stringValue = stringValue;
	}

	@Override
	public String sayHello(){
		return "Hello my friend " + stringValue;
	}
	
	public String sayHelloToPrivate(){
		return "Hello my friend " + stringPrivateValue;
	}

	public int getIntPrivateValue() {
		return intPrivateValue;
	}

	public void setIntPrivateValue(int intPrivateValue) {
		this.intPrivateValue = intPrivateValue;
	}

	public String getStringPrivateValue() {
		return stringPrivateValue;
	}

	public void setStringPrivateValue(String stringPrivateValue) {
		this.stringPrivateValue = stringPrivateValue;
	}
}
