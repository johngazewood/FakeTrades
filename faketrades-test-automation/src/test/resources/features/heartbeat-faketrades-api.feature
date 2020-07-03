@FakeTradesApiHeartbeat
Feature: heartbeat-faketrades-api.feature
  

  Scenario: Get the heartbeat
    Given Faketrades API endpoint
    And "heartbeat" path
    When REST GET is made
    Then we get the response "The heart beats back"

  
