package Event;

import java.util.EventObject;
/**
 *  Classe d'evenement de message
 */
public class MessageEvent extends EventObject {
    /**
     *  le message
     */
    private final String mess;
    /**
     * Constructeur
     * @param mess le message
     * @param source le CC
     */
    public MessageEvent(Object source, String mess) {
        super(source);
        this.mess=mess;
    }
    /**
     * @return le message du CC
     */
    public String getMess(){
        return mess;
    }
}
