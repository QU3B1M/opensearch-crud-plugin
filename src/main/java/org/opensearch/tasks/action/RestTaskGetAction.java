package org.opensearch.tasks.action;

import org.opensearch.action.get.GetRequest;
import org.opensearch.rest.RestRequest;
import org.opensearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;

import static org.opensearch.tasks.TasksPlugin.TASK_INDEX;


public class RestTaskGetAction {

    public static GetRequest getRequest(RestRequest request) throws IOException {
        String taskId = request.param("id");

        GetRequest getRequest = new GetRequest(TASK_INDEX, taskId);
        getRequest.fetchSourceContext(FetchSourceContext.parseFromRestRequest(request));

        return getRequest;
    }
}
