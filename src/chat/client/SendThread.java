package chat.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SendThread implements Runnable {

    private Socket socket;
    private String nickname;

    public SendThread(Socket socket, String nickname) {
        this.socket = socket;
        this.nickname = nickname;
    }

    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            while (true) {
                // Читаем введенное с экрана сообщение:
                String line = scanner.nextLine();
                // Отправляем на сервер:
                dos.writeUTF(nickname + ": " + line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Закрываем соединение:
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
