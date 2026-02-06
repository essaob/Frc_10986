package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{
    private static Intake instanceIntake = null;
    private TalonFX intake_motor;
     private TalonFX intake_open_motor;
    private MotionMagicConfigs magicConfigs; // to give deatiles for speed motor
    private TalonFXConfiguration configs; // motor deayiles like KP , KI
    private TalonFXConfiguration configs_intake;
    final MotionMagicVoltage value = new MotionMagicVoltage(0.0);
    private DutyCycleOut dutyCycleOut;
    
    private Intake(){
        intake_motor = new TalonFX(88);
        configs_intake = new TalonFXConfiguration();   //confige : motor deatils
        configs_intake.MotorOutput.Inverted = InvertedValue.Clockwise_Positive; // clocke move 

    //     magicConfigs = new MotionMagicConfigs();
    //    configs.CurrentLimits.SupplyCurrentLimit = 20; //20 amps
    //    configs.CurrentLimits.SupplyCurrentLimitEnable = true;
    //    configs.CurrentLimits.StatorCurrentLimit = 20;
    //    configs.CurrentLimits.StatorCurrentLimitEnable = false;
    //     intake_motor.getConfigurator().apply(configs);

        intake_open_motor = new TalonFX(9);
        configs = new TalonFXConfiguration();   //confige : motor deatils
        magicConfigs = new MotionMagicConfigs();
       configs.CurrentLimits.SupplyCurrentLimit = 20; //20 amps
       configs.CurrentLimits.SupplyCurrentLimitEnable = true;
       configs.CurrentLimits.StatorCurrentLimit = 20;
       configs.CurrentLimits.StatorCurrentLimitEnable = false;

       //PID motor linmit
        configs.Slot0.kP = 0.1;  // 3
        configs.Slot0.kI = 0.0;    // 4
        configs.Slot0.kS = 0.0; //static 1
        configs.Slot0.kV = 0.0; //velocity  2
        configs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        configs.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        //motionMagic 
        magicConfigs=configs.MotionMagic;
        magicConfigs.MotionMagicCruiseVelocity   = 70; //rotion per min
        magicConfigs.MotionMagicAcceleration = 120; //rotation per min per sec
        magicConfigs.MotionMagicJerk = 1200; //rotation per min per sec per sec
        intake_open_motor.getConfigurator().apply(configs);
        intake_motor.getConfigurator().apply(configs_intake);




        //defult value for duty cycle out
        intake_open_motor.setPosition(0);
        //intake_motor.setPosition(0);

    
    }

    //Function to get the instance of the intake subsystem
    public static Intake getInstanceIntake(){
        if(instanceIntake == null){
            instanceIntake = new Intake();
        }
        return instanceIntake;
    }

    public Command resetAngleCommand(){ //reset the angle of the intake to 0 , save postion
        return runOnce(() -> {
            intake_open_motor.setControl(value.withPosition(0));
            //intake_motor.setPosition(0);
        });
    }
    
    public void setIntakeOpen(double position){ //set the angle of the intake
        intake_open_motor.setControl(value.withPosition(position));
    }

    public void setIntakespeed(double speed){ //set the speed of the intake
            intake_motor.setControl(new DutyCycleOut(speed));
        }
    
    public void stopIntake(){
        intake_motor.setControl(new DutyCycleOut(0));
    }

}







