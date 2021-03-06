package sma.citizen_agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import sma.model.DFServices;
import sma.model.Roles;
import sma.model.TypeIA;
import sma.player_agent.PlayerAgent;
import sma.vote_behaviour.CitizenSimpleSuspicionBehaviour;
import sma.vote_behaviour.CitizenSuspicionListener;
import sma.vote_behaviour.CitizenSuspicionBehaviour;

public class CitizenInitBehaviour extends OneShotBehaviour{
	private PlayerAgent agent;
	private AID receiver;

	public CitizenInitBehaviour(PlayerAgent agent, AID receiver) {
		super();
		this.agent = agent;
		this.receiver = receiver;
	}

	@Override
	public void action() {
		System.out.println("CitizenInitBehaviour THIS PLAYER "+this.agent.getName());
		ArrayList<Behaviour> list_behav = new ArrayList<Behaviour>();
		HashMap<String, ArrayList<Behaviour>> map_behaviour = this.agent.getMap_role_behaviours();
		CitizenSuspicionBehaviour citizenSuspicionBehaviour = new CitizenSuspicionBehaviour(this.agent);
		list_behav.add(citizenSuspicionBehaviour);

		CitizenSuspicionListener citizenSuspicionListener = new CitizenSuspicionListener(this.agent);
		list_behav.add(citizenSuspicionListener);

		CitizenSimpleSuspicionBehaviour citizenSimpleSuspicionBehaviour = new CitizenSimpleSuspicionBehaviour(this.agent);
		list_behav.add(citizenSimpleSuspicionBehaviour);
		

		this.agent.addBehaviour(citizenSimpleSuspicionBehaviour);
		this.agent.addBehaviour(citizenSuspicionBehaviour);
		this.agent.addBehaviour(citizenSuspicionListener);
		this.agent.getVotingBehaviours().add(citizenSuspicionBehaviour.getName_behaviour());
		//Handle attributes
		map_behaviour.put(Roles.CITIZEN, list_behav);

		//TEST
		boolean have_to_regist = true;
		List<AID> agents = DFServices.findGamePlayerAgent( Roles.CITIZEN , this.agent, this.agent.getGameid());
		for (AID agent : agents) {
			if (agent == this.agent.getAID())
				have_to_regist = false;		
		}
		System.out.println("HAVE TO REGISTER CITIZEN "+this.agent.getName() + "  " + have_to_regist);
		if (have_to_regist)
			DFServices.registerPlayerAgent(Roles.CITIZEN, this.agent, this.agent.getGameid());
	
		this.agent.getTypeVotingBehaviours().put(citizenSuspicionBehaviour.getName_behaviour(), TypeIA.SUSPICIOUS);
		
		//Envoie message fin d'initialisation
		ACLMessage messageRequest = new ACLMessage(ACLMessage.AGREE);
		messageRequest.setSender(this.agent.getAID());
		messageRequest.setConversationId("INIT_ROLE");
		messageRequest.addReceiver(this.receiver);
		this.myAgent.send(messageRequest);	

	}

}
