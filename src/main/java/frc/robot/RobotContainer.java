package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class RobotContainer {

  private final SwerveDriveSubsystem drive = new SwerveDriveSubsystem();

  private final CommandXboxController driver =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {

    drive.setDefaultCommand(
      new RunCommand(() -> {
        double x = -deadband(driver.getLeftY()); // קדימה/אחורה
        double y = -deadband(driver.getLeftX()); // שמאל/ימין (הפכתי כדי שימינה זה + לפי נוחות; אם הפוך תגיד לי)

        double xSpeed = x * DriveConstants.kMaxSpeedMps * DriveConstants.kDriverSpeedScale;
        double ySpeed = y * DriveConstants.kMaxSpeedMps * DriveConstants.kDriverSpeedScale;

        drive.driveFieldRelative(xSpeed, ySpeed);
      }, drive)
    );

    // START = איפוס ג'יירו
    driver.start().onTrue(new RunCommand(drive::zeroGyro, drive));

    // BACK = סנכרון מודולות לאבסולוטי (אחרי יישור/או אם קפץ)
    driver.back().onTrue(new RunCommand(drive::syncAllToAbsolute, drive));

    // A = עצירה
    driver.a().onTrue(new RunCommand(drive::stopAll, drive));
  }

  private static double deadband(double v) {
    return (Math.abs(v) < DriveConstants.kDeadband) ? 0.0 : v;
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
