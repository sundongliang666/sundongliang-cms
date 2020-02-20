package com.sundongliang.common;

import java.io.UnsupportedEncodingException;



import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * @author zhuzg
 *
 */
public class CmsUtils {
	
	
	/**
	 *  加盐加密
	 * @param src  明文
	 * @param salt  盐
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String encry(String src,String salt) {
		String md5Hex = DigestUtils.md5Hex(salt + src + salt);
		//byte[] md5 = DigestUtils.md5(salt + src + salt);
		return md5Hex;
		 
		
	}
}
