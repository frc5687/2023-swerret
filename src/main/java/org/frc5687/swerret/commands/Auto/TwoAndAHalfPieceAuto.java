package org.frc5687.swerret.commands.Auto;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import org.frc5687.swerret.Constants;
import org.frc5687.swerret.OI;
import org.frc5687.swerret.commands.DriveTrajectory;
import org.frc5687.swerret.subsystems.DriveTrain;
import org.frc5687.swerret.util.AutoChooser;
import org.frc5687.swerret.util.Trajectories;

public class TwoAndAHalfPieceAuto extends SequentialCommandGroup {
    private PathPlannerTrajectory _trajectory1;
    private PathPlannerTrajectory _trajectory2;
    private PathPlannerTrajectory _trajectory3;
    private Pose2d pose;
    // private Rotation2d rotation1;
    // private Rotation2d rotation2;

    public TwoAndAHalfPieceAuto(
        DriveTrain driveTrain,
        OI _oi,
        AutoChooser.Node _node,
        Trajectories trajectories
        ) {
        String alliance = driveTrain.isRedAlliance() ? "RED_" : "BLUE_";
        boolean waitInstead = false;

        switch (_node) {
            case OneCone:
                _trajectory1 = trajectories.getTrajectory(alliance + "NODE_ONE_GOAL_ONE");
                _trajectory2 = trajectories.getTrajectory(alliance + "GOAL_ONE_NODE_TWO");
                _trajectory3 = trajectories.getTrajectory(alliance + "NODE_TWO_SHOOT_GOAL_TWO");
                pose = driveTrain.isRedAlliance() ? Constants.Auto.FieldPoses.RED_NODE_TWO_GOAL : Constants.Auto.FieldPoses.BLUE_NODE_TWO_GOAL;
                break;
            case TwoCube:
                _trajectory1 = trajectories.getTrajectory(alliance + "NODE_TWO_GOAL_ONE");
                _trajectory2 = trajectories.getTrajectory(alliance + "GOAL_ONE_NODE_TWO");
                _trajectory3 = trajectories.getTrajectory(alliance + "NODE_TWO_SHOOT_GOAL_TWO");
                pose = driveTrain.isRedAlliance() ? Constants.Auto.FieldPoses.RED_NODE_TWO_GOAL : Constants.Auto.FieldPoses.BLUE_NODE_TWO_GOAL;
                break;
            case ThreeCone:
                DriverStation.reportError("Unimplemented case: " + _node, false);
                waitInstead = true;
                break;
            case FourCone:
                DriverStation.reportError("Unimplemented case: " + _node, false);
                waitInstead = true;
                break;
            case FiveCube:
                DriverStation.reportError("Unimplemented case: " + _node, false);
                waitInstead = true;
                break;
            case SixCone:
                DriverStation.reportError("Unimplemented case: " + _node, false);
                waitInstead = true;
                break;
            case SevenCone:
                DriverStation.reportError("Unimplemented case: " + _node, false);
                waitInstead = true;
                break;
            case EightCube:
                _trajectory1 = trajectories.getTrajectory(alliance + "NODE_EIGHT_GOAL_FOUR");
                _trajectory2 = trajectories.getTrajectory(alliance + "GOAL_FOUR_NODE_EIGHT");
                _trajectory3 = trajectories.getTrajectory(alliance + "NODE_EIGHT_SHOOT_GOAL_THREE");
                pose = driveTrain.isRedAlliance() ? Constants.Auto.FieldPoses.RED_NODE_EIGHT_GOAL : Constants.Auto.FieldPoses.BLUE_NODE_EIGHT_GOAL;
                break;
            case NineCone:
                _trajectory1 = trajectories.getTrajectory(alliance + "NODE_NINE_GOAL_FOUR");
                _trajectory2 = trajectories.getTrajectory(alliance + "GOAL_FOUR_NODE_EIGHT");
                _trajectory3 = trajectories.getTrajectory(alliance + "NODE_EIGHT_SHOOT_GOAL_THREE");
                pose = driveTrain.isRedAlliance() ? Constants.Auto.FieldPoses.RED_NODE_EIGHT_GOAL : Constants.Auto.FieldPoses.BLUE_NODE_EIGHT_GOAL;                break;
            case Unknown:
                DriverStation.reportError("Unimplemented case: " + _node, false);
                waitInstead = true;
                break;
            default:
                DriverStation.reportError("Unexpected value: " + _node, false);
                waitInstead = true;
                break;
        }
        if (waitInstead) {
            addCommands(new WaitCommand(15));
        } else {
            addCommands(
                new SequentialCommandGroup(
                    new ResetRobotPose(driveTrain, _trajectory1.getInitialHolonomicPose()),
                    new DriveTrajectory(driveTrain, _trajectory1, true, false),
                    new DriveTrajePose(driveTrain, _trajectory2, true, false, pose.transformBy(new Transform2d(new Translation2d(0.02, 0), new Rotation2d())), true, false),
                    new DriveTrajectory(driveTrain, _trajectory3, true, false)
                )
            );
        }
    }
}