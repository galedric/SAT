package sat.tower;

import sat.events.Event;
import sat.radio.RadioID;
import sat.utils.geo.Coordinates;

@SuppressWarnings("serial")
public abstract class TowerEvent extends Event {
	public static abstract class PlaneTowerEvent extends TowerEvent {
		RadioID id;

		public PlaneTowerEvent(RadioID id) {
			this.id = id;
		}

		public RadioID getId() {
			return id;
		}
	}

	public static class PlaneMoved extends PlaneTowerEvent {
		private Coordinates where;
		
		public PlaneMoved(RadioID id, Coordinates where) {
			super(id);
			this.where = where;
		}
		
		public Coordinates getWhere() {
			return where;
		}
	}
}