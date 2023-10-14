package elevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestScheduler {
    private final int actualFloor = 0;
    @Test
    public void testAddUpRequest() {
        Scheduler s = new Scheduler();
        s.addUpRequest(3);
        assertTrue(s.getAllUpRequests(actualFloor).contains(3));
    }
    @Test
    public void testAddDownRequest() {
        Scheduler s = new Scheduler();
        s.addDownRequest(1);
        assertTrue(s.getAllDownRequests(actualFloor).contains(1));
    }
    @Test
    public void testAddCabinRequest() {
        Scheduler s = new Scheduler();
        s.addCabinRequest(4);
        assertTrue(s.getAllUpRequests(actualFloor).contains(4));
    }
    @Test
    public void testRemoveRequests() {
        Scheduler s = new Scheduler();
        s.addUpRequest(3);
        s.addDownRequest(3);
        s.addCabinRequest(3);
        s.removeRequests(3);
        assertTrue(s.getAllUpRequests(actualFloor).isEmpty());
        assertTrue(s.getAllDownRequests(actualFloor).isEmpty());
    }
    @Test
    public void testCalculateNextFloorUp() {
        Scheduler s = new Scheduler();
        s.addUpRequest(5);
        s.addUpRequest(3);
        s.addDownRequest(4);
        s.addDownRequest(1);
        s.addCabinRequest(3);
        assertEquals(3, s.calculateNextFloor(2, Direction.UP));
        assertEquals(5, s.calculateNextFloor(3, Direction.UP));
        assertEquals(4, s.calculateNextFloor(5, Direction.DOWN));
        assertEquals(1, s.calculateNextFloor(4, Direction.DOWN));
        assertEquals(-1, s.calculateNextFloor(3, Direction.DOWN));
        assertEquals(-1, s.calculateNextFloor(3, Direction.UP));
    }
    @Test
    public void testCalculateNextFloorDown() {
        Scheduler s = new Scheduler();
        s.addDownRequest(1);
        s.addDownRequest(4);
        assertEquals(4, s.calculateNextFloor(3, Direction.DOWN));
        assertEquals(1, s.calculateNextFloor(4, Direction.DOWN));
    }
    @Test
    public void testCalculateNextFloorEmpty() {
        Scheduler s = new Scheduler();
        assertEquals(-1, s.calculateNextFloor(0, Direction.UP));
    }
    @Test
    public void testRemoveAllRequests() {
        Scheduler s = new Scheduler();
        s.addUpRequest(5);
        s.addDownRequest(3);
        s.addCabinRequest(4);
        s.removeAllRequests();
        assertTrue(s.getAllUpRequests(actualFloor).isEmpty());
        assertTrue(s.getAllDownRequests(actualFloor).isEmpty());
        assertTrue(s.getAllUpRequests(actualFloor).isEmpty());
    }
}
