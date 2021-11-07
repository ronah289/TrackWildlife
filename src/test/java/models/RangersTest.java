package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

class RangersTest {

    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();


    private Rangers createRanger(){
        return new Rangers("brian",34);
    }

    @Test
    public void newRangerInstantiatesCorrectly_true() {
        Rangers ranger1 = createRanger();
        assertTrue(ranger1 instanceof Rangers);
    }
    @Test
    public void rangerSavedCorrectly(){
        Rangers ranger1 = createRanger();
        ranger1.save();
        assertEquals(ranger1.getName(),Rangers.all().get(0).getName());
    }
    @Test
    public void nonEmptyFieldsForARanger(){
        Rangers ranger1 = new Rangers("",0);
        try {
            ranger1.save();
        }
        catch (IllegalArgumentException error){
            System.out.println(error);
        }
    }
    @Test
    public void findResultsCorrect(){
        Rangers ranger = createRanger();
        ranger.save();
        Rangers retrievedRanger = Rangers.find(ranger.getId());
        assertEquals(retrievedRanger, ranger);
    }
}