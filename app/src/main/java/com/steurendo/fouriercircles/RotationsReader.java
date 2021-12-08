package com.steurendo.fouriercircles;

import com.steurendo.fouriercircles.types.RotationDude;

import java.io.*;

public class RotationsReader
{
    public static RotationDude[] buildRotationsFromFile(File file)
    {
        RotationDude[] rotations;
        BufferedReader in;
        String line;
        String[] data;
        Reader reader;
        int i;

        try
        {
            reader = new FileReader(file);
            in = new BufferedReader(reader);
            line = in.readLine();
            rotations = new RotationDude[Integer.parseInt(line)];
            i = 0;
            while ((line = in.readLine()) != null)
            {
                data = line.split(" ");
                rotations[i] = new RotationDude(
                        Float.parseFloat(data[0]),
                        Float.parseFloat(data[1]),
                        Float.parseFloat(data[2])
                );
                i++;
            }
            in.close();
        } catch (IOException e)
        {
            e.printStackTrace();
            rotations = null;
        }

        return rotations;
    }
}
