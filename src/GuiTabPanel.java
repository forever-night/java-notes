import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by anna on 29/01/15.
 */
class GuiTabPanel extends JPanel{
    protected JPanel notesPanel = new JPanel(),
                        remindersPanel = new JPanel();

    protected JTabbedPane tabbedPane = new JTabbedPane();

    protected GuiItemList<Note> notesList;
    protected GuiItemList<Reminder> remindersList;

    protected ArrayList<Note> noteArrayList;
    protected ArrayList<Reminder> reminderArrayList;


    public GuiTabPanel(){
        setLayout(null);
        setBounds(0, 0, 200, Gui.panelHeight - 70);

        notesPanel.setLayout(null);
        notesPanel.setBackground(Gui.darkGrey);

        remindersPanel.setLayout(null);
        remindersPanel.setBackground(Gui.darkGrey);

        tabbedPane.setBounds(0, 0, getWidth(), getHeight());
        tabbedPane.setBackground(Gui.androidGreen);

        tabbedPane.addTab("Notes",
                        new JScrollPane(
                                       notesPanel,
                                       ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                       ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));

        tabbedPane.addTab("Reminders",
                        new JScrollPane(
                                       remindersPanel,
                                       ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                       ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));


        add(tabbedPane);

        bindNotes();
        bindReminders();
    }


    protected void bindNotes(){
        if (notesPanel.getComponentCount() != 0) {
            notesPanel.removeAll();
            bindNotes();
        }
        else {
            ListSelectionListener[] listSelectionListeners = null;

            if (notesList != null)
                listSelectionListeners = notesList.getListSelectionListeners();

            noteArrayList = populateNotesList();
            notesList = new GuiItemList<>(noteArrayList);

            if (listSelectionListeners != null)
                notesList.addListSelectionListener(listSelectionListeners[0]);

            notesPanel.setPreferredSize(
                                       new Dimension(
                                                    getWidth(),
                                                    notesList.getPreferredSize().height));

            notesPanel.add(notesList).setBounds(
                                               0, 0,
                                               notesList.getPreferredSize().width,
                                               notesList.getPreferredSize().height);
        }
    }


    protected void bindReminders(){
        if (remindersPanel.getComponentCount() != 0) {
            remindersPanel.removeAll();
            bindReminders();
        }
        else {
            ListSelectionListener[] listSelectionListeners = null;

            if (remindersList != null)
                listSelectionListeners = remindersList.getListSelectionListeners();

            reminderArrayList = populateReminderList();
            remindersList = new GuiItemList<>(reminderArrayList);

            if (listSelectionListeners != null)
                remindersList.addListSelectionListener(listSelectionListeners[0]);

            remindersPanel.setPreferredSize(
                                           new Dimension(
                                                        getWidth(),
                                                        remindersList.getPreferredSize().height));

            remindersPanel.add(remindersList).setBounds(
                                                       0, 0,
                                                       remindersList.getPreferredSize().width,
                                                       remindersList.getPreferredSize().height);
        }
    }


    private ArrayList<Note> populateNotesList() {
        Actions actions = new Actions();

        ArrayList<Note> list = null;

        try {
            list = actions.selectNotes();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    private ArrayList<Reminder> populateReminderList(){
        Actions actions = new Actions();

        ArrayList<Reminder> list = null;

        try {
            list = actions.selectReminders();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}