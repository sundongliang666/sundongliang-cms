package com.sundongliang.service;

import java.util.List;

import com.mysql.jdbc.Driver;
import com.sundongliang.entity.Car;
import com.sundongliang.entity.DriveType;
import com.sundongliang.entity.DriverTwo;


public interface DriverService {

	List<DriveType> list();

	void add(Car c);

	void zc(Driver d);

	Driver getList(DriverTwo d);

	String driver(Driver d1);

	List<Car> cars(String split);

	Car toUpdate(Car c);

	void update(Car c);

	

	
	

}
