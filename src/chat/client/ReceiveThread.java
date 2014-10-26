package chat.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReceiveThread implements Runnable {

    private Socket socket;

    public ReceiveThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            while (true) {
                // Читаем новое сообщение с сервера:
                String line = dis.readUTF();
                // Отображаем на экране:
                System.out.println(line);
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
