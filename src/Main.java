import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);

		System.out.println("Digite a opção: ");
		System.out.println("1. Criptografar ");
		System.out.println("2. Decriptografar ");

		Character opc =  sc.next().charAt(0);
		String msg = "";

		switch (opc) {
			case '1':
				System.out.printf("Digite sua mensagem: ");
				msg = new String(sc.nextLine());
				new CriptografiaRSA(msg).gerarTextoCriptografado();
				break;
			case  '2':
				System.out.printf("Cole a mensagem: ");
				msg = new String(sc.nextLine());
			default:
				break;
		}
	}
}