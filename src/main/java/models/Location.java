package models;

import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Location {
    private int id;
    private final String name;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


    public static List<Location> all(){
        try (Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM locations";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Location.class);
        }

    }

    public void save(){
        try (Connection con = DB.sql2o.open()){
            String sql = "INSERT INTO locations (name) VALUES (:name)";
            if(name.equals("")){
                throw new IllegalArgumentException("Empty Fields Not Allowed");
            }
            this.id = (int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .executeUpdate()
                    .getKey();
        }
    }
    public void deleteOneLocation(){
        try (Connection con = DB.sql2o.open()){
            String sql = "DELETE FROM locations WHERE id=:id";
            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeUpdate();
        }
    }

    public static Location find(int id){
        try (Connection con = DB.sql2o.open()){
            String sql="SELECT * FROM locations WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Location.class);
        }

    }

    public List<Sightings> getLocationSightings(){
        try (Connection con = DB.sql2o.open()){
            String sql="SELECT sighting_id FROM locations_sightings WHERE location_id=:location_id";
            List<Integer> sightings_ids = con.createQuery(sql)
                    .addParameter("location_id",this.getId())
                    .executeAndFetch(Integer.class);
            List<Sightings> sightings = new ArrayList<>();

            for(Integer sighting_id:sightings_ids){
                String sightingsQuery = "SELECT * FROM sightings WHERE id=:sighting_id";
                Sightings sighting = con.createQuery(sightingsQuery)
                        .addParameter("sighting_id",sighting_id)
                        .executeAndFetchFirst(Sightings.class);
                sightings.add(sighting);

            }
            if(sightings.size() == 0){
                throw new IllegalArgumentException("Location has no Records");
            }
            else {return sightings;}


        }

    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Location locations = (Location) object;
        return id == locations.id &&
                name.equals(locations.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
