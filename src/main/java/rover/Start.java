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
		
		
		for (int i = 0; i < Koordinaten[0]; i++) {
			System.out.print("=");
		}
		System.out.println();
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
	
	
	
	
	
	public static void main(String[] args) {

		//if (args.length > 1) {
		//	long seed = Long.parseLong(args[1]);
		//	random.setSeed(seed);
			// System.out.println("Seed: " + seed);
		//}
		
		befülleDenMars();
		String eingabe = args[0];
		zeichneMap();
		for (int i = 0; i < eingabe.length(); i++) {
			bewegeDenRover(eingabe.charAt(i));
			zeichneMap();
		}
	}

	public static void bewegeDenRover(char bewegung) {
		if (bewegung == 'f') {
			int[] p = findeRover();
			if (getZeichen(mars, p).equals("n"))
				p[1]--;
			else if (getZeichen(mars, p).equals("s"))
				p[1]++;
			else if (getZeichen(mars, p).equals("e"))
				p[0]++;
			else if (getZeichen(mars, p).equals("w"))
				p[0]--;
		} else if (bewegung == 'b') {
			int[] p = findeRover();
			if (getZeichen(mars, p).equals("s"))
				p[1]--;
			else if (getZeichen(mars, p).equals("n"))
				p[1]++;
			else if (getZeichen(mars, p).equals("w"))
				p[0]++;
			else if (getZeichen(mars, p).equals("e"))
				p[0]--;
		} else if (bewegung == 'l') {
			int[] p = findeRover();
			if (getZeichen(mars, p).equals("n"))
				mars.put(p, "w");
			else if (getZeichen(mars, p).equals("s"))
				mars.put(p, "e");
			else if (getZeichen(mars, p).equals("e"))
				mars.put(p, "n");
			else if (getZeichen(mars, p).equals("w"))
				mars.put(p, "s");
		} else if (bewegung == 'r') {
			int[] p = findeRover();
			if (getZeichen(mars, p).equals("w"))
				mars.put(p, "n");
			else if (getZeichen(mars, p).equals("e"))
				mars.put(p, "s");
			else if (getZeichen(mars, p).equals("n"))
				mars.put(p, "e");
			else if (getZeichen(mars, p).equals("s"))
				mars.put(p, "w");
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
