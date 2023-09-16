import java.util.Random;

public class Game {

    private Player firstPlayer;
    private Player secondPlayer;

    private int[][] firstField = new int[10][10];

    private int[][] secondField = new int[10][10];

    public Game(Player firstPlayer, Player secondPlayer ){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    private void autoPositionShips(int[][] field, int numberOfDecks){
        Random random = new Random();
        while (true) {
            int i = random.nextInt(10);
            int j = random.nextInt(10);
            int direction = random.nextInt(4);

            if (testNewDeck(field, i, j)) {
                if (direction == 0) { //вверх
                    if (testNewDeck(field, i - (numberOfDecks - 1), j)){

                        field[i][j] = numberOfDecks;
                        okrBegin(field, i, j, -2);
                        for (int k = numberOfDecks - 1; k >= 1; k--) {
                            field[i -k][j] = numberOfDecks;
                            okrBegin(field, i - k, j, -2);
                        }
                    }
                }
                else if (direction == 1){ // вправо
                    if (testNewDeck(field, i, j + (numberOfDecks - 1))){

                        field[i][j] = numberOfDecks;
                        okrBegin(field, i, j, -2);
                        for (int k = numberOfDecks - 1; k >= 1; k--) {
                            field[i][j + k] = numberOfDecks;
                            okrBegin(field, i, j + k, -2);
                        }
                    }
                }
                else if (direction == 2){ // вниз
                    if (testNewDeck(field, i + (numberOfDecks - 1), j)){

                        field[i][j] = numberOfDecks;
                        okrBegin(field, i, j, -2);
                        for (int k = numberOfDecks - 1; k >= 1; k--) {
                            field[i + k][j] = numberOfDecks;
                            okrBegin(field, i + k, j, -2);
                        }
                    }
                }
                else { // влево
                    if (testNewDeck(field, i, j - (numberOfDecks - 1))){
                        field[i][j] = numberOfDecks;
                        okrBegin(field, i, j, -2);
                        for (int k = numberOfDecks - 1; k >= 1; k--) {
                            field[i][j -k] = numberOfDecks;
                            okrBegin(field, i, j - k, -2);
                        }
                    }
                }
                break;
            }
        }
        okrEnd(field);
    }

    private boolean testNewDeck(int [][]mas, int i, int j){
        if (!isOutOfRange(i, j)) return false;
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

    private void okrBegin(int[][] mas, int i, int j, int val) {
        setEnv(mas, i - 1, j - 1, val);
        setEnv(mas, i - 1, j, val);
        setEnv(mas, i - 1, j + 1, val);
        setEnv(mas, i, j + 1, val);
        setEnv(mas, i, j - 1, val);
        setEnv(mas, i + 1, j + 1, val);
        setEnv(mas, i + 1, j, val);
        setEnv(mas, i + 1, j - 1, val);
    }

    private void setEnv(int[][] mas, int i, int j, int val) {
        if (isOutOfRange(i, j) && mas[i][j] == 0) {
            mas[i][j] = val;
        }
    }

    private boolean isOutOfRange(int i, int j) {
        if (((i >= 0) && (i <= 9)) && ((j >= 0) && (j <= 9))) {
            return true;
        } else return false;
    }

    public int[][] setAutoDeck(){
        int[][] field = new int[10][10];
        autoPositionShips(field, 4);
        for (int i = 1; i <= 2; i++) {
            autoPositionShips(field, 3);
        }
        for (int i = 1; i <= 3; i++) {
            autoPositionShips(field, 2);
        }
        for (int i = 1;i<= 4;i++){
            autoPositionShips(field,1);
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

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public int[][] getFirstField() {
        return firstField;
    }

    public void setFirstField(int[][] firstField) {
        this.firstField = firstField;
    }

    public int[][] getSecondField() {
        return secondField;
    }

    public void setSecondField(int[][] secondField) {
        this.secondField = secondField;
    }
}
