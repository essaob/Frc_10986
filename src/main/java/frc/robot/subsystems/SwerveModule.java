package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.hardware.CANcoder;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import frc.robot.Constants.DriveConstants;

public class SwerveModule {

  private final TalonFX drive;
  private final TalonFX steer;
  private final CANcoder absEncoder;
  private final double offsetDeg;

  private final DutyCycleOut driveReq = new DutyCycleOut(0);
  private final PositionVoltage steerReq = new PositionVoltage(0).withSlot(0);

  public SwerveModule(int driveId, int steerId, int cancoderId, double offsetDeg) {
    this.drive = new TalonFX(driveId);
    this.steer = new TalonFX(steerId);
    this.absEncoder = new CANcoder(cancoderId);
    this.offsetDeg = offsetDeg;

    TalonFXConfiguration cfg = new TalonFXConfiguration();
    cfg.Slot0.kP = DriveConstants.kSteerKp;
    cfg.Slot0.kI = DriveConstants.kSteerKi;
    cfg.Slot0.kD = DriveConstants.kSteerKd;
    cfg.ClosedLoopGeneral.ContinuousWrap = true;
    steer.getConfigurator().apply(cfg);
  }

  // אבסולוטי של המודולה (deg) אחרי offset
  public double getAbsAngleDeg() {
    double rot = absEncoder.getAbsolutePosition().getValueAsDouble(); // 0..1 rotations
    double deg = rot * 360.0;
    return MathUtil.inputModulus(deg - offsetDeg, -180.0, 180.0);
  }

  // סנכרון ה-steer Talon לזווית האבסולוטית
  public void syncToAbsolute() {
    double moduleRad = Math.toRadians(getAbsAngleDeg());
    double motorRot = (moduleRad / (2.0 * Math.PI)) * DriveConstants.kSteerGearRatio;
    steer.setPosition(motorRot);
  }

  // זווית לפי ה-steer Talon (Rotation2d)
  public Rotation2d getSteerRotation() {
    double motorRot = steer.getPosition().getValueAsDouble();
    double moduleRad = motorRot / DriveConstants.kSteerGearRatio * 2.0 * Math.PI;
    return new Rotation2d(MathUtil.angleModulus(moduleRad));
  }

  public void setDesiredState(SwerveModuleState state) {
    // Optimize: להפחית סיבוב מודולה
    SwerveModuleState optimized = SwerveModuleState.optimize(state, getSteerRotation());

    // ---- steer (angle) ----
    double targetRad = optimized.angle.getRadians();
    double targetMotorRot = (targetRad / (2.0 * Math.PI)) * DriveConstants.kSteerGearRatio;
    steer.setControl(steerReq.withPosition(targetMotorRot));

    // ---- drive (speed) open-loop duty cycle ----
    double percent = optimized.speedMetersPerSecond / DriveConstants.kMaxSpeedMps;
    percent = MathUtil.clamp(percent, -1.0, 1.0);
    drive.setControl(driveReq.withOutput(percent));
  }

  public void stop() {
    drive.setControl(driveReq.withOutput(0));
    steer.setControl(steerReq.withSlot(0));
  }
}
