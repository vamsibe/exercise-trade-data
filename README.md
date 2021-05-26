# exercise-trade-data
- Exercise project to parse trade data 
- This can be imported as maven project and run the test to simulate various parsing scenarios.

## approach
- Using spring initializer created a skeleton maven project.
- Used jackson-databind & lombok for parsing the json and build the output.
- Coded two approaches to parse the data.
- Parsed trade field validations performed externally.
- TradeTransformerTest will simulate various parsing scenarios
- Error log will be written to target

## Performance - Further improvements
- Dependencies auto provided
- Further analysis on shared state and refactor
- Use concurrent collection
- Error codes in case of failures
- Create performance test plan using tools like JMeter
- add spring-boot-starter-actuator and micrometer-registry-prometheus

## Monitoring - metrics 
- Actuator
- Health of the service
- Total trade parsed successfully 
- Total trade parsed failures/warnings 
- Duration to transform

## Monitoring - alerts    
- add spring-boot-starter-actuator and micrometer-registry-prometheus
- To expose health value in Prometheus we need to write registry bean configuration code for registering to micrometer module
- Prometheus can be used for event monitoring and alerting.It records realt time events and store in them in a time series db.
- In order to show metrics in Prometheus we need to add our configuration to prometheus.yml file.
- Add Prometheus as a datasource to Grafana, to show metrics. 
- Define a panel to query prometheus
- Options to add graphs & alerts using Grafana
 
