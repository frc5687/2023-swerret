package org.frc5687.swerret.subsystems;

import org.frc5687.lib.drivers.OutliersTalon;
import org.frc5687.swerret.Constants;
import org.frc5687.swerret.RobotMap;
import org.frc5687.swerret.util.OutliersContainer;

public class CubeShooter extends OutliersSubsystem {
    private OutliersTalon _armMotor;
    private OutliersTalon _rollerMotor;

    public CubeShooter(OutliersContainer container) {
        super(container);
        _armMotor = new OutliersTalon(RobotMap.CAN.TALONFX.SHOOTER_ARM, "rio", "arm");
        _armMotor.configureClosedLoop(Constants.Shooter.CONTROLLER_CONFIG);
        _rollerMotor = new OutliersTalon(RobotMap.CAN.TALONFX.SHOOTER_ROLLER, "rio", "roller");
    }

    public double getArmEncoderPositionRotations() {
        return _armMotor.getPosition().getValue();
    }

    public double getArmEncoderPositionRadians() {
        return OutliersTalon.rotationsToRadians(getArmEncoderPositionRotations(), 1.0);
    }

    public double getArmPositionRadians() {
        return OutliersTalon.rotationsToRadians(
                getArmEncoderPositionRotations(), Constants.Shooter.ARM_GEAR_RATIO);
    }

    // public double getRollerEncoderPositionRotations() {
    // return _rollerMotor.getPosition().getValue();
    // }

    // public double getRollerEncoderRotationRadians() {
    // return OutliersTalon.rotationsToRadians(getRollerEncoderPositionRotations(),
    // Constants.Shooter.ROLLER_GEAR_RATIO);
    // }

    public void setShooterAngleRadians(double angle) {
        _armMotor.setMotionMagic(OutliersTalon.radiansToRotations(angle, Constants.Shooter.ARM_GEAR_RATIO));
    }

    public void setRollerSpeed(double speed) {
        _rollerMotor.setPercentOutput(speed);
    }

    @Override
    public void updateDashboard() {
        metric("Arm Encoder Radians", getArmEncoderPositionRadians());
        metric("Roller Speed", _rollerMotor.get());
    }
}
