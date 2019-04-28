package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.plaf.Border;

public class GameButton extends Button 
{
	/**
	 * Creates specialized buttons for the game
	 * @param cmd - The command to invoke when button is pressed
	 */
	public GameButton(Command cmd)
	{		
		this.getAllStyles().setBgTransparency(255);
		this.getAllStyles().setBorder(Border.createBevelRaised());
		this.getAllStyles().setBorder(Border.createBevelLowered());
		this.getAllStyles().setBorder(Border.createDoubleBorder(2, ColorUtil.BLACK	));
//  		this.getAllStyles().setMargin(TOP, 2);
  		this.getAllStyles().setMargin(BOTTOM, 4);
  		
		this.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		this.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		
		this.getPressedStyle().setBgTransparency(255/2);
		this.getPressedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		this.getPressedStyle().setFgColor(ColorUtil.BLUE);
		
		this.getDisabledStyle().setBgTransparency(255);
		this.getDisabledStyle().setBgColor(ColorUtil.GRAY);
		this.getDisabledStyle().setFgColor(ColorUtil.BLUE);
		this.getDisabledStyle().setStrikeThru(true);
		
		/*This prevents button from being clicked and remaining in focus.
		Without this line you could traverse through buttons with arrow keys
		and when pressing space invoke the focused button, and invoke two commands
		at once instead of the desirable which is to simply fire a missile.
		*/
		this.setFocusable(false);
		
		this.setCommand(cmd);
	}
}
