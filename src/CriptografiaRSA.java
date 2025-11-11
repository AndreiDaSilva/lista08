import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import comum.FileService;

public class CriptografiaRSA {
	private static GerandoChaveRSA gerandoChaveRSA;
	private FileService fileService;

	private FileService getFileService() {
		if (fileService == null) {
			fileService = new FileService();
		}
		return fileService;
	}

	public void gerarTextoCriptografado(String msg) throws Exception {
		new GerandoChaveRSA().gerarChave();
		try {
			String publicKeyBase64 = getGerandoChaveRSA().lerAquivos("PublicKey.txt");
			String privateKeyBase64 = getGerandoChaveRSA().lerAquivos("PublicKey.txt");

			PublicKey publicKey = loadKeyPublic(publicKeyBase64);

			byte[] textoCifrado = criptografiaRSA(getMsg(), publicKey);

			String textoCifradoBase64 = Base64.getEncoder().encodeToString(textoCifrado);



		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static GerandoChaveRSA getGerandoChaveRSA() {
		if (gerandoChaveRSA == null) {
			gerandoChaveRSA = new GerandoChaveRSA();
		}
		return gerandoChaveRSA;
	}

	private static byte[] criptografiaRSA(String msg, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(msg.getBytes());
	}

	private static byte[] descriptografiaRSA(String msg, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(msg.getBytes());
	}

	private static PublicKey loadKeyPublic(String base64Key) throws Exception {
		byte[] decodedKey = Base64.getDecoder().decode(base64Key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
		return KeyFactory.getInstance("RSA").generatePublic(keySpec);
	}

	private static PrivateKey loadKeyPrivate(String base64Key) throws Exception {
		byte[] decodedKey = Base64.getDecoder().decode(base64Key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
		return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
	}
}
