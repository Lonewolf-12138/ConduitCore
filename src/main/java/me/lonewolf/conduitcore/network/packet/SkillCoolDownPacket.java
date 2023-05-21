package me.lonewolf.conduitcore.network.packet;

import me.lonewolf.conduitcore.render.gui.hud.skillcooldown.SkillDownManger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;

import java.nio.charset.StandardCharsets;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/20 15:43
 * @description TODO
 */

public class SkillCoolDownPacket implements IPacket{

    @Override
    public byte[] array() {
        return new byte[0];
    }

    @Override
    public void write(PacketByteBuf buf) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null){
            return;
        }
        if (buf.readBoolean()) {
            SkillDownManger.clearSkillCoolDown();
        }
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            int skillLength = buf.readInt();
            byte[] skillBytes = new byte[skillLength];
            buf.readBytes(skillBytes);
            String skill = new String(skillBytes, StandardCharsets.UTF_8);
            long coolDown = buf.readLong();
            if(coolDown != 0){
                SkillDownManger.addSkillCoolDown(skill, coolDown);
            }
        }
    }

}
