package dayten;

public abstract class Tuple<E, I> {

    protected E firstElement;
    protected I secondElement;

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
