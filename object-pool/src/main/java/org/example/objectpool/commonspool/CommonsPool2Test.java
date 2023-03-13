package org.example.objectpool.commonspool;

import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author lch
 * @date 2023年03月13日 23:46
 */
public class CommonsPool2Test {
    public static void main(String[] args) throws Exception {
        GenericObjectPool<Money> moneyGenericObjectPool = new GenericObjectPool<>(new MoneyPooledObjectFactory());
        Money money = moneyGenericObjectPool.borrowObject();
        money.setType("RMB");
        moneyGenericObjectPool.returnObject(money);
    }
}
