import java.util.Random;
//Robert Brandl Recitation: RI
//I pledge my honor that I have abided by the Stevens Honor System.
public class CoinPurse {
	
	//Data Fields
	private int numGalleons;
	private int numSickles;
	private int numKnuts;
	private static final int CAPACITY = 256; //max amount of ANY coins the purse can hold
	private Random r = new Random(); //initialize a random variable for generating random integers
	
	//Constructors
	
	/**
	 * Initializes a coin purse with no initial coins (empty).
	 */
	public CoinPurse() {
		this(0,0,0); // calls other constructor to initialize with no coins
	}
	
	/**
	 * Initializes the coin purse with a certain amount of coins less than or equal to CAPACITY.
	 * @param g - int galleons being put in purse
	 * @param s - int sickles being put in purse
	 * @param k - int knuts being put in purse
	 */
	public CoinPurse(int g, int s, int k) {
		if (g + s + k > CAPACITY){ // if capacity exceeded, error message will be outputted and purse will be created with no coins
			System.out.println("Error: total coins exceed the maximum capacity. Coin purse will be created with 0 coins (empty).");
			numGalleons = 0; 
			numSickles = 0;
			numKnuts = 0;
		}
		else if (g < 0 || s < 0 || k < 0) {
			System.out.println("Error: cannot have negative coin values. Coin purse will be created with 0 coins (empty).");
			numGalleons = 0; 
			numSickles = 0;
			numKnuts = 0;
		}
		else if (g >= 0 && s >= 0 && k >=0) {
			numGalleons = g;
			numSickles = s;
			numKnuts = k;
		}
		else {
			System.out.println("Error: input does not respresent an integer, no purse will be created");
		}
	}
	
	//Deposit/Withdrawal Methods
	/**
	 * Increases the number of galleons by n, unless capacity would be exceeded.
	 * @param n - the number of galleons to be deposited in the coin purse
	 */
	public void depositGalleons(int n) {
		if (n <= 0) {
			System.out.println("Error: invalid deposit amount, none will be deposited");
		}
		else if (numGalleons + numSickles + numKnuts + n > CAPACITY) {
			System.out.println("Error: Cannot carry this many additional coins, none will be deposited");
		}
		else if (n > 0) {
			numGalleons += n; //increases galleons by amount n
		}
		else {
			System.out.println("Error: input does not respresent an integer");
		}
	}
	
	/**
	 * Increases the number of sickles by n, unless capacity would be exceeded.
	 * @param n - the number of sickles to be deposited in the coin purse
	 */
	public void depositSickles(int n) {
		if (n <= 0) {
			System.out.println("Error: invalid deposit amount, none will be deposited");
		}
		else if (numGalleons + numSickles + numKnuts + n > CAPACITY) {
			System.out.println("Error: Cannot carry this many additional coins, none will be deposited");
		}
		else if (n > 0) {
			numSickles += n; //increases sickles by amount n
		}
		else {
			System.out.println("Error: input does not respresent an integer");
		}
	}
	
	/**
	 * Increases the number of knuts by n, unless capacity would be exceeded.
	 * @param n - the number of knuts to be deposited in the coin purse
	 */
	public void depositKnuts(int n) {
		if (n <= 0) {
			System.out.println("Error: invalid deposit amount, none will be deposited");
		}
		else if (numGalleons + numSickles + numKnuts + n > CAPACITY) {
			System.out.println("Error: Cannot carry this many additional coins, none will be deposited");
		}
		else if (n > 0) {
			numKnuts += n; //increases knuts by amount n
		}
		else {
			System.out.println("Error: input does not respresent an integer");
		}
	}
	
	/**
	 * Withdraws n number of galleons, as long as that many coins are in coin purse.
	 * @param n - represents the number of galleons being withdrawn.
	 */
	public void withdrawGalleons(int n) {
		if (n <= 0) {
			System.out.println("Error: invalid withdraw amount, none will be withdrawn");
		}
		else if (n > numGalleons) {
			System.out.println("Error: Withdrawal amount exceeds number of Galleons in purse, none will be withdrawn");
		}
		else if (n>0) {
			numGalleons -= n; //decreases galleons by amount withdrawn
		}
		else {
			System.out.println("Error: input does not respresent an integer");
		}
	}
	
	/**
	 *Withdraws n number of sickles, as long as that many coins are in coin purse.
	 * @param n - represents the number of sickles being withdrawn.
	 */
	public void withdrawSickles(int n) {
		if (n <= 0) {
			System.out.println("Error: invalid withdraw amount, none will be withdrawn");
		}
		else if (n > numSickles) {
			System.out.println("Error: Withdrawal amount exceeds number of Sickles in purse, none will be withdrawn");
		}
		else if (n>0) {
			numSickles -= n; //decreases sickles by amount withdrawn
		}
		else {
			System.out.println("Error: input does not respresent an integer");
		}
	}
	
	/**
	 * Withdraws n number of knuts, as long as that many coins are in coin purse.
	 * @param n - represents the number of knuts being withdrawn.
	 */
	public void withdrawKnuts(int n) {
		if (n <= 0) {
			System.out.println("Error: invalid withdraw amount, none will be withdrawn");
		}
		else if (n > numKnuts) {
			System.out.println("Error: Withdrawal amount exceeds number of Knuts in purse, none will be withdrawn");
		}
		else if (n >0) {
			numKnuts -= n; //decreases knuts by amount withdrawn
		}
		else {
			System.out.println("Error: input does not respresent an integer");
		}
	}
	
	//Cumulative Methods
	
	/**
	 *  Adds up the total number of coins (knuts+galleons+sickles).
	 * @return int representing the total amount of all types of coins in the purse.
	 */
	public int numCoins() {
		return (numGalleons + numSickles + numKnuts);
	}
	
	/**
	 * Sums up all the coins of the purse by converting each coin into knuts (29 knuts in a sickle and 493 knuts in a galleon).
	 * @return int for total knut value of coin purse.
	 */
	public int totalValue() {
		return (numKnuts + (numGalleons*493) + (numSickles*29));
	}
	
	/**
	 * Catenates a string which describes the quantity of each coin in the purse.
	 * @return String to describe contents of coin purse.
	 */
	public String toString() {
		return "The coin purse contains " + numGalleons + " Galleons, " + numSickles + " Sickles, and " + numKnuts + " Knuts";
	}

	//Exact Change Methods
	
	/**
	 * Determines whether the exact number of n knuts can be represented with the current coins (some subset).
	 * @param n - int representing number of knuts
	 * @return boolean value true (if the exact change of n knuts can be made), false otherwise.
	 */
	public boolean exactChange(int n) {
		if (n < 0) {
			System.out.println("Error: cannot have negative coins");
		}
		int galleons = numGalleons;
		int sickles = numSickles;
		int knuts = numKnuts;
		while (n >= 493 && galleons > 0) { //each galleon represents 493 knuts
			n -= 493;
			galleons--;
		}
		while (n >= 29 && sickles > 0) {//each sickle represents 29 knuts
			n -= 29;
			sickles--;
		}
		while (n >= 1  && knuts > 0) {
			n -= 1;
			knuts--;
		}
		if (n == 0) {
			return true;
		}
		else {
			return false;
		}
			
	}
	
	/**
	 * Determines whether n knuts (in value) can be withdrawn, and returns the coins that make up this value or a value just greater than it.
	 * @param n - int amount of knuts to withdraw.
	 * @return int array representing the coins that make up n value of knuts
	 */
	public int[] withdraw(int n) {
		int coinsOut = n;
		int[] arr = {0,0,0};//starts with blank array of length 3
		if (n < 0) {
			System.out.println("Error: invalid withdraw amount, none will be withdrawn");
			return new int [0];//returns empty array since no coins will be withdrawn
		}
		else if (n > this.totalValue()) {//checks if n exceeds value of purse
			System.out.println("Error: Withdrawal amount exceeds total value of coin purse! No coins will be withdrawn");
			return new int [0];//returns empty array since no coins will be withdrawn
		}
		else if (n == 0) {
			System.out.println("Error: coin value inputted equals 0, no coins will be withdrawn");
			return arr;
		}
		else if (n > 0) {
			while (this.exactChange(coinsOut) == false) {
				coinsOut += 1;
			}
			int galleons = numGalleons;
			int sickles = numSickles;
			int knuts = numKnuts;
			int gCount = 0, sCount = 0, kCount = 0; //counters for how much of each coin should be withdrawn
			while (coinsOut >= 493 && galleons > 0) {
				coinsOut -= 493;
				galleons -= 1;
				gCount +=1;
			}
			arr[0] = gCount;
			while (coinsOut >= 29 && sickles > 0) {
				coinsOut -= 29;
				sickles -= 1;
				sCount += 1;
			}
			arr[1] = sCount;
			while (coinsOut >= 1  && knuts > 1) {
				coinsOut -= 1;
				knuts -= 1;
				kCount += 1;
			}
			arr[2] = kCount;
			numGalleons -= gCount; //withdraws the coins necessary for n knuts (in value)
			numSickles -= sCount;
			numKnuts -= kCount;
			return arr; //returns how many of each coins will be withdrawn to create that amount [galleons, sickles, knuts]
		}
		else {
			System.out.println("Error: input does not respresent an integer");
			return new int [0];
		}
		
	}
	
	//Game of Chance - Random Coin Methods
	
	/**
	 * Draws a random coin from the coin purse based on the proportion of each coin in the purse (with replacement).
	 * If no coins in the purse, prints an error message.
	 * @return integer where 0 represents a Knut, 1 is a Sickle, and 2 is a Galleon.
	 */
	public int drawRandCoin() {
		if (this.numCoins() == 0) { //checks if there is any coins
			System.out.println("Error: No coins in purse, cannot draw a coin.");
			return -1;//returns -1 since there are no coins in purse
		}
		else {
			int randCoin = r.nextInt(this.numCoins());//uses total number of ocins to pick random number
			if (randCoin >=0 && randCoin < numKnuts) {//if statements are porportional to amount of each coins
				return 0;//for knut
			}
			else if (randCoin >= numKnuts && randCoin < numKnuts + numSickles) {
				return 1;//for sickle
			}
			else {
				return 2;//for galleon
			}
		}
	}
	
	/**
	 * Uses the randCoin function to generate n randomly selected coins from the coin purse, if possible.
	 * @param n - how many coins to be randomly chosen (length of return array)
	 * @return int array representing n randomly chosen coins with replacement.
	 */
	public int[] drawRandSequence(int n) {
		if (n <= 0) {
			System.out.println("Error: cannot have a sequence of negative or 0 length.");
			return new int [0];
		}
		else if (this.numCoins() == 0) {
			System.out.println("Error: No coins in purse, cannot draw any coins.");
			return new int [0];
		}
		else if (n > 0) {
			int[] randCoinList = new int[n];
			for (int i = 0; i < n; i++) { //sets up array with n randomly selected coins (represented by 0, 1, 2 accordingly)
				randCoinList[i]= this.drawRandCoin();
			}
			return randCoinList;
		}
		else {
			System.out.println("Error: input does not respresent an integer");
			return new int [0];
		}
	}
	
	/**
	 * Compares two sequences of randomly selected coins, comparing each coin at each index to each other, subtracting the value of sequence 1 by the value at sequence 2 and keeping an updating variable to be returned.
	 * @param coinSeq1 - int array of randomly selected coins represented by 0 (knut), 1 (sickle), 2 (galleon)
	 * @param coinSeq2 - int array like coinSeq1
	 * @return int representing the ultimate summation of one sequences subtracted by the other
	 */
	public static int compareSequences(int[] coinSeq1, int[] coinSeq2) {
		if (coinSeq1.length != coinSeq2.length) {
			System.out.println("Error: Sequences are not of equal length");
			return -2;
		}
		else if (coinSeq1.length == 0 || coinSeq2.length == 0) {
			System.out.println("Error: Sequences are empty, no comparisons can be made");
			return -2;
		}
		else {
			int step = 0; //holds the int value of the current comparison
			int seq1Wins = 0, seq2Wins = 0; //keeps track of wins
			for (int i = 0; i < coinSeq1.length; i++) {
				step = coinSeq1[i] - coinSeq2[i];
				if (step > 0) {
					seq1Wins++;
				}
				else if (step < 0) {
					seq2Wins++;
				}
			}
			if (seq1Wins > seq2Wins) {
				return 1;//coinSeq1 won more rounds
			}
			else if (seq2Wins > seq1Wins) {
				return -1;//coinSeq2 won more rounds
			}
			else {
				return 0;//tie
			}
		}
	}
	
	/**
	 * BONUS: same as drawRandSequence except with no coin replacement when drawing coins
	 * @param n - int that represents how many coins to choose randomly
	 * @return an integer array of random coins (represented by 0,1,2) selected with no replacement
	 */
	public int[] drawRandSequenceNoRepl(int n) {
		if (n <=0) {
			System.out.println("Error: cannot have a sequence of negative or 0 length.");
			return new int [0];
		}
		else if (this.numCoins() < n) {
			System.out.println("Error: Not enough coins in the purse to compute.");
			return new int[0];
		}
		else if (n>0) {
			int[] randCoinList = new int[n];
			int value = 0;
			int totCoins = this.numCoins();//uses initial amount of coins, and decreases after each random pick
			int knuts = numKnuts, sickles = numSickles;//uses initial amount of each coin, decreases based on if selected randomly
			for (int i = 0; i < n; i++) {
				int randCoin = r.nextInt(totCoins);
				if (randCoin >=0 && randCoin < knuts) {
					value = 0;
					knuts--;
					totCoins--;
				}
				else if (randCoin >= knuts && randCoin < knuts + sickles) {
					value = 1;
					sickles--;
					totCoins--;
				}
				else {
					value = 2;
					totCoins--;
				}
				randCoinList[i]= value;
			}
			return randCoinList;
			
		}
		else {
			System.out.println("Error: input does not respresent an integer");
			return new int [0];
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
