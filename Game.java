import java.util.Random;

public class Game {
    Random rng = new Random();
    Nim nim;
    // caches results across replays
    ExpertSolver ai = new ExpertSolver();
    boolean play = true;
    Input input;

    public static void main(String[] args) {
        Game g = new Game();
        if (args.length > 1 && args[1].equals("--debug"))
            g.ai = ExpertSolver.withDebug();
        g.input = new Input(System.in, System.out);
        while (g.play)
            g.run();
        g.input.close();
    }

    public void run() {
        System.out.println("Welcome to the game of Nim!\n-----\n");
        int pileSize = input.getNumberRangeOrEmpty("Pile size (leave empty to randomize): ", 1, Integer.MAX_VALUE);
        if (pileSize == -1) {
            pileSize = rng.nextInt(100);
            System.out.println("Using a pile with " + pileSize + " items\n");
        }
        nim = new Nim(pileSize);

        boolean useExpertAI = input.getBoolean("Do you want to play against an expert AI?");
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
                    choice = input.getNumber("Choose how many to take (1-" + nim.maxChoose() + "): ");
                }
            }
            nim.play(choice);
            System.out.println(nim.getPile() + " items left");
            player = (player + 1) % 2;
        }
        int winner = nim.win();
        System.out.println("Player " + (winner + 1) + " wins!\n");

        play = input.getBoolean("Would you like to play again?");
    }
}
