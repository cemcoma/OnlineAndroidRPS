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
            int eloDiff = elo1-elo2;
            double p1WinProbability = 1/(1+ Math.pow(10,  -(eloDiff/400.0)));
            double p2WinProbability = 1/(1+ Math.pow(10, (eloDiff/400.0)));
            if(didP1Win == 1) {
                elo1 = (int) (elo1 + 30* (1 - p1WinProbability));
                elo2 = (int) (elo2 + 30* (0 - p2WinProbability));
            } else if(didP1Win == 0) {
                elo1 = (int) (elo1 + 30* (0.5 - p1WinProbability));
                elo2 = (int) (elo2 + 30* (0.5 - p2WinProbability));
            }else {
                elo1 = (int) (elo1 + 30* (0 - p1WinProbability));
                elo2 = (int) (elo2 + 30* (1 - p2WinProbability));
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
