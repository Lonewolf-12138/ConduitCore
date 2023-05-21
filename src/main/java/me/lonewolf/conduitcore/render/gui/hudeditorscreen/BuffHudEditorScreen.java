package me.lonewolf.conduitcore.render.gui.hudeditorscreen;

import me.lonewolf.conduitcore.render.gui.hudeditorscreen.widget.FormatTextFieldWidget;
import me.lonewolf.conduitcore.render.gui.widget.NumberTextFieldWidget;
import me.lonewolf.conduitcore.render.gui.widget.TextWidget;
import me.lonewolf.conduitcore.util.config.BuffConfig;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/21 14:57
 * @description TODO
 */

public class BuffHudEditorScreen extends HudEditorScreen{

    private boolean enable = BuffConfig.isEnable();
    private float offX = BuffConfig.getRealOffX() * 100;
    private float offY = BuffConfig.getRealOffY() * 100;
    private String format = BuffConfig.getFormat();
    private boolean isRightAligned = BuffConfig.isRightAligned();
    private boolean isDescending = BuffConfig.isDescending();

    private NumberTextFieldWidget numberTextFieldWidgetOffX;

    private NumberTextFieldWidget numberTextFieldWidgetOffY;

    private FormatTextFieldWidget formatTextFieldWidget;

    @Override
    protected void init() {
        super.init();
        int fontHeight = textRenderer.fontHeight;
        this.addDrawable(new TextWidget(width / 2 - 18, 75, 16777215, TextWidget.Align.RIGHT, I18n.translate("gui.buff.enable"), textRenderer));
        this.addDrawable(new TextWidget(width / 2 - 18, (int) (75 + fontHeight * 2.6 * 1), 16777215, TextWidget.Align.RIGHT, I18n.translate("gui.buff.offX"), textRenderer));
        this.addDrawable(new TextWidget(width / 2 - 18, (int) (75 + fontHeight * 2.6 * 2), 16777215, TextWidget.Align.RIGHT, I18n.translate("gui.buff.offY"), textRenderer));
        this.addDrawable(new TextWidget(width / 2 - 18, (int) (75 + fontHeight * 2.6 * 3), 16777215, TextWidget.Align.RIGHT, I18n.translate("gui.buff.format"), textRenderer));
        this.addDrawable(new TextWidget(width / 2 - 18, (int) (75 + fontHeight * 2.6 * 4), 16777215, TextWidget.Align.RIGHT, I18n.translate("gui.buff.isRightAligned"), textRenderer));
        this.addDrawable(new TextWidget(width / 2 - 18, (int) (75 + fontHeight * 2.6 * 5), 16777215, TextWidget.Align.RIGHT, I18n.translate("gui.buff.isDescending"), textRenderer));
        this.addDrawableChild(new ButtonWidget(width / 2 + 20, 65, 40, 20, Text.of(this.enable ? "§a开启" : "§c关闭"), button -> {
            enable = !enable;
            button.setMessage(Text.of(enable ? "§a开启" : "§c关闭"));
        }));
        numberTextFieldWidgetOffX = this.addDrawableChild(new NumberTextFieldWidget(this, 120, -20, textRenderer, width / 2 + 22, (int) (65 + fontHeight * 2.6 * 1), 36, 18, Text.of(String.valueOf(roundToOneDecimal(offX)))));
        numberTextFieldWidgetOffY = this.addDrawableChild(new NumberTextFieldWidget(this, 120, -20, textRenderer, width / 2 + 22, (int) (65 + fontHeight * 2.6 * 2), 36, 18, Text.of(String.valueOf(roundToOneDecimal(offY)))));
        List<Text> list = new ArrayList<>();
        list.add(Text.of("§a<value> §f- §a增益数值"));
        list.add(Text.of("§a<attr> §f- §a增益属性"));
        list.add(Text.of("§a<duration> §f- §a增益持续时间"));
        list.add(Text.of("§a默认值: §e&f<value> &f<attr> &f(<duration>)"));
        formatTextFieldWidget = this.addDrawableChild(new FormatTextFieldWidget(this, list, textRenderer, width / 2 + 22, (int) (65 + fontHeight * 2.6 * 3), 180, 18, Text.of(format)));
        this.addDrawableChild(new ButtonWidget(width / 2 + 20, (int) (65 + fontHeight * 2.6 * 4), 40, 20, Text.of(this.isRightAligned ? "§a开启" : "§c关闭"), button -> {
            isRightAligned = !isRightAligned;
            button.setMessage(Text.of(isRightAligned ? "§a开启" : "§c关闭"));
        }));
        this.addDrawableChild(new ButtonWidget(width / 2 + 20, (int) (65 + fontHeight * 2.6 * 5), 40, 20, Text.of(this.isDescending ? "§a开启" : "§c关闭"), button -> {
            isDescending = !isDescending;
            button.setMessage(Text.of(isDescending ? "§a开启" : "§c关闭"));
        }));
    }

    @Override
    public void clickSaveButton() {
        BuffConfig.setEnable(this.enable);
        BuffConfig.setOffX(numberTextFieldWidgetOffX.getNumber(50.0F) / 100);
        BuffConfig.setOffY(numberTextFieldWidgetOffY.getNumber(50.0F) / 100);
        BuffConfig.setFormat(formatTextFieldWidget.getText());
        BuffConfig.setIsRightAligned(this.isRightAligned);
        BuffConfig.setIsDescending(this.isDescending);
        try {
            BuffConfig.saveConfig();
        } catch (IOException ignored) {
        }
    }

}
