package IHM;

import Event.PanelSimulator;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 *  Interface principal qui contient le mainPanel
 */
public class MyFrame extends JFrame{
    /**
     *  Constructeur
     * @param nbFloor nombre d'etage
     * @param panelSimulator le simulateur
     */
    public MyFrame(PanelSimulator panelSimulator, int nbFloor) {
        super("Ascensseur");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quit();
            }
        });
        setSize(800, 800);
        getContentPane().add(new MainPanel(panelSimulator, nbFloor));
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setTitle("Ascenseur");
    }
    /**
     *  Methode qui demande avant de fermer la fenetre
     */
    private void quit() {
        int quitButton= JOptionPane.showConfirmDialog(this,
                "Are you sure you want to quit ?", "Exit",JOptionPane.YES_NO_OPTION);
        if(quitButton==JOptionPane.YES_OPTION) System.exit(0);
    }
}
