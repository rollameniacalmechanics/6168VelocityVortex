package org.firstinspires.ftc.teamcode.VelocityVortex;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by spmce on 11/14/2016.
 */
public class VelocityVortexAutonomous extends VelocityVortexAutoMeth {

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

    public void init_loop() {
        shanesTelemetry tele = new shanesTelemetry();
        tele.sensorTele();
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

    /**
     * @param drIfBlue
     */
    public void autoLoop(boolean drIfBlue) {
        double drPower;
        double drAngle;
        boolean changeState;
        final double wallDistance = 100;
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
}
