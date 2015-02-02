import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by anna on 30/01/15.
 */
class GuiItemCellRenderer<T extends Item> extends JLabel implements ListCellRenderer<T> {
    @Override
    public Component getListCellRendererComponent(
                                                    JList<? extends T> list,
                                                    T value,
                                                    int index,
                                                    boolean isSelected,
                                                    boolean cellHasFocus) {

        setText(value.getTitle());

        setFont(list.getFont());
        setVisible(list.isVisible());
        setEnabled(list.isEnabled());

        // TODO background doesnt change
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            setBorder(new LineBorder(Gui.lightGrey));
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setBorder(null);
        }

        return this;
    }
}
