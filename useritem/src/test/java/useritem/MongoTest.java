package useritem;

import org.junit.Test;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;

public class MongoTest {

	@Test
	public void testUserItemsWithMongoDB() {

		Morphia morphia = new Morphia();
		morphia.map(User.class).map(Item.class);
		Datastore ds = morphia.createDatastore("mydb");
		UserDAO dao = new MongoUserDAO(ds);

		UserItemTest userItemTest = new UserItemTest(dao);
		userItemTest.run();
	}

}