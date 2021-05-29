
public class MultiplayerGame {
	//Robert Brandl Recitation: RI
	//I pledge my honor that I have abided by the Stevens Honor System.
	
	//Data Fields
	private GameEntity turnTracker;
	private GameEntity[] index;
	
	//Basic Operations
	/**
	 *  Creates a new MultiplayerGame with n players, initializes index to an array of size n and creates a 
	 *  new GamePlayer with the appropriate id to store at each index. 
	 * @param n
	 */
	public MultiplayerGame(int n) {
		index = new GameEntity[n];
		if (n <= 0) {
			throw new IllegalArgumentException("MultiplayerGame: cannot have 0 or negative players");
		}
		if (n == 1) {
			index[0] = new GamePlayer(null,null, 0);
		}
		else {
			for (int i = 0; i < n; i++) {
				index[i] = new GamePlayer(null,null, i);
			}
			for (int i = 0; i < n; i++) {
				if (i == 0 ) {
					index[i].prev = index[n-1];
					index[i].next = index[i+1];
				}
				else if (i == n - 1) {
					index[i].prev = index[i - 1];
					index[i].next = index[0];
				}
				else {
					index[i].next = index[i+1];
					index[i].prev = index[i-1];
				}
			}
		}
	}
	
	/**
	 * Returns the size of the MultiplayerGame (the number of GamePieces in play, GamePlayers do not add to size)
	 * @return
	 */
	public int size() {
		if (index.length == 0) {
			return 0;
		}
		int count = 0;
		while (index[count] == null) {//checks whether a player exists at count, if not, moves to next index until not null
			count++;
		}
		int counter = index[count].size();//starts counting the size with the first player that is not null
		GameEntity start = index[count];
		GameEntity current = start.next;
		while (current.equals(start) == false) {//loops through the double linked list until returning to start
			counter += current.size();
			current = current.next;
		}
		return counter;
	}
	
	/**
	 * Adds a GamePiece owned by specified player of the given strength to the game. 
	 * If the player already owns a GamePiece of that name, or the player doesn't exist, then an exception is thrown
	 * @param playerId - player to add a piece to
	 * @param name - name of the piece
	 * @param strength - strength of the piece
	 */
	public void addGamePiece(int playerId, String name, int strength) {
		if (playerId < 0 || playerId > index.length - 1 || index[playerId] == null) {
			throw new IllegalArgumentException("addGamePiece: no such player");
		}
		GameEntity current = index[playerId];
		while (current.next.isGamePlayer() == false) {//makes sure game piece is not a repeat name
			if (current.next.getName().equals(name)) {
				throw new IllegalArgumentException( "addGamePiece: duplicate entity");
			}
			current = current.next;
		}
		boolean lessThan = true;//keeps track of whether the new piece's strength is less than all other pieces
		current = index[playerId];//returns current to initial value
		GameEntity save = new GamePiece(null, null, null, 0);//GameEntity to save the place where the new piece should be inserted
		if (current.next.isGamePlayer() == false) {//checks if there are any game pieces owned by playerId
			while (current.next.isGamePlayer() == false) {//looping to check where the new piece should be placed, based on strength
				if (((GamePiece)current.next).getStrength() <= strength) {
					save = current.next;
					lessThan = false;
				}
				current= current.next;
			}
			if (lessThan == true) {//if piece's strength is less than all pieces, place at end of playerId's pieces
				GameEntity newPiece = new GamePiece(current, current.next, name, strength);
				current.next = newPiece;
				newPiece.next.prev = newPiece;
			}
			else {//places piece before the player that is JUST less than or equal to it, based on save
				GameEntity newPiece = new GamePiece(save.prev, save, name, strength);
				GameEntity previous = save.prev;
				previous.next = newPiece;
				save.prev = newPiece;
			}
			
		}
		else {//if no other pieces already!
			GameEntity newPiece = new GamePiece(current, current.next, name, strength);
			current.next = newPiece;
			newPiece.next.prev = newPiece;
		}
		
	}
	
	/**
	 * Removes the GamePiece owned by specified player of the given name. If no such GamePiece exists, or if the
	 * player does not exist, then an exception is thrown.
	 * @param playerId - player to remove the piece from
	 * @param name - name of piece to be removed
	 */
	public void removeGamePiece(int playerId, String name) {
		if (playerId < 0 || playerId > index.length - 1 || index[playerId] == null) {
			throw new IllegalArgumentException("removeGamePiece: no such player");
		}
		GameEntity current = index[playerId];
		boolean nameAppears = false;//checks whether the name exists within playerId's pieces
		GameEntity temp = new GamePiece(null,null,null,0);//holds the place, if any, where the game piece exists
		while (current.next.isGamePlayer() == false) {//iterates through playerId's pieces
			if (current.next.getName().equals(name)) {
				temp = current.next;
				//current.next = current.next.next;
				//current.next.next.prev = current;
				nameAppears = true;
			}
			current = current.next;
		}
		if (nameAppears == true) {//if the piece exists, removes it
			GameEntity before = temp.prev;
			GameEntity after = temp.next;
			before.next = after;
			after.prev = before;
			temp = null;
		}
		else {//throws exception if piece does not exist
			throw new IllegalArgumentException("removeGamePiece: entity does not exist");
		}
	}
	
	/**
	 * Checks if any player has a GamePiece of the given name.
	 * @param name - name of the piece to check
	 * @return true or false
	 */
	public boolean hasGamePiece(String name) {
		int count = 0;
		while (index[count] == null) {//checks to make sure the first piece that exists is considered
			count++;
		}
		GameEntity start = index[count];
		GameEntity current = start.next;
		while (current.equals(start) == false) {//loops through entire linked list
			if (current.isGamePlayer() == false) {
				if (current.getName().equals(name)) {//searches for that piece name
					return true;//ends loop if found
				}
			}
			current = current.next;
		}
		return false;//not found
	}
	
	/**
	 * Removes all the GamePieces owned by specified player. If the player does not exist, then an exception is thrown.
	 * @param playerId - player to remove all pieces from
	 */
	public void removeAllGamePieces(int playerId) {
		if (playerId < 0 || playerId > index.length - 1 || index[playerId] == null) {
			throw new IllegalArgumentException("removeAllGamePieces: no such player");
		}
		if (playerId == index.length - 1) {//handles edge case when the last player is chosen
			int count = 0;
			while(index[count] == null) {//finds the first player that exists
				count++;
			}
			index[playerId].next = index[count];
			index[count].prev = index[playerId];
		}
		else {
			index[playerId].next = index[playerId+1];//when edge case does not apply
			index[playerId+1].prev = index[playerId];
		}
	}
	
	/**
	 * Increases the strength of all GamePieces owned by the specified player by n. If the player does not exist,
	 * then an exception is thrown.
	 * @param playerId - player to increase pieces strength
	 * @param n - amount to increase strength by
	 */
	public void increaseStrength(int playerId, int n) {
		if ( playerId < 0 || playerId > index.length - 1 || index[playerId] == null ) {
			throw new IllegalArgumentException("increaseStrength: no such player");
		}
		GameEntity current = index[playerId].next;
		while (current.isGamePlayer() == false) {//loops through all game pieces, if none, then nothing happens
			((GamePiece)current).updateStrength(n);//updates strength by n amount for each piece
			current = current.next;
		}
	}
	
	/**
	 * Removes the GamePlayer and all their GamePieces from the game. If the player does not exist or has already been 
	 * removed, then an exception is thrown.
	 * @param playerId - the player to remove
	 */
	public void removePlayer(int playerId) {
		if (playerId < 0 || playerId > index.length - 1 || index[playerId] == null ) {
			throw new IllegalArgumentException("removePlayer: no such player");
		}
		if (playerId == index.length - 1) {//handles for if the last player in index is removed
			GameEntity previous = index[playerId].prev;
			int count = 0;
			while(index[count] == null) {
				count++;
			}
			GameEntity nextP = index[count];
			previous.next = nextP;
			nextP.prev = previous;
		}
		else {//handles the in between cases
			if(index[playerId+1] != null) {
				GameEntity nextP = index[playerId+1];
				GameEntity previous = index[playerId].prev;
				previous.next = nextP;
				nextP.prev = previous;
			}
			else {//when playerId+1 doesn't exist, find next player that does
				int count = playerId+1;
				while(index[count] == null) {
					if (count == index.length -1 ) {
						count = 0;
					}
					count++;
				}
				GameEntity nextP = index[count];
				GameEntity previous = index[playerId].prev;
				previous.next = nextP;
				nextP.prev = previous;
			}
		}
		index[playerId] = null;
		
	}
	
	/**
	 * Swaps the GamePieces of the two specified players. If either of the players does not exist, then an 
	 * exception is thrown.
	 * @param playerId1 - the first player to be swapped
	 * @param playerid2 - the second player to be swapped
	 */
	public void swapPieces(int playerId1, int playerid2) {
		if ( playerId1 < 0 || playerId1 > index.length - 1 || index[playerId1] == null || playerid2 < 0 || playerid2 > index.length - 1 || index[playerid2] == null ) {
			throw new IllegalArgumentException("swap: no such player");
		}
		if (playerId1 != playerid2 || (index[playerId1].next == null && index[playerid2].next == null)) {//if same player or no pieces for either, nothing should happen
			if (index[playerid2].next.isGamePlayer() == true) {//when player 2 has no pieces, but the first does
				GameEntity current = index[playerId1].next;
				while(current.isGamePlayer() == false) {
					addGamePiece(playerid2,current.getName(), ((GamePiece) current).getStrength());
					current = current.next;
				}
				removeAllGamePieces(playerId1);
			}
			else if (index[playerId1].next.isGamePlayer() == true) {//when the first player has no pieces, but the second does
				GameEntity current = index[playerid2].next;
				while(current.isGamePlayer() == false) {
					addGamePiece(playerId1,current.getName(), ((GamePiece) current).getStrength());
					current = current.next;
				}
				removeAllGamePieces(playerid2);
			}
			else {
				GameEntity id1Piece1 = index[playerId1].next;//takes the first player's first piece
				GameEntity id1PieceLast = new GamePiece(null,null,null,0);//takes the first player's last piece
				if (playerId1 == index.length - 1) {
					int count = 0;
					while(index[count] == null) {
						count++;
					}
					id1PieceLast = index[count].prev;
				}
				else {
					id1PieceLast = index[playerId1 + 1].prev;
				}
				GameEntity id2Piece1 = index[playerid2].next;//takes the second player's first piece
				GameEntity id2PieceLast = new GamePiece(null,null,null,0);//takes the second player's last piece
				if (playerid2 == index.length - 1) {
					int count = 0;
					while(index[count] == null) {
						count++;
					}
					id2PieceLast = index[count].prev;
				}
				else {
					id2PieceLast = index[playerid2 + 1].prev;
				}
				//uses the first and last pieces of each player to adjust the next and prev fields as needed
				(id1Piece1.prev).next = id2Piece1;
				id1Piece1.prev = index[playerid2];
				if (playerid2 == index.length - 1) {
					(id1PieceLast.next).prev = id2PieceLast;
					int count = 0;
					while(index[count] == null) {
						count++;
					}
					id1PieceLast.next = index[count];
				}
				else {
					(id1PieceLast.next).prev = id2PieceLast;
					id1PieceLast.next = index[playerid2+1];
				}
				(id2Piece1.prev).next = id1Piece1;
				id2Piece1.prev = index[playerId1];
				if (playerId1 == index.length - 1) {
					(id2PieceLast.next).prev = id1PieceLast;
					int count = 0;
					while(index[count] == null) {
						count++;
					}
					id2PieceLast.next = index[count];
				}
				else {
					(id2PieceLast.next).prev = id1PieceLast;
					id2PieceLast.next = index[playerId1+1];
				}
			}
		}
	}
	
	/**
	 *  Should output the String representation of all players and their pieces (in order of strength - already 
	 *  accounted for when new pieces are added).
	 */
	public String toString() {
		int count = 0;
		while (index[count] == null) {//starts at the first player that exists (not null)
			count++;
		}
		GameEntity start = index[count];
		GameEntity current = start.next;
		String repr = "[" + start.toString() + "; ";//uses the toString of each player and piece
		while(current.next != start && current.next != null) {
			repr += current.toString();
			if (current.isGamePlayer() == false) {
				repr += " strength: " + ((GamePiece)current).getStrength();
			}
			repr += "; ";
			current = current.next;
		}
		if (current != start) {
			repr += current.toString();
		}
		if (current.isGamePlayer() == false && current != start) {
			repr += " strength: " + ((GamePiece)current).getStrength();
		}
		return repr + "]";
	}
	
	/**
	 * public method to access the values in index, ONLY used for TESTING in MultiplayerGameTest!!
	 * @return GameEntity[] index to represent the contents of the MuliplayerGame
	 */
	public GameEntity[] getIndex() {
		return index;
	}
	
	/**
	 * public method to access the value in turnTracker, ONLY used for TESTING in MultiplayerGameTest!!
	 * @return GameEntity 
	 */
	public GameEntity getTurnTracker() {
		return turnTracker;
	}
	
	
	//Processing A Turn Functions
	
	/**
	 * Sets the turnTracker to the first GamePlayer.
	 */
	public void initializeTurnTracker() {
		if (index.length == 0) {///checks if any players in game
			throw new ArrayIndexOutOfBoundsException("initializeTurnTracker: no players in game");
		}
		int count = 0;
		while (index[count] == null && count != index.length - 1) {//starts at the first player that exists (not null)
			count++;
		}
		if (count == index.length - 1 && index[count] == null) {//all players in game are null
			throw new ArrayIndexOutOfBoundsException("initializeTurnTracker: no valid players in game");
		}
		turnTracker = index[count];
	}
	
	/**
	 * Moves the turnTracker to the next GamePlayer.
	 */
	public void nextPlayer() {
		if (index.length > 0) {//checks that at least one player exists
			if (turnTracker.isGamePlayer() == true) {
				if (((GamePlayer)turnTracker).getPlayerId() == index.length -1) {//if on last player, return to beginning
					int count = 0;
					while(index[count] == null) {//check for first player that exists
						count++;
					}
					turnTracker = index[count];
				}
				else {
					int count = ((GamePlayer)turnTracker).getPlayerId()+1;
					if (index[((GamePlayer)turnTracker).getPlayerId()+1] == null){//finds the next player not null
						for (int i = 0; i < index.length; i++) {
							if (index[count] == null && count != index.length -1) {
								count++;
							}
							else if (index[count] == null && count == index.length -1) {
								count = 0;
							}
							else {
								break;
							}
						}
					}
					turnTracker = index[count];
				}
			}
			else if (turnTracker.isGamePlayer() == false) {//if turntRacker is set to a GamePiece
				while(turnTracker.next.isGamePlayer() == false || turnTracker.next == null) {
					turnTracker = turnTracker.next;
				}
				turnTracker = turnTracker.next;
			}
		}
		else {//no players in game
			throw new ArrayIndexOutOfBoundsException("nextPlayer: no valid players in game");
		}
	}
	
	/**
	 * Moves the turnTracker to the next GameEntity, which could be a GamePlayer or a GamePiece.
	 */
	public void nextEntity() {
		if (turnTracker.next == null) {//finds the next valid entity
			while (turnTracker.next == null) {
				turnTracker = turnTracker.next;
			}
		}
		else {
			turnTracker = turnTracker.next;
		}
	}
	
	/**
	 * Backtracks the turnTracker to the previous GamePlayer.
	 */
	public void prevPlayer() {//similar to nextPlayer, just using the prev values
		if (index.length > 0) {
			if (turnTracker.isGamePlayer() == true) {
				if (turnTracker == index[0]) {
					int count = index.length - 1;
					while(index[count] == null) {
						count--;
					}
					turnTracker = index[count];
				}
				else {
					int count = ((GamePlayer)turnTracker).getPlayerId()-1;
					if (index[count] == null){
						for (int i = 0; i < index.length; i++) {
							if (index[count] == null && count != 0) {
								count--;
							}
							else if (index[count] == null && count == 0) {
								count = index.length - 1;
							}
							else {
								break;
							}
						}
					}
					turnTracker = index[count];
				}
			}
			else if (turnTracker.isGamePlayer() == false) {
				while(turnTracker.prev.isGamePlayer() == false || turnTracker.prev == null) {
					turnTracker = turnTracker.prev;
				}
				turnTracker = turnTracker.prev;
			}
		}
		else {//no players in index
			throw new ArrayIndexOutOfBoundsException("nextPlayer: no valid players in game");
		}
	}
	
	/**
	 * Returns the string representation of the current entity pointed to by the turnTracker.
	 */
	public String currentEntityToString() {
		return turnTracker.toString();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*MultiplayerGame g3 = new MultiplayerGame(3);
		g3.addGamePiece(2, "BluePiece", 5);
		g3.addGamePiece(1, "PurplePiece", 2);
		g3.addGamePiece(1, "OrangePiece", 17);
		g3.removeGamePiece(1, "PurplePiece");
		System.out.println(g3.toString());*/
		
		/*MultiplayerGame mg = new MultiplayerGame(3);
		mg.addGamePiece(0, "Hulk", 2);
		mg.addGamePiece(0, "Captain America", 3);
		mg.addGamePiece(1, "Thor", 4);
		mg.addGamePiece(1, "Black Widow", 1);
		mg.addGamePiece(2, "Wasp", 1);
		System.out.println(mg.hasGamePiece("Thor"));
		System.out.println("The size of mg is " + mg.size());
		mg.swapPieces(1,0);
		System.out.println(mg.toString());
		mg.removeAllGamePieces(0);
		//mg.removeGamePiece(1, "Black Widow");
		mg.increaseStrength(1, 7);
		System.out.println("The size of mg is " + mg.size());
		mg.removePlayer(1);
		System.out.println(mg.toString());*/
	}

}
