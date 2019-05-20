package dk.sdu.mmmi.cbse.core.main;

public class SortingAlgorithm {
    
    private HighScore[] highscore;
    private int HighScoreIndex = 0;

    public SortingAlgorithm() {
        this.highscore = new HighScore[10];
    }
    
    private  void sort()
    {
        for (int i = 1; i < HighScoreIndex; i++) {
            HighScore key = highscore[i];
            int j = i - 1;
 
            while (j >= 0 && highscore[j].getWave() < key.getWave()) {
                highscore[j + 1] = highscore[j];
                j = j - 1;
            }
            highscore[j + 1] = key;
        }
    }
    
      public void addPlayerToNewArray(HighScore HighScore) {
        if (HighScoreIndex <= 10) {
            highscore[HighScoreIndex] = HighScore;
            HighScoreIndex++;
            sort();
        }
        
    }

    public HighScore[] getNewHighScoreArray() {
        return highscore;
    }
    
}
