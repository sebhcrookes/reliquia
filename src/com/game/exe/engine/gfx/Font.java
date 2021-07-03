package com.game.exe.engine.gfx;

public class Font {

    public static final Font STANDARD = new Font("/assets/fonts/standard.png", 1);

    private Image fontImage;
    private int[] offsets;
    private int[] widths;

    private int textSpacing;

    public Font(String path, int textSpacing) {
        fontImage = new Image(path);
        offsets = new int[256];
        widths = new int[256];

        this.textSpacing = textSpacing;

        int unicode = 0;

        for(int i = 0; i < fontImage.getW(); i++) {
            if(fontImage.getP()[i] == 0xff0000ff) {
                offsets[unicode] = i + textSpacing;
            }

            if(fontImage.getP()[i] == 0xffffff00) {
                widths[unicode] = i - offsets[unicode];
                unicode++;
            }
        }
    }

    public int getTextSpacing() {
        return textSpacing;
    }

    public Image getFontImage() {
        return fontImage;
    }

    public void setFontImage(Image fontImage) {
        this.fontImage = fontImage;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public void setOffsets(int[] offsets) {
        this.offsets = offsets;
    }

    public int[] getWidths() {
        return widths;
    }

    public void setWidths(int[] widths) {
        this.widths = widths;
    }

    public int getHeight() {
        return fontImage.getH();
    }

}
