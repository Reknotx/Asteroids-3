package com.mycompany.a3;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.UITimer;
import com.mycompany.commands.*;
import com.mycompany.views.MapView;
import com.mycompany.views.PointsView;

//Game is the Controller in MVC architecture\

//Need to fix the map size
public class Game extends Form  implements Runnable
{
	public final int TIME_TILL_TICK = 20;

	private GameWorld gw;
	private MapView mv;
	private PointsView pv;
	private UITimer timer;
	private Toolbar menu;
	
	private AddAsteroidCmd asteroidCMD;
	private AddSpaceStationCmd addStationCMD;
	private AddPlayerCmd addPlayerCMD;
	private AccelerateCmd accelCMD;
	private DecelerateCmd decelCMD;
	private TurnLeftCmd turnLCMD;
	private TurnRightCmd turnRCMD;
	private LauncherTurnLeftCmd launcherLCMD;
	private LauncherTurnRightCmd launcherRCMD;
	private FirePlayerMissileCmd fireCMD;
	private JumpCmd jumpCMD;
	private PauseCmd pauseCmd;
	
	private GameButton addAsteroid, addStation, addPlayer, accelerate, decelerate, turnLeft, turnRight,
						launcherTurnLeft, launcherTurnRight, playerFire, jump, pauseGame;
	
	private boolean paused = false;
	
	private int timeElapsed = 0;
	
	public Game()
	{
		this.setLayout(new BorderLayout());
		this.setScrollable(false);
		
		gw = new GameWorld();
		mv = new MapView();
		pv = new PointsView();
		
//		System.out.println("Height = " + gw.getGameWorldHeight());
//		System.out.println("Width = " + gw.getGameWorldWidth());
		
		System.out.println("Form Width = " + this.getWidth() + " Form Height = " + this.getHeight());
		
		//Register the observers
		gw.addObserver(mv);
		gw.addObserver(pv);

		this.addComponent(BorderLayout.NORTH, pv);
		this.addComponent(BorderLayout.CENTER, mv);

		SetUpSideMenu();
		
		SetUpCommands();
		
		gw.init();
		
		this.show();
		
		System.out.println("Map width = " + mv.getMapWidth() + " Map height = " + mv.getMapHeight());
		
		gw.setGameWorldHeight(mv.getMapHeight());
		gw.setGameWorldWidth(mv.getMapWidth());
		
		timer = new UITimer(this);
				
		timer.schedule(TIME_TILL_TICK, true, this);
	}
	
	@Override
	public void run()
	{
		gw.AdvanceGameClock(TIME_TILL_TICK);
		timeElapsed += TIME_TILL_TICK;
		
		if (timeElapsed % 500 == 0)
		{
			int roll = RandClass.getRandInt(0, 100);
			if (roll <= 5)
			{
				gw.SpawnEnemy();
			}
		}
		
		if (gw.getLives() == 0)
		{
			timer.cancel();
		}
	}
	
	private void SetUpCommands()
	{
		/* Container creation start */
		Container buttonContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		/* Container creation end */
		
		/* Add asteroid button */
		asteroidCMD = new AddAsteroidCmd(gw);
		addAsteroid = new GameButton(asteroidCMD);
		buttonContainer.add(addAsteroid);
		/* Add asteroid button */
		
		/* Add space station button */
		addStationCMD = new AddSpaceStationCmd(gw);
		addStation = new GameButton(addStationCMD);
		buttonContainer.add(addStation);
		/* Add space station button */
		
		/* Add player button */
		addPlayerCMD = new AddPlayerCmd(gw);
		addPlayer = new GameButton(addPlayerCMD);
		buttonContainer.add(addPlayer);
		/* Add player button */
		
		/* Accelerate button */
		accelCMD = new AccelerateCmd(gw);
		accelerate = new GameButton(accelCMD);
		this.addKeyListener('w', accelCMD);
		this.addKeyListener(-91, accelCMD);
		buttonContainer.add(accelerate);
		/* Accelerate button */
		
		/* Decelerate button */
		decelCMD = new DecelerateCmd(gw);
		decelerate = new GameButton(decelCMD);
		this.addKeyListener('s', decelCMD);
		this.addKeyListener(-92, decelCMD);
		buttonContainer.add(decelerate);
		/* Decelerate button */
		
		/* Turn left button */
		turnLCMD = new TurnLeftCmd(gw);
		turnLeft = new GameButton(turnLCMD);
		this.addKeyListener('a', turnLCMD);
		this.addKeyListener(-93, turnLCMD);
		buttonContainer.add(turnLeft);
		/* Turn left button */
		
		/* Turn right button */
		turnRCMD = new TurnRightCmd(gw);
		turnRight = new GameButton(turnRCMD);
		this.addKeyListener('d', turnRCMD);
		this.addKeyListener(-94, turnRCMD);
		buttonContainer.add(turnRight);
		/* Turn right button */
		
		/* Turn launcher left button */
		launcherLCMD = new LauncherTurnLeftCmd(gw);
		launcherTurnLeft = new GameButton(launcherLCMD);
		this.addKeyListener(44, launcherLCMD);
		buttonContainer.add(launcherTurnLeft);
		/* Turn launcher left button */
		
		/* Turn launcher right button */
		launcherRCMD = new LauncherTurnRightCmd(gw);
		launcherTurnRight = new GameButton(launcherRCMD);
		this.addKeyListener(46, launcherRCMD);
		buttonContainer.add(launcherTurnRight);
		/* Turn launcher right button */
		
		/* Fire player missile button */
		fireCMD = new FirePlayerMissileCmd(gw);
		playerFire = new GameButton(fireCMD);
		this.addKeyListener(-90, fireCMD);
		buttonContainer.add(playerFire);
		/* Fire player missile button */
		
		/* Jump button */
		jumpCMD = new JumpCmd(gw);
		jump = new GameButton(jumpCMD);
		this.addKeyListener('j', jumpCMD);
		buttonContainer.add(jump);
		/* Jump button */
		
		/* Pause button */
		pauseCmd = new PauseCmd(this);
		pauseGame = new GameButton(pauseCmd);
		this.addKeyListener('p', pauseCmd);
		buttonContainer.add(pauseGame);
		/* Pause button */
				
		//Sets the size of the map view container so that it fills in the remaining leftover space properly
		mv.setWidth(this.getWidth() - buttonContainer.getPreferredW() - mv.getX());
		
		this.addComponent(BorderLayout.WEST, buttonContainer);
	}
	
	private void SetUpSideMenu()
	{		
		menu = new Toolbar();
		this.setToolbar(menu);
		menu.setTitle("Asteroid Game");
		
		NewGameCmd newGame = new NewGameCmd();
		menu.addCommandToSideMenu(newGame);
		
		SaveCmd save = new SaveCmd();
		menu.addCommandToSideMenu(save);
		
		UndoCmd undo = new UndoCmd();
		menu.addCommandToSideMenu(undo);

		CheckBox soundOn = new CheckBox("Sound");
		SoundCmd sound = new SoundCmd(gw, soundOn);
		soundOn.setCommand(sound);
		menu.addCommandToSideMenu(sound);
		
		AboutCmd about = new AboutCmd();
		menu.addCommandToSideMenu(about);
		
		QuitCmd quit = new QuitCmd();
		this.addKeyListener(81, quit); //Doesn't work for some reason
		menu.addCommandToSideMenu(quit);
	}
	
	/**
	 * This method will handle both pausing and unpausing the game all in one place.
	 */
	public void PauseAndUnPauseGame()
	{
		this.paused = !paused;

		addAsteroid.setEnabled(!addAsteroid.isEnabled());
		addStation.setEnabled(!addStation.isEnabled());
		addPlayer.setEnabled(!addPlayer.isEnabled());
		accelerate.setEnabled(!accelerate.isEnabled());
		decelerate.setEnabled(!decelerate.isEnabled());
		turnLeft.setEnabled(!turnLeft.isEnabled());
		turnRight.setEnabled(!turnRight.isEnabled());
		launcherTurnLeft.setEnabled(!launcherTurnLeft.isEnabled());
		launcherTurnRight.setEnabled(!launcherTurnRight.isEnabled());
		playerFire.setEnabled(!playerFire.isEnabled());
		jump.setEnabled(!jump.isEnabled());
		
		if (paused)
		{
			//Game is to be paused
			timer.cancel();
			
			pauseGame.setText("Resume");
			
			//Accelerate command
			this.removeKeyListener('w', accelCMD);
			this.removeKeyListener(-91, accelCMD);
			//Accelerate command
			
			//Decelerate command
			this.removeKeyListener('s', decelCMD);
			this.removeKeyListener(-92, decelCMD);
			//Decelerate command
			
			//Turn left command
			this.removeKeyListener('a', turnLCMD);
			this.removeKeyListener(-93, turnLCMD);
			//Turn left command
			
			//Turn right command
			this.removeKeyListener('d', turnRCMD);
			this.removeKeyListener(-94, turnRCMD);
			//Turn right command

			//Turn launcher left command
			this.removeKeyListener(44, launcherLCMD);
			//Turn launcher left command
			
			//Turn launcher right command
			this.removeKeyListener(46, launcherRCMD);
			//Turn launcher right command
			
			//Fire player missile command
			this.removeKeyListener(-90, fireCMD);
			//Fire player missile command
			
			//Jump command
			this.removeKeyListener('j', jumpCMD);
			//Jump command
		}
		else
		{
			//Game is to resume
			timer.schedule(TIME_TILL_TICK, true, this);
			
			//Accelerate command
			this.addKeyListener('w', accelCMD);
			this.addKeyListener(-91, accelCMD);
			//Accelerate command
			
			//Decelerate command
			this.addKeyListener('s', decelCMD);
			this.addKeyListener(-92, decelCMD);
			//Decelerate command
			
			//Turn left command
			this.addKeyListener('a', turnLCMD);
			this.addKeyListener(-93, turnLCMD);
			//Turn left command
			
			//Turn right command
			this.addKeyListener('d', turnRCMD);
			this.addKeyListener(-94, turnRCMD);
			//Turn right command
			
			//Turn launcher left command
			this.addKeyListener(44, launcherLCMD);
			//Turn launcher left command
			
			//Turn launcher right command
			this.addKeyListener(46, launcherRCMD);
			//Turn launcher right command
			
			//Fire player missile command
			this.addKeyListener(-90, fireCMD);
			//Fire player missile command
			
			//Jump command
			this.addKeyListener('j', jumpCMD);
			//Jump command
		}
	}
}