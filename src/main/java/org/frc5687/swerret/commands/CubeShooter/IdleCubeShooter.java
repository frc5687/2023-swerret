package org.frc5687.swerret.commands.CubeShooter;

import org.frc5687.swerret.Constants;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.CubeShooter;

public class IdleCubeShooter extends OutliersCommand {
    private CubeShooter _cubeShooter;
    public IdleCubeShooter(CubeShooter cubeShooter) {
        _cubeShooter = cubeShooter;
        addRequirements(_cubeShooter);
    }

    @Override
    public void initialize() {
        _cubeShooter.setShooterAngleRadians(Constants.Shooter.IDLE_ANGLE_RAD);
        _cubeShooter.setRollerSpeed(Constants.Shooter.IDLE_ROLLER_SPEED);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}