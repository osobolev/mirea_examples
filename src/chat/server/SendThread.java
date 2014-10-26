package chat.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Поток отправки сообщений клиенту
 */
public class SendThread implements Runnable {

    private Socket socket;

    /**
     * Новые сообщения - потокобезопасная блокирующая очередь. К ней идет обращение из потоков:
     * 1. Поток receive вызывает метод Publisher.publish, что в свою очерель вызывает setNewLine
     * 2. Поток send (текущий поток) читает из очереди строки
     */
    private BlockingQueue<String> newLines = new LinkedBlockingQueue<>();

    public SendThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            while (true) {
                // Ждем, пока в очереди новых сообщений что-то появится.
                // Метод take возвращается только если в очереди что-то есть.
                String line = newLines.take();
                // Отправляем сообщение клиенту:
                dos.writeUTF(line);
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            // Закрываем соединение:
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // todo: здесь надо оповестить Publisher, что этот клиент сдох и новые сообщения ему посылать не надо
        }
    }

    public void setNewLine(String line) {
        newLines.add(line);
    }
}
