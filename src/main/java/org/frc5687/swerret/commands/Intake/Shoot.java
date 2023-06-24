package org.frc5687.swerret.commands.Intake;

import org.frc5687.swerret.Constants;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.Intake;

public class Shoot extends OutliersCommand {
    private Intake _intake;

    public Shoot(Intake intake) {
        _intake = intake;
    }

    @Override
    public void execute() {
        new SetSpeed(_intake, Constants.Intake.OUT_SPEED);
    }

   

    // end is not needed because ./IdleIntake overrides
}
