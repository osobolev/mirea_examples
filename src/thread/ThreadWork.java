package thread;

public class ThreadWork implements Runnable {

    /**
     * Код, который будет выполняться в отдельном потоке
     */
    public void run() {
        // Просто печатаем числа от 0 до 9 с задержкой в 500 миллисекунд (полсекунды).
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread: " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                // Это исключение возникает при прерывании потока; в нашем случае поток
                // не прерывается, поэтому его можно игнорировать.
            }
        }
    }
}
