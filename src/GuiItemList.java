import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Created by anna on 30/01/15.
 */
public class GuiItemList<T extends Item> extends JList{
    public GuiItemList(ArrayList<T> list){
        super(list.toArray());

        setLayout(null);
        setEnabled(true);

        setFixedCellHeight(50);
        setFixedCellWidth(193);
        setPreferredSize(new Dimension(200, list.size() * getFixedCellHeight()));

        setListData(list.toArray());
        setCellRenderer(new GuiItemCellRenderer<T>());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        setBackground(Gui.darkGrey);
        setForeground(Gui.lightGrey);

        setSelectionBackground(Gui.androidGreen);
        setSelectionForeground(Gui.androidGreen);
    }
}