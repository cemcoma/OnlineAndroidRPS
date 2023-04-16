package com.cemcoma.rps.rpsSystems;


/**
 * A single Rock Paper Scissors game.
 * 0 for rock, 1 for scissors, 2 for paper
 * @author Cemcoma & Sodaa
 */
public class rockpaperscissors {
    private int playerChoice, computerChoice;
    


    public rockpaperscissors(int playerChoice) {
        generateComputerChoice();    
        this.playerChoice = playerChoice; 
    }
    /** 
     * Generates the computer choice (0 -> tas; 1 -> kağıt; 2 -> makas)
    */
    private void generateComputerChoice() {
        computerChoice = (int)Math.floor(Math.random() * 3);
        System.out.println(computerChoice);
    }

    public int getComputerChoice() {
        return computerChoice;
    }

    /**
     * @return player kazanıyorsa 1 ,kaybediyorsa -1, beraberse 0
     */
    public int didPlayerWin() {
        if(playerChoice == 0 && computerChoice == 2){ // taş vs makas
            return 1;
        }
        else if(playerChoice == 0 && computerChoice == 1){ // taş vs kağıt
            return -1;
        }
        else if(playerChoice == computerChoice){ //taş vs taş
            return 0;
        }
        else if(playerChoice == 2 && computerChoice == 0){ // makas vs taş
            return -1;
        }
        else if(playerChoice == 2 && computerChoice == 1){
            return 1;
        }
        else if(playerChoice == computerChoice){
            return 0;
        }
        else if(playerChoice == 1 && computerChoice == 0){
            return 1;
        }
        else if(playerChoice == 1 && computerChoice == 2){
            return -1;
        }
        else{
            return 0;
        }
        
    }
    
}