package day._10;

public abstract class Tuple<E, I> {

    protected final E firstElement;
    protected final I secondElement;

    Tuple(E firstElement, I secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    public E getFirstElement() {
        return firstElement;
    }

    public I getSecondElement() {
        return secondElement;
    }
}
