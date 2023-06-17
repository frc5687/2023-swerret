/* Team 5687 (C)2021 */
/* Team 5687 (C)2021-2022 */
package org.frc5687.swerret;

import org.frc5687.swerret.commands.Drive;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.commands.Turret.TurretWiggle;
import org.frc5687.swerret.subsystems.*;
import org.frc5687.swerret.util.*;

import com.ctre.phoenixpro.configs.Pigeon2Configuration;
import com.ctre.phoenixpro.hardware.Pigeon2;
import com.pathplanner.lib.PathConstraints;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RobotContainer extends OutliersContainer {

    private OI _oi;
    // private VisionProcessor _visionProcessor;
    private Pigeon2 _imu;
    private Robot _robot;
    private DriveTrain _driveTrain;
    private Turret _turret;
    // private PhotonProcessor _photonProcessor;
    // private Trajectories _trajectories;

    public RobotContainer(Robot robot, IdentityMode identityMode) {
        super(identityMode);
        _robot = robot;
    }

    public void init() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        Thread.currentThread().setName("Robot Thread");
        _oi = new OI();
        // create the vision processor
        // _visionProcessor = new VisionProcessor();
        // subscribe to a vision topic for the correct data
        // _visionProcessor.createSubscriber("vision", "tcp://10.56.87.20:5557");
        // _trajectories = new Trajectories(new PathConstraints(3.0, 2.0));

//         try {
//             _photonProcessor =
// //                    new PhotonProcessor(AprilTagFieldLayout.loadFromResource("2023-chargedup.json"));
//             new PhotonProcessor(FieldConstants.aprilTags);
//         } catch (Exception e) {
//             e.getMessage();
//         }
        // configure pigeon
        _imu = new Pigeon2(RobotMap.CAN.PIGEON.PIGEON, "CANivore");
        var pigeonConfig = new Pigeon2Configuration();
        _imu.getConfigurator().apply(pigeonConfig);

        _driveTrain = new DriveTrain(this, _imu);
        //         This is for auto temporarily, need to fix for both in future.
        _turret = new Turret(this);

        setDefaultCommand(_driveTrain, new Drive(_driveTrain, _oi));

        _oi.initializeButtons(_driveTrain);

        // _visionProcessor.start();
        _robot.addPeriodic(this::controllerPeriodic, 0.005, 0.00);
        _driveTrain.startModules();
        startPeriodic();
    }

    public void periodic() {}

    public void disabledPeriodic() {}

    @Override
    public void disabledInit() {}

    @Override
    public void teleopInit() {}

    @Override
    public void autonomousInit() {
        // _autoChooser.updateChooser();
    }

    private void setDefaultCommand(OutliersSubsystem subSystem, OutliersCommand command) {
        if (subSystem == null || command == null) {
            return;
        }
        CommandScheduler s = CommandScheduler.getInstance();
        s.setDefaultCommand(subSystem, command);
    }

    public Command getAutoCommand() {
        return new TurretWiggle(_turret);
    }

    public void controllerPeriodic() {
        if (_driveTrain != null) {
            _driveTrain.modulePeriodic();
        }
    }
}
