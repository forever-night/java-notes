import java.sql.*;
import java.time.LocalDateTime;


/**
 * Created by anna on 18/01/15.
 */
public class Note extends Item {
    private static final String DB_URL = "jdbc:sqlite:data/db1";
    private static final String JDBC = "org.sqlite.JDBC";

    private Connection conn;
    private Statement stmt;

    private int id;
    private String input;
    private LocalDateTime dateCreated;


    public Note(){}


    public Note(String input){
        this.dateCreated = LocalDateTime.now();
        setInput(input);
    }


    public Note (int id, String input, LocalDateTime dateCreated){
        setId(id);
        setInput(input);
        setDateCreated(dateCreated);
    }


    private void setId(int id){
        this.id = id;
    }


    public int getId(){
        return this.id;
    }


    public void setInput(String input) { this.input = input; }


    public String getInput() { return this.input; }


    private void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }


    public LocalDateTime getDateCreated(){
        return this.dateCreated;
    }


    public String getTitle(){
        if (this.getInput().length() > 20)
            return this.getInput().substring(0, 20) + "...";
        else
            return this.getInput();
    }


    public void insert() throws ClassNotFoundException, SQLException{
        if (!this.getInput().equals(null)) {
            String dateCreated, sql;


            dateCreated = this.getDateCreated().format(Actions.dtf);


            conn = null;
            sql = String.format("insert into notes(text, created) " +
                                        "values('%s', '%s');",
                                        this.getInput(), dateCreated);

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
        if (!this.getInput().equals(null)){
            String sql;

            conn = null;
            sql = String.format("update notes set text = '%s' where notes.id = '%d';",
                                    this.getInput(), this.getId());

            Class.forName(JDBC);
            conn = DriverManager.getConnection(DB_URL);

            stmt = conn.createStatement();
            stmt.execute(sql);

            if (conn != null)
                conn.close();
        }
    }


    public void remove() throws  ClassNotFoundException, SQLException{
        String sql = String.format("delete from notes where notes.id = '%d';", this.getId());

        Class.forName(JDBC);
        conn = DriverManager.getConnection(DB_URL);

        stmt = conn.createStatement();
        stmt.execute(sql);

        if (conn != null)
            conn.close();
    }
}