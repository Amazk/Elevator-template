package elevator;


import elevatorSimulator.ElevatorSimulator;
import elevatorSimulator.PanelSimulator;
import org.junit.jupiter.api.Test;

import static elevatorSimulator.State.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAutomaticElevator {

	@Test
	public void testIPanel() throws Exception {
		var p = new PanelSimulator(3);

		p.pressUpButton(3);

	}
	/*
	@Test
	public void testElevator() throws Exception {
		var s = new ElevatorSimulator(3, false);
		IElevator e = s;
		int n = 0;
		e.openDoor();
		e.up();
		while (e.getState() == UP) {
			Thread.sleep(1000);
			if(e.getAndResetStageSensor()) n++;
			if(n==2) e.stopNext();
		}
		System.out.println(e.getState());
		s.stopSimulator();
       /*

       assertEquals(3.0, e.getLevel());
       assertEquals(ERROR, e.getState());
       assertEquals("-S0-U0-U1-U2-U3-E3", e.getEvents());
	}
	*/
	@Test
	public void testAutomaticElevator() throws Exception {
		// 3 étages en mode automatique
		var e = new ElevatorSimulator(3, false);
		// activer la montée
		e.up();
		// surveiller l'évolution de l'ascenseur
		while (e.getState() == UP) {
			System.out.printf("level = %3.2f\n", e.getLevel());
			Thread.sleep(100);
		}
		e.stopSimulator();

		// l'ascenseur est au 3ème
		assertEquals(3.0, e.getLevel());
		// l'ascenseur est en erreur
		assertEquals(ERROR, e.getState());
		// les étapes
		assertEquals("-S0-U0-U1-U2-U3-E3", e.getEvents());
	}

	@Test
	public void testStepByStepElevator() throws Exception {
		// 3 étages en mode automatique
		var e = new ElevatorSimulator(3, true);
		// activer la montée
		e.up();

		//compteur d'étages
		double n = 0;

		// surveiller l'évolution de l'ascenseur
		while (e.getState() == UP) {
			e.oneStep();
			if(n == 2){
				e.stopNext();
				n += 0.1;
			}

			System.out.printf("level = %3.2f\n", e.getLevel());

			if(e.getAndResetStageSensor()){
				System.out.println("Vous venez de franchir un palier");
				n += 1;
			}
		}
		e.stopSimulator();

		// l'ascenseur est au 3ème
		assertEquals(3.0, e.getLevel());
		// les portes sont ouvertes
		assertEquals(OPEN, e.getState());
		// les étapes
		assertEquals("-S0-U0-U1-U2-O3", e.getEvents());
	}

	@Test
	public void testDoors() throws Exception{
		var e = new ElevatorSimulator(3, true);

		e.openDoor();
		assertEquals(OPEN, e.getState());

		while (e.getState() == OPEN){
			e.oneStep();
		}

		assertEquals(STOP,e.getState());


		//System.out.println(e.getState());
		e.up();
		e.stopNext();
		while (e.getState() == UP){
			e.oneStep();
		}

		assertEquals(1, e.getLevel());

		//assertEquals(0, e.getLevel());
	}

	@Test
	public void test111(){
		ElevatorSimulator elevator = new ElevatorSimulator(10, false);
		elevator.reset();
		while (elevator.getState() == RESET){
			System.out.println("Initialisation...");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		System.out.println(elevator.getState());
	}

}
