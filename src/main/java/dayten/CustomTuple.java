package dayten;

import java.util.List;

public class CustomTuple extends Tuple<ChunkBoundaries, List<ChunkBoundaries>> {

    public CustomTuple(ChunkBoundaries firstElement, List<ChunkBoundaries> secondElement) {
        super(firstElement, secondElement);
    }

}
