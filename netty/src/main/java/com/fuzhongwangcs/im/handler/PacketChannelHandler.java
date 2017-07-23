package com.fuzhongwangcs.im.handler;

import com.fuzhongwangcs.im.constant.ProtocolConstant;
import com.fuzhongwangcs.im.entity.Packet;
import com.fuzhongwangcs.im.manager.IMMessageManager;
import com.fuzhongwangcs.im.manager.IMUserManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 代表一个Channel连接，保存着
 * <p>
 * author: vector.huang
 * date：2016/4/18 19:25
 */
public class PacketChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet packet = (Packet) msg;

        //用户服务
        if (packet.getServiceId() == ProtocolConstant.SID_USER) {
            switch (packet.getCommandId()) {
                case ProtocolConstant.CID_USER_ONLINE_REQ:
                    IMUserManager.onlineUserReq(ctx.channel(), packet.getBody());
                    break;
            }
            return;
        }

        //消息服务
        if (packet.getServiceId() == ProtocolConstant.SID_MSG) {
            switch (packet.getCommandId()) {
                case ProtocolConstant.CID_MSG_SEND_SINGLE_REQ:
                    IMMessageManager.sendSingleMsgReq(packet.getUserId(), packet.getBody());
                    break;
            }
            return;
        }

    }
}
