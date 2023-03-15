package org.example.objectpool.datasource;


import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lch
 * @date 2023年03月14日 23:25
 */
@Endpoint(id = "datasource")
public class MyDataSourceEndpoint {
    private MyDataSource dataSource;

    public MyDataSourceEndpoint(MyDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @ReadOperation
    public Map<String, Object> pool() {
        GenericObjectPool<MyConnection> pool = dataSource.getPool();
        HashMap<String, Object> map = new HashMap<>();
        map.put("numActive", pool.getNumActive());
        map.put("numIdle", pool.getNumIdle());
        map.put("createdCount", pool.getCreatedCount());
        return map;
    }
}
