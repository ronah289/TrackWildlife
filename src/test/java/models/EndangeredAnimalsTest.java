package models;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EndangeredAnimalsTest {

    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    @AfterEach
    public void deleteTestRunRecords(){
        Animals.deleteAllRecords();
    }

    private EndangeredAnimals createEndangeredAnimal() {
        return new EndangeredAnimals("Elephant2", "endangered", "ok", "newborn");
    }

    @Test
    public void endangeredAnimalInstantiatedCorrectly_true(){
        Animals endangeredAnimal = createEndangeredAnimal();
        assertTrue(endangeredAnimal instanceof EndangeredAnimals);
    }

    @Test
    public void deleteOneEntryWorksCorrectly(){
        EndangeredAnimals testAnimal = createEndangeredAnimal();
        testAnimal.save();
        testAnimal.deleteOneEntry();
        assertNull(Animals.find(testAnimal.getId()));

    }

    @SuppressWarnings("ThrowablePrintedToSystemOut")
    @Test
    public void ensureNameFieldCannotBeEmpty(){
        EndangeredAnimals endangeredAnimal = new EndangeredAnimals("","","","");
        try {
            endangeredAnimal.save();
        }
        catch (IllegalArgumentException error){
            System.out.println(error);
        }
    }

    @Test
    public void findReturnsExpectedResults(){
        EndangeredAnimals endangeredAnimal = createEndangeredAnimal();
        endangeredAnimal.save();
        Animals foundAnimal = Animals.find(endangeredAnimal.getId());
        assertEquals(foundAnimal.getHealth(), endangeredAnimal.getHealth());
    }
    @SuppressWarnings("ThrowablePrintedToSystemOut")
    @Test
    public void nonEmptyFieldsForNewEndangeredAnimal_true(){
        Animals testAnimal = new EndangeredAnimals("","","","");
        try {
            testAnimal.save();
        }
        catch (IllegalArgumentException error){
            System.out.println(error);
        }
    }

    @Test
    public void deleteAllRecordsWorkCorrectly(){
        EndangeredAnimals endangeredAnimal1 = createEndangeredAnimal();
        EndangeredAnimals endangeredAnimal2 =createEndangeredAnimal();
        endangeredAnimal1.save();
        endangeredAnimal2.save();
        Animals.deleteAllRecords();
        List<Animals> animals = Animals.all();
        assertEquals(0,animals.size());
    }

    @Test
    public void deleteOneEntryWorksAsExpected(){
        Animals testAnimal = createEndangeredAnimal();
        testAnimal.save();
        testAnimal.deleteOneEntry();
        assertNull(Animals.find(testAnimal.getId()));
    }

    @Test
    public void getNameAndSetNameMethodsWorksCorrectly_true(){
        Animals testAnimal = createEndangeredAnimal();
        testAnimal.setName("elephant1");
        assertEquals("elephant1", testAnimal.getName());
    }
    @Test
    public void getIdAndSetIdMethodsWorkCorrectly_true(){
        Animals testAnimal = createEndangeredAnimal();
        testAnimal.setId(18);
        assertEquals(18, (testAnimal.getId()));
    }

}