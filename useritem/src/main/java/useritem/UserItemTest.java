package useritem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserItemTest {

	private UserDAO userDao;

	public UserItemTest(UserDAO userDao) {
		this.userDao = userDao;
	}

	public void run() {

		System.out.println("Generating 1000 random users");
		// Randomly generate 1000 users
		List<User> users = getRandomUsers(1000);

		System.out.println("Saving new users to db");
		userDao.save(users);

		long count = userDao.getUserCount();
		System.out.println("Now storing " + count + " users in the db");

		String firstId = users.get(0).getId();
		// Make sure at least one of the users successfully
		// made it to the database
		System.out.println("Getting user with id " + firstId);
		System.out.println(userDao.getById(String.valueOf(firstId)));

		// Get the most common items
		System.out.println("Getting the top ten items");
		List<ItemCount> topTenItems = userDao.getTopTenItems();

		for (ItemCount itemCount : topTenItems) {
			System.out.println(itemCount);
		}
	}

	private List<User> getRandomUsers(int num) {
		List<User> users = new ArrayList<User>(num);

		Random random = new Random();
		for (int i = 0; i < num; i++) {
			long id = random.nextLong();
			String name = "Bob";
			List<Item> items = getRandomItems(random.nextInt(20));
			users.add(new User(String.valueOf(id), name, items));
		}

		return users;
	}

	private List<Item> getRandomItems(int num) {
		List<Item> items = new ArrayList<Item>(num);

		Random random = new Random();
		for (int i = 0; i < num; i++) {
			items.add(new Item(random.nextInt(100)));
		}
		return items;
	}

}