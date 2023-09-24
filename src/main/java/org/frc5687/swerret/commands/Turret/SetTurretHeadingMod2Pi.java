package org.frc5687.swerret.commands.Turret;

import org.frc5687.swerret.Constants;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.Turret;

public class SetTurretHeadingMod2Pi extends OutliersCommand{

    private Turret _turret;
    private double _angle;
    
    public SetTurretHeadingMod2Pi(Turret turret, double angle) {
        _turret = turret;
        _angle = angle;
        addRequirements(turret);
    }

    @Override
    public void initialize() {
        _turret.setTurretHeadingMod2Pi(_angle);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(_turret.getEncoderRotationRadians() - _angle) < Constants.Turret.ANGLE_TOLERANCE;
    }
}
