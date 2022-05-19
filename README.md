# Houtaroy SQLite JDBC Driver

本项目fork自[SQLite JDBC Driver](https://github.com/xerial/sqlite-jdbc)

## 拓展功能

### 执行批量SQL

```java
public class Batch {
    public void batch() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:/database.db");
        PreparedStatement batch = connection.prepareStatement("delete from batch;insert into batch values (?);")
        // 事务
        PreparedStatement transactionBatch = connection.prepareStatement(
            "begin transaction;delete from batch;insert into batch values (?);commit;"
        )
        batch.execute();
        transactionBatch.execute();
        connection.close()
    }
}
```

使用ORM框架时, 如已自动开启事务, 请不要书写`begin transaction`和`commit`

