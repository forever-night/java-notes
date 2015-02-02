import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Created by anna on 18/01/15.
 */
public class Reminder extends Item {
    private static final String DB_URL = "jdbc:sqlite:data/db1";
    private static final String JDBC = "org.sqlite.JDBC";

    private Connection conn;
    private Statement stmt;

    private int id;
    private String input;
    private LocalDate dateNotify;
    private LocalDateTime dateCreated;


    public Reminder(){}


    public Reminder(String input, LocalDate dateNotify){
        this.dateCreated = LocalDateTime.now();
        setInput(input);
        setDateNotify(dateNotify);
    }


    public Reminder(int id, String input, LocalDateTime dateCreated, LocalDate dateNotify) {
        setId(id);
        setInput(input);
        setDateCreated(dateCreated);
        setDateNotify(dateNotify);
    }


    private void setId(int id) {this.id = id;}


    public int getId() {return this.id;}


    public void setInput(String input) { this.input = input; }


    public String getInput() { return this.input; }


    private void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }


    public LocalDateTime getDateCreated() { return this.dateCreated; }


    public void setDateNotify(LocalDate dateNotify) { this.dateNotify = dateNotify; }


    public LocalDate getDateNotify() { return this.dateNotify; }


    public String getTitle(){
        if (this.getInput().length() > 20)
            return this.getInput().substring(0, 20) + "...";
        else
            return this.getInput();
    }


    public void insert() throws ClassNotFoundException, SQLException{
        if (!this.getInput().equals(null) || !this.getDateNotify().equals(null)) {
            String dateCreated, dateNotify, sql;


            dateCreated = this.getDateCreated().format(Actions.dtf);
            dateNotify = this.getDateNotify().format(Actions.fntf);


            conn = null;
            sql = String.format("insert into reminders(text, created, date) " +
                                        "values('%s', '%s', '%s');",
                                        this.getInput(), dateCreated, dateNotify);

            Class.forName(JDBC);
            conn = DriverManager.getConnection(DB_URL);

            stmt = conn.createStatement();
            stmt.execute(sql);

            ResultSet rs = stmt.getGeneratedKeys();
            int id = (int)rs.getLong(1);

            this.setId(id);

            if (conn != null)
                conn.close();
        }
    }


    public void update() throws ClassNotFoundException, SQLException{
        if (!this.getInput().equals(null)) {
            String sql;

            conn = null;
            sql = String.format("update reminders set text = '%s', date = '%s' " +
                                        "where reminders.id = '%d';",
                                        this.getInput(), this.getDateNotify(), this.getId());

            Class.forName(JDBC);
            conn = DriverManager.getConnection(DB_URL);

            stmt = conn.createStatement();
            stmt.execute(sql);

            if (conn != null)
                conn.close();
        }
    }


    public void remove() throws ClassNotFoundException, SQLException{
        String sql = String.format("delete from reminders where reminders.id = '%d';",
                                          this.getId());

        Class.forName(JDBC);
        conn = DriverManager.getConnection(DB_URL);

        stmt = conn.createStatement();
        stmt.execute(sql);

        if(conn != null)
            conn.close();
    }
}