package me.lonewolf.conduitcore.network;

import me.lonewolf.conduitcore.network.packet.BuffPacket;
import me.lonewolf.conduitcore.network.packet.IPacket;
import me.lonewolf.conduitcore.network.packet.SkillCoolDownPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/16 21:48
 * @description TODO
 */

public class MessageCore {

    private final String channelId = "conduit";

    private final String channelVersion = "v1";

    private final Map<Byte, IPacket> channelPacket = new HashMap<>();

    private final List<String> incompletePackets = new CopyOnWriteArrayList<>();

    private final Logger logger;

    public MessageCore(Logger logger) {
        this.logger = logger;
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(this.channelId + this.channelVersion, this.channelId + this.channelVersion), this::receivePacket);
        channelPacket.put((byte) 10, new BuffPacket());
        channelPacket.put((byte) 11, new SkillCoolDownPacket());
        ClientPlayConnectionEvents.DISCONNECT.register(this::onDisconnect);
    }

    public void receivePacket(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        byte id = buf.readByte();
        this.incompletePackets.add(String.valueOf(id));
        IPacket packet = channelPacket.get(id);
        if (packet != null){
            packet.write(buf);
            this.incompletePackets.remove(String.valueOf(id));
        }
    }

    private void onDisconnect(ClientPlayNetworkHandler clientPlayNetworkHandler, MinecraftClient client) {
        if (this.incompletePackets.size() != 0) {
            this.logger.warning("有未完成的数据包: " + incompletePackets);
            this.incompletePackets.clear();
        }else {
            this.logger.info("无未完成的数据包");
        }

    }

    public void sendPacket() {
        // 创建并发送包
//        MyPacket packet = new MyPacket("Hello from client!");
        ClientPlayNetworking.send(new Identifier(channelId, channelVersion), null);
    }

}
