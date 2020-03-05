/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.links.SPILink;

//import java.util.Map;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;

  //pixy shtuff 
  Pixy2 pixy = Pixy2.createInstance(new SPILink());

  //auto distance 
  private int autoDistance; 


  //color sensor 
  public final static String colorOrder = "RGBYRG" ;


  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  private final ColorMatch m_colorMatcher = new ColorMatch();

  public Spark colorWheel = new Spark(6);
 
  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

//xbox buttons
  private static XboxController xController = new XboxController(0);
  private static Button xContRightTrigger = new JoystickButton(xController, 8);
  private static Button xContLeftTrigger = new JoystickButton(xController, 7);

  private static Button xContElvaUp = new JoystickButton(xController, 4);
  private static Button xContElvaDown = new JoystickButton(xController, 2);

  private static Button autoColorSpin = new JoystickButton(xController, 10);





  //test pixy2 code 
  /*
  private JoystickButton lampRedButton; 
  private JoystickButton lampGreenButton;
  private JoystickButton lampBlueButton;
  private JoystickButton lampHighButton;
  private JoystickButton lampLowButton;
  */

  //xbox buttons 
  private JoystickButton intakePower;
  private JoystickButton dislodgePower;

  public static Button xContBallOutput = new JoystickButton(xController, 1);
  public static Button xContBallOutputRev = new JoystickButton(xController, 3);

  boolean xboxRightPressed;

  boolean xboxLeftPressed; 

  boolean xboxElvaUp;

  boolean xboxElvaDown;

  boolean intakeButton;

  boolean dislodgeButton;

  boolean outPutButton;

  boolean readyToShoot;

  boolean reverseDrive;

  //boolean colorBlock = false;

  enum autoStateType {moveForward, shoot, moveBackward};
  autoStateType autoState;

  boolean haveGameData;

  //private final SuppliedValueWidget colorWidget = Shuffleboard.getTab("Color").addBoolean ("current color", ()-> haveGameData);


  long startTime;
  
  boolean outPutRevButton;



  //encoder shtuff
  public Encoder leftEncoder = new Encoder(6, 7, false, Encoder.EncodingType.k4X);
  public Encoder rightEncoder = new Encoder(8, 9, false, Encoder.EncodingType.k4X);
  
  //final number for wheel diameter 
  private double wheelDiameter = 7.56;
  
  //four motor drive train 
  private Spark leftDrive;
  private Spark rightDrive;
  private Spark leftDriveTwo;
  private Spark rightDriveTwo;
  SpeedControllerGroup m_left;
  SpeedControllerGroup m_right;

  //sparks will need to be organised 
  public Spark elvaLift;

  private Spark upperIntake;
  private Spark lowerIntake;

  //auto code
  //private boolean moveForward;

  private int autoMode = 0;

  private double colorWheelSpeed;

  
  private SendableChooser<Integer>autoCommand;
  
  UsbCamera Camera1; 
  UsbCamera Camera2;
  

  @Override
  public void robotInit() {

    m_leftStick = new Joystick(1);
    m_rightStick = new Joystick(2);

    //pixy2 tests don't add to new code
    /*
   lampRedButton = new JoystickButton(m_leftStick, 7);
   lampGreenButton = new JoystickButton(m_leftStick, 6);
   lampBlueButton = new JoystickButton(m_leftStick, 5);
   lampHighButton = new JoystickButton(m_leftStick, 8);
   lampLowButton = new JoystickButton(m_leftStick, 9);
   */

   //power cell stuff
   intakePower = new JoystickButton(m_rightStick, 1);
   dislodgePower = new JoystickButton(m_leftStick, 1);

   //once again cleaning up code for sparks needs to happen 
    elvaLift = new Spark(7);

    leftDrive = new Spark(0);
    leftDrive.setInverted(true);
 
    leftDriveTwo = new Spark(1);
    leftDriveTwo.setInverted(true);

    //four motor drive train
    m_left = new SpeedControllerGroup(leftDrive, leftDriveTwo);

    //sparks
    rightDrive = new Spark(3);
    rightDrive.setInverted(true);

    rightDriveTwo = new Spark(2);
    rightDriveTwo.setInverted(true);

    //four motor drive train
    m_right = new SpeedControllerGroup(rightDrive, rightDriveTwo);

    //auto stuff 
    leftEncoder.setReverseDirection(true);


    m_myRobot = new DifferentialDrive(m_left, m_right);

    //spark clean up
    upperIntake = new Spark(4);

    lowerIntake = new Spark(5);

    //color sensor code
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget); 

    //pixy2 code don't add to new code
   
   

    
    autoCommand = new SendableChooser<Integer>();
		autoCommand.setDefaultOption("Move to low goal", 1);
		autoCommand.addOption("Move to low goal and shoot", 2);
    autoCommand.addOption("No auto", 3);
    
    SmartDashboard.putData("Autonomous Selector", autoCommand);
    

    //life cam code lol
    Camera1 = CameraServer.getInstance().startAutomaticCapture(0);
    Camera2 = CameraServer.getInstance().startAutomaticCapture(1);


    haveGameData = false;

    reverseDrive = false;
  }

  public void autonomousInit() {

    //distance from init line to alliance wall in inches minus Length of robot (120-34) 86
    m_myRobot.setSafetyEnabled(false);

    autoDistance = 86;

    
    leftEncoder.reset();

    rightEncoder.reset();

    leftEncoder.setDistancePerPulse(wheelDiameter * Math.PI / 2048.);

    rightEncoder.setDistancePerPulse(wheelDiameter * Math.PI / 2048);

    //moveForward = true;

    readyToShoot = false;

    autoMode = (int) autoCommand.getSelected();

    autoState = autoStateType.moveForward;

  }
  public void autonomousPeriodic() {

    double leftSpeed = 0.0;
    double rightSpeed = 0.0;

    /*
    if (moveForward){
      leftSpeed = -0.3;
      rightSpeed = 0.3;
      if (leftEncoder.getDistance() > autoDistance) {
        leftSpeed = 0.0;
        rightSpeed = 0.0;
        moveForward = false;

    }
    */

    //SmartDashboard.putNumber("autoMode", autoMode);


    switch(autoMode) {

      case 1 :
      //Driving 40 inches then stopping
      //System.out.println(autoMode);
        if (rightEncoder.getDistance() < autoDistance) {
          leftSpeed = -0.3;
          rightSpeed = 0.3;
        } else {
          leftSpeed = 0.0;
          rightSpeed = 0.0;
        }
        break;
      case 2 :
      //Drives forward 40 inches, stops and then shoots for three seconds
        if (autoState == autoStateType.moveForward) {
          leftSpeed = -0.3;
          rightSpeed = 0.3;
        
          if (rightEncoder.getDistance() > autoDistance) {
            startTime = System.currentTimeMillis();
            leftSpeed = 0.0;
            rightSpeed = 0.0;
            autoState = autoStateType.shoot;
          }
        } 
        if (autoState == autoStateType.shoot) {
          upperIntake.set(-0.35);
          lowerIntake.set(-0.35);
          if (System.currentTimeMillis() - startTime > 3000){
            upperIntake.set(0.0);
            lowerIntake.set(0.0);
            autoState = autoStateType.moveBackward;
          }
        } 
        if (autoState == autoStateType.moveBackward){
          leftSpeed = 0.3;
          rightSpeed = -0.3;
          if (rightEncoder.getDistance() < -36.0){ //Length of the robot plus 2 inches 
            leftSpeed = 0.0;
            rightSpeed = 0.0;
          }

        }
          
          
           break;

           

           case 3 :
      //Do nothing

      }

      m_left.set(leftSpeed);
      m_right.set(rightSpeed);

//probably won't add to new code
    //SmartDashboard.putNumber("leftEncoder", leftEncoder.getDistance());
    SmartDashboard.putNumber("rightEncoder", rightEncoder.getDistance());

    //SmartDashboard.putNumber("leftEncoderTicks", leftEncoder.get());
    SmartDashboard.putNumber("rightEncoderTicks", rightEncoder.get());
  }

   @Override
  public void teleopInit() {
    // TODO Auto-generated method stub
    //super.teleopInit();
    m_myRobot.setSafetyEnabled(true);
  }
  @Override
  public void teleopPeriodic() {
    if (m_rightStick.getThrottle() > 0.0) {
      reverseDrive = false;
    } else {
      reverseDrive = true;
    }
    if (reverseDrive) {
     m_myRobot.tankDrive(-m_rightStick.getY(), -m_leftStick.getY());
    } else {
     m_myRobot.tankDrive(m_leftStick.getY(), m_rightStick.getY());
    }
    final Color detectedColor = m_colorSensor.getColor();

    final double IR = m_colorSensor.getIR();

    //get rid of most likely 
    //SmartDashboard.putNumber("POV", xController.getPOV());

    //don't know what to put at the moment. Will decide later if it needs to be deleted 
    
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
    
    

    final int proximity = m_colorSensor.getProximity();

    //keep
    SmartDashboard.putNumber("Proximity", proximity);

    final String colorString;
    final ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    boolean robotSeesRed = false;
    boolean robotSeesGreen = false;
    boolean robotSeesBlue = false;
    boolean robotSeesYellow = false;

    /*
    boolean fieldSeesRed = false;
    boolean fieldSeesGreen = false;
    boolean fieldSeesBlue = false;
    boolean fieldSeesYellow = false;
    */

    colorWheelSpeed = 0.0;
    
    xboxRightPressed = xContRightTrigger.get();
    xboxLeftPressed = xContLeftTrigger.get();

    xboxElvaUp = xContElvaUp.get();
    xboxElvaDown = xContElvaDown.get();

    intakeButton = intakePower.get();
    dislodgeButton = dislodgePower.get();

    outPutButton = xContBallOutput.get();
    outPutRevButton = xContBallOutputRev.get();

    if (xboxRightPressed == true && xboxLeftPressed == true) {
      colorWheelSpeed = 0.0;
    } else if (xboxLeftPressed == true) {
      colorWheelSpeed = -0.5;
    } else if (xboxRightPressed == true) {
      colorWheelSpeed = 0.5;
    }else {
      colorWheelSpeed = 0.0;
    }

    if (xboxElvaUp == true && xboxElvaDown == true) {
      elvaLift.set(0.0);
    } else if (xboxElvaUp == true) {
      elvaLift.set(0.5);
    } else if (xboxElvaDown == true) {
      elvaLift.set(-0.5);
    }else {
      elvaLift.set(0.0);
    }
     
    
    if (intakeButton == true) {
      upperIntake.set(0.5);
      lowerIntake.set(-0.5);
    } else if (dislodgeButton == true) {
      upperIntake.set(-0.5);
      lowerIntake.set(0.5);
    } else if (outPutRevButton == true) {
      upperIntake.set(-0.3);
      lowerIntake.set(0.0);
    } else if (outPutButton == true) {
      upperIntake.set(-0.4);
      lowerIntake.set(-0.5);
    } else {
      upperIntake.set(0.0);
      lowerIntake.set(0.0);
    }
    

    if (match.color == kBlueTarget) {
      colorString = "Blue";
      SmartDashboard.putBoolean("isBlue", robotSeesBlue);
      //colorWidget.withProperties("colorWhenTrue", Blue);
      robotSeesBlue = true;
    } else if (match.color == kRedTarget) {
      colorString = "Red";
        SmartDashboard.putBoolean("isRed", robotSeesRed);
     // colorWidget.withProperties(Map.of("colorWhenTrue", kRedTarget));
      robotSeesRed = true;
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
      SmartDashboard.putBoolean("isGreen", robotSeesGreen);
     // colorWidget.withProperties(Map.of("colorWhenTrue", kGreenTarget));
      robotSeesGreen = true;
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
      SmartDashboard.putBoolean("isYellow", robotSeesYellow);
     // colorWidget.withProperties(Map.of("colorWhenTrue", kYellowTarget));
      robotSeesYellow = true;
    } else {
      colorString = "Unknown";
    }

    //colorWidget.withProperties(Map.of("colorWhenTrue", match.color));
    
    //keep (color block code)
    
    SmartDashboard.putBoolean("isRed", robotSeesRed);
    SmartDashboard.putBoolean("isYellow", robotSeesYellow);
    SmartDashboard.putBoolean("isBlue", robotSeesBlue);
    SmartDashboard.putBoolean("isGreen", robotSeesGreen);
    

  

    //get rid of most likely 
    //SmartDashboard.putBoolean("elvaUp", xboxElvaUp);
   // SmartDashboard.putBoolean("elvaDown", xboxElvaDown );

    
   


  
    
  
  //automatically changes properties for color block
    

  
    String gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length() > 0) {
      haveGameData = true;
      if (autoColorSpin.get()) {
        colorWheelSpeed = 0.2;
      } 
      char lookingFor = robotShouldSee(gameData);
      switch (lookingFor) {
        case 'B' :
          //Blue case code, feild wants blue stop at red
          if (robotSeesBlue == true) {
            colorWheelSpeed = 0.0;
          }
          break;
        case 'G' :
          //Green case code feild wants green stop at yellow
          if (robotSeesGreen == true) {
            colorWheelSpeed = 0.0;
          }
          break;
        case 'R' :
          //Red case code feild wants red stop at blue
          if (robotSeesRed == true) {
            colorWheelSpeed = 0.0;
          }
          break;
        case 'Y' :
          //Yellow case code feild wants yellow stop at green
          if (robotSeesYellow == true) {
            colorWheelSpeed = 0.0;
          }
          break;
        default :
          //This is corrupt data
          
          break;
      }
    } 
    

  //keep
  
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);
    

    //pixy2 code do not add to new code
    /*
    byte lampHigh = 0;
    byte lampLow = 0;
    int lampRed = 0;
    int lampGreen = 0;
    int lampBlue = 0;

    if (lampRedButton.get()) {
      lampRed = 255;
    }

    if (lampGreenButton.get()) {
      lampGreen = 255;
    }

    if (lampBlueButton.get()) {
      lampBlue = 255;
    }

    if (lampHighButton.get()) {
      lampHigh = 1;
    }

    if (lampLowButton.get()) {
      lampLow = 1;
    }
    pixy.init();
    pixy.setLamp(lampHigh, lampLow);
    pixy.setLED(lampRed,lampGreen,lampBlue);
    */


    colorWheel.set(colorWheelSpeed);
    
  }

  public char robotShouldSee(String gameData) {
    int foundAt = colorOrder.indexOf(gameData);
    return colorOrder.charAt(foundAt + 2);
  }
}
