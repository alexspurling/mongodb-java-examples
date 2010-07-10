package useritem;

import com.google.code.morphia.Datastore;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceOutput;
import java.util.Map;
import java.util.HashMap;

public class MongoUserDAO implements UserDAO {

  private Datastore ds;
  
  public MongoUserDAO(Datastore ds) {
    this.ds = ds;
  }

  public void save(User user) {
    ds.save(user);
  }
  
  public User getById(String id) {
    return ds.get(User.class, id);
  }
  
  public long getUserCount() {
    return ds.getCount(User.class);
  }
  
  public Map<Integer, Integer> getItemCounts() {
    DBCollection col = ds.getCollection(User.class);
    String mapFunction = 
      "function () {" +
        "if (this.items != null) {" +
          "this.items.forEach(function (z) {emit(z, {count:1});});" +
        "}" +
      "}";
    String reduceFunction = 
      "function (key, values) {" + 
        "var total = 0;" +
        "for (var i = 0; i < values.length; i++) {" +
          "total += values[i].count;" +
        "}" +
        "return {count:total};" +
      "}";
    MapReduceOutput out = col.mapReduce(mapFunction, reduceFunction, null, null);
    
    Map<Integer, Integer> itemCounts = new HashMap<Integer, Integer>();
    
    for (DBObject r : out.results()){
      int id = (Integer)((DBObject)r.get("_id")).get("id");
      int count = ((Number)((DBObject)r.get("value")).get("count")).intValue();
      itemCounts.put(id, count);
    }
    
    return itemCounts;
  }

}