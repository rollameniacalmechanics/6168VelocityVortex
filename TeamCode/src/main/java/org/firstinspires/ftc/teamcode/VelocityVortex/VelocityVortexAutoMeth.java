package org.firstinspires.ftc.teamcode.VelocityVortex;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by spmce on 11/28/2016.
 */
public class VelocityVortexAutoMeth extends VelocityVortexHardware {

    protected final static double topSpeed = Math.sqrt(2);
    protected final static double maxSpeed = 1;
    protected final static double highSpeed = 0.75;
    protected final static double halfSpeed = 0.5;
    protected final static double slowSpeed = 0.4;
    protected final static double quarterSpeed = 0.35;
    protected final static double minSpeed = 0.31;
    protected final static double zeroSpeed = 0;
    
    private final static double light1value = 0.35;
    private final static double light2value = 0.39;
    private final static double light3value = 0.39;
    private final static double light4value = 0.38;

    private final static int notWorking = 255; // value when color sensor does not work
    private final static int beaconValue = 1; // min value to sense beacon
    private final static double pressLeftBeacon = 0.96;
    private final static double pressRightBeacon = 0;
    
    protected final static double minWallDistance = 5; // TODO find distance when touching wall
    
    private String messageForTel = null;
    protected int beaconCount;
    protected int maxBeaconCount;
    protected int numCount = 0;
    protected  int numClock = 0;
    protected  int numCountClock = 0;

    OmniWheelDrive drive = new OmniWheelDrive();
    private double[] power;
    protected int state = 0;
    protected int shooterState = 0;
    protected int beaconState = 0;
    protected double robotRotate = 0;
    protected double rangeDistance = 0;
    protected boolean overshoot = false;
    protected double overshootAngle = 0;
    protected double powAcc = 0;

    //@Override
    public void init() {
        super.init();
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
    protected boolean findLineR(double ang, double pow, boolean ifBlue) {
        double angle = ang;
        /*double value;
        if (ifBlue) {
            value = light1value;
        } else {
            angle = redAngle(ang);
            value = light2value;
        }*/
        double rotate = 0;
        if (!ifBlue) {
            angle = redAngle(ang);
            rotate = 0.03;
        }
        power = drive.drive(angle,pow,rotate);
        powerDrive(power);
        if (light1.getLightDetected() > light1value) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean findLine(double ang, double pow, boolean ifBlue) {
        double angle = ang;
        /*double value;
        if (ifBlue) {
            value = light1value;
        } else {
            angle = redAngle(ang);
            value = light2value;
        }*/
        double rotate = 0;
        if (!ifBlue) {
            angle = redAngle(ang);
            rotate = .03;
        }
        power = drive.drive(angle,pow,rotate);
        powerDrive(power);
        if (light1.getLightDetected() > light1value) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected double distance() {
        double distance = range.getDistance(DistanceUnit.MM);
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
                robotRotate -= .0008;
            } else if (gyroHeading < rotate - 1) {
                robotRotate += .0008;
            }
            if (gyroRotate > .582) {
                robotRotate += .0006;
            } else if (gyroRotate < .542) {
                robotRotate -= .0006;
            }
        }
    }
    protected boolean vvFindLine(double ang, double pow, boolean ifBlue) {
        double angle = ang;
        double lightDetected;
        double value;
        if (ifBlue) {
            value = light2value;
            lightDetected = light2.getLightDetected();
        } else {
            angle = redAngle(ang);
            value = light1value;
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
            value = light4value;
            lightDetected = light4.getLightDetected();
        } else {
            angle = redAngle(ang);
            value = light3value;
            lightDetected = light3.getLightDetected();
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
        power = drive.drive(angle,slowSpeed,robotRotate);
        powerDrive(power);
        double distance = distance();
        if (distance <= mmDistance) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean untilDistance(double ang, double mmDistance, boolean ifBlue) {
        double angle = ang;
        double turn = 0;
        if (!ifBlue) {
            angle = redAngle(ang);
        } else {
            angle += .3;
            turn = .012;
        }
        power = drive.drive(angle,slowSpeed,turn);
        powerDrive(power);
        if (range.getDistance(DistanceUnit.MM) <= mmDistance) {
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
        power = drive.drive(angle,slowSpeed,turn);
        powerDrive(power);
        if (range.getDistance(DistanceUnit.MM) >= mmDistance) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean alignLine(double ang, boolean ifBlue, double turn) {
        return aLine(ang, ifBlue, turn);
    }
    protected boolean vvAlignLine(double ang, boolean ifBlue, int degreeTurn) {
        return aLine(ang, ifBlue, degreeTurn);
    }
    protected boolean alignLine(double ang, boolean ifBlue) {
        return aLine(ang, ifBlue, 0);
    }
    protected boolean vvAlignLine(double ang, boolean ifBlue) {
        return vvALine(ang, ifBlue, 0);
    }
    protected boolean aLine(double ang, boolean ifBlue, double turn) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        //if (count > 50 && count < 100)
            //angle = -angle;
        power = drive.drive(angle,minSpeed,turn);
        powerDrive(power);
        /*boolean part1 = false;
        boolean part2 = false;
        if (od.getLightDetected() > 0.5) {
            mFL.setPower(0);
            mFR.setPower(0);
            part1 = true;
        }
        if (light2.getLightDetected() > light2value) {
            mBL.setPower(0);
            mBR.setPower(0);
            part2 = true;
        }
        if (part1 && part2) {
            return true;
        }*/
        if (light2.getLightDetected() > light2value) {
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
            value = light2value;
            lightDetected = light2.getLightDetected();
            double val = light4value;
            double lDet = light4.getLightDetected();
            if (lDet > val && !overshoot) {
                angle = -angle;
                overshoot = true;
            }
        } else {
            angle = redAngle(ang);
            value = light1value;
            lightDetected = light1.getLightDetected();
            double val = light3value;
            double lDet = light3.getLightDetected();
            if (lDet > val && !overshoot) {
                angle = -angle;
                overshoot = true;
            }
        }
        gyroRotate(degreeTurn);
        power = drive.drive(angle,minSpeed,robotRotate);
        powerDrive(power);
        if (lightDetected > value) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean untilPressed(double pow, double ang, boolean ifBlue) {
        return uPressed(pow,ang,ifBlue);
    }
    protected boolean vvUntilPressed(double pow, double ang, boolean ifBlue) {
        return vvuPressed(pow,ang,ifBlue);
    }
    protected boolean untilPressed(double ang, boolean ifBlue) {
        return uPressed(slowSpeed,ang,ifBlue);
    }
    protected boolean vvUntilPressed(double ang, boolean ifBlue) {
        return vvuPressed(slowSpeed,ang,ifBlue);
    }
    protected boolean uPressed(double pow, double ang, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        power = drive.drive(angle, pow);
        powerDrive(power);
        if (touch.isPressed()) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean vvuPressed(double pow, double ang, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue) {
            angle = redAngle(ang);
        }
        double mPow = pow + powAcc;
        if (mPow > maxSpeed) {
            mPow = maxSpeed;
            if (distance() < minWallDistance) {
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
        if (color1.blue() == notWorking && color2.blue() == notWorking) {
            beaconCount++;
            return beaconCount > maxBeaconCount;
        } else if (color2.blue() == notWorking) {
            if (ifBlue) {
                return color1BlueBeacon();
            } else {
                return color1RedBeacon();
            }
        } else if (color1.blue() == notWorking) {
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
        if (color1.blue() > beaconValue)
            sLeftBeacon.setPosition(pressLeftBeacon);
        else if (color1.red() > beaconValue)
            sRightBeacon.setPosition(pressRightBeacon);
        boolean ifTrue = false;
        if (color1.blue() > beaconValue && color2.blue() > beaconValue)
            ifTrue = true;
        beaconCount++;
        if (beaconCount > maxBeaconCount)
            ifTrue = true;
        // completes case if the colors are the same
        return ifTrue;
    }
    private boolean redBeacon() {
        if (color1.red() > beaconValue)
            sLeftBeacon.setPosition(pressLeftBeacon);
        else if (color1.blue() > beaconValue)
            sRightBeacon.setPosition(pressRightBeacon);
        boolean ifTrue = false;
        if (color1.red() > beaconValue && color2.red() > beaconValue)
            ifTrue = true;
        beaconCount++;
        if (beaconCount > maxBeaconCount)
            ifTrue = true;
        // completes case if the colors are the same
        return ifTrue;
    }
    private boolean color1BlueBeacon() {
        if (color1.blue() > beaconValue)
            sLeftBeacon.setPosition(pressLeftBeacon);
        else if (color1.red() > beaconValue)
            sRightBeacon.setPosition(pressRightBeacon);
        beaconCount++;
        return beaconCount > maxBeaconCount;
    }
    private boolean color1RedBeacon() {
        if (color1.red() > beaconValue)
            sLeftBeacon.setPosition(pressLeftBeacon);
        else if (color1.blue() > beaconValue)
            sRightBeacon.setPosition(pressRightBeacon);
        beaconCount++;
        return beaconCount > maxBeaconCount;
    }
    private boolean color2BlueBeacon() {
        if (color2.red() > beaconValue)
            sLeftBeacon.setPosition(pressLeftBeacon);
        else if (color2.blue() > beaconValue)
            sRightBeacon.setPosition(pressRightBeacon);
        beaconCount++;
        return beaconCount > maxBeaconCount;
    }
    private boolean color2RedBeacon() {
        if (color2.blue() > beaconValue)
            sLeftBeacon.setPosition(pressLeftBeacon);
        else if (color2.red() > beaconValue)
            sRightBeacon.setPosition(pressRightBeacon);
        beaconCount++;
        return beaconCount > maxBeaconCount;
    }

    //*******************************************

    void driveForward(double pow) {
        powerDrive(drive.drive(Math.PI/2,pow));
    }

    boolean untilLight(boolean ifLight1) {
        return true;
    }
    //******************************************

    protected boolean shooter() {
        switch (state) {
            case 0:
                mLauncher.setPower(1);
                try {
                    Thread.sleep(800); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 1:
                mLauncher.setPower(0);
                sLoaderStopper.setPosition(openLoaderStopper);
                state++;
                break;
            case 2:
                mSweeper.setPower(.5);
                try {
                    Thread.sleep(1700); // 1.7 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 3:
                mSweeper.setPower(0);
                state++;
                break;
            case 4:
                mLauncher.setPower(1);
                try {
                    Thread.sleep(200); // .3 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 5:
                state++;
                break;
            case 6:
                mLauncher.setPower(0);
                try {
                    Thread.sleep(800); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 7:
                state++;
                break;
            case 8:
                mLauncher.setPower(1);
                try {
                    Thread.sleep(1200); // 2.95 seconds //I am commenting on your code to draw your attention to me... -Your secret admirer
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 9:
                mLauncher.setPower(0);
                return true;
            default:
                mLauncher.setPower(0);
                mSweeper.setPower(0);
                break;

        }
        return false;
    }
    protected boolean vvShooter() {
        boolean changeState;
        switch (shooterState) {
            case 0:
                mLauncher.setPower(1);
                changeState = mLauncher.getCurrentPosition() > 1600;
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
                changeState = mLauncher.getCurrentPosition() > 2000;
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
                changeState = mLauncher.getCurrentPosition() > 3200;
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
    protected boolean vvBeacon(double wallDistance, boolean drIfBlue) {
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
                beaconState++;
                return true;
            default:
                return true;
        }
        return false;
    }
}
