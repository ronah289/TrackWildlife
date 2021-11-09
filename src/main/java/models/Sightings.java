package models;

import org.sql2o.Connection;

import java.sql.Timestamp;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Sightings {

    private int id;
    private final int ranger_id;
    private final int animal_id;
    private final int location_id;
    private final Timestamp time;


    public Sightings(int location_id, int ranger_id, int animal_id) {
        this.ranger_id = ranger_id;
        this.animal_id = animal_id;
        this.location_id = location_id;
        AtomicReference<Date> date = new AtomicReference<>(new Date());
        this.time = new Timestamp(date.get().getTime());
    }

    public int getId() {
        return id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public int getRanger_id() {
        return ranger_id;
    }

    public int getAnimal_id() {
        return animal_id;
    }

    public static List<Sightings> all(){
        try (Connection con = DB.sql2o.open()){
            String sql = ("SELECT * FROM sightings");
            return con.createQuery(sql)
                    .executeAndFetch(Sightings.class);
        }
    }

    public void deleteOneSighting(){
        try (Connection con = DB.sql2o.open()){
            String sql = "DELETE FROM sightings WHERE id=:id";
            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeUpdate();
        }

    }

    public static Sightings find(int id){
        try (Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM sightings WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Sightings.class);

        }
    }

    public static void deleteAllSightings(){
        try (Connection con = DB.sql2o.open()){
            String sql = "DELETE FROM sightings";
            con.createQuery(sql)
                    .executeUpdate();
        }

    }

    public void save(){

        if(this.animal_id <= 0 || this.location_id <= 0 || this.ranger_id <= 0 ){
            throw new IllegalArgumentException("Fill blank fields.");
        }
        try (Connection con = DB.sql2o.open()){
            String sql = "INSERT INTO sightings (animal_id,ranger_id,location_id,time) VALUES (:animal_id,:ranger_id," +
                    ":location_id,:time)";
            String rangerSighting = "INSERT INTO rangers_sightings (ranger_id,sighting_id) VALUES (:ranger_id,:sighting_id)";
            String LocationSightings = "INSERT INTO locations_sightings (location_id,sighting_id) VALUES (:location_id," +
                    ":sighting_id)";

            this.id=(int) con.createQuery(sql,true)
                    .addParameter("animal_id",this.animal_id)
                    .addParameter("ranger_id",this.ranger_id)
                    .addParameter("location_id",this.location_id)
                    .addParameter("time",this.time)
                    .executeUpdate()
                    .getKey();

            con.createQuery(rangerSighting).addParameter("ranger_id",this.getRanger_id())
                    .addParameter("sighting_id", this.getId()).executeUpdate();
            con.createQuery(LocationSightings).addParameter("location_id",this.getLocation_id())
                    .addParameter("sighting_id",this.id).executeUpdate();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Sightings sightings = (Sightings) object;
        return id == sightings.id &&
                location_id == sightings.location_id &&
                ranger_id == sightings.ranger_id &&
                animal_id == sightings.animal_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location_id, ranger_id, animal_id);
    }

    public void setId(int id) {
        this.id = id;
    }
}
