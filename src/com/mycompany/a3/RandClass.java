package com.mycompany.a3;

import java.util.Random;

public class RandClass
{
	public static int getRandInt(int min, int max)
	{
		Random r = new Random();
		int x = r.nextInt((max - min) + 1) + min;
		return x;
	}
}
