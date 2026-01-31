package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.SwerveTestDriveSubsystem;

public class RobotContainer {

  private final SwerveTestDriveSubsystem drive = new SwerveTestDriveSubsystem();

  private final CommandXboxController driver =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();

    drive.setDefaultCommand(
        new RunCommand(() -> {
          double forward = -deadband(driver.getLeftY());                       // סטיק שמאלי למעלה/למטה
          double turn    =  deadband(driver.getLeftX()) * DriveConstants.kTurnScale; // סטיק שמאלי ימינה/שמאלה (פנייה)
          drive.arcadeDrive(forward, turn);
        }, drive)
    );
  }

  private void configureBindings() {
    // A = עצירה מיידית
    driver.a().onTrue(new RunCommand(drive::stopAll, drive));

    // START = איפוס ג'יירו (מומלץ אחרי הדלקה ולפני נהיגה)
    driver.start().onTrue(new RunCommand(drive::zeroGyro, drive));
  }

  private static double deadband(double v) {
    return (Math.abs(v) < DriveConstants.kDeadband) ? 0.0 : v;
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
