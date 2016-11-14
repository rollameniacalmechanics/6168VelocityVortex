package org.firstinspires.ftc.teamcode.VelocityVortex;

import android.hardware.Sensor;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRRangeSensor;

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
    protected DcMotor mSweeper; // Sweeper motor
    // Servos
    //protected Servo sLeftBeacon;
    //protected Servo sRightBeacon;
    // Sensors
    //protected TouchSensor touch;
    //protected ColorSensor color1;
    //protected LightSensor light1;
    //protected LightSensor light2;
    //protected GyroSensor gyro;
    //protected RangeSe range;
    //------------initial positions------------
    // ADD INITIAL POWER AND POSITIONS VARIABLES HERE:
    // DcMotors - Initial Power
    private double initLeftDrivePower = 0;
    private double initRightDrivePower = 0;
    private double initBackLeftPower = 0;
    private double initBackRightPower = 0;
    private double initSweeperPower = 0;
    // Servos - Initial Positions
    private double initLeftBeacon = 0.9;
    private double initRightBeacon = 0.1;
    //------------loop positions------------
    // ADD LOOP POWER AND POSITION VARIABLES HERE:
    // DcMotors - Loop Power
    protected double leftDrivePower = 0;
    protected double rightDrivePower = 0;
    protected double backLeftPower = 0;
    protected double backRightPower = 0;
    protected double sweeperPower = 0;
    // Servos - Loop Positions
    protected double leftBeaconPosition = 0.9;
    protected double rightBeaconPosition = 0.1;
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
        //Warning message = new Warning();
        //message.initWarnings(); //Provide telemetry data to a class user
        // Hardware Map
        //Map hardware = new Map();
        // ADD HARDWARE MAP HERE;
        // DcMotors - Map
        //hardware.map(mFL,initLeftDrivePower);
        //hardware.map(mFR,initRightDrivePower,true); // "true" reverses motor direction
        //hardware.map(mBL,initBackLeftPower);
        //hardware.map(mBR,initBackRightPower,true); // "true" reverses motor direction
        // Servos - Map

        // Sensors - Map
        VelocityVortexMap hardware = new VelocityVortexMap();
        hardware.map();
    }
    //------------------------Loop------------------------
    /**
     * Loop
     */
    @Override
    public void loop() {

    }
}
