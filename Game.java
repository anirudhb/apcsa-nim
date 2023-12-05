import java.util.Random;
import java.util.Scanner;

public class Game {
    Random rng = new Random();
    Nim nim;
    // caches results across replays
    ExpertSolver ai = new ExpertSolver();
    boolean play = true;
    Scanner sc;

    public static void main(String[] args) {
        Game g = new Game();
        if (args.length > 1 && args[1].equals("--debug"))
            g.ai = ExpertSolver.withDebug();
        g.sc = new Scanner(System.in);
        while (g.play)
            g.run();
        g.sc.close();
    }

    public void run() {
        System.out.println("Welcome to the game of Nim!\n-----\n");
        System.out.print("Pile size (leave empty to randomize): ");
        int pileSize;
        try {
            pileSize = Integer.parseInt(sc.nextLine().strip());
        } catch (NumberFormatException e) {
            pileSize = rng.nextInt(100);
            System.out.println("Using a pile with " + pileSize + " items\n");
        }
        nim = new Nim(pileSize);

        System.out.println("Do you want to play against an expert AI? (y for yes)");
        boolean useExpertAI = sc.nextLine().strip().toLowerCase().equals("y");
        int aiPlayer = rng.nextInt(2);

        // play the game
        int player = 0;
        while (nim.win() == -1) {
            System.out.println("Player " + (player + 1) + "'s turn");
            int choice = 0;
            if (useExpertAI && player == aiPlayer) {
                choice = ai.choose(nim.getPile());
                System.out.println("AI plays " + choice);
            } else {
                while (!nim.isValidChoice(choice)) {
                    System.out.print("Choose how many to take (1-" + nim.maxChoose() + "): ");
                    choice = Integer.parseInt(sc.nextLine().strip());
                }
            }
            nim.play(choice);
            System.out.println(nim.getPile() + " items left");
            player = (player + 1) % 2;
        }
        int winner = nim.win();
        System.out.println("Player " + (winner + 1) + " wins!");

        System.out.println("\nWould you like to play again? (y for yes)");
        play = sc.nextLine().strip().toLowerCase().equals("y");
    }
}
