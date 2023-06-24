package org.frc5687.swerret.commands.Intake;

import org.frc5687.swerret.Constants;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.Intake;

public class IntakeIntake extends OutliersCommand {
    private Intake _intake;

    public IntakeIntake(Intake intake) {
        _intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        new SetSpeed(_intake, Constants.Intake.IN_SPEED);
    }

    @Override
    public boolean isFinished() {
        return _intake.getProximity();
    }

}
