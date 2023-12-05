import java.util.HashMap;

public class ExpertSolver {
    private HashMap<Integer, Float> choiceCache = new HashMap<>();
    private boolean debug = false;

    public static ExpertSolver withDebug() {
        ExpertSolver s = new ExpertSolver();
        s.debug = true;
        return s;
    }

    public int choose(int pile) {
        int bestChoice = 1;
        float bestProbability = 0;
        for (int i = 1; i <= pile / 2; i++) {
            // try that choice
            float probability = choiceProbability(pile - i);
            if (debug)
                System.out.println("choice " + i + " has probability of " + probability);
            if (probability > bestProbability) {
                bestChoice = i;
                bestProbability = probability;
            }
        }
        return bestChoice;
    }

    public float choiceProbability(int pile) {
        if (pile == 1 || pile == 3) {
            return 1;
        } else if (pile == 2) {
            return 0;
        } else {
            if (choiceCache.containsKey(pile)) {
                return choiceCache.get(pile);
            }
            float p = 0;
            int numProbs = 0;
            // other player's choice
            for (int i = 1; i <= pile / 2; i++) {
                int pile2 = pile - i;
                // our choice
                for (int j = 1; j <= pile2 / 2; j++) {
                    p += choiceProbability(pile2 - j);
                    numProbs++;
                }
            }
            float r = p / ((float) numProbs);
            if (debug)
                System.out.println("choiceProbability(" + pile + ") = " + r);
            choiceCache.put(pile, r);
            return r;
        }
    }
}