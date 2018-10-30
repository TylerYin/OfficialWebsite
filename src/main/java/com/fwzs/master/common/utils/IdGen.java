/**
 * 
 */
package com.fwzs.master.common.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.h2.util.New;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fwzs.master.modules.fwzs.entity.BsProduct;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * 
 * @author numberone
 * @version 2013-01-15
 */
@Service
@Lazy(false)
public class IdGen implements IdGenerator, SessionIdGenerator {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}

	/**
	 * Activiti ID 生成
	 */
	@Override
	public String getNextId() {
		return IdGen.uuid();
	}

	@Override
	public Serializable generateId(Session session) {
		return IdGen.uuid();
	}

	/*
	 * 返回长度为【strLength】的随机数，在前面补0
	 */
	public static String getFixLenthString(int strLength) {
		Random rm = new Random();
		// 获得随机数
		long pross = (long) ((1 + rm.nextDouble()) * Math.pow(10, strLength));
		// 将获得的获得随机数转化为字符串
		String fixLenthString = String.valueOf(pross);
		// 返回固定的长度的随机数
		return fixLenthString.substring(1, strLength + 1);
	}

	/**
	 * 生成不重复的21位随机码
	 * 
	 * @return
	 */
	public static String genRadom21() {
		//long time=System.nanoTime();
		//return String.valueOf(time).substring(8)+IdGen.getFixLenthString(15);
		/**
		 * 2017-11-06修改;原因nanoTime在window下长度位14,在linux下长度位16
		 */
		String nanoTime=String.valueOf(System.nanoTime());
		/**
		 * 为了增加不重复的概率，添加时间系数
		 * getTime 方法返回一个整数值，这个整数代表了从 1970 年 1 月 1 日开始计算到 Date 对象中的时间之间的毫秒数。
		 */
		String time=String.valueOf(new Date().getTime());
		return nanoTime.substring(nanoTime.length()-6) +time.substring(time.length()-9)+IdGen.getFixLenthString(6);
	}
	/**
	 * 生成不重复的32位防伪码
	 * 
	 * @return
	 */
	public static String genFwm32(BsProduct bsProduct) {
		String preUrl=bsProduct.getRegType()+bsProduct.getRegCode()+bsProduct.getScType()+bsProduct.getProdSpec().getSpecCode();
		return preUrl+genRadom21();
	}
	/**
	 * 生成生码文件序列化（格式171002001）
	 * @param num
	 * @return
	 */
	public static String genProdFileSerialNum(int num,String pre){
		SerialNumber serial = new FileEveryDaySerialNumber(num, "SerialNumber.dat");
//		System.out.println(serial.getSerialNumber());
		return pre+serial.getSerialNumber();
	}


}
