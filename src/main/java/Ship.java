import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ship {
    private int size;

    private int health;

    private List<HashMap<Integer,Integer>> positions = new ArrayList<>();

    public Ship(int size){
        this.size = size;
        health = size;
    }

    public void addPosition(int i,int j){
        HashMap<Integer, Integer> position = new HashMap<>();
        position.put(i,j);
        this.positions.add(position);
    }

    public void takeDamage(){
        health--;
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

    public List<HashMap<Integer, Integer>> getPositions() {
        return positions;
    }

    public void setPositions(List<HashMap<Integer, Integer>> positions) {
        this.positions = positions;
    }
}
