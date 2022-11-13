package day._10;

public enum ChunkBoundaries {

    SIMPLE('(', ')', 3, 1),
    MEDIUM('[', ']', 57, 2),
    COMPLEX('{', '}', 1197, 3),
    EXTRA_COMPLEX('<', '>', 25137, 4);

    private final char opener;
    private final char closer;
    private final int penalty;
    private final long bonus;

    ChunkBoundaries(char opener, char closer, int penalty, long bonus) {
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

    public long getBonus() {
        return bonus;
    }

    public static Integer get(Character character) {
        Integer boundaries = null;
        ChunkBoundaries[] values = values();
        for (int i = 0, valuesLength = values.length; i < valuesLength; i++) {
            ChunkBoundaries value = values[i];
            if (value.getOpener() == character) {
                boundaries = i;
            }
        }
        return boundaries;
    }
    
}
