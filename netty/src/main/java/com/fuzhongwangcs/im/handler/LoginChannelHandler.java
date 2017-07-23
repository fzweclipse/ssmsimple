package com.fuzhongwangcs.im.handler;

import com.fuzhongwangcs.im.constant.ProtocolConstant;
import com.fuzhongwangcs.im.entity.Packet;
import com.fuzhongwangcs.im.im.ThreadServerSocket;
import com.fuzhongwangcs.im.manager.IMTestManager;
import com.fuzhongwangcs.im.manager.IMUserManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 登录处理着，只有登录之后的用户才能去到下一个Handler
 * 不然就丢弃全部数据
 * <p>
 * author: vector.huang
 * date：2016/4/18 19:25
 */
public class LoginChannelHandler extends MessageToMessageDecoder<Packet> {

    private int userId;
    private ThreadServerSocket.OnChannelActiveListener listener;

    public LoginChannelHandler(ThreadServerSocket.OnChannelActiveListener listener) {
        this.listener = listener;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        if (listener != null) {
            listener.onChannelActive(ctx);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        if (userId > 0) {
            IMUserManager.logout(userId);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {

        System.out.println("packet -- "+packet.getServiceId() + ":"+packet.getCommandId());
        System.out.println(packet.getBody().toString(Charset.defaultCharset()));

        //测试服务
        if (packet.getServiceId() == ProtocolConstant.SID_TEST) {
            switch (packet.getCommandId()) {
                case ProtocolConstant.CID_TEST_TEST_REQ:
                    IMTestManager.testReq(ctx.channel(), packet.getBody());
                    break;
            }
            return;
        }

        //登录服务
        if (packet.getServiceId() == ProtocolConstant.SID_LOGIN) {
            if (packet.getCommandId() == ProtocolConstant.CID_LOGIN_IN) {
                ctx.channel().close();
            }
            return;
        }

        //用户服务
        if (packet.getServiceId() == ProtocolConstant.SID_USER) {
            if (packet.getCommandId() == ProtocolConstant.CID_USER_LOGIN_REQ) {
                userId = IMUserManager.loginReq(ctx.channel(),packet.getBody());
                return;
            }
        }

        if(userId <= 0){
            //未登录的什么都不做了
            return;
        }

        packet.setUserId(userId);
        out.add(packet);
    }
}
