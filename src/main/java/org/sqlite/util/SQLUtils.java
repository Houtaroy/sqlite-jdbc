package org.sqlite.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Houtaroy
 */
public class SQLUtils {
    private SQLUtils() {
    }

    /**
     * 拆分SQL
     *
     * @param sql 批量SQL语句
     * @return SQL语句列表
     */
    public static List<String> split(String sql) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        char quoteChar = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            sb.append(c);
            if (inQuotes) {
                if (StringUtils.isQuote(c) && c == quoteChar) {
                    inQuotes = false;
                }
            } else {
                if (StringUtils.isQuote(c)) {
                    inQuotes = true;
                    quoteChar = c;

                } else if (c == ';') {
                    result.add(sb.toString());
                    sb.delete(0, sb.length());
                }
            }
        }
        if (sb.length() > 0) {
            result.add(sb.toString());
        }
        return result;
    }
}
