package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by spmce on 11/28/2016.
 */
@Autonomous(name = "Velocity Vortex Auto Blue" , group = "Autonomous") //Register
public class VelocityVortexAutoBlue extends VelocityVortexHardware{

    /**
     * constructor
     */
    public VelocityVortexAutoBlue() {
        //constructor
    }

    /**
     * Init
     */
    //@Override
    public void init() {
        super.init();
    }

    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     * The system calls this member once when the OpMode is enabled.
     */
    @Override
    public void start() {
        super.start();
        //resetDriveEncoders();
        //motorLeftDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        //motorRightDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    /**
     * Loop
     */
    //@Override
    public void loop() {
        VelocityVortexAutonomous autonomous = new VelocityVortexAutonomous();
        autonomous.autoLoop(true);
    }
}
