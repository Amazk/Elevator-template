package Event;

import java.util.EventListener;
/**
 *  Interface d'ecoute d'evenement
 */
public interface MessageListener extends EventListener {
    /**
     * @param event messageEvent
     *  transmet le message
     */
    void messageReceived(MessageEvent event);
}
