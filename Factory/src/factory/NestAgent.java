package factory;

import java.util.ArrayList;

import agent.Agent;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import factory.masterControl.MasterControl;

public class NestAgent extends Agent implements Nest {
	
	public NestAgent(MasterControl mc) {
		super(mc); // needed for the server 
	}


	/** DATA **/
	public ArrayList<MyPart> myParts = new ArrayList<MyPart>();
	public Lane myLane;
	public enum NestState { NORMAL, NEEDS_TO_DUMP, HAS_STABILIZED }
	public NestState nestState = NestState.NORMAL;
	public enum MyPartState {  NEEDED, REQUESTED }
	public class MyPart {
		public Part pt;
		public MyPartState state;

		public MyPart(Part partType){
			this.state = MyPartState.NEEDED;
			this.pt = partType;
		}
	}

	/** MESSAGES **/
	public void msgDump() {
		nestState = NestState.NEEDS_TO_DUMP;
		stateChanged();
	}
	public void msgYouNeedPart(Part part) {
		debug("received msgYouNeedPart("+part.name+").");
		myParts.add(new MyPart(part));
		stateChanged();
	}
	
	/** 
	 * Message from the animation notifying the NestAgent 
	 * that its parts have stabilized after resettling.
	 */
	public void msgNestHasStabilized() {
		nestState = NestState.HAS_STABILIZED;
		stateChanged();
	}

	
	
	
	
	

	/** SCHEDULER **/
	public boolean pickAndExecuteAnAction() {
		if (nestState == NestState.HAS_STABILIZED)
		{
			tellMyLaneIHaveStabilized();
		}
		if (nestState == NestState.NEEDS_TO_DUMP)
		{
			dump();
			return true;
		}
		for(MyPart p : myParts)
		{

			if (p.state == MyPartState.NEEDED)
			{

				askLaneToSendParts(p);
				return true;
			}
		}
		

		return false;
	}


	/** ACTIONS **/
	public void dump() {
		DoDumpNest();
		nestState = NestState.NORMAL;
		stateChanged();
	}
	
	public void tellMyLaneIHaveStabilized() {
		nestState = NestState.NORMAL;
		myLane.msgNestHasStabilized();
		stateChanged();
	}
	
	public void askLaneToSendParts(MyPart part) { 
		debug("asking lane to send parts of type "+part.pt.name + ".");
		part.state = MyPartState.REQUESTED;
		myLane.msgNestNeedsPart(part.pt);
		stateChanged();
	}
	
	
	/** ANIMATIONS **/
	private void DoDumpNest() {
		debug("dumping.");
	}
	
	
	/** OTHER **/
	public void setLane(Lane la) {
		myLane = la;
	}
	@Override
	public String getNestName() {
		// TODO Auto-generated method stub
		return null;
	}
	

	

}




