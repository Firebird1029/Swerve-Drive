package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by byee20 on 11/5/15.
 */

public class Autonomous extends OpMode {

	// These are the hardware stuff.
	DcMotor motorRight;
	DcMotor motorLeft;

	// CHANGE THESE.
	int mIncrement = 10;
	public long tiger = 1000;

	// These are the statistic variables.
	public int i = 0;
	public int phase = 1;
	public int acc = i;
	public String status = "";

	final Timer timer = new Timer();

	// This is to make every phase run longer.
	public int sec (double val) {
		return (int) val * mIncrement;
	}

	// Beginning function in every phase.
	public void begin (String msg) {
		status = msg;
	}

	// Ending function in every phase.
	public void end (int length) {
		if (i >= length) {
			phase = 2;
			acc += i;
		}
	}

	// Final function for the second-to-last phase.
	public void endProgram () {
		phase = -1;
		acc += i;
	}

	public Autonomous () {}

	@Override
	public void init() {
		motorLeft = hardwareMap.dcMotor.get("m1");
		motorRight = hardwareMap.dcMotor.get("m2");

		// motorLeft.setDirection(DcMotor.Direction.REVERSE);
		motorRight.setDirection(DcMotor.Direction.REVERSE);
	}

	@Override
	public void start() {
		i = 0;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				i++;

				if (phase == 1) {
					begin("Robot moving backward for 25 seconds.");

					motorLeft.setPower(-0.25);
					motorRight.setPower(-0.25);

					end(sec(28));

				}

				if (phase == 2) {
					begin("Placeholder phase 2.");

					motorLeft.setPower(0);
					motorRight.setPower(0);

					end(sec(1));
					endProgram();
				}


				if (phase == -1 || i >= sec(30)) {
					status = "Done!";
					timer.cancel();
					motorLeft.setPower(0);
					motorRight.setPower(0);
				}
			}
		}, 0, 100);
	}

	@Override
	public void loop() {
		telemetry.addData("Status", "" + status);
		telemetry.addData("Phase", "" + phase);
		telemetry.addData("i", "" + i);
		telemetry.addData("acc", "" + acc);
	}

	@Override
	public void stop() {
		timer.cancel();
		motorLeft.setPower(0);
		motorRight.setPower(0);
	}
}