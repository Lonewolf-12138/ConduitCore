package me.lonewolf.conduitcore.render.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.KeybindsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/21 13:27
 * @description TODO
 */

public class TextScreenn extends KeybindsScreen {

    private ControlsListWidget controlsList;

    public TextScreenn(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions);
    }

    protected void init() {
        this.controlsList = new ControlsListWidget(this, this.client);
        this.addSelectableChild(this.controlsList);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.controlsList.render(matrices, mouseX, mouseY, delta);
//        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
//        boolean bl = false;
//        KeyBinding[] var6 = this.gameOptions.allKeys;
//        int var7 = var6.length;
//
//        for(int var8 = 0; var8 < var7; ++var8) {
//            KeyBinding keyBinding = var6[var8];
//            if (!keyBinding.isDefault()) {
//                bl = true;
//                break;
//            }
//        }
//
////        this.resetAllButton.active = bl;
//        super.render(matrices, mouseX, mouseY, delta);
    }

}
