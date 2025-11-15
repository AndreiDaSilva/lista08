package crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class RSAKeyPairGenerator {

	public static KeyPair generateKeyPair() throws Exception {
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(2048);
		return gen.generateKeyPair();
	}
}
