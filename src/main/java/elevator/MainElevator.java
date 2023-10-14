package elevator;

import Event.PanelControlCommand;
import Event.PanelSimulator;
import IHM.MessagePanel;
import IHM.MyFrame;
import elevatorSimulator.ElevatorSimulator;
import elevatorSimulator.IElevator;
import elevatorSimulator.IPanel;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
/**
 * Classe Main qui execute le CC et l'IHM
 */
public class MainElevator {
	private static final int nbFloor = 15;
	private static final int actualFloor = 0;
	private static final elevator.Direction direction = Direction.NONE;
	private static final Scheduler s = new Scheduler();
	private static final IElevator e = new ElevatorSimulator(nbFloor, false);
    private static final IPanel p = new elevatorSimulator.PanelSimulator(nbFloor);
    private static final MessagePanel messagePanel = new MessagePanel();
    private static final ControlCommand CC = new ControlCommand(s,e,p,nbFloor,actualFloor,direction);
	private static final PanelSimulator panelSimulator = new PanelSimulator(new PanelControlCommand(CC),messagePanel);
	/**
	 * main qui execute le CC et l'IHM
	 */
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		CC.setMessageListener(panelSimulator);
		SwingUtilities.invokeLater(MainElevator::MyFrameRun);
		CC.start();
	}
	/**
	 * IHM
	 */
	private static void MyFrameRun() {
		new MyFrame(panelSimulator, nbFloor);
	}
}
