package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by spmce on 2/25/2017.
 */
@Autonomous(name = "vvFullAutoRed", group = "Autonomous")
public class VVFullAutoRed extends VVFullAuto {
    /**
     * constructor
     */
    public VVFullAutoRed() {
        // constructor
    }

    /**
     * Init
     */
    //@Override
    public void init() {
        super.init();
    }

    /**
     * Loop
     */
    //@Override
    public void loop() {
        autoLoop(false,true);
    }
}
