package me.lonewolf.conduitcore.network.packet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.util.CharsetUtil;
import me.lonewolf.conduitcore.render.gui.hud.buff.Buff;
import me.lonewolf.conduitcore.render.gui.hud.buff.BuffManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;

import java.util.Map;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/16 22:06
 * @description TODO
 */

public class BuffPacket implements IPacket{


    public BuffPacket() {
    }

    @Override
    public byte[] array() {
        return new byte[3];
    }

    @Override
    public void write(PacketByteBuf byteBuf) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null){
            return;
        }
        byte[] readBytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(readBytes);
        String readJson = new String(readBytes, CharsetUtil.UTF_8);
        JsonElement jsonElement = JsonParser.parseString(readJson);
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        if (asJsonObject.get("clearPreviousEffects").getAsBoolean()) {
            BuffManager.clearBuff();
        }
        JsonObject buffs = asJsonObject.getAsJsonObject("buffs");
        for (Map.Entry<String, JsonElement> entry : buffs.entrySet()) {
            String name = entry.getKey();
            JsonObject buff = entry.getValue().getAsJsonObject();
            for (Map.Entry<String, JsonElement> attrEntry : buff.entrySet()) {
                String attr = attrEntry.getKey();
                JsonObject attrJson = attrEntry.getValue().getAsJsonObject();
                String type = attrJson.get("type").getAsString();
                int value = attrJson.get("value").getAsInt();
                long duration = attrJson.get("duration").getAsLong();
                BuffManager.addBuff(type, name, attr, new Buff(value, duration));
            }
        }
    }

}
