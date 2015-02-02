import javax.swing.*;
import javax.swing.border.LineBorder;


/**
 * Created by anna on 27/01/15.
 */
class GuiBottomPanel extends JPanel{
    protected JButton btnAdd = new JButton("Add");


    public GuiBottomPanel(){
        super();

        setLayout(null);
        setVisible(true);
        setBounds(0, Gui.panelHeight - 70, 200, 70);

        btnAdd.setBounds(50, 15, 100, 40);
        btnAdd.setBackground(Gui.androidGreen);
        btnAdd.setForeground(Gui.lightGrey);
        btnAdd.setBorder(new LineBorder(Gui.androidGreen, 2));

        add(btnAdd);
    }
}