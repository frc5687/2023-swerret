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
        _motor.configureClosedLoop(Constants.TURRET.CONTROLLER_CONFIG);

        _hall = new HallEffect(RobotMap.DIO.TURRET_HALL);
    } 

    /**
     * Points the turret in a direction mod 2pi.
     * For example, if you pass in 0.1 radians and the encoder is at 0.2 radians, it
     * will go backwards.
     * But, if the encoder is at 6.3 radians, it will go to 6.38, which is the same
     * heading but a different encoder angle.
     * This method keeps in mind the range of motion of the turret and will never
     * tangle the wires
     * 
     * @param targetHeading Target heading in radians.
     */
    public void setTurretHeadingMod2Pi(double targetHeading) {
        if (targetHeading < Math.PI * -2 || targetHeading > Math.PI * 2) {
            error("Turret heading can only be from -2pi to 2pi.");
        }
        double startingHeading = getTurretRotationRadians();

        // generate three possible headings
        // the left one is from [-2pi, 0)
        // the center one is from [0, 2pi)
        double rightHeading = targetHeading % (2 * Math.PI);
        double leftHeading = rightHeading - (2 * Math.PI);

        if (Math.abs(startingHeading - rightHeading) < Math.abs(startingHeading - leftHeading) && startingHeading + rightHeading < Units.degreesToRadians(300) || startingHeading + leftHeading < Units.degreesToRadians(-300)) {
            // right target is closer
            setTurretHeadingRaw(rightHeading);
            error("CHOSE RIGHT HEADING");
        } else {
            // left target is closer
            error("CHOSE LEFT HEADING");
            setTurretHeadingRaw(leftHeading);
        }

        // also check if the units work, this entire method is in degrees
    }

    public void setTurretHeadingRaw(double targetHeading) {
        if (targetHeading < Math.PI * -2 || targetHeading > Math.PI * 2) {
            error("Raw turret heading can only be from -2pi to 2pi.");
        }
        _motor.setMotionMagic(OutliersTalon.radiansToRotations(targetHeading, Constants.TURRET.GEAR_RATIO));
    }

    public void setSpeed(double speed){
        _motor.set(speed);
    }

    public boolean getHall() {
        return _hall.get();
    }

    public boolean getHasZeroed() {
        return _hasZeroed;
    }
    
    public boolean getPastUpLimit(){
        return _pastUpLimit;
    }

    public void setPastUpLimit(boolean value){
        _pastUpLimit = value;
    }

    public boolean getPastDownLimit(){
        return _pastDownLimit;
    }

    public void setPastDownLimit(boolean value){
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
                getEncoderPositionRotations(), Constants.TURRET.GEAR_RATIO);
    }

    public enum TurretState{
        MANUAL(0),
        AUTOMATIC(1);

        private final int _value;

        TurretState(int value){
            _value = value;
        }

        public int getValue(){
            return _value;
        }

    }

    @Override
    public void updateDashboard() {
        metric("Encoder Position", getEncoderPositionRotations());
        metric("Encoder Radians", getEncoderRotationRadians());
        metric("Turret Radians", getTurretRotationRadians());
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

        // Should the turret spin past, tells the console and sets the boolean to true depending on which direction - Simeon
        if (getTurretRotationRadians() > (Units.degreesToRadians(200))){
            // error("BRO TURN BACK ITS TOO FAR UP!!");
            // setPastUpLimit(true);
        } else {
            // setPastUpLimit(false);
        }

        if (getTurretRotationRadians() < (Units.degreesToRadians(-200))){
            // error("BRO TURN BACK ITS TOO FAR DOWN!!");
            // setPastDownLimit(true);
        } else {
            // setPastDownLimit(false);
        }
    }

}
