package models;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SightingsTest {

    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    private Sightings createNewSighting() {
        return new Sightings(2,11,3);
    }

    @Test
    public void sightingInstantiatesCorrectly(){
        Sightings testSighting = createNewSighting();
        assertTrue(testSighting instanceof Sightings);
    }

    @Test
    public void deleteOneEntryWorksCorrectly(){
        Sightings testSighting = createNewSighting();
        testSighting.save();
        testSighting.deleteOneSighting();
        assertNull(Animals.find(testSighting.getId()));
    }
    @Test
    public void deleteAllSightingsWorksAsExpected_true(){
        Sightings sighting1 = createNewSighting();
        Sightings sighting2 =createNewSighting();
        sighting1.save();
        sighting2.save();
        Sightings.deleteAllSightings();
        List<Sightings> sightings = Sightings.all();
        assertEquals(0,sightings.size());
    }
    @Test
    public void getIdAndSetIdMethodsWorksCorrectly_true(){
        Sightings sighting = createNewSighting();
        sighting.setId(34);
        assertEquals(34, sighting.getId());
    }
    @Test
    public void getAnimalIdReturnCorrectResult_true(){
        Sightings sighting = createNewSighting();
        sighting.save();
        assertEquals(3, sighting.getAnimal_id());
    }

}