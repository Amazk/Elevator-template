package elevator;

import elevatorSimulator.ElevatorSimulator;
import elevatorSimulator.IElevator;
import elevatorSimulator.PanelSimulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestControlCommand {
    private ControlCommand controlCommand;
    private final int nbFloor = 10;
    private final Scheduler scheduler = new Scheduler();
    private final IElevator elevator = new ElevatorSimulator(nbFloor, false);
    private final PanelSimulator panel = new PanelSimulator(nbFloor);
    @BeforeEach
    public void setUp() throws InterruptedException {
        controlCommand = new ControlCommand(scheduler, elevator, panel, nbFloor, 0, Direction.UP);
    }
    @Test
    public void testSaveRequests() {
        panel.getAndResetInitButton();
        panel.pressUpButton(3);
        panel.pressUpButton(4);
        panel.pressDownButton(1);
        panel.pressDownButton(5);
        panel.pressFloorButton(7);
        panel.pressFloorButton(9);
        controlCommand.saveRequests();
        assertEquals(List.of(3,4), controlCommand.getUpRequests());
        assertEquals(List.of(7,9), controlCommand.getCabinRequests());
        assertEquals(List.of(1,5), controlCommand.getDownRequests());
    }
    @Test
    public void testCheckAndProcess () throws InterruptedException {
        testSaveRequests();
        assertEquals(List.of(), controlCommand.getAllDownRequests());
        assertEquals(List.of(3,4,7,9), controlCommand.getAllUpRequests());

        controlCommand.checkAndProcess();

        assertEquals(1, controlCommand.getActualFloor());
    }
}
