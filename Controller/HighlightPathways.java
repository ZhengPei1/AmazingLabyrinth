package AmazingLabyrinth.Controller;

import AmazingLabyrinth.Model.Player;
import AmazingLabyrinth.Model.Board;

/*
// Potential pathways are highlighted and made apparent to players to show possible moves, which can be useful if a player is stuck or unsure.
//FATIMAH RANA
//HIGHLIGHT PATHWAYS
public class HighlightPathways {

	// Fields
	private static int playerTurn;
	private static Player[] players;
	private static boolean[][] visited = new boolean[7][7];

	// the method i tried to implement here uses a way to search the board for possible pathways. it would update this information and return the possible values
	// with these possible values, it checks whether or not the tiles connect, and if the player can actually move.
	public static void findPath() {

		playerTurn = ((Board) Controller.board).getPlayerTurn();
		players = ((Board) Controller.board).getPlayers();

		// set initial  visited value for each tile to be false
		for (int i = 0; i < 7; i++) {

			for (int j = 0; j < 7; j++) {
				visited[i][j] = false;
			}
		}

		// uses pathexists method to see if there is a possible path for the player during thier turn.
		pathExists(players[playerTurn].getRow(), players[playerTurn].getColumn());

	

	}

	private static void pathExists(int playerRow, int playerCol) {
		
		//information of Tile Openings 
		boolean[] openings = ((Board) Controller.board).getMatrix()[playerRow][playerCol].getOpenings();
		
		//Set the current cell visited as true
		visited[playerRow][playerCol] = true;
		
		//Loop through 4 different directions for every cell
		for (int direction = 0; direction < 4; direction++) {

			//depending of the direction of movement, we calculate the change in row and column.
			int dX = 0;
			int dY = 0;

			//finds the change in the coordinates of player
			if (direction == 0) {
				dY = -1;
			} else if (direction == 1) {
				dX = 1;
			} else if (direction == 2) {
				dY = 1;
			} else if (direction == 3) {
				dX = -1;
			}

			//constantly checks if there is a pathway (recursion)
			if (openings[direction] && canPlayerMove(direction, playerRow, playerCol, dX, dY)
					&& !visited[playerRow + dY][playerCol + dX]) {

				if (direction == 0) 
					pathExists(playerRow + dY, playerCol + dX);

				if (direction == 1) 
					pathExists(playerRow + dY, playerCol + dX);

				if (direction == 2) 
					pathExists(playerRow + dY, playerCol + dX);
				

				if (direction == 3) 
					pathExists(playerRow + dY, playerCol + dX);
				
			}
		}

		return;

	}

	//checks for pathways next to the current player tile
	private static boolean canPlayerMove(int direction, int row, int col, int dX, int dY) {

		//returns a flase/invalid value if the player would move out of bounds in an unavailable spot. - fatimah
		if (col + dX < 0 || row + dY < 0 || row + dY > 6 || col + dX > 6)
			return false;

		return ((Board) Controller.board).getMatrix()[row][col].getOpenings()[direction]
				&& ((Board) Controller.board).getMatrix()[row + dY][col + dX].getOpenings()[(direction + 2)
						% 4];
	}

}


 */
 