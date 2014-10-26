package chat.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) throws IOException {
        System.out.println("Your nickname: ");
        String nickname = new Scanner(System.in).nextLine();

        String host = "localhost";
        // Запускаем в отдельном потоке отправку сообщений, сервер принимает их на порту 7000:
        Socket send = new Socket(host, 7000);
        new Thread(new SendThread(send, nickname)).start();
        // Запускаем в отдельном потоке прием сообщений, сервер отправляет их на порту 7001:
        Socket receive = new Socket(host, 7001);
        new Thread(new ReceiveThread(receive)).start();
    }
}
