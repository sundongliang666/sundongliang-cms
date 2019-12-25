package com.sundongliang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mysql.jdbc.Driver;
import com.sundongliang.entity.Car;
import com.sundongliang.entity.DriveType;
import com.sundongliang.entity.DriverTwo;



public interface DriverMapper {

	@Select("select * from t_drive_type")
	List<DriveType> list();

	@Insert("insert into t_car values(null,#{cname},#{trademark},#{rent},#{car_type},#{pic_url},#{created})")
	void add(Car c);

	@Insert("insert into t_driver values(null,#{dname},#{phone},#{password},#{driver_license},#{drive_type},#{issued},#{created})")
	void zc(Driver d);

	@Select("select * from t_driver where phone = #{phone} and password = #{password}")
	Driver getList(DriverTwo d);

	@Select("select t.include from t_driver d LEFT JOIN t_drive_type t on d.drive_type = t.code")
	String driver(Driver d1);

	@Select("select * from t_car where car_type = #{split} ORDER BY rent desc ")
	List<Car> cars(String split);

	@Select("select * from t_car where cid = #{cid}")
	Car toUpdate(Car c);

	@Update("update t_car set cname=#{cname},trademark=#{trademark},rent=#{rent},car_type=#{car_type},pic_url=#{pic_url},created=#{created} where cid = #{cid}")
	void update(Car c);
	



	

}
