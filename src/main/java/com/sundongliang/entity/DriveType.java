package com.sundongliang.entity;

public class DriveType {

	private String code;
	private String name;
	private String include;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInclude() {
		return include;
	}
	public void setInclude(String include) {
		this.include = include;
	}
	public DriveType(String code, String name, String include) {
		super();
		this.code = code;
		this.name = name;
		this.include = include;
	}
	public DriveType() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "DriveType [code=" + code + ", name=" + name + ", include="
				+ include + "]";
	}
	
	
}
