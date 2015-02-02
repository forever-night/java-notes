import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;


/**
 * Created by anna on 27/01/15.
 */
class GuiEditorPanel extends JPanel {
    protected JButton btnCancel = new JButton("Cancel"),
                    btnSave = new JButton("Save");

    protected JTextArea txtEditor = new JTextArea();

    protected JCheckBox chkBox = new JCheckBox("notify me on: ");

    protected JFormattedTextField dateTxtField;


    private MaskFormatter formatter;


    public GuiEditorPanel(){
        super();

        setLayout(null);
        setBounds(200, 0, Gui.windowWidth - 200, Gui.panelHeight);


        txtEditor.setPreferredSize(new Dimension(400, 230));
        txtEditor.setBackground(Gui.lightGrey);
        txtEditor.setBorder(new LineBorder(Gui.androidGreen, 2));

        btnCancel.setSize(Gui.btnWidth, Gui.btnHeight);
        btnCancel.setBackground(Gui.androidGreen);
        btnCancel.setForeground(Gui.lightGrey);
        btnCancel.setBorder(new LineBorder(Gui.androidGreen, 2));

        btnSave.setSize(Gui.btnWidth, Gui.btnHeight);
        btnSave.setBackground(Gui.androidGreen);
        btnSave.setForeground(Gui.lightGrey);
        btnSave.setBorder(new LineBorder(Gui.androidGreen, 2));

        chkBox.setSize(130, Gui.btnHeight);
        chkBox.setBackground(Gui.darkDarkGrey);
        chkBox.setForeground(Gui.lightGrey);


        try{
            formatter = new MaskFormatter("##/##/####");
            formatter.setPlaceholderCharacter('0');
            formatter.setAllowsInvalid(false);
            formatter.setOverwriteMode(true);
        }
        catch(ParseException e) {
            e.printStackTrace();
        }


        dateTxtField = new JFormattedTextField(formatter);
        dateTxtField.createToolTip();
        dateTxtField.setToolTipText("dd/MM/yyyy");

        dateTxtField.setSize(Gui.btnWidth, Gui.btnHeight);
        dateTxtField.setBorder(new LineBorder(Gui.androidGreen, 2));
        dateTxtField.setBackground(Gui.lightGrey);
        dateTxtField.setVisible(false);


        // TODO add scroll
        add(txtEditor).setBounds(
                                20, 40,
                                txtEditor.getPreferredSize().width,
                                txtEditor.getPreferredSize().height);


        add(btnCancel).setBounds(
                                20,
                                Gui.panelHeight - btnCancel.getHeight() - 20,
                                btnCancel.getWidth(),
                                btnCancel.getHeight()
        );


        add(btnSave).setBounds(
                              getWidth() - btnSave.getWidth() - 20,
                              Gui.panelHeight - btnSave.getHeight() - 20,
                              btnSave.getWidth(),
                              btnSave.getHeight()
        );


        add(chkBox).setBounds(
                             20,
                             txtEditor.getHeight() + 50,
                             chkBox.getWidth(),
                             chkBox.getHeight()
        );


        add(dateTxtField).setBounds(
                                   chkBox.getWidth() + 20,
                                   txtEditor.getHeight() + 50,
                                   dateTxtField.getWidth(),
                                   dateTxtField.getHeight()
        );


        chkBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chkBox.isSelected())
                    dateTxtField.setVisible(true);
                else
                    dateTxtField.setVisible(false);
            }
        });
    }
}
