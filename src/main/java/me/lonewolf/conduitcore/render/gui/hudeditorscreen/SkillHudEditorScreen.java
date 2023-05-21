package me.lonewolf.conduitcore.render.gui.hudeditorscreen;

import me.lonewolf.conduitcore.render.gui.hudeditorscreen.widget.FormatTextFieldWidget;
import me.lonewolf.conduitcore.render.gui.widget.NumberTextFieldWidget;
import me.lonewolf.conduitcore.render.gui.widget.TextWidget;
import me.lonewolf.conduitcore.util.config.SkillCoolDownConfig;
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

public class SkillHudEditorScreen extends HudEditorScreen{

    private boolean enable = SkillCoolDownConfig.isEnable();
    private float offY = SkillCoolDownConfig.getRealOffY();
    private String format = SkillCoolDownConfig.getFormat();
    private String separator = SkillCoolDownConfig.getSeparator();
    private boolean isCoordinateFromBottom = SkillCoolDownConfig.isCoordinateFromBottom();
    private NumberTextFieldWidget numberTextFieldWidgetOffY;
    private FormatTextFieldWidget formatTextFieldWidgetFormat;
    private FormatTextFieldWidget formatTextFieldWidgetSeparator;

    @Override
    protected void init() {
        super.init();
        int fontHeight = textRenderer.fontHeight;
        this.addDrawable(new TextWidget(width / 2 - 18, 75, 16777215, TextWidget.Align.RIGHT, I18n.translate("gui.skill.enable"), textRenderer));
        this.addDrawable(new TextWidget(width / 2 - 18, (int) (75 + fontHeight * 2.6 * 1), 16777215, TextWidget.Align.RIGHT, I18n.translate("gui.skill.offY"), textRenderer));
        this.addDrawable(new TextWidget(width / 2 - 18, (int) (75 + fontHeight * 2.6 * 2), 16777215, TextWidget.Align.RIGHT, I18n.translate("gui.skill.format"), textRenderer));
        this.addDrawable(new TextWidget(width / 2 - 18, (int) (75 + fontHeight * 2.6 * 3), 16777215, TextWidget.Align.RIGHT, I18n.translate("gui.skill.separator"), textRenderer));
        this.addDrawable(new TextWidget(width / 2 - 18, (int) (75 + fontHeight * 2.6 * 4), 16777215, TextWidget.Align.RIGHT, I18n.translate("gui.skill.isCoordinateFromBottom"), textRenderer));
        this.addDrawableChild(new ButtonWidget(width / 2 + 20, 65, 40, 20, Text.of(this.enable ? "§a开启" : "§c关闭"), button -> {
            enable = !enable;
            button.setMessage(Text.of(enable ? "§a开启" : "§c关闭"));
        }));
        numberTextFieldWidgetOffY = this.addDrawableChild(new NumberTextFieldWidget(this, 999999, -999999, textRenderer, width / 2 + 22, (int) (65 + fontHeight * 2.6 * 1), 36, 18, Text.of(String.valueOf(roundToOneDecimal(offY)))));
        List<Text> listFormat = new ArrayList<>();
        listFormat.add(Text.of("§a<skill> §f- §a技能名称"));
        listFormat.add(Text.of("§a<cooldown> §f- §a冷却时间"));
        listFormat.add(Text.of("§a默认值: §e&7<skill> (&f<cooldown>s&7)"));
        formatTextFieldWidgetFormat = this.addDrawableChild(new FormatTextFieldWidget(this, listFormat, textRenderer, width / 2 + 22, (int) (65 + fontHeight * 2.6 * 2), 180, 18, Text.of(format)));
        List<Text> listSeparator = new ArrayList<>();
        listSeparator.add(Text.of("§a默认值: §e&7 - "));
        formatTextFieldWidgetSeparator = this.addDrawableChild(new FormatTextFieldWidget(this, listSeparator, textRenderer, width / 2 + 22, (int) (65 + fontHeight * 2.6 * 3), 180, 18, Text.of(separator)));
        this.addDrawableChild(new ButtonWidget(width / 2 + 20, (int) (65 + fontHeight * 2.6 * 4), 40, 20, Text.of(this.isCoordinateFromBottom ? "§a开启" : "§c关闭"), button -> {
            isCoordinateFromBottom = !isCoordinateFromBottom;
            button.setMessage(Text.of(isCoordinateFromBottom ? "§a开启" : "§c关闭"));
        }));
    }

    @Override
    public void clickSaveButton() {
        SkillCoolDownConfig.setEnable(this.enable);
        SkillCoolDownConfig.setIsCoordinateFromBottom(this.isCoordinateFromBottom);
        SkillCoolDownConfig.setOffY(this.numberTextFieldWidgetOffY.getNumber(88F));
        SkillCoolDownConfig.setFormat(this.formatTextFieldWidgetFormat.getText());
        SkillCoolDownConfig.setSeparator(this.formatTextFieldWidgetSeparator.getText());
        try {
            SkillCoolDownConfig.saveConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
