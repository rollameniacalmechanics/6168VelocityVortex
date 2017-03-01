package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.hardware.hitechnic.HiTechnicNxtGyroSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.TouchSensorMultiplexer;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.vuforia.ar.pl.SensorController;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRRangeSensor;

import android.hardware.Sensor;
import android.hardware.SensorManager;
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
    protected DcMotor mLauncher; // launcher motor
    // Servos
    protected Servo sLeftBeacon;
    protected Servo sRightBeacon;
    protected Servo sLoaderStopper;
    // Sensors
    protected TouchSensor touch;
    protected ColorSensor color1;
    protected ColorSensor color2;
    protected LightSensor light1;
    protected LightSensor light2;
    protected LightSensor light3;
    protected LightSensor light4;
    protected GyroSensor gyro;
    protected GyroSensor nxtGyro;
    protected ModernRoboticsI2cRangeSensor range;
    protected UltrasonicSensor sonar1;
    protected UltrasonicSensor sonar2;
    protected UltrasonicSensor sonar3;
    //protected OpticalDistanceSensor od;
    //protected TouchSensorMultiplexer multi;
    //protected SensorManager sm;
    DeviceInterfaceModule dim;
    //------------initial positions------------
    // ADD INITIAL POWER AND POSITIONS VARIABLES HERE:
    // DcMotors - Initial Power
    private double initLeftDrivePower = 0;
    private double initRightDrivePower = 0;
    private double initBackLeftPower = 0;
    private double initBackRightPower = 0;
    private double initSweeperPower = 0;
    private double initlauncherPower = 0;
    // Servos - Initial Positions
    protected double initLeftBeacon = 0.81;
    protected double initRightBeacon = 0.15;
    protected double initLoaderStopper = 0;
    protected double openLoaderStopper = 0.6;
    //------------loop positions------------
    // ADD LOOP POWER AND POSITION VARIABLES HERE:
    // DcMotors - Loop Power
    protected double leftDrivePower = 0;
    protected double rightDrivePower = 0;
    protected double backLeftPower = 0;
    protected double backRightPower = 0;
    protected double sweeperPower = 0;
    protected double launcherPower = 0;
    // Servos - Loop Positions
    protected double leftBeaconPosition;
    protected double rightBeaconPosition;
    protected double loaderStopperPosition;
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
        dim = hardwareMap.deviceInterfaceModule.get("DIM");
        dim.setLED(0,true);
        VelocityVortexTelemetry tele = new VelocityVortexTelemetry();
        tele.initTele();
        // Initialize Warnings Generated and Warning Messages
        Warning message = new Warning();
        message.initWarnings(); //Provide telemetry data to a class user
        //----------------------Map----------------------
        //Motors
        mFL = hardwareMap.dcMotor.get("fl");
        mFR = hardwareMap.dcMotor.get("fr");
        mFR.setDirection(DcMotorSimple.Direction.REVERSE);
        mBL = hardwareMap.dcMotor.get("bl");
        mBR = hardwareMap.dcMotor.get("br");
        mBR.setDirection(DcMotorSimple.Direction.REVERSE);
        mSweeper = hardwareMap.dcMotor.get("swpr");
        mLauncher = hardwareMap.dcMotor.get("Launcher");
        //Servos
        sLeftBeacon = hardwareMap.servo.get("sLeftButt");
        sRightBeacon = hardwareMap.servo.get("sRightButt");
        sLoaderStopper = hardwareMap.servo.get("sls");
        sLeftBeacon.setPosition(initLeftBeacon);
        sRightBeacon.setPosition(initRightBeacon);
        sLoaderStopper.setPosition(initLoaderStopper);
        //Sensors
        touch = hardwareMap.touchSensor.get("touch");
        color1 = hardwareMap.colorSensor.get("color1");
        color1.enableLed(false);
        color2 = hardwareMap.colorSensor.get("color2");
        color2.enableLed(false);
        light1 = hardwareMap.lightSensor.get("light1");
        light1.enableLed(true);
        light2 = hardwareMap.lightSensor.get("light2");
        light2.enableLed(true);
        light3 = hardwareMap.lightSensor.get("light3");
        light3.enableLed(true);
        light4 = hardwareMap.lightSensor.get("light4");
        light4.enableLed(true);
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        nxtGyro = hardwareMap.gyroSensor.get("nxtGyro");
        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class,"range");
        sonar1 = hardwareMap.ultrasonicSensor.get("sonar1");
        sonar2 = hardwareMap.ultrasonicSensor.get("sonar2");
        sonar3 = hardwareMap.ultrasonicSensor.get("sonar3");
        //od = hardwareMap.opticalDistanceSensor.get("od");
        //od.enableLed(true);
        mFL.setPower(0);
        mFR.setPower(0);
        mBL.setPower(0);
        mBL.setPower(0);
        telemetry.addData("gyro cal", gyro.isCalibrating());
        dim.setLED(1,true);
    }

    @Override
    public void init_loop() {
        super.init_loop();
        telemetry.addData("gyro cal", gyro.isCalibrating());
    }

    @Override
    public void start() {
        color1.setI2cAddress(I2cAddr.create8bit(0x6c));
        color1.enableLed(false);
        color2.setI2cAddress(I2cAddr.create8bit(0x3a));
        color2.enableLed(false);
    }

    //------------------------Loop------------------------
    /**
     * Loop
     */
    @Override
    public void loop() {

    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public void setMotorWarningMessage(String motorWarningMessage) {
        this.motorWarningMessage = motorWarningMessage;
    }

    public void setDriveWarningMessage(String driveWarningMessage) {
        this.driveWarningMessage = driveWarningMessage;
    }

    public void setServoWarningMessage(String servoWarningMessage) {
        this.servoWarningMessage = servoWarningMessage;
    }

    public void setSensorWarningMessage(String sensorWarningMessage) {
        this.sensorWarningMessage = sensorWarningMessage;
    }
}
