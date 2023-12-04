import java.util.HashMap;

public class ExpertSolver {
    HashMap<Integer, Float> choiceCache = new HashMap<>();

    public int choose(int pile) {
        int bestChoice = 1;
        float bestProbability = 0;
        for (int i = 1; i <= pile/2; i++) {
            // try that choice
            float probability = choiceProbability(pile-i);
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
            return 0;
        } else if (pile == 2) {
            return 1;
        } else {
            if (choiceCache.containsKey(pile)) {
                return choiceCache.get(pile);
            }
            float p = 0;
            int numProbs = 0;
            for (int i = 1; i <= pile/2; i++) {
                p += choiceProbability(pile-i);
                numProbs++;
            }
            float r = p/((float)numProbs);
            choiceCache.put(pile, r);
            return r;
        }
    }
}