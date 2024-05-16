/*
 *	This code is property of the Seaside High School Robotics team, C.Y.B.O.R.G. Seagulls.
 *	Used for FIRST FRC Deep Space 2019 
 *	Alexia M. Walgren 02.09.19
 */

package org.usfirst.frc.team3673.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.TimedRobot;
//import org.usfirst.frc.team3673.robot.Pipeline;

public class Robot extends TimedRobot {

	// Define joysticks
	public Joystick red = new Joystick(1);
	public Joystick black = new Joystick(0);

	// Define joystick buttons for red
	public JoystickButton redTrigger = new JoystickButton(red, 1);
	public JoystickButton redTwo = new JoystickButton(red, 2);
	public JoystickButton redThree = new JoystickButton(red, 3);
	public JoystickButton redFour = new JoystickButton(red, 4);
	public JoystickButton redFive = new JoystickButton(red, 5);
	public JoystickButton redSix = new JoystickButton(red, 6);
	public JoystickButton redSeven = new JoystickButton(red, 7);
	public JoystickButton redEight = new JoystickButton(red, 8);
	public JoystickButton redNine = new JoystickButton(red, 9);
	public JoystickButton redTen = new JoystickButton(red, 10);
	public JoystickButton redEleven = new JoystickButton(red, 11);
	public JoystickButton redTwelve = new JoystickButton(red, 12);

	// Define joystick buttons for black
	public JoystickButton blackTrigger = new JoystickButton(black, 1);
	public JoystickButton blackTwo = new JoystickButton(black, 2);
	public JoystickButton blackThree = new JoystickButton(black, 3);
	public JoystickButton blackFour = new JoystickButton(black, 4);
	public JoystickButton blackFive = new JoystickButton(black, 5);
	public JoystickButton blackSix = new JoystickButton(black, 6);
	public JoystickButton blackSeven = new JoystickButton(black, 7);
	public JoystickButton blackEight = new JoystickButton(black, 8);
	public JoystickButton blackNine = new JoystickButton(black, 9);
	public JoystickButton blackTen = new JoystickButton(black, 10);
	public JoystickButton blackEleven = new JoystickButton(black, 11);
	public JoystickButton blackTwelve = new JoystickButton(black, 12);

	// Create AxisCamera
	AxisCamera aCam;
	AxisCamera bCam;

	// Boolean for whether or not encode.reset is used in auto
	public boolean once = false;

	// Assign a port to the encoder
	public Encoder encode = new Encoder(0, 1, false, Encoder.EncodingType.k4X);

	// Set wheel size
	public double wheelDiameter = 5.98;

	// Get number of rotations since last reset
	public int count = encode.get();

	// Get distance per pulse
	public double distancePerRotation = encode.getDistancePerPulse();

	// Use this formula to get a predicted distance for the next pulse
	public double distance = encode.getDistance();

	// Assign PWM ports to the spark motor controllers
	public Spark right = new Spark(1);
	public Spark left = new Spark(0);
	public Spark definitelyTheArmBoy = new Spark(2);

	// Define groups to be used for driving
	public DifferentialDrive drive = new DifferentialDrive(left, right);


	@Override
	public void robotInit() {

		// Define the Axis Camera
		aCam = CameraServer.getInstance().addAxisCamera("aCam", "10.36.73.54");
		bCam = CameraServer.getInstance().addAxisCamera("bCam", "10.36.73.56");

		// Initiate automatic capture
		CameraServer.getInstance().startAutomaticCapture(aCam);
		CameraServer.getInstance().startAutomaticCapture(bCam);

	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {

		// Reset rotation count
		encode.reset();

		// Enable motor safety
		drive.setSafetyEnabled(true);

		// Set distance per pulse based on wheel circumference (in inches)
		encode.setDistancePerPulse((wheelDiameter * 3.14159265358979323846264) / 2048);


	}

	@Override
	public void autonomousPeriodic() {

		// Create int hatPosition to show if hat is pressed up or down
		int hatPosition = black.getPOV();

		// If hat is pressed down
		if (hatPosition > 95 && hatPosition < 265){

			// Set actuator to go down at 0.2 speed
			definitelyTheArmBoy.set(-0.7);

		}


		// If hat is pressed up
		else if (hatPosition > 0 && hatPosition < 85 || hatPosition > 275){

			// Set actuator to go up at 0.2 speed
			definitelyTheArmBoy.set(0.7);
		
		}

		// If hat is neither pressed up nor down
		else {

			definitelyTheArmBoy.set(0.0);
			//System.out.println("NOTHING");

		}

		// Set up tank drive based on joystick inputs
		drive.tankDrive(red.getY(), black.getY());
		
	}

	@Override
	public void teleopInit() {

		// Reset rotation count
		encode.reset();

		// Set distance per pulse based on wheel circumference (in inches)
		encode.setDistancePerPulse((wheelDiameter * 3.14159265358979323846264) / 2048);

	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		encode.setReverseDirection(false);

	// Create int hatPosition to show if hat is pressed up or down
	int hatPosition = black.getPOV();

	// If hat is pressed down
	if (hatPosition > 95 && hatPosition < 265){

		// Set actuator to go down at 0.2 speed
		definitelyTheArmBoy.set(-0.7);

	}


	// If hat is pressed up
	else if (hatPosition > 0 && hatPosition < 85 || hatPosition > 275){

		// Set actuator to go up at 0.2 speed
		definitelyTheArmBoy.set(0.7);

	}

	// If hat is neither pressed up nor down
	else {

		definitelyTheArmBoy.set(0.0);
		//System.out.println("NOTHING");

	}


	// Define drive type as tank drive (two joysticks)
	drive.tankDrive(red.getY(), black.getY());


	}

	@Override
	public void testPeriodic() {
	}
}

// .- .-.. . -..- .. .-    -- .- -.-- .
// -- .- -. ..- . .-..    .. ...   .-   -.. --- .-. -.-
