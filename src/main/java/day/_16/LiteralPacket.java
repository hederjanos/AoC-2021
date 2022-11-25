package day._16;

public class LiteralPacket extends Packet {

    protected final int value;

    public LiteralPacket(int version, int typeId, int value) {
        super(version, typeId);
        this.value = value;
    }

    @Override
    public int sumVersions() {
        return version;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString() + ", v:" + value + '}';
    }
}
