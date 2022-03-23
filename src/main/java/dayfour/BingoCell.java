package dayfour;

public class BingoCell {

    private Integer number;
    private boolean marked;

    public BingoCell(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked() {
        this.marked = true;
    }

    public void setUnMarked() {
        this.marked = false;
    }
}
