from confluent_kafka import Consumer
import json


f = open("config.json")

driver_options = json.load(f)

f.close()

consumer = Consumer(driver_options)

consumer.subscribe(['INBOUND'])

while True:
#for message in consumer:
    # message value and key are raw bytes -- decode if necessary!
    # e.g., for unicode: `message.value.decode('utf-8')`©
    message = consumer.poll(1.0)
    if message is None:
        continue
    if message.error():
        print("Consumer error: {}".format(message.error()))
        continue
    print ("%s:%d:%s: value=%s" % (message.topic(), message.partition(),message.offset(),message.value()))

consumer.close()