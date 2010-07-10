package useritem;

import java.util.Map;

public interface UserDAO {
  
  void save(User User);
  User getById(String id);
  long getUserCount();
  Map<Integer, Integer> getItemCounts();

}