import comum.CriptografiaRSA;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);

		System.out.println("====================================");
		System.out.println("🔐 Sistema de Criptografia RSA");
		System.out.println("====================================");
		System.out.println("[1] Criptografar mensagem");
		System.out.println("[2] Descriptografar mensagem");
		System.out.print("→ Escolha uma opção: ");

		char opc = sc.next().charAt(0);
		sc.nextLine(); // limpa o buffer

		switch (opc) {
			case '1':
				System.out.print("Digite sua mensagem: ");
				String msg = sc.nextLine();

				System.out.println("🔒 Criptografando...");
				new CriptografiaRSA().gerarTextoCriptografado(msg);
				System.out.println("✅ Mensagem criptografada com sucesso!");
				break;

			case '2':
				System.out.println("🔓 Descriptografando mensagem...");
				new CriptografiaRSA().decriptografarTexto("mensagem_criptografada.txt");
				break;

			default:
				System.out.println("⚠️ Opção inválida. Escolha 1 ou 2.");
				break;
		}

		sc.close();
	}
}
