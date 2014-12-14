package animation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Графический компонент с анимацией
 */
public class Scene extends JComponent {

    // Физическая модель - все координаты объектов
    private final Model model = new Model();

    private BufferedImage image;
    private BufferedImage rotated;
    private double angle = 0;

    /**
     * Конструктор компонента
     */
    public Scene() throws IOException {
        // Устанавливаем начальный размер компонента - 600x400
        setPreferredSize(new Dimension(600, 400));

        // Загружаем картинку
        BufferedImage tmp = ImageIO.read(getClass().getResource("barrel.png"));
        GraphicsConfiguration cfg = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        // Создаем ее копию для оптимального отображения (формат внутреннего представления должен совпадать
        // с форматом, заданным конфигурацией графического устройства):
        image = cfg.createCompatibleImage(tmp.getWidth(), tmp.getHeight(), tmp.getTransparency());
        image.getGraphics().drawImage(tmp, 0, 0, null);
        // Создаем пустую картинку для рисования изображения с поворотом:
        rotated = cfg.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());

        // Таймер будет срабатывать каждые 20 миллисекунд (50 раз в секунду)
        Timer timer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Обновляем координаты объекта
                model.update(getWidth());
                angle += 5.0 / 180.0 * Math.PI;
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

        // Очищаем картинку rotated - там остался результат предыдущего рисования:
        Graphics2D tg = (Graphics2D) rotated.getGraphics();
        tg.setComposite(AlphaComposite.Clear);
        tg.fillRect(0, 0, rotated.getWidth(), rotated.getHeight());
        tg.dispose();
        // Помещаем в rotated картинку, созданную из image поворотом на угол angle вокруг точки - центра картинки:
        AffineTransform rotate = AffineTransform.getRotateInstance(angle, image.getWidth() / 2, image.getHeight() / 2);
        new AffineTransformOp(rotate, AffineTransformOp.TYPE_BILINEAR).filter(image, rotated);
        // Рисуем повернутую картинку:
        g.drawImage(rotated, width / 2 - image.getWidth() / 2, 20, null);
    }

    public static void main(String[] args) throws IOException {
        // Создаем главное окно приложения с заголовком
        JFrame frame = new JFrame("Simple animation component");
        // Создаем компонент...
        Scene scene = new Scene();
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
