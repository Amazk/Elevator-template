package Event;

import IHM.MessagePanel;

import java.awt.event.ActionEvent;
/**
 *  Classe d'ecoute qui va liée le CC <-> IHM
 */
public class PanelSimulator implements MessageListener,IPanelSimulator{
    /**
     *  instance de PanelControlCommand pour transmettre au CC
     */
    private final PanelControlCommand panelControlCommand;
    /**
     * instance de MessagePanel pour transmettre a IHM
     */
    public final MessagePanel messagePanel;
    /**
     *  Constructeur
     * @param messagePanel le panel de message du CC
     * @param panelControlCommand le panelCC
     */
    public PanelSimulator(PanelControlCommand panelControlCommand, MessagePanel messagePanel) {
        this.panelControlCommand = panelControlCommand;
        this.messagePanel = messagePanel;
    }
    /**
     * Transmet l'evenement au panelcontrolcommand
     * @param event click bouton
     */
    @Override
    public void simulateButtonAction(ActionEvent event) throws InterruptedException {
        panelControlCommand.handlePanelEvent(event);
    }
    /**
     * transmet le message a IHM
     * @param event messageEvent
     */
    @Override
    public void messageReceived(MessageEvent event) {
        messagePanel.printMess(event.getMess());
    }
}
