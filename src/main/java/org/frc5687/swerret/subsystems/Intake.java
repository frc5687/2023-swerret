package org.frc5687.swerret.subsystems;

import org.frc5687.lib.drivers.OutliersTalon;
import org.frc5687.swerret.RobotMap;
import org.frc5687.swerret.util.OutliersContainer;
import org.frc5687.lib.sensors.ProximitySensor;

public class Intake extends OutliersSubsystem {
    private OutliersTalon motor;
    private ProximitySensor proximitySensor;

    public Intake(OutliersContainer container) {
        super(container);
        motor = new OutliersTalon(RobotMap.CAN.TALONFX.INTAKE, "rio", "Intake");
        proximitySensor = new ProximitySensor(RobotMap.DIO.PROXIMITY_INTAKE);
    }

    /**
     * speed 0-1
     * 
     * @param percentOutput
     */
    public void setSpeed(double percentOutput) {
        motor.setPercentOutput(percentOutput);
    }

    public boolean getProximity() {
        return proximitySensor.get();
    }

    @Override
    public void updateDashboard() {
        metric("Proximity", getProximity());
        metric("Motor Output", motor.get());
    }
}