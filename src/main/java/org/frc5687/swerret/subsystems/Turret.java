package org.frc5687.swerret.subsystems;

import org.frc5687.lib.drivers.OutliersTalon;
import org.frc5687.swerret.Constants;
import org.frc5687.swerret.RobotMap;
import org.frc5687.swerret.util.HallEffect;
import org.frc5687.swerret.util.Helpers;
import org.frc5687.swerret.util.OutliersContainer;

import edu.wpi.first.math.util.Units;

public class Turret extends OutliersSubsystem {
    private OutliersTalon _motor;
    private boolean _hasZeroed = false;
    private boolean _pastUpLimit = false;
    private boolean _pastDownLimit = false;
    private HallEffect _hall;

    public Turret(OutliersContainer container) {
        super(container);
        _motor = new OutliersTalon(RobotMap.CAN.TALONFX.TURRET, "rio", "Turret");
        _motor.configureClosedLoop(Constants.Turret.CONTROLLER_CONFIG);

        _hall = new HallEffect(RobotMap.DIO.TURRET_HALL);
    }

    /**
     * 
     * @param targetHeading the target heading in radians
     * @param rangeOfMotion the amount the turret can be off from zero (a value of
     *                      180 would mean that the turret can rotate +-180 from 0)
     */
    public void setTurretHeadingRangeOfMotion(double targetHeading, double rangeOfMotion) {
        double currentHeading = getTurretRotationRadians();
        double currentHeadingMod2Pi = currentHeading % (2 * Math.PI);
        double targetHeadingMod2Pi = targetHeading % (2 * Math.PI);
        double newTarget;
        if (currentHeadingMod2Pi > targetHeadingMod2Pi) {
            if (currentHeadingMod2Pi - targetHeadingMod2Pi < (2 * Math.PI) + targetHeadingMod2Pi
                    - currentHeadingMod2Pi) {
                newTarget = currentHeading + targetHeadingMod2Pi - currentHeadingMod2Pi;
            } else {
                newTarget = currentHeading + targetHeadingMod2Pi - currentHeadingMod2Pi + (2 * Math.PI);
            }
        } else {
            if (targetHeadingMod2Pi - currentHeadingMod2Pi < (2 * Math.PI) + currentHeadingMod2Pi
                    - targetHeadingMod2Pi) {
                newTarget = currentHeading + targetHeadingMod2Pi - currentHeadingMod2Pi;
            } else {
                newTarget = currentHeading + targetHeadingMod2Pi - currentHeadingMod2Pi - (2 * Math.PI);
            }
        }

        if (newTarget > rangeOfMotion) {
            newTarget -= (2 * Math.PI) * Math.ceil((newTarget - rangeOfMotion) / (2 * Math.PI));
        } else if (newTarget < -rangeOfMotion) {
            newTarget += (2 * Math.PI) * Math.ceil((-rangeOfMotion - newTarget) / (2 * Math.PI));
        }

        setTurretHeadingRaw(newTarget);
    }

    public void setTurretHeadingRaw(double targetHeading) {
        if (targetHeading < Math.PI * -4 || targetHeading > Math.PI * 4) {
            error("Raw turret heading can only be from -4pi to 4pi.");
        }
        _motor.setMotionMagic(OutliersTalon.radiansToRotations(targetHeading, Constants.Turret.GEAR_RATIO));
    }

    public void setSpeed(double speed) {
        _motor.set(speed);
    }

    public boolean getHall() {
        return _hall.get();
    }

    public boolean getHasZeroed() {
        return _hasZeroed;
    }

    public boolean getPastUpLimit() {
        return _pastUpLimit;
    }

    public void setPastUpLimit(boolean value) {
        _pastUpLimit = value;
    }

    public boolean getPastDownLimit() {
        return _pastDownLimit;
    }

    public void setPastDownLimit(boolean value) {
        _pastDownLimit = value;
    }

    public void zeroEncoder() {
        warn("Turret Zeroed");
        _motor.setRotorPosition(0);
        _hasZeroed = true;
    }

    public double getEncoderPositionRotations() {
        return _motor.getPosition().getValue();
    }

    public double getEncoderRotationRadians() {
        return OutliersTalon.rotationsToRadians(getEncoderPositionRotations(), 1.0);
    }

    public double getTurretRotationRadians() {
        return OutliersTalon.rotationsToRadians(
                getEncoderPositionRotations(), Constants.Turret.GEAR_RATIO);
    }

    public double getTurretTarget() {
        return Units.rotationsToRadians(_motor.getClosedLoopReference().getValue()) / Constants.Turret.GEAR_RATIO;
    }

    public enum TurretState {
        MANUAL(0),
        AUTOMATIC(1);

        private final int _value;

        TurretState(int value) {
            _value = value;
        }

        public int getValue() {
            return _value;
        }

    }

    @Override
    public void updateDashboard() {
        metric("Encoder Position", getEncoderPositionRotations());
        metric("Encoder Radians", getEncoderRotationRadians());
        metric("Turret Radians", getTurretRotationRadians());
        metric("Turret Degrees", Units.radiansToDegrees(getTurretRotationRadians()));
        metric("Turret Target Radians", getTurretTarget());
        metric("Turret Target Degrees", Units.radiansToDegrees(getTurretTarget()));
        metric("Turret Difference", getTurretTarget() - Units.radiansToDegrees(getTurretRotationRadians()));
        metric("Hall", getHall());
        metric("Motor Output", _motor.get());
        metric("Calibrated", _hasZeroed);
    }

    @Override
    public void periodic() {
        super.periodic();
        if (_hall.get()) {

            if (!_hasZeroed) {
                warn("Turret _hall activated. Encoder position pre-zero: " + getEncoderPositionRotations());
                zeroEncoder();
                setTurretHeadingRaw(0);
            }
        }

        // Should the turret spin past, tells the console and sets the boolean to true
        // depending on which direction - Simeon
        if (getTurretRotationRadians() > (Units.degreesToRadians(200))) {
            // error("BRO TURN BACK ITS TOO FAR UP!!");
            // setPastUpLimit(true);
        } else {
            // setPastUpLimit(false);
        }

        if (getTurretRotationRadians() < (Units.degreesToRadians(-200))) {
            // error("BRO TURN BACK ITS TOO FAR DOWN!!");
            // setPastDownLimit(true);
        } else {
            // setPastDownLimit(false);
        }
    }

}
