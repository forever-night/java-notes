import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


/**
 * Created by anna on 27/01/15.
 */
class GuiViewerPanel extends JPanel{
    protected JTextPane txtPane = new JTextPane();

    protected JButton btnEdit = new JButton("Edit"),
                        btnDelete = new JButton("Delete");

    protected JLabel lblCreated = new JLabel(),
                        lblNotify = new JLabel();


    public GuiViewerPanel(){
        super();

        setLayout(null);
        setBounds(200, 0, Gui.windowWidth - 200, Gui.panelHeight);


        txtPane.setEditable(false);
        txtPane.setPreferredSize(new Dimension(400, 230));
        txtPane.setBorder(new LineBorder(Gui.darkGrey, 2));
        txtPane.setBackground(Gui.darkGrey);
        txtPane.setForeground(Gui.lightGrey);


        lblCreated.setSize(130, Gui.btnHeight);
        lblCreated.setForeground(Gui.lightGrey);

        lblNotify.setSize(130, Gui.btnHeight);
        lblNotify.setForeground(Gui.lightGrey);

        btnEdit.setSize(Gui.btnWidth, Gui.btnHeight);
        btnEdit.setBackground(Gui.androidGreen);
        btnEdit.setForeground(Gui.lightGrey);
        btnEdit.setBorder(new LineBorder(Gui.androidGreen, 2));

        btnDelete.setSize(Gui.btnWidth, Gui.btnHeight);
        btnDelete.setBackground(Gui.androidGreen);
        btnDelete.setForeground(Gui.lightGrey);
        btnDelete.setBorder(new LineBorder(Gui.androidGreen, 1));


        // TODO add scroll
        add(txtPane).setBounds(
                              20, 40,
                              txtPane.getPreferredSize().width,
                              txtPane.getPreferredSize().height);


        add(lblCreated).setBounds(
                                 20, txtPane.getHeight() + 50,
                                 lblCreated.getWidth(),
                                 lblCreated.getHeight()
        );


        add(lblNotify).setBounds(
                                20, txtPane.getHeight() + lblCreated.getHeight() + 60,
                                lblNotify.getWidth(),
                                lblNotify.getHeight()
        );


        add(btnEdit).setBounds(
                              getWidth() - btnEdit.getWidth() - 20,
                              txtPane.getHeight() + 50,
                              btnEdit.getWidth(),
                              btnEdit.getHeight()
        );


        add(btnDelete).setBounds(
                                getWidth() - btnDelete.getWidth() - 20,
                                txtPane.getHeight() + btnEdit.getHeight() + 60,
                                btnDelete.getWidth(),
                                btnDelete.getHeight()
        );
    }
}