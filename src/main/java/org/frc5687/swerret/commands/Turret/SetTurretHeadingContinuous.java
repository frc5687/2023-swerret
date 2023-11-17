package org.frc5687.swerret.commands.Turret;

import org.frc5687.swerret.Constants;
import org.frc5687.swerret.OI;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.DriveTrain;
import org.frc5687.swerret.subsystems.Turret;

import edu.wpi.first.math.util.Units;

public class SetTurretHeadingContinuous extends OutliersCommand {

    private DriveTrain _driveTrain;
    private Turret _turret;
    private OI _oi;
    private double _angle;

    public SetTurretHeadingContinuous(DriveTrain drivetrain, Turret turret, OI oi) {
        _driveTrain = drivetrain;
        _turret = turret;
        _oi = oi;
        addRequirements(turret);
    }

    @Override
    public void execute() {
        double absoluteTargetRotation = _oi.getTargetTurretHeading();
        // robot's rotation relative to the field
        double robotRotation = _driveTrain.getYaw();
        double relativeTurretTargetRotation = absoluteTargetRotation/*- robotRotation*/; // bypassed

        if (!_oi.isTargetWithinDeadband()) {
            _turret.setTurretHeadingRangeOfMotion(relativeTurretTargetRotation, Constants.Turret.RANGE_OF_MOTION);
        }
    }

    // @Override
    // public boolean isFinished() {
    // return Math.abs(_turret.getEncoderRotationRadians() - _angle) <
    // Constants.Turret.ANGLE_TOLERANCE;
    // }
}