import java.util.Random;

public class Game {
    Random rng = new Random();
    // Manages game state
    Nim nim;
    // Making the solver an instance variable allows the tree search to be cached
    // across games
    ExpertSolver ai = new ExpertSolver();
    // Whether the game is in play or the program should be quit
    boolean play = true;
    Input input;

    public static void main(String[] args) {
        Game g = new Game();
        // Passing --debug makes the AI print debug information which is helpful
        if (args.length > 1 && args[1].equals("--debug"))
            g.ai = ExpertSolver.withDebug();
        g.input = new Input(System.in, System.out);
        while (g.play)
            g.run();
        g.input.close();
    }

    // Runs one game
    public void run() {
        System.out.println("Welcome to the game of Nim!\n-----\n");
        int pileSize = input.getNumberRangeOrEmpty("Pile size (leave empty to randomize): ", 1, Integer.MAX_VALUE);
        // randomize pile size if not provided (1 <= pileSize <= 100)
        if (pileSize == -1) {
            pileSize = rng.nextInt(100);
            System.out.println("Using a pile with " + pileSize + " items\n");
        }
        nim = new Nim(pileSize);

        boolean useExpertAI = input.getBoolean("Do you want to play against an expert AI?");
        // determine whether AI is player 1 or 2
        int aiPlayer = rng.nextInt(2);

        // play the game
        int player = 0;
        // play rounds until a winner is determined
        while (nim.win() == -1) {
            System.out.println("Player " + (player + 1) + "'s turn");
            int choice = 0;
            if (useExpertAI && player == aiPlayer) {
                choice = ai.choose(nim.getPile());
                System.out.println("AI plays " + choice);
            } else {
                // require a valid choice
                while (!nim.isValidChoice(choice)) {
                    choice = input.getNumber("Choose how many to take (1-" + nim.maxChoose() + "): ");
                }
            }
            nim.play(choice);
            System.out.println(nim.getPile() + " items left");
            // alternates between 0 and 1
            player = (player + 1) % 2;
        }
        // determine which player won
        int winner = nim.win();
        System.out.println("Player " + (winner + 1) + " wins!\n");

        play = input.getBoolean("Would you like to play again?");
    }
}
