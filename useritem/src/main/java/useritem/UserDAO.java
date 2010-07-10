package useritem;

import java.util.List;

public interface UserDAO {
  
  void save(User User);
  User getById(String id);
  long getUserCount();
  List<ItemCount> getTopTenItems();

}