package useritem;

import com.google.code.morphia.Datastore;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceOutput;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Iterator;

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
  
  public List<ItemCount> getTopTenItems() {
    long startTime = System.currentTimeMillis();
    Map<Integer, Integer> itemCounts = new HashMap<Integer, Integer>();
    for (User user : ds.find(User.class)) {
      if (user.getItems() != null) {
        for (Item item : user.getItems()) {
          Integer itemCount = itemCounts.get(item.getId());
          if (itemCount == null) {
            itemCount = 0;
          }
          itemCount++;
          itemCounts.put(item.getId(), itemCount);
        }
      }
    }
    System.out.println("Map time: " + (System.currentTimeMillis() - startTime));
    SortedSet<ItemCount> itemCountSet = new TreeSet(new Comparator<ItemCount>() {
      public int compare(ItemCount itemCount1, ItemCount itemCount2) {
        return new Integer(itemCount2.getItemCount()).compareTo(itemCount1.getItemCount());
      }
    });
    for (Integer itemId : itemCounts.keySet()) {
      itemCountSet.add(new ItemCount(itemId, itemCounts.get(itemId)));
    }
    List<ItemCount> topTenItemCounts = new ArrayList<ItemCount>();
    Iterator<ItemCount> itemCountIter = itemCountSet.iterator();
    for (int i = 0; i < 10 && itemCountIter.hasNext(); i++) {
      topTenItemCounts.add(itemCountIter.next());
    }
    System.out.println("Total time: " + (System.currentTimeMillis() - startTime));
    return topTenItemCounts;
  }

  public List<ItemCount> getTopTenItems2() {
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
    
    
    long startTime = System.currentTimeMillis();
    MapReduceOutput out = col.mapReduce(mapFunction, reduceFunction, null, null);
    System.out.println("Map reduce time: " + (System.currentTimeMillis() - startTime));
    
    DBCollection outCol = out.getOutputCollection();
    
    DBCursor cur = outCol.find().sort(new BasicDBObject("value", -1)).limit(10);
    
    List<ItemCount> itemCounts = new ArrayList<ItemCount>();
    
    while(cur.hasNext()) {
      DBObject r = cur.next();
      int id = (Integer)((DBObject)r.get("_id")).get("id");
      int count = ((Number)((DBObject)r.get("value")).get("count")).intValue();
      itemCounts.add(new ItemCount(id, count));
    }
    
    return itemCounts;
  }

}