package me.lonewolf.conduitcore.network.packet;

import me.lonewolf.conduitcore.render.gui.hud.buff.Buff;
import me.lonewolf.conduitcore.render.gui.hud.buff.BuffManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;

import java.nio.charset.StandardCharsets;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/16 22:06
 * @description TODO
 */

public class BuffPacket implements IPacket{

    @Override
    public byte[] array() {
        return new byte[3];
    }

    @Override
    public void write(PacketByteBuf buf) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null){
            return;
        }
        if (buf.readBoolean()) {
            BuffManager.clearBuff();
        }
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            int nameLength = buf.readInt();
            byte[] nameBytes = new byte[nameLength];
            buf.readBytes(nameBytes);
            String name = new String(nameBytes, StandardCharsets.UTF_8);

            int attrLength = buf.readInt();
            byte[] attrBytes = new byte[attrLength];
            buf.readBytes(attrBytes);
            String attr = new String(attrBytes, StandardCharsets.UTF_8);

            int typeLength = buf.readInt();
            byte[] typeBytes = new byte[typeLength];
            buf.readBytes(typeBytes);
            String type = new String(typeBytes, StandardCharsets.UTF_8);

            int value = buf.readInt();
            long duration = buf.readLong();
            BuffManager.addBuff(type, name, attr, new Buff(value, duration));
        }
//        byte[] readBytes = new byte[byteBuf.readableBytes()];
//        byteBuf.readBytes(readBytes);
//        String readJson = new String(readBytes, CharsetUtil.UTF_8);
//        JsonElement jsonElement = JsonParser.parseString(readJson);
//        JsonObject asJsonObject = jsonElement.getAsJsonObject();
//        if (asJsonObject.get("clearPreviousEffects").getAsBoolean()) {
//            BuffManager.clearBuff();
//        }
//        JsonObject buffs = asJsonObject.getAsJsonObject("buffs");
//        for (Map.Entry<String, JsonElement> entry : buffs.entrySet()) {
//            String name = entry.getKey();
//            JsonObject buff = entry.getValue().getAsJsonObject();
//            for (Map.Entry<String, JsonElement> attrEntry : buff.entrySet()) {
//                String attr = attrEntry.getKey();
//                JsonObject attrJson = attrEntry.getValue().getAsJsonObject();
//                String type = attrJson.get("type").getAsString();
//                int value = attrJson.get("value").getAsInt();
//                long duration = attrJson.get("duration").getAsLong();
//                BuffManager.addBuff(type, name, attr, new Buff(value, duration));
//            }
//        }
    }

}
