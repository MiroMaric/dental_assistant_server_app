package so.tooth_state;

import domain.GeneralEntity;
import domain.tooth.ToothState;
import java.sql.SQLException;
import java.util.List;
import so.AbstractGenericOperation;

public class GetAllToothStatesSO extends AbstractGenericOperation {

    private List<GeneralEntity> toothStates;

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof ToothState)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        try {
            toothStates = databaseBroker.getAllRecord(new ToothState());
        } catch (SQLException ex) {
            throw new Exception("Sistem ne može da učita stanja zuba");
        }
    }

    public List<GeneralEntity> getList() {
        return toothStates;
    }
}
