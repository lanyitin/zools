# Zools
zools is a language designed for describing how to do data format validation and conversion.

## Quick Start

```bash
$ mvn clean compile assembly:single
$ java -jar target\zools-<version>-jar-with-dependencies.jar -rule example.zools -source example.json -if json -of json
# origin format
[
        {"id":"a-b-c", "sensor":"b-c-d", "slot_status":true, "timestamp":"2017-07-12T23:19:00.000", "pos": {"x": 1.0, "y": 2.0}},
        {"id":"a-b-c-d", "sensor":"b-c-d-e", "slot_status":false, "timestamp":"2017-07-12T23:19:30.000", "pos": {"x": 1.0, "y": 2.0}}
]
# new format
[{"RecordTime":"2017-07-12T23:19:00.000","loc":{"lng":2.0,"lat":1.0},"SenID":"b-c-d","ParkStatus":true,"ParkingID":"a-b-c"},{"RecordTime":"2017-07-12T23:19:30.000","loc":{"lng":2.0,"lat":1.0},"SenID":"b-c-d-e","ParkStatus":false,"ParkingID":"a-b-c-d"}]

```

## License
MIT License

Copyright (c) 2018 Lan, Yitin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.