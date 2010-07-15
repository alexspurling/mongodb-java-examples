package useritem;

import java.util.List;

public interface UserDAO {

	void save(User user);

	void save(List<User> users);

	User getById(String id);

	long getUserCount();

	List<ItemCount> getTopTenItems();

}