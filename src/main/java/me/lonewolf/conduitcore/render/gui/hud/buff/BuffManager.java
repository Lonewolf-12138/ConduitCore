package me.lonewolf.conduitcore.render.gui.hud.buff;

import me.lonewolf.conduitcore.util.config.BuffConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/18 23:51
 * @description TODO
 */

public class BuffManager {

    /**
     * 百分比？ , <buff名字, <buff加成，<buff>>>
     */
    private static final Map<String, Map<String, Map<String, Buff>>> BUFF_MAP = new ConcurrentHashMap<>();
    /**
     * 百分比？ , <buff加成，<buff>>
     */
    private static final Map<String, Map<String, Buff>> RENDER_BUFF_MAP = new ConcurrentHashMap<>();

    public static void init(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> tickUpdate());
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> render(matrixStack));
    }

    public static void addBuff(String directOrPercentage, String name, String attr, Buff buff){
        BUFF_MAP.computeIfAbsent(directOrPercentage, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(name, k -> new ConcurrentHashMap<>())
                .put(attr, buff);
    }

    public static void clearBuff(){
        BUFF_MAP.clear();

    }

    private static void render(MatrixStack matrixStack){
        if (!BuffConfig.isEnable()) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.world == null || client.player == null) {
            return;
        }
        matrixStack.push();
        TextRenderer textRenderer = client.textRenderer;
        Window window = client.getWindow();
        int fontHeight = client.textRenderer.fontHeight;
        float x = BuffConfig.getOffX(window);
        float y = BuffConfig.getOffY(window);
        Map<String, Map<String, Buff>> buffMap = getRenderBuffMap();
        for (String directOrPercentage : getRenderBuffMap().keySet()) {
            String sign = "";
            if(directOrPercentage.equals("PERCENTAGE_ADDITION")){
                sign = "%";
            }
            Map<String, Buff> attrMap = buffMap.get(directOrPercentage);
            for (String attr : attrMap.keySet()) {
                Buff buff = attrMap.get(attr);
                String value;
                if(buff.value == 0){
                    value = "0" + sign;
                }else if(buff.value > 0){
                    value = "+" + buff.value + sign;
                }else {
                    value = buff.value + sign;
                }
                String duration = getTime(buff.duration);
                String renderText = BuffConfig.replaceRenderText(attr, value, duration);
                int width = 0;
                if (BuffConfig.isRightAligned()){
                    width = textRenderer.getWidth(renderText);
                }
                textRenderer.draw(matrixStack, Text.of(renderText), x - width, y, 0);
                if(BuffConfig.isDescending()){
                    y += fontHeight + 1;
                }else {
                    y -= fontHeight + 1;
                }
            }
        }
        matrixStack.pop();
    }

    private static void tickUpdate(){
//        if(!BuffConfig.isEnable()){
//            return;
//        }
        RENDER_BUFF_MAP.clear();
        for (Map.Entry<String ,Map<String, Map<String, Buff>>> directOrPercentageEntry : BUFF_MAP.entrySet()) {
            String directOrPercentage = directOrPercentageEntry.getKey();
            Map<String, Map<String, Buff>> nameMap = directOrPercentageEntry.getValue();
            for (Map.Entry<String, Map<String, Buff>> nameEntry : nameMap.entrySet()) {
                Map<String, Buff> attrMap = nameEntry.getValue();
                for (Map.Entry<String, Buff> attrEntry : attrMap.entrySet()) {
                    String attr = attrEntry.getKey();
                    Buff buff = attrEntry.getValue();
                    buff.duration -= 1;
                    if (buff.duration <= 0) {
                        attrMap.remove(attr);
                        continue;
                    }
                    Map<String, Buff> renderDirectOrPercentageMap = RENDER_BUFF_MAP.computeIfAbsent(directOrPercentage, k -> new ConcurrentHashMap<>());
                    Buff renderBuff = renderDirectOrPercentageMap.get(attr);
                    if (renderBuff == null) {
                        renderBuff = new Buff(buff.value, buff.duration);
                        renderDirectOrPercentageMap.put(attr, renderBuff);
                    }else {
                        renderBuff.value += buff.value;
                        renderBuff.duration = Math.max(renderBuff.duration, buff.duration);
                    }
                }
            }
        }
    }

    private static String getTime(long duration){
        long totalSeconds = duration / 20;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    private static Map<String, Map<String, Buff>> getRenderBuffMap(){
        return new ConcurrentHashMap<>(RENDER_BUFF_MAP);
    }


}
