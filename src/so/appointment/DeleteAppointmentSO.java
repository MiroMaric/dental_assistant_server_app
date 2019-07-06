package so.appointment;

import domain.Appointment;
import domain.GeneralEntity;
import exception.DeleteEntityException;
import java.sql.SQLException;
import so.AbstractGenericOperation;

public class DeleteAppointmentSO extends AbstractGenericOperation {

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Appointment)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        try {
            databaseBroker.deleteRecord((GeneralEntity) entity);
        } catch (SQLException | DeleteEntityException ex) {
            throw new Exception("Sistem ne može da otkaže termin");
        }
    }

}
