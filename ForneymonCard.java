package ForneyMonCardGame;

public abstract class ForneymonCard {
	private String name;
	private String type;
	
	ForneymonCard (String n, String t) {
		if (n.equals("")) {
			throw new IllegalArgumentException("Name cannot be blank.");
		} else if (!t.equals("Burnymon") && !t.equals("Dampymon") && !t.equals("Leafymon")) {
			throw new IllegalArgumentException("Incorrect type.");
		}
		name = n;
		type = t;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public String toString() {
		return (type + ": " + name);
	}
}
