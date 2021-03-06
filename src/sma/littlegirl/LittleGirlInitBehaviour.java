package sma.littlegirl;

import java.util.ArrayList;
import java.util.HashMap;
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
import sma.vote_behaviour.LittleGirlSuspicionListener;
import sma.vote_behaviour.CitizenSuspicionBehaviour;

public class LittleGirlInitBehaviour extends OneShotBehaviour{
	private PlayerAgent agent;
	private AID receiver;

	public LittleGirlInitBehaviour(PlayerAgent agent, AID receiver) {
		super();
		this.agent = agent;
		this.receiver = receiver;
	}


	@Override
	public void action() {
		ArrayList<Behaviour> list_behav = new ArrayList<Behaviour>();
		HashMap<String, ArrayList<Behaviour>> map_behaviour = this.agent.getMap_role_behaviours();

		CitizenSuspicionBehaviour citizenSuspicionBehaviour = new CitizenSuspicionBehaviour(this.agent);
		list_behav.add(citizenSuspicionBehaviour);

		CitizenSuspicionListener citizenSuspicionListener = new CitizenSuspicionListener(this.agent);
		list_behav.add(citizenSuspicionListener);
		
		LittleGirlSuspicionListener littleGirlSuspicionListener = new LittleGirlSuspicionListener(this.agent);
		list_behav.add(littleGirlSuspicionListener);

		CitizenSimpleSuspicionBehaviour citizenSimpleSuspicionBehaviour = new CitizenSimpleSuspicionBehaviour(this.agent);
		list_behav.add(citizenSimpleSuspicionBehaviour);
	
		
		this.agent.addBehaviour(citizenSimpleSuspicionBehaviour);
		this.agent.addBehaviour(citizenSuspicionBehaviour);
		this.agent.addBehaviour(citizenSuspicionListener);
		this.agent.addBehaviour(littleGirlSuspicionListener);
		
		this.agent.getVotingBehaviours().add(citizenSuspicionBehaviour.getName_behaviour());
		
		this.agent.getTypeVotingBehaviours().put(citizenSuspicionBehaviour.getName_behaviour(), TypeIA.SUSPICIOUS);
		
		//Handle attributes
		map_behaviour.put(Roles.LITTLE_GIRL, list_behav);

		System.out.println("[ "+this.agent.getName()+" ] REGISTER "+Roles.LITTLE_GIRL);
		DFServices.registerPlayerAgent(Roles.LITTLE_GIRL, this.myAgent, this.agent.getGameid());
		
		//Envoie message fin d'initialisation		
		ACLMessage messageRequest = new ACLMessage(ACLMessage.AGREE);
		messageRequest.setSender(this.agent.getAID());
		messageRequest.setConversationId("INIT_ROLE");
		messageRequest.addReceiver(this.receiver);
		this.myAgent.send(messageRequest);
	}

}
