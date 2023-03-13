package org.example.objectpool.commonspool;

import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.math.BigDecimal;

/**
 * 资金池对象工厂
 *
 * @author lch
 * @date 2023年03月13日 23:40
 */
public class MoneyPooledObjectFactory implements PooledObjectFactory<Money> {
    /**
     * 激活对象
     *
     * @param pooledObject 池对象
     * @throws Exception 异常
     */
    @Override
    public void activateObject(PooledObject<Money> pooledObject) throws Exception {

    }

    /**
     * 销毁对象
     *
     * @param pooledObject 池对象
     * @throws Exception 异常
     */
    @Override
    public void destroyObject(PooledObject<Money> pooledObject) throws Exception {

    }

    /**
     * 使对象
     *
     * @return {@link PooledObject}<{@link Money}>
     * @throws Exception 异常
     */
    @Override
    public PooledObject<Money> makeObject() throws Exception {
        DefaultPooledObject<Money> object =
                new DefaultPooledObject<>(new Money("USD", new BigDecimal("1")));
        System.out.println("makeObject..state = " + object.getState());
        return object;
    }

    /**
     * 使钝化对象
     *
     * @param pooledObject 池对象
     * @throws Exception 异常
     */
    @Override
    public void passivateObject(PooledObject<Money> pooledObject) throws Exception {

    }

    /**
     * 验证对象
     *
     * @param pooledObject 池对象
     * @return boolean
     */
    @Override
    public boolean validateObject(PooledObject<Money> pooledObject) {
        return false;
    }

    /**
     * 销毁对象
     *
     * @param p           p
     * @param destroyMode 破坏模式
     * @throws Exception 异常
     */
    @Override
    public void destroyObject(PooledObject<Money> p, DestroyMode destroyMode) throws Exception {
        PooledObjectFactory.super.destroyObject(p, destroyMode);
    }
}
