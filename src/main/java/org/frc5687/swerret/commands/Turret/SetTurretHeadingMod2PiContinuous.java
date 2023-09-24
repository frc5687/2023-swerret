package org.frc5687.swerret.commands.Turret;

import org.frc5687.swerret.Constants;
import org.frc5687.swerret.OI;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.Turret;

public class SetTurretHeadingMod2PiContinuous extends OutliersCommand{

    private Turret _turret;
    private OI _oi;
    private double _angle;
    
    public SetTurretHeadingMod2PiContinuous(Turret turret, OI oi) {
        _turret = turret;
        _oi = oi;
        addRequirements(turret);
    }

    @Override
    public void execute() {
        _turret.setTurretHeadingMod2Pi(_oi.getTurretHeading());
    }

    // @Override
    // public boolean isFinished() {
    //     return Math.abs(_turret.getEncoderRotationRadians() - _angle) < Constants.Turret.ANGLE_TOLERANCE;
    // }
}
