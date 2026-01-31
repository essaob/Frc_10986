package frc.robot;

public final class Constants {

  public static final class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class DriveConstants {

    // Drive motors (even IDs)
    public static final int REAR_LEFT_DRIVE_ID = 0;
    public static final int REAR_RIGHT_DRIVE_ID = 2;
    public static final int FRONT_RIGHT_DRIVE_ID = 4;
    public static final int FRONT_LEFT_DRIVE_ID = 6;

    // Steer motors (odd IDs) - כרגע לא משתמשים בלי אנקודר
    public static final int REAR_LEFT_STEER_ID = 1;
    public static final int REAR_RIGHT_STEER_ID = 3;
    public static final int FRONT_RIGHT_STEER_ID = 5;
    public static final int FRONT_LEFT_STEER_ID = 7;

    // Driving parameters
    public static final double kMaxDriveOutput = 0.6; // עוצמת נסיעה
    public static final double kTurnScale = 0.7;      // עוצמת פנייה ידנית
    public static final double kDeadband = 0.08;      // סינון רעידות סטיק

    // Pigeon2
    public static final int kPigeonCanId = 9;

    // Heading hold (שמירת כיוון) - כיוון ראשוני מומלץ
    public static final double kHeadingKp = 0.02;      // אם עדיין "בורח" - תגדיל ל-0.03
    public static final double kMaxHoldTurn = 0.35;    // מגבלת תיקון אוטומטי
    public static final double kTurnDeadband = 0.06;   // אם הסטיק כמעט באמצע -> נחשב "לא פונים"
  }
}
