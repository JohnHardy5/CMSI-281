package forneymonGame;
/*
 * Written by John Hardy
 */

public class FlippingForneymonCard extends ForneymonCard {
	private boolean isFaceDown;
	
	FlippingForneymonCard (String name, String type, boolean isFD) {
		super(name, type);
		isFaceDown = isFD | false;//Will default to faceUp if no input is given.
	}
	
	public boolean getIsFaceDown() {
		return isFaceDown;
	}
	
	public boolean flip() {
		isFaceDown = !isFaceDown;
		return isFaceDown;
	}
	
	public int match (FlippingForneymonCard other) {
		int value = 3;
		if (isFaceDown || other.getIsFaceDown()) {
			value = 2;
		} else if ((!isFaceDown && !other.getIsFaceDown()) && (this.getName().equals(other.getName())) && (this.getType().equals(other.getType()))) {
			value = 1;
		} else if ((!isFaceDown && !other.getIsFaceDown()) && ((!this.getName().equals(other.getName())) || (!this.getType().equals(other.getType())))) {
			value = 0;
		}
		return value;
	}
	
	public String toString() {
		if (isFaceDown) {
			return("?: ?");
		}
		return super.toString();
	}
}
