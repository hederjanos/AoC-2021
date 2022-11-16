package day._13;

public class Fold {

    private final Mirror mirror;
    private final int value;

    public Fold(String line) {
        String input = line.replace("fold along ", "");
        this.mirror = (input.charAt(0) == Mirror.HORIZONTAL.getaChar()) ? Mirror.HORIZONTAL : Mirror.VERTICAL;
        this.value = Integer.parseInt(input.split("=")[1]);
    }

    public Mirror getMirror() {
        return mirror;
    }

    public int getValue() {
        return value;
    }

    public enum Mirror {
        HORIZONTAL('y'), VERTICAL('x');

        private final char aChar;

        Mirror(char aChar) {
            this.aChar = aChar;
        }

        public char getaChar() {
            return aChar;
        }
    }

}
