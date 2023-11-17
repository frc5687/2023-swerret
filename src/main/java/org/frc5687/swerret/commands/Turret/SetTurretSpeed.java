package org.frc5687.swerret.commands.Turret;

import org.frc5687.swerret.OI;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.Turret;

import edu.wpi.first.math.util.Units;

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
        // Will only spin manually between the limits, otherwise will automatically spin around to approximately the same angle the other way around. - Simeon
        if (!_turret.getPastDownLimit() && !_turret.getPastUpLimit()){
        _turret.setSpeed(_oi.getTurretX());
        } else if (_turret.getPastUpLimit()){
            _turret.setTurretHeadingMod2Pi(Units.degreesToRadians(-160));
        } else if (_turret.getPastDownLimit()){
            _turret.setTurretHeadingMod2Pi(Units.degreesToRadians(160));
        }
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
