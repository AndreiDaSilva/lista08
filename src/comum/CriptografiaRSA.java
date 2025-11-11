package comum;

import module.Application;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

/**
 * Implementação híbrida RSA + AES
 * - RSA protege a chave AES
 * - AES protege a mensagem
 *
 * Corrigida: separa as partes por linhas, sem ambiguidade.
 */
public class CriptografiaRSA {

	private final comum.GerandoChaveRSA geradorDeChave;
	private final FileService fileService;

	public CriptografiaRSA() {
		this.geradorDeChave = Application.getSingleton(comum.GerandoChaveRSA.class);
		this.fileService = Application.getSingleton(FileService.class);
	}

	/**
	 * Criptografa uma mensagem (modo híbrido RSA + AES).
	 */
	public void gerarTextoCriptografado(String msg) throws Exception {
		// 1️⃣ Garante que as chaves existam
		File publicKeyFile = new File("src/keys/PublicKey.txt");
		if (!publicKeyFile.exists()) {
			geradorDeChave.gerarChave();
		}

		// 2️⃣ Lê a chave pública RSA
		String publicKeyBase64 = geradorDeChave.lerArquivos("PublicKey.txt");
		PublicKey publicKey = loadKeyPublic(publicKeyBase64);

		// 3️⃣ Gera chave AES aleatória
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		SecretKey aesKey = keyGen.generateKey();

		// 4️⃣ Criptografa a mensagem com AES
		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
		byte[] mensagemCriptografada = aesCipher.doFinal(msg.getBytes());

		// 5️⃣ Criptografa a chave AES com RSA pública
		Cipher rsaCipher = Cipher.getInstance("RSA");
		rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] chaveAesCriptografada = rsaCipher.doFinal(aesKey.getEncoded());

		// 6️⃣ Concatena as duas partes em DUAS LINHAS
		String conteudoFinal = Base64.getEncoder().encodeToString(chaveAesCriptografada)
				+ "\n"
				+ Base64.getEncoder().encodeToString(mensagemCriptografada);

		fileService.saveCriptonMsg("mensagem_criptografada", conteudoFinal);

		System.out.println("✅ Mensagem criptografada e salva em: src/keys/mensagem_criptografada.txt");
	}

	/**
	 * Descriptografa uma mensagem híbrida (RSA + AES)
	 */
	public void decriptografarTexto(String fileName) throws Exception {
		// 1️⃣ Lê a chave privada RSA
		String privateKeyBase64 = geradorDeChave.lerArquivos("PrivateKey.txt");
		PrivateKey privateKey = loadKeyPrivate(privateKeyBase64);

		// 2️⃣ Lê o arquivo com 2 linhas (chave AES + mensagem AES)
		String conteudo = geradorDeChave.lerArquivos(fileName).trim();
		String[] partes = conteudo.split("\\R+"); // divide por quebras de linha

		if (partes.length < 2) {
			throw new IllegalArgumentException("❌ Arquivo inválido: esperado 2 linhas (chave AES e mensagem).");
		}

		String aesCifradaBase64 = partes[0].trim();
		String msgCifradaBase64 = partes[1].trim();

		// 3️⃣ Descriptografa a chave AES com RSA privada
		Cipher rsaCipher = Cipher.getInstance("RSA");
		rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] aesKeyBytes = rsaCipher.doFinal(Base64.getDecoder().decode(aesCifradaBase64));
		SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");

		// 4️⃣ Descriptografa a mensagem com AES
		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
		byte[] msgDecifrada = aesCipher.doFinal(Base64.getDecoder().decode(msgCifradaBase64));

		String mensagemFinal = new String(msgDecifrada);

		// 5️⃣ Salva e mostra a mensagem decifrada
		fileService.saveCriptonMsg("mensagem_decifrada", mensagemFinal);
		System.out.println("🔓 Mensagem decifrada: " + mensagemFinal);
		System.out.println("📁 Arquivo salvo em: src/keys/mensagem_decifrada.txt");
	}

	// --------------------------------------------------------------------
	// Métodos auxiliares de carregamento de chaves RSA
	// --------------------------------------------------------------------
	private static PublicKey loadKeyPublic(String base64Key) throws Exception {
		byte[] decoded = Base64.getDecoder().decode(base64Key);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
		return KeyFactory.getInstance("RSA").generatePublic(spec);
	}

	private static PrivateKey loadKeyPrivate(String base64Key) throws Exception {
		byte[] decoded = Base64.getDecoder().decode(base64Key);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
		return KeyFactory.getInstance("RSA").generatePrivate(spec);
	}
}
