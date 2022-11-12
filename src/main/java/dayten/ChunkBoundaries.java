package dayten;

public enum ChunkBoundaries {

    SIMPLE('(', ')', 3, 1),
    MEDIUM('[', ']', 57, 2),
    COMPLEX('{', '}', 1197, 3),
    EXTRA_COMPLEX('<', '>', 25137, 4);

    private final char opener;
    private final char closer;
    private final int penalty;
    private final int bonus;

    ChunkBoundaries(char opener, char closer, int penalty, int bonus) {
        this.opener = opener;
        this.closer = closer;
        this.penalty = penalty;
        this.bonus = bonus;
    }

    public char getOpener() {
        return opener;
    }

    public char getCloser() {
        return closer;
    }

    public int getPenalty() {
        return penalty;
    }

    public int getBonus() {
        return bonus;
    }
    
}
