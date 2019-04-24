package com.mycompany.a3;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
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
	private GameWorld gw;
	private MapView mv;
	private PointsView pv;
	private UITimer timer;
	private TickCmd tickRun;
	private Toolbar menu;
	
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
		
		tickRun = new TickCmd(gw);
		
		//timer.schedule(20, true, this);
	}
	
	@Override
	public void run()
	{
		tickRun.actionPerformed(null);
	}
	
	private void SetUpCommands()
	{
		/* Container creation start */
		Container buttonContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		/* Container creation end */
		
		/* Add asteroid button */
		AddAsteroidCmd asteroidCMD = new AddAsteroidCmd(gw);
		GameButton addAsteroid = new GameButton(asteroidCMD);
		buttonContainer.add(addAsteroid);
		/* Add asteroid button */
		
		/* Add space station button */
		AddSpaceStationCmd addStationCMD = new AddSpaceStationCmd(gw);
		GameButton addStation = new GameButton(addStationCMD);
		buttonContainer.add(addStation);
		/* Add space station button */
		
		/* Add player button */
		AddPlayerCmd addPlayerCMD = new AddPlayerCmd(gw);
		GameButton addPlayer = new GameButton(addPlayerCMD);
		buttonContainer.add(addPlayer);
		/* Add player button */
		
		/* Accelerate button */
		AccelerateCmd accelCMD = new AccelerateCmd(gw);
		GameButton accelerate = new GameButton(accelCMD);
		addKeyListener('w', accelCMD);
		addKeyListener(-91, accelCMD);
		buttonContainer.add(accelerate);
		/* Accelerate button */
		
		/* Decelerate button */
		DecelerateCmd decelCMD = new DecelerateCmd(gw);
		GameButton decelerate = new GameButton(decelCMD);
		addKeyListener('s', decelCMD);
		addKeyListener(-92, decelCMD);
		buttonContainer.add(decelerate);
		/* Decelerate button */
		
		/* Turn left button */
		TurnLeftCmd turnLCMD = new TurnLeftCmd(gw);
		GameButton turnLeft = new GameButton(turnLCMD);
		addKeyListener('a', turnLCMD);
		addKeyListener(-93, turnLCMD);
		buttonContainer.add(turnLeft);
		/* Turn left button */
		
		/* Turn right button */
		TurnRightCmd turnRCMD = new TurnRightCmd(gw);
		GameButton turnRight = new GameButton(turnRCMD);
		addKeyListener('d', turnRCMD);
		addKeyListener(-94, turnRCMD);
		buttonContainer.add(turnRight);
		/* Turn right button */
		
		/* Turn launcher left button */
		LauncherTurnLeftCmd launcherLCMD = new LauncherTurnLeftCmd(gw);
		GameButton launcherTurnLeft = new GameButton(launcherLCMD);
		addKeyListener(44, launcherLCMD);
		buttonContainer.add(launcherTurnLeft);
		/* Turn launcher left button */
		
		/* Turn launcher right button */
		LauncherTurnRightCmd launcherRCMD = new LauncherTurnRightCmd(gw);
		GameButton launcherTurnRight = new GameButton(launcherRCMD);
		addKeyListener(46, launcherRCMD);
		buttonContainer.add(launcherTurnRight);
		/* Turn launcher right button */
		
		/* Fire player missile button */
		FirePlayerMissileCmd fireCMD = new FirePlayerMissileCmd(gw);
		GameButton playerFire = new GameButton(fireCMD);
		addKeyListener(-90, fireCMD);
		buttonContainer.add(playerFire);
		/* Fire player missile button */
		
		/* Jump button */
		JumpCmd jumpCMD = new JumpCmd(gw);
		GameButton jump = new GameButton(jumpCMD);
		addKeyListener('j', jumpCMD);
		buttonContainer.add(jump);
		/* Jump button */
		
		/* Reload button */
		ReloadCmd reloadCMD = new ReloadCmd(gw);
		GameButton reload = new GameButton(reloadCMD);
		addKeyListener('r', reloadCMD);
		buttonContainer.add(reload);
		/* Reload button */
		
		//Sets the size of the map view container so that it fills in the remaining leftover space properly
		
		//this doesn't get me a perfect size for the map yet, need to work on this and find out how to 
//		mv.setPrefSize(this.getWidth() - buttonContainer.getPreferredW(), this.getHeight() - (pv.getHeight() + menu.getHeight()) );
		
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
		addKeyListener('q', quit); //Doesn't work for some reason
		menu.addCommandToSideMenu(quit);
	}
}