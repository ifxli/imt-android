package its.my.time.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Types {

	public static class Event {
		public static final int BASE = 0;
		public static final int TASK = 1;
		public static final int MEETING = 2;
		public static final int CALL = 3;

		public static String getLabelById(int id) {
			if(id==BASE) return "event";
			else if (id == TASK) return "task";
			else if (id == MEETING) return "meeting";
			else if (id ==CALL) return "call";
			else return null;
		}

		public static int getIdByLabel(String label) {
			if(label == null) {
				return BASE;
			}
			if(label.equals("event")) return BASE;
			else if (label.equals("task")) return TASK;
			else if (label.equals("meeting")) return MEETING;
			else if (label.equals("call")) return CALL;
			else return BASE;
		}
	
	public static String[] getAll() {
		return new String[]{"Ev�nement", "R�union","Appel"};
	}
		
	}

	public static class Comptes {

		public final static Compte GOOGLE = new Compte(1, "Google", "gmail");
		public final static Compte MYTIME = new Compte(2, "My Time", "imt");

		public static List<Compte> getAllCompte() {
			List<Compte> all = new ArrayList<Types.Comptes.Compte>();
			all.add(GOOGLE);
			all.add(MYTIME);
			return all;
		}


		public static List<Compte> getAllCompteEditable() {
			List<Compte> all = new ArrayList<Types.Comptes.Compte>();
			all.add(GOOGLE);
			return all;
		}

		public static Collection<? extends CharSequence> getAllLabels() {
			List<String> labels = new ArrayList<String>();
			List<Compte> all = getAllCompte();
			for (Compte compte : all) {
				labels.add(compte.label);
			}
			return labels;
		}

		public static Collection<? extends CharSequence> getAllLabelsEditable() {
			List<String> labels = new ArrayList<String>();
			List<Compte> all = getAllCompteEditable();
			for (Compte compte : all) {
				labels.add(compte.label);
			}
			return labels;
		}

		public static int getIdFromLabel(String label) {
			if(label == null) {
				return 0;
			}
			if(label.equals(MYTIME.label)) {return MYTIME.id;}
			if(label.equals(GOOGLE.label)) {return GOOGLE.id;}
			return 0;
		}

		public static String geWsLabelFromId(int type) {
			if(type == MYTIME.id) {return MYTIME.wsLabel;}
			if(type == GOOGLE.id) {return GOOGLE.wsLabel;}
			return "";
		}

		public static class Compte {
			public int id;
			public String label;
			public String wsLabel;
			public Compte(int id, String label, String wsLabel) {
				super();
				this.id = id;
				this.label = label;
				this.wsLabel = wsLabel;
			}
		}

	}
}