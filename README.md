# Challenge - Task CRUD Plugin

This plugin is a simple CRUD plugin for OpenSearch. It provides endpoints to create, read, update and delete tasks.

## Usage

0. (optional) Run the tests: `gradle check`
1. Start the cluster: `gradle run`
2. Create a task: `curl -XPOST 'localhost:9200/_plugins/tasks' -H 'Content-Type: application/json' -d '{"title": "test"}'`
    ```json
    {"_index":"task","_id":"test","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":0,"_primary_term":1}
    ```
3. Read the task: `curl -XGET 'localhost:9200/_plugins/tasks/test'`
    ```json
    {"_index":"task","_id":"test","_version":1,"_seq_no":0,"_primary_term":1,"found":true,"_source":{"title":"test","description":"","status":"PENDING"}}
    ```
4. Update the task: `curl -XPUT 'localhost:9200/_plugins/tasks/test' -H 'Content-Type: application/json' -d '{"title": "test", "description": "test"}'`
    ```json
    {"_index":"task","_id":"test","_version":2,"result":"updated","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":1,"_primary_term":1,"get":{"_seq_no":1,"_primary_term":1,"found":true,"_source":{"title":"test","description":"test","status":"PENDING"}}}
    ```
5. Delete the task: `curl -XDELETE 'localhost:9200/_plugins/tasks/test'`
    ```json
    {"_index":"task","_id":"test","_version":3,"result":"deleted","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":2,"_primary_term":1}
    ```

## Endpoints

- **Create task:** `POST /_plugins/tasks`
- **Read task:** `GET /_plugins/tasks/{id}`
- **Update task:** `PUT /_plugins/tasks/{id}`
- **Delete task:** `DELETE /_plugins/tasks/{id}`

## Architecture

The plugin is composed of the following components:

- **action:** Contains the REST actions for each CRUD operation.
- **handler:** Contains the handler for the REST actions.
- **model:** Contains the model for the task.
- **TasksPlugin:** The main plugin class.
- **test:** Contains the integration and unit tests for the plugin.
- **yamlRestTest:** Contains the API test suite on YAML for the plugin.
  - **rest-api-spec.api:** Contains the API definition for each CRUD operation.
  - **rest-api-spec.test:** Contains the test cases for each CRUD operation.

The plugin source code is structured as follows:
```
/src
  ├── main/java/org/opensearch/tasks
  │   ├── action
  │   │   ├── RestTaskDeleteAction.java
  │   │   ├── RestTaskGetAction.java
  │   │   ├── RestTaskIndexAction.java
  │   │   └── RestTaskUpdateAction.java
  │   ├── handler
  │   │   └── RestTaskHandler.java
  │   ├── model
  │   │   └── Task.java
  │   └── TasksPlugin.java
  ├── test/java/org/opensearch/tasks
  │   ├── TasksPluginIT.java
  │   └── TasksTests.java
  └── yamlRestTest
      ├── java/org/opensearch/tasks/TasksClientYamlTestSuiteIT.java
      └── resources/rest-api-spec
          ├── api
          │   ├── _plugins.tasks_create.json
          │   ├── _plugins.tasks_delete.json
          │   ├── _plugins.tasks_read.json
          │   └── _plugins.tasks_update.json
          └── test
              ├── 10_basic.yml
              ├── 20_tasks_create.yml
              ├── 30_tasks_read.yml
              ├── 40_tasks_update.yml
              └── 50_tasks_delete.yml
```

---

### Technical debt

- **Task Search:** The plugin should have a search endpoint to search for tasks using filters. It should also return all tasks if no filter is provided.
- **Error handling:** The plugin error handling is very basic. It should return a proper error message when an error occurs.
- **Tests:** The plugin is missing the unit and integration tests. Only the YAML tests are implemented.

### Improvements
- **Logging:** The plugin should log the actions it performs.
- **Security:** The plugin should have some security mechanisms to prevent unauthorized access.
- **Documentation:** The plugin should have more documentation, especially about the endpoints and the model.

---

### Used software versions
- **JVM:** 21.0.4
- **Gradle:** 8.5
- **Groovy:** 3.0.17

### Faced issues

- Not a lot of documentation about OpenSearch Plugin development.
- Error `Found invalid file permissions:  Source file is executable:...` for several source files.
  > **FIX:** `chmod -x` for each problematic source file.
  > It may be because of the compression/decompression process, as the project was downloaded as a zip file.
- Errors displayed by OpenSearch results not clear.
  > Faced the error `illegal_argument_exception` because of missing `request.param("id")` on upgrade endpoint,
  > I wasted a lot of time trying to figure it out by googling and doing a poor debug until I found the reason.
  
