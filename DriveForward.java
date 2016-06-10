package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class DriveForward extends OpMode {
    DcMotor m1;
    DcMotor m2;
    DcMotor m3;
    DcMotor m4;

    public DriveForward() {}

    @Override
    public void init () {
        m1 = hardwareMap.dcMotor.get("m1");
        m2 = hardwareMap.dcMotor.get("m2");
	    m3 = hardwareMap.dcMotor.get("m3");
	    m4 = hardwareMap.dcMotor.get("m4");
    }

    @Override
    public void loop() {
	    m1.setPower(1);
	    m2.setPower(1);
	    m3.setPower(1);
	    m4.setPower(1);
    }

}
