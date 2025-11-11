package service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import comum.FileService;

public class FileServiceImpl implements FileService {

	static File dir;

	@Override
	public void savePublicKey(PublicKey publicKey) throws IOException {
		String encoded = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		saveKeyToFile("PublicKey", encoded);
	}

	@Override
	public void savePrivateKey(PrivateKey privateKey) throws IOException {
		String encoded = Base64.getEncoder().encodeToString(privateKey.getEncoded());
		saveKeyToFile("PrivateKey", encoded);
	}

	@Override
	public void saveCriptonMsg(String msg) throws IOException {
		String encoded = Base64.getEncoder().encodeToString(msg.getBytes());
		saveKeyToFile("CriptonMsg", encoded);
	}

	@Override
	public String lerAquivos(String file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(getFileDir() + "/" + file))).trim();
	}

	private static void saveKeyToFile(String fileName, String content) throws IOException {
		File file = new File(getFileDir(), fileName + ".txt");
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(content);
		}
		System.out.println("Arquivo salvo em: " + file.getAbsolutePath());
	}

	public static File getFileDir() {
		if (dir == null) {
			dir = new File("src/keys");

			if (!dir.exists()) {
				dir.mkdir();
			}
		}

		return dir;
	}
}
