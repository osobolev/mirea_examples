package chat.server;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Publisher {

    // Потокобезопасная коллекция, к ней идет обращение из:
    // 1. Потока acceptSend - вызов метода addClient
    // 2. Потока receive - вызов метода publish
    private Collection<SendThread> activeClients = new ConcurrentLinkedQueue<>();

    public void addClient(SendThread client) {
        activeClients.add(client);
    }

    public void publish(String line) {
        for (SendThread client : activeClients) {
            client.setNewLine(line);
        }
    }
}
