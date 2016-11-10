= Install a local Cassandra

Link: http://www.java-allandsundry.com/2016/04/first-steps-to-spring-boot-cassandra.html

The simplest way to install Cassandra is via the excellent https://github.com/pcmanus/ccm[ccm] tool.

[source]
----
ccm create test -v 3.7 -n 3 -s --vnodes
----


Connect to one of the nodes:
[source]
----
ccm node1 cqlsh
----

And run these cql statements

[source]
----

CREATE KEYSPACE IF NOT EXISTS sample WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

use sample;

CREATE TABLE IF NOT EXISTS  sample.hotels (
    id UUID,
    name varchar,
    address varchar,
    state varchar,
    zip varchar,
    primary key((id), name)
);

CREATE TABLE IF NOT EXISTS  sample.hotels_by_letter (
    first_letter varchar,
    hotel_name varchar,
    hotel_id UUID,
    address varchar,
    state varchar,
    zip varchar,
    primary key((first_letter), hotel_name, hotel_id)
);


CREATE MATERIALIZED VIEW sample.hotels_by_state AS
SELECT id, name, address, state, zip FROM hotels
WHERE state IS NOT NULL AND id IS NOT NULL AND name IS NOT NULL
PRIMARY KEY ((state), name, id)
WITH CLUSTERING ORDER BY (name DESC);
----

= Spring Data Cassandra

Run the test which makes use of Spring Data Cassandra - `cass.SampleCassandraApplicationTest`


After run the application:

open browser and open: http://localhost:8080/hotels/2f70cc0f-f25c-4903-bc3e-d783fe3e2919

You should see a restful response: 

{
id: "2f70cc0f-f25c-4903-bc3e-d783fe3e2919",
name: "Sample Hotel",
address: "Sample Address",
state: null,
zip: "8764"
}

