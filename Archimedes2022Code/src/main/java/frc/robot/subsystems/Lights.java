package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import com.mach.LightDrive.*;
import java.awt.Color; //Predefined colors and routines
public class Lights extends SubsystemBase {
private Spark intakeMotor;

    public Lights(){
      //C++/JAVA

//PWM Servo Controlled LightDrive (specify 2 WPI Servo objects)
 //  ld_pwm = new LightDrivePWM(Servo0,Servo1);

 // added allowInsecureProtocol

      


        
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }
    public void spin(double speed) {
        intakeMotor.set(speed); 
        
    }
    public void stop(){
        intakeMotor.set(0.0);
        
        
    }
}