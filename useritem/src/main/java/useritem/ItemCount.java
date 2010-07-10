package useritem;

public class ItemCount {

  private int itemId;
  private int itemCount;

  public ItemCount(int itemId, int itemCount) {
    this.itemId = itemId;
    this.itemCount = itemCount;
  }
  
  public String toString() {
    return "Item " + itemId + ": " + itemCount;
  }
  
}