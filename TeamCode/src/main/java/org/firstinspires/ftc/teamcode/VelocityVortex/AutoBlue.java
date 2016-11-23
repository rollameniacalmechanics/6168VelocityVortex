package org.firstinspires.ftc.teamcode.VelocityVortex;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by spmce on 11/18/2016.
 */
@Autonomous(name = "Autonomous Blue" , group = "Autonomous")
public class AutoBlue extends shanesTelemetry {
    //blue

    /**
     * Construct the class.
     * The system calls this member when the class is instantiated.
     */
    public AutoBlue() {
        // Initialize base classes and class members.
        // All via self-construction.
    } // PushBotAuto

    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     * The system calls this member once when the OpMode is enabled.
     */
    @Override
    public void start() {
        super.start();
        resetDriveEncoders();//motorLeftDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        //motorRightDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    /**
     * This class member remembers which state is currently active.  When the
     * start method is called, the state will be initialized (0).  When the loop
     * starts, the state will change from initialize to state_1.  When state_1
     * actions are complete, the state will change to state_2.  This implements
     * a state machine for the loop method.
     */
    private int state = 0;

    /**
     * Implement a state machine that controls the robot during auto-operation.
     * The state machine uses a class member and encoder input to transition
     * between states.
     * <p/>
     * The system calls this member repeatedly while the OpMode is running.
     */
    @Override
    public void loop() {
        OmniWheelDrive drive = new OmniWheelDrive();
        double[] power;
        switch (state) {
            // Synchronize the state machine and hardware.
            case 0:
                resetDriveEncoders();//motorLeftDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                //motorRightDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                state++;
                break;
            // Drive forward until the encoders exceed the specified values.
            case 1:
                // Tell the system that motor encoders will be used.  This call MUST
                // be in this state and NOT the previous or the encoders will not
                // work.  It doesn't need to be in subsequent states.
                //runUsingDriveEncoders ();//motorLeftDrive.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                //motorRightDrive.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                power = drive.drive(Math.PI / 4, 1, false);
                setDrivePower(power[0], power[1], power[2], power[3]);
                // Have the motor shafts turned the required amount?
                // If they haven't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                if (light1.getLightDetected() > 0.35) {
                    // Reset the encoders.
                    //resetDriveEncoders();
                    // Stop the motors.
                    setDrivePower(0, 0, 0, 0);
                    // Transition to the next state when this method is called again.
                    state++;
                }
                break;

            // Wait...
            case 2:          // reset drive encoders
                if (haveDriveEncodersReset())
                {
                    state++;
                }
                break;
            // Turn until the encoders exceed the specified values.
            case 3:                 //follows white line until robot reaches distance from beacon
                drive.drive(0,0.15,true);
                boolean part1 = false;
                boolean part2= false;
                if (od.getLightDetected() > 0.5) {
                    mFL.setPower(0);
                    mFR.setPower(0);
                    part1 = true;
                }
                if (light2.getLightDetected() > .35) {
                    mBL.setPower(0);
                    mBR.setPower(0);
                    part2 = true;
                }
                if (part1 && part2) {
                    state++;
                }
                break;
            /*case 4:
                if (haveDriveEncodersReset ())
                {
                    state++;
                }
                break;
            case 5:
                runUsingEncoders ();
                setDrivePower (0.2f, 0.2f, 0.2f, 0.2f);
                if (haveDriveEncodersReached (2200, 2200))
                {
                    resetDriveEncoders ();
                    setDrivePower (0.0f, 0.0f);
                    state++;
                }
                break;
            // Wait...
            case 6:
                if (haveDriveEncodersReset())
                {
                    state++;
                }
                break;
                */
            // Perform no action - stay in this case until the OpMode is stopped.
            // This method will still be called regardless of the state machine.
            default:
                // The autonomous actions have been accomplished (i.e. the state has
                // transitioned into its final state.
                break;
        }
        shanesTelemetry tele = new shanesTelemetry();
        tele.allTele(); // Update common telemetry
        telemetry.addData("25", "State: " + state);

        telemetry.addData("money=)=(=P",motorWarningMessage);

    }



}

