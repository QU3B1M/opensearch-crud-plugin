/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks.action;

import org.opensearch.action.delete.DeleteRequest;
import org.opensearch.rest.RestRequest;
import org.opensearch.tasks.model.Task;

/**
 * Handles the deletion of tasks in OpenSearch.
 */
public class RestTaskDeleteAction {

    /**
     * Creates a DeleteRequest for deleting a task.
     *
     * @param request The RestRequest containing the task ID.
     * @return The DeleteRequest to delete the task.
     */
    public static DeleteRequest deleteRequest(RestRequest request) {
        String taskId = request.param("id");
        if (taskId == null) {
            throw new IllegalArgumentException("Missing required parameters: id is mandatory");
        }

        return new DeleteRequest(Task.TASK_INDEX, taskId);
    }
}
