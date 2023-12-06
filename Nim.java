/* The game of Nim (subtraction)
 * Holds all the game logic */
public class Nim {
    // Size of the pile
    private int pile;
    // Last player that went (0 or 1)
    private int lastPlayer;

    Nim(int pile) {
        this.pile = pile;
    }

    public int getPile() {
        return pile;
    }

    public int maxChoose() {
        return pile / 2;
    }

    // 1 <= choice <= pile / 2 (aka maxChoice)
    public boolean isValidChoice(int choice) {
        return choice >= 1 && choice <= maxChoose();
    }

    // Returns true if the choice was successfully played
    public boolean play(int choice) {
        if (!isValidChoice(choice)) {
            return false;
        }
        pile -= choice;
        // alternates between 0 and 1
        lastPlayer = (lastPlayer + 1) % 2;
        return true;
    }

    public int win() {
        if (pile == 0 || pile == 2) { // current player loses
            return lastPlayer;
        } else if (pile == 1 || pile == 3) { // current player wins
            return Math.abs(1 - lastPlayer);
        } else { // no winner yet
            return -1;
        }
    }
}