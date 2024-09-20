/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks;

import com.carrotsearch.randomizedtesting.annotations.ThreadLeakScope;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.opensearch.client.Request;
import org.opensearch.client.Response;
import org.opensearch.plugins.Plugin;
import org.opensearch.test.OpenSearchIntegTestCase;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

import static org.opensearch.tasks.handler.RestTaskHandler.BASE_URI;
import static org.opensearch.tasks.model.Task.TASK_INDEX;

@ThreadLeakScope(ThreadLeakScope.Scope.NONE)
@OpenSearchIntegTestCase.ClusterScope(scope = OpenSearchIntegTestCase.Scope.SUITE)
public class TasksPluginIT extends OpenSearchIntegTestCase {

    @Override
    protected Collection<Class<? extends Plugin>> nodePlugins() {
        return Collections.singletonList(TasksPlugin.class);
    }

    public void testPluginInstalled() throws IOException, ParseException {
        Response response = getRestClient().performRequest(new Request("GET", "/_cat/plugins"));
        String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

        assertTrue(body.contains("tasks"));
    }

    public void testIndexCreated() throws IOException, ParseException {
        Request request = new Request("POST", BASE_URI);
        request.setJsonEntity("{\"title\":\"test\"}");
        getRestClient().performRequest(request);

        Response response = getRestClient().performRequest(new Request("GET", "_cat/indices"));
        String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

        assertTrue(body.contains(TASK_INDEX));
    }

    public void testCreateTask() throws IOException, ParseException {
        String title = "Task_1";
        String description = "Description of Task 1";
        String status = "PENDING";

        String task = "{\"title\":\""+ title + "\",\"description\":\""+ description +"\",\"status\":\"" + status + "\"}";

        Request post = new Request("POST", BASE_URI);
        post.setJsonEntity(task);

        Response postResponse = getRestClient().performRequest(post);
        String postBody = EntityUtils.toString(postResponse.getEntity(), StandardCharsets.UTF_8);
        assertTrue(postBody.contains("\"result\":\"created\""));

        Request get = new Request("GET", BASE_URI + "/" + title);
        Response getResponse = getRestClient().performRequest(get);
        String getBody = EntityUtils.toString(getResponse.getEntity(), StandardCharsets.UTF_8);

        assertTrue(getBody.contains("\"found\":true"));
        assertTrue(getBody.contains(task));
    }

    public void testTaskCompleteLifeCycle() throws IOException, ParseException {
        String id = "Task_id";
        String title = "Task_1";
        String description = "Description updated";
        String status = "IN_PROGRESS";

        String update_task = "{\"title\":\""+ title + "\",\"description\":\""+ description +"\",\"status\":\"" + status + "\"}";

        Request post = new Request("POST", BASE_URI);
        Request get = new Request("GET", BASE_URI + "/" + id);
        Request put = new Request("PUT", BASE_URI + "/" + id);
        Request delete = new Request("DELETE", BASE_URI + "/" + id);

        post.setJsonEntity("{\"title\":\""+ id +"\"}");
        put.setJsonEntity(update_task);

        Response postResponse = getRestClient().performRequest(post);
        String postBody = EntityUtils.toString(postResponse.getEntity(), StandardCharsets.UTF_8);
        assertTrue(postBody.contains("\"result\":\"created\""));

        Response getResponse = getRestClient().performRequest(get);
        String getBody = EntityUtils.toString(getResponse.getEntity(), StandardCharsets.UTF_8);
        assertTrue(getBody.contains("\"found\":true"));

        Response putResponse = getRestClient().performRequest(put);
        String putBody = EntityUtils.toString(putResponse.getEntity(), StandardCharsets.UTF_8);
        assertTrue(putBody.contains("\"result\":\"updated\""));
        assertTrue(putBody.contains(update_task));

        Response deleteResponse = getRestClient().performRequest(delete);
        String deleteBody = EntityUtils.toString(deleteResponse.getEntity(), StandardCharsets.UTF_8);
        assertTrue(deleteBody.contains("\"result\":\"deleted\""));

        getResponse = getRestClient().performRequest(get);
        getBody = EntityUtils.toString(getResponse.getEntity(), StandardCharsets.UTF_8);
        assertTrue(getBody.contains("\"found\":false"));
    }
}
