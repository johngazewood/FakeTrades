package faketrades.persistence;

import com.amazonaws.services.dynamodbv2.model.Record;

public class QueryGenerator {

	public String generateInsertQuery(Record record, String db, String table) {
		String query = String.format("INSERT INTO %s.%s VALUES (", db, table);

		String[] params;
		switch (table) {
		case "trades":
			params = extractTradeParams(record);
			query += String.format("%s, '%s');", params[0], params[1]);
			break;
		case "events":
			params = extractEventsParams(record);
			query += String.format("%s, %s, '%s', %s)", params[0], params[1], params[2], params[3]);
		}
		return query;
	}

	protected String[] extractEventsParams(Record record) {
		String[] params = new String[4];
		params[0] = record.getDynamodb().getNewImage().get("tradeId").getN() + "";
		params[1] = record.getDynamodb().getNewImage().get("eventId").getN() + "";
		params[2] = record.getDynamodb().getNewImage().get("eventtype").getS();
		params[3] = record.getDynamodb().getNewImage().get("version").getN() + "";
		return params;
	}

	protected String[] extractTradeParams(Record record) {
		String[] params = new String[2];
		params[0] = record.getDynamodb().getNewImage().get("tradeId").getN() + "";
		params[1] = record.getDynamodb().getNewImage().get("amount").getS();
		return params;
	}

}
