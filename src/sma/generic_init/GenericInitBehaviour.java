package sma.generic_init;

import java.util.ArrayList;
import java.util.HashMap;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import sma.generic.behaviour.DeleteBehavioursFromRoleBehaviour;
import sma.generic.behaviour.DeleteRoleBehaviour;
import sma.generic_death.AbstractDeathBehaviour;
import sma.generic_vote.AbstractVoteBehaviour;
import sma.model.Roles;
import sma.model.TypeIA;
import sma.player_agent.GetRoleBehaviour;
import sma.player_agent.PlayerAgent;
import sma.player_agent.SleepBehaviour;
import sma.player_agent.VictimStatusBehaviour;
import sma.player_agent.WakeBehaviour;
import sma.vote_behaviour.CitizenScoreBehaviour;

public class GenericInitBehaviour extends OneShotBehaviour{
	private PlayerAgent agent;

	
	public GenericInitBehaviour(PlayerAgent agent) {
		super();
		this.agent = agent;
	}

	@Override
	public void action() {

		//System.out.println("GenericInitBehaviour THIS PLAYER "+this.agent.getName());
		ArrayList<Behaviour> list_behav = new ArrayList<Behaviour>();
		HashMap<String, ArrayList<Behaviour>> map_behaviour = this.agent.getMap_role_behaviours();

		if(!this.agent.isHuman())
		{
			AbstractVoteBehaviour abstractVoteBehaviour = new AbstractVoteBehaviour(this.agent);
			this.agent.addBehaviour(abstractVoteBehaviour);
		}
		
		CitizenScoreBehaviour citizenScoreBehaviour = new CitizenScoreBehaviour(this.agent);
		this.agent.addBehaviour(citizenScoreBehaviour);
		this.agent.getVotingBehaviours().add(citizenScoreBehaviour.getName_behaviour()); 
		
		this.agent.addBehaviour(new AbstractDeathBehaviour(this.agent));

		WakeBehaviour genericWakeBehaviour = new WakeBehaviour(this.agent);
		this.agent.addBehaviour(genericWakeBehaviour);
		list_behav.add(genericWakeBehaviour);

		VictimStatusBehaviour victimStatusBehaviour = new VictimStatusBehaviour(this.agent);
		this.agent.addBehaviour(victimStatusBehaviour);
		list_behav.add(victimStatusBehaviour);
		
		SleepBehaviour genericSleepBehaviour = new SleepBehaviour(this.agent);
		this.agent.addBehaviour(genericSleepBehaviour);
		list_behav.add(genericSleepBehaviour);

		//System.err.println("...............................GET ROLE...........................");
		GetRoleBehaviour getRoleBehaviour = new GetRoleBehaviour(this.agent);
		this.agent.addBehaviour(getRoleBehaviour);
		
		DeleteRoleBehaviour deleteRoleBehaviour = new DeleteRoleBehaviour(this.agent);
		this.agent.addBehaviour(deleteRoleBehaviour);
		list_behav.add(deleteRoleBehaviour);
		
		DeleteBehavioursFromRoleBehaviour deleteBehavioursFromRoleBehaviour = new DeleteBehavioursFromRoleBehaviour(this.agent);
		this.agent.addBehaviour(deleteBehavioursFromRoleBehaviour);
		list_behav.add(deleteBehavioursFromRoleBehaviour);

		this.agent.getTypeVotingBehaviours().put(citizenScoreBehaviour.getName_behaviour(), TypeIA.STRATEGIC);

		map_behaviour.put(Roles.GENERIC, list_behav);

	}


}
