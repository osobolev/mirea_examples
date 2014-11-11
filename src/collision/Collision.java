package collision;

/**
 * Определение столкновений для прямоугольных объектов
 */
public class Collision {

    /**
     * Координата X точки, в которой объект останавливается при столкновении
     */
    public final int x;
    /**
     * Координата Y точки, в которой объект останавливается при столкновении
     */
    public final int y;
    /**
     * true, если прямоугольники столкнулись по вертикали (нижняя сторона одного с верхней другого)
     */
    public final boolean horizontal;
    /**
     * true, если прямоугольники столкнулись по горизонтали (правая сторона одного с левой другого)
     */
    public final boolean vertical;

    public Collision(int x, int y, boolean horizontal, boolean vertical) {
        this.x = x;
        this.y = y;
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    /**
     * Определение пересечения одномерных отрезков.
     *
     * @param x1 координата начала первого отрезка
     * @param size1 длина первого отрезка
     * @param x2 координата начала второго отрезка
     * @param size2 длина второго отрезка
     * @return true, если отрезки пересекаются.
     */
    public static boolean intersects(int x1, int size1, int x2, int size2) {
        return collides(x1, size1, x1, x2, size2);
    }

    private static boolean collides(int x1, int size1, int newx1,
                                    int x2, int size2) {
        if (x1 < x2) {
            return newx1 + size1 > x2;
        } else {
            return x2 + size2 > newx1;
        }
    }

    private static boolean collides(GameObject c1, int newx1, int newy1,
                                    GameObject c2) {
        return collides(c1.x, c1.width, newx1, c2.x, c2.width) && collides(c1.y, c1.height, newy1, c2.y, c2.height);
    }

    private static int collide(int x1, int size1,
                               int x2, int size2) {
        if (x1 < x2) {
            return x2 - size1;
        } else {
            return x2 + size2;
        }
    }

    /**
     * Определение столкновения прямоугольников.
     *
     * @param c1 движущийся объект
     * @param newx1 координата X, которую хочет занять объект c1
     * @param newy1 координата Y, которую хочет занять объект c1
     * @param c2 другой объект
     * @return если столкновения нет, то возвращается объект
     * <code>new Collision(newx1, newy1, false, false)</code>; если столкновение есть, то одно из полей
     * {@link #horizontal} или {@link #vertical} будет true, а в полях
     * {@link #x} и {@link #y} будут координаты места, в котором c1 сталкивается с c2.
     */
    public static Collision collide(GameObject c1, int newx1, int newy1,
                                    GameObject c2) {
        if (collides(c1, newx1, newy1, c2)) {
            double dx = newx1 - c1.x;
            double dy = newy1 - c1.y;
            int cx = collide(c1.x, c1.width, c2.x, c2.width);
            int cy = collide(c1.y, c1.height, c2.y, c2.height);
            double realDx = cx - c1.x;
            double realDy = cy - c1.y;
            if (Math.abs(realDx * dy) > Math.abs(realDy * dx)) {
                // dy меньше
                int nx = (int) (c1.x + dx * (realDy / dy));
                return new Collision(nx, cy, false, true);
            } else {
                int ny = (int) (c1.y + dy * (realDx / dx));
                return new Collision(cx, ny, true, false);
            }
        } else {
            return new Collision(newx1, newy1, false, false);
        }
    }

    /**
     * Определение пересечения прямоугольников.
     *
     * @param c1 первый прямоугольник
     * @param c2 второй прямоугольник
     * @return true, если прямоугольники пересекаются
     */
    public static boolean intersects(GameObject c1, GameObject c2) {
        return collides(c1, c1.x, c1.y, c2);
    }

    /**
     * Определяет, что один прямоугольник стоит на втором.
     *
     * @param c1 первый прямоугольник
     * @param c2 второй прямоугольник
     * @return true, если прямоугольник c1 стоит на прямоугольнике c2.
     */
    public static boolean isStandingOn(GameObject c1, GameObject c2) {
        return c1.y == c2.y + c2.height && intersects(c1.x, c1.width, c2.x, c2.width);
    }
}
