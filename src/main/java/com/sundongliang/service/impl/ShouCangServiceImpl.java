package com.sundongliang.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sundongliang.cms.utils.StringUtils;
import com.sundongliang.entity.ShouCang;
import com.sundongliang.mapper.ShouCangMapper;
import com.sundongliang.service.ShouCangService;


@Service
public class ShouCangServiceImpl implements ShouCangService{

	@Autowired
	ShouCangMapper shouCangMapper;
	/**
	 * 添加收藏夹的方法
	 * @param shouCang 收藏夹类
	 */
	public int addShouCang(ShouCang shouCang){
		try {
			boolean httpUrl = StringUtils.isHttpUrl(shouCang.getUrl());
			if(httpUrl==true){
				int i=shouCangMapper.addShouCang(shouCang);
				System.out.println(i);
				return i;
			}
		} catch (Exception e) {
			System.err.println("CMSRuntimeException");
		}
		//
		return 0;
	}
	
	/**
	 * 删除收藏夹的方法
	 * @param id 收藏夹的Id
	 */
	public int delShouCangById(int id){
		int i =shouCangMapper.delShouCangById(id);
		return i;
	}
	
	public List<ShouCang> findShouCang(Integer id){
		List<ShouCang> list =shouCangMapper.findShouCang(id);
		for (ShouCang shouCang : list) {
			System.out.println(shouCang);
		}
		return list;
		
	}

	
	
	
}
