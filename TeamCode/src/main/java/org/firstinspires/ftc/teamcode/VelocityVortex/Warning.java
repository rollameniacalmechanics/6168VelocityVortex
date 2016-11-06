package org.firstinspires.ftc.teamcode.VelocityVortex;

/**
 * Created by spmce on 11/4/2016.
 */
public class Warning extends VelocityVortexHardware {
    //------------Telemetry Warnings------------
    //Determine if warning generated
    private boolean warningGenerated = false;
    private boolean motorWarningGenerated = false;
    private boolean driveWarningGenerated = false;
    private boolean servoWarningGenerated = false;
    private boolean sensorWarningGenerated = false;
    /**
     * Init
     */
    /*public void init(){
        super.init();
        //Initialize Warnings Generated and Warning Messages
        initWarnings(); //Provide telemetry data to a class user
    }*/
    //------------Init Warnings Method------------
    void initWarnings() {
        warningGenerated = false;
        warningMessage = "Can't map: ";
        motorWarningGenerated = false;
        motorWarningMessage = "Motors: ; ";
        driveWarningGenerated = false;
        driveWarningMessage = "DriveMotors: ";
        servoWarningGenerated = false;
        servoWarningMessage = "Servos: ; ";
        sensorWarningGenerated = false;
        sensorWarningMessage = "Sensors: ; ";
    }
    //------------Warnings------------
    boolean getWarningGenerated      () {return warningGenerated;}
    boolean getMotorWarningGenerated () {return motorWarningGenerated;}
    boolean getDriveWarningGenerated () {return driveWarningGenerated;}
    boolean getServoWarningGenerated () {return servoWarningGenerated;}
    boolean getSensorWarningGenerated() {return sensorWarningGenerated;}

    String getWarningMessage      () {return warningMessage;}
    String getMotorWarningMessage () {return motorWarningMessage;}
    String getDriveWarningMessage () {return driveWarningMessage;}
    String getServoWarningMessage () {return servoWarningMessage;}
    String getSensorWarningMessage() {return servoWarningMessage;}

    void setWarningMessage (String opModeExceptionMessage) {
        if (warningGenerated)
            warningMessage += ", ";
        warningGenerated = true;
        warningMessage += opModeExceptionMessage;
    }
    void setMotorWarningMessage (String opModeExceptionMessage) {
        if (motorWarningGenerated)
            motorWarningMessage += ", ";
        warningGenerated = true;
        motorWarningGenerated = true;
        motorWarningMessage += opModeExceptionMessage;
    }
    void setDriveWarningMessage (String opModeExceptionMessage) {
        if (driveWarningGenerated)
            driveWarningMessage += ", ";
        warningGenerated = true;
        driveWarningGenerated = true;
        driveWarningMessage += opModeExceptionMessage;
    }
    void setServoWarningMessage (String opModeExceptionMessage) {
        if (servoWarningGenerated)
            servoWarningMessage += ", ";
        warningGenerated = true;
        servoWarningGenerated = true;
        servoWarningMessage += opModeExceptionMessage;
    }
    void setSensorWarningMessage (String opModeExceptionMessage) {
        if (sensorWarningGenerated)
            sensorWarningMessage += ", ";
        warningGenerated = true;
        sensorWarningGenerated = true;
        sensorWarningMessage += opModeExceptionMessage;
    }
}
