package com.fuzhongwangcs.im.manager;

import com.fuzhongwangcs.im.constant.ProtocolConstant;
import com.fuzhongwangcs.im.entity.Packet;
import com.fuzhongwangcs.im.entity.UserChannel;
import com.fuzhongwangcs.im.im.IMChannelGroup;
import com.fuzhongwangcs.im.spring.SpringContainer;
import com.fuzhongwangcs.ssmsimple.web.model.User;
import com.fuzhongwangcs.ssmsimple.web.service.UserService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * author: vector.huang
 * date：2016/4/18 22:37
 */
public class IMUserManager {

    private static UserService userService = SpringContainer.getBean(UserService.class);

    /**
     * 登录，返回userId，响应userId
     *
     * @param channel
     * @param body
     * @return
     */
    public static int loginReq(Channel channel, ByteBuf body) {
        String username = body.readBytes(body.readInt()).toString(Charset.defaultCharset());
        String password = body.readBytes(body.readInt()).toString(Charset.defaultCharset());

        // 通过数据库进行验证
        final User authentication = userService.authentication(new User(username, password));
        if (authentication == null) {
            //返回错误代码
            ByteBuf byteBuf = channel.alloc().buffer();
            byteBuf.writeInt(-1);
            Packet packet = new Packet(byteBuf.readableBytes() + 12, ProtocolConstant.SID_USER,
                    ProtocolConstant.CID_USER_LOGIN_RESP, byteBuf);
            channel.writeAndFlush(packet);
            return -1;
        }else {

            //保存Channel 和 用户信息到IMChannelGroup
            UserChannel userChannel = new UserChannel();
            userChannel.setChannel(channel);
            userChannel.setUsername(username);
            int userId = IMChannelGroup.instance().put(userChannel);
            userChannel.setUserId(userId);

            //返回userId
            ByteBuf byteBuf = channel.alloc().buffer();
            byteBuf.writeInt(userId);
            Packet packet = new Packet(byteBuf.readableBytes() + 12, ProtocolConstant.SID_USER,
                    ProtocolConstant.CID_USER_LOGIN_RESP, byteBuf);
            channel.writeAndFlush(packet);

            System.out.println("连接-在线用户为: " + IMChannelGroup.instance().size());
            return userId;
        }
    }

    /**
     * 登出，断开连接
     *
     * @param userId
     */
    public static void logout(int userId) {
        IMChannelGroup.instance().remove(userId);
        System.out.println("断开-在线用户为: " + IMChannelGroup.instance().size());
    }

    public static void onlineUserReq(Channel channel, ByteBuf body) {

        ByteBuf users = channel.alloc().buffer();
        Map<Integer, UserChannel> userChannels = IMChannelGroup.instance().getChannels();

        //在线总人数
        users.writeInt(userChannels.size());
        //用户列表
        userChannels.forEach((userId, userChannel) -> {
            byte[] bytes = userChannel.getUsername().getBytes();
            users.writeInt(userId)
                    .writeInt(bytes.length)
                    .writeBytes(bytes);
        });

        Packet packet = new Packet(users.readableBytes() + 12, ProtocolConstant.SID_USER
                , ProtocolConstant.CID_USER_ONLINE_RESP, users);
        channel.writeAndFlush(packet);

    }

}
