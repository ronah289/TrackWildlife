package models;

import org.junit.rules.ExternalResource;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DatabaseRule extends ExternalResource{
    @Override
    public void before(){
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test","moringa","password");
    }

    @Override
    public void after(){
        try (Connection con = DB.sql2o.open()){

            String deleteRangersQuery = "DELETE FROM rangers";
            con.createQuery(deleteRangersQuery).executeUpdate();
            String deleteAnimalsQuery="DELETE FROM animals";
            con.createQuery(deleteAnimalsQuery).executeUpdate();
            String deleteSightingsQuery="DELETE FROM sightings";
            con.createQuery(deleteSightingsQuery).executeUpdate();
            String deleteLocationQuery="DELETE FROM locations";
            con.createQuery(deleteLocationQuery).executeUpdate();
        }
    }
}
