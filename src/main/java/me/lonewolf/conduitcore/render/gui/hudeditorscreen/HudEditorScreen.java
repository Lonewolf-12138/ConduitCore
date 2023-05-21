package me.lonewolf.conduitcore.render.gui.hudeditorscreen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dlovin.inventoryhud.config.InGameConfigScreen;
import dlovin.inventoryhud.config.gui.ArmorConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.KeybindsScreen;
import net.minecraft.client.gui.screen.option.OnlineOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/19 17:30
 * @description TODO
 */

public class HudEditorScreen extends Screen {

    public HudEditorScreen() {
        this(Text.of("HudEditorScreen"));
    }

    public HudEditorScreen(Text title) {
        super(title);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {
        HudEditorScreen hudEditorScreen = this;
        // buff
        this.addDrawableChild(new ButtonWidget(width / 2 - 100, 17, 80, 20, Text.of(I18n.translate("gui.buff.settingbutton")), new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                if (!(hudEditorScreen instanceof BuffHudEditorScreen)) {
                    client.setScreen(new BuffHudEditorScreen());
                }
            }

        }, new ButtonWidget.TooltipSupplier() {
            @Override
            public void onTooltip(ButtonWidget button, MatrixStack matrices, int mouseX, int mouseY) {
                renderTooltip(matrices, Text.of(I18n.translate("gui.buff.settingbutton")), mouseX, mouseY);
            }
        }));
        // 技能
        this.addDrawableChild(new ButtonWidget(width / 2  + 20, 17, 80, 20, Text.of(I18n.translate("gui.skill.settingbutton")), new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                if (!(hudEditorScreen instanceof SkillHudEditorScreen)) {
                    client.setScreen(new SkillHudEditorScreen());
                }
            }

        }, new ButtonWidget.TooltipSupplier() {
            @Override
            public void onTooltip(ButtonWidget button, MatrixStack matrices, int mouseX, int mouseY) {
                renderTooltip(matrices, Text.of(I18n.translate("gui.skill.settingbutton")), mouseX, mouseY);
            }
        }));
        // 保存
        this.addDrawableChild(new ButtonWidget(width / 2  - 60, height - 25, 40, 20, Text.of(I18n.translate("gui.hudeditor.save")), new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                clickSaveButton();
            }
        }, new ButtonWidget.TooltipSupplier() {
            @Override
            public void onTooltip(ButtonWidget button, MatrixStack matrices, int mouseX, int mouseY) {
                renderTooltip(matrices, Text.of(I18n.translate("gui.hudeditor.save")), mouseX, mouseY);
            }
        }));
        // 关闭
        this.addDrawableChild(new ButtonWidget(width / 2  + 20, height - 25, 40, 20, Text.of(I18n.translate("gui.hudeditor.close")), new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                clickSaveButton();
                client.setScreen((Screen) null);
            }
        }, new ButtonWidget.TooltipSupplier() {
            @Override
            public void onTooltip(ButtonWidget button, MatrixStack matrices, int mouseX, int mouseY) {
                renderTooltip(matrices, Text.of(I18n.translate("gui.hudeditor.close")), mouseX, mouseY);
            }
        }));
    }

    public void clickSaveButton(){

    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int left = 0;
        int right = width;
        int top = 55;
        int bottom = height - 30;
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, DrawableHelper.OPTIONS_BACKGROUND_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex((double)left, (double)bottom, 0.0).texture((float)left / 32.0F, (float)(bottom + (int)0) / 32.0F).color(32, 32, 32, 255).next();
        bufferBuilder.vertex((double)right, (double)bottom, 0.0).texture((float)right / 32.0F, (float)(bottom + (int)0) / 32.0F).color(32, 32, 32, 255).next();
        bufferBuilder.vertex((double)right, (double)top, 0.0).texture((float)right / 32.0F, (float)(top + (int)0) / 32.0F).color(32, 32, 32, 255).next();
        bufferBuilder.vertex((double)left, (double)top, 0.0).texture((float)left / 32.0F, (float)(top + (int)0) / 32.0F).color(32, 32, 32, 255).next();
        tessellator.draw();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, DrawableHelper.OPTIONS_BACKGROUND_TEXTURE);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(519);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex((double)left, (double)top, -100.0).texture(0.0F, (float)top / 32.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex((double)(left + width), (double)top, -100.0).texture((float)width / 32.0F, (float)top / 32.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex((double)(left + width), 0.0, -100.0).texture((float)width / 32.0F, 0.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex((double)left, 0.0, -100.0).texture(0.0F, 0.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex((double)left, (double)height, -100.0).texture(0.0F, (float)height / 32.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex((double)(left + width), (double)height, -100.0).texture((float)width / 32.0F, (float)height / 32.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex((double)(left + width), (double)bottom, -100.0).texture((float)width / 32.0F, (float)bottom / 32.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex((double)left, (double)bottom, -100.0).texture(0.0F, (float)bottom / 32.0F).color(64, 64, 64, 255).next();
        tessellator.draw();
        RenderSystem.depthFunc(515);
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
        RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex((double)left, (double)(top + 4), 0.0).color(0, 0, 0, 0).next();
        bufferBuilder.vertex((double)right, (double)(top + 4), 0.0).color(0, 0, 0, 0).next();
        bufferBuilder.vertex((double)right, (double)top, 0.0).color(0, 0, 0, 255).next();
        bufferBuilder.vertex((double)left, (double)top, 0.0).color(0, 0, 0, 255).next();
        bufferBuilder.vertex((double)left, (double)bottom, 0.0).color(0, 0, 0, 255).next();
        bufferBuilder.vertex((double)right, (double)bottom, 0.0).color(0, 0, 0, 255).next();
        bufferBuilder.vertex((double)right, (double)(bottom - 4), 0.0).color(0, 0, 0, 0).next();
        bufferBuilder.vertex((double)left, (double)(bottom - 4), 0.0).color(0, 0, 0, 0).next();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        super.render(matrices, mouseX, mouseY, delta);
    }

    public float roundToOneDecimal(float number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String formattedNumber = decimalFormat.format(number);
        return Float.parseFloat(formattedNumber);
    }

}
