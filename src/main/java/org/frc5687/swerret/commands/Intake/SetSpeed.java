package org.frc5687.swerret.commands.Intake;

import org.frc5687.swerret.commands.OutliersCommand;
import org.frc5687.swerret.subsystems.Intake;

public class SetSpeed extends OutliersCommand{

    private Intake _intake;
    private Double _speeed;

     public SetSpeed(Intake intake, double Speed){

        _intake = intake;

        
     }
     @Override
     public void execute() {
         _intake.setTopSpeed(_speeed);
         _intake.setBottomSpeed(-_speeed);
     }
}
