package org.sqlite;

import org.sqlite.core.CorePreparedStatement;
import org.sqlite.jdbc4.JDBC4PreparedStatement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Houtaroy
 */
public class BatchPreparedStatement extends JDBC4PreparedStatement {
    private final List<PreparedStatement> statements = new ArrayList<>();
    private final List<Integer> parameterStatementIndices = new ArrayList<>();
    private final List<Integer> parameterIndices = new ArrayList<>();

    /**
     * 构造方法
     * @param conn 数据库连接
     * @param sqlList SQL语句列表
     * @throws SQLException SQL异常
     */
    public BatchPreparedStatement(SQLiteConnection conn, List<String> sqlList) throws SQLException {
        super(conn);

        for (int i = 0; i < sqlList.size(); i++) {
            PreparedStatement statement = conn.prepareStatement(sqlList.get(i));
            statements.add(statement);
            int parameterCount = statement.getParameterMetaData().getParameterCount();
            if (parameterCount > 0) {
                for (int j = 0; j < parameterCount; j++) {
                    parameterStatementIndices.add(i);
                    parameterIndices.add(j + 1);
                }
            }
        }
    }

    @Override
    public void batch(int pos, Object value) throws SQLException {
        PreparedStatement statement = statements.get(parameterStatementIndices.get(pos - 1));
        if (statement instanceof CorePreparedStatement) {
            ((CorePreparedStatement) statement).batch(parameterIndices.get(pos - 1), value);
        }
    }

    @Override
    public boolean execute() throws SQLException {
        for (PreparedStatement statement : statements) {
            statement.execute();
        }
        return true;
    }
}
