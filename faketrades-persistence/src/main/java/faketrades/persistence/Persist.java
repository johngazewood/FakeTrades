package faketrades.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import com.amazonaws.services.dynamodbv2.model.Record;

public class Persist implements Runnable {

	// TODO: inject this
	String dbUrl = "jdbc:awsathena://AwsRegion=us-east-2";

	List<Record> records;
	String targetDatabase;
	String targetTable;

	QueryGenerator qgenerator;

	public Persist(List<Record> records, String targetDatabase, String targetTable) {
		this.records = records;
		this.targetDatabase = targetDatabase;
		this.targetTable = targetTable;
		qgenerator = new QueryGenerator();
	}

	@Override
	public void run() {
		for (Record record : records) {
			String dynamoEvent = record.getEventName();
			switch (dynamoEvent) {
			case "REMOVE":
				System.out.println("Persist>> please implement remove.");
				break;
			case "INSERT":
				insert(record);
				break;
			default:
				System.out.println("Persist>> please implement persistence behavior for dynamoEvent : " + dynamoEvent);
				break;
			}

		}
	}

	private void insert(Record record) {
		String query = qgenerator.generateInsertQuery(record, targetDatabase, targetTable);
		Connection conn = null;
		Statement stmt = null;

		Properties props = System.getProperties();
		try {
			conn = DriverManager.getConnection(dbUrl, props);
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(query);
			System.out.println("Persist>> query result: " + result);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


}
