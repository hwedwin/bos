package com.sirier.web.action;

import java.util.Random;

/**
 * 生成随机不相同纯数字验证码
 * 
 * @author tps
 *
 */
public class RandStringUtils {

	public static String getCode() {
        StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			sb.append(getRandomString());
		}
		return sb.toString();
	}

	public static int getRandomString() {
		int num = new Random().nextInt(10);
		return num;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(getCode());
		}
	}
}
