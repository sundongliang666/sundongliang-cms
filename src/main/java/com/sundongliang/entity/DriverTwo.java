package com.sundongliang.entity;

import org.hibernate.validator.constraints.NotBlank;

public class DriverTwo {
	@NotBlank(message="手机号码不能为空")
	private String phone;
	@NotBlank(message="密码不能为空")
	private String password;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public DriverTwo(String phone, String password) {
		super();
		this.phone = phone;
		this.password = password;
	}
	public DriverTwo() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "DriverTwo [phone=" + phone + ", password=" + password + "]";
	}

	
}
