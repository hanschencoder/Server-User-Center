package site.hanschen.api.user.db;


import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.annotation.Nullable;

/**
 * @author HansChen
 */
public abstract class TransactionExecutor<T> {

    private boolean succeed = false;
    private T       result  = null;

    public TransactionExecutor() {
    }

    public final TransactionExecutor<T> execute(Session session) {
        if (session == null || !session.isOpen()) {
            throw new IllegalStateException("session is not valid");
        }
        Transaction tx = session.beginTransaction();
        try {
            result = doTransaction(session);
            tx.commit();
            succeed = true;
        } catch (Throwable t) {
            t.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
        return this;
    }

    protected abstract T doTransaction(Session session);

    public boolean isSucceed() {
        return succeed;
    }

    @Nullable
    public T getResult() {
        return result;
    }
}
