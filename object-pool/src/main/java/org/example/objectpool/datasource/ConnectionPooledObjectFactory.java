package org.example.objectpool.datasource;

import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lch
 * @date 2023年03月14日 22:46
 */
public class ConnectionPooledObjectFactory implements PooledObjectFactory<Connection> {
    @Override
    public PooledObject<Connection> makeObject() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cmcc_hap?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false",
                "root",
                "123456");
        return new DefaultPooledObject<>(connection);
    }

    @Override
    public void activateObject(PooledObject<Connection> pooledObject) throws Exception {

    }

    @Override
    public void destroyObject(PooledObject<Connection> pooledObject) throws Exception {
        pooledObject.getObject().close();
    }

    @Override
    public void passivateObject(PooledObject<Connection> pooledObject) throws Exception {

    }

    @Override
    public boolean validateObject(PooledObject<Connection> pooledObject) {
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
