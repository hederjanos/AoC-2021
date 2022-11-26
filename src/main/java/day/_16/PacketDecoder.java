package day._16;

import util.common.Solver;

import java.util.ArrayList;
import java.util.List;

public class PacketDecoder extends Solver<Long> {

    private final Packet packet;

    protected PacketDecoder(String filename) {
        super(filename);
        String binaryCode = getBinaryString(puzzle.get(0));
        packet = parsePacket(binaryCode);
        System.out.println(packet);
    }

    private String getBinaryString(String code) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            int binary = Integer.parseInt(String.valueOf(code.charAt(i)), 16);
            String binaryString = String.format("%4s", Integer.toBinaryString(binary)).replace(' ', '0');
            stringBuilder.append(binaryString);
        }
        return stringBuilder.toString();
    }

    private Packet parsePacket(String binaryCode) {
        List<Packet> packets = new ArrayList<>();
        parseSubPacket(binaryCode, packets);
        return packets.get(0);
    }

    private int parseSubPacket(String binaryCode, List<Packet> packets) {
        int pointer = 0;
        int version = Integer.parseInt(binaryCode.substring(pointer, pointer + 3), 2);
        pointer += 3;
        int typeId = Integer.parseInt(binaryCode.substring(pointer, pointer + 3), 2);
        pointer += 3;
        if (typeId == 4) {
            return parseLiteralPacket(binaryCode, packets, pointer, version, typeId);
        } else {
            return parseOperatorPacket(binaryCode, packets, pointer, version, typeId);
        }
    }

    private int parseLiteralPacket(String binaryCode, List<Packet> packets, int pointer, int version, int typeId) {
        StringBuilder subPacket = new StringBuilder();
        String group;
        do {
            group = binaryCode.substring(pointer, pointer + 5);
            subPacket.append(group.substring(1));
            pointer += 5;
        } while (group.charAt(0) == '1');
        packets.add(new LiteralPacket(version, typeId, Long.parseLong(subPacket.toString(), 2)));
        return pointer;
    }

    private int parseOperatorPacket(String binaryCode, List<Packet> packets, int pointer, int version, int typeId) {
        char lengthTypeId = binaryCode.charAt(pointer);
        ++pointer;
        List<Packet> subPacketsForOperatorPacket = new ArrayList<>();
        if ('0' == lengthTypeId) {
            int lengthOfSubPackets = Integer.parseInt(binaryCode.substring(pointer, pointer + 15), 2);
            pointer += 15;
            String subPackets = binaryCode.substring(pointer, pointer + lengthOfSubPackets);
            int localBitCounter = 0;
            while (localBitCounter < lengthOfSubPackets) {
                int localPointer = parseSubPacket(subPackets, subPacketsForOperatorPacket);
                localBitCounter += localPointer;
                subPackets = subPackets.substring(localPointer);
            }
            pointer += lengthOfSubPackets;
            packets.add(new OperatorPacket(version, typeId, subPacketsForOperatorPacket));
        } else {

            int numberOfSubPackets = Integer.parseInt(binaryCode.substring(pointer, pointer + 11), 2);
            pointer += 11;
            for (int i = 0; i < numberOfSubPackets; ++i) {
                pointer += parseSubPacket(binaryCode.substring(pointer), subPacketsForOperatorPacket);
            }
            packets.add(new OperatorPacket(version, typeId, subPacketsForOperatorPacket));
        }
        return pointer;
    }

    @Override
    protected Long solvePartOne() {
        return (long) packet.sumVersions();
    }

    @Override
    protected Long solvePartTwo() {
        return packet.getValue();
    }

}
