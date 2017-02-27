package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by spmce on 11/28/2016.
 */
public class VVAutoMeth extends VelocityVortexHardware {

    protected final static double TOP_SPEED = Math.sqrt(2);
    protected final static double MAX_SPEED = 1;
    protected final static double HIGH_SPEED = 0.75;
    protected final static double HALF_SPEED = 0.5;
    protected final static double SLOW_SPEED = 0.4;
    protected final static double QUARTER_SPEED = 0.35;
    protected final static double MIN_SPEED = 0.31;
    protected final static double ZERO_SPEED = 0;
    
    private final static double LIGHT_1_VALUE = 0.35;
    private final static double LIGHT_2_VALUE = 0.39;
    private final static double LIGHT_3_VALUE = 0.39;
    private final static double LIGHT_4_VALUE = 0.38;

    private final static int COLOR_NOT_WORKING = 255; // value when color sensor does not work
    private final static int MIN_BEACON_VALUE = 1; // min value to sense beacon
    private final static double PRESS_LEFT_BEACON = 0.96;
    private final static double PRESS_RIGHT_BEACON = 0;
    
    private final static double MIN_WALL_DISTANCE = 15;
    
    private int beaconCount = 0;
    private final static int MAX_BEACON_COUNT = 280;

    OmniWheelDrive drive = new OmniWheelDrive();
    private double[] power;
    protected int state = 0;
    private int shooterState = 0;
    private double robotRotate = 0;
    private boolean overshoot = false;
    private double powAcc = 0;

    //@Override
    public void init() {
        super.init();
        resetEncoders();
    }
    //@Override
    public void start() {
        super.init();
        runEncoders();
    }
    protected void resetEncoders() {
        mFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mSweeper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mLauncher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    protected void runEncoders() {
        mFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mSweeper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    protected void drivePow(double ang, double pow, boolean ifBlue) {
        dPow(ang, pow, ifBlue, 0, 0);
    }
    protected void drivePow(double ang, double pow, boolean ifBlue,double turn, boolean ifBlueTurn) {
        if(ifBlueTurn)
            dPow(ang,pow,ifBlue,turn,0);
        else
            dPow(ang,pow,ifBlue,0,turn);
    }
    protected void drivePow(double ang, double pow, boolean ifBlue, double turn) {
        dPow(ang,pow,ifBlue,turn,turn);
    }
    protected void dPow(double ang, double pow, boolean ifBlue, double blueTurn, double redTurn) {
        double angle = ang;
        double turn;
        if (!ifBlue) {
            angle = redAngle(ang);
            turn = .01 + redTurn;
        } else {
            turn = -.011 + blueTurn;
        }
        power = drive.drive(angle,pow,turn);
        powerDrive(power);
    }
    protected double distance() {
        double distance = range.getDistance(DistanceUnit.MM);
        double rangeDistance = 0;
        if (distance == rangeDistance) {
            telemetry.addData("rDistance",distance);
            distance = sonar3.getUltrasonicLevel()*10; //changes cm to mm
        }
        telemetry.addData("distance",distance);
        return distance;
    }
    protected void gyroRotate(int rotateDegrees) {
        gRotate(rotateDegrees);
    }
    protected void gyroRotate() {
        gRotate(0);
    }
    protected void gRotate(int rotate) {
        if (gyro.getHeading() != 361) {
            double gyroHeading = gyro.getHeading();
            double gyroRotate = nxtGyro.getRotationFraction();
            if (gyroHeading > 180) {
                gyroHeading = gyroHeading - 360;
            }
            if (gyroHeading > rotate + 1) {
                robotRotate -= .00004;
            } else if (gyroHeading < rotate - 1) {
                robotRotate += .00004;
            }
            if (gyroRotate > .572) {
                robotRotate -= .00006;
            } else if (gyroRotate < .552) {
                robotRotate += .00006;
            }
            if (gyroRotate > .582) {
                robotRotate -= .00002;
            } else if (gyroRotate < .542) {
                robotRotate += .00002;
            }
        }
        /*if (gyro.getHeading() != 361) {
            double gyroHeading = gyro.getHeading();
            double gyroRotate = nxtGyro.getRotationFraction();
            if (gyroHeading > 180) {
                gyroHeading = gyroHeading - 360;
            }
            if (gyroHeading > rotate + 1) {
                robotRotate -= .00008*gyroHeading;
            } else if (gyroHeading < rotate - 1) {
                robotRotate -= .00008*gyroHeading;
            } else {
                robotRotate = 0;
            }
            if (gyroRotate > .582) {
                robotRotate -= .0006;
            } else if (gyroRotate < .542) {
                robotRotate += .0006;
            }
            if (gyroRotate > .592) {
                robotRotate += .0002;
            } else if (gyroRotate < .532) {
                robotRotate -= .0002;
            }
        }*/
    }
    protected boolean vvFindLine(double ang, double pow, boolean ifBlue) {
        double angle = ang;
        double lightDetected;
        double value;
        if (ifBlue) {
            value = LIGHT_2_VALUE;
            lightDetected = light2.getLightDetected();
        } else {
            angle = redAngle(ang);
            value = LIGHT_1_VALUE;
            lightDetected = light1.getLightDetected();
        }
        gyroRotate();
        power = drive.drive(angle,pow,robotRotate);
        powerDrive(power);
        if (lightDetected > value) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean vvFindOutsideLine(double ang, double pow, boolean ifBlue) {
        double angle = ang;
        double lightDetected;
        double value;
        if (ifBlue) {
            value = LIGHT_3_VALUE;
            lightDetected = light3.getLightDetected();
        } else {
            angle = redAngle(ang);
            value = LIGHT_4_VALUE;
            lightDetected = light4.getLightDetected();
        }
        gyroRotate();
        power = drive.drive(angle,pow,robotRotate);
        powerDrive(power);
        if (lightDetected > value) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean vvUntilDistance(double ang, double mmDistance, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue) {
            angle = redAngle(ang);
        }
        gyroRotate();
        power = drive.drive(angle,SLOW_SPEED,robotRotate);
        powerDrive(power);
        double distance = distance();
        if (distance <= mmDistance) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean untilFarDistance(double ang, double mmDistance, boolean ifBlue) {
        double angle = ang;
        double turn = 0;
        if (!ifBlue) {
            angle = redAngle(ang);
        } else {
            angle += .3;
            turn = .012;
        }
        power = drive.drive(angle,SLOW_SPEED,turn);
        powerDrive(power);
        if (range.getDistance(DistanceUnit.MM) >= mmDistance) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean vvAlignLine(double ang, boolean ifBlue, int degreeTurn) {
        return aLine(ang, ifBlue, degreeTurn);
    }
    protected boolean vvAlignLine(double ang, boolean ifBlue) {
        return vvALine(ang, ifBlue, 0);
    }
    protected boolean aLine(double ang, boolean ifBlue, double turn) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        power = drive.drive(angle,MIN_SPEED,turn);
        powerDrive(power);
        if (light2.getLightDetected() > LIGHT_2_VALUE) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean vvALine(double ang, boolean ifBlue, int degreeTurn) {
        double angle = ang;
        boolean ifb = ifBlue;
        if (overshoot)
            ifb = !ifb;
        double value;
        double lightDetected;
        if (ifb) {
            value = LIGHT_2_VALUE;
            lightDetected = light2.getLightDetected();
            double val = LIGHT_4_VALUE;
            double lDet = light4.getLightDetected();
            if (lDet > val && !overshoot) {
                angle = -angle;
                overshoot = true;
            }
        } else {
            angle = redAngle(ang);
            value = LIGHT_1_VALUE;
            lightDetected = light1.getLightDetected();
            double val = LIGHT_3_VALUE;
            double lDet = light3.getLightDetected();
            if (lDet > val && !overshoot) {
                angle = -angle;
                overshoot = true;
            }
        }
        gyroRotate(degreeTurn);
        power = drive.drive(angle,MIN_SPEED,robotRotate);
        powerDrive(power);
        if (lightDetected > value) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean vvUntilPressed(double pow, double ang, boolean ifBlue) {
        return vvuPressed(pow,ang,ifBlue);
    }
    protected boolean vvUntilPressed(double ang, boolean ifBlue) {
        return vvuPressed(SLOW_SPEED,ang,ifBlue);
    }
    protected boolean vvuPressed(double pow, double ang, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue) {
            angle = redAngle(ang);
        }
        double mPow = pow + powAcc;
        if (mPow > MAX_SPEED) {
            mPow = MAX_SPEED;
            if (distance() < MIN_WALL_DISTANCE) {
                zeroDrive();
                powAcc = 0;
                return true;
            }
        }
        gyroRotate();
        power = drive.drive(angle, mPow,robotRotate);
        powerDrive(power);
        if (touch.isPressed()) {
            zeroDrive();
            powAcc = 0;
            return true;
        }
        powAcc += .001;
        return false;
    }
    protected boolean pressBeacon(boolean ifBlue) {
        if (color1.blue() == COLOR_NOT_WORKING && color2.blue() == COLOR_NOT_WORKING) {
            beaconCount++;
            return beaconCount > MAX_BEACON_COUNT;
        } else if (color2.blue() == COLOR_NOT_WORKING) {
            if (ifBlue) {
                return color1BlueBeacon();
            } else {
                return color1RedBeacon();
            }
        } else if (color1.blue() == COLOR_NOT_WORKING) {
            if (ifBlue) {
                return color2BlueBeacon();
            } else {
                return color2RedBeacon();
            }
        } else if (ifBlue) {
            return blueBeacon();
        }
        return redBeacon();
    }
    protected void resetBeacon() {
        sLeftBeacon.setPosition(initLeftBeacon);
        sRightBeacon.setPosition(initRightBeacon);
    }
    protected void powerDrive(double[] power) {
        leftDrivePower  = power[0];
        rightDrivePower = power[1];
        backRightPower  = power[2];
        backLeftPower   = power[3];
        mFL.setPower(leftDrivePower);
        mFR.setPower(rightDrivePower);
        mBR.setPower(backRightPower);
        mBL.setPower(backLeftPower);
    }
    protected void zeroDrive() {
        leftDrivePower  = 0;
        rightDrivePower = 0;
        backRightPower  = 0;
        backLeftPower   = 0;
        mFL.setPower(leftDrivePower);
        mFR.setPower(rightDrivePower);
        mBR.setPower(backRightPower);
        mBL.setPower(backLeftPower);
    }
    private double redAngle(double angle) {
        if (angle < 0)
            return -Math.PI - angle;
        return Math.PI - angle;
    }
    private boolean blueBeacon() {
        if (color1.blue() > MIN_BEACON_VALUE)
            sLeftBeacon.setPosition(PRESS_LEFT_BEACON);
        else if (color1.red() > MIN_BEACON_VALUE)
            sRightBeacon.setPosition(PRESS_RIGHT_BEACON);
        boolean ifTrue = false;
        if (color1.blue() > MIN_BEACON_VALUE && color2.blue() > MIN_BEACON_VALUE)
            ifTrue = true;
        beaconCount++;
        if (beaconCount > MAX_BEACON_COUNT)
            ifTrue = true;
        // completes case if the colors are the same
        return ifTrue;
    }
    private boolean redBeacon() {
        if (color1.red() > MIN_BEACON_VALUE)
            sLeftBeacon.setPosition(PRESS_LEFT_BEACON);
        else if (color1.blue() > MIN_BEACON_VALUE)
            sRightBeacon.setPosition(PRESS_RIGHT_BEACON);
        boolean ifTrue = false;
        if (color1.red() > MIN_BEACON_VALUE && color2.red() > MIN_BEACON_VALUE)
            ifTrue = true;
        beaconCount++;
        if (beaconCount > MAX_BEACON_COUNT)
            ifTrue = true;
        // completes case if the colors are the same
        return ifTrue;
    }
    private boolean color1BlueBeacon() {
        if (color1.blue() > MIN_BEACON_VALUE)
            sLeftBeacon.setPosition(PRESS_LEFT_BEACON);
        else if (color1.red() > MIN_BEACON_VALUE)
            sRightBeacon.setPosition(PRESS_RIGHT_BEACON);
        beaconCount++;
        return beaconCount > MAX_BEACON_COUNT;
    }
    private boolean color1RedBeacon() {
        if (color1.red() > MIN_BEACON_VALUE)
            sLeftBeacon.setPosition(PRESS_LEFT_BEACON);
        else if (color1.blue() > MIN_BEACON_VALUE)
            sRightBeacon.setPosition(PRESS_RIGHT_BEACON);
        beaconCount++;
        return beaconCount > MAX_BEACON_COUNT;
    }
    private boolean color2BlueBeacon() {
        if (color2.red() > MIN_BEACON_VALUE)
            sLeftBeacon.setPosition(PRESS_LEFT_BEACON);
        else if (color2.blue() > MIN_BEACON_VALUE)
            sRightBeacon.setPosition(PRESS_RIGHT_BEACON);
        beaconCount++;
        return beaconCount > MAX_BEACON_COUNT;
    }
    private boolean color2RedBeacon() {
        if (color2.blue() > MIN_BEACON_VALUE)
            sLeftBeacon.setPosition(PRESS_LEFT_BEACON);
        else if (color2.red() > MIN_BEACON_VALUE)
            sRightBeacon.setPosition(PRESS_RIGHT_BEACON);
        beaconCount++;
        return beaconCount > MAX_BEACON_COUNT;
    }

    //*******************************************

    void driveForward(double pow) {
        powerDrive(drive.drive(Math.PI/2,pow));
    }

    boolean untilLight(boolean ifLight1) {
        return true;
    }
    //******************************************
    
    protected boolean vvShooter() {
        boolean changeState;
        switch (shooterState) {
            case 0:
                mLauncher.setPower(1);
                changeState = mLauncher.getCurrentPosition() > 1680;
                if (changeState) {
                    shooterState++;
                }
                break;
            case 1:
                mLauncher.setPower(0);
                sLoaderStopper.setPosition(openLoaderStopper);
                shooterState++;
                break;
            case 2:
                mSweeper.setPower(.5);
                changeState = mSweeper.getCurrentPosition() > 3000;
                if (changeState) {
                    shooterState++;
                }
                break;
            case 3:
                mSweeper.setPower(0);
                shooterState++;
                break;
            case 4:
                mLauncher.setPower(1);
                changeState = mLauncher.getCurrentPosition() > 2400;
                if (changeState) {
                    shooterState++;
                }
                break;
            case 5:
                mLauncher.setPower(0);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                shooterState++;
                break;
            case 6:
                mLauncher.setPower(1);
                changeState = mLauncher.getCurrentPosition() > 3360;
                if (changeState) {
                    shooterState++;
                }
                break;
            case 7:
                mLauncher.setPower(0);
                return true;
            default:
                mLauncher.setPower(0);
                mSweeper.setPower(0);
                return true;
        }
        return false;
    }
    /*protected boolean vvBeacon(double wallDistance, boolean drIfBlue) {
        boolean changeState;
        double drAngle;
        switch (beaconState) {
            case 0: // moves towards the beacon until it is 150 mm away
                drAngle = -Math.PI/2;
                changeState = vvUntilDistance(drAngle, wallDistance, drIfBlue);
                if (changeState) {
                    beaconState++;
                }
                break;
            case 1: // aligns with the line
                drAngle = Math.PI;
                changeState = vvAlignLine(drAngle, drIfBlue);
                if (changeState) {
                    beaconState++;
                }
                break;
            case 2: // slowly moves to the button until button is pressed
                drAngle = -Math.PI/2;
                changeState = vvUntilPressed(drAngle, drIfBlue);
                if (changeState) {
                    beaconState++;
                }
                break;
            case 3: // presses the beacon button according to the color
                changeState = pressBeacon(drIfBlue);
                if (changeState) {
                    beaconState++;
                }
                break;
            case 4: // resets the beacon button pressers
                resetBeacon();
                return true;
            default:
                return true;
        }
        return false;
    }*/
}
