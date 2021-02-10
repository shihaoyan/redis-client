package com.shy.redis.client.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;

/**
 * @author shihaoyan
 * @version:
 * @since 2021-02-10 16:24
 */
@Configuration
@Slf4j
@SuppressWarnings("all")
public class ZookeeperConfig {

    @Value("${zookeeper.connect-string}")
    private String connextString;
    @Value("${zookeeper.session-timeout}")
    private int sessionTimeout;

    @Bean
    @Scope(value = "prototype")
    public ZooKeeper zooKeeper() {
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(connextString, sessionTimeout, (event) -> {
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    log.info("zookeeper client connected");
                }
            });
        } catch (IOException e) {
            log.error("zookeeper create fail", e);
        }
        return zooKeeper;
    }


}
