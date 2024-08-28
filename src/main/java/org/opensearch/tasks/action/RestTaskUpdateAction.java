package org.opensearch.tasks.action;

import org.opensearch.action.update.UpdateRequest;
import org.opensearch.common.xcontent.XContentFactory;
import org.opensearch.core.xcontent.ToXContent;
import org.opensearch.rest.RestRequest;
import org.opensearch.tasks.model.TaskDetails;

import java.io.IOException;

import static org.opensearch.tasks.TasksPlugin.TASK_INDEX;


public class RestTaskUpdateAction {

    public static UpdateRequest updateRequest(RestRequest request) throws IOException {
        String taskId = request.param("id");
        String title = request.contentParser().mapStrings().get("title");
        String description = request.contentParser().mapStrings().get("description");

        TaskDetails task = new TaskDetails(title, description);
        UpdateRequest updateRequest = new UpdateRequest(TASK_INDEX, taskId);
        updateRequest.doc(task.toXContent(XContentFactory.jsonBuilder(), ToXContent.EMPTY_PARAMS));
        updateRequest.fetchSource(true);

        return updateRequest;
    }
}
