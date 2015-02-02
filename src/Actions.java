import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


/**
 * Created by anna on 23/01/15.
 */
class Actions{
    private static final String DB_URL = "jdbc:sqlite:data/db1";
    private static final String JDBC = "org.sqlite.JDBC";

    private static final String DATE_PATTERN = "dd/MM/yyyy HH:mm";
    private static final String NOTIFY_PATTERN = "dd/MM/yyyy";

    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final DateTimeFormatter fntf = DateTimeFormatter.ofPattern(NOTIFY_PATTERN);


    private Connection conn;
    private Statement stmt;


    public Actions(){}


    public ArrayList<Note> selectNotes() throws ClassNotFoundException, SQLException{
        String sql;
        ArrayList<Note> itemList = new ArrayList<>();


        conn = null;
        Class.forName(JDBC);
        conn = DriverManager.getConnection(DB_URL);

        sql = "select * from notes order by notes.created desc;";

        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next())
            itemList.add(new Note(
                rs.getInt("id"),
                rs.getString("text"),
                LocalDateTime.parse(rs.getString("created"), dtf)));


        if (conn != null)
            conn.close();

        return itemList;
    }


    public ArrayList<Reminder> selectReminders() throws ClassNotFoundException, SQLException{
        String sql;
        ArrayList<Reminder> itemList = new ArrayList<>();


        conn = null;
        Class.forName(JDBC);
        conn = DriverManager.getConnection(DB_URL);

        sql = "select * from reminders order by reminders.created desc;";

        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next())
            try {
                itemList.add(new Reminder(
                                         rs.getInt("id"),
                                         rs.getString("text"),
                                         LocalDateTime.parse(rs.getString("created"), dtf),
                                         LocalDate.parse(rs.getString("date"), fntf)));
            }
            catch (DateTimeParseException e) {
                itemList.add(new Reminder(
                                 rs.getInt("id"),
                                 rs.getString("text"),
                                 LocalDateTime.parse(rs.getString("created"), dtf),
                                 LocalDate.parse(rs.getString("date"),
                                                    DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            }


        if (conn != null)
            conn.close();

        return itemList;
    }


    public Note selectNote(int id) throws ClassNotFoundException, SQLException{
        String sql;
        Note n = new Note();


        conn = null;
        Class.forName(JDBC);
        conn = DriverManager.getConnection(DB_URL);

        sql = String.format("select * from notes where id == %d;", id);

        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next())
            n = new Note(
                rs.getInt("id"),
                rs.getString("text"),
                LocalDateTime.parse(rs.getString("created"), dtf));


        if (conn != null)
            conn.close();

        return n;
    }


    public Reminder selectReminder(int id) throws ClassNotFoundException, SQLException{
        String sql;
        Reminder r = new Reminder();


        conn = null;
        Class.forName(JDBC);
        conn = DriverManager.getConnection(DB_URL);

        sql = String.format("select * from reminders where id == %d;", id);

        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next())
            try {
                r = new Reminder(
                                rs.getInt("id"),
                                rs.getString("text"),
                                LocalDateTime.parse(rs.getString("created"), dtf),
                                LocalDate.parse(rs.getString("date"), fntf));
            }
            catch (DateTimeParseException e) {
                r = new Reminder(
                                rs.getInt("id"),
                                rs.getString("text"),
                                LocalDateTime.parse(rs.getString("created"), dtf),
                                LocalDate.parse(rs.getString("date"),
                                                       DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }


        if (conn != null)
            conn.close();

        return r;
    }
}