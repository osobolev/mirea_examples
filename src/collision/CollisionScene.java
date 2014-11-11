package collision;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CollisionScene extends JComponent {

    // Физическая модель - все координаты объектов
    private final Model model = new Model();

    /**
     * Конструктор компонента
     */
    public CollisionScene() {
        // Устанавливаем размер компонента - 600x400
        Dimension size = new Dimension(600, 400);
        setPreferredSize(size);

        // Добавляем 20 прямоугольников:
        for (int i = 0; i < 20; i++) {
            model.addObject(size.width, size.height);
        }

        // Таймер будет срабатывать каждые 20 миллисекунд (50 раз в секунду)
        Timer timer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Обновляем координаты объекта
                model.move(size.width, size.height);
                // Перерисовываем картинку
                repaint();
            }
        });
        // Запускаем таймер:
        timer.start();
    }

    /**
     * Этот метод будет вызываться для отрисовки компонента. Параметр g можно использовать для рисования
     * произвольных фигур.
     */
    protected void paintComponent(Graphics g) {
        // Вызов super.paintComponent заполняет весь компонент цветом фона, т.е. просто стирает весь экран
        super.paintComponent(g);

        List<GameObject> objects = model.getObjects();
        // Рисуем прямоугольники:
        g.setColor(Color.blue);
        for (GameObject object : objects) {
            g.fillRect(object.x, object.y, object.width, object.height);
        }
    }

    public static void main(String[] args) {
        // Создаем главное окно приложения с заголовком
        JFrame frame = new JFrame("Collision detection");
        // Создаем компонент...
        CollisionScene scene = new CollisionScene();
        // ...и добавляем его в окно
        frame.add(scene);
        // При закрытии окна выходим из приложения:
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Авто-определение размера окна
        frame.pack();
        // Перемещение окна в центр экрана
        frame.setLocationRelativeTo(null);
        // Показываем окно на экране
        frame.setVisible(true);
    }
}
