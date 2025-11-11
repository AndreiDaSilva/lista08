package comum;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface FileService {

	void savePublicKey(PublicKey publicKey) throws IOException;

	void savePrivateKey(PrivateKey privateKey) throws IOException;

	void saveCriptonMsg(String msg) throws IOException;

	String lerAquivos(String file) throws IOException;
}
