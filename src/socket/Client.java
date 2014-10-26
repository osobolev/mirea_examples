package socket;

import java.io.*;
import java.net.Socket;

/**
 * Обращение к сетевому сервису, реализованному в классе Server.
 */
public class Client {

    // throws IOException означает, что может возникнуть исключение IOException
    public static void main(String[] args) throws IOException {
        int number = 41;
        // Открываем соединение с сервером по адресу localhost на порту 7000.
        // Программа будет работать и в том случае, если сервер находится на другом компьютере,
        // тогда нужно указать его адрес.
        try (Socket s = new Socket("localhost", 7000)) {
            // Получаем поток для записи данных:
            OutputStream os = s.getOutputStream();
            // Оборачиваем его в объект DataOutputStream, позволяющий писать данные разных типов, а не только
            // отдельные байты в поток
            DataOutputStream dos = new DataOutputStream(os);
            System.out.println("Sending number to server: " + number);
            // Записываем запрос в поток:
            dos.writeInt(number);

            // Получаем поток для чтения из соединения:
            InputStream is = s.getInputStream();
            // Читаем полученный ответ:
            DataInputStream dis = new DataInputStream(is);
            int answer = dis.readInt();
            System.out.println("Got answer: " + answer);
        }
    }
}
