package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by spmce on 2/24/2017.
 */

public class VVFullAuto extends VelocityVortexAutoMeth {

    /**
     * Construct the class.
     * The system calls this member when the class is instantiated.
     */
    public VVFullAuto() {
        // Initialize base classes and class members.
        // All via self-construction.
    }

    /**
     * Stops and Resets Motor Encoders
     */
    public void init() {
        super.init();
        mFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mSweeper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mLauncher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * Run sensor telemetry in init loop
     */
    @Override
    public void init_loop() {
        allTele();
    }

    /**
     * Sets Motors to run using encoders
     */
    @Override
    public void start() {
        super.start();
        mFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mSweeper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private int initLTime = 550;
    private double accel;
    /**
     * @param drIfBlue determines if blue or red autonomous
     */
    protected void autoLoop(boolean drIfBlue, boolean ifRamp) {
        double drPower;
        double drAngle;
        boolean changeState;
        double distance;
        final double wallDistance = 150; // in mm
        switch (state) {
            case 0: // drive until the outside light hits the line
                drAngle = -Math.PI/4;
                drPower = maxSpeed;
                distance = distance(); // finds current distance from wall
                if (distance <= 3*wallDistance) {
                    drAngle = -.03; // in radians - about -1.7 degrees
                    drPower = topSpeed;
                }
                changeState = vvFindLine(drAngle, drPower, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 1: // presses beacons
                changeState = vvBeacon(wallDistance,drIfBlue);
                if (changeState) {
                    state++;
                }
            case 2: // moves forward to align for launcher shot
                long num;
                //double turn;
                if(drIfBlue) {
                    num = (long) (initLTime * Math.sqrt(2));
                    drAngle = Math.PI/4;
                    gyroRotate();
                    //turn = .18;
                } else {
                    num = (long) (initLTime);
                    drAngle = Math.PI/2 + .11;
                    gyroRotate(5);
                    //turn = .35;
                }
                drPower = topSpeed;
                drivePow(drAngle, drPower, drIfBlue,robotRotate);
                try {
                    Thread.sleep(num);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 3: // stops the robot when ready to shoot balls
                zeroDrive();
                state++;
                break;
            case 4: // shoots the two balls
                changeState = vvShooter();
                if (changeState) {
                    state++;
                }
                break;
            case 5: // continues for line with outside light sensor
                drAngle = -Math.PI/8;
                drPower = topSpeed;
                distance = distance(); // finds current distance from wall
                if (distance <= 3*wallDistance) {
                    drAngle = -.03; // in radians - about -1.7 degrees
                }
                changeState = vvFindOutsideLine(drAngle, drPower, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 6: // continues for line with outside light sensor
                drAngle = -Math.PI/32;
                drPower = topSpeed;
                distance = distance(); // finds current distance from wall
                if (distance <= 3*wallDistance) {
                    drAngle = -.03; // in radians - about -1.7 degrees
                }
                changeState = vvFindLine(drAngle, drPower, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 7: // presses beacons
                changeState = vvBeacon(wallDistance,drIfBlue);
                if (changeState) {
                    state++;
                }
            case 8:
                if (ifRamp) {
                    state = 13;
                } else {
                    state++;
                }
            case 9:
                drAngle = Math.PI/2;
                drPower = 1;
                int timeNum = 80;
                if(drIfBlue) {
                    timeNum = 500;
                }
                drivePow(drAngle, drPower, drIfBlue,.11);
                try {
                    Thread.sleep(timeNum); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 10:
                drAngle = 104*Math.PI/128;
                if (!drIfBlue)
                    drAngle = 96*Math.PI/128;
                drPower = 1;
                drivePow(drAngle, drPower, drIfBlue, .07, true);
                try {
                    Thread.sleep(2600); // 2.95 seconds `````````````
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                if (!drIfBlue) {
                    try {
                        Thread.sleep(200); // 2.95 seconds `````````````
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                state++;
                break;
            case 11:
                drAngle = 121*Math.PI/128;
                changeState = untilFarDistance(drAngle,1130,drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 12:
                zeroDrive();
                break;
            case 13:
                drAngle = Math.PI/2;
                drPower = 1;
                timeNum = 600;
                if(drIfBlue) {
                    timeNum = 600;
                }
                drivePow(drAngle, drPower, drIfBlue,.11);
                try {
                    Thread.sleep(timeNum); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 14:
                drAngle = Math.PI;
                double myTurn = 0;
                if (!drIfBlue) {
                    drAngle = Math.PI;
                    myTurn = .07;
                }
                drPower = 1;
                drivePow(drAngle, drPower, drIfBlue, myTurn, true);
                try {
                    Thread.sleep(3900); // 2.95 seconds `````````````
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                if (!drIfBlue) {
                    try {
                        Thread.sleep(200); // 2.95 seconds `````````````
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                state = 40;
                break;
            case 15:
                drAngle = Math.PI;
                changeState = untilFarDistance(drAngle,1130,drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 16:
                zeroDrive();
                break;
            default:
                zeroDrive();
                break;
        }
        //allTele();
        telemetry.addData("25", "State: " + state);
        telemetry.addData("1vvRotate",robotRotate);
        telemetry.addData("1vvCount",numCount);
        telemetry.addData("1vvClock",numClock);
        telemetry.addData("1vvcClock",numCountClock);
        endoderTele();
    }
    void allTele() {
        motorTele();
        servoTele();
        sensorTele();
        endoderTele();
    }
    void motorTele() {
        telemetry.addData("1 Motors", "  fl-" + format1(leftDrivePower) +
                                    "   fr-" + format1(rightDrivePower) +
                                    "   bl-" + format1(backLeftPower) +
                                    "   br-" + format1(backRightPower) +
                                    "   sw-" + format1(sweeperPower) +
                                    "   lr-" + format1(launcherPower));
        //telemetry.addData("fl", leftDrivePower);
        //telemetry.addData("fr", rightDrivePower);
        //telemetry.addData("bl", backLeftPower);
        //telemetry.addData("br", backRightPower);
        //telemetry.addData("sweeper", sweeperPower);
    }
    void servoTele() {
        telemetry.addData("2 Servos", " rBeacon-" + format(rightBeaconPosition) +
                                        "  lBeacon-" + format(leftBeaconPosition) +
                                        "  loaderStopper-" + format(loaderStopperPosition));
        //telemetry.addData("rightBeacon", rightBeaconPosition);
        //telemetry.addData("leftBeacon", leftBeaconPosition);
    }
    void sensorTele() {
        telemetry.addData("3 Sensors", "");
        //telemetry.addData("touch", touch.isPressed());
        telemetry.addData("3.1 touch double", touch.getValue());
        telemetry.addData("3.2 light", "l1-" + format(light1.getLightDetected()) +
                " l2-" + format(light2.getLightDetected()) +
                " l3-" + format(light3.getLightDetected()) +
                " l4-" + format(light4.getLightDetected()));
        //telemetry.addData("light1", light1.getLightDetected());
        //telemetry.addData("light2", light2.getLightDetected());
        //telemetry.addData("light3", light3.getLightDetected());
        //telemetry.addData("light4", light4.getLightDetected());
        telemetry.addData("3.3 Color","c1 red-" + color1.red() +
                " c1 blue-" + color1.blue() +
                " c2 red-" + color2.red() +
                " c2 blue-" + color2.blue());
        //telemetry.addData("color1 red", color1.red());
        //telemetry.addData("color1 blue", color1.blue());
        //telemetry.addData("color2 red", color2.red());
        //telemetry.addData("color2 blue", color2.blue());
        telemetry.addData("3.4 Gyro","gyro heading-" + gyro.getHeading() +
                                         " gyro rotate-" + format(nxtGyro.getRotationFraction()));
        //telemetry.addData("gyro heading", gyro.getHeading());
        //telemetry.addData("gyro rotate",nxtGyro.getRotationFraction());
        telemetry.addData("3.5 Distances","range-" + format2(range.getDistance(DistanceUnit.MM)) +
                                            " s1-" + format2(sonar1.getUltrasonicLevel()*10) +
                " s2-" + format2(sonar2.getUltrasonicLevel()*10) +
                " s3-" + format2(sonar3.getUltrasonicLevel()*10));
        //telemetry.addData("range", range.getDistance(DistanceUnit.MM));
        telemetry.addData("", "");

        //telemetry.addData("0 count thingy", countThingy);
        //telemetry.addData("0 count ", count);
    }

    void endoderTele() {
        telemetry.addData("fl encoder", mFL.getCurrentPosition());
        telemetry.addData("fr encoder", mFR.getCurrentPosition());
        telemetry.addData("bl encoder", mBL.getCurrentPosition());
        telemetry.addData("br encoder", mBR.getCurrentPosition());
    }

    double format (double dec) {
        NumberFormat num = new DecimalFormat("#0.00");
        return Double.parseDouble(num.format(dec));
    }
    double format1 (double dec) {
        NumberFormat num = new DecimalFormat("#0.00");
        return Double.parseDouble(num.format(dec));
    }
    double format2 (double dec) {
        NumberFormat num = new DecimalFormat("#0000.00");
        return Double.parseDouble(num.format(dec));
    }
}
