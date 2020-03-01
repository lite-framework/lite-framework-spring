package cc.lite.framework.component;


import cc.lite.framework.type.Callback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chen.ce
 */

public class TransactionHelper {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Object requireTransaction(Callback callback) {
        return callback.exec();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Object newTransaction(Callback callback) {
        return callback.exec();
    }
}

