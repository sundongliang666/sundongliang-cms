package com.sundongliang.service;

import java.util.List;

import com.sundongliang.entity.ShouCang;



public interface ShouCangService {

	int addShouCang(ShouCang sc);

	int delShouCangById(int i);

	List<ShouCang> findShouCang(Integer integer);

	 

}
