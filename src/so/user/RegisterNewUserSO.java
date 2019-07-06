package so.user;

import domain.GeneralEntity;
import domain.User;
import exception.InsertEntityException;
import java.sql.SQLException;
import so.AbstractGenericOperation;

public class RegisterNewUserSO extends AbstractGenericOperation {

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof User)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        try {
            databaseBroker.insertRecord((GeneralEntity) entity);
        } catch (SQLException | InsertEntityException ex) {
            throw new Exception("Sistem ne može da registruje korisnika");
        }
    }

}
