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
