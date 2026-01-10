package game.item;
import game.crop.CropType;
public enum SeedType {
CARROT(CropType.CARROT),
TOMATO(CropType.TOMATO),
RICE(CropType.RICE),
SUNFLOWER(CropType.SUNFLOWER);
	public final CropType cropType;
	SeedType(CropType cropType){
		this.cropType = cropType;
	}
	
}
