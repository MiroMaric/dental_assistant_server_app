package so;

import storage.database.IDatabaseBroker;
import storage.database.DatabaseBroker;
import storage.database.connection.DatabaseConnection;

public abstract class AbstractGenericOperation {

    protected IDatabaseBroker databaseBroker;

    public AbstractGenericOperation() {
        databaseBroker = new DatabaseBroker();
    }

    public final void templateExecute(Object entity) throws Exception {
        try {
            validate(entity);
            try {
                startTransaction();
                execute(entity);
                commitTransaction();
            } catch (Exception ex) {
                rollbackTransaction();
                throw ex;
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            throw ex;
        }
    }

    protected abstract void validate(Object entity) throws Exception;

    private void startTransaction() throws Exception {
        DatabaseConnection.getInstance().startTransaction();
    }

    protected abstract void execute(Object entity) throws Exception;

    private void commitTransaction() throws Exception {
        DatabaseConnection.getInstance().commitTransaction();
    }

    private void rollbackTransaction() throws Exception {
        DatabaseConnection.getInstance().rollbackTransaction();
    }

}
