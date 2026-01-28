// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */


public final class Constants {

  public static final class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  // =========================
  // Drive / Swerve Constants
  // =========================
  public static final class DriveConstants {

    // Rear Left Module
    public static final int REAR_LEFT_DRIVE_ID = 0;   // קדימה / אחורה
    public static final int REAR_LEFT_STEER_ID = 1;   // סיבוב גלגל

    // Rear Right Module
    public static final int REAR_RIGHT_DRIVE_ID = 2;
    public static final int REAR_RIGHT_STEER_ID = 3;

    // Front Right Module
    public static final int FRONT_RIGHT_DRIVE_ID = 4;
    public static final int FRONT_RIGHT_STEER_ID = 5;

    // Front Left Module
    public static final int FRONT_LEFT_DRIVE_ID = 6;
    public static final int FRONT_LEFT_STEER_ID = 7;

    // =========================
    // Driving parameters
    // =========================
    public static final double kMaxDriveOutput = 0.6;   // עוצמת נסיעה מקסימלית
    public static final double kTurnScale = 0.7;        // עוצמת סיבוב
    public static final double kDeadband = 0.08;        // סינון רעידות סטיק
  }
}
