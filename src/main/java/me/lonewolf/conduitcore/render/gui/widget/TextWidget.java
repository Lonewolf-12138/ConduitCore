package me.lonewolf.conduitcore.render.gui.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/21 20:02
 * @description TODO
 */

public class TextWidget extends Widget {

    private int color;
    private TextRenderer fontRenderer;

    public TextWidget(int x, int y, int color, Align align, String text, TextRenderer fontRenderer) {
        super(x - 5, y - 9, fontRenderer.getWidth(text) + 10, 18, text);
        switch (align) {
            case LEFT:
            default:
                break;
            case RIGHT:
                this.x -= this.width - 10;
                break;
            case MIDDLE:
                this.x -= (this.width - 10) / 2;
        }

        this.color = color;
        this.fontRenderer = fontRenderer;
    }

    public void render(MatrixStack mat, int mouseX, int mouseY, float partialTick) {
        super.render(mat, mouseX, mouseY, partialTick);
        this.fontRenderer.drawWithShadow(mat, this.getMessage(), (float)(this.x + 5), (float)(this.y + 5), this.color);

    }

    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        return false;
    }

    public static enum Align{
        LEFT,
        MIDDLE,
        RIGHT
    }

}
