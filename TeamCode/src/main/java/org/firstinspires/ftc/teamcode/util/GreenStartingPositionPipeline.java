package org.firstinspires.ftc.teamcode.util;


import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.openftc.easyopencv.OpenCvPipeline;

public class GreenStartingPositionPipeline extends OpenCvPipeline
{
    private final int threshold = 25;

    public enum StartingPosition
    {
        LEFT,
        CENTER,
        RIGHT,
        NONE
    }

    private StartingPosition position = StartingPosition.NONE;

    Point region1_pointA = new Point(
            1,
            0);
    Point region1_pointB = new Point(
            320,
            480);
    Point region2_pointA = new Point(
            160,
            0);
    Point region2_pointB = new Point(
            480,
            480);
    Point region3_pointA = new Point(
            320,
            0);
    Point region3_pointB = new Point(
            640,
            480);

    Mat region1, region2, region3 = new Mat();

    int total1, total2, total3;

    @Override
    public Mat processFrame(Mat input)
    {
        int countL = 0, countC = 0, countR = 0;
        for(int i = 240; i < 480; i++)
        {
            for(int j = 0; j < 640; j++)
            {
                if(input.get(i, j)[1] > (input.get(i, j)[0] + threshold) && input.get(i, j)[1] > (input.get(i, j)[2] + threshold))
                {
                    if(j < 213)
                    {
                        countL ++;
                    } else if(j < 427)
                    {
                        countC ++;
                    } else {
                        countR ++;
                    }
                }
            }
        }
        total1 = countL;
        total2 = countC;
        total3 = countR;
        int maxOneTwo = Math.max(countR, countC);
        int max = Math.max(maxOneTwo, countL);

        if(max == countL)
        {
            position = StartingPosition.LEFT;
        } else if(max == countC)
        {
            position = StartingPosition.CENTER;
        } else
        {
            position = StartingPosition.RIGHT;
        }

        return input;
    }

    public String getPosition()
    {
        if(position == StartingPosition.LEFT)
        {
            return "LEFT";
        } else if(position == StartingPosition.CENTER)
        {
            return "CENTER";
        } else if(position == StartingPosition.RIGHT) {
            return "RIGHT";
        } else
        {
            return "ERROR";
        }
    }

    public int[] totalGreen()
    {
        return new int[]{total1, total2, total3};
    }
}
