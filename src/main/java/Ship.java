public class Ship {

    private int size;

    private int health;

    public Ship(int size){
        this.size = size;
        health = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
