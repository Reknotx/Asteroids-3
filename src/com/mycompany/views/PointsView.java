package com.mycompany.views;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.a3.GameWorldProxy;
import com.mycompany.interfaces.IGameWorld;

//View in MVC architecture
public class PointsView extends Container implements Observer 
{
	private Label pointsValue;
	private Label missileCountNum;
	private Label elapsedTimeNum;
	private Label soundSetting;
	private Label livesRemaining;
	
	/**
	 * Creates a container displaying the values of the game such as points, lives, etc.
	 */
	public PointsView() 
	{
		this.setLayout(new FlowLayout(LEFT));
		SetUpPoints();
		SetUpMissileCount();
		SetUpTime();
		SetUpSound();
		SetUpLives();
	}

	@Override
	public void update(Observable observable, Object data) 
	{
		IGameWorld tempProxy = (IGameWorld) data;
		pointsValue.setText("" + Integer.toString(tempProxy.getPoints()));
		missileCountNum.setText("" + Integer.toString(tempProxy.getMissileCount()));
		elapsedTimeNum.setText("" + Integer.toString(tempProxy.getTime()));
		livesRemaining.setText("" + Integer.toString(tempProxy.getLives()));
		if (tempProxy.getSoundSetting())
		{
			soundSetting.setText("ON ");
		}
		else
		{
			soundSetting.setText("OFF");
		}
		this.repaint();
	}
	
	private void SetUpPoints()
	{
		Label pointsText = new Label("Points:");
		pointsText.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		
		pointsValue = new Label("0");
		pointsValue.getAllStyles().setPadding(RIGHT, 5);
		
//		this.add(pointsText);
//		this.add(pointsValue);

		Container pointsContainer = new Container();
		pointsContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		pointsContainer.add(pointsText);
		pointsContainer.add(pointsValue);
		
		this.add(pointsContainer);
//		return pointsContainer;
	}

	private void SetUpMissileCount()
	{
		Label missileText = new Label("Missile count:");
		missileText.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		
		missileCountNum = new Label("0");
		missileCountNum.getAllStyles().setPadding(RIGHT, 5);
		
		this.add(missileText);
		this.add(missileCountNum);
		
//		Container missileContainer = new Container();
//		missileContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
//		
//		missileContainer.add(missileText);
//		missileContainer.add(missileCountNum);
//		
//		return missileContainer;
	}
	
	private void SetUpTime()
	{
		Label timeText = new Label("Elapsed time:");
		timeText.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		
		elapsedTimeNum = new Label("0");
		elapsedTimeNum.getAllStyles().setPadding(RIGHT, 5);
		
		this.add(timeText);
		this.add(elapsedTimeNum);
		
//		Container timeContainer = new Container();
//		timeContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
//		
//		timeContainer.add(timeText);
//		timeContainer.add(elapsedTimeNum);
//		
//		return timeContainer;
	}
	
	private void SetUpSound()
	{
		Label soundText = new Label("Sound:");
		soundText.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		
		soundSetting = new Label("OFF");
		soundSetting.getAllStyles().setPadding(RIGHT, 5);
		
		this.add(soundText);
		this.add(soundSetting);
		
//		Container soundContainer = new Container();
//		soundContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
//		
//		soundContainer.add(soundText);
//		soundContainer.add(soundSetting);
//		
//		return soundContainer;
	}
	
	private void SetUpLives()
	{
		Label livesText = new Label("Lives:");
		livesText.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		
		livesRemaining = new Label("0");
		livesRemaining.getAllStyles().setPadding(RIGHT, 5);
		
		this.add(livesText);
		this.add(livesRemaining);
		
//		Container livesContainer = new Container();
//		livesContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
//		
//		livesContainer.add(livesText);
//		livesContainer.add(livesRemaining);
//		
//		return livesContainer;
	}
}
