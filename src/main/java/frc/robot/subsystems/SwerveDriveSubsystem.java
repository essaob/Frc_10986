package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

import com.ctre.phoenix6.hardware.Pigeon2;

import frc.robot.Constants.DriveConstants;

public class SwerveDriveSubsystem extends SubsystemBase {

  private final Pigeon2 pigeon = new Pigeon2(DriveConstants.kPigeonCanId);

  private final SwerveModule fl = new SwerveModule(
      DriveConstants.FL_DRIVE_ID, DriveConstants.FL_STEER_ID,
      DriveConstants.FL_CANCODER_ID, DriveConstants.FL_OFFSET_DEG);

  private final SwerveModule fr = new SwerveModule(
      DriveConstants.FR_DRIVE_ID, DriveConstants.FR_STEER_ID,
      DriveConstants.FR_CANCODER_ID, DriveConstants.FR_OFFSET_DEG);

  private final SwerveModule rl = new SwerveModule(
      DriveConstants.RL_DRIVE_ID, DriveConstants.RL_STEER_ID,
      DriveConstants.RL_CANCODER_ID, DriveConstants.RL_OFFSET_DEG);

  private final SwerveModule rr = new SwerveModule(
      DriveConstants.RR_DRIVE_ID, DriveConstants.RR_STEER_ID,
      DriveConstants.RR_CANCODER_ID, DriveConstants.RR_OFFSET_DEG);

  private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
      DriveConstants.FL_LOCATION,
      DriveConstants.FR_LOCATION,
      DriveConstants.RL_LOCATION,
      DriveConstants.RR_LOCATION
  );

  public SwerveDriveSubsystem() {
    // חשוב: לסנכרן steer לאבסולוטי בתחילת הריצה
    syncAllToAbsolute();
  }

  public void syncAllToAbsolute() {
    fl.syncToAbsolute();
    fr.syncToAbsolute();
    rl.syncToAbsolute();
    rr.syncToAbsolute();
  }

  public void zeroGyro() {
    pigeon.setYaw(0);
  }

  /** שמאלה חיובי (Yaw) */
  public Rotation2d getGyroRotation2d() {
    double yawDeg = pigeon.getYaw().getValueAsDouble();
    return Rotation2d.fromDegrees(yawDeg);
  }

  /**
   * נסיעה תרגומית בלבד בעזרת הסטיק השמאלי:
   * xSpeed = קדימה/אחורה (m/s)
   * ySpeed = שמאל/ימין (m/s)
   * omega = 0 (אין סיבוב!)
   */
  public void driveFieldRelative(double xSpeed, double ySpeed) {
    ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(
        xSpeed, ySpeed, 0.0, getGyroRotation2d()
    );

    SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(states, DriveConstants.kMaxSpeedMps);

    fl.setDesiredState(states[0]);
    fr.setDesiredState(states[1]);
    rl.setDesiredState(states[2]);
    rr.setDesiredState(states[3]);
  }

  public void stopAll() {
    fl.stop();
    fr.stop();
    rl.stop();
    rr.stop();
  }
}
