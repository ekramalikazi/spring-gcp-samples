# Datastore Mode

### Cloud Firestore Datastore Instance
There can only be one Cloud Firestore instance associated with a single project. The Datastore instance is automatically created when you enable the API.
There can only be one Datastore instance associated with a single project. The Cloud Firestore in Datastore instance is automatically created when you enable the API.


### Enable API
`gcloud services enable datastore.googleapis.com`

### Data Schema
Because Cloud Firestore is a NoSQL database, 
you do not need to explicitly create tables, define data schema, etc. 
Simply use the API to store new documents, and perform CRUD operations.

### Launch Application locally

Launch application locally using IntelliJ

### Launch Datastore GUI application locally

```shell
docker-compose up
```

```shell
sample output:

Starting spring-gcp-datastore_dsadmin_1 ... done
Attaching to spring-gcp-datastore_dsadmin_1
dsadmin_1  | 2022/07/06 22:35:32 dsadmin (project 'my-datastore-project') listening on http://localhost:8080

Go To Browser: http://localhost:30303/
```


### References

https://docs.spring.io/spring-cloud-gcp/docs/current/reference/html/datastore.html#_spring_data_cloud_datastore
