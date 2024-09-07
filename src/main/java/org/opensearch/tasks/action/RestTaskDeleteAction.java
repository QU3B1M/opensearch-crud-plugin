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

import java.io.IOException;

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
        return new DeleteRequest(Task.TASK_INDEX, request.param("id"));
    }
}
