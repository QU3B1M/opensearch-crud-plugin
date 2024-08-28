package org.opensearch.tasks.action;

import org.opensearch.action.index.IndexRequest;
import org.opensearch.common.xcontent.XContentFactory;
import org.opensearch.core.xcontent.ToXContent;
import org.opensearch.rest.RestRequest;
import org.opensearch.tasks.model.TaskDetails;

import java.io.IOException;

import static org.opensearch.tasks.TasksPlugin.TASK_INDEX;


public class RestTaskIndexAction {

    public static IndexRequest createIndexRequest(RestRequest request) throws IOException {
        String title = request.contentParser().mapStrings().get("title");
        String description = request.contentParser().mapStrings().get("description");

        TaskDetails task = new TaskDetails(title, description);
        IndexRequest indexRequest = new IndexRequest(TASK_INDEX);
        indexRequest.id(task.getTitle());
        indexRequest.source(task.toXContent(XContentFactory.jsonBuilder(), ToXContent.EMPTY_PARAMS));
        indexRequest.create(true);

        return indexRequest;
    }
}
