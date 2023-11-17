package org.frc5687.swerret.commands.Intake;

import org.frc5687.swerret.Constants;
import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.Intake;

public class Shoot extends OutliersCommand {
    private Intake _intake;

    public Shoot(Intake intake) {
        _intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        _intake.setTopSpeed(-Constants.Intake.OUT_SPEED); 
        _intake.setBottomSpeed(-Constants.Intake.OUT_SPEED);
    }

   @Override
   public void end(boolean interrupted) {
       _intake.setTopSpeed(0);
       _intake.setBottomSpeed(0);
   }

    // end is not needed because ./IdleIntake overrides
}
