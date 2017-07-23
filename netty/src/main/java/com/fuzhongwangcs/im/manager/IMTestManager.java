package com.fuzhongwangcs.im.manager;

import com.fuzhongwangcs.im.constant.ProtocolConstant;
import com.fuzhongwangcs.im.entity.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.nio.charset.Charset;

/**
 * author: vector.huang
 * date：2016/4/18 19:35
 */
public class IMTestManager {

    public static void testReq(Channel channel, ByteBuf body) {
        String test = body.toString(Charset.defaultCharset());
        System.out.println(test);
        testRsp(channel, test + " -- 屁");
    }

    public static void testRsp(Channel channel, String body) {
        byte[] bytes = body.getBytes();
        int length = bytes.length + 12;
        ByteBuf buf = channel.alloc().buffer(bytes.length);
        buf.writeBytes(bytes);
        Packet packet = new Packet(length, ProtocolConstant.SID_TEST, ProtocolConstant.CID_TEST_TEST_REQ
                , buf);
        channel.writeAndFlush(packet);
    }

}
