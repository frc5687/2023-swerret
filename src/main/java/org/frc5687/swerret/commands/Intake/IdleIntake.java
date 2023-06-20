package org.frc5687.swerret.commands.Intake;

import org.frc5687.swerret.Constants;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.Intake;

public class IdleIntake extends OutliersCommand {
    private Intake _intake;

    public IdleIntake(Intake intake) {
        _intake = intake;
    }

    @Override
    public void execute() {
        super.execute();
        if (_intake.getProximity()) {
            // there is a cube!!!!! stop intaking you are going to break something
            _intake.setSpeed(0);
        } else {
            _intake.setSpeed(Constants.Intake.IN_SPEED);
        }

    }
}
