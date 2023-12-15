/* Team 5687 (C)2020-2022 */
package org.frc5687.swerret;

import com.ctre.phoenix.led.TwinkleAnimation.TwinklePercent;
import com.ctre.phoenix.led.TwinkleOffAnimation.TwinkleOffPercent;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import java.util.Arrays;
import java.util.List;
import org.frc5687.swerret.subsystems.SwerveModule.ModuleConfiguration;
import org.frc5687.lib.drivers.OutliersTalon;
import org.frc5687.lib.swerve.SwerveSetpointGenerator.KinematicLimits;

public class Constants {
    public static final int TICKS_PER_UPDATE = 1;
    public static final double METRIC_FLUSH_PERIOD = 0.02;
    public static final double UPDATE_PERIOD = 0.02; // 20 ms
    public static final double CONTROL_PERIOD = 0.02; // 10 ms
    public static final double DATA_PERIOD = 0.02; // 20 ms
    public static final double EPSILON = 1e-9;

    /**
     * Coordinate System
     *
     * <p>
     * (X, Y): X is N or S, N is + Y is W or E, W is +
     *
     * <p>
     * NW (+,+) NE (+,-)
     *
     * <p>
     * SW (-,+) SE (-,-)
     *
     * <p>
     * We go counter-counter clockwise starting at NW of chassis:
     *
     * <p>
     * NW, SW, SE, NE
     *
     * <p>
     * Note: when robot is flipped over, this is clockwise.
     */
    public static class DriveTrain {
        public static final String CAN_BUS = "CANivore";
        public static final int NUM_MODULES = 4;

        // Size of the robot chassis in meters
        public static final double WIDTH = 0.4445; // meters
        public static final double LENGTH = 0.4445; // meters
        // Distance of swerve modules from center of robot
        public static final double SWERVE_NS_POS = LENGTH / 2.0;
        public static final double SWERVE_WE_POS = WIDTH / 2.0;

        public static final double MAX_MPS = 6.0; // Max speed of robot (m/s)
        public static final double MAX_LOW_GEAR_MPS = 3.5;
        public static final double MAX_HIGH_GEAR_MPS = 6.5; // 6.85
        public static final double SLOW_MPS = 2.0; // Slow speed of robot (m/s)
        public static final double MAX_ANG_VEL = Math.PI; // Max rotation rate of robot (rads/s)
        public static final double SLOW_ANG_VEL = Math.PI; // Max rotation rate of robot (rads/s)

        public static final double SHIFT_UP_SPEED_MPS = 2.3; // Speed to start shift y
        public static final double SHIFT_DOWN_SPEED_MPS = 1.75; // Speed to start shift y

        public static final KinematicLimits HIGH_KINEMATIC_LIMITS = new KinematicLimits();

        static {
            HIGH_KINEMATIC_LIMITS.maxDriveVelocity = MAX_HIGH_GEAR_MPS; // m/s
            HIGH_KINEMATIC_LIMITS.maxDriveAcceleration = 30; // m/s^2
            HIGH_KINEMATIC_LIMITS.maxSteeringVelocity = 25; // rad/s
        }
        public static final KinematicLimits LOW_KINEMATIC_LIMITS = new KinematicLimits();

        static {
            LOW_KINEMATIC_LIMITS.maxDriveVelocity = MAX_LOW_GEAR_MPS; // m/s
            LOW_KINEMATIC_LIMITS.maxDriveAcceleration = 20; // m/s^2
            LOW_KINEMATIC_LIMITS.maxSteeringVelocity = 25; // rad/s
        }

        public static final KinematicLimits KINEMATIC_LIMITS = LOW_KINEMATIC_LIMITS;

        public static final KinematicLimits DRIVE_POSE_KINEMATIC_LIMITS = new KinematicLimits();
        static {
            DRIVE_POSE_KINEMATIC_LIMITS.maxDriveVelocity = 2.5; // m/s
            DRIVE_POSE_KINEMATIC_LIMITS.maxDriveAcceleration = 20; // m/s^2
            DRIVE_POSE_KINEMATIC_LIMITS.maxSteeringVelocity = 20; // rad/s
        }

        public static final KinematicLimits TRAJECTORY_FOLLOWING = new KinematicLimits();
        static {
            TRAJECTORY_FOLLOWING.maxDriveVelocity = 5.0; // m/s
            TRAJECTORY_FOLLOWING.maxDriveAcceleration = 20; // m/s^2
            TRAJECTORY_FOLLOWING.maxSteeringVelocity = 20; // rad/s
        }
        public static final KinematicLimits SLOW_KINEMATIC_LIMITS = new KinematicLimits();

        static {
            SLOW_KINEMATIC_LIMITS.maxDriveVelocity = 2; // m/s
            SLOW_KINEMATIC_LIMITS.maxDriveAcceleration = 10; // m/s^2
            SLOW_KINEMATIC_LIMITS.maxSteeringVelocity = 10; // rad/s
        }

        public static final KinematicLimits VISION_KINEMATIC_LIMITS = new KinematicLimits();

        static {
            VISION_KINEMATIC_LIMITS.maxDriveVelocity = 3.0; // m/s
            VISION_KINEMATIC_LIMITS.maxDriveAcceleration = 15; // m/s^2
            VISION_KINEMATIC_LIMITS.maxSteeringVelocity = 20; // rad/s
        }

        public static final KinematicLimits POV_KINEMATIC_LIMITS = new KinematicLimits();

        static {
            POV_KINEMATIC_LIMITS.maxDriveVelocity = 1; // m/s
            POV_KINEMATIC_LIMITS.maxDriveAcceleration = 10; // m/s^2
            POV_KINEMATIC_LIMITS.maxSteeringVelocity = 10; // rad/s
        }

        public static final ModuleConfiguration NORTH_WEST_CONFIG = new ModuleConfiguration();

        static {
            NORTH_WEST_CONFIG.moduleName = "North West";
            NORTH_WEST_CONFIG.canBus = CAN_BUS;
            NORTH_WEST_CONFIG.position = new Translation2d(SWERVE_NS_POS, SWERVE_WE_POS); // +,+

            NORTH_WEST_CONFIG.encoderInverted = false;
            NORTH_WEST_CONFIG.encoderOffset = 0.270508;
        }

        public static final ModuleConfiguration SOUTH_WEST_CONFIG = new ModuleConfiguration();

        static {
            SOUTH_WEST_CONFIG.moduleName = "South West";
            SOUTH_WEST_CONFIG.canBus = CAN_BUS;
            SOUTH_WEST_CONFIG.position = new Translation2d(-SWERVE_NS_POS, SWERVE_WE_POS); // -,+

            SOUTH_WEST_CONFIG.encoderInverted = false;
            SOUTH_WEST_CONFIG.encoderOffset = -0.091553;
        }

        public static final ModuleConfiguration SOUTH_EAST_CONFIG = new ModuleConfiguration();

        static {
            SOUTH_EAST_CONFIG.moduleName = "South East";
            SOUTH_EAST_CONFIG.canBus = CAN_BUS;
            SOUTH_EAST_CONFIG.position = new Translation2d(-SWERVE_NS_POS, -SWERVE_WE_POS); // -,-

            SOUTH_EAST_CONFIG.encoderInverted = false;
            SOUTH_EAST_CONFIG.encoderOffset = 0.118652;
        }

        public static final ModuleConfiguration NORTH_EAST_CONFIG = new ModuleConfiguration();

        static {
            NORTH_EAST_CONFIG.moduleName = "North East";
            NORTH_EAST_CONFIG.canBus = CAN_BUS;
            NORTH_EAST_CONFIG.position = new Translation2d(SWERVE_NS_POS, -SWERVE_WE_POS); // +,-

            NORTH_EAST_CONFIG.encoderInverted = false;
            NORTH_EAST_CONFIG.encoderOffset = -0.170166;
        }

        public static final double TRANSLATION_DEADBAND = 0.05; // Avoid unintentional joystick movement
        public static final double ROTATION_DEADBAND = 0.05; // Avoid unintentional joystick movement
        public static final long DISABLE_TIME = 500; // ms

        public static final double LINEAR_VELOCITY_REFERENCE = 0.5;

        // Maximum rates of motion

        public static final double POLE_THRESHOLD = Units.degreesToRadians(5.0);

        // PID controller settings
        public static final double MAINTAIN_kP = 4.5;
        public static final double MAINTAIN_kI = 0.0;
        public static final double MAINTAIN_kD = 0.3;

        public static final double SNAP_kP = 4.0;
        public static final double SNAP_kI = 0.0;
        public static final double SNAP_kD = 0.1;

        public static final double SNAP_TOLERANCE = Units.degreesToRadians(5.0);

        public static final double PROFILE_CONSTRAINT_VEL = Math.PI * 4.0;
        public static final double PROFILE_CONSTRAINT_ACCEL = Math.PI * 8.0;

        public static final double kP = 3.3;
        public static final double kI = 0.0;
        public static final double kD = 0.05;

        public static final double X_TRAJECTORY_kP = 3.8;
        public static final double X_TRAJECTORY_kI = 0.0;
        public static final double X_TRAJECTORY_kD = 0.02;

        public static final double Y_TRAJECTORY_kP = 3.8;
        public static final double Y_TRAJECTORY_kI = 0.0;
        public static final double Y_TRAJECTORY_kD = 0.02;

        public static final double ANGLE_TRAJECTORY_kP = 3.2;
        public static final double ANGLE_TRAJECTORY_kI = 0.0;
        public static final double ANGLE_TRAJECTORY_kD = 0.05;

        public static final double POSITION_TOLERANCE = 0.01;
        public static final double LEVEL_TOLERANCE = 0.5;
        public static final double HEADING_TOLERANCE = 0.15; // rad
        public static final double BUMP_DEGREES = 7;

        public static final double PITCH_LOOKING_ANGLE = Units.degreesToRadians(15.0); // this is degrees because sad.
        public static final double PITCH_LEVELED_ANGLE = Units.degreesToRadians(5.0); // this is degrees because sad.

        public static final double DRIVING_UP_RAMP_SPEEDS_VX = 2.0;
        public static final double DRIVING_DOWN_RAMP_SPEEDS_VX = 1.0;

        public static final double AUTO_LEVEL_KP = 4.5; // PID controller for leveling
        public static final double AUTO_LEVEL_KI = 0.0;
        public static final double AUTO_LEVEL_KD = 1.0;

        public static final double QUICK_LEVEL_KP = 3.0; // PID controller for leveling
        public static final double QUICK_LEVEL_KI = 0.0;
        public static final double QUICK_LEVEL_KD = 0.5;
    }

    public static class SwerveModule {

        // Size of the robot chassis in meters
        public static final double WIDTH = 0.4445; // meters
        public static final double LENGTH = 0.4445; // meters
        // Distance of swerve modules from center of robot
        public static final double SWERVE_NS_POS = LENGTH / 2.0;
        public static final double SWERVE_WE_POS = WIDTH / 2.0;

        public static final double MAX_MPS = 6.0; // Max speed of robot (m/s)
        public static final double SLOW_MPS = 2.0; // Slow speed of robot (m/s)
        public static final double MAX_ANG_VEL = Math.PI; // Max rotation rate of robot (rads/s)
        public static final double SLOW_ANG_VEL = Math.PI; // Max rotation rate of robot (rads/s)

        public static final String CAN_BUS = "CANivore";
        public static final int NUM_MODULES = 4;

        public static final double kDt = 0.005;
        public static final OutliersTalon.Configuration CONFIG = new OutliersTalon.Configuration();
        public static final OutliersTalon.Configuration STEER_CONFIG = new OutliersTalon.Configuration();

        public static final double WHEEL_RADIUS = 0.0508;
        public static final double GEAR_RATIO_DRIVE_HIGH = 4.9;
        public static final double GEAR_RATIO_DRIVE_LOW = 9.6;
        public static final double GEAR_RATIO_STEER = (52 / 14) * (96 / 16);
        // public static
        final double MAX_SPEED = 0;

        public static final double kP = 5.0;
        public static final double kI = 0.0;
        public static final double kD = 0.0;

        // this is the motor config for the swerve motors
        static {
            CONFIG.TIME_OUT = 0.1;

            CONFIG.NEUTRAL_MODE = NeutralModeValue.Brake;
            CONFIG.INVERTED = InvertedValue.CounterClockwise_Positive;

            CONFIG.MAX_VOLTAGE = 12.0;

            CONFIG.MAX_STATOR_CURRENT = 120;
            CONFIG.MAX_CURRENT = 120;
            CONFIG.ENABLE_STATOR_CURRENT_LIMIT = true;
            CONFIG.CURRENT_DEADBAND = 0.1;
        }

        static {
            STEER_CONFIG.TIME_OUT = 0.1;

            STEER_CONFIG.NEUTRAL_MODE = NeutralModeValue.Brake;
            STEER_CONFIG.INVERTED = InvertedValue.CounterClockwise_Positive;

            STEER_CONFIG.MAX_VOLTAGE = 12.0;

            STEER_CONFIG.MAX_STATOR_CURRENT = 120;
            STEER_CONFIG.MAX_CURRENT = 120;
            STEER_CONFIG.ENABLE_STATOR_CURRENT_LIMIT = true;
            STEER_CONFIG.CURRENT_DEADBAND = 0.1;
        }

        public static final OutliersTalon.ClosedLoopConfiguration DRIVE_CONTROLLER_CONFIG = new OutliersTalon.ClosedLoopConfiguration();

        static {
            DRIVE_CONTROLLER_CONFIG.SLOT = 0;

            // use these PID values when shifted down
            DRIVE_CONTROLLER_CONFIG.kP = 11.0;// 11.0 //23.0
            DRIVE_CONTROLLER_CONFIG.kI = 0.0;
            DRIVE_CONTROLLER_CONFIG.kD = 0.02;
            DRIVE_CONTROLLER_CONFIG.kF = 0.0;
            // use these PID values when shifted up
            DRIVE_CONTROLLER_CONFIG.kP1 = 11.0;
            DRIVE_CONTROLLER_CONFIG.kI1 = 0;
            DRIVE_CONTROLLER_CONFIG.kD1 = 0.02;

            DRIVE_CONTROLLER_CONFIG.kF1 = 0.0;

            DRIVE_CONTROLLER_CONFIG.CRUISE_VELOCITY = 1000;
            DRIVE_CONTROLLER_CONFIG.ACCELERATION = 525;
            DRIVE_CONTROLLER_CONFIG.JERK = 1000;
        }
        public static final OutliersTalon.ClosedLoopConfiguration STEER_CONTROLLER_CONFIG = new OutliersTalon.ClosedLoopConfiguration();

        static {
            STEER_CONTROLLER_CONFIG.SLOT = 0;
            STEER_CONTROLLER_CONFIG.kP = 70; // 70
            STEER_CONTROLLER_CONFIG.kI = 0;
            STEER_CONTROLLER_CONFIG.kD = 0.7; // 0.7
            STEER_CONTROLLER_CONFIG.kF = 0.0;

            STEER_CONTROLLER_CONFIG.CRUISE_VELOCITY = 1000;
            STEER_CONTROLLER_CONFIG.ACCELERATION = 4000;
            STEER_CONTROLLER_CONFIG.JERK = 10000;

            STEER_CONTROLLER_CONFIG.IS_CONTINUOUS = true;
        }

    }

    public static class Auto {
        public static class FieldPoses {
            public static final Pose2d BLUE_NODE_ONE_GOAL = new Pose2d(BLUE_X_COORDINATE, 0.519, new Rotation2d());
            public static final Pose2d BLUE_NODE_TWO_GOAL = new Pose2d(BLUE_X_COORDINATE, 1.080, new Rotation2d());
            public static final Pose2d BLUE_NODE_THREE_GOAL = new Pose2d(BLUE_X_COORDINATE, 1.637, new Rotation2d());
            public static final Pose2d BLUE_NODE_FOUR_GOAL = new Pose2d(BLUE_X_COORDINATE, 2.195, new Rotation2d());
            public static final Pose2d BLUE_NODE_FIVE_GOAL = new Pose2d(BLUE_X_COORDINATE, 2.753, new Rotation2d());
            public static final Pose2d BLUE_NODE_SIX_GOAL = new Pose2d(BLUE_X_COORDINATE, 3.313, new Rotation2d());
            public static final Pose2d BLUE_NODE_SEVEN_GOAL = new Pose2d(BLUE_X_COORDINATE, 3.872, new Rotation2d());
            public static final Pose2d BLUE_NODE_EIGHT_GOAL = new Pose2d(BLUE_X_COORDINATE, 4.431, new Rotation2d());
            public static final Pose2d BLUE_NODE_NINE_GOAL = new Pose2d(BLUE_X_COORDINATE, 4.989, new Rotation2d());
            public static final Pose2d BLUE_BUMP_CENTER_GOAL = new Pose2d(3.988, 0.824, new Rotation2d());

            // left to right on red side
            public static final Pose2d RED_NODE_ONE_GOAL = new Pose2d(RED_X_COORDINATE, 0.519,
                    Rotation2d.fromDegrees(180));
            public static final Pose2d RED_NODE_TWO_GOAL = new Pose2d(RED_X_COORDINATE, 1.08,
                    Rotation2d.fromDegrees(180));
            public static final Pose2d RED_NODE_THREE_GOAL = new Pose2d(RED_X_COORDINATE, 1.637,
                    Rotation2d.fromDegrees(180));
            public static final Pose2d RED_NODE_FOUR_GOAL = new Pose2d(RED_X_COORDINATE, 2.195,
                    Rotation2d.fromDegrees(180));
            public static final Pose2d RED_NODE_FIVE_GOAL = new Pose2d(RED_X_COORDINATE, 2.753,
                    Rotation2d.fromDegrees(180));
            public static final Pose2d RED_NODE_SIX_GOAL = new Pose2d(RED_X_COORDINATE, 3.313,
                    Rotation2d.fromDegrees(180));
            public static final Pose2d RED_NODE_SEVEN_GOAL = new Pose2d(RED_X_COORDINATE, 3.872,
                    Rotation2d.fromDegrees(180));
            public static final Pose2d RED_NODE_EIGHT_GOAL = new Pose2d(RED_X_COORDINATE, 4.431,
                    Rotation2d.fromDegrees(180));
            public static final Pose2d RED_NODE_NINE_GOAL = new Pose2d(RED_X_COORDINATE, 4.989,
                    Rotation2d.fromDegrees(180));
            public static final Pose2d RED_BUMP_CENTER_GOAL = new Pose2d(12.67, 0.74, Rotation2d.fromDegrees(180));
            public static final Pose2d RED_NOBUMP_CENTER_GOAL = new Pose2d(13.329, 4.776, Rotation2d.fromDegrees(-180));

            public static final Pose2d BLUE_TARGET_ONE = new Pose2d(7.12, 4.6, new Rotation2d(Math.PI / 2.0));
            public static final Pose2d BLUE_TARGET_TWO = new Pose2d(7.12, 3.36, new Rotation2d(Math.PI / 2.0));
            public static final Pose2d BLUE_TARGET_THREE = new Pose2d(7.12, 2.15, new Rotation2d(Math.PI / 2.0));
            public static final Pose2d BLUE_TARGET_FOUR = new Pose2d(7.12, 0.92, new Rotation2d(Math.PI));

            public static final Pose2d RED_TARGET_FOUR = new Pose2d(9.562, 4.6, new Rotation2d(Math.PI));
            public static final Pose2d RED_TARGET_THREE = new Pose2d(9.562, 3.36, new Rotation2d(Math.PI / 2.0));
            public static final Pose2d RED_TARGET_TWO = new Pose2d(9.562, 2.15, new Rotation2d(Math.PI / 2.0));
            public static final Pose2d RED_TARGET_ONE = new Pose2d(9.562, 0.92, new Rotation2d(Math.PI)); // mechies
                                                                                                          // give us
                                                                                                          // magic
                                                                                                          // units.
        }

        public static final double RED_X_COORDINATE = 14.75;
        public static final double BLUE_X_COORDINATE = 1.795;

        public static final double RED_X_TRAJ_END_COORDINATE = 13.5;
        public static final double BLUE_X_TRAJ_END_COORDINATE = 3;

        public static final Pose2d STARTING_ONE = new Pose2d(1.820, 3.04, new Rotation2d());
        public static final Pose2d STARTING_CHARGING_STATION = new Pose2d(1.820, 4.025, new Rotation2d());
        public static final Pose2d STARTING_ONE_TEMP = new Pose2d(0, 0, new Rotation2d());

        public static class TrajectoryPoints {
            /*
             * public static class S {
             * public static final List<Pose2d> waypoints =
             * Arrays.asList(FieldPoses.POSE_1, FieldPoses.POSE_2, FieldPoses.POSE_3);
             * }
             */

            /*
             * public static class FIRST_TO_TARGET_ONE {
             * public static final List<Pose2d> waypoints = Arrays.asList(STARTING_ONE,
             * TARGET_ONE);
             * }
             */

            public static class Node1 {

                public static final List<Pose2d> BLUE_NODE_ONE_TRAJECTORY_ONE = Arrays.asList(
                        FieldPoses.BLUE_NODE_ONE_GOAL,
                        FieldPoses.BLUE_BUMP_CENTER_GOAL,
                        FieldPoses.BLUE_TARGET_ONE);
                public static final List<Pose2d> BLUE_NODE_ONE_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.BLUE_NODE_ONE_GOAL);
                public static final List<Pose2d> RED_NODE_ONE_TRAJECTORY_ONE = Arrays.asList(
                        FieldPoses.RED_NODE_ONE_GOAL,
                        FieldPoses.RED_BUMP_CENTER_GOAL,
                        FieldPoses.RED_TARGET_ONE);
                public static final List<Pose2d> RED_NODE_ONE_TRAJECTORY_TWO = Arrays.asList(FieldPoses.RED_TARGET_ONE,
                        FieldPoses.RED_BUMP_CENTER_GOAL,
                        FieldPoses.RED_NODE_ONE_GOAL);

            }

            public static class Node2 {

                public static final List<Pose2d> BLUE_NODE_TWO_TRAJECTORY_ONE = Arrays.asList(
                        FieldPoses.BLUE_NODE_TWO_GOAL,
                        FieldPoses.BLUE_BUMP_CENTER_GOAL,
                        FieldPoses.BLUE_TARGET_ONE);
                public static final List<Pose2d> BLUE_NODE_TWO_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.BLUE_NODE_TWO_GOAL);
                public static final List<Pose2d> RED_NODE_TWO_TRAJECTORY_ONE = Arrays.asList(
                        FieldPoses.RED_NODE_TWO_GOAL,
                        FieldPoses.RED_BUMP_CENTER_GOAL,
                        FieldPoses.RED_TARGET_ONE);
                public static final List<Pose2d> RED_NODE_TWO_TRAJECTORY_TWO = Arrays.asList(FieldPoses.RED_TARGET_ONE,
                        FieldPoses.RED_BUMP_CENTER_GOAL,
                        FieldPoses.RED_NODE_TWO_GOAL);
            }

            public static class Node3 {

                public static final List<Pose2d> BLUE_NODE_THREE_TRAJECTORY_ONE = Arrays.asList(
                        FieldPoses.BLUE_NODE_THREE_GOAL,
                        FieldPoses.BLUE_BUMP_CENTER_GOAL,
                        FieldPoses.BLUE_TARGET_ONE);
                public static final List<Pose2d> BLUE_NODE_THREE_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.BLUE_NODE_THREE_GOAL);
                public static final List<Pose2d> RED_NODE_THREE_TRAJECTORY_ONE = Arrays.asList(
                        FieldPoses.RED_NODE_THREE_GOAL,
                        FieldPoses.RED_BUMP_CENTER_GOAL,
                        FieldPoses.RED_TARGET_ONE);
                public static final List<Pose2d> RED_NODE_THREE_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.RED_NODE_THREE_GOAL);

            }

            public static class Node4 {

                public static final List<Pose2d> BLUE_NODE_FOUR_TRAJECTORY_ONE = Arrays
                        .asList(FieldPoses.BLUE_NODE_FOUR_GOAL);
                public static final List<Pose2d> BLUE_NODE_FOUR_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.BLUE_NODE_FOUR_GOAL);
                public static final List<Pose2d> RED_NODE_FOUR_TRAJECTORY_ONE = Arrays
                        .asList(FieldPoses.RED_NODE_FOUR_GOAL);
                public static final List<Pose2d> RED_NODE_FOUR_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.RED_NODE_FOUR_GOAL);
            }

            public static class Node5 {

                public static final List<Pose2d> BLUE_NODE_FIVE_TRAJECTORY_ONE = Arrays
                        .asList(FieldPoses.BLUE_NODE_FIVE_GOAL);
                public static final List<Pose2d> BLUE_NODE_FIVE_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.BLUE_NODE_FIVE_GOAL);
                public static final List<Pose2d> RED_NODE_FIVE_TRAJECTORY_ONE = Arrays
                        .asList(FieldPoses.RED_NODE_FIVE_GOAL);
                public static final List<Pose2d> RED_NODE_FIVE_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.RED_NODE_FIVE_GOAL);

            }

            public static class Node6 {

                public static final List<Pose2d> BLUE_NODE_SIX_TRAJECTORY_ONE = Arrays
                        .asList(FieldPoses.BLUE_NODE_SIX_GOAL);
                public static final List<Pose2d> BLUE_NODE_SIX_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.BLUE_NODE_SIX_GOAL);
                public static final List<Pose2d> RED_NODE_SIX_TRAJECTORY_ONE = Arrays
                        .asList(FieldPoses.RED_NODE_SIX_GOAL);
                public static final List<Pose2d> RED_NODE_SIX_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.RED_NODE_SIX_GOAL);

            }

            public static class Node7 {

                public static final List<Pose2d> BLUE_NODE_SEVEN_TRAJECTORY_ONE = Arrays
                        .asList(FieldPoses.BLUE_NODE_SEVEN_GOAL);
                public static final List<Pose2d> BLUE_NODE_SEVEN_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.BLUE_NODE_SEVEN_GOAL);
                public static final List<Pose2d> RED_NODE_SEVEN_TRAJECTORY_ONE = Arrays.asList(
                        FieldPoses.RED_NODE_SEVEN_GOAL,
                        FieldPoses.RED_NOBUMP_CENTER_GOAL,
                        FieldPoses.RED_TARGET_FOUR);
                public static final List<Pose2d> RED_NODE_SEVEN_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.RED_NODE_SEVEN_GOAL);

            }

            public static class Node8 {

                public static final List<Pose2d> BLUE_NODE_EIGHT_TRAJECTORY_ONE = Arrays.asList(
                        FieldPoses.BLUE_NODE_EIGHT_GOAL,

                        FieldPoses.BLUE_TARGET_ONE);
                public static final List<Pose2d> BLUE_NODE_EIGHT_TRAJECTORY_TWO = Arrays.asList(
                        FieldPoses.BLUE_TARGET_ONE,
                        FieldPoses.BLUE_NODE_EIGHT_GOAL);
                public static final List<Pose2d> RED_NODE_EIGHT_TRAJECTORY_ONE = Arrays.asList(
                        FieldPoses.RED_NODE_EIGHT_GOAL,
                        // FieldPoses.RED_NOBUMP_CENTER_GOAL,
                        FieldPoses.RED_TARGET_FOUR);
                public static final List<Pose2d> RED_NODE_EIGHT_TRAJECTORY_TWO = Arrays.asList(
                        FieldPoses.RED_TARGET_FOUR,
                        // FieldPoses.RED_NOBUMP_CENTER_GOAL,
                        FieldPoses.RED_NODE_EIGHT_GOAL);

            }

            public static class Node9 {

                public static final List<Pose2d> BLUE_NODE_NINE_TRAJECTORY_ONE = Arrays
                        .asList(FieldPoses.BLUE_NODE_NINE_GOAL);
                public static final List<Pose2d> BLUE_NODE_NINE_TRAJECTORY_TWO = Arrays
                        .asList(FieldPoses.BLUE_NODE_NINE_GOAL);
                public static final List<Pose2d> RED_NODE_NINE_TRAJECTORY_ONE = Arrays.asList(
                        FieldPoses.RED_NODE_NINE_GOAL,
                        FieldPoses.RED_NOBUMP_CENTER_GOAL,
                        FieldPoses.RED_TARGET_FOUR);
                public static final List<Pose2d> RED_NODE_NINE_TRAJECTORY_TWO = Arrays.asList(
                        FieldPoses.RED_TARGET_FOUR,
                        FieldPoses.RED_NOBUMP_CENTER_GOAL,
                        FieldPoses.RED_NODE_NINE_GOAL);
            }

        }
    }

    public static class Vision {
        public static final float Z_CAM_Z_OFFSET = 0.78111f;
        public static final float Z_CAM_Y_OFFSET = 0.17653f;
        public static final float Z_CAM_X_OFFSET = 0.03566f;

        public static final double VISION_kP = 3.0;
        public static final double VISION_kI = 0.0;
        public static final double VISION_kD = 0.2;
    }

    public static class VisionConfig {
        public static double STATE_STD_DEV_X = 0.01;
        public static double STATE_STD_DEV_Y = 0.01;
        public static double STATE_STD_DEV_ANGLE = Units.degreesToRadians(0.5); // imu deviations lower number to trust
                                                                                // more;

        public static double VISION_STD_DEV_X = 0.35;
        public static double VISION_STD_DEV_Y = 0.35;
        public static double VISION_STD_DEV_ANGLE = Units.degreesToRadians(70); // imu deviations lower number to trust
                                                                                // more;
    }
}
