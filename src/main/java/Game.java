import java.util.*;

public class Game {

    private Player firstPlayer;
    private Player secondPlayer;

    public Game(Player firstPlayer, Player secondPlayer ){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        firstPlayer.setField(setAutoDeck(firstPlayer));
        secondPlayer.setField(setAutoDeck(secondPlayer));
        while(true){
            System.out.println("Field of " +  firstPlayer.getName() + " player: ");
            printField(firstPlayer);
            System.out.println("Field of " + secondPlayer.getName() + " player: ");
            printField(secondPlayer);
            System.out.println("Shot coordinates to second player: ");

            if(shoot(secondPlayer,scanner)){
                break;
            }
            System.out.println("Shot coordinates to first player: ");
            if(shoot(firstPlayer,scanner)){
                break;
            }

        }
    }

    private boolean shoot(Player player, Scanner scanner){
        int iFirst = scanner.nextInt();
        int jFirst = scanner.nextInt();
        return attack(player, iFirst, jFirst) != null;
    }

    private void printField(Player player){
        for(int i = 0; i < 10; i++ ){
            for(int j =0; j < 10; j ++){
                System.out.print(player.getField()[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void autoPositionShips(int[][] field, int numberOfDecks,Player player){
        Random random = new Random();
        while (true) {
            int i = random.nextInt(10);
            int j = random.nextInt(10);
            int direction = random.nextInt(4);

            if (testNewDeck(field, i, j)) {
                if (direction == 0) { //вверх
                    if (testNewDeck(field, i - (numberOfDecks - 1), j)){

                        field[i][j] = numberOfDecks;
                        setPositionToShip(i,j,numberOfDecks, player);
                        okrBegin(field, i, j, -2);

                        for (int k = numberOfDecks - 1; k >= 1; k--) {
                            field[i - k][j] = numberOfDecks;
                            setPositionToShip(i - k,j,numberOfDecks, player);
                            okrBegin(field, i - k, j, -2);
                        }
                        break;
                    }
                }
                else if (direction == 1){ // вправо
                    if (testNewDeck(field, i, j + (numberOfDecks - 1))){

                        field[i][j] = numberOfDecks;
                        setPositionToShip(i,j,numberOfDecks, player);
                        okrBegin(field, i, j, -2);

                        for (int k = numberOfDecks - 1; k >= 1; k--) {
                            field[i][j + k] = numberOfDecks;
                            setPositionToShip(i,j + k,numberOfDecks, player);
                            okrBegin(field, i, j + k, -2);
                        }
                        break;
                    }
                }
                else if (direction == 2){ // вниз
                    if (testNewDeck(field, i + (numberOfDecks - 1), j)){

                        field[i][j] = numberOfDecks;
                        setPositionToShip(i,j,numberOfDecks, player);
                        okrBegin(field, i, j, -2);

                        for (int k = numberOfDecks - 1; k >= 1; k--) {
                            field[i + k][j] = numberOfDecks;
                            setPositionToShip(i + k,j,numberOfDecks, player);
                            okrBegin(field, i + k, j, -2);
                        }
                        break;
                    }
                }
                else { // влево
                    if (testNewDeck(field, i, j - (numberOfDecks - 1))){

                        field[i][j] = numberOfDecks;
                        setPositionToShip(i,j,numberOfDecks, player);
                        okrBegin(field, i, j, -2);

                        for (int k = numberOfDecks - 1; k >= 1; k--) {
                            field[i][j -k] = numberOfDecks;
                            setPositionToShip(i,j - k,numberOfDecks, player);
                            okrBegin(field, i, j - k, -2);
                        }
                        break;
                    }
                }
            }
        }
        okrEnd(field);
    }

    private boolean testNewDeck(int [][]mas, int i, int j){
        if (!isNotOutOfRange(i, j)) return false;
        return (mas[i][j] == 0) || (mas[i][j] == -2);
    }

    private void okrEnd(int[][] mas) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mas[i][j] == -2)
                    mas[i][j] = -1;
            }
        }
    }

    private void okrBegin(int[][] field, int i, int j, int val) {
        setEnv(field, i - 1, j - 1, val);
        setEnv(field, i - 1, j, val);
        setEnv(field, i - 1, j + 1, val);
        setEnv(field, i, j + 1, val);
        setEnv(field, i, j - 1, val);
        setEnv(field, i + 1, j + 1, val);
        setEnv(field, i + 1, j, val);
        setEnv(field, i + 1, j - 1, val);
    }

    private void setEnv(int[][] field, int i, int j, int val) {
        if (isNotOutOfRange(i, j) && field[i][j] == 0) {
            field[i][j] = val;
        }
    }

    private boolean isNotOutOfRange(int i, int j) {
        return ((i >= 0) && (i <= 9)) && ((j >= 0) && (j <= 9));
    }

    private Player attack(Player player, int i, int j){// Передаем игрока по которому ведется огонь
        if (!isNotOutOfRange(i,j)) return null;

        if(player.getField()[i][j] > 0){
            int numberOfDecks = player.getField()[i][j];
            int indexOfShip = giveDamageToShip(i,j,numberOfDecks,player.getShips());
            player.getField()[i][j] = -3;
            if (player.getShips().get(indexOfShip).getHealth() == 0){
                setEnvForDeadShip(player.getShips().get(indexOfShip).getPositions(),player.getField(),numberOfDecks);
                Player winner = checkEndGame();
                if(winner != null){
                    System.out.println(winner.getName() + " win!!!");
                    return winner;
                }
            }

        } else player.getField()[i][j] = -2;
        return null;
    }

    private int[][] setAutoDeck(Player player){
        int[][] field = new int[10][10];
        autoPositionShips(field, 4, player);
        for (int i = 1; i <= 2; i++) {
            autoPositionShips(field, 3, player);
        }
        for (int i = 1; i <= 3; i++) {
            autoPositionShips(field, 2, player);
        }
        for (int i = 1;i<= 4;i++){
            autoPositionShips(field,1, player);
        }
        return field;
    }

    private void setEnvForDeadShip(List<HashMap<Integer, Integer>> positions, int[][] field, int numberOfDecks){

        for (int i =0; i < numberOfDecks; i++){
            for (Map.Entry entry: positions.get(i).entrySet() ){
                int key = (int) entry.getKey();
                int value = (int) entry.getValue();
                setDeadValue(field,key - 1,value - 1);
                setDeadValue(field,key - 1,value);
                setDeadValue(field,key - 1,value + 1);
                setDeadValue(field, key,value + 1);
                setDeadValue(field,key + 1,value + 1);
                setDeadValue(field,key + 1,value);
                setDeadValue(field,key + 1,value - 1);
                setDeadValue(field,key ,value - 1);
            }
        }
    }

    private void setDeadValue(int[][] field, int i, int j){
        if (isNotOutOfRange(i,j)){
            if(field[i][j] != -3){
                field[i][j] = -2;
            }
        }
    }

    private Player checkEndGame(){
        if(firstPlayer.getNumberOfNotBrokenShips() == 10){
            return firstPlayer;
        } else if (secondPlayer.getNumberOfNotBrokenShips() == 10) {
            return secondPlayer;
        }
        return null;
    }

    private int giveDamageToShip(int i, int j, int numberOfDecks, List<Ship> ships){

        switch (numberOfDecks){
            case 4 ->{
                ships.get(9).takeDamage();
                return 9;
            }

            case 3 ->{
                for(int k = 0; k < 6; k++){
                    if (k < 3){
                        if(isValueExistsInHashMap(ships, k, i, 8) && ships.get(8).getPositions().get(k).get(i) == j){
                            ships.get(8).takeDamage();
                            return 8;
                        }
                    } else {
                        if (isValueExistsInHashMap(ships,k -3,i,7) && ships.get(7).getPositions().get(k-3).get(i) == j){
                            ships.get(7).takeDamage();
                            return 7;
                        }
                    }
                }
            }

            case 2 -> {
                for(int k = 0; k < 6; k++){
                    if (k < 2){
                        if(isValueExistsInHashMap(ships, k, i,6)
                                &&ships.get(6).getPositions().get(k).get(i) == j){
                            ships.get(6).takeDamage();
                            return 6;
                        }
                    } else if( k < 4) {
                        if ( isValueExistsInHashMap(ships,k - 2, i,5)
                                &&ships.get(5).getPositions().get(k-2).get(i) == j){
                            ships.get(5).takeDamage();
                            return 5;
                        }
                    } else {
                        if (isValueExistsInHashMap(ships,k - 4, i,4)
                                &&ships.get(4).getPositions().get(k-4).get(i) == j){
                            ships.get(4).takeDamage();
                            return 4;
                        }
                    }
                }
            }

            case 1 ->{
                for(int k = 0; k < 4; k++){
                    switch (k){
                        case 0 ->{
                            if ( isValueExistsInHashMap(ships,0,i,3)
                                    &&ships.get(3).getPositions().get(0).get(i) == j){
                                ships.get(3).takeDamage();
                                return 3;
                            }
                        }
                        case 1 ->{
                            if (isValueExistsInHashMap(ships,0,i,2)
                                    &&ships.get(2).getPositions().get(0).get(i) == j){
                                ships.get(2).takeDamage();
                                return 2;
                            }
                        }
                        case 2 ->{
                            if ( isValueExistsInHashMap(ships,0,i,1)
                                    &&ships.get(1).getPositions().get(0).get(i) == j){
                                ships.get(1).takeDamage();
                                return 1;
                            }
                        }
                        case 3 ->{
                            if (isValueExistsInHashMap(ships,0,i,0) &&
                                    ships.get(0).getPositions().get(0).get(i) == j){
                                ships.get(0).takeDamage();
                                return 0;
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    public boolean isValueExistsInHashMap(List<Ship> ships,int index, int key, int numberOfShip){
        return ships.get(numberOfShip).getPositions().get(index).get(key) != null;
    }

    private void setPositionToShip(int i, int j, int numberOfDecks, Player player){
        switch (numberOfDecks){
            case 4 ->player.getShips().get(9).addPosition(i,j);

            case 3 ->{
                if(player.getShips().get(8).getPositions().size() < 3 ){
                    player.getShips().get(8).addPosition(i,j);
                } else {
                    player.getShips().get(7).addPosition(i,j);
                }
            }

            case 2 -> {
                if(player.getShips().get(6).getPositions().size() < 2){
                    player.getShips().get(6).addPosition(i,j);
                } else if (player.getShips().get(5).getPositions().size() <2) {
                    player.getShips().get(5).addPosition(i,j);
                } else {
                    player.getShips().get(4).addPosition(i,j);
                }
            }

            case 1 ->{
                if (player.getShips().get(3).getPositions().size() != 1){
                    player.getShips().get(3).addPosition(i,j);
                } else if (player.getShips().get(2).getPositions().size() != 1) {
                    player.getShips().get(2).addPosition(i,j);
                } else if (player.getShips().get(1).getPositions().size() != 1) {
                    player.getShips().get(1).addPosition(i,j);
                } else player.getShips().get(0).addPosition(i,j);
            }
        }
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }
}
