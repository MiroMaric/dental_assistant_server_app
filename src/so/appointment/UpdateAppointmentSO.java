package so.appointment;

import domain.Appointment;
import domain.GeneralEntity;
import exception.UpdateEntityException;
import java.sql.SQLException;
import so.AbstractGenericOperation;

public class UpdateAppointmentSO extends AbstractGenericOperation {

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Appointment)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        try {
            databaseBroker.updateRecord((GeneralEntity) entity);
        } catch (SQLException | UpdateEntityException ex) {
            throw new Exception("Sistem ne može da izmeni zakazani termin");
        }
    }

}
