package game.item;

import java.util.EnumMap;

public class SeedInventory {
	
	private EnumMap<SeedType,Integer> seeds = new EnumMap<>(SeedType.class);
	
	public SeedInventory() {
		for(SeedType s : SeedType.values()) {
			// DEFAULT EACH PLANT HAVE 5 SEEDS
			seeds.put(s, 5);
			
		}
	}
	
	public SeedType getCurrentSeed() {
		for(SeedType s : seeds.keySet()) {
			if(seeds.get(s)>0) return s;
			
		}
		return null;
	}
	
	public int getAmount(SeedType type) {
		return seeds.getOrDefault(type,0);
	}
	// CHECK HAS SEED OR NOT
	public boolean hasSeed(SeedType type) {
		return getAmount(type)> 0;
	}
	// USING SEED HAS
	public boolean useSeed(SeedType type) {
		int amount = getAmount(type);
		if (amount >0) {
			seeds.put(type, amount -1);
			return true;
		}
		return false;
	}
	// ADDING SEED
	public void addSeed(SeedType type, int amount) {
		seeds.put(type,getAmount(type)+amount);
	}
	public EnumMap<SeedType,Integer> getAll(){
		return seeds;
	}

}
