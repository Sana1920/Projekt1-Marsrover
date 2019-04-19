package rover;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Start {

	static Random random = new Random();
	static LinkedHashMap<int[], String> mars;

	public static void befülleDenMars() {
		mars = new LinkedHashMap<>();
		int BREITE = 80;
		int HÖHE = 20;
		int HÄLFTE_X = BREITE / 2;
		int HÄLFTE_Y = HÖHE / 2;
		for (int i = 0; i < BREITE; i++) {
			for (int j = 0; j < HÖHE; j++) {
				int[] Koordinaten = new int[] { i, j };
				if (random.nextDouble() < 0.25 && !(HÄLFTE_X == i && HÄLFTE_Y == j))
					mars.put(Koordinaten, "#");
			}
		}
		mars.put(new int[] { HÄLFTE_X, HÄLFTE_Y }, "n");
	}

	public static int[] maxBreiteUndHöhe(Set<int[]> keys) {
		int[] Koordinaten = new int[2];
		for (int[] key : keys) {
			if (key[0] > Koordinaten[0])
				Koordinaten[0] = key[0];
			if (key[1] > Koordinaten[1])
				Koordinaten[1] = key[1];
		}
		return Koordinaten;
	}

	public static String getZeichen(Map<int[], String> kloetze, int[] Koordinaten) {
		Set<Entry<int[], String>> entrySet = kloetze.entrySet();
		for (Entry<int[], String> entry : entrySet) {
			if (entry.getKey()[0] == Koordinaten[0] && entry.getKey()[1] == Koordinaten[1])
				return entry.getValue();
		}
		return null;
	}

	public static void zeichneMap() {
		
		String [] Zeichen1 = {"#","n","s","e","w"};
		String [] Zeichen2 = {"#","^","V",">","<"};
		int[] Koordinaten = maxBreiteUndHöhe(mars.keySet());
		
		zeichneKreuze(Zeichen1, Zeichen2, Koordinaten);
		zeichneGrenze(Koordinaten);
		
		
	}

	
	public static void zeichneKreuze(String[] Zeichen1, String[] Zeichen2, int[]Koordinaten) {
		
		for (int j = 0; j < Koordinaten[1]; j++) {
			for (int i = 0; i < Koordinaten[0]; i++) {
				
				if (getZeichen(mars, new int[] { i, j }) == null) {
					System.out.print(" ");
					continue;
				}
				
				for(int k = 0; k <Zeichen1.length; k++) {
					if (getZeichen(mars, new int[] { i, j }).equals(Zeichen1[k]))
						System.out.print(Zeichen2[k]);
				}
				

			}
			
			System.out.println();
		}
		
		
	}
	
	public static void zeichneGrenze(int[]Koordinaten) {
		for (int i = 0; i < Koordinaten[0]; i++) {
			System.out.print("=");
		}
		System.out.println();
		
	}
	
	
	
	
	
	public static void main(String[] args) {

		if (args.length > 1) {
			long seed = Long.parseLong(args[1]);
			random.setSeed(seed);
			 //System.out.println("Seed: " + seed);
		}
		
		befülleDenMars();
		String eingabe = args[0];
		zeichneMap();
		for (int i = 0; i < eingabe.length(); i++) {
			bewegeDenRover(eingabe.charAt(i));
			zeichneMap();
		}
	}

	
	public static void untersucheDieUmgebung(int [] Rover, int [] Speicher) {
		  
		if(getZeichen(mars, Rover).equals("#")) {
			   
			   Rover[0] = Speicher[0];
			   Rover[1] = Speicher[1];
			   //mars.put(Rover, getZeichen(mars,Rover));
		   }
		
		
		
	}
	
	
	
	
	public static void bewegeDenRover(char bewegung) {
		String  Zeichen3 = "fblr";
		int [] Rover = findeRover();
		for(int l = 0; l < Zeichen3.length(); l++) {
			char  lauf = Zeichen3.charAt(l);
			
			if (lauf == bewegung)  {
				if(l==0) bewegeDenRoverf(Rover);
				else if(l==1)bewegeDenRoverb(Rover);
				else if(l==2)bewegeDenRoverl(Rover);
				else if(l==3)bewegeDenRoverr(Rover);
				
			}
			
			
		}
				
		
	}

	public static void bewegeDenRoverf(int [] Rover) {
		int [] Speicher = new int [2];
		Speicher [0] = Rover[0];
		Speicher [1] = Rover[1];
		
		if (getZeichen(mars, Rover).equals("n")) {
			Rover[1]--;
			untersucheDieUmgebung(Rover,Speicher);
		
		}
				
		else if (getZeichen(mars, Rover).equals("s")) {
			Rover[1]++;
			untersucheDieUmgebung(Rover,Speicher);
		}
		else if (getZeichen(mars, Rover).equals("e")) {
			Rover[0]++;
			untersucheDieUmgebung(Rover,Speicher);
		}	
		else if (getZeichen(mars, Rover).equals("w")) {
			Rover[0]--;
			untersucheDieUmgebung(Rover,Speicher);
		}
		//mars.put(Rover, getZeichen(mars,Rover));
	}
	
	public static void bewegeDenRoverb(int [] Rover) {
		int[] Speicher = new int[2];
		Speicher [0] = Rover[0];
		Speicher [1] = Rover[1];
		
		
		if (getZeichen(mars, Rover).equals("s")) {
			Rover[1]--;
			untersucheDieUmgebung(Rover, Speicher);
		}
		else if (getZeichen(mars, Rover).equals("n")) {
			Rover[1]++;
			untersucheDieUmgebung(Rover, Speicher);
		}
		else if (getZeichen(mars, Rover).equals("w")) {
			Rover[0]++;
			untersucheDieUmgebung(Rover, Speicher);
		}	
		else if (getZeichen(mars, Rover).equals("e")) {
			Rover[0]--;
			untersucheDieUmgebung(Rover, Speicher);
		}
		//mars.put(Rover, getZeichen(mars,Rover));
	}
	
	public static void bewegeDenRoverl(int [] Rover) {
		String [] Zeichen1 = {"n","s","e","w"};
		String [] Zeichen2 = {"w","e","n","s"};
		
		for(int k = 0; k <Zeichen1.length; k++) {
			if (getZeichen(mars, Rover).equals(Zeichen1[k])) {
				mars.put(Rover, Zeichen2[k]);
		        k = Zeichen1.length;
			}
			
		}	
		
		
	
	}

	public static void bewegeDenRoverr(int [] Rover) {
		String [] Zeichen2 = {"n","s","e","w"};
		String [] Zeichen1 = {"w","e","n","s"};
		
		for(int k = 0; k <Zeichen1.length; k++) {
			if (getZeichen(mars, Rover).equals(Zeichen1[k])) {
				mars.put(Rover, Zeichen2[k]);
		        k = Zeichen1.length;
			}
			
		}	
	
	
	}

	
	
	
	
	
	
	private static int[] findeRover() {
		Set<Entry<int[], String>> entrySet = mars.entrySet();
		for (Entry<int[], String> entry : entrySet) {
			if (entry.getValue() != null && !entry.getValue().equals("#"))
				return entry.getKey();
		}
		throw new IllegalStateException("Rover missing in action");
	}


	




}
