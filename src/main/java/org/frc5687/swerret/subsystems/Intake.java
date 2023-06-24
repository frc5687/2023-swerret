package org.frc5687.swerret.subsystems;

import org.frc5687.lib.drivers.OutliersTalon;
import org.frc5687.swerret.Constants;
import org.frc5687.swerret.RobotMap;
import org.frc5687.swerret.util.OutliersContainer;
import org.frc5687.lib.sensors.ProximitySensor;

public class Intake extends OutliersSubsystem {
    private OutliersTalon _bottomMotor;
    private OutliersTalon _topMotor;
    private ProximitySensor _ProximitySensor;

    public Intake(OutliersContainer container) {
        super(container);
        _topMotor = new OutliersTalon(RobotMap.CAN.TALONFX.TOP_INTAKE, "rio", "Top Intake");
        _topMotor.configure(Constants.Intake.TOP_CONFIG);
        _topMotor.configureClosedLoop(Constants.Intake.CONTROLLER_CONFIG_TOP);
        _bottomMotor = new OutliersTalon(RobotMap.CAN.TALONFX.BOTTOM_INTAKE, "rio", "Bottom Intake");
        _bottomMotor.configure(Constants.Intake.BOTTOM_CONFIG);
        _bottomMotor.configureClosedLoop(Constants.Intake.CONTROLLER_CONFIG_BOTTOM);
        _ProximitySensor = new ProximitySensor(RobotMap.DIO.INTAKE_PROXIMITY_SENSOR);
    }

    /**
     * speed 0-1
     * 
     * @param percentOutput
     */
    public void setTopSpeed(double percentOutput) {
        _topMotor.setPercentOutput(percentOutput);
    }

    public void setBottomSpeed(double percentOutput) {
        _bottomMotor.setPercentOutput(percentOutput);
    }

    public boolean getProximity() {
        return _ProximitySensor.get();
    }

    @Override
    public void updateDashboard() {
        metric("Top Motor Output", _topMotor.get());
        metric("Bottom Motor Output", _bottomMotor.get());
    }
}