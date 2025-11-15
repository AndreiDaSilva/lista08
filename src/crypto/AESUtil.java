package com.andrei.crypto.aes;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESUtil {

	public static SecretKey generateAESKey() throws Exception {
		KeyGenerator gen = KeyGenerator.getInstance("AES");
		gen.init(256);
		return gen.generateKey();
	}

	public static byte[] generateIV() {
		byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		return iv;
	}

	public static byte[] encrypt(byte[] data, SecretKey key, byte[] iv) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] data, SecretKey key, byte[] iv) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
		return cipher.doFinal(data);
	}

	public static SecretKey restoreKey(byte[] raw) {
		return new SecretKeySpec(raw, "AES");
	}
}
