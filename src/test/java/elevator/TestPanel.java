package elevator;

import elevatorSimulator.PanelSimulator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPanel {

    PanelSimulator p;

    @Test
    public void testUpButton() throws InterruptedException {
        System.out.println("Test starting...");
        p = new PanelSimulator(4);
        p.pressUpButton(1);
        //System.out.println(p.getMessage());
        assertEquals(true, p.getAndResetUpButton(1)); // on vérifie si le bouton UP1 est activé
        assertNotEquals(true, p.getAndResetUpButton(1)); // après RESET on vérifie s'il l'est plus

    }

    @Test
    public void testDownButton() throws InterruptedException{
        p = new PanelSimulator(4);
        p.pressDownButton(1);
        assertEquals(true, p.getAndResetDownButton(1)); // on vérifie si le bouton UP1 est activé
        assertNotEquals(true, p.getAndResetDownButton(1)); // après RESET on vérifie s'il l'est plus

    }

    @Test
    public void testInitButton() throws InterruptedException{
        p = new PanelSimulator(4);
        p.pressInitButton();
        assertEquals(true, p.getAndResetInitButton());
        assertNotEquals(true, p.getAndResetInitButton());
    }

    @Test
    public void testFloorButton() throws InterruptedException{
        p = new PanelSimulator(4);
        p.pressFloorButton(2);
        assertEquals(true, p.getAndResetFloorButton(2));
        assertNotEquals(true, p.getAndResetFloorButton(2));
    }

    @Test
    public void testStopButton() throws InterruptedException{
        p = new PanelSimulator(4);
        p.pressStopButton();
        assertEquals(true, p.getAndResetStopButton());
        assertNotEquals(true, p.getAndResetStopButton());
    }

    @Test
    public void testLights() throws InterruptedException{
        p = new PanelSimulator(4);

        p.setUpLight(2, true);
        // vérification si UP2 est allumé
        assertTrue(p.getUpLight(2));
        //p.getAndResetUpButton(2);

        p.setDownLight(3, true);
        assertTrue(p.getDownLight(3));

        p.setFloorLight(2, true);
        assertTrue(p.getFloorLight(2));
    }
}
