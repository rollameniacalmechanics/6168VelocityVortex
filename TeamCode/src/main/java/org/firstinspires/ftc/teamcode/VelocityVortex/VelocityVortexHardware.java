package org.firstinspires.ftc.teamcode.VelocityVortex;

import android.hardware.Sensor;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by spmce on 11/3/2016.
 */
public class VelocityVortexHardware extends OpMode {
    /**
     * Velocity Vortex Hardware Constructor
     */
    VelocityVortexHardware () {
        // Constructor Code
    }
    //------------Hardware Devices------------
    // ADD HARDWARE DEVICES HERE:
    // DcMotors
    protected DcMotor mFL; // Front Left Drive Motor
    protected DcMotor mFR; // Front Right Drive Motor
    protected DcMotor mBL; // Back Left Drive Motor
    protected DcMotor mBR; // Back Right Drive Motor
    protected DcMotor mSwpr; // Sweeper motor
    // Servos

    // Sensors

    //------------initial positions------------
    // ADD INITIAL POWER AND POSITIONS VARIABLES HERE:
    // DcMotors - Initial Power
    private double initLeftDrivePower = 0;
    private double initRightDrivePower = 0;
    private double initBackLeftPower = 0;
    private double initBackRightPower = 0;
    private double initSweeperPower = 0;
    // Servos - Initial Positions

    //------------initial positions------------
    // ADD LOOP POWER AND POSITION VARIABLES HERE:
    // DcMotors - Loop Power
    protected double leftDrivePower = 0;
    protected double rightDrivePower = 0;
    protected double backLeftPower = 0;
    protected double backRightPower = 0;
    protected double SweeperPower = 0;
    // Servos - Loop Positions

    //------------Telemetry Warnings------------
    // Create message of warning if created
    protected String warningMessage = "";
    protected String motorWarningMessage = "";
    protected String driveWarningMessage = "";
    protected String servoWarningMessage = "";
    protected String sensorWarningMessage= "";
    //------------------------Init------------------------
    /**
     * Init
     */
    @Override
    public void init() {
        // Initialize Warnings Generated and Warning Messages
        Warning message = new Warning();
        message.initWarnings(); //Provide telemetry data to a class user
        // Hardware Map
        Map hardware = new Map();
        // ADD HARDWARE MAP HERE;
        // DcMotors - Map
        hardware.map(mFL,initLeftDrivePower);
        hardware.map(mFR,initRightDrivePower,true); // "true" reverses motor direction
        hardware.map(mBL,initBackLeftPower);
        hardware.map(mBR,initBackRightPower,true); // "true" reverses motor direction
        // Servos - Map

        // Sensors - Map

    }
    //------------------------Loop------------------------
    /**
     * Loop
     */
    @Override
    public void loop() {

    }
}
