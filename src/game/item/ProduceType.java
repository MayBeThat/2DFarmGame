package game.item;

import java.awt.image.BufferedImage;

public enum ProduceType {
    CARROT,
    TOMATO,
    RICE,
    SUNFLOWER;

    private BufferedImage icon;

    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }

    public BufferedImage getIcon() {
        return icon;
    }
}
