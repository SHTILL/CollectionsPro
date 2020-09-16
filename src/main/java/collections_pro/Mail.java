package collections_pro;

import java.util.*;

public class Mail {
    public static void main(String[] args) {
        System.out.println("Mails merging example");
        Map<String, Set<String>> userList = new HashMap<>();

        Set<String> user1MailList = new HashSet<>();
        user1MailList.add("xxx@ya.ru");
        user1MailList.add("foo@gmail.com");
        user1MailList.add("lol@mail.ru");
        userList.put("user1", user1MailList);

        Set<String> user2MailList = new HashSet<>();
        user2MailList.add("foo@gmail.com");
        user2MailList.add("ups@pisem.net");
        userList.put("user2", user2MailList);

        Set<String> user3MailList = new HashSet<>();
        user3MailList.add("xyz@pisem.net");
        user3MailList.add("vasya@pupkin.com");
        userList.put("user3", user3MailList);

        Set<String> user4MailList = new HashSet<>();
        user4MailList.add("ups@pisem.net");
        user4MailList.add("aaa@bbb.ru");
        userList.put("user4", user4MailList);

        Set<String> user5MailList = new HashSet<>();
        user5MailList.add("xyz@pisem.net");
        userList.put("user5", user5MailList);

        System.out.println("Initial Mails' List:");
        System.out.print(userList);
        System.out.println();

        System.out.println("Merged Mails' List:");
        userList = userMerge(userList);
        System.out.print(userList);
        System.out.println();
    }

    static public Map<String, Set<String>> userMerge(Map<String, Set<String>> initList)
    {
        //contains mails that were processed and its correspondent users
        Map<String, String> processedMails = new HashMap<>();

        //contains merged mails' list
        Map<String, Set<String>> mergedList = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry: initList.entrySet()) {
            for (String mail: entry.getValue()) {
                String curUser = entry.getKey();
                String existedUser = processedMails.get(mail);

                if (existedUser == null) {
                    // mail was not processed -> add it to processed list
                    processedMails.put(mail, curUser);

                    //assign mail to a user
                    Set<String> list = mergedList.get(curUser);
                    if (list == null) {
                        //create new Set if doesn't exist
                        mergedList.put(curUser, new HashSet<>(Collections.singletonList(mail)));
                    } else {
                        //add mail to Set if exists
                        list.add(mail);
                    }
                } else {
                    Set<String> list = mergedList.get(curUser);
                    //if current user had unique mails current user consumes all mails of existed user
                    //if current user didn't have unique mails then do nothing - his not unique email will be consumed by existed user
                    if (list != null) {
                        Set<String> existedUserMails = mergedList.get(existedUser);
                        mergedList.get(curUser).addAll(existedUserMails);
                        mergedList.remove(existedUser);
                        for (String existedMail: existedUserMails) {
                            processedMails.put(existedMail, curUser);
                        }
                    }
                }
            }
        }
        return mergedList;
    }
}
