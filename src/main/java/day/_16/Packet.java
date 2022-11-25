package day._16;

public abstract class Packet {

    protected final int typeId;
    protected final int version;

    protected Packet(int version, int typeId) {
        this.version = version;
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public int getVersion() {
        return version;
    }

    public abstract int sumVersions();

    public abstract int getValue();

    @Override
    public String toString() {
        return "P{" +
               "id:" + typeId +
               ", v:" + version;
    }
}
