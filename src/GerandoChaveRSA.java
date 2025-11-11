package comum;

import module.Application;
import java.io.IOException;
import java.security.*;

/**
 * Responsável por gerar e salvar as chaves RSA.
 */
public class GerandoChaveRSA {

	private FileService fileService;

	private FileService getFileService() {
		if (fileService == null) {
			fileService = Application.getSingleton(FileService.class);
		}
		return fileService;
	}

	public void gerarChave() throws NoSuchAlgorithmException, IOException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);

		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();

		getFileService().savePublicKey(publicKey);
		getFileService().savePrivateKey(privateKey);
	}

	public String lerArquivos(String file) throws IOException {
		return getFileService().lerArquivos(file);
	}
}
