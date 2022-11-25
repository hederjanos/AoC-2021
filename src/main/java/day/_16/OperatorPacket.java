package day._16;

import java.util.List;

public class OperatorPacket extends Packet {

    private final List<Packet> subPackets;

    public OperatorPacket(int version, int typeId, List<Packet> subPackets) {
        super(version, typeId);
        this.subPackets = subPackets;
    }

    @Override
    public int sumVersions() {
        return subPackets.stream().mapToInt(Packet::sumVersions).sum() + version;
    }

    @Override
    public int getValue() {
        int value;
        switch (typeId) {
            case 0:
                value = subPackets.stream().mapToInt(Packet::getValue).sum();
                break;
            case 1:
                value = subPackets.stream().mapToInt(Packet::getValue).reduce(1, (a, b) -> a * b);
                break;
            case 2:
                value = subPackets.stream().mapToInt(Packet::getValue).min().orElseThrow();
                break;
            case 3:
                value = subPackets.stream().mapToInt(Packet::getValue).max().orElseThrow();
                break;
            case 5:
                value = subPackets.get(0).getValue() > subPackets.get(1).getValue() ? 1 : 0;
                break;
            case 6:
                value = subPackets.get(0).getValue() < subPackets.get(1).getValue() ? 1 : 0;
                break;
            case 7:
                value = subPackets.get(0).getValue() == subPackets.get(1).getValue() ? 1 : 0;
                break;
            default:
                throw new IllegalStateException();
        }
        return value;
    }

    @Override
    public String toString() {
        return super.toString() + ", sub:" + subPackets + '}';
    }

}
