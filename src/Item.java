import java.sql.SQLException;
import java.time.LocalDateTime;


/**
 * Created by anna on 20/01/15.
 */
abstract class Item {
    public abstract int getId();

    public abstract String getTitle();

    public abstract String getInput();

    public abstract LocalDateTime getDateCreated();

    public abstract void insert() throws ClassNotFoundException, SQLException;

    public abstract void update() throws ClassNotFoundException, SQLException;

    public abstract void remove() throws ClassNotFoundException, SQLException;
}