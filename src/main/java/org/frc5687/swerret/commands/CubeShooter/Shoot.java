package org.frc5687.swerret.commands.CubeShooter;

import org.frc5687.swerret.Constants;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.CubeShooter;

public class Shoot extends OutliersCommand {
    private CubeShooter _cubeShooter;

    public Shoot(CubeShooter cubeShooter) {
        _cubeShooter = cubeShooter;
        addRequirements(_cubeShooter);
    }

    @Override
    public void initialize() {
        // _cubeShooter.setShooterAngleRadians(Constants.Shooter.SHOOT_ANGLE_RAD);
        _cubeShooter.setRollerSpeed(Constants.Shooter.SHOOT_ROLLER_SPEED);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        error("shoot done" + interrupted);
    }
}