package ru.yandex.money.common.dbqueue.internal.dao;

import org.junit.BeforeClass;
import ru.yandex.money.common.dbqueue.dao.Oracle11QueueDao;
import ru.yandex.money.common.dbqueue.internal.pick.Oracle11QueuePickTaskDao;
import ru.yandex.money.common.dbqueue.settings.QueueId;
import ru.yandex.money.common.dbqueue.settings.QueueLocation;
import ru.yandex.money.common.dbqueue.utils.OracleDatabaseInitializer;

import java.util.UUID;

/**
 * @author Oleg Kandaurov
 * @since 12.10.2019
 */
public class CustomOracle11QueuePickTaskDaoTest extends QueuePickTaskDaoTest {

    @BeforeClass
    public static void beforeClass() {
        OracleDatabaseInitializer.initialize();
    }

    public CustomOracle11QueuePickTaskDaoTest() {
        super(new Oracle11QueueDao(OracleDatabaseInitializer.getJdbcTemplate(), OracleDatabaseInitializer.CUSTOM_SCHEMA),
                pickTaskSettings -> new Oracle11QueuePickTaskDao(OracleDatabaseInitializer.getJdbcTemplate(),
                        OracleDatabaseInitializer.CUSTOM_SCHEMA, pickTaskSettings),
                OracleDatabaseInitializer.CUSTOM_TABLE_NAME, OracleDatabaseInitializer.CUSTOM_SCHEMA,
                OracleDatabaseInitializer.getJdbcTemplate(), OracleDatabaseInitializer.getTransactionTemplate());
    }

    @Override
    protected String currentTimeSql() {
        return "CURRENT_TIMESTAMP";
    }

    @Override
    protected QueueLocation generateUniqueLocation() {
        return QueueLocation.builder().withTableName(tableName)
                .withIdSequence("tasks_seq")
                .withQueueId(new QueueId("test-queue-" + UUID.randomUUID())).build();
    }
}
