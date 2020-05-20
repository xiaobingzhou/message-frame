package com.github.xiaobingzhou.messageframe.matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MatcherImpl implements Matcher {

    protected Map<String, String> matchKeyCache = new ConcurrentHashMap<>(128);
    protected String noMatchKey = "";

    @Override
    public Object match(KeyGenerator keyGenerator, Map<String, ?> keyMap) {
        if (keyMap == null)
            return null;

        String key = keyGenerator.generateKey();

        // 匹配到的key
        String matchCompleteKey = matchKeyCache.get(key);
        if (matchCompleteKey != null) {
            return noMatchKey.equals(matchCompleteKey) ? null : keyMap.get(matchCompleteKey);
        }

        matchCompleteKey = key;
        Object result = keyMap.get(key);

        if (result == null) {
            // 匹配*号
            for (int i = key.length(); i >= key.length() - 4; i--) {
                matchCompleteKey = key.substring(0, i) + MATCH_ALL;
                result = keyMap.get(matchCompleteKey);
                if (result != null) {
                    break;
                }
            }
        }

        matchKeyCache.put(key, matchCompleteKey == null ? noMatchKey : matchCompleteKey);

        return result;
    }

    public static void main(String[] args) {
        MatcherImpl matcher = new MatcherImpl();
        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("1234:1234", "1");
        keyMap.put("1234:123*", "2");
        keyMap.put("1234:12*", "3");
        keyMap.put("1234:1*", "4");
        keyMap.put("1234:*", "5");
        System.out.println(matcher.match(() -> "1234:1234", keyMap));
        System.out.println(matcher.match(() -> "1234:1234", keyMap));
        System.out.println(matcher.match(() -> "1234:1233", keyMap));
        System.out.println(matcher.match(() -> "1234:1222", keyMap));
        System.out.println(matcher.match(() -> "1234:1211", keyMap));
    }
}
