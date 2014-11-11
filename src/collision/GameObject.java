package collision;

public class GameObject {

    public int x;
    public int y;
    public int width;
    public int height;
    public int vx;
    public int vy;

    public GameObject(int x, int y, int width, int height, int vx, int vy) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vx = vx;
        this.vy = vy;
    }
}
