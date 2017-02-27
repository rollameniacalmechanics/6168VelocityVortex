package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by spmce on 2/25/2017.
 */
@TeleOp (name = "Telemetry", group = "TeleOp")
public class vvTele extends VelocityVortexHardware {
    @Override
    public void init() {
        allTele();
    }

    @Override
    public void loop() {
allTele();
    }

    void allTele() {
        //telemetry.addData("001101", pad1Config);
        motorTele();;
        servoTele();
        sensorTele();
        //encoderTele();
    }
    void motorTele() {
        telemetry.addData("fl", leftDrivePower);
        telemetry.addData("fr",rightDrivePower);
        telemetry.addData("bl", backLeftPower);
        telemetry.addData("br", backRightPower);
        telemetry.addData("sweeper",sweeperPower);
        telemetry.addData("launcher",launcherPower);
    }
    void servoTele() {
        telemetry.addData("rightBeacon", rightBeaconPosition);
        telemetry.addData("leftBeacon", leftBeaconPosition);
        telemetry.addData("loaderStopper", loaderStopperPosition);
    }
    void sensorTele() {
        //telemetry.addData("touch", touch.isPressed());
        telemetry.addData("touch double", touch.getValue());
        telemetry.addData("light1", format(light1.getLightDetected()));
        telemetry.addData("light2", format(light2.getLightDetected()));
        telemetry.addData("light3", format(light3.getLightDetected()));
        telemetry.addData("light4", format(light4.getLightDetected()));
        telemetry.addData("color1 red", color1.red());
        telemetry.addData("color1 blue", color1.blue());
        telemetry.addData("color2 red", color2.red());
        telemetry.addData("color2 blue", color2.blue());
        telemetry.addData("gyro heading", gyro.getHeading());
        telemetry.addData("gyro rotate",nxtGyro.getRotationFraction());
        telemetry.addData("range", range.getDistance(DistanceUnit.MM));
        telemetry.addData("sonar1", sonar1.getUltrasonicLevel());
        telemetry.addData("sonar2", sonar2.getUltrasonicLevel());
        telemetry.addData("sonar3", sonar3.getUltrasonicLevel());
        //telemetry.addData("optical distance", od.getLightDetected());
        telemetry.addData("", "");
    }
    double format (double dec) {
        NumberFormat num = new DecimalFormat("#0.00");
        return Double.parseDouble(num.format(dec));
    }
}
