import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int matches = 0;
        String play = "c";
        while (play.equals("c") || play.equals("C")) {
            // Initialize
            Game grid = new Game();
            Player player1 = new Player();
            Player player2 = new Player();
            boolean end = false;
            int turns = 0;

            matches++;
            System.out.println();
            System.out.println("Match " + matches);

            // Get player1 name
            System.out.print("Enter player 1's name: ");
            String input = getInput(in);
            while (input.length() < 1 || Character.isWhitespace(input.charAt(0))) {
                System.out.print("Please enter a valid name: ");
                input = getInput(in);
            }
            player1.setName(input);

            // Get player2 name
            System.out.print("Enter player 2's name: ");
            input = getInput(in);
            while (input.length() < 1 || Character.isWhitespace(input.charAt(0))) {
                System.out.print("Please enter a valid name: ");
                input = getInput(in);
            }
            player2.setName(input);

            // Display grid
            printGrid(grid);
        
            // While the game isn't over
            while (!end) {
                // Get random column blocked
                Random rand = new Random();
                int upperboundHorizontal = 7;
                int c = rand.nextInt(upperboundHorizontal);
                System.out.println("The blocked column is: " + c); 

                // Get column for player 1
                System.out.print("It's " + player1.getName() + "'s turn, please choose a column: ");
                String player1Column = getInput(in);

                // Check if entry is valid and set player 1's checkers
                while (!isEntryValid(grid, player1Column, c)) {
                    System.out.print(player1.getName() + "'s entry is invalid, please choose a column between 0 and 6: ");
                    player1Column = getInput(in);
                }

                addChecker(grid, player1, Integer.parseInt(player1Column), true);

                // Display grid
                printGrid(grid);

                // Check if player 1 won
                if (win(grid, player1, player2, turns)) {
                    end = true;
                    System.out.println("Game Over!");
                    System.out.println("Press c to continue playing or any other letter key to quit: ");
                    play = getInput(in);
                    break;
                }

                // Increment turns
                turns++;

                c = rand.nextInt(upperboundHorizontal);
                System.out.println("The blocked column is: " + c); 

                // Get column for player 2
                System.out.print("It's " + player2.getName() + "'s turn, please choose a column: ");
                String player2Column = getInput(in);

                // Check if entry is valid and set player 2's checkers
                while (!isEntryValid(grid, String.valueOf(player2Column), c)) {
                    System.out.print(player2.getName() + "'s entry is invalid, please choose a column between 0 and 6: ");
                    player2Column = getInput(in);
                }

                addChecker(grid, player2, Integer.parseInt(player2Column), false);

                // Display grid
                printGrid(grid);

                // Check if player 2 won
                if (win(grid, player1, player2, turns)) {
                    end = true;
                    System.out.println();
                    System.out.print("Press 'c' to continue playing or any other key to quit: ");
                    
                    play = in.nextLine();
                }

                // Increment turns
                turns++;
            }

            if (play.length() == 0 || Character.isWhitespace(play.charAt(0))) {
                break;
            }
        }   
    }

    public static void printGrid(Game grid) {
        System.out.println();
        System.out.println(" CONNECT FOUR! ");
        System.out.println(" 0 1 2 3 4 5 6");
		System.out.println("---------------");
        for (int i = 0; i < grid.getHeight(); i++) {
            System.out.print("|");
            for (int j = 0; j < grid.getWidth(); j++) {
                System.out.print(grid.getGrid(i, j));
                System.out.print("|");
            }
            System.out.println();
            System.out.println("---------------");
        }
        System.out.println(" 0 1 2 3 4 5 6");
        System.out.println();
    }

    public static String getInput(Scanner in) {
        return in.nextLine();
    }

    public static boolean isEntryValid(Game grid, String entry, int blocked) {
        try {
            // Check if null
            if (entry.length() < 1) {
                return false;
            }

            // Check if whitespace
            if (Character.isWhitespace(entry.charAt(0))) {
                System.out.println("No whitespaces!");
                return false;
            }

            // Check if out of bounds
            if (Integer.parseInt(entry) < 0 || grid.getHeight() < Integer.parseInt(entry)) {
                System.out.println("Index out of bounds!");
                return false;
            }

            // Check if blocked
            if (Integer.parseInt(entry) == blocked) {
                System.out.println("Entry blocked!");
                return false;
            }

            // Check if top is filled
            if (grid.getGrid(0, Integer.parseInt(entry)) == 'O' || grid.getGrid(0, Integer.parseInt(entry)) == 'X') {
                System.out.println("Column is full!");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Numbers only!");
            return false;
        }
        return true;
    }

    public static void addChecker(Game grid, Player player, int playerColumn, boolean rb) {
        for (int i = grid.getHeight() - 1; i >= 0; i--) {
            if (grid.getGrid(i, playerColumn) != 'O' && grid.getGrid(i, playerColumn) != 'X') {
                player.setCheckers(i, playerColumn);
                if (rb) {
                    grid.setGrid(i, playerColumn, 'O');
                } else {
                    grid.setGrid(i, playerColumn, 'X');
                }
                break;
            }
        }
    }

    public static boolean win(Game grid, Player p1, Player p2, int rounds) {
        // Connect Four logic
        // Vertical combo
        for (int i = 0; i < grid.getHeight() - 3; i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                if (p1.getCheckers(i, j) && p1.getCheckers(i + 1, j) 
                    && p1.getCheckers(i + 2, j) && p1.getCheckers(i + 3, j)) {
                    System.out.println(p1.getName() + " wins!");
                    return true;
                }

                if (p2.getCheckers(i, j) && p2.getCheckers(i + 1, j) 
                    && p2.getCheckers(i + 2, j) && p2.getCheckers(i + 3, j)) {
                    System.out.println(p2.getName() + " wins!");
                    return true;
                }
            }
        }

        // Horizontal combo
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth() - 3; j++) {
                if (p1.getCheckers(i, j) && p1.getCheckers(i, j + 1) 
                    && p1.getCheckers(i, j + 2) && p1.getCheckers(i, j + 3)) {
                    System.out.println(p1.getName() + " wins!");
                    return true;
                }

                if (p2.getCheckers(i, j) && p2.getCheckers(i, j + 1) 
                    && p2.getCheckers(i, j + 2) && p2.getCheckers(i , j + 3)) {
                    System.out.println(p2.getName() + " wins!");
                    return true;
                }
            }
        }

        // Diagonal upward combo
        for(int col = 3; col < grid.getWidth(); col++){
			for(int row = 0; row < grid.getHeight() - 3; row++){
				if (p1.getCheckers(row, col) && p1.getCheckers(row + 1, col - 1) 
                    && p1.getCheckers(row + 2, col - 2) && p1.getCheckers(row + 3, col - 3)) {
                    System.out.println(p1.getName() + " wins!");
					return true;
				}

                if (p2.getCheckers(row, col) && p2.getCheckers(row + 1, col - 1) 
                    && p2.getCheckers(row + 2, col - 2) && p2.getCheckers(row + 3, col - 3)) {
                    System.out.println(p2.getName() + " wins!");
					return true;
				}
			}
		}
        
        // Diagonal downward combo
        for(int col = 0; col < grid.getWidth() - 3; col++){
			for(int row = 0; row < grid.getHeight() - 3; row++){
				if (p1.getCheckers(row, col) && p1.getCheckers(row + 1, col + 1) 
                    && p1.getCheckers(row + 2, col + 2) && p1.getCheckers(row + 3, col + 3)) {
                        System.out.println(p1.getName() + " wins!");
					return true;
				}

                if (p2.getCheckers(row, col) && p2.getCheckers(row + 1, col + 1) 
                    && p2.getCheckers(row + 2, col + 2) && p2.getCheckers(row + 3, col + 3)) {
                    System.out.println(p2.getName() + " wins!");
					return true;
				}
			}
		}

        // Check if grid is full
        if (rounds >= 42) {
            System.out.println("It's a draw!");
            return true;
        }
        return false;
    }
}
