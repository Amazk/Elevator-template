package IHM;

import Event.PanelSimulator;

import javax.swing.*;
import java.awt.*;
/**
 *  Panel principal qui contient les panel de boutons et de message
 */
public class MainPanel extends JPanel {
    /**
     *  Construteur
     * @param nbFloor nombre d'etage
     * @param panelSimulator panelSimulator
     */
    public MainPanel(PanelSimulator panelSimulator, int nbFloor) {
        setPreferredSize(new Dimension(800,800));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new ButtonsPanel(panelSimulator, nbFloor));
        add(panelSimulator.messagePanel);
        setVisible(true);
    }
}
