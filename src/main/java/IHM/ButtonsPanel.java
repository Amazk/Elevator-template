package IHM;

import Event.IPanelSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *  Le panel des boutons
 */
public class ButtonsPanel extends JPanel {
    /**
     *  instance de panelSimulator pour transmettre l'evenement de click
     */
    private final IPanelSimulator panelSimulator;
    /**
     *  nombre d'etage
     */
    private final int nbFloor;
    /**
     *  Contructeur du panel
     * @param panelSimulator panelSimulator
     * @param nbFloor nombre d'etage
     */
    public ButtonsPanel(IPanelSimulator panelSimulator, int nbFloor) {
        this.panelSimulator=panelSimulator;
        this.nbFloor = nbFloor;
        setButtons();
        setPreferredSize(new Dimension(400,550));
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
    }
    /**
     *  Implemente les boutons
     */
    private void setButtons() {
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        for(int i=nbFloor;i>=0;i--) {
            if(i!=nbFloor) p1.add(createButtons("Up"+i,this::buttonClicked));
            if(i!=0) p2.add(createButtons("Down"+i, this::buttonClicked));
            p3.add(createButtons(String.valueOf(i),this::buttonClicked));
        }
        p1.setPreferredSize(new Dimension(140,400));
        p2.setPreferredSize(new Dimension(140,400));
        p2.setPreferredSize(new Dimension(140,400));
        JPanel p4 = new JPanel();
        p4.setPreferredSize(new Dimension(200,200));
        p4.add(p1);
        p4.add(p2);
        p4.add(p3);
        p4.setLayout(new BoxLayout(p4,BoxLayout.X_AXIS));
        add(p4);

        JPanel p5 = new JPanel();
        p5.setLayout(new BoxLayout(p5,BoxLayout.Y_AXIS));
        p5.setPreferredSize(new Dimension(200,200));
        p5.add(createButtons("Halt",this::buttonClicked));
        p5.add(createButtons("Reset",this::buttonClicked));
        add(p5);

    }
    /**
     *  Methode de creation de boutons
     * @param action click du bouton
     * @param name le nom (etage)
     * @return un JButton
     */
    private JButton createButtons(String name, ActionListener action) {
        JButton button = new JButton(name);
        button.addActionListener(action);
        button.setPreferredSize(new Dimension(16,16));
        return button;
    }
    /**
     *  Ecoute du click sur les boutons
     * @param event click d'un bouton
     */
    public void buttonClicked(ActionEvent event) {
        JButton b = (JButton) event.getSource();
        if(b.getText().equals("Halt"))
            b.setBackground(Color.red);
         else if(b.getText().equals("Reset"))
            b.setBackground(Color.blue);
        else b.setBackground(Color.green);
        try {
            panelSimulator.simulateButtonAction(event);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
