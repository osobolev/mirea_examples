package animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Графический компонент с анимацией
 */
public class Scene extends JComponent {

    // Физическая модель - все координаты объектов
    private final Model model = new Model();

    /**
     * Конструктор компонента
     */
    public Scene() {
        // Устанавливаем начальный размер компонента - 600x400
        setPreferredSize(new Dimension(600, 400));

        // Таймер будет срабатывать каждые 20 миллисекунд (50 раз в секунду)
        Timer timer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Обновляем координаты объекта
                model.update(getWidth());
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

        // Определяем текущие размеры компонента (могут измениться при изменении размера окна)
        int width = getWidth();
        int height = getHeight();
        // Определяем координаты центра компонента, чтобы далее рисовать все относительно центра
        int centerX = width / 2;
        int centerY = height / 2;

        // Текущая X-координата квадрата относительно центра окна:
        int x = model.getX();
        // Координата X левого верхнего угла квадрата относительно начала координат:
        int x1 = centerX + x - Model.SIZE / 2;
        // Координата Y левого верхнего угла квадрата относительно начала координат:
        int y1 = centerY - Model.SIZE / 2;
        g.drawRect(x1, y1, Model.SIZE, Model.SIZE);
    }

    public static void main(String[] args) {
        // Создаем главное окно приложения с заголовком
        JFrame frame = new JFrame("Simple animation component");
        // Создаем компонент...
        Scene scene = new Scene();
        // ...и добавляем его в окно
        frame.add(scene);
        // Авто-определение размера окна
        frame.pack();
        // Перемещение окна в центр экрана
        frame.setLocationRelativeTo(null);
        // Показываем окно на экране
        frame.setVisible(true);
    }
}
