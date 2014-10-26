package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Сетевой сервис, увеличивающий присланное число на 1.
 */
public class Server {

    // throws IOException означает, что может возникнуть исключение IOException
    public static void main(String[] args) throws IOException {
        // Слушаем соединения на порту 7000
        ServerSocket ss = new ServerSocket(7000);
        while (true) {
            // Метод accept ждет соединения и возвращается только если соединение есть
            try (Socket s = ss.accept()) {
                // Получаем поток для чтения из соединения:
                InputStream is = s.getInputStream();
                // Оборачиваем его в объект DataInputStream, позволяющий читать данные разных типов, а не только
                // отдельные байты из потока
                DataInputStream dis = new DataInputStream(is);
                // Читаем целое число, присланное клиентом
                int value = dis.readInt();
                System.out.println("Client sent value: " + value);

                // Ответ = запрос + 1
                int answer = value + 1;
                System.out.println("Sending answer: " + answer);

                // Получаем поток для записи данных:
                OutputStream os = s.getOutputStream();
                // Записываем ответ в виде целого числа:
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeInt(answer);
            } // здесь соединение закроется
        }
    }
}
