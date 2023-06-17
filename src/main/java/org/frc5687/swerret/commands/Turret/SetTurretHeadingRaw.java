package org.frc5687.swerret.commands.Turret;

import org.frc5687.swerret.Constants;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.Turret;

public class SetTurretHeadingRaw extends OutliersCommand{

    private Turret _turret;
    private double _angle;
    
    public SetTurretHeadingRaw(Turret turret, double angle) {
        _turret = turret;
        _angle = angle;
    }

    @Override
    public void initialize() {
        _turret.setTurretHeadingRaw(_angle);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(_turret.getEncoderRotationRadians() - _angle) < Constants.TURRET.ANGLE_TOLERANCE;
    }
}
