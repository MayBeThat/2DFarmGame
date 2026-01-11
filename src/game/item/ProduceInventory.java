package game.item;

import java.util.EnumMap;

public class ProduceInventory {

    private final EnumMap<ProduceType, Integer> produceMap = new EnumMap<>(ProduceType.class);

    public ProduceInventory() {
        for (ProduceType t : ProduceType.values()) {
            produceMap.put(t, 0);
        }
    }

    public int getAmount(ProduceType type) {
        return produceMap.getOrDefault(type, 0);
    }

    public void addProduce(ProduceType type, int amount) {
        if (amount <= 0) return;
        produceMap.put(type, getAmount(type) + amount);
    }
}
