package org.example.objectpool.commonspool.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author lch
 * @date 2023年03月14日 22:10
 */
public class JDBCTest {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cmcc_hap?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false",
                "root",
                "123456");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from grape.login_log;"
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("id"));
            System.out.println(resultSet.getString("username"));
        }
        resultSet.close();
        preparedStatement.close();

        PreparedStatement preparedStatement2 = connection.prepareStatement(
                "select * from grape.user;"
        );
        ResultSet resultSet2 = preparedStatement2.executeQuery();
        while (resultSet2.next()) {
            System.out.println(resultSet2.getString(1));
            System.out.println(resultSet2.getString(2));
        }
        resultSet2.close();
        preparedStatement2.close();

        connection.close();
    }
}
