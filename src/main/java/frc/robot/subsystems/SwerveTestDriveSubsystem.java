package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.DutyCycleOut;

import frc.robot.Constants.DriveConstants;

public class SwerveTestDriveSubsystem extends SubsystemBase {

  // Drive motors (even IDs)
  private final TalonFX rlDrive = new TalonFX(DriveConstants.REAR_LEFT_DRIVE_ID);
  private final TalonFX rrDrive = new TalonFX(DriveConstants.REAR_RIGHT_DRIVE_ID);
  private final TalonFX frDrive = new TalonFX(DriveConstants.FRONT_RIGHT_DRIVE_ID);
  private final TalonFX flDrive = new TalonFX(DriveConstants.FRONT_LEFT_DRIVE_ID);

  // Steer motors (odd IDs) - כרגע לא משתמשים בלי אנקודר
  private final TalonFX rlSteer = new TalonFX(DriveConstants.REAR_LEFT_STEER_ID);
  private final TalonFX rrSteer = new TalonFX(DriveConstants.REAR_RIGHT_STEER_ID);
  private final TalonFX frSteer = new TalonFX(DriveConstants.FRONT_RIGHT_STEER_ID);
  private final TalonFX flSteer = new TalonFX(DriveConstants.FRONT_LEFT_STEER_ID);

  private final DutyCycleOut req = new DutyCycleOut(0);

public SwerveTestDriveSubsystem() {

  TalonFXConfiguration rightConfig = new TalonFXConfiguration();
  rightConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

  rrDrive.getConfigurator().apply(rightConfig);
  frDrive.getConfigurator().apply(rightConfig);

  // כרגע steer כבוי
  stopSteer();
}
  /** מצב בדיקה: "Arcade" זמני על מנועי Drive בלבד */
  public void arcadeDrive(double forward, double turn) {
    double left = forward + turn;
    double right = forward - turn;

    double maxMag = Math.max(Math.abs(left), Math.abs(right));
    if (maxMag > 1.0) {
      left /= maxMag;
      right /= maxMag;
    }

    left *= DriveConstants.kMaxDriveOutput;
    right *= DriveConstants.kMaxDriveOutput;

    // Left side drive motors: FL + RL
    flDrive.setControl(req.withOutput(left));
    rlDrive.setControl(req.withOutput(left));

    // Right side drive motors: FR + RR
    frDrive.setControl(req.withOutput(right));
    rrDrive.setControl(req.withOutput(right));

    // steer נשאר כבוי
    stopSteer();
  }

  public void stopAll() {
    flDrive.setControl(req.withOutput(0));
    rlDrive.setControl(req.withOutput(0));
    frDrive.setControl(req.withOutput(0));
    rrDrive.setControl(req.withOutput(0));
    stopSteer();
  }

  private void stopSteer() {
    rlSteer.setControl(req.withOutput(0));
    rrSteer.setControl(req.withOutput(0));
    frSteer.setControl(req.withOutput(0));
    flSteer.setControl(req.withOutput(0));
  }
}