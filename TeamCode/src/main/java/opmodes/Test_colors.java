/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Robot2017;
import org.firstinspires.ftc.teamcode.sensors.ColorSensor;

/*
 * This is an example LinearOpMode that shows how to use
 * the REV Robotics Color-Distance Sensor.
 *
 * It assumes the sensor is configured with the name "sensor_color_distance".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 */
@Autonomous(name = "Color Knocking Test", group = "Sensor")
public class Test_colors extends LinearOpMode {


  @Override
  public void runOpMode() throws InterruptedException {

    Robot robot = new Robot2017(hardwareMap);

    while(opModeIsActive()) {
      telemetry.addData("Status", "yes");
      telemetry.update();

      String colors = "";

      sleep(1000);
      ColorSensor test = (ColorSensor) robot.getSensor("color");

      if( test.getSensorValue().blue > test.getSensorValue().red )
        colors ="blue";
      else{
        colors = "red";
      }

      telemetry.addData("red", test.getSensorValue().blue);
      telemetry.addData("green", test.getSensorValue().green);
      telemetry.addData("blue", test.getSensorValue().blue);
      telemetry.addData("color sensed", colors);
      sleep(1000);
    }

    telemetry.addData("sattus", "done");
    telemetry.update();

  }
}
