package org.frc5687.swerret.commands.Turret;

import org.frc5687.swerret.subsystems.Turret;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TurretWiggle extends SequentialCommandGroup{

    public TurretWiggle(Turret turret) {
        addCommands(
            new ParallelDeadlineGroup(
                new WaitForZero(turret),
                new SequentialCommandGroup(
                    new SetTurretHeadingRaw(turret, Math.PI / 8),
                    new SetTurretHeadingRaw(turret, -Math.PI / 8)
                )
            )
        );
    }
    
}
