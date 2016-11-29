package org.firstinspires.ftc.teamcode.VelocityVortex;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by spmce on 11/14/2016.
 */
public class VelocityVortexAutonomous extends VelocityVortexHardware {

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

    private int state = 0;
    private final static double minSpeed = .22;
    private final static double maxSpeed = 1;
    private final static double topSpeed = Math.sqrt(2);
    private final static double light1value = 0.35;
    private final static double light2value = 0.38;
    private double[] power;
    OmniWheelDrive drive = new OmniWheelDrive();

    /**
     * @param drIfBlue
     */
    public void autoLoop(boolean drIfBlue) {
        double drPower;
        double drAngle;
        boolean changeState;
        double wallDistance = 100;
        switch (state) {
            case 0: // nothing - add reset encoders here
                state++;
                break;
            case 1: // drive until the light 1 hits the line
                drAngle = -Math.PI/4;
                drPower = maxSpeed;
                changeState = findLine(drAngle, drPower, drIfBlue);
                if (changeState)
                    state++;
                break;
            case 2: // moves towards the beacon until it is 100 mm away
                drAngle = -Math.PI/2;
                changeState = untilDistance(drAngle, wallDistance, drIfBlue);
                if (changeState)
                    state++;
                break;
            case 3: // aligns with the lion
                drAngle = Math.PI;
                changeState = alignLine(drAngle, drIfBlue);
                if (changeState)
                    state++;
                break;
            case 4: // slowly moves to the button until button is pressed
                drAngle = -Math.PI/2;
                changeState = untilPressed(drAngle, drIfBlue);
                if (changeState)
                    state++;
                break;
            case 5: // presses the beacon button according to the color
                changeState = pressBeacon(drIfBlue);
                if (changeState)
                    state++;
                break;
            case 6: // resets the beacon button pressers
                resetBeacon();
                state++;
                break;
            case 7: // moves to the other beacon without sensing anything
                drAngle = 0.01;
                drPower = topSpeed;
                drivePow(drAngle, drPower, drIfBlue);
                try {
                    Thread.sleep(200);//.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 8: // continues program until the next beacon
                drAngle = 0.01;
                drPower = topSpeed;
                changeState = findLine(drAngle, drPower, drIfBlue);
                if (changeState)
                    state++;
                break;
            case 9: // moves towards the beacon until it is 100 mm away
                drAngle = -Math.PI/2;
                changeState = untilDistance(drAngle,wallDistance,drIfBlue);
                if (changeState)
                    state++;
                break;
            case 10: //follows white line until robot reaches distance from beacon
                drAngle = Math.PI;
                changeState = alignLine(drAngle, drIfBlue);
                if (changeState)
                    state++;
                break;
            case 11: // slowly moves to the beacon
                drAngle = -Math.PI/2;
                changeState = untilPressed(drAngle, drIfBlue);
                if (changeState)
                    state++;
                break;
            case 12: // presses the beacon button according to color
                changeState = pressBeacon(drIfBlue);
                if (changeState)
                    state++;
                break;
            case 13: // resets the beacon button pressers
                resetBeacon();
                state++;
                break;
            case 14: // moves to hit the ball
                drAngle = 47*Math.PI/64;
                drPower = 1;
                drivePow(drAngle, drPower, drIfBlue);
                try {
                    Thread.sleep(2950); // 2.95 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 15: // parks the robot
                zeroDrive();
            default:
                break;
        }
        shanesTelemetry tele = new shanesTelemetry();
        tele.allTele(); // Update common telemetry
        telemetry.addData("25", "State: " + state);
    }
    void drivePow(double ang, double pow, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        power = drive.drive(angle,pow);
        powerDrive(power);
    }
    boolean findLine(double ang, double pow, boolean ifBlue) {
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
    boolean untilDistance(double ang, double mmDistance, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        power = drive.drive(angle,minSpeed);
        powerDrive(power);
        if (range.getDistance(DistanceUnit.MM) <= mmDistance) {
            zeroDrive();
            return true;
        }
        return false;
    }
    boolean alignLine(double ang, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        power = drive.drive(angle,minSpeed);
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
    boolean untilPressed(double ang, boolean ifBlue) {
        double angle = ang;
        if (!ifBlue)
            angle = redAngle(ang);
        power = drive.drive(angle, minSpeed);
        powerDrive(power);
        if (touch.isPressed()) {
            zeroDrive();
            return true;
        }
        return false;
    }
    boolean pressBeacon(boolean ifBlue) {
        if(ifBlue)
            return blueBeacon();
        return redBeacon();
    }
    boolean blueBeacon() {
        if (color1.blue() > 3)
            sLeftBeacon.setPosition(.96);
        else
            sRightBeacon.setPosition(0);
        // completes case if the colors are the same
        return color1.blue() > 3 && color2.blue() > 3;
    }
    boolean redBeacon() {
        if (color1.red() > 3)
            sLeftBeacon.setPosition(.96);
        else
            sRightBeacon.setPosition(0);
        // completes case if the colors are the same
        return color1.red() > 3 && color2.red() > 3;
    }
    void resetBeacon() {
        sLeftBeacon.setPosition(initLeftBeacon);
        sRightBeacon.setPosition(initRightBeacon);
    }
    private void powerDrive(double[] power) {
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
    }
    private void zeroDrive() {
        leftDrivePower  = 0;
        rightDrivePower = 0;
        backRightPower  = 0;
        backLeftPower   = 0;
        telemetry.addData("fl", leftDrivePower);
        telemetry.addData("fr",rightDrivePower);
        telemetry.addData("bl", backLeftPower);
        telemetry.addData("br", backRightPower);
        mFL.setPower(leftDrivePower);
        mFR.setPower(rightDrivePower);
        mBR.setPower(backRightPower);
        mBL.setPower(backLeftPower);
    }
    private double redAngle(double angle) {
        return Math.PI - angle;
    }
}
