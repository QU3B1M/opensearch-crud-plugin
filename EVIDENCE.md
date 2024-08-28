- Check plugins
    ```powershell
    curl -XGET 'localhost:9200/_cat/plugins'
    integTest-0 tasks unspecified
    ```
- Test simple endpoint
    ```powershell
    curl -XGET 'http://localhost:9200/_plugins/tasks'
    Task: NO
    ```
    ```powershell
    curl -XPOST 'http://localhost:9200/_plugins/tasks'
    New task
    ```
  
- Test Post endpoint
    ```powershell
    curl -XPOST 'localhost:9200/_plugins/tasks' -H 'Content-Type: application/json' -d '{"title":"test", "description": "asd"}'
    
    {"_index":"tasks","_id":"test","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":0,"_primary_term":1}
    ```
- Test Get endpoint
    ```powershell
    curl -XGET 'localhost:9200/_plugins/tasks/test' -H 'Content-Type: application/json'

    {"_index":"tasks","_id":"test","_version":1,"_seq_no":0,"_primary_term":1,"found":true,"_source":{"title":"test","description":"asd","status":"PENDING"}}
    ```
- Test Put endpoint
    ```powershell
    curl -XPUT 'localhost:9200/_plugins/tasks/test' -H 'Content-Type: application/json' -d '{"title": "test", "description": "asdss"}'
    
    {"_index":"tasks","_id":"test","_version":2,"result":"updated","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":1,"_primary_term":1,"get":{"_seq_no":1,"_primary_term":1,"found":true,"_source":{"title":"test","description":"asdss","status":"PENDING"}}}
    ```
    ```powershell
    curl -XGET 'localhost:9200/_plugins/tasks/test' -H 'Content-Type: application/json'
    {"_index":"tasks","_id":"test","_version":2,"_seq_no":1,"_primary_term":1,"found":true,"_source":{"title":"test","description":"asdss","status":"PENDING"}}
    ```
