package transpose;

/**
 * Represents an entry in a Dictionary data structure, with a key-value pairing.
 * Consists of an integer key, and a String value.
 * @author Philip Lung
 *
 */

public class KeyValue {
	/* Integer declaration */
	private int key;
	/* String declaration */
	private String value;

	/**
	 * Constructor with parameters
	 * @param key integer representing the key
	 * @param value String representing the value
	 */
	public KeyValue(int key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @return Returns String representation of key-value pair.
	 */
	public String toString() {
		return this.key + "    " + this.value;
	}

}
