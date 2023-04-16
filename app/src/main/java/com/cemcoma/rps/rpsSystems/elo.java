package com.cemcoma.rps.rpsSystems;

public class elo {
    private int elo1, elo2;
    private int didP1Win;

    public elo(int elo1, int elo2, int didP1Win) {
        this.elo1 = elo1;
        this.elo2 = elo2;
        this.didP1Win = didP1Win;
        finalizeElos();
    }

    private void finalizeElos() {
        //TODO: finalze elo1 & elo2
        if(elo1 > 0 && elo2 > 0){
            if(elo1 > elo2 && didP1Win == 1){
                int eloFark = (elo1 - elo2)/10;
                elo1 += eloFark;
                elo2-= eloFark;
            }
            else if(elo1 > elo2 && didP1Win == 0){
                int eloFark = (elo1 - elo2)/30;
                elo1 += eloFark;
                elo2 -= eloFark;
            }
            else if(elo1 > elo2 && didP1Win == -1){
                int eloFark = (elo1 - elo2)/150;
                elo1 -= eloFark;
                elo2 += eloFark;

            }
            else if(elo2 > elo1 && didP1Win == 1){
                int eloFark = (elo2 - elo1)/10;
                elo2 += eloFark;
                elo1 -= eloFark;
            }
            else if(elo2 > elo1 && didP1Win == 0){
                int eloFark = (elo2 - elo1)/30;
                elo2 += eloFark;
                elo1 -= eloFark;
            }
            else if(elo2 > elo1 && didP1Win == -1){
                int eloFark = (elo1 - elo2)/150;
                elo2 += eloFark;
                elo1 -= eloFark;
            }
            
            
        }
        else if(elo1 <= 0 || elo2 <= 0){
            System.out.println("elolar 0 ın altında");
            // geçici
        }
    }

    public int getFinalEloP1() {
        return elo1;
    }

    public int getFinalEloP2() {
        return elo2;
    }

}
