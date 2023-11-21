package frc.robot.commands;
import java.util.function.Supplier;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Launcher;

public class LauncherCmd extends CommandBase {
        private final Launcher m_launcher;
        private final Double launchSpeed;


    public LauncherCmd(Launcher subsystem, Double launchSpeed){
        this.launchSpeed = launchSpeed;

        
        m_launcher = subsystem;
        addRequirements(m_launcher);
    }



    @Override
    public void initialize(){
       // System.out.println("Launcher starting");
    }
    @Override
    public void execute(){


        m_launcher.spin(launchSpeed);
       // System.out.println(m_launcher.getUltrasonic());
        
    }
     // Called once the command ends or is interrupted.
     @Override
     public void end(boolean interrupted) {
         //System.out.println("Yeet!");
         m_launcher.spin(0.0);
         
 
     }
 
     // Returns true when the command should end.
     @Override
     public boolean isFinished() {
         return false;
     }
 
     @Override
     public boolean runsWhenDisabled() {
         // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
         return false;
 
     // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
     }
 
 

}