package me.lonewolf.conduitcore.render.gui.hud.skillcooldown;

import me.lonewolf.conduitcore.util.config.SkillCoolDownConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* @author  Lonewolf_12138(QQ1090001011)
* @date  2023/5/20 15:48
* @description  TODO
*/

public class SkillDownManger {

    private static final Map<String, Long> SKILL_COOLDOWN = new ConcurrentHashMap<>();

    public static void init(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> tickUpdate());
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> render(matrixStack));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {clearSkillCoolDown();});
    }

    public static void addSkillCoolDown(String skill, long coolDown){
        SKILL_COOLDOWN.put(skill, coolDown);
    }

    public static void clearSkillCoolDown(){
        SKILL_COOLDOWN.clear();
    }

    private static void tickUpdate(){
        Iterator<Map.Entry<String, Long>> iterator = SKILL_COOLDOWN.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> skill = iterator.next();
            skill.setValue(skill.getValue() - 1);
            if (skill.getValue() <= 0) {
                iterator.remove();
            }
        }
    }

    private static void render(MatrixStack matrixStack){
        if(!SkillCoolDownConfig.isEnable()){
            return;
        }
        Map<String, Long> skillCooldownMap = getSkillCooldownMap();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.world == null || client.player == null) {
            return;
        }
        matrixStack.push();
        TextRenderer textRenderer = client.textRenderer;
        Window window = client.getWindow();
        float y = SkillCoolDownConfig.getOffY(window);
        Map<String, String> skillCoolDownStringMap = new HashMap<>();
        for (Map.Entry<String, Long> next : skillCooldownMap.entrySet()) {
            Long coolDown = next.getValue();
            String coolDownString = getCoolDown(coolDown);
            skillCoolDownStringMap.put(next.getKey(), coolDownString);
        }
        String renderText = SkillCoolDownConfig.replaceRenderText(skillCoolDownStringMap);
        int width = textRenderer.getWidth(renderText);
        textRenderer.draw(matrixStack, Text.of(renderText), (float) window.getScaledWidth() / 2 - (float) width / 2, y, 0);
        matrixStack.pop();
    }

    private static String getCoolDown(long coolDown){
        double seconds = coolDown / 20.0;
        return String.format("%.1f", seconds);
    }

    private static Map<String, Long> getSkillCooldownMap(){
        return new ConcurrentHashMap<>(SKILL_COOLDOWN);
    }

}
