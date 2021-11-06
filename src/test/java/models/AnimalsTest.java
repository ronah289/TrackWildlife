package models;

import org.junit.Rule;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class AnimalsTest {

    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    private Animals createTestAnimal(){
        AtomicReference<Animals> testAnimal1 = new AtomicReference<>(new Animals("elephant", "endangered"));
        return testAnimal1.get();
    }

    @Test
    public void animalInstantiatedCorrectly_true(){
        Animals testAnimalInstance = createTestAnimal();
        assertNotNull(testAnimalInstance);
    }
    @Test
    public void animalSavedCorrectly_true(){
        Animals testAnimal1 = createTestAnimal();
        testAnimal1.save();
        assertEquals(Animals.all().get(0), testAnimal1);
    }
    @SuppressWarnings("ThrowablePrintedToSystemOut")
    @Test
    public void nameFieldNeverEmpty_true(){
        Animals testAnimal=new Animals("","normal");
        try {
            testAnimal.save();
        }
        catch (IllegalArgumentException error){
            System.out.println(error);
        }
        }
    @SuppressWarnings("ThrowablePrintedToSystemOut")
    @Test
    public void animalIsUpdatedCorrectly() {
        Animals testAnimal1 = createTestAnimal();
        AtomicReference<Animals> testAnimal2 = new AtomicReference<>(testAnimal1);
        testAnimal1.save();
        try {
            testAnimal1.update(testAnimal1.getId(),"normal","health","adult");
            Animals updatedAnimal =  Animals.find(testAnimal1.getId());
            assertEquals(updatedAnimal.getId(), testAnimal2.get().getId());
            assertNotEquals(updatedAnimal.getHealth(), testAnimal2.get().getHealth());
        }
        catch (IllegalArgumentException error){
            System.out.println(error);
        }
    }
    @Test
    public void findByIdResultsCorrect(){
        Animals testAnimal = createTestAnimal();
        testAnimal.save();
        Animals retrievedAnimal= Animals.find(testAnimal.getId());
        assertEquals(retrievedAnimal, testAnimal);
    }
    @Test
    public void deleteOneEntryWorksAsExpected(){
        Animals testAnimal = createTestAnimal();
        testAnimal.save();
        testAnimal.deleteOneEntry();
        assertNull(Animals.find(testAnimal.getId()));
    }
    @Test
    public void deleteAllRecordsWorksCorrectly_true(){
        Animals testAnimal1 = createTestAnimal();
        Animals testAnimal2 = createTestAnimal();
        testAnimal1.save();
        testAnimal2.save();
        Animals.deleteAllRecords();
        List<Animals> animals=Animals.all();
        assertEquals(0,animals.size());
    }
    @Test
    public void getNameAndSetNameMethodsWorksCorrectly_true(){
        Animals testAnimal = createTestAnimal();
        testAnimal.setName("elephant1");
        assertEquals("elephant1", testAnimal.getName());
    }
    @Test
    public void getIdAndSetIdMethodsWorkCorrectly_true(){
        Animals testAnimal = createTestAnimal();
        testAnimal.setId(25);
        assertEquals(25, (testAnimal.getId()));
    }
}