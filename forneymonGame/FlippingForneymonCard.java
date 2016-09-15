package forneymonGame;
/*
 * Written by John Hardy
 */

public class FlippingForneymonCard extends ForneymonCard {
	private boolean isFaceDown;
	
	FlippingForneymonCard (String name, String type, boolean isFD) {
		super(name, type);
		isFaceDown = isFD;
	}
	
	public boolean getIsFaceDown() {
		return isFaceDown;
	}
	
	public boolean flip() {
		isFaceDown = !isFaceDown;
		return isFaceDown;
	}
	
	public int match(FlippingForneymonCard other) {
		if (isFaceDown || other.getIsFaceDown()) {
			return 2;
		} else if (super.getName().equals(other.getName()) && (super.getType().equals(other.getType()))) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public String toString() {
		if (isFaceDown) {
			return("?: ?");
		}
		return super.toString();
	}
}
