package daytwo;

public class Command {

    private final Direction direction;
    private final Integer value;

    public Command(String command) {
        String[] commandParts = command.split(" ");
        this.direction = Direction.valueOf(commandParts[0].toUpperCase());
        this.value = Integer.parseInt(commandParts[1]);
    }

    public Direction getDirection() {
        return direction;
    }

    public Integer getValue() {
        return value;
    }
}
