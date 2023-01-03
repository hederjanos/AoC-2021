package day._12;

import java.util.Objects;
import java.util.function.Predicate;

public class Cave {

    private final String label;
    private final boolean isBig;

    public Cave(String label, Predicate<String> predicate) {
        this.label = label;
        this.isBig = predicate.test(label);
    }

    public String getLabel() {
        return label;
    }

    public boolean isBig() {
        return isBig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cave cave = (Cave) o;
        return Objects.equals(label, cave.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

}
