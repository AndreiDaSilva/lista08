package crypto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class RSAUtil {

	public static byte[] encrypt(byte[] data, PublicKey key) throws Exception {
		var cipher = javax.crypto.Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] data, PrivateKey key) throws Exception {
		var cipher = javax.crypto.Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(data);
	}

	public static void savePublicKeyPEM(PublicKey key, Path path) throws Exception {
		String pem = "-----BEGIN PUBLIC KEY-----\n"
				+ Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(key.getEncoded())
				+ "\n-----END PUBLIC KEY-----";

		Files.writeString(path, pem);
	}

	public static void savePrivateKeyPEM(PrivateKey key, Path path) throws Exception {
		String pem = "-----BEGIN PRIVATE KEY-----\n"
				+ Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(key.getEncoded())
				+ "\n-----END PRIVATE KEY-----";

		Files.writeString(path, pem);
	}
}
