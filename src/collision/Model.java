package collision;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Физическая модель сцены
 */
public class Model {

    public static final int OBJ_WIDTH = 20;
    public static final int OBJ_HEIGHT = 20;

    /**
     * Список объектов
     */
    private List<GameObject> objects = new ArrayList<GameObject>();
    private Random rnd = new Random();

    /**
     * Добавление нового объекта
     * @param width ширина экрана
     * @param height высота экрана
     */
    public void addObject(int width, int height) {
        while (true) {
            int x = rnd.nextInt(width - OBJ_WIDTH);
            int y = rnd.nextInt(height - OBJ_HEIGHT);
            int vx = rnd.nextInt(11) - 5;
            int vy = rnd.nextInt(11) - 5;
            GameObject newObject = new GameObject(x, y, OBJ_WIDTH, OBJ_HEIGHT, vx, vy);
            boolean intersects = false;
            for (GameObject object : objects) {
                if (Collision.intersects(newObject, object)) {
                    intersects = true;
                    break;
                }
            }
            if (!intersects) {
                objects.add(newObject);
                break;
            }
        }
    }

    /**
     * Шаг моделирования - изменение координат объектов
     * @param width ширина экрана
     * @param height высота экрана
     */
    public void move(int width, int height) {
        for (GameObject object : objects) {
            // Координаты, которые объект должен занять с учетом скорости движения:
            int x1 = object.x + object.vx;
            int y1 = object.y + object.vy;
            boolean horizontalCollision = false;
            boolean verticalCollision = false;
            for (GameObject object2 : objects) {
                if (object2 == object)
                    continue;
                // Проверяем, что не налетел на другой объект:
                Collision collision = Collision.collide(object, x1, y1, object2);
                if (collision.horizontal) {
                    horizontalCollision = true;
                }
                if (collision.vertical) {
                    verticalCollision = true;
                }
                x1 = collision.x;
                y1 = collision.y;
            }
            // Проверяем, что не вылетел за границы экрана:
            if (x1 < 0) {
                x1 = 0;
                horizontalCollision = true;
            } else if (x1 > width - object.width) {
                x1 = width - object.width;
                horizontalCollision = true;
            }
            if (y1 < 0) {
                y1 = 0;
                verticalCollision = true;
            } else if (y1 > height - object.height) {
                y1 = height - object.height;
                verticalCollision = true;
            }
            object.x = x1;
            object.y = y1;
            // При столкновении меняем направление движения:
            if (horizontalCollision) {
                object.vx = -object.vx;
            }
            if (verticalCollision) {
                object.vy = -object.vy;
            }
        }
    }

    public List<GameObject> getObjects() {
        return objects;
    }
}
