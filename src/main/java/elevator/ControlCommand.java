package elevator;

import Event.MessageEvent;
import Event.MessageListener;
import elevatorSimulator.IElevator;
import elevatorSimulator.IPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import static elevatorSimulator.State.*;
/**
 * Le systeme Controle Commande de l'ascenseur
 */
public class ControlCommand extends Thread implements IControlCommand{
    /**
     * Scheduler
     */
    private final Scheduler s;
    /**
     * IElevator
     */
    private final IElevator e;
    /**
     * IPanel
     */
    private final IPanel p;
    /**
     * L'etage actuel
     */
    private int actualFloor;
    /**
     * Nombre d'etage
     */
    private final int numberOfFloor;
    /**
     * Direction
     */
    private Direction direction;
    /**
     * Parametre de la boucle while de l'ascenseur
     */
    private final boolean isRunning = true;
    /**
     * Le prochain etage
     */
    private int nextFloor;
    /**
     * MessageListener
     */
    private MessageListener messageListener;
    /**
     * Les boutons qui ont ete cliques
     */
    private final List<JButton> buttonClicked = new ArrayList<>();
    /**
     * Contructeur
     * @param s scheduler
     * @param e elevator
     * @param p panel
     * @param nbFloor nombre d'etage
     * @param actualFloor etage actuel
     * @param direction direction de l'ascenseur
     */
    public ControlCommand(Scheduler s, IElevator e, IPanel p, int nbFloor,int actualFloor, Direction direction){
        this.s=s;
        this.e=e;
        this.p=p;
        this.numberOfFloor=nbFloor;
        this.actualFloor=actualFloor;
        this.direction= direction;
    }
    /**
     * Mets en place le MessageListener
     * @param listener l'ecouteur
     */
    @Override
    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }
    /**
     * @return la liste des requetes en dehors de la cabine vers le haut
     */
    public List<Integer> getUpRequests() {
        return s.getUpRequests();
    }
    /**
     * @return la liste des requetes en dehors de la cabine vers le bas
     */
    public List<Integer> getDownRequests() {
        return s.getDownRequests();
    }
    /**
     * @return la liste des requetes de la cabine
     */
    public List<Integer> getCabinRequests() {
        return s.getCabinRequests();
    }
    /**
     * @return la liste de toutes les requetes vers le haut qui ont un etage > actualFloor
     */
    public List<Integer> getAllUpRequests() {
        return s.getAllUpRequests(actualFloor);
    }
    /**
     * @return la liste de toutes les requetes vers le bas qui ont un etage < actualFloor
     */
    public List<Integer> getAllDownRequests() {
        return s.getAllDownRequests(actualFloor);
    }
    /**
     * Choisit quelles sont les demandes prioritaire et choisit la prochaine direction
     */
    private void choosePriority() {
        if(!getCabinRequests().isEmpty()) {
            if(getCabinRequests().get(0)==actualFloor) {
                getCabinRequests().remove(0);
                direction=Direction.NONE;
                return;
            }
            setDirection(getCabinRequests().get(0));
        }
        else if(getUpRequests().size() >= getDownRequests().size()){
            if(getUpRequests().isEmpty()) {
                direction = Direction.NONE;
                return;
            }
            if(getUpRequests().get(0)==actualFloor) {
                getUpRequests().remove(0);
                direction = Direction.NONE;
                return;
            }
            setDirection(getUpRequests().get(0));
        }

        else {
            if(getDownRequests().get(0)==actualFloor) {
                getDownRequests().remove(0);
                direction = Direction.NONE;
                return;
            }
            setDirection(getDownRequests().get(0));
        }
    }
    /**
     * Set la prochain direction
     * @param floor l'etage cible
     */
    private void setDirection(int floor) {
        if(floor > actualFloor) direction = Direction.UP;
        else if(floor < actualFloor) direction = Direction.DOWN;
    }
    /**
     * @return l'etage actuel
     */
    public int getActualFloor() {
        return actualFloor;
    }
    /**
     * Enregistre les requetes
     */
    @Override
    public void saveRequests() {
        for (int i = 0; i < numberOfFloor; i++) {
            if(p.getAndResetUpButton(i)) s.addUpRequest(i);
            if(p.getAndResetDownButton(i)) s.addDownRequest(i);
            if(p.getAndResetFloorButton(i)) s.addCabinRequest(i);
        }
    }
    /**
     * Supprime toutes les requetes
     */
    @Override
    public void removeAllRequests() {
        s.removeAllRequests();
    }
    /**
     * Supprime toutes les requetes de l'etage floor
     * @param floor etage
     */
    @Override
    public void removeRequests(int floor) {
        s.removeRequests(floor);
    }
    /**
     * Methode qui affiche dans le terminal/IHM
     * @param message le message a afficher
     */
    @Override
    public void print(String message) {
        //System.out.println(message);                                                                          // Affiche dans la console
        if(messageListener!=null) messageListener.messageReceived(new MessageEvent(this,message));      // Affiche dans l'interface graphique
    }
    /**
     * Methode d'arret d'urgence
     */
    @Override @SuppressWarnings("BusyWait")
    public void emergency() throws InterruptedException {
        s.removeAllRequests();
        synchronized (this) {
            for(JButton b : buttonClicked) b.setBackground(Color.LIGHT_GRAY);
            buttonClicked.clear();
        }
        var count = 0;
        while (e.getState()==ERROR) {
            if(count%5==0)  print("En attente a l'etage "+actualFloor +" d'un reset...");
            Thread.sleep(1000);
            count++;
        }
    }
    /**
     * Methode de reset
     */
    @Override@SuppressWarnings("BusyWait")
    public void reset() throws InterruptedException {
        s.removeAllRequests();
        while (e.getState()==RESET) {
            Thread.sleep(2000);
        }
        actualFloor = 0;
        direction = Direction.NONE;
        synchronized (this) {
            for(JButton b : buttonClicked) b.setBackground(Color.LIGHT_GRAY);
            buttonClicked.clear();
        }
        print("Fin du reset, Ascenseur a l'etage: "+actualFloor);
    }
    /**
     * Methode qui fait s'arreter l'ascenseur au prochain etage
     */
    @Override
    public void stopAtNextFloor() {
        if (e.getState() == UP || e.getState() == DOWN) {
            e.stopNext();
        }
    }
    /**
     * Methode d'ouverture des portes
     */
    @Override @SuppressWarnings("BusyWait")
    public void openDoors() throws InterruptedException {
        while (e.getState() == OPEN) {
            print("Les portes sont ouvertes au niveau "+actualFloor);
            Thread.sleep(2800);
        }
        print("Les portes sont fermees");
        Thread.sleep(500);
        synchronized (this) {
            var buttons = new ArrayList<JButton>();
            removeRequests(actualFloor);
            for (JButton b : buttonClicked) {
                if (b.getText().endsWith(String.valueOf(actualFloor))) {
                    buttons.add(b);
                    b.setBackground(Color.LIGHT_GRAY);
                }
            }
            buttonClicked.removeAll(buttons);
        }
    }
    /**
     * Methode de monter
     */
    @Override
    public void up() {
        if (nextFloor == actualFloor + 1) stopAtNextFloor();
        if(e.getAndResetStageSensor()) {
            actualFloor++;
            print("Direction: "+direction+ " | Niveau: "+actualFloor);
        }
    }
    /**
     * Methode de descente
     */
    @Override
    public void down() {
        if (nextFloor == actualFloor - 1) stopAtNextFloor();
        if (e.getAndResetStageSensor()) {
            actualFloor--;
            print("Direction: "+direction +" | Niveau: "+actualFloor);
        }
    }
    /**
     * Methode d'arret d'etage
     */
    @Override @SuppressWarnings("BusyWait")
    public void stopp() throws InterruptedException {
        var count = 0;
        while (!hasRequests()){
            if(e.getState()==RESET || e.getState()==ERROR) break;
            if(count%5==0) print("En attente a l'etage "+actualFloor+" d'une requete...");
            Thread.sleep(1000);
            saveRequests();
            count++;
        }
        choosePriority();
        switch (direction) {
            case UP -> e.up();
            case DOWN -> e.down();
            default -> {
                if(e.getState()==ERROR) break;
                e.openDoor();
                openDoors();
            }
        }
    }
    /**
     * @return true s'il y a des requetes sinon false
     */
    private boolean hasRequests() {
        return s.hasRequests();
    }
    /**
     * Methode principal qui control les deplacements de l'ascenseur suivant son etat actuel
     */
    @Override
    public void checkAndProcess() throws InterruptedException {
        print("Demarrage de l'ascenseur au niveau "+actualFloor);
        while(isRunning){
            saveRequests();
            nextFloor = s.calculateNextFloor(actualFloor, direction);
            switch (e.getState()) {
                case UP -> up();
                case DOWN -> down();
                case OPEN -> openDoors();
                case STOP -> stopp();
                case ERROR -> emergency();
                case RESET -> reset();
            }
        }
    }
    /**
     * Methode qui enregistre les demandes via une ecoute des boutons de l'interface
     * @param event le click d'un bouton
     */
    @Override
    public void processPanelEvent(ActionEvent event) {
        JButton b = (JButton) event.getSource();
        if(b.getText().startsWith("Up")) {
            s.addUpRequest(Integer.parseInt(b.getText().substring(2)));
        } else if(b.getText().startsWith("Down")) {
            s.addDownRequest(Integer.parseInt(b.getText().substring(4)));
        } else if(b.getText().equals("Halt")) {
            e.halt();
            print("Arret d'urgence !");
        } else if(b.getText().equals("Reset")) {
            if(e.getState() == STOP ||e.getState() == ERROR) {
                print("Reset de l'ascenseur en cours...");
                e.reset();
            } else print("Reset de l'ascenseur possible seulement dans l'etat ERROR ou STOP");
        } else
            s.addCabinRequest(Integer.parseInt(b.getText()));
        synchronized (this) {buttonClicked.add(b);}
    }
    /**
     * Execute checkAndProcess()
     */
    @Override
    public void run() {
        try {
            checkAndProcess();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
