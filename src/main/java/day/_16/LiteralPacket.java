package day._16;

public class LiteralPacket extends Packet {

    protected final long value;

    public LiteralPacket(int version, int typeId,long value) {
        super(version, typeId);
        this.value = value;
    }

    @Override
    public int sumVersions() {
        return version;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString() + ", v:" + value + '}';
    }
}
