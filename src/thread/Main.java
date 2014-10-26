package thread;

public class Main {

    // Здесь InterruptedException может возникнуть при прерывании основного потока.
    // В нашем случае поток не прерывается, и реально исключение не должно возникнуть,
    // поэтому мы его никак не обрабатываем.
    public static void main(String[] args) throws InterruptedException {
        // Создаем объект, реализующий интерфейс Runnable. Этот интерфейс определяет метод run, который
        // определяет код, который будет выполняться в потоке.
        ThreadWork work = new ThreadWork();
        // Создаем поток и передаем ему код, который должен выполняться в этом потоке
        Thread thread = new Thread(work);
        // Запускаем поток:
        thread.start();

        // Ждем секунду в основном потоке. В это время созданный поток работает параллельно и выводит числа.
        Thread.sleep(1000);
        // Печатаем также в основном потоке.
        System.out.println("Main program");
        // Вызов join ждет завершения запущенного потока:
        thread.join();
        System.out.println("Done!");
    }
}
