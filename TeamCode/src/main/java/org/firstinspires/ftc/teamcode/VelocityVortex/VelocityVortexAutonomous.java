package org.firstinspires.ftc.teamcode.VelocityVortex;

import android.util.Log;

import com.qualcomm.ftccommon.DbgLog;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by spmce on 11/14/2016.
 */
public class VelocityVortexAutonomous extends VelocityVortexAutoMeth {
    String messageForTel = null;
    /**
     * Construct the class.
     * The system calls this member when the class is instantiated.
     */
    public VelocityVortexAutonomous() {
        // Initialize base classes and class members.
        // All via self-construction.
    }

    /**
     * Init
     */
    public void init() {
        super.init();
    }

    /**
     * Run sensor telemetry in init loop
     */
    @Override
    public void init_loop() {
        //shanesTelemetry tele = new shanesTelemetry();
        //tele.sensorTele();
        allTele();
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

    //private int state = 0;

    /**
     * @param drIfBlue determines if blue or red autonomous
     */
    protected void autoLoop(boolean drIfBlue) {
        double drPower;
        double drAngle;
        boolean changeState;
        final double wallDistance = 100;

        switch (state) {
            case 0: // nothing - add reset encoders here
                messageForTel = "State 0 reached.";
                state++;
                break;
            case 1: // drive until the light 1 hits the line
                drAngle = -Math.PI/4;
                drPower = maxSpeed;
                messageForTel = "Finding line";
                changeState = findLine(drAngle, drPower, drIfBlue);
                //DbgLog.msg("ChangeState is %s", changeState);
                if (changeState) {
                    state++;
                    messageForTel = "Line found. Incremented state.";
                }
                break;
            case 2: // moves towards the beacon until it is 100 mm away
                drAngle = -Math.PI/2;
                changeState = untilDistance(drAngle, wallDistance, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 3: // aligns with the lion
                drAngle = Math.PI;
                //alignLin(drAngle, drIfBlue);
                messageForTel = "Aligning to line";
                changeState = alignLine(drAngle, drIfBlue);
                if (changeState) {
                    state++;
                    /*try {
                        Thread.sleep(10); // .01 seconds
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }*/
                }
                break;
            case 4: // slowly moves to the button until button is pressed
                drAngle = -Math.PI/2;
                changeState = untilPressed(drAngle, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 5: // presses the beacon button according to the color
                changeState = pressBeacon(drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 6: // resets the beacon button pressers
                resetBeacon();
                count = 0;
                state++;
                break;
            case 7: // moves to the other beacon without sensing anything
                drAngle = Math.PI/2;
                drPower = maxSpeed;
                drivePow(drAngle, drPower, drIfBlue);
                try {
                    Thread.sleep(800);//.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 8:
                zeroDrive();
                state++;
                break;
            case 9:
                mLauncher.setPower(1);
                try {
                    Thread.sleep(1200); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                mLauncher.setPower(0);
                state++;
            case 10:
                mSweeper.setPower(.5);
                try {
                    Thread.sleep(1700); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                mSweeper.setPower(0);
            case 11:
                mLauncher.setPower(1);
                try {
                    Thread.sleep(300); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                mLauncher.setPower(0);
                try {
                    Thread.sleep(700); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
            case 12:
                mLauncher.setPower(1);
                try {
                    Thread.sleep(1200); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                mLauncher.setPower(0);
                state = 17;
            case 17: // moves to the other beacon without sensing anything
                drAngle = 0;
                drPower = maxSpeed;
                drivePow(drAngle, drPower, drIfBlue);
                try {
                    Thread.sleep(700);//.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 18: // continues program until the next beacon
                drAngle = 0;
                drPower = topSpeed;
                changeState = findLine(drAngle, drPower, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 19: // moves towards the beacon until it is 100 mm away
                drAngle = -Math.PI/2;
                changeState = untilDistance(drAngle,wallDistance,drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 20: //follows white line until robot reaches distance from beacon
                drAngle = Math.PI;
                //alignLin(drAngle, drIfBlue);
                changeState = alignLine(drAngle, drIfBlue);
                if (changeState)
                    state++;
                break;
            case 21: // slowly moves to the beacon
                drAngle = -Math.PI/2;
                changeState = untilPressed(drAngle, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 22: // presses the beacon button according to color
                changeState = pressBeacon(drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 23: // resets the beacon button pressers
                resetBeacon();
                state++;
                break;
            case 24: // moves to hit the ball
                drAngle = 47*Math.PI/64;
                drPower = 1;
                drivePow(drAngle, drPower, drIfBlue);
                try {
                    Thread.sleep(700); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 25: // parks the robot
                zeroDrive();
                state++;
                break;
            case 26:
                /*mLauncher.setPower(1);
                try {
                    Thread.sleep(2000); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }*/
                state++;
                break;
            case 27:
                drAngle = 47*Math.PI/64;
                drPower = 1;
                drivePow(drAngle, drPower, drIfBlue);
                try {
                    Thread.sleep(2000); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 29:
                zeroDrive();
                break;
            default:
                zeroDrive();
                break;
        }
        //shanesTelemetry tele = new shanesTelemetry();
        //tele.allTele(); // Update common telemetry
        allTele();
        telemetry.addData("25", "State: " + state);
        telemetry.addData("DbgMsg", messageForTel);
        //DbgLog.msg("State: %d", state);
    }
    void allTele() {
        motorTele();
        servoTele();
        sensorTele();
    }
    void motorTele() {
        telemetry.addData("fl", leftDrivePower);
        telemetry.addData("fr", rightDrivePower);
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
