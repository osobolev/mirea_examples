package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) throws IOException {
        // Объект, хранящий список текущих подключенных клиентов для рассылки сообщений
        Publisher publisher = new Publisher();

        // Запускаем в отдельном потоке прием соединений на запись сообщений; имя потока - acceptReceive
        ServerSocket receive = new ServerSocket(7000);
        Runnable acceptReceive = new Runnable() {
            public void run() {
                try {
                    while (true) {
                        Socket s = receive.accept();
                        ReceiveThread runnable = new ReceiveThread(s, publisher);
                        // Запускаем поток чтения сообщений от клиента; имя потока - receive
                        new Thread(runnable, "receive").start();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        new Thread(acceptReceive, "acceptReceive").start();

        // Запускаем в отдельном потоке прием соединений на чтение сообщений; имя потока - acceptSend
        ServerSocket send = new ServerSocket(7001);
        Runnable acceptSend = new Runnable() {
            public void run() {
                try {
                    while (true) {
                        Socket s = send.accept();
                        SendThread runnable = new SendThread(s);
                        // Регистрируем еще одног читателя сообщений
                        publisher.addClient(runnable);
                        // Запускаем поток отдачи новых сообщений клиенту; имя потока - send
                        new Thread(runnable, "send").start();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        new Thread(acceptSend, "acceptSend").start();
    }
}
