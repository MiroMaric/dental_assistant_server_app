package so.patient;

import domain.GeneralEntity;
import domain.Patient;
import exception.UpdateEntityException;
import java.sql.SQLException;
import so.AbstractGenericOperation;

public class UpdatePatientSO extends AbstractGenericOperation {

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Patient)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        try {
            databaseBroker.updateRecord((GeneralEntity) entity);
        } catch (SQLException | UpdateEntityException ex) {
            throw new Exception("Sistem ne može da izmeni podatke o pacijentu");
        }
    }

}
