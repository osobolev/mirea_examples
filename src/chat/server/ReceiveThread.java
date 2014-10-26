package chat.server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReceiveThread implements Runnable {

    private Socket socket;
    private Publisher publisher;

    public ReceiveThread(Socket socket, Publisher publisher) {
        this.socket = socket;
        this.publisher = publisher;
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            while (true) {
                // Читаем новое сообщение:
                String line = dis.readUTF();
                // Рассылаем всем клиентам:
                publisher.publish(line);
            }
        } catch (EOFException ex) {
            // Клиент закрыл соединение, это не ошибка, а нормальное состояние дел
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
