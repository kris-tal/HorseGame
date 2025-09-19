package race;

import race.objects.EmptyObject;

import java.util.ArrayList;
import java.util.List;

public class MapGenerator {

    public static MapObject[][] generate(int lanes, int length) {
        MapObject[][] mapData = new MapObject[lanes][length];

        for (int col = 0; col < length; col++) {
            for (int lane = 0; lane < lanes; lane++) {
                mapData[lane][col] = (col <= 2) ? new EmptyObject() : MapObjectType.random(col);
            }
        }

        for (int col = 2; col < length; col++) {
            int prev = col - 2, mid = col - 1, curr = col;

            //checking lanes in first two cols
            List<Integer> safe12 = new ArrayList<>();
            for (int lane = 0; lane < lanes; lane++) {
                if (!mapData[lane][prev].isFatal() && !mapData[lane][mid].isFatal()) {
                    safe12.add(lane);
                }
            }

            //checking lanes in last two cols
            List<Integer> safe23 = new ArrayList<>();
            for (int lane = 0; lane < lanes; lane++) {
                if (!mapData[lane][mid].isFatal() && !mapData[lane][curr].isFatal()) {
                    safe23.add(lane);
                }
            }


            //finding overlap
            boolean ok = false;
            for (int l1 : safe12) {
                for (int l2 : safe23) {
                    if (Math.abs(l1 - l2) <= 1) { // same or adjacent lanes
                        ok = true;
                        break;
                    }
                }
                if (ok) break;
            }

            //if not force middle col to have a safe bridge
            if (!ok) {
                int bridgeLane = lanes / 2;
                mapData[bridgeLane][mid] = new EmptyObject();
            }
        }

        return mapData;
    }
}
