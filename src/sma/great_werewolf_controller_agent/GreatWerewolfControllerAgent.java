package sma.great_werewolf_controller_agent;

import jade.core.Agent;

import sma.generic.interfaces.IController;
import sma.model.DFServices;
import sma.model.Roles;

/**
 * Controlleur gestion du tour citizen
 * @author Davy
 *
 */
public class GreatWerewolfControllerAgent extends Agent implements IController {
	private int gameid;
	
	public GreatWerewolfControllerAgent() {
		super();	

	}
	

	@Override
	protected void setup() {
		
		Object[] args = this.getArguments();
		this.gameid = (Integer) args[0];
		
		DFServices.registerGameControllerAgent(Roles.GREAT_WEREWOLF, this, this.gameid);		
		this.addBehaviour(new sma.generic_vote.SynchronousVoteBehaviour(this));
		this.addBehaviour(new TurnBehaviour(this));
		
	}


	public int getGameid() {
		return gameid;
	}
	

}
