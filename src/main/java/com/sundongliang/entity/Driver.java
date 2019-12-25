package com.sundongliang.entity;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class Driver {
	
	private Integer did;
	@NotBlank(message="司机姓名不能为空")
	private String dname;
	@NotBlank(message="手机号码不能为空")
	@Pattern(regexp="^1[3456789]\\d{9}$",message="必须输入正确的手机号码")
	private String phone;
	@NotBlank(message="密码号码不能为空")
	@Pattern(regexp="^\\w{6,}$",message="密码不能小于6位")
	private String password;
	@NotBlank(message="驾驶证号不能为空")
	private String driver_license;
	@NotBlank(message="驾驶证等级不能为空")
	private String drive_type;
	@NotBlank(message="发证不能为空")
	private String issued;
	private String created;
	public Integer getDid() {
		return did;
	}
	public void setDid(Integer did) {
		this.did = did;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
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
	public String getDriver_license() {
		return driver_license;
	}
	public void setDriver_license(String driver_license) {
		this.driver_license = driver_license;
	}
	public String getDrive_type() {
		return drive_type;
	}
	public void setDrive_type(String drive_type) {
		this.drive_type = drive_type;
	}
	public String getIssued() {
		return issued;
	}
	public void setIssued(String issued) {
		this.issued = issued;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public Driver(Integer did, String dname, String phone, String password,
			String driver_license, String drive_type, String issued,
			String created) {
		super();
		this.did = did;
		this.dname = dname;
		this.phone = phone;
		this.password = password;
		this.driver_license = driver_license;
		this.drive_type = drive_type;
		this.issued = issued;
		this.created = created;
	}
	public Driver() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Driver [did=" + did + ", dname=" + dname + ", phone=" + phone
				+ ", password=" + password + ", driver_license="
				+ driver_license + ", drive_type=" + drive_type + ", issued="
				+ issued + ", created=" + created + "]";
	}
	
	
}
