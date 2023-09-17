import java.util.Scanner;

public class SeaBattle {

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name of first player: ");
        Player firstPlayer = new Player(scanner.nextLine());
        System.out.println();
        System.out.print("Enter name of second player: ");
        Player secondPlayer = new Player(scanner.nextLine());
        Game game = new Game(firstPlayer,secondPlayer);
        game.start();

    }

}
