package org.example.objectpool.datasource;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author lch
 * @date 2023年03月14日 22:46
 */
public class ConnectionPooledObjectFactory implements PooledObjectFactory<MyConnection> {

    private ObjectPool<MyConnection> objectPool;

    public ObjectPool<MyConnection> getObjectPool() {
        return objectPool;
    }

    public void setObjectPool(ObjectPool<MyConnection> objectPool) {
        this.objectPool = objectPool;
    }

    @Override
    public PooledObject<MyConnection> makeObject() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/grape?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false",
                "root",
                "123456");
        MyConnection myConnection = new MyConnection();
        myConnection.setConnection(connection);
        myConnection.setObjectPool(objectPool);
        return new DefaultPooledObject<>(myConnection);
    }

    @Override
    public void activateObject(PooledObject<MyConnection> pooledObject) throws Exception {
        // 把connection额外配置放在这里
    }

    @Override
    public void passivateObject(PooledObject<MyConnection> pooledObject) throws Exception {
        // 钝化
        MyConnection myConnection = pooledObject.getObject();
        Statement statement = myConnection.getStatement();
        if (statement != null) {
            statement.close();
        }
    }

    @Override
    public void destroyObject(PooledObject<MyConnection> pooledObject) throws Exception {
        pooledObject.getObject().close();
    }

    @Override
    public boolean validateObject(PooledObject<MyConnection> pooledObject) {
        Connection connection = pooledObject.getObject();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT  1");
            ResultSet resultSet = statement.executeQuery();
            int i = resultSet.getInt(1);
            return i == 1;
        } catch (SQLException e) {
            return false;
        }
    }
}
