package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by spmce on 11/17/2016.
 */
//@Autonomous(name = "Velocity Vortex Autonomous Blue" , group = "Autonomous")
public class AutonomousBlue extends VelocityVortexHardware {

    //@Override
    public void init() {
        super.init();
    }

    //@Override
    public void loop() {
        //super.loop();
        OmniWheelDrive drive = new OmniWheelDrive();
        double[] wheels;
        sensorTele();
        while (light1.getLightDetected() < 4) {
            wheels = drive.drive(Math.PI/4,1,false);
            setDrive(wheels);
            sensorTele();
        }
        wheels = drive.drive(0,0,true);
        setDrive(wheels);
        sensorTele();
        while (!touch.isPressed()) {
            wheels = drive.drive(3*Math.PI/4,.25,false);
            setDrive(wheels);
            sensorTele();
        }
        wheels = drive.drive(0,0,true);
        setDrive(wheels);
        sensorTele();
        while (color1.blue() > color1.red()) {
            rightBeaconPosition = 0;
            sRightBeacon.setPosition(rightBeaconPosition);
            sensorTele();
            if (color2.blue() > color2.red()) {
                return;
            } else {
                long wait = 200;
                try {
                    wait(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sensorTele();
                rightBeaconPosition = .15;
                sRightBeacon.setPosition(rightBeaconPosition);
            }
        }
        while (color1.red() > color1.blue()) {
            leftBeaconPosition = .97;
            sLeftBeacon.setPosition(leftBeaconPosition);
            sensorTele();
            if (color2.red() > color2.blue()) {
                return;
            } else {
                long wait = 200;
                try {
                    wait(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sensorTele();
                leftBeaconPosition = .72;
                sLeftBeacon.setPosition(leftBeaconPosition);
            }
        }
    }

    private void setDrive(double[] wheels) {
        leftDrivePower  = wheels[0];
        rightDrivePower = wheels[1];
        backRightPower  = wheels[2];
        backLeftPower   = wheels[3];
        mFL.setPower(leftDrivePower);
        mFR.setPower(rightDrivePower);
        mBR.setPower(backRightPower);
        mBL.setPower(backLeftPower);
    }
    void allTele() {
        motorTele();;
        servoTele();
        sensorTele();
    }
    void motorTele() {
        telemetry.addData("fl", leftDrivePower);
        telemetry.addData("fr",rightDrivePower);
        telemetry.addData("bl", backLeftPower);
        telemetry.addData("br", backRightPower);
        telemetry.addData("sweeper",sweeperPower);
    }
    void servoTele() {
        telemetry.addData("rightBeacon", rightBeaconPosition);
        telemetry.addData("leftBeacon", leftBeaconPosition);
    }
    void sensorTele() {
        telemetry.addData("touch",touch.isPressed());
        telemetry.addData("touch double",touch.getValue());
        telemetry.addData("light1",light1.getLightDetected());
        telemetry.addData("light2",light2.getLightDetected());
        telemetry.addData("color1 red",color1.red());
        telemetry.addData("color1 blue",color1.blue());
        telemetry.addData("color2 red",color2.red());
        telemetry.addData("color2 blue",color2.blue());
        telemetry.addData("gyro heading",gyro.getHeading());
        telemetry.addData("gyro rotate",gyro.getRotationFraction());
        telemetry.addData("gyro x",gyro.rawX());
        telemetry.addData("gyro y",gyro.rawY());
        telemetry.addData("gyro z",gyro.rawZ());
        telemetry.addData("range",range.getDistance(DistanceUnit.INCH));
        telemetry.addData("","");


    }
}
