<<<<<<< HEAD
package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;

public final class Constants {

  public static final class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class DriveConstants {
    // ===== CAN IDs =====
    public static final int RL_DRIVE_ID = 0;
    public static final int RL_STEER_ID = 1;

    public static final int RR_DRIVE_ID = 2;
    public static final int RR_STEER_ID = 3;

    public static final int FR_DRIVE_ID = 4;
    public static final int FR_STEER_ID = 5;

    public static final int FL_DRIVE_ID = 6;
    public static final int FL_STEER_ID = 7;

    // ===== Pigeon2 =====
    public static final int kPigeonCanId = 19;

    // ===== CANcoder IDs =====
    public static final int RL_CANCODER_ID = 15;
    public static final int RR_CANCODER_ID = 16;
    public static final int FR_CANCODER_ID = 17;
    public static final int FL_CANCODER_ID = 18;

    // ===== Offsets (deg) =====
    // חובה למדוד! (כשהמודולה ישר קדימה)
    public static final double RL_OFFSET_DEG = 19.4;
    public static final double RR_OFFSET_DEG = 34.45;
    public static final double FR_OFFSET_DEG = 93.51576;
    public static final double FL_OFFSET_DEG =93.564;

    // ===== מידות שלדה (מטרים) =====
    // תמדוד: מרחק בין גלגל שמאל לימין = trackWidth
    //        מרחק בין גלגל קדמי לאחורי = wheelBase
    public static final double kTrackWidthMeters = 0.55; 
    public static final double kWheelBaseMeters  = 0.55; 

    // מיקום המודולות ביחס למרכז הרובוט
    public static final Translation2d FL_LOCATION =
        new Translation2d(kWheelBaseMeters / 2.0,  kTrackWidthMeters / 2.0);
    public static final Translation2d FR_LOCATION =
        new Translation2d(kWheelBaseMeters / 2.0, -kTrackWidthMeters / 2.0);
    public static final Translation2d RL_LOCATION =
        new Translation2d(-kWheelBaseMeters / 2.0,  kTrackWidthMeters / 2.0);
    public static final Translation2d RR_LOCATION =
        new Translation2d(-kWheelBaseMeters / 2.0, -kTrackWidthMeters / 2.0);

    // ===== Steering gear ratio =====
    public static final double kSteerGearRatio = 12.8; // אצלכם

    // ===== Steer PID =====
    public static final double kSteerKp = 60.0;
    public static final double kSteerKi = 0.0;
    public static final double kSteerKd = 1.0;

    // ===== נסיעה =====
    public static final double kDeadband = 0.08;
    public static final double kMaxSpeedMps = 3.0; // תתחיל נמוך אם צריך
    public static final double kDriverSpeedScale = 0.6; // 60% מהמהירות
=======
// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
>>>>>>> 6e162ba (Frc_10986)
  }
}
