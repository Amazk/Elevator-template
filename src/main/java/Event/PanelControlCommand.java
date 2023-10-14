package Event;

import elevator.IControlCommand;

import java.awt.event.ActionEvent;
/**
 *  Classe qui va faire la liaison entre le panelSimulator et le CC
 */
public class PanelControlCommand {
    /**
     *  instance du CC
     */
    private final IControlCommand controlCommand;
    /**
     *  Constructeur
     * @param controlCommand le CC
     */
    public PanelControlCommand(IControlCommand controlCommand) {
        this.controlCommand=controlCommand;
    }
    /**
     *  Transmet l'evenement au CC
     * @param event le click d'un bouton
     */
    public void handlePanelEvent(ActionEvent event) throws InterruptedException {
        controlCommand.processPanelEvent(event);
    }
}
