# Zools
zools is a language designed for describing how to do data format validation and conversion.


* For now, only json is supported


```bash
mvn clean compile assembly:single
java -jar target\zools-0.0.2-jar-with-dependencies.jar -rule example.zools -source example.json -if json -of json
# output
# origin format
[
        {"id":"a-b-c", "sensor":"b-c-d", "slot_status":true, "timestamp":"2017-07-12T23:19:00.000", "pos": {"x": 1.0, "y": 2.0}},
        {"id":"a-b-c-d", "sensor":"b-c-d-e", "slot_status":false, "timestamp":"2017-07-12T23:19:30.000", "pos": {"x": 1.0, "y": 2.0}}
]
# new format
[{"RecordTime":"2017-07-12T23:19:00.000","loc":{"lng":2.0,"lat":1.0},"SenID":"b-c-d","ParkStatus":true,"ParkingID":"a-b-c"},{"RecordTime":"2017-07-12T23:19:30.000","loc":{"lng":2.0,"lat":1.0},"SenID":"b-c-d-e","ParkStatus":false,"ParkingID":"a-b-c-d"}]

```

