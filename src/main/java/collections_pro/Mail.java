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
        userList = fastMergeUsers(userList);
        userList = mergeUsers(userList);
        System.out.print(userList);
        System.out.println();
    }

    static public Map<String, Set<String>> mergeUsers(Map<String, Set<String>> initList)
    {
        //contains mails that were processed and its correspondent users
        Map<String, String> processedMails = new HashMap<>();

        Map<String, Set<String>> mergedList = new HashMap<>();

        for (String user: initList.keySet()) {
            Set<String> mailList = initList.get(user);
            String alias = user;

            for (String mail: mailList) {
                String duplicate = processedMails.get(mail);

                if (duplicate == null) {
                    processedMails.put(mail, alias);

                    Set<String> list = mergedList.get(alias);
                    if (list == null) {
                        mergedList.put(alias, new HashSet<>(Collections.singletonList(mail)));
                    } else {
                        list.add(mail);
                    }
                } else {
                    Set<String> duplicateMails = mergedList.get(duplicate);
                    Set<String> aliasMails     = mergedList.get(alias);

                    Set<String> destList, sourceList;
                    String dest, source;
                    if (duplicateMails.size() > aliasMails.size()) {
                        dest = duplicate;
                        destList = duplicateMails;

                        source = alias;
                        sourceList = aliasMails;
                    } else {
                        dest = alias;
                        destList = aliasMails;

                        source = duplicate;
                        sourceList = duplicateMails;
                    }

                    destList.addAll(sourceList);
                    mergedList.remove(source);
                    for (String sourceMail: sourceList) {
                        processedMails.put(sourceMail, dest);
                    }

                    alias = dest;
                }
            }
        }
        return mergedList;
    }

    static public Map<String, Set<String>> fastMergeUsers(Map<String, Set<String>> initList)
    {
        //contains mails that were processed and its correspondent users
        Map<String, String> processedMails = new HashMap<>();

        //contains aliases for each user
        Map<String, Set<String>> aliasesMap = new HashMap<>();

        for (String user: initList.keySet()) {
            Set<String> mailList = initList.get(user);

            for (String mail : mailList) {
                String duplicate = processedMails.get(mail);

                if (duplicate == null) {
                    processedMails.put(mail, user);
                    aliasesMap.computeIfAbsent(user, u -> new HashSet<>(Collections.singletonList(u)));
                } else {
                    Set<String> userAliases = aliasesMap.get(user);
                    Set<String> duplicateAliases = aliasesMap.get(duplicate);

                    if (userAliases == null && duplicateAliases == null) {
                        Set<String> aliases = new HashSet<>(Arrays.asList(user, duplicate));
                        aliasesMap.put(user, aliases);
                        aliasesMap.put(duplicate, aliases);
                        continue;
                    }

                    if (userAliases == null) {
                        duplicateAliases.add(user);
                        aliasesMap.put(user, duplicateAliases);
                        continue;
                    }

                    if (duplicateAliases == null) {
                        userAliases.add(duplicate);
                        aliasesMap.put(duplicate, userAliases);
                        continue;
                    }

                    if (duplicateAliases.size() > userAliases.size()) {
                        duplicateAliases.addAll(userAliases);
                        aliasesMap.put(user, duplicateAliases);
                    } else {
                        userAliases.addAll(duplicateAliases);
                        aliasesMap.put(duplicate, userAliases);
                    }
                }
            }
        }

        Map<String, Set<String>> mergedList = new HashMap<>();
        Set<String> mergedUsers = new HashSet<>();

        for (String k: aliasesMap.keySet()) {
            Set<String> aliases = aliasesMap.get(k);
            if (!mergedUsers.contains(k)) {
                Set<String> mails = new HashSet<>();
                mergedList.put(k, mails);
                for (String user : aliases) {
                    mails.addAll(initList.get(user));
                    mergedUsers.add(user);
                }
            }
        }
        return mergedList;
    }
}
