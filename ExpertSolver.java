import java.util.HashMap;

/// Expert solver AI using tree search
public class ExpertSolver {
    /// Caches the result of choiceProbabilty for performance
    private HashMap<Integer, Float> choiceCache = new HashMap<>();
    private boolean debug = false;

    public static ExpertSolver withDebug() {
        ExpertSolver s = new ExpertSolver();
        s.debug = true;
        return s;
    }

    /// Chooses the best move for the AI given a pile size
    public int choose(int pile) {
        int bestChoice = 1;
        float bestProbability = 0;
        // find the best probability of winning from picking each valid i
        // 1 <= i <= pile / 2
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
        if (pile == 1 || pile == 3) { // AI will trivially win
            return 1;
        } else if (pile == 2) { // AI will trivially lose
            return 0;
        } else {
            // cache hit if possible
            if (choiceCache.containsKey(pile)) {
                return choiceCache.get(pile);
            }
            float p = 0;
            int numProbs = 0;
            // other player's choice, i
            // 1 <= i <= pile / 2
            for (int i = 1; i <= pile / 2; i++) {
                // pile size after other player's hypothetical choice
                int pile2 = pile - i;
                // our choice, j
                // 1 <= j <= pile2 / 2
                for (int j = 1; j <= pile2 / 2; j++) {
                    // get probability of winning after both hypothetical choices
                    p += choiceProbability(pile2 - j);
                    numProbs++;
                }
            }
            // average all probabilities
            float r = p / ((float) numProbs);
            if (debug)
                System.out.println("choiceProbability(" + pile + ") = " + r);
            // add to cache
            choiceCache.put(pile, r);
            return r;
        }
    }
}