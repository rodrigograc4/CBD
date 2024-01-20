package redis.ex3;

import java.util.Set;
import redis.clients.jedis.Jedis;
import java.util.*;
 
public class SimplePost {
 
	private Jedis jedis;
	public static String USERS = "users"; // Key set for users' name
    public static String USERS_LIST = "users_list";  // Key list for users' name
    public static String USERS_HASHMAP = "users_hashmap";  // Key hashmap for users' name
	
	public SimplePost() {
		this.jedis = new Jedis("localhost", 6379);
	}
 
    // using lists:

    public void saveUserList(String username) {
        jedis.rpush(USERS_LIST, username);
    }

    public List<String> getUserList() {
        return jedis.mget(USERS_LIST);
    }
    
    // using hashmaps

    public void saveUserHashmap(String username, Map<String, String> hash) {
        jedis.hmset(username, hash);
    }

    public Map<String, String> getUserHashmap(String user) {
        return jedis.hgetAll(user);
    }

    // using sets:

	public void saveUser(String username) {
		jedis.sadd(USERS, username);
	}
	public Set<String> getUser() {
		return jedis.smembers(USERS);
	}
	
	public Set<String> getAllKeys() {
		return jedis.keys("*");
	}
 
	public static void main(String[] args) {

		SimplePost board = new SimplePost();

		// set some users
		String[] users = { "Ana", "Pedro", "Maria", "Luis" };

        // for sets
        System.out.println("Set:");
		for (String user: users) 
			board.saveUser(user);
		board.getAllKeys().stream().forEach(System.out::println);
		board.getUser().stream().forEach(System.out::println);

        // for lists
        System.out.println("List:");
        for (String user: users)
            board.saveUserList(user);
        board.getAllKeys().stream().forEach(System.out::println);
        board.getUserList().stream().forEach(System.out::println);

        // for hashmaps
        System.out.println("Hashmap:");
        Map<String, String> hash = new HashMap<String, String>();
        hash.put("Ana", "Porto");
        hash.put("Pedro", "Aveiro");
        hash.put("Maria", "Santarém");
        hash.put("Luis", "Beja");

        for (String user : users)
            board.saveUserHashmap(user, hash);
        board.getAllKeys().stream().forEach(System.out::println);
        for (Map.Entry<String, String> entry : board.getUserHashmap("names_city").entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());

        }
	}
}


    
