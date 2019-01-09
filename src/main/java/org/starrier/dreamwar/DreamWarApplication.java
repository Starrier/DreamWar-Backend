package org.starrier.dreamwar;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;





/**
 * Warning.
 *
 * 1.When we use the {@link EnableCaching},we can not use {@link java.util.Optional},
 *   Because the {@link java.util.Optional} can not applied for {@link java.io.Serializable}.
 *
 * @author Starrier
 * @date 2018/1/7
 * */
@EnableRetry
@PropertySource(value= "classpath:/nettyserver.properties")
@EnableAspectJAutoProxy
@EnableCaching
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
@ServletComponentScan
public class DreamWarApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(DreamWarApplication.class);

    @Value("${tcp.port}")
    private int tcpPort;

    @Value("${boss.thread.count}")
    private int bossCount;

    @Value("${worker.thread.count}")
    private int workerCount;

    @Value("${so.keepalive}")
    private boolean keepAlive;

    @Value("${so.backlog}")
    private int backlog;

   /* @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(nettyWebSocketChannelInitializer);
        Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
        Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
        for (@SuppressWarnings("rawtypes") ChannelOption option : keySet) {
            b.option(option, tcpChannelOptions.get(option));
        }
        return b;
    }*/

   /* @Autowired
    @Qualifier("somethingChannelInitializer")
    private NettyWebSocketChannelInitializer nettyWebSocketChannelInitializer;

    @Bean(name = "tcpChannelOptions")
    public Map<ChannelOption<?>, Object> tcpChannelOptions() {
        Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
        options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
        options.put(ChannelOption.SO_BACKLOG, backlog);
        return options;
    }

    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerCount);
    }

    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(tcpPort);
    }
*/

	public static void main(String[] args) throws Exception{

		LOGGER.info("------------ DreamWar-Backend -------- is starting!-----");

       /* ConfigurableApplicationContext context = SpringApplication.run(DreamWarApplication.class, args);
        TCPServer tcpServer = context.getBean(TCPServer.class);
        tcpServer.start();*/

        SpringApplication.run(DreamWarApplication.class, args);

		LOGGER.info("------------ DreamWar-Backend -------- Have Started ! -----");
	}


}
