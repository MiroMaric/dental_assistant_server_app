package so.tooth_root_state;

import domain.GeneralEntity;
import domain.tooth.ToothRootState;
import java.sql.SQLException;
import java.util.List;
import so.AbstractGenericOperation;

public class GetAllToothRootStatesSO extends AbstractGenericOperation {

    private List<GeneralEntity> toothRootStates;

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof ToothRootState)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        try {
            toothRootStates = databaseBroker.getAllRecord(new ToothRootState());
        } catch (SQLException ex) {
            throw new Exception("Sistem ne može da učita stanja korena zuba");
        }
    }

    public List<GeneralEntity> getList() {
        return toothRootStates;
    }
}
