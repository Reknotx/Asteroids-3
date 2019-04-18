package com.mycompany.a3;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.commands.*;
import com.mycompany.views.MapView;
import com.mycompany.views.PointsView;

//Game is the Controller in MVC architecture
public class Game extends Form 
{
	private GameWorld gw;
	private MapView mv;
	private PointsView pv;
	
	public Game()
	{
		this.setLayout(new BorderLayout());
		this.setScrollable(false);
		
		gw = new GameWorld();
		mv = new MapView();
		pv = new PointsView();
		
//		System.out.println("Height = " + gw.getGameWorldHeight());
//		System.out.println("Width = " + gw.getGameWorldWidth());
		
		System.out.println("Form Height = " + this.getHeight() + " Form Width = " + this.getWidth());
		
		//Register the observers
		gw.addObserver(mv);
		gw.addObserver(pv);
		
		SetUpCommands();
		SetUpSideMenu();
		
		this.addComponent(BorderLayout.CENTER, mv);
		this.addComponent(BorderLayout.NORTH, pv);
		
		System.out.println(mv.getX());
		
		gw.init();
		
		gw.setGameWorldHeight(mv.getMapHeight());
		gw.setGameWorldWidth(mv.getMapWidth());
		
		this.show();
	}
	
	private void SetUpCommands()
	{
		/* Container creation start */
		Container buttonContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		buttonContainer.setWidth(1000);
		buttonContainer.setScrollableY(false);
		Label cmdLBL = new Label("Commands");
		buttonContainer.add(cmdLBL);
		/* Container creation end */
		
		/* Add asteroid button */
		AddAsteroidCmd asteroidCMD = new AddAsteroidCmd(gw);
		GameButton addAsteroid = new GameButton(asteroidCMD);
		buttonContainer.add(addAsteroid);
		/* Add asteroid button */

		/* Add enemy ship button */
		AddEnemyShipCmd addEnemyCMD = new AddEnemyShipCmd(gw);
		GameButton addEnemy = new GameButton(addEnemyCMD);
		buttonContainer.add(addEnemy);
		/* Add enemy ship button */
		
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
		
		/* Fire enemy missile button */
		FireEnemyMissileCmd enemyFireCMD = new FireEnemyMissileCmd(gw);
		GameButton enemyFire = new GameButton(enemyFireCMD);
		buttonContainer.add(enemyFire);
		/* Fire enemy missile button */
		
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
		
		/* Player missile hits asteroid button */
		MissileKillsAsteroidCmd playerShootAsteroidCMD = new MissileKillsAsteroidCmd(gw);
		GameButton playerShootsAsteroid = new GameButton(playerShootAsteroidCMD);
		buttonContainer.add(playerShootsAsteroid);
		/* Player missile hits asteroid button */
		
		/* Player missile hits enemy button */
		MissileKillsEnemyCmd playerShootEnemyCMD = new MissileKillsEnemyCmd(gw);
		GameButton playerShootsEnemy = new GameButton(playerShootEnemyCMD);
		buttonContainer.add(playerShootsEnemy);
		/* Player missile hits enemy button */
		
		/* Enemy missile hits player button */
		EnemyMissileKillsPlayerCmd enemyShootPlayerCMD = new EnemyMissileKillsPlayerCmd(gw);
		GameButton enemyShootsPlayer = new GameButton(enemyShootPlayerCMD);
		buttonContainer.add(enemyShootsPlayer);
		/* Enemy missile hits player button */
		
		/* Player collides with asteroid button */
		PlayerHitsAsteroidCmd playerHitAsteroidCMD = new PlayerHitsAsteroidCmd(gw);
		GameButton playerCollidesWithAsteroid = new GameButton(playerHitAsteroidCMD);
		buttonContainer.add(playerCollidesWithAsteroid);
		/* Player collides with asteroid button */
		
		/* Player collides with enemy button */
		PlayerHitsEnemyCmd playerHitEnemyCMD = new PlayerHitsEnemyCmd(gw);
		GameButton playerCollidesWithEnemy = new GameButton(playerHitEnemyCMD);
		buttonContainer.add(playerCollidesWithEnemy);
		/* Player collides with enemy button */
		
		/* Asteroid collides with asteroid button */
		AsteroidHitsAsteroidCmd asteroidHitAsteroidCMD = new AsteroidHitsAsteroidCmd(gw);
		GameButton asteroidsCollide = new GameButton(asteroidHitAsteroidCMD);
		buttonContainer.add(asteroidsCollide);
		/* Asteroid collides with asteroid button */
		
		/* Asteroid collides with enemy button */
		AsteroidHitsEnemyCmd asteroidHitEnemyCMD = new AsteroidHitsEnemyCmd(gw);
		GameButton asteroidCollidesWithEnemy = new GameButton(asteroidHitEnemyCMD);
		buttonContainer.add(asteroidCollidesWithEnemy);
		/* Asteroid collides with enemy button */
		
		/* Advance game clock button */
		TickCmd tickCMD = new TickCmd(gw);
		GameButton advanceGameTime = new GameButton(tickCMD);
		buttonContainer.add(advanceGameTime);
		/* Advance game clock button */
		System.out.println("Button container: " + buttonContainer.getPreferredSize());
		
		mv.setPrefSize(this.getWidth() - buttonContainer.getPreferredW(), buttonContainer.getPreferredH());
		
		this.addComponent(BorderLayout.WEST, buttonContainer);
	}
	
	private void SetUpSideMenu()
	{		
		Toolbar menu = new Toolbar();
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