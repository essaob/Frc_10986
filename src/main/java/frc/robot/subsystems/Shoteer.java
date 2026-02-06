package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shoteer extends SubsystemBase{

    private TalonFX shoteer_motor;
    private TalonFXConfiguration shoot_Configuration;
    private DutyCycleOut dutyCycleOut;
    public static Shoteer instanceShoteer = null ; 
    private Shoteer(){
        shoteer_motor = new TalonFX(8);
        shoot_Configuration = new TalonFXConfiguration();
        shoot_Configuration.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        shoot_Configuration.MotorOutput.Inverted=InvertedValue.Clockwise_Positive;
        shoteer_motor.getConfigurator().apply(shoot_Configuration);
        dutyCycleOut= new DutyCycleOut(0); // start speed
    }

      public static Shoteer getInstanceShoteer(){
        if(instanceShoteer == null){
            instanceShoteer = new Shoteer();
        }
        return instanceShoteer;
    }

    public void shootAtSpeed(double speed){ //set the speed of the intake
            shoteer_motor.setControl(new DutyCycleOut(speed));
        }
    
    public void stopShoot(){
        shoteer_motor.setControl(new DutyCycleOut(0));
    }


}
