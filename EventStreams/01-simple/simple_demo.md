# Simple demo of IBM Event Streams

## Prerequistes
* OCP installed
* Event Streams operator deployed

## Build the IBM Event Stremas cluster

Create the cluster
```
oc apply -f es-cluster.yaml
```

Create the Topic
```
oc apply -f topic.yaml
```

Create the User 
```
oc apply -f user.yaml
```

## Prepare the environment for apps

Get the host of the bootstrap
```
oc get -n cp4i route/mydev-kafka-bootstrap -o jsonpath={.spec.host} 
```

Get the certificate
```
oc extract secret/mydev-cluster-ca-cert --keys=ca.crt --to=- > ca.crt
```

Get the test user password
```
oc get secret/test -o jsonpath={.data.password} | base64 -d 
```

update the file apps/config.json with the collectected values

## Launch apps

Launch the consumer 
```
python ./apps/consumer.py
```

In a second terminal windows, launch the producer
```
python ./apps/prodcuer.py
```

you should see the producer activity and the meesage retreived by the consumer. 