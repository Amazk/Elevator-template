package IHM;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
/**
 *  Panel de message
 */
public class MessagePanel extends JPanel {
    /**
     *  Le textArea qui va contenir les messages du CC
     */
    private final JTextArea textArea = new JTextArea(15,74);
    /**
     * Constructeur
     */
    public MessagePanel() {
        setLayout(new FlowLayout());
        setPanel();
        setBackground(Color.BLACK);
    }
    /**
     *  Set le JScrollpane dans le messagePanel
     */
    public void setPanel() {
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        add(scrollPane);
    }
    /**
     *  Affiche le message du CC dans le TextArea
     * @param mess le message a afficher
     */
    public void printMess(String mess) {
        textArea.append("           "+mess+ "\n");
    }
}
