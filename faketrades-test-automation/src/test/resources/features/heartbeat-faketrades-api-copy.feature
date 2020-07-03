@FakeTradesApiHeartbeat
Feature: heartbeat-faketrades-api-copy.feature
  

  Scenario: Copied - Get the heartbeat
    Given Faketrades API endpoint
    And "heartbeat" path
    When REST GET is made
    Then we get the response "The heart beats back"

  
