package models;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    private Location createNewLocation() {
        return new Location("location1");
    }

    @Test
    public void locationInstantiatesCorrectly() {
        Location testLocation = createNewLocation();
        assertTrue(testLocation instanceof Location);
    }

    @Test
    public void locationSaved_true() {
        Location location = createNewLocation();
        Location location2 = new Location("location2");
        location.save();
        location2.save();
        assertNotNull(Location.all().size());
    }
    @Test
    public void locationDeleted_true(){
        Location location = createNewLocation();
        location.save();
        location.deleteOneLocation();
        assertNull(Location.find(location.getId()));
    }
    @SuppressWarnings("ThrowablePrintedToSystemOut")
    @Test
    public void sightingsOfARangerRetrieved() {
        Location location = createNewLocation();
        try {
            location.save();
            Sightings sighting = new Sightings(location.getId(),4,7);
            Sightings sighting2 = new Sightings(location.getId(),1,5);
            sighting.save();
            sighting2.save();
            assertEquals(location.getLocationSightings().get(0),sighting);
            assertEquals(location.getLocationSightings().get(1),sighting2);
        }
        catch (IllegalArgumentException error){
            System.out.println(error);
        }

    }
}