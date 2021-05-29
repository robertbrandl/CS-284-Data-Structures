import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class MultiplayerGameTest {
	MultiplayerGame mg = new MultiplayerGame(3);
	//MultiplayerGame s = new MultiplayerGame(2);
	

	@Test
	void constructorNoPlayerTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			MultiplayerGame failTest = new MultiplayerGame(0);
	    });
	}
	
	@Test
	void checkAddGamePiece() {
		mg.addGamePiece(0, "Hulk", 2);
		mg.addGamePiece(0, "Captain America", 3);
		mg.addGamePiece(1, "Thor", 4);
		mg.addGamePiece(1, "Black Widow", 1);
		mg.addGamePiece(2, "Wasp", 1);
		assertTrue(mg.size() == 5);
		assertThrows(IllegalArgumentException.class, () -> {
			mg.addGamePiece(2, "Wasp", 1);
	    });
		assertThrows(IllegalArgumentException.class, () -> {
			mg.addGamePiece(-1, "Wasp", 1);
	    });
	}
	
	@Test
	void checkRemoveGamePiece() {
		mg.addGamePiece(0, "Captain America", 3);
		mg.addGamePiece(1, "Black Widow", 1);
		mg.removeGamePiece(1, "Black Widow");
		assertTrue(mg.size() == 1);
		assertThrows(IllegalArgumentException.class, () -> {
			mg.removeGamePiece(-1, "Wasp");
	    });
		assertThrows(IllegalArgumentException.class, () -> {
			mg.removeGamePiece(0, "Wasp");
	    });
	}
	
	@Test
	void checkSize() {
		mg.addGamePiece(0, "Hulk", 2);
		mg.addGamePiece(0, "Captain America", 3);
		mg.addGamePiece(1, "Thor", 4);
		assertTrue(mg.size() == 3);
		MultiplayerGame em = new MultiplayerGame(2);
		assertTrue(em.size() == 0);
	}
	
	@Test
	void checkHasPlayer() {
		assertFalse(mg.hasGamePiece("Thor"));
		mg.addGamePiece(0, "Hulk", 2);
		mg.addGamePiece(0, "Captain America", 3);
		mg.addGamePiece(1, "Thor", 4);
		assertTrue(mg.hasGamePiece("Thor"));
	}
	
	@Test
	void checkRemoveAllGamePieces() {
		mg.addGamePiece(0, "Hulk", 2);
		mg.addGamePiece(0, "Captain America", 3);
		mg.addGamePiece(1, "Thor", 4);
		mg.addGamePiece(1, "Black Widow", 1);
		mg.addGamePiece(2, "Wasp", 1);
		mg.removeAllGamePieces(0);
		assertTrue(mg.size() == 3);
		mg.removeAllGamePieces(1);
		assertTrue(mg.size() == 1);
		assertThrows(IllegalArgumentException.class, () -> {
			mg.removeAllGamePieces(10);
	    });
	}
	
	@Test
	void checkIncreaseStrength() {
		mg.addGamePiece(0, "Hulk", 2);
		mg.addGamePiece(0, "Captain America", 3);
		mg.addGamePiece(1, "Thor", 4);
		mg.increaseStrength(1, 7);
		assertThrows(IllegalArgumentException.class, () -> {
			mg.increaseStrength(-1,1);
	    });
		assertTrue(((((GamePiece) (mg.getIndex()[1].next)).getStrength())) == 11);
	}
	
	@Test
	void checkRemovePlayer() {
		mg.addGamePiece(0, "Hulk", 2);
		mg.addGamePiece(0, "Captain America", 3);
		mg.addGamePiece(1, "Thor", 4);
		mg.addGamePiece(1, "Black Widow", 1);
		mg.addGamePiece(2, "Wasp", 1);
		mg.removePlayer(1);
		assertTrue(mg.getIndex()[1] == null);
		assertThrows(IllegalArgumentException.class, () -> {
			mg.removePlayer(1);
	    });
	}
	
	@Test
	void checkSwapPieces() {
		mg.addGamePiece(0, "Hulk", 2);
		mg.addGamePiece(1, "Black Widow", 1);
		mg.addGamePiece(2, "Wasp", 1);
		mg.swapPieces(0, 1);
		assertTrue(mg.getIndex()[1].next.getName().equals("Hulk"));
		assertThrows(IllegalArgumentException.class, () -> {
			mg.swapPieces(0, -1);
	    });
		assertThrows(IllegalArgumentException.class, () -> {
			mg.swapPieces(10, 1);
	    });
		
	}
	
	@Test
	void checkToString() {
		mg.addGamePiece(0, "Hulk", 2);
		mg.addGamePiece(1, "Black Widow", 1);
		assertTrue(mg.toString().equals("[Player0; GamePiece: Hulk strength: 2; Player1; GamePiece: Black Widow strength: 1; Player2]"));
	}
	
	@Test
	void checkInitializeTurnTracker() {
		mg.initializeTurnTracker();
		assertTrue(mg.getTurnTracker() == mg.getIndex()[0]);
		mg.removePlayer(0);
		mg.initializeTurnTracker();
		assertTrue(mg.getTurnTracker() == mg.getIndex()[1]);
	}
	
	@Test
	void checkNextPlayer() {
		mg.initializeTurnTracker();
		mg.nextPlayer();
		assertTrue(mg.getTurnTracker() == mg.getIndex()[1]);
		mg.removePlayer(2);
		mg.removePlayer(0);
		mg.nextPlayer();
		assertTrue(mg.getTurnTracker() == mg.getIndex()[1]);
	}
	
	@Test
	void checkNextEntity() {
		mg.addGamePiece(0, "Hulk", 2);
		mg.addGamePiece(1, "Black Widow", 1);
		mg.addGamePiece(2, "Wasp", 1);
		mg.initializeTurnTracker();
		mg.nextEntity();
		mg.nextEntity();
		assertTrue(mg.getTurnTracker() == mg.getIndex()[1]);
		mg.nextEntity();
		assertFalse(mg.getTurnTracker() == mg.getIndex()[2]);
	}
	
	@Test
	void checkPrevPlayer() {
		mg.addGamePiece(0, "Hulk", 2);
		mg.addGamePiece(1, "Black Widow", 1);
		mg.addGamePiece(2, "Wasp", 1);
		mg.initializeTurnTracker();
		mg.prevPlayer();
		assertTrue(mg.getTurnTracker() == mg.getIndex()[2]);
		mg.prevPlayer();
		assertTrue(mg.getTurnTracker() == mg.getIndex()[1]);
	}
	
	@Test
	void checkCurrentEntityToString() {
		mg.addGamePiece(0, "Hulk", 2);
		mg.addGamePiece(1, "Black Widow", 1);
		mg.addGamePiece(2, "Wasp", 1);
		mg.initializeTurnTracker();
		assertTrue(mg.currentEntityToString().equals(mg.getIndex()[0].toString()));
		mg.nextEntity();
		assertTrue(mg.currentEntityToString().equals(mg.getIndex()[0].next.toString()));
	}

}
