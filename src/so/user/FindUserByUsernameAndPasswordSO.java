package so.user;

import domain.GeneralEntity;
import domain.User;
import exception.EntityNotFoundException;
import java.sql.SQLException;
import so.AbstractGenericOperation;

public class FindUserByUsernameAndPasswordSO extends AbstractGenericOperation {

    private GeneralEntity user;

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof User)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object entity)throws Exception{
        try {
            user = databaseBroker.findRecord((User) entity);
        } catch (SQLException ex) {
            throw new Exception("Greška na strani servera");
        } catch (EntityNotFoundException ex) {
            throw new Exception("Pogrešno korisničko ime ili šifra");
        }
    }

    public GeneralEntity getValue() {
        return user;
    }

}
