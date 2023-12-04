/* The game of Nim (subtraction) */
public class Nim {
    private int pile;
    private int lastPlayer;

    Nim(int pile) {
        this.pile = pile;
    }

    public int getPile() { return pile; }

    public int maxChoose() {
        return pile/2;
    }

    public boolean isValidChoice(int choice) {
        return choice >= 1 && choice <= maxChoose();
    }

    public boolean play(int choice) {
        if (!isValidChoice(choice)) {
            return false;
        }
        pile -= choice;
        lastPlayer = (lastPlayer + 1) % 2;
        return true;
    }

    public int win() {
        if (pile == 0 || pile == 2) {
            return lastPlayer;
        } else if (pile == 1 || pile == 3) {
            return Math.abs(1 - lastPlayer);
        } else {
            return -1;
        }
    }
}