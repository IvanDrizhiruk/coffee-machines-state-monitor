Story: Register new cup and check status

BeforeStories:
Given cleanup coffee machine configurations
Given cleanup coffee machine states


Scenario: on registration new cap stete should be updated

Given a counter

When register in coffee machine with id 1 new coffee cup with 2 portions of coffee and milk


Then state of coffee machine with id 1 is:
|a|b|
|1|2|
