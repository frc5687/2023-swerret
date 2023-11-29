package org.frc5687.swerret.commands.CubeShooter;

import org.frc5687.lib.sensors.ProximitySensor;
import org.frc5687.swerret.Constants;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.CubeShooter;

public class Intake extends OutliersCommand {
    private CubeShooter _cubeShooter;

    public Intake(CubeShooter cubeShooter) {
        _cubeShooter = cubeShooter;
        addRequirements(_cubeShooter);
    }

    @Override
    public void initialize() {
        _cubeShooter.setShooterAngleRadians(Constants.Shooter.INTAKE_ANGLE_RAD);
        _cubeShooter.setRollerSpeed(Constants.Shooter.INTAKE_ROLLER_SPEED);
    }

    @Override
    public boolean isFinished() {
        return _cubeShooter.getProximitySensor();
    }
}
