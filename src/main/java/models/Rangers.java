package models;

import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Rangers {

    private int id;
    private final String name;
    private final Integer staff_number;

    public Rangers(String name, Integer staff_number) {
        this.name = name;
        this.staff_number = staff_number;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static List<Rangers> all(){
        try (Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM rangers";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Rangers.class);

        }

    }

    public void save(){
        try (Connection con = DB.sql2o.open()){
            String sql = "INSERT INTO rangers (name,staff_number) VALUES (:name,:staff_number)";
            if(name.equals("")|| staff_number.equals(0)){
                throw new IllegalArgumentException("All fields must be filled");
            }
            this.id = (int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("staff_number",this.staff_number)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static Rangers find(int id){
        try (Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM rangers WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Rangers.class);
        }
    }

    public List<Sightings> getRangerSightings(){
        try (Connection con = DB.sql2o.open()){
            String sql = "SELECT sighting_id FROM rangers_sightings WHERE ranger_id=:ranger_id";
            List<Integer> sightings_ids;
            sightings_ids = con.createQuery(sql)
                    .addParameter("ranger_id",this.getId())
                    .executeAndFetch(Integer.class);
            List<Sightings> sightings = new ArrayList<>();

            for(Integer sighting_id:sightings_ids){
                String sightingsQuery = "SELECT * FROM sightings WHERE id=:sighting_id";
                Sightings sighting=con.createQuery(sightingsQuery)
                        .addParameter("sighting_id",sighting_id)
                        .executeAndFetchFirst(Sightings.class);
                sightings.add(sighting);

            }
            if(sightings.size() == 0){
                throw new IllegalArgumentException("No Sightings By This Ranger");
            }
            else {return sightings;}


        }

    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Rangers rangers = (Rangers) object;
        return id == rangers.id &&
                name.equals(rangers.name) &&
                staff_number.equals(rangers.staff_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, staff_number);
    }
}
