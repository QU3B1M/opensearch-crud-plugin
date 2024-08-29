- Check plugins
    ```shell
    curl -XGET 'localhost:9200/_cat/plugins'
    integTest-0 tasks unspecified
    ```
- Test simple endpoint
    ```shell
    curl -XGET 'http://localhost:9200/_plugins/tasks'
    Task: NO
    ```
    ```shell
    curl -XPOST 'http://localhost:9200/_plugins/tasks'
    New task
    ```
  
- Test Post endpoint
    ```shell
    curl -XPOST 'localhost:9200/_plugins/tasks' -H 'Content-Type: application/json' -d '{"title":"test", "description": "asd"}'
    
    {"_index":"tasks","_id":"test","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":0,"_primary_term":1}
    ```
- Test Get endpoint
    ```shell
    curl -XGET 'localhost:9200/_plugins/tasks/test' -H 'Content-Type: application/json'

    {"_index":"tasks","_id":"test","_version":1,"_seq_no":0,"_primary_term":1,"found":true,"_source":{"title":"test","description":"asd","status":"PENDING"}}
    ```
- Test Put endpoint
    ```shell
    curl -XPUT 'localhost:9200/_plugins/tasks/test' -H 'Content-Type: application/json' -d '{"title": "test", "description": "asdss"}'
    
    {"_index":"tasks","_id":"test","_version":2,"result":"updated","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":1,"_primary_term":1,"get":{"_seq_no":1,"_primary_term":1,"found":true,"_source":{"title":"test","description":"asdss","status":"PENDING"}}}
    ```shell
    curl -XGET 'localhost:9200/_plugins/tasks/test' -H 'Content-Type: application/json'
    {"_index":"tasks","_id":"test","_version":2,"_seq_no":1,"_primary_term":1,"found":true,"_source":{"title":"test","description":"asdss","status":"PENDING"}}
    ```
- Test Delete endpoint
    ```shell
    curl -XDELETE 'localhost:9200/_plugins/tasks/test' -H 'Content-Type: application/json'

    {"_index":"tasks","_id":"test","_version":3,"result":"deleted","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":2,"_primary_term":1}
    ```
    ```shell
    curl -XGET 'localhost:9200/_plugins/tasks/test' -H 'Content-Type: application/json'

    {"_index":"tasks","_id":"test","found":false}
    ```

