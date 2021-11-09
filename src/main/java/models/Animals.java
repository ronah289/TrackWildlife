package models;


import java.util.List;
import java.util.Objects;

import org.sql2o.Connection;
import org.sql2o.Sql2oException;

public class Animals implements DatabaseManagement {
    public int id;
    public String name;
    public String type;
    private final String health;
    private final String age;
    public static final String ANIMAL_TYPE = "normal";

    public Animals(String name,String type) {
        this.name = name;
        this.type = ANIMAL_TYPE;
        this.health = "";
        this.age = "";
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getHealth() {
        return health;
    }

    public String getAge() {
        return age;
    }

    @SuppressWarnings("ThrowablePrintedToSystemOut")
    public void save(){
        if(this.name.equals("")||this.type.equals("")||this.name.equals(null)||this.type.equals(null)){
            throw new IllegalArgumentException("Fill All Entries");
        }
        try (Connection con = DB.sql2o.open()){
            String sql = "INSERT INTO animals (name,type) VALUES (:name,:type)";
            this.id = (int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("type",this.type)
                    .executeUpdate()
                    .getKey();
        }
        catch (IllegalArgumentException error) {
            System.out.println(error);
        }
    }


    public void update(int id,String type,String health,String age) {
        try (Connection con = DB.sql2o.open()) {
            con.commit(false);
            if (type.equals("")) {
                throw new IllegalArgumentException("Fill All Entries");
            }
            if (type.equals("endangered")) {
                if (health.equals("") || age.equals("")) {
                    throw new IllegalArgumentException("Fill All Entries");
                }
                String sql = "UPDATE animals SET type=:type,health=:health,age=:age WHERE id=:id";
                con.createQuery(sql)
                        .addParameter("type", type)
                        .addParameter("health", health)
                        .addParameter("age", age)
                        .addParameter("id", this.id)
                        .executeUpdate();
            } else {

                String sql = "UPDATE animals SET type=:type,health=:health,age=:age WHERE id=:id";
                con.createQuery(sql)
                        .addParameter("type", type)
                        .addParameter("health", "")
                        .addParameter("age", "")
                        .addParameter("id", this.id)
                        .executeUpdate();
            }
            con.commit(true);

        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    public static Animals find(int id){
        try (Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM animals WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Animals.class);

        }

    }

    public void deleteOneEntry(){
        try (Connection con = DB.sql2o.open()){
            String sql = "DELETE FROM animals WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeUpdate();

        }
    }

    public static void deleteAllRecords(){
        try (Connection con = DB.sql2o.open()){
            String sql = "DELETE FROM animals";
            con.createQuery(sql)
                    .executeUpdate();
        }  catch (Sql2oException ex){
            System.out.println(ex);
        }

    }
    public static List<Animals> all(){
        try (Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Animals.class);

        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Animals animals = (Animals) object;
        return name.equals(animals.name) &&
                type.equals(animals.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
