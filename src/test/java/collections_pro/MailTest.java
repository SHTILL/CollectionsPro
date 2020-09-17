package collections_pro;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class MailTest {
    @Test
    public void whenMergeUsers() {
        Map<String, Set<String>> userList = new HashMap<>();

        Set<String> user1MailList = new HashSet<>();
        user1MailList.add("xxx@ya.ru");
        user1MailList.add("fox@gmail.com");
        user1MailList.add("lol@mail.ru");
        userList.put("user1", user1MailList);

        Set<String> user2MailList = new HashSet<>();
        user2MailList.add("fox@gmail.com");
        user2MailList.add("lol@mail.ru");
        userList.put("user2", user2MailList);

        Map<String, Set<String>> mergedList = Mail.mergeUsers(userList);
        assertFalse(mergedList.containsKey("user2"));
    }

    @Test
    public void whenAddUniqueEmails() {
        Map<String, Set<String>> userList = new HashMap<>();

        Set<String> user1MailList = new HashSet<>();
        user1MailList.add("first@ya.ru");
        userList.put("user1", user1MailList);

        Set<String> user2MailList = new HashSet<>();
        user2MailList.add("second@mail.com");
        userList.put("user2", user2MailList);

        Map<String, Set<String>> mergedList = Mail.mergeUsers(userList);
        assertTrue(mergedList.containsKey("user1") && mergedList.containsKey("user2"));
    }
}