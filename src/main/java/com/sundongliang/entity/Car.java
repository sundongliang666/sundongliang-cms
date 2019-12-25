package com.sundongliang.entity;



import org.hibernate.validator.constraints.NotBlank;

public class Car {

	private Integer cid;
	@NotBlank(message="名称不能为空")
	private String cname;
	@NotBlank(message="品牌不能为空")
	private String trademark;
	@NotBlank(message="日租金不能为空")
	private String rent;
	@NotBlank(message="最低驾驶车型不能为空")
	private String car_type;
	private String pic_url;
	private String created;
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getTrademark() {
		return trademark;
	}
	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}
	public String getRent() {
		return rent;
	}
	public void setRent(String rent) {
		this.rent = rent;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public Car(Integer cid, String cname, String trademark, String rent,
			String car_type, String pic_url, String created) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.trademark = trademark;
		this.rent = rent;
		this.car_type = car_type;
		this.pic_url = pic_url;
		this.created = created;
	}
	public Car() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Car [cid=" + cid + ", cname=" + cname + ", trademark="
				+ trademark + ", rent=" + rent + ", car_type=" + car_type
				+ ", pic_url=" + pic_url + ", created=" + created + "]";
	}
	
	
}

