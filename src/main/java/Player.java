import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;

    private List<Ship> ships;

    public Player(String name){
        this.name = name;
        List<Ship> ships = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            if( i <= 3){
                ships.add(new Ship(1));
            } else if (i <=6) {
                ships.add(new Ship(2));
            } else if( i <=8){
                ships.add(new Ship(3));
            } else {
                ships.add(new Ship(4));
            }
        }
        this.ships = ships;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Ship> getShips() {
        return ships;
    }
}
