package org.firstinspires.ftc.teamcode.VelocityVortex;

import android.util.Log;

import com.qualcomm.ftccommon.DbgLog;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by spmce on 11/14/2016.
 */
public class VelocityVortexAutonomous extends VelocityVortexAutoMeth {
    String messageForTel = null;
    private int timeCounter; // runs about 20 times a second
    //private int timeCounter2;
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
                timeCounter = 0;
                break;
            case 7: // moves forward to align for launcher shot
                drAngle = Math.PI/2;
                //drPower = maxSpeed;
                drPower = topSpeed;
                drivePow(drAngle, drPower, drIfBlue);
                try {
                    Thread.sleep(400);//.8 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                //if (timeCounter >= 15) {
                    state++;
                //}
                timeCounter++;
                break;
            case 8:
                timeCounter = 0;
                zeroDrive();
                state++;
                break;
            case 9:
                mLauncher.setPower(1);
                try {
                    Thread.sleep(1200); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                //if (timeCounter >= 23) {
                    state++;
                //}
                timeCounter++;
                break;
            case 10:
                mLauncher.setPower(0);
                timeCounter = 0;
                state++;
                break;
            case 11:
                mSweeper.setPower(.5);
                try {
                    Thread.sleep(1700); // 1.7 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                //if (timeCounter >= 33)
                    state++;
                //timeCounter++;
                break;
            case 12:
                timeCounter = 0;
                mSweeper.setPower(0);
                state++;
                break;
            case 13:
                mLauncher.setPower(1);
                try {
                    Thread.sleep(300); // .3 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                //if (timeCounter >= 5) {
                    state++;
                //}
                timeCounter++;
                break;
            case 14:
                timeCounter = 0;
                state++;
                break;
            case 15:
                mLauncher.setPower(0);
                try {
                    Thread.sleep(700); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                //if (timeCounter >= 13) {
                    state++;
                //}
                timeCounter++;
                break;
            case 16:
                timeCounter = 0;
                state++;
                break;
            case 17:
                mLauncher.setPower(1);
                try {
                    Thread.sleep(1200); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                //if (timeCounter >= 23) {
                    state++;
                //}
                timeCounter++;
                break;
            case 18:
                mLauncher.setPower(0);
                timeCounter = 0;
                state = 26;
                break;
            case 26: // moves to the other beacon without sensing anything
                drAngle = 0.2;
                drPower = topSpeed;
                drivePow(drAngle, drPower, drIfBlue);
                try {
                    Thread.sleep(700);//.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                //if (timeCounter >= 13) {
                    state++;
                //}
                timeCounter++;
                break;
            case 27:
                timeCounter = 0;
                state++;
                break;
            case 28: // continues program until the next beacon
                drAngle = 0.8;
                drPower = topSpeed;
                changeState = findLine(drAngle, drPower, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 29: // moves towards the beacon until it is 100 mm away
                drAngle = -Math.PI/2;
                changeState = untilDistance(drAngle,wallDistance,drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 30: //follows white line until robot reaches distance from beacon
                drAngle = Math.PI;
                //alignLin(drAngle, drIfBlue);
                changeState = alignLine(drAngle, drIfBlue);
                if (changeState)
                    state++;
                break;
            case 31: // slowly moves to the beacon
                drAngle = -Math.PI/2;
                changeState = untilPressed(drAngle, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 32: // presses the beacon button according to color
                changeState = pressBeacon(drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 33: // resets the beacon button pressers
                resetBeacon();
                state++;
                break;
            case 34: // moves to hit the ball
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
            case 35: // parks the robot
                //zeroDrive();
                state++;
                break;
            case 36:
                /*mLauncher.setPower(1);
                try {
                    Thread.sleep(2000); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }*/
                state++;
                break;
            case 37:
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
            case 38:
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
        telemetry.addData("timeCounter", timeCounter);
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
