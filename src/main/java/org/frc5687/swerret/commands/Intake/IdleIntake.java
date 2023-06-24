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

        new SetSpeed(_intake, Constants.Intake.IDLESPEED);

    }
}
