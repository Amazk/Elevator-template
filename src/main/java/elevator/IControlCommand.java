package elevator;

import Event.MessageListener;

import java.awt.event.ActionEvent;
/**
 * L'interface du CC
 */
public interface IControlCommand {
    /**
     * Enregistre les requetes
     */
    void saveRequests();
    /**
     * Methode principal qui control les deplacements de l'ascenseur suivant son etat actuel
     */
    void checkAndProcess() throws InterruptedException;
    /**
     * Supprime toutes les requetes
     */
    void removeAllRequests();
    /**
     * Supprime toutes les requetes de l'etages floor
     * @param floor etage
     */
    void removeRequests(int floor);
    /**
     * Methode d'arret d'urgence
     */
    void emergency() throws InterruptedException;
    /**
     * Methode de reset
     */
    void reset() throws InterruptedException;
    /**
     * Methode qui fait s'arreter l'ascenseur au prochain etage
     */
    void stopAtNextFloor();
    /**
     * Methode d'affichage
     * @param message le message a afficher
     */
    void print(String message);
    /**
     * Methode d'ouverture de porte
     */
    void openDoors() throws InterruptedException;
    /**
     * Methode qui enregistre les demandes via une ecoute des boutons de l'interface
     * @param event le click d'un bouton
     */
    void processPanelEvent(ActionEvent event) throws InterruptedException;
    /**
     * Mets en place le MessageListener
     * @param listener l'ecouteur
     */
    void setMessageListener(MessageListener listener);
    /**
     * Methode de monter
     */
    void up();
    /**
     * Methode de descente
     */
    void down();
    /**
     * Methode d'arret d'etage
     */
    void stopp() throws InterruptedException;
}
