package org.frc5687.swerret.commands.Turret;

import org.frc5687.swerret.OI;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.Turret;

public class SetTurretSpeed extends OutliersCommand {
    private Turret _turret;
    private OI _oi;

    public SetTurretSpeed(Turret turret, OI oi){
        _oi = oi;
        _turret = turret;

        addRequirements(_turret);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void execute() {
        _turret.setSpeed(_oi.getTurretX() - 0.5);
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return super.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }
}
