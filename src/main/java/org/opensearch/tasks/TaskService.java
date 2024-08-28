/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks;

import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestResponse;
import org.opensearch.core.rest.RestStatus;


public class TaskService {
    public static RestResponse getResponse(String taskId) {
        return new BytesRestResponse(RestStatus.OK, "Task: " + taskId + "\n");
    }

    public static RestResponse postResponse() {
        return new BytesRestResponse(RestStatus.OK, "New task\n");
    }
}
