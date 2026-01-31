package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.controls.DutyCycleOut;

import frc.robot.Constants.DriveConstants;

public class SwerveTestDriveSubsystem extends SubsystemBase {

  // Drive motors
  private final TalonFX rlDrive = new TalonFX(DriveConstants.REAR_LEFT_DRIVE_ID);
  private final TalonFX rrDrive = new TalonFX(DriveConstants.REAR_RIGHT_DRIVE_ID);
  private final TalonFX frDrive = new TalonFX(DriveConstants.FRONT_RIGHT_DRIVE_ID);
  private final TalonFX flDrive = new TalonFX(DriveConstants.FRONT_LEFT_DRIVE_ID);

  // Steer motors (כרגע כבוי)
  private final TalonFX rlSteer = new TalonFX(DriveConstants.REAR_LEFT_STEER_ID);
  private final TalonFX rrSteer = new TalonFX(DriveConstants.REAR_RIGHT_STEER_ID);
  private final TalonFX frSteer = new TalonFX(DriveConstants.FRONT_RIGHT_STEER_ID);
  private final TalonFX flSteer = new TalonFX(DriveConstants.FRONT_LEFT_STEER_ID);

  // Gyro
  private final Pigeon2 pigeon = new Pigeon2(DriveConstants.kPigeonCanId);

  private final DutyCycleOut req = new DutyCycleOut(0);

  // Heading hold state
  private double targetHeadingDeg = 0.0;
  private boolean headingHoldEnabled = false;

  public SwerveTestDriveSubsystem() {
    // צד ימין הפוך (כמו שהיה אצלך)
    TalonFXConfiguration rightConfig = new TalonFXConfiguration();
    rightConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    rrDrive.getConfigurator().apply(rightConfig);
    frDrive.getConfigurator().apply(rightConfig);

    stopSteer();
  }

  /** איפוס ג'יירו */
  public void zeroGyro() {
    pigeon.setYaw(0);
    targetHeadingDeg = 0.0;
    headingHoldEnabled = false;
  }

  private double getHeadingDeg() {
    return pigeon.getYaw().getValueAsDouble();
  }

  private static double wrapDegrees(double deg) {
    deg = deg % 360.0;
    if (deg > 180.0) deg -= 360.0;
    if (deg < -180.0) deg += 360.0;
    return deg;
  }

  /** Drive על מנועי Drive בלבד + שמירת כיוון עם ג'יירו */
  public void arcadeDrive(double forward, double turn) {

    boolean wantMove = Math.abs(forward) > DriveConstants.kDeadband;
    boolean wantTurn = Math.abs(turn) > DriveConstants.kTurnDeadband;

    // שמירת כיוון רק אם נוסעים ולא מבקשים פנייה
    if (wantMove && !wantTurn) {
      if (!headingHoldEnabled) {
        targetHeadingDeg = getHeadingDeg(); // "ננעלים" על הכיוון הנוכחי
        headingHoldEnabled = true;
      }

      double error = wrapDegrees(targetHeadingDeg - getHeadingDeg());
      double correction = error * DriveConstants.kHeadingKp;
      correction = MathUtil.clamp(
          correction,
          -DriveConstants.kMaxHoldTurn,
           DriveConstants.kMaxHoldTurn
      );

      turn = correction; // תיקון אוטומטי קטן
    } else {
      // אם הנהג פונה ידנית או לא זז – לא שומרים כיוון
      headingHoldEnabled = false;
    }

    // תרגום ל-left/right
    double left = forward + turn;
    double right = forward - turn;

    // נרמול
    double maxMag = Math.max(Math.abs(left), Math.abs(right));
    if (maxMag > 1.0) {
      left /= maxMag;
      right /= maxMag;
    }

    // סקייל מהירות
    left *= DriveConstants.kMaxDriveOutput;
    right *= DriveConstants.kMaxDriveOutput;

    // Left side
    flDrive.setControl(req.withOutput(left));
    rlDrive.setControl(req.withOutput(left));

    // Right side
    frDrive.setControl(req.withOutput(right));
    rrDrive.setControl(req.withOutput(right));

    // steer כבוי
    stopSteer();
  }

  public void stopAll() {
    flDrive.setControl(req.withOutput(0));
    rlDrive.setControl(req.withOutput(0));
    frDrive.setControl(req.withOutput(0));
    rrDrive.setControl(req.withOutput(0));
    stopSteer();
    headingHoldEnabled = false;
  }

  private void stopSteer() {
    rlSteer.setControl(req.withOutput(0));
    rrSteer.setControl(req.withOutput(0));
    frSteer.setControl(req.withOutput(0));
    flSteer.setControl(req.withOutput(0));
  }
}
