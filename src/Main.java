import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;


/**
 * Created by anna on 20/01/15.
 */
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        start();

        Gui.main(args);
    }


    private static void start() throws ClassNotFoundException, SQLException{
        Connection conn;
        Statement stmt;

        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:data/db1");

        stmt = conn.createStatement();
        stmt.execute("create table if not exists 'notes'('id' integer primary key," +
                             "'text' text," +
                             "'created' text);");

        stmt.execute("create table if not exists 'reminders'('id' integer primary key," +
                             "'text' text," +
                             "'created' text," +
                             "'date' text);");

        if (conn != null)
            conn.close();
    }
}