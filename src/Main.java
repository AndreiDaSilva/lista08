
import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.util.Base64;

import crypto.RSAKeyPairGenerator;
import crypto.RSAUtil;

public class Main {

	public static void main(String[] args) throws Exception {

		Path outputDir = Path.of("output");
		Files.createDirectories(outputDir);

		System.out.println("=== Lista 08 - Modo Automático ===");

		// QUESTÃO 1 - Frase curta RSA
		System.out.println("\n[1] Gerando par de chaves RSA...");
		KeyPair keyPair = RSAKeyPairGenerator.generateKeyPair();

		Path pubPath = outputDir.resolve("public_key.pem");
		Path privPath = outputDir.resolve("private_key.pem");

		RSAUtil.savePublicKeyPEM(keyPair.getPublic(), pubPath);
		RSAUtil.savePrivateKeyPEM(keyPair.getPrivate(), privPath);

		System.out.println("Chaves salvas!");

		String frase = "Olá Andrei, vim comunicar que a terra não é plana.";
		System.out.println("\n[1] Criptografando frase: " + frase);

		byte[] encryptedMsg = RSAUtil.encrypt(frase.getBytes(), keyPair.getPublic());
		com.andrei.crypto.util.FileUtil.writeBytes(outputDir.resolve("mensagem.enc"), encryptedMsg);

		byte[] decryptedMsg = RSAUtil.decrypt(encryptedMsg, keyPair.getPrivate());
		com.andrei.crypto.util.FileUtil.writeBytes(outputDir.resolve("mensagem_decriptada.txt"), decryptedMsg);

		System.out.println("[1] Mensagens salvas em output/");

		// QUESTÃO 2 - PDF com RSA
		System.out.println("\n[2] Tentando criptografar PDF (deve falhar)...");
		try {
			byte[] pdf = Files.readAllBytes(Path.of("L08 - Chave assimétrica (novo).pdf"));
			RSAUtil.encrypt(pdf, keyPair.getPublic());
			System.out.println("ERRO: PDF não deveria criptografar!");
		} catch (Exception e) {
			System.out.println("Comportamento esperado: " + e.getMessage());
		}

		// QUESTÃO 3 - AES + RSA híbrido
		System.out.println("\n[3] Criptografia híbrida AES + RSA");

		Path imagePath = Path.of("img.png");
		byte[] imageBytes = Files.readAllBytes(imagePath);

		SecretKey aesKey = com.andrei.crypto.aes.AESUtil.generateAESKey();
		byte[] iv = com.andrei.crypto.aes.AESUtil.generateIV();

		byte[] encryptedImage = com.andrei.crypto.aes.AESUtil.encrypt(imageBytes, aesKey, iv);

		com.andrei.crypto.util.FileUtil.writeBytes(outputDir.resolve("imagem_cifrada.aes"), encryptedImage);
		com.andrei.crypto.util.FileUtil.writeBytes(outputDir.resolve("iv.bin"), iv);

		byte[] encryptedAESKey = RSAUtil.encrypt(aesKey.getEncoded(), keyPair.getPublic());
		com.andrei.crypto.util.FileUtil.writeBytes(outputDir.resolve("chave_aes.enc"), encryptedAESKey);

		System.out.println("[3] Arquivos gerados!");

		byte[] decryptedAESKey = RSAUtil.decrypt(encryptedAESKey, keyPair.getPrivate());
		SecretKey aesKeyRecovered = com.andrei.crypto.aes.AESUtil.restoreKey(decryptedAESKey);

		byte[] finalImage = com.andrei.crypto.aes.AESUtil.decrypt(encryptedImage, aesKeyRecovered, iv);
		com.andrei.crypto.util.FileUtil.writeBytes(outputDir.resolve("imagem_final.png"), finalImage);

		System.out.println("[3] Imagem final descriptografada salva!");

		System.out.println("\n=== FINALIZADO ===\nArquivos disponíveis em /output/");
	}
}
