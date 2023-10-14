package Event;

import java.awt.event.ActionEvent;
/**
 * Interface d'ecoute qui va liée le CC <-> IHM
 */
public interface IPanelSimulator {
    /**
     * @param event click d'un bouton
     *  Transmet l'evenement au panelcontrolcommand
     */
    void simulateButtonAction(ActionEvent event) throws InterruptedException;
}
