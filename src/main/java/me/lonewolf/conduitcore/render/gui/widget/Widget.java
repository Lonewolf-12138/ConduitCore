package me.lonewolf.conduitcore.render.gui.widget;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/21 20:03
 * @description TODO
 */

public class Widget extends DrawableHelper implements Drawable, Element {

    public int x;
    public int y;
    public int width;
    public int height;
    private String msg;
    protected boolean hovered;

    public Widget(int x, int y, int width, int height, String msg) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.msg = msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return this.msg;
    }

    public void renderTooltip(MatrixStack mat, int mx, int my) {


    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }

    public void mouseMoved(double mouseX, double mouseY) {
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return false;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    public boolean charTyped(char chr, int keyCode) {
        return false;
    }

    public boolean changeFocus(boolean lookForwards) {
        return false;
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return false;
    }

}
