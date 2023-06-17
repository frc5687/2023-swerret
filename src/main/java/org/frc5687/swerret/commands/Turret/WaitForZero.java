package org.frc5687.swerret.commands.Turret;

import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.Turret;

public class WaitForZero extends OutliersCommand {

    private Turret _turret;

    public WaitForZero(Turret turret) {
        _turret = turret;
    }

    @Override
    public boolean isFinished() {
        return _turret.getHasZeroed();
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            error("Unable to calibrate turret. Please point it to the zero position and reset the robot.");
        }
    }
}