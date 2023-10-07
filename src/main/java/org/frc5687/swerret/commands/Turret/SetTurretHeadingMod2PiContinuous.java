package org.frc5687.swerret.commands.Turret;

import org.frc5687.swerret.Constants;
import org.frc5687.swerret.OI;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.DriveTrain;
import org.frc5687.swerret.subsystems.Turret;

import edu.wpi.first.math.util.Units;

public class SetTurretHeadingMod2PiContinuous extends OutliersCommand {

    private DriveTrain _driveTrain;
    private Turret _turret;
    private OI _oi;
    private double _angle;

    public SetTurretHeadingMod2PiContinuous(DriveTrain drivetrain, Turret turret, OI oi) {
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
        _turret.setTurretHeadingRangeOfMotion(relativeTurretTargetRotation, 360.0);
    }

    // @Override
    // public boolean isFinished() {
    // return Math.abs(_turret.getEncoderRotationRadians() - _angle) <
    // Constants.Turret.ANGLE_TOLERANCE;
    // }
}
