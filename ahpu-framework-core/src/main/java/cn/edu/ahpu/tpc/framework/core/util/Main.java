package cn.edu.ahpu.tpc.framework.core.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class Main {
	public static void main(String[] args) {
		String salt = SecurityContextUtil.generateRandomSalt();
		System.out.println(salt);

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword("000000", salt);
		
		System.out.println(password);
	}
}
