package AmazingLabyrinth.Application;


import AmazingLabyrinth.View.StartingFrame;

/*
 - Names: Fatimah Rana (20%), Hussain Murtaza(15%), Hanyu Zhou (30%), Zheng Pei(35%)
    - Group Project Contribution Breakdown (files and methods written for individual tasks are not included)
    
        @ Fatimah Rana:
            Important features completed:
                - moving algorithm (check if there's a path connecting two points)
                - read in Cards.csv and Treasure.csv file
                - free tile rotation 20 %
                
            Credit for File:
                (View)GameRuleFrame 100%
                (Model)Player 50%
                (Model)Utility 40%
        
        @ Hussain Murtaza:
            Important features completed:
                - pick frame
            
            Credit for File:
                (View) PickFrame 80%
            
        @ Hanyu Zhou:
            Important features completed:
                - check and print out responses in the message panel based on the game state
                - the algorithm behind a human player's turn
                - algorithm for switching turns between different players 50%
                - scoring algorithm, the player receives mark when moved to a tile with his treasure, also update the
                   cards 50%
                - the starting and ending animation
                
            Credit for File:
                (Controller) Controller 20%
                (View) Button Border 100%
                (View) FreeTilePanel 40%
                (View) GameFrame 50%
                (View) StartingFrame 100%
                (View) ScoringPanel 100%
                (View) PlayerPanel 60%
                (View) PickFrame 20%
                (View) MessagePanel: 100%
                (View) GameStatePanel: 100%
            
        @ Zheng Pei:
            Important features completed:
                - basic board loads up and treasure tiles show up on the board (random placement)
                - cards are shuffled and dealt to each player (1 to 6 cards each)
                - free tile rotation 80%
                - free tile insert
                - detect where the user has selected on a board
                - the movement of a player (update GUI whenever a player moved to a new tile)
                - algorithm for switching turns between different players 50%
                - scoring algorithm, the player receives mark when moved to a tile with his treasure, also update the
                   cards 50%
                - function that finds the orientation of the tile
                
            Credit for File:
                (Controller) Controller 80%
                (Model) Board 100%
                (Model) Card 100%
                (Model) Player 50%
                (Model) Tile 60%
                (Model) Utility 60%
                (View) FreeTile Panel 60%
                (View) GameFrame 50%
                (View) PlayerPanel 40%
        
    - Individual Task Breakdown
        - Fatimah Rana
            Task: Highlight potential pathway
            Statue: Incomplete
            
        - Hussain Murtaza
            Task: Moving animation
            Statue: Incomplete
            
        - Hanyu Zhou
            Task: Save and Reload
            Statue: Completed
        
        - Zheng Pei
            Task: AI
            Statue: Completed
            
         

 - Date: Nov 23, 2022
 
 - Course Code: ICS4U1-01
 
 - Course Instructor: Nicholas Fernandes (Mr.Fernandes)
 
 - Title: Amazing Labyrinth game
 
 - Description: A multiplayer board game where you need to travel through maze and collect all the treasures to win the
    game
 
 - Features:
    AI
    Pathway highlight
    Save & reload
    Attractive, User Friendly GUI
    
 - Major Skills:
    Java Swing, OOP programming, maze traversal
 
 - Areas of Concern:
    - Note to end user: N/A
    - Incomplete or partial solution:
        Highlight pathway
        Animation
 
 */

public class Application {
    public static void main(String[] args) {
        new StartingFrame();
    }
}
