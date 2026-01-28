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
          double forward = -deadband(driver.getLeftY());
          double turn = deadband(driver.getLeftX()) * DriveConstants.kTurnScale;
          drive.arcadeDrive(forward, turn);
        }, drive)
    );
  }

  private void configureBindings() {
    // A = עצירה מיידית
    driver.a().onTrue(new RunCommand(drive::stopAll, drive));
  }

  private static double deadband(double v) {
    return (Math.abs(v) < DriveConstants.kDeadband) ? 0.0 : v;
  }

  public Command getAutonomousCommand() {
    return null;
  }
}