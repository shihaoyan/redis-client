package com.shy.redis.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shihaoyan
 * @version:
 * @since 2021-02-10 16:08
 */
@RestController
@Slf4j
@SuppressWarnings("all")
public class ZookeeperClientController {


    @Autowired
    private ZooKeeper zooKeeper;


    @RequestMapping("/zk/{znode}")
    public Object zkClient(@PathVariable("znode") String znode) {
        Stat stat = null;
        byte[] data = null;
        try {
            log.debug("开始创建节点 /" + znode);
            if (zooKeeper.exists("/" + znode, false) == null) {
                zooKeeper.create("/" + znode, znode.getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            }
            log.debug("节点 /" + znode + " 创建成功");
            data = zooKeeper.getData("/" + znode, (event) -> {
                log.info(event.toString());
            }, stat);
        } catch (Exception e) {
            log.error("zookeeper connect fail", e);
        }
        return data;
    }


}
