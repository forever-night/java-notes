import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;


/**
 * Created by anna on 27/01/15.
 */
class Gui extends JFrame{
    static final int windowWidth = 640,
                                windowHeight = 400,
                                panelHeight = windowHeight - 30,
                                btnWidth = 100,
                                btnHeight = 30;


    static final Color darkGrey = new Color(87, 105, 121),
                            androidGreen = new Color(163, 191, 53),
                            lightGrey = new Color(228, 235, 238),
                            darkDarkGrey = new Color(49, 62, 73);


    private JPanel rightPanel = new JPanel();
    private GuiTabPanel leftPanel = new GuiTabPanel();
    private GuiViewerPanel viewerPanel = new GuiViewerPanel();
    private GuiEditorPanel addPanel = new GuiEditorPanel(),
                            editorPanel = new GuiEditorPanel();

    private Actions actions = new Actions();


    public Gui(){
        rightPanel.setLayout(null);
        rightPanel.setBounds(leftPanel.getWidth(), 0,
                                windowWidth - leftPanel.getWidth(), panelHeight);


        add(leftPanel);
        add(rightPanel);
        GuiBottomPanel bottomPanel = new GuiBottomPanel();
        add(bottomPanel);
        add(editorPanel).setVisible(false);
        add(addPanel).setVisible(false);
        add(viewerPanel).setVisible(false);


        leftPanel.setBackground(darkDarkGrey);
        bottomPanel.setBackground(darkDarkGrey);
        rightPanel.setBackground(darkDarkGrey);
        addPanel.setBackground(darkDarkGrey);
        editorPanel.setBackground(darkDarkGrey);
        viewerPanel.setBackground(darkDarkGrey);


        bottomPanel.btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (!e.getSource().equals(addPanel)) {
                    rightPanel.setVisible(false);
                    viewerPanel.setVisible(false);
                    editorPanel.setVisible(false);
                    addPanel.setVisible(true);
                }

                editorPanel.txtEditor.setText("");
                addPanel.dateTxtField.setValue("");
                addPanel.chkBox.setSelected(false);
                addPanel.dateTxtField.setVisible(false);

                leftPanel.notesList.clearSelection();
                leftPanel.remindersList.clearSelection();
            }
        });


        editorPanel.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                removeEditorPanel(editorPanel);
                viewerPanel.setVisible(true);
            }
        });


        addPanel.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                removeEditorPanel(addPanel);
                rightPanel.setVisible(true);
            }
        });


        addPanel.btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Item item;

                if (addPanel.chkBox.isSelected() && editorPanel.dateTxtField.getValue() != "")
                    item = new Reminder(
                                   addPanel.txtEditor.getText(),
                                   LocalDate.parse(addPanel.dateTxtField.getText(), Actions.fntf));
                else
                    item = new Note(addPanel.txtEditor.getText());


                try {
                    item.insert();
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }


                if (item instanceof Note) {
                    leftPanel.bindNotes();
                    leftPanel.notesList.setSelectedIndex(0);
                }
                else{
                    leftPanel.bindReminders();
                    leftPanel.remindersList.setSelectedIndex(0);
                }
            }
        });


        editorPanel.btnSave.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int index = -1;
                Item item;

                if (editorPanel.chkBox.isSelected() && editorPanel.dateTxtField.getValue() != ""){
                    index = leftPanel.remindersList.getSelectedIndex();

                    Reminder r = (Reminder)leftPanel.remindersList.getSelectedValue();

                    r.setInput(editorPanel.txtEditor.getText());
                    r.setDateNotify(LocalDate.parse(
                                                   editorPanel.dateTxtField.getText(),
                                                   Actions.fntf));
                    item = r;
                }
                else {
                    index = leftPanel.notesList.getSelectedIndex();

                    Note n = (Note)leftPanel.notesList.getSelectedValue();
                    n.setInput(editorPanel.txtEditor.getText());
                    item = n;
                }


                try {
                    item.update();
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }


                if (item instanceof Note) {
                    leftPanel.bindNotes();
                    leftPanel.notesList.setSelectedIndex(index);
                }
                else {
                    leftPanel.bindReminders();
                    leftPanel.remindersList.setSelectedIndex(index);
                }
            }
        });


        viewerPanel.btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                viewerPanel.setVisible(false);
                editorPanel.setVisible(true);

                editorPanel.txtEditor.setText(viewerPanel.txtPane.getText());

                if (viewerPanel.lblNotify.getText().equals("")) {
                        editorPanel.chkBox.setSelected(false);
                        editorPanel.chkBox.setEnabled(false);
                        editorPanel.dateTxtField.setValue("");
                        editorPanel.dateTxtField.setVisible(false);
                }
                else {
                    editorPanel.chkBox.setEnabled(true);
                    editorPanel.chkBox.setSelected(true);
                    editorPanel.dateTxtField.setVisible(true);
                    editorPanel.dateTxtField.setValue(viewerPanel.lblNotify.getText());
                }
            }
        });


        viewerPanel.btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Object confirm = JOptionPane.showConfirmDialog(
                                                         viewerPanel.btnDelete,
                                                         "really delete?",
                                                         "delete",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.QUESTION_MESSAGE);

                if (confirm.equals(JOptionPane.YES_OPTION)){
                    if (!viewerPanel.lblNotify.getText().equals("")) {
                        Reminder r = (Reminder) leftPanel.remindersList.getSelectedValue();

                        try {
                            r.remove();
                        } catch (ClassNotFoundException | SQLException e1) {
                            e1.printStackTrace();
                        }

                        leftPanel.bindReminders();
                    }
                    else {
                        Note n = (Note) leftPanel.notesList.getSelectedValue();

                        try {
                            n.remove();
                        } catch (ClassNotFoundException | SQLException e1) {
                            e1.printStackTrace();
                        }

                        leftPanel.bindNotes();
                    }

                    removeViewerPanel();
                    rightPanel.setVisible(true);
                }
                else {
                    removeEditorPanel(editorPanel);
                    viewerPanel.setVisible(true);
                }
            }
        });


        leftPanel.notesList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                leftPanel.remindersList.clearSelection();

                GuiItemList list = (GuiItemList)e.getSource();

                if (list.getSelectedIndex() >= 0)
                    showItem((Note)list.getSelectedValue());
            }
        });


        leftPanel.remindersList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e){
                leftPanel.notesList.clearSelection();

                GuiItemList list = (GuiItemList)e.getSource();

                if (list.getSelectedIndex() >= 0)
                    showItem((Reminder) list.getSelectedValue());
            }
        });
    }


    public static void main(String[] args) throws ParseException{
        run(new Gui());
    }


    private static void run(final JFrame jf){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                jf.setTitle(jf.getClass().getSimpleName());
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                jf.setSize(windowWidth, windowHeight);
                jf.setVisible(true);
                jf.setLayout(null);
            }
        });
    }


    private void removeEditorPanel(final GuiEditorPanel panel){
        panel.txtEditor.setText("");
        panel.chkBox.setSelected(false);
        panel.dateTxtField.setValue("");
        panel.setVisible(false);
    }


    private void removeViewerPanel() {
        viewerPanel.txtPane.setText("");
        viewerPanel.lblCreated.setText("");
        viewerPanel.lblNotify.setText("");
        viewerPanel.setVisible(false);
    }


    private void showItem(Item item){
        rightPanel.setVisible(false);

        if(editorPanel.isVisible())
            removeEditorPanel(editorPanel);

        if (addPanel.isVisible())
            removeEditorPanel(addPanel);

        viewerPanel.setVisible(true);
        viewerPanel.lblNotify.setVisible(true);


        if (item instanceof Note) {
            try {
                item = actions.selectNote(item.getId());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            viewerPanel.lblNotify.setText("");
        }
        else if (item instanceof Reminder) {
            Reminder r = null;

            try {
                r = actions.selectReminder(item.getId());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            if (r != null)
                viewerPanel.lblNotify.setText(r.getDateNotify().format(Actions.fntf));
        }

        viewerPanel.txtPane.setText(item.getInput());
        viewerPanel.lblCreated.setText(item.getDateCreated().format(Actions.dtf));
    }
}