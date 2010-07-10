package useritem;

import java.util.List;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Embedded;

@Entity
public class User {

  @Id private String id;
  private String name;
  
  @Embedded
  private List<Item> items;
  private int itemCount;
  
  public User() {
  
  }

  public User(String id, String name, List<Item> items) {
    this.id = id;
    this.name = name;
    this.items = items;
    this.itemCount = items.size();
  }
  
  public String getId() {
    return id;
  }
  
  public String toString() {
    return "User id: " + id + ", name: " + name + ", items: " + items;
  }
  
}