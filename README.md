# Zools
zools is a language designed for describing how to do data format validation and conversion.


* For now, only json is supported


```bash
mvn clean compile assembly:single
java -jar target/zools-0.0.1-SNAPSHOT-jar-with-dependencies.jar example.zools example.json
# output
{"id":"a-b-c", "sensor":"b-c-d", "slot_status":true, "timestamp":"2017-07-12T23:19:00.000"} # original content in example.json
{"Parking_ID":"a-b-c","SEN_ID":"b-c-d","Park_Status":"true","Record_Time":"2017-07-12T23:19:00.000"} # field names were renamed according to the rules described in example.zools

```

