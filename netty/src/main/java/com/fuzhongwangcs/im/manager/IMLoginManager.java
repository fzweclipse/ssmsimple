package com.fuzhongwangcs.im.manager;

import com.fuzhongwangcs.im.constant.ProtocolConstant;
import com.fuzhongwangcs.im.entity.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * author: vector.huang
 * date：2016/4/20 20:34
 */
public class IMLoginManager {

    public static void outIpPort(Channel channel, String host, int port) {
        ByteBuf ipPort = channel.alloc().buffer();
        byte[] hostByte = host.getBytes();
        ipPort.writeInt(hostByte.length);
        ipPort.writeBytes(hostByte);
        ipPort.writeInt(port);

        Packet packet = new Packet(ipPort.readableBytes() + 12, ProtocolConstant.SID_LOGIN
                , ProtocolConstant.CID_LOGIN_OUT, ipPort);

        channel.writeAndFlush(packet);
    }

}
