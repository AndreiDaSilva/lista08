import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author Andrei R da Silva
 *
 */
public class GerandoChaveRSA {

	static FileWriter arq;
	static File dir;

	private static File getFileDir() {
		if (dir == null) {
			dir = new File("src/keys");

		if (!dir.exists()) {
				dir.mkdir();
			}
		}

		return dir;
	}

	private static void saveKeyToFile(String fileName, String content) throws IOException {
		File file = new File(getFileDir(), fileName + ".txt");
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(content);
		}
		System.out.println("Arquivo salvo em: " + file.getAbsolutePath());
	}

	private static void savePrivateKey(PrivateKey privateKey) throws IOException {
		String encoded = Base64.getEncoder().encodeToString(privateKey.getEncoded());
		saveKeyToFile("PrivateKey", encoded);
	}

	private static void savePublicKey(PublicKey publicKey) throws IOException {
		String encoded = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		saveKeyToFile("PublicKey", encoded);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);

		KeyPair kp = kpg.generateKeyPair();
		PublicKey publicKey = kp.getPublic();
		PrivateKey privateKey = kp.getPrivate();

		savePublicKey(publicKey);
		savePrivateKey(privateKey);

		KeyFactory factory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec pks = factory.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);

		System.out.println("Module: " + pks.getModulus());
		System.out.println("Exponent: " + pks.getPublicExponent());

		factory = KeyFactory.getInstance("RSA");
		RSAPrivateKeySpec prks = factory.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);

		System.out.println("Módulo: " + prks.getModulus());
		System.out.println("Expoente (d): " + prks.getPrivateExponent());
	}
}
