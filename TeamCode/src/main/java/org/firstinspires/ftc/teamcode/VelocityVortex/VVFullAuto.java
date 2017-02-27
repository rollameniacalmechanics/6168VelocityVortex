package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by spmce on 2/24/2017.
 */

public class VVFullAuto extends VVAutoMeth {

    private int initLTime = 550;
    final double WALL_DISTANCE = 150; // in mm

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
    }

    /**
     * @param drIfBlue determines if blue or red autonomous
     * @param ifRamp determines to finish on ramp or center vortex platform
     */
    protected void autoLoop(boolean drIfBlue, boolean ifRamp) {
        double drPower;
        double drAngle;
        boolean changeState;
        double distance;
        switch (state) {
            case 0: // drive until the outside light hits the line
                drAngle = -Math.PI/4;
                drPower = MAX_SPEED;
                distance = distance(); // finds current distance from wall
                if (distance <= 3*WALL_DISTANCE) {
                    drAngle = -.03; // in radians - about -1.7 degrees
                    drPower = TOP_SPEED;
                }
                changeState = vvFindLine(drAngle, drPower, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 1: // moves towards the beacon until it is 150 mm away
                drAngle = -Math.PI/2;
                changeState = vvUntilDistance(drAngle, WALL_DISTANCE, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 2: // aligns with the line
                drAngle = Math.PI;
                changeState = vvAlignLine(drAngle, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 3: // slowly moves to the button until button is pressed
                drAngle = -Math.PI/2;
                changeState = vvUntilPressed(drAngle, drIfBlue);
                if (changeState) {
                    state++;
                }
                beaconCount = 0;
                break;
            case 4: // presses the beacon button according to the color
                changeState = pressBeacon(drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 5: // resets the beacon button pressers
                resetBeacon();
                state++;
                break;
            case 6: // moves forward to align for launcher shot
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
                drPower = TOP_SPEED;
                drivePow(drAngle, drPower, drIfBlue,robotRotate);
                try {
                    Thread.sleep(num);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 7: // stops the robot when ready to shoot balls
                zeroDrive();
                state++;
                break;
            case 8: // shoots the two balls
                changeState = vvShooter();
                if (changeState) {
                    state++;
                }
                break;
            case 9: // continues for line with outside light sensor
                drAngle = -Math.PI/8;
                drPower = TOP_SPEED;
                distance = distance(); // finds current distance from wall
                if (distance <= 3*WALL_DISTANCE) {
                    drAngle = -.03; // in radians - about -1.7 degrees
                }
                changeState = vvFindOutsideLine(drAngle, drPower, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 10: // continues for line with outside light sensor
                drAngle = -Math.PI/32;
                drPower = TOP_SPEED;
                distance = distance(); // finds current distance from wall
                if (distance <= 3*WALL_DISTANCE) {
                    drAngle = -.03; // in radians - about -1.7 degrees
                }
                changeState = vvFindLine(drAngle, drPower, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 11: // moves towards the beacon until it is 150 mm away
                drAngle = -Math.PI/2;
                changeState = vvUntilDistance(drAngle, WALL_DISTANCE, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 12: // aligns with the line
                drAngle = Math.PI;
                changeState = vvAlignLine(drAngle, drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 13: // slowly moves to the button until button is pressed
                drAngle = -Math.PI/2;
                changeState = vvUntilPressed(drAngle, drIfBlue);
                if (changeState) {
                    state++;
                }
                beaconCount = 0;
                break;
            case 14: // presses the beacon button according to the color
                changeState = pressBeacon(drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 15: // resets the beacon button pressers
                resetBeacon();
                state++;
                break;
            case 16:
                if (ifRamp) {
                    state = 21;
                } else {
                    state++;
                }
                break;
            case 17:
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
            case 18:
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
            case 19:
                drAngle = 121*Math.PI/128;
                changeState = untilFarDistance(drAngle,1130,drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 20:
                zeroDrive();
                break;
            case 21:
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
            case 22:
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
            case 23:
                drAngle = Math.PI;
                changeState = untilFarDistance(drAngle,1130,drIfBlue);
                if (changeState) {
                    state++;
                }
                break;
            case 24:
                zeroDrive();
                break;
            default:
                zeroDrive();
                break;
        }
        //allTele();
        telemetry.addData("25", "State: " + state);
        telemetry.addData("1vvRotate",robotRotate);
        telemetry.addData("26",beaconCount);
        sensorTele();
        encoderTele();
    }
    private void allTele() {
        motorTele();
        servoTele();
        sensorTele();
        encoderTele();
    }
    private void motorTele() {
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
    private void servoTele() {
        telemetry.addData("2 Servos", " rBeacon-" + format(rightBeaconPosition) +
                                        "  lBeacon-" + format(leftBeaconPosition) +
                                        "  loaderStopper-" + format(loaderStopperPosition));
        //telemetry.addData("rightBeacon", rightBeaconPosition);
        //telemetry.addData("leftBeacon", leftBeaconPosition);
    }
    private void sensorTele() {
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

    private void encoderTele() {
        telemetry.addData("fl encoder", mFL.getCurrentPosition());
        telemetry.addData("fr encoder", mFR.getCurrentPosition());
        telemetry.addData("bl encoder", mBL.getCurrentPosition());
        telemetry.addData("br encoder", mBR.getCurrentPosition());
    }

    private double format (double dec) {
        NumberFormat num = new DecimalFormat("#0.00");
        return Double.parseDouble(num.format(dec));
    }
    private double format1 (double dec) {
        NumberFormat num = new DecimalFormat("#0.00");
        return Double.parseDouble(num.format(dec));
    }
    private double format2 (double dec) {
        NumberFormat num = new DecimalFormat("#0000.00");
        return Double.parseDouble(num.format(dec));
    }
}
