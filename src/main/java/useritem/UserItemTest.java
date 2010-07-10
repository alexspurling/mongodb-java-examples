package useritem;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

public class UserItemTest {

  private UserDAO userDao;
  
  public UserItemTest(UserDAO userDao) {
    this.userDao = userDao;
  }

  public void run() {
    
    //Randomly generate 100 users
    List<User> users = getRandomUsers(100);
    
    for (User user : users) {
      System.out.println("user: " + user);
      //Persist them in the db
      userDao.save(user);
    }
    
    long count = userDao.getUserCount();
    System.out.println("Now storing " + count + " users in the db");
    
    String firstId = users.get(0).getId();
    //Make sure at least one of the users successfully
    //made it to the database
    System.out.println("Getting user with id " + firstId);
    System.out.println(userDao.getById(String.valueOf(firstId)));
    
    //Get the most common items
    Map<Integer, Integer> itemCounts = userDao.getItemCounts();
    
    for (Integer itemId : itemCounts.keySet()) {
      System.out.println("Item " + itemId + ": " + itemCounts.get(itemId));
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