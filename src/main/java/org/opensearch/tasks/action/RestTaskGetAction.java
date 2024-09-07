/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks.action;

import org.opensearch.action.get.GetRequest;
import org.opensearch.rest.RestRequest;
import org.opensearch.search.fetch.subphase.FetchSourceContext;
import org.opensearch.tasks.model.Task;

/**
 * Handles the retrieval of tasks in OpenSearch.
 */
public class RestTaskGetAction {

    /**
     * Creates a GetRequest for retrieving a task.
     *
     * @param request The RestRequest containing the task ID.
     * @return The GetRequest to retrieve the task.
     */
    public static GetRequest getRequest(RestRequest request) {
        GetRequest getRequest = new GetRequest(Task.TASK_INDEX, request.param("id"));
        getRequest.fetchSourceContext(FetchSourceContext.parseFromRestRequest(request));

        return getRequest;
    }
}
