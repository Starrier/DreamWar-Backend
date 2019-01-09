/*
package org.starrier.dreamwar.handler;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

*/
/**
 * @author Starrier
 * @date 2018/12/30
 * *//*

@Component
@Qualifier("somethingChannelInitializer")
public class NettyWebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private TextWebSocketFrameHandler textWebSocketFrameHandler;

    */
/**
     * @param ch  SocketChannel.
     * *//*

    @Override
    public void initChannel(SocketChannel ch) {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec())
                .addLast(new HttpObjectAggregator(64*1024))
                .addLast(new ChunkedWriteHandler())
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                .addLast(textWebSocketFrameHandler);

    }
}*/
