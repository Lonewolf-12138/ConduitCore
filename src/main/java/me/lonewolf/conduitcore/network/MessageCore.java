package me.lonewolf.conduitcore.network;

import me.lonewolf.conduitcore.network.packet.BuffPacket;
import me.lonewolf.conduitcore.network.packet.IPacket;
import me.lonewolf.conduitcore.network.packet.SkillCoolDownPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/16 21:48
 * @description TODO
 */

public class MessageCore {

    private final String namespace = "conduit";

    private final Map<Byte, IPacket> channelPacket = new HashMap<>();

    public MessageCore() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(namespace, namespace), this::receivePacket);
        channelPacket.put((byte) 10, new BuffPacket());
        channelPacket.put((byte) 11, new SkillCoolDownPacket());
    }

    public void receivePacket(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        IPacket iPacket = channelPacket.get(buf.readByte());
        if (iPacket != null){
            iPacket.write(buf);
        }
    }

    public void sendPacket() {
        // 创建并发送包
//        MyPacket packet = new MyPacket("Hello from client!");
        ClientPlayNetworking.send(new Identifier(namespace, namespace), null);
    }

}
