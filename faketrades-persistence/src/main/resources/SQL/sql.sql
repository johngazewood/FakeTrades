CREATE EXTERNAL TABLE trades (
  tradeId int,
  amount varchar(50)
  ) LOCATION 's3://faketrades/production/trades/';

 CREATE EXTERNAL TABLE events (
    eventId int,
    tradeId int,
    eventtype varchar(50),
    version int
 ) LOCATION 's3://faketrades/production/events/';
 