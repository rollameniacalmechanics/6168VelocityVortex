package org.firstinspires.ftc.teamcode.VelocityVortex;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by spmce on 11/18/2016.
 */
@Autonomous(name = "Auto Blue" , group = "Autonomous")
public class AutoBlue extends VelocityVortexHardware {
    //blue

    /**
     * Construct the class.
     * The system calls this member when the class is instantiated.
     */
    public AutoBlue() {
        // Initialize base classes and class members.
        // All via self-construction.
    } // AutoBlue

    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     * The system calls this member once when the OpMode is enabled.
     */
    public void init() {
        super.init();
    }
    @Override
    public void start() {
        super.start();
        //resetDriveEncoders();//motorLeftDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
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
    //@Override
    public void loop() {
        super.loop();
        OmniWheelDrive drive = new OmniWheelDrive();
        double[] power;
        switch (state) {
            // Synchronize the state machine and hardware.
            case 0:
                //resetDriveEncoders();//motorLeftDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
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
                //setDrivePower(power[0], power[1], power[2], power[3]);
                leftDrivePower  = power[0];
                rightDrivePower = power[1];
                backRightPower  = power[2];
                backLeftPower   = power[3];
                telemetry.addData("fl", leftDrivePower);
                telemetry.addData("fr",rightDrivePower);
                telemetry.addData("bl", backLeftPower);
                telemetry.addData("br", backRightPower);
                mFL.setPower(leftDrivePower);
                mFR.setPower(rightDrivePower);
                mBR.setPower(backRightPower);
                mBL.setPower(backLeftPower);
                // Have the motor shafts turned the required amount?
                // If they haven't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                if (light1.getLightDetected() > 0.35) {
                    // Reset the encoders.
                    //resetDriveEncoders();
                    // Stop the motors.
                    leftDrivePower  = 0;
                    rightDrivePower = 0;
                    backRightPower  = 0;
                    backLeftPower   = 0;
                    mFL.setPower(leftDrivePower);
                    mFR.setPower(rightDrivePower);
                    mBR.setPower(backRightPower);
                    mBL.setPower(backLeftPower);
                    // Transition to the next state when this method is called again.
                    state++;
                }
                break;

            // Wait...
            case 2:          // reset drive encoders
                power = drive.drive(Math.PI / 2, .25, false);
                //setDrivePower(power[0], power[1], power[2], power[3]);
                leftDrivePower  = power[0];
                rightDrivePower = power[1];
                backRightPower  = power[2];
                backLeftPower   = power[3];
                telemetry.addData("fl", leftDrivePower);
                telemetry.addData("fr",rightDrivePower);
                telemetry.addData("bl", backLeftPower);
                telemetry.addData("br", backRightPower);
                mFL.setPower(leftDrivePower);
                mFR.setPower(rightDrivePower);
                mBR.setPower(backRightPower);
                mBL.setPower(backLeftPower);
                // Have the motor shafts turned the required amount?
                // If they haven't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                if (range.getDistance(DistanceUnit.MM) <= 100) {
                    // Reset the encoders.
                    //resetDriveEncoders();
                    // Stop the motors.
                    leftDrivePower  = 0;
                    rightDrivePower = 0;
                    backRightPower  = 0;
                    backLeftPower   = 0;
                    mFL.setPower(leftDrivePower);
                    mFR.setPower(rightDrivePower);
                    mBR.setPower(backRightPower);
                    mBL.setPower(backLeftPower);
                    // Transition to the next state when this method is called again.
                    state++;
                }
                break;
            // Turn until the encoders exceed the specified values.
            case 3:                 //follows white line until robot reaches distance from beacon
                power = drive.drive(3.14,0.22,true);
                leftDrivePower  = power[0];
                rightDrivePower = power[1];
                backRightPower  = power[2];
                backLeftPower   = power[3];
                /*leftDrivePower  = .15;
                rightDrivePower = .15;
                backRightPower  = .15;
                backLeftPower   = .15;*/
                telemetry.addData("fl", leftDrivePower);
                telemetry.addData("fr",rightDrivePower);
                telemetry.addData("bl", backLeftPower);
                telemetry.addData("br", backRightPower);
                mFL.setPower(leftDrivePower);
                mFR.setPower(rightDrivePower);
                mBR.setPower(backRightPower);
                mBL.setPower(backLeftPower);
                boolean part1 = false;
                boolean part2= false;
                /*if (od.getLightDetected() > 0.5) {
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
                }*/
                if (light2.getLightDetected() > .38) {
                    leftDrivePower  = 0;
                    rightDrivePower = 0;
                    backRightPower  = 0;
                    backLeftPower   = 0;
                    mFL.setPower(leftDrivePower);
                    mFR.setPower(rightDrivePower);
                    mBR.setPower(backRightPower);
                    mBL.setPower(backLeftPower);
                    state++;
                }
                break;
            case 4:
                power = drive.drive(Math.PI / 2, .22, false);
                //setDrivePower(power[0], power[1], power[2], power[3]);
                leftDrivePower  = power[0];
                rightDrivePower = power[1];
                backRightPower  = power[2];
                backLeftPower   = power[3];
                telemetry.addData("fl", leftDrivePower);
                telemetry.addData("fr",rightDrivePower);
                telemetry.addData("bl", backLeftPower);
                telemetry.addData("br", backRightPower);
                mFL.setPower(leftDrivePower);
                mFR.setPower(rightDrivePower);
                mBR.setPower(backRightPower);
                mBL.setPower(backLeftPower);
                // Have the motor shafts turned the required amount?
                // If they haven't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                if (touch.isPressed()) {
                    // Reset the encoders.
                    //resetDriveEncoders();
                    // Stop the motors.
                    leftDrivePower  = 0;
                    rightDrivePower = 0;
                    backRightPower  = 0;
                    backLeftPower   = 0;
                    mFL.setPower(leftDrivePower);
                    mFR.setPower(rightDrivePower);
                    mBR.setPower(backRightPower);
                    mBL.setPower(backLeftPower);
                    // Transition to the next state when this method is called again.
                    state++;
                }
                break;
            case 5:
                if (color1.blue() > 3)
                    sLeftBeacon.setPosition(.96);
                else
                    sRightBeacon.setPosition(0);
                if (color1.blue() > 3 && color2.blue() > 3)
                    state++;
                break;
            case 6:
                sLeftBeacon.setPosition(initLeftBeacon);
                sRightBeacon.setPosition(initRightBeacon);
                state++;
                break;
            case 7:
                state++;
                break;
            case 8:
                power = drive.drive(.01, 1, false);
                //setDrivePower(power[0], power[1], power[2], power[3]);
                leftDrivePower  = power[0];
                rightDrivePower = power[1];
                backRightPower  = power[2];
                backLeftPower   = power[3];
                telemetry.addData("fl", leftDrivePower);
                telemetry.addData("fr",rightDrivePower);
                telemetry.addData("bl", backLeftPower);
                telemetry.addData("br", backRightPower);
                mFL.setPower(leftDrivePower);
                mFR.setPower(rightDrivePower);
                mBR.setPower(backRightPower);
                mBL.setPower(backLeftPower);
                // Have the motor shafts turned the required amount?
                // If they haven't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                if (light1.getLightDetected() > 0.35) {
                    // Reset the encoders.
                    //resetDriveEncoders();
                    // Stop the motors.
                    leftDrivePower  = 0;
                    rightDrivePower = 0;
                    backRightPower  = 0;
                    backLeftPower   = 0;
                    mFL.setPower(leftDrivePower);
                    mFR.setPower(rightDrivePower);
                    mBR.setPower(backRightPower);
                    mBL.setPower(backLeftPower);
                    // Transition to the next state when this method is called again.
                    state++;
                }
                break;
            case 9:
                power = drive.drive(Math.PI / 2, .25, false);
                //setDrivePower(power[0], power[1], power[2], power[3]);
                leftDrivePower  = power[0];
                rightDrivePower = power[1];
                backRightPower  = power[2];
                backLeftPower   = power[3];
                telemetry.addData("fl", leftDrivePower);
                telemetry.addData("fr",rightDrivePower);
                telemetry.addData("bl", backLeftPower);
                telemetry.addData("br", backRightPower);
                mFL.setPower(leftDrivePower);
                mFR.setPower(rightDrivePower);
                mBR.setPower(backRightPower);
                mBL.setPower(backLeftPower);
                // Have the motor shafts turned the required amount?
                // If they haven't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                if (range.getDistance(DistanceUnit.MM) <= 100) {
                    // Reset the encoders.
                    //resetDriveEncoders();
                    // Stop the motors.
                    leftDrivePower  = 0;
                    rightDrivePower = 0;
                    backRightPower  = 0;
                    backLeftPower   = 0;
                    mFL.setPower(leftDrivePower);
                    mFR.setPower(rightDrivePower);
                    mBR.setPower(backRightPower);
                    mBL.setPower(backLeftPower);
                    // Transition to the next state when this method is called again.
                    state++;
                }
                break;
            // Turn until the encoders exceed the specified values.
            case 10:                 //follows white line until robot reaches distance from beacon
                power = drive.drive(3.14,0.22,true);
                leftDrivePower  = power[0];
                rightDrivePower = power[1];
                backRightPower  = power[2];
                backLeftPower   = power[3];
                /*leftDrivePower  = .15;
                rightDrivePower = .15;
                backRightPower  = .15;
                backLeftPower   = .15;*/
                telemetry.addData("fl", leftDrivePower);
                telemetry.addData("fr",rightDrivePower);
                telemetry.addData("bl", backLeftPower);
                telemetry.addData("br", backRightPower);
                mFL.setPower(leftDrivePower);
                mFR.setPower(rightDrivePower);
                mBR.setPower(backRightPower);
                mBL.setPower(backLeftPower);
                //boolean part1 = false;
                //boolean part2= false;
                /*if (od.getLightDetected() > 0.5) {
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
                }*/
                if (light2.getLightDetected() > .38) {
                    leftDrivePower  = 0;
                    rightDrivePower = 0;
                    backRightPower  = 0;
                    backLeftPower   = 0;
                    mFL.setPower(leftDrivePower);
                    mFR.setPower(rightDrivePower);
                    mBR.setPower(backRightPower);
                    mBL.setPower(backLeftPower);
                    state++;
                }
                break;
            case 11:
                power = drive.drive(Math.PI / 2, .22, false);
                //setDrivePower(power[0], power[1], power[2], power[3]);
                leftDrivePower  = power[0];
                rightDrivePower = power[1];
                backRightPower  = power[2];
                backLeftPower   = power[3];
                telemetry.addData("fl", leftDrivePower);
                telemetry.addData("fr",rightDrivePower);
                telemetry.addData("bl", backLeftPower);
                telemetry.addData("br", backRightPower);
                mFL.setPower(leftDrivePower);
                mFR.setPower(rightDrivePower);
                mBR.setPower(backRightPower);
                mBL.setPower(backLeftPower);
                // Have the motor shafts turned the required amount?
                // If they haven't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                if (touch.isPressed()) {
                    // Reset the encoders.
                    //resetDriveEncoders();
                    // Stop the motors.
                    leftDrivePower  = 0;
                    rightDrivePower = 0;
                    backRightPower  = 0;
                    backLeftPower   = 0;
                    mFL.setPower(leftDrivePower);
                    mFR.setPower(rightDrivePower);
                    mBR.setPower(backRightPower);
                    mBL.setPower(backLeftPower);
                    // Transition to the next state when this method is called again.
                    state++;
                }
                break;
            case 12:
                if (color1.blue() > 3)
                    sLeftBeacon.setPosition(.96);
                else
                    sRightBeacon.setPosition(0);
                if (color1.blue() > 3 && color2.blue() > 3)
                    state++;
                break;
            case 13:
                sLeftBeacon.setPosition(initLeftBeacon);
                sRightBeacon.setPosition(initRightBeacon);
                state++;
                break;
            // Perform no action - stay in this case until the OpMode is stopped.
            // This method will still be called regardless of the state machine.
            default:
                // The autonomous actions have been accomplished (i.e. the state has
                // transitioned into its final state.
                break;
        }
        shanesTelemetry tele = new shanesTelemetry();
        tele.allTele(); // Update common telemetry
        motorTele();
        sensorTele();
        sensorTele();
        telemetry.addData("25", "State: " + state);

    }
    void motorTele() {
        telemetry.addData("fl", leftDrivePower);
        telemetry.addData("fr",rightDrivePower);
        telemetry.addData("bl", backLeftPower);
        telemetry.addData("br", backRightPower);
        telemetry.addData("sweeper", sweeperPower);
    }
    void servoTele() {
        telemetry.addData("rightBeacon", rightBeaconPosition);
        telemetry.addData("leftBeacon", leftBeaconPosition);
    }
    void sensorTele() {
        //telemetry.addData("touch", touch.isPressed());
        telemetry.addData("touch double", touch.getValue());
        telemetry.addData("light1", light1.getLightDetected());
        telemetry.addData("light2", light2.getLightDetected());
        telemetry.addData("color1 red", color1.red());
        telemetry.addData("color1 blue", color1.blue());
        telemetry.addData("color2 red", color2.red());
        telemetry.addData("color2 blue", color2.blue());
        telemetry.addData("gyro heading", gyro.getHeading());
        //telemetry.addData("gyro rotate",gyro.getRotationFraction());
        //telemetry.addData("gyro x",gyro.rawX());
        //telemetry.addData("gyro y",gyro.rawY());
        //telemetry.addData("gyro z",gyro.rawZ());
        telemetry.addData("range", range.getDistance(DistanceUnit.MM));
        telemetry.addData("optical distance", od.getLightDetected());
        telemetry.addData("optical distance", od.getLightDetected());
        telemetry.addData("", "");
    }


}

