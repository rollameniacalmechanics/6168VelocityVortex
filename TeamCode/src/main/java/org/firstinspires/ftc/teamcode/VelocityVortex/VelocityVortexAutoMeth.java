package org.firstinspires.ftc.teamcode.VelocityVortex;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by spmce on 11/28/2016.
 */
public class VelocityVortexAutoMeth extends VelocityVortexHardware {

    protected final static double topSpeed = Math.sqrt(2);
    protected final static double maxSpeed = 1;
    protected final static double halfSpeed = 0.5;
    protected final static double slowSpeed = 0.3;
    protected final static double quarterSpeed = 0.25;
    protected final static double minSpeed = .22;
    private final static double light1value = 0.35;
    private final static double light2value = 0.38;
    private String messageForTel = null;

    OmniWheelDrive drive = new OmniWheelDrive();
    private double[] power;
    protected int state = 0;

    //@Override
    public void init() {
        super.init();
    }

    protected void drivePow(double ang, double pow, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        power = drive.drive(angle,pow);
        powerDrive(power);
    }
    protected boolean findLine(double ang, double pow, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        power = drive.drive(angle,pow);
        powerDrive(power);
        if (light1.getLightDetected() > light1value) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean untilDistance(double ang, double mmDistance, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        power = drive.drive(angle,quarterSpeed);
        powerDrive(power);
        if (range.getDistance(DistanceUnit.MM) <= mmDistance) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean alignLine(double ang, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        power = drive.drive(angle,quarterSpeed);
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
    protected boolean untilPressed(double ang, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        power = drive.drive(angle, slowSpeed);
        powerDrive(power);
        if (touch.isPressed()) {
            zeroDrive();
            return true;
        }
        return false;
    }
    protected boolean pressBeacon(boolean ifBlue) {
        if(ifBlue)
            return blueBeacon();
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
        /*telemetry.addData("fl", leftDrivePower);
        telemetry.addData("fr",rightDrivePower);
        telemetry.addData("bl", backLeftPower);
        telemetry.addData("br", backRightPower);*/
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
        /*telemetry.addData("fl", leftDrivePower);
        telemetry.addData("fr",rightDrivePower);
        telemetry.addData("bl", backLeftPower);
        telemetry.addData("br", backRightPower);*/
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
        if (color1.blue() > 1)
            sLeftBeacon.setPosition(.96);
        else
            sRightBeacon.setPosition(0);
        // completes case if the colors are the same
        return color1.blue() > 1 && color2.blue() > 1;
    }
    private boolean redBeacon() {
        if (color1.red() > 1)
            sLeftBeacon.setPosition(.96);
        else
            sRightBeacon.setPosition(0);
        // completes case if the colors are the same
        return color1.red() > 1 && color2.red() > 1;
    }
    /*protected void alignLin(double ang, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        power = drive.drive(angle,minSpeed);
        if (light2.getLightDetected() > light2value) {
            zeroDrive();
            state++;
        }
    }*/
}
