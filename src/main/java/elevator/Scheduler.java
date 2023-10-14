package elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * Représente l'ordonnanceur, elle enregistre les requetes et calcul le prochain etage
 */
public class Scheduler {
    /**
     * List d'entier qui Stock les requetes en dehors de la cabine vers le haut
     */
    private final List<Integer> upRequests = new ArrayList<>();
    /**
     * List d'entier qui Stock les requetes en dehors de la cabine vers le bas
     */
    private final List<Integer> downRequests = new ArrayList<>();
    /**
     * List d'entier qui Stock les requetes de la cabine
     */
    private final List<Integer> cabinRequests = new ArrayList<>();
    /**
     * @return la list des requetes en dehors de la cabine vers le haut
     */
    public List<Integer> getUpRequests() {
        return upRequests;
    }
    /**
     * @return la list des requetes en dehors de la cabine vers le bas
     */
    public List<Integer> getDownRequests() {
        return downRequests;
    }
    /**
     * @return la list des requetes de la cabine
     */
    public List<Integer> getCabinRequests() {
        return cabinRequests;
    }
    /**
     * @param actualFloor etage actuel
     * @return la list de toutes les requetes vers le haut qui ont un etage superieur a actualFloor
     */
    public List<Integer> getAllUpRequests(int actualFloor) {
        var allUpRequests = new ArrayList<>(upRequests);
        allUpRequests.addAll(cabinRequests);
        allUpRequests.removeIf(floor -> floor < actualFloor);
        return allUpRequests;
    }
    /**
     * @param actualFloor etage actuel
     * @return la list de toutes les requetes vers le bas qui ont un etage inferieur a actualFloor
     */
    public List<Integer> getAllDownRequests(int actualFloor) {
        var allDownRequests = new ArrayList<>(downRequests);
        allDownRequests.addAll(cabinRequests);
        allDownRequests.removeIf(floor -> floor > actualFloor);
        return allDownRequests;
    }
    /**
     * @return true s'il y a des requetes sinon false
     */
    public boolean hasRequests() {
        return !upRequests.isEmpty() || !downRequests.isEmpty() || !cabinRequests.isEmpty();
    }
    /**
     * Supprime toutes les requetes
     */
    public void removeAllRequests() {
        upRequests.clear();
        downRequests.clear();
        cabinRequests.clear();
    }
    /**
     * Supprime toutes les requetes de l'etage floor
     * @param floor etage
     */
    public void removeRequests(int floor) {
        upRequests.remove((Integer) floor);
        downRequests.remove((Integer) floor);
        cabinRequests.remove((Integer) floor);
    }
    /**
     * @param floorWhereHeIs etage
     * Ajoute une requetes en dehors de la cabine vers le haut a upRequests
     */
    public void addUpRequest(int floorWhereHeIs) {
        upRequests.add(floorWhereHeIs);
    }
    /**
     * @param floorWhereHeIs etage
     * Ajoute une requetes en dehors de la cabine vers le bas a downRequests
     */
    public void addDownRequest(int floorWhereHeIs) {
        downRequests.add(floorWhereHeIs);
    }
    /**
     * @param floorToGo etage
     * Ajoute une requetes de la cabine a cabinRequests
     */
    public void addCabinRequest(int floorToGo) {
        cabinRequests.add(floorToGo);
    }
    /**
     * @param actualFloor etage actuel
     * @param direction direction
     * @return le prochain etage
     */
    public int calculateNextFloor(int actualFloor, Direction direction) {
        int nextFloor;
        switch (direction) {
            case UP -> {
                var allUpRequests = getAllUpRequests(actualFloor);
                nextFloor = allUpRequests.isEmpty() ? -1 : Collections.min(allUpRequests, Comparator.naturalOrder());
                if(nextFloor==-1) {
                    var downRequests = getDownRequests();
                    downRequests.removeIf(floor -> floor < actualFloor);
                    nextFloor = downRequests.isEmpty() ? -1 : Collections.min(downRequests, Comparator.naturalOrder());
                }
                return nextFloor;
            }
            case DOWN -> {
                var allDownRequests = getAllDownRequests(actualFloor);
                nextFloor = allDownRequests.isEmpty() ? -1 : Collections.max(allDownRequests, Comparator.naturalOrder());
                if(nextFloor==-1) {
                    var upRequests = getUpRequests();
                    upRequests.removeIf(floor -> floor > actualFloor);
                    nextFloor = upRequests.isEmpty() ? -1 : Collections.max(upRequests, Comparator.naturalOrder());
                }
                return nextFloor;
            }
            default -> {
                return -1;
            }
        }
    }
}