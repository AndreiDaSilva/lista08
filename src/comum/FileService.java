package comum;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface FileService {

	void savePublicKey(PublicKey publicKey) throws IOException;

	void savePrivateKey(PrivateKey privateKey) throws IOException;

	void saveCriptonMsg(String fileName, String textoCifrado) throws IOException;

	String lerArquivos(String file) throws IOException;
}
