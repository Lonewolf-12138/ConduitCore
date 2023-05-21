package me.lonewolf.conduitcore.network.packet;

import net.minecraft.network.PacketByteBuf;

public interface IPacket {

    byte[] array();

    void write(PacketByteBuf buf);

}
