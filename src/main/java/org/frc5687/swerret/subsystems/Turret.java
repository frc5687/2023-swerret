package org.frc5687.swerret.subsystems;

import org.frc5687.lib.drivers.OutliersTalon;
import org.frc5687.swerret.Constants;
import org.frc5687.swerret.RobotMap;
import org.frc5687.swerret.util.HallEffect;
import org.frc5687.swerret.util.Helpers;
import org.frc5687.swerret.util.OutliersContainer;

import edu.wpi.first.math.util.Units;

public class Turret extends OutliersSubsystem {
    private OutliersTalon motor;
    private boolean hasZeroed = false;
    private HallEffect hall;

    public Turret(OutliersContainer container) {
        super(container);
        motor = new OutliersTalon(RobotMap.CAN.TALONFX.TURRET, "rio", "Turret");
        motor.configureClosedLoop(Constants.TURRET.CONTROLLER_CONFIG);

        hall = new HallEffect(RobotMap.DIO.TURRET_HALL);
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

        if (Math.abs(startingHeading - rightHeading) < Math.abs(startingHeading - leftHeading)) {
            // right target is closer
            setTurretHeadingRaw(rightHeading);
        } else {
            // left target is closer
            setTurretHeadingRaw(leftHeading);
        }

        // also check if the units work, this entire method is in degrees
    }

    public void setTurretHeadingRaw(double targetHeading) {
        if (targetHeading < Math.PI * -2 || targetHeading > Math.PI * 2) {
            error("Raw turret heading can only be from -2pi to 2pi.");
        }
        motor.setMotionMagic(targetHeading * Constants.TURRET.GEAR_RATIO);
    }

    public void setSpeed(double speed){
        motor.set(speed);
    }

    public boolean getHall() {
        return hall.get();
    }

    public boolean getHasZeroed() {
        return hasZeroed;
    }

    public void zeroEncoder() {
        warn("Turret Zeroed");
        motor.setRotorPosition(0);
        hasZeroed = true;
    }

    public double getEncoderPositionRotations() {
        return motor.getPosition().getValue();
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
        metric("Motor Output", motor.get());
        metric("Calibrated", hasZeroed);
    }

    @Override
    public void periodic() {
        super.periodic();
        if (hall.get()) {

            if (!hasZeroed) {
                warn("Turret hall activated. Encoder position pre-zero: " + getEncoderPositionRotations());
                zeroEncoder();
                setTurretHeadingRaw(0);
            }
        }

        if (getTurretRotationRadians() > (Units.degreesToRadians(200))){
            error("BRO TURN BACK ITS TOO FAR UP!!");
            setTurretHeadingMod2Pi(-160 * Math.PI/180);
        }

        if (getTurretRotationRadians() < (Units.degreesToRadians(-200))){
            error("BRO TURN BACK ITS TOO FAR DOWN!!");
            setTurretHeadingMod2Pi(160 * Math.PI/180);
        }
    }

}
