import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

public class GerandoChaveRSA {

	static FileWriter arq;





	private static void savePrivateKey(PrivateKey privateKey) throws IOException {

	}

	private static void savePublicKey(PublicKey publicKey) throws IOException {

	}

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

	}

	public void gerarChave() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);

		KeyPair kp = kpg.generateKeyPair();
		PublicKey publicKey = kp.getPublic();
		PrivateKey privateKey = kp.getPrivate();

		savePublicKey(publicKey);
		savePrivateKey(privateKey);

		KeyFactory factory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec pks = factory.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);

		factory = KeyFactory.getInstance("RSA");
		RSAPrivateKeySpec prks = factory.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);
	}
}
