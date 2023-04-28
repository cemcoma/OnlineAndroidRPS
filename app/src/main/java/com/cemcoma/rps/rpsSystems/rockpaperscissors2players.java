package com.cemcoma.rps.rpsSystems;
// deneme

/**
 * A single Rock Paper Scissors game.
 * 0 for rock, 1 for scissors, 2 for paper
 * @author Cemcoma & Sodaa
 */
public class rockpaperscissors2players {
    private int player1Choice, player2Choice;



    public rockpaperscissors2players(int player1Choice, int player2Choice) {
        this.player1Choice = player1Choice;
    }
    /** 
     * Generates the computer choice (0 -> tas; 1 -> kağıt; 2 -> makas)
    */


    public int getPlayer2ChoiceChoice() {
        return player2Choice;
    }

    /**
     * @return player kazanıyorsa 1 ,kaybediyorsa -1, beraberse 0
     */
    public int didPlayerWin() {
        if(player1Choice == 0 && player2Choice == 2){ // taş vs makas
            return 1;
        }
        else if(player1Choice == 0 && player2Choice == 1){ // taş vs kağıt
            return -1;
        }
        else if(player1Choice == player2Choice){ //taş vs taş
            return 0;
        }
        else if(player1Choice == 2 && player2Choice == 0){ // makas vs taş
            return -1;
        }
        else if(player1Choice == 2 && player2Choice == 1){
            return 1;
        }
        else if(player1Choice == player2Choice){
            return 0;
        }
        else if(player1Choice == 1 && player2Choice == 0){
            return 1;
        }
        else if(player1Choice == 1 && player2Choice == 2){
            return -1;
        }
        else{
            return 0;
        }
        
    }
    
}