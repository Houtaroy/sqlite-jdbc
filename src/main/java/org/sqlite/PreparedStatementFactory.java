package org.sqlite;

import org.sqlite.jdbc4.JDBC4PreparedStatement;
import org.sqlite.util.SQLUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Houtaroy
 */
public class PreparedStatementFactory {
    /**
     * 创建PreparedStatement
     * 如果为批量语句, 返回BatchPreparedStatement
     *
     * @param connection 数据库连接
     * @param sql SQL语句
     * @return PreparedStatement
     * @throws SQLException SQL异常
     */
    public static PreparedStatement create(SQLiteConnection connection, String sql)
            throws SQLException {
        List<String> sqlList = SQLUtils.split(sql);
        return sqlList.size() > 1 ? new BatchPreparedStatement(connection, sqlList)
                : new JDBC4PreparedStatement(connection, sql);
    }
}
