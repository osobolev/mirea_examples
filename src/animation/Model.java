package animation;

public class Model {

    public static final int SIZE = 40;
    public static final int SPEED = 5;

    /**
     * Координата X центра квадрата относительно центра окна
     */
    private int x = 0;
    /**
     * Скорость перемещения центра квадрата
     */
    private int dx = SPEED;

    /**
     * Обновление координат объектов
     *
     * @param width ширина окна
     */
    public void update(int width) {
        // Продолжаем движение в выбранном направлении:
        x += dx;
        if (x + SIZE / 2 >= width / 2) {
            // Квадрат вышел за правую границу окна: координата правой границы квадрата - (x + SIZE / 2),
            // координата правой гранцы окна (относительно центра окна) - (width / 2)
            // Квадрат изменяет направление движения налево:
            dx = -SPEED;
        } else if (x - SIZE / 2 <= -width / 2) {
            // Квадрат вышел за левую границу окна: координата левой границы квадрата - (x - SIZE / 2),
            // координата левой гранцы окна (относительно центра окна) - (-width / 2)
            // Квадрат изменяет направление движения направо:
            dx = SPEED;
        }
    }

    /**
     * Текущая X-координата квадрата относительно центра окна
     */
    public int getX() {
        return x;
    }
}
