# Cloud Spanner

### Launch Cloud Spanner and CLI locally

```shell
docker-compose up -d
```

```shell
sample output:

Starting spring-gcp-spanner_gcloud-spanner-init_1 ... done
Starting spring-gcp-spanner_spanner_1             ... done
Starting spring-gcp-spanner_spanner-cli_1         ... done



docker ps -a                       
                  
CONTAINER ID   IMAGE                                             COMMAND                  CREATED         STATUS                           PORTS                                            NAMES
707812ad2df8   sjdaws/spanner-cli:latest                         "sh -c 'echo spanner…"   3 minutes ago   Up 3 minutes                                                                      spring-gcp-spanner_spanner-cli_1
a4f2a4880607   gcr.io/cloud-spanner-emulator/emulator:latest     "./gateway_main --ho…"   3 minutes ago   Up 3 minutes                     0.0.0.0:9010->9010/tcp, 0.0.0.0:9020->9020/tcp   spring-gcp-spanner_spanner_1
713b5639e198   gcr.io/google.com/cloudsdktool/cloud-sdk:latest   "bash -c 'gcloud con…"   3 minutes ago   Exited (0) 3 minutes ago                                                          spring-gcp-spanner_gcloud-spanner-init_1

```

### Launch Application locally

Launch application locally using IntelliJ using local profile

### Connect to Local Spanner using CLI

```shell
docker exec -it spring-gcp-spanner_spanner-cli_1 bash
 ```

```shell
sample output:

root@c605805167f6:/go#
```

#### Interact Spanner using CLI

```shell
root@707812ad2df8:/go# spanner-cli -p spanner-project -i spanner-instance -d spanner-database
Connected.
spanner>  

spanner> show tables;
+----------------------------+
| Tables_in_spanner-database |
+----------------------------+
| order_items                |
| orders                     |
| DATABASECHANGELOG          |
| DATABASECHANGELOGLOCK      |
+----------------------------+
4 rows in set (0.07 sec)


spanner> select * from orders;
+--------------------------------------+-------------+-----------------------------+-------------+-----------------------------+-----------------+
| order_id                             | description | creation_timestamp          | created_by  | lastmodified_timestamp      | lastmodified_by |
+--------------------------------------+-------------+-----------------------------+-------------+-----------------------------+-----------------+
| 5523755e-55bd-4db6-86de-998b4e25a7e9 | hello       | 2022-07-12T01:47:03.238607Z | Mr. Auditor | 2022-07-12T01:47:03.257386Z | Mr. Auditor     |
+--------------------------------------+-------------+-----------------------------+-------------+-----------------------------+-----------------+
1 rows in set (4.837125ms)

To Exit CLI; 

Ctld+D
```

#### Stop and Clean

```shell
stop IntelliJ application
```

```shell
docker-compose down --remove-orphans

Stopping spring-gcp-spanner_spanner-cli_1 ... done
Stopping spring-gcp-spanner_spanner_1     ... done
Removing spring-gcp-spanner_spanner-cli_1         ... done
Removing spring-gcp-spanner_spanner_1             ... done
Removing spring-gcp-spanner_gcloud-spanner-init_1 ... done
Removing network spring-gcp-spanner_default


docker ps -a

CONTAINER ID   IMAGE                           COMMAND             CREATED       STATUS                     PORTS     NAMES
```
### References

1. Local CLI - https://github.com/cloudspannerecosystem/spanner-cli 
