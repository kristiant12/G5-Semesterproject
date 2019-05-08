package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.services.IHighScore;

/**
 *
 * @author tfvg-pc11
 */
public class SortingAlgorithm {
    
    public void sort(IHighScore[] arr)
    {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i].getWave();
            int j = i - 1;
 
            
            while (j >= 0 && arr[j].getWave() > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1].setWave(key);
        }
    }
    
    
    
}
