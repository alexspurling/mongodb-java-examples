package useritem;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class Item {

  private int id;
  
  public Item() {
  
  }
  
  public Item(int id) {
    this.id = id;
  }
  
  public String toString() {
     return "Item id: " + id;
  }

}