/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.core.main;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tfvg-pc11
 */
public class SortingAlgorithmTest {
    
    public SortingAlgorithmTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sort method, of class SortingAlgorithm.
   

    /**
     * Test of addPlayerToNewArray method, of class SortingAlgorithm.
     */
    @Test
    public void testAddPlayerToNewArray() {
        System.out.println("addPlayerToNewArray");
        HighScore HighScore1 = new HighScore(10, "bo");
        HighScore HighScore2 = new HighScore(20, "jens");
        SortingAlgorithm instance = new SortingAlgorithm();
        instance.addPlayerToNewArray(HighScore1);
        HighScore[] test = instance.getNewHighScoreArray();
        HighScore first = test[0];
        
        assertEquals(first, HighScore1);
        
        instance.addPlayerToNewArray(HighScore2);
        
        test = instance.getNewHighScoreArray();
        first = test[0];
        
        assertEquals(first, HighScore2);
        
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getNewHighScoreArray method, of class SortingAlgorithm.
     */
    @Test
    public void testGetNewHighScoreArray() {
        System.out.println("getNewHighScoreArray");
        SortingAlgorithm instance = new SortingAlgorithm();
        HighScore[] expResult = new HighScore[10];
        HighScore[] result = instance.getNewHighScoreArray();
        assertArrayEquals(expResult, result);
    }
    
}
