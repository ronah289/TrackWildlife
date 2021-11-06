package models;

import org.junit.rules.ExternalResource;
import org.sql2o.Sql2o;

public class DatabaseRule extends ExternalResource{
    @Override
    public void before(){
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test","moringa","password");
    }
}
