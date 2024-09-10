/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks.action;

import org.opensearch.action.get.GetRequest;
import org.opensearch.tasks.model.Task;
import org.opensearch.test.OpenSearchTestCase;
import org.opensearch.test.rest.FakeRestRequest;

import java.util.Collections;

public class RestTaskGetActionTests extends OpenSearchTestCase {

    public void testGetRequest() {
        String taskId = "task_123";
        FakeRestRequest request = new FakeRestRequest.Builder(xContentRegistry())
                .withParams(Collections.singletonMap("id", taskId))
                .build();

        GetRequest getRequest = RestTaskGetAction.getRequest(request);

        assertEquals(Task.TASK_INDEX, getRequest.index());
        assertEquals(taskId, getRequest.id());
    }

    public void testGetRequestWithoutId() {
        FakeRestRequest request = new FakeRestRequest.Builder(xContentRegistry()).build();

        IllegalArgumentException e = expectThrows(IllegalArgumentException.class, () -> RestTaskGetAction.getRequest(request));
        assertEquals("Missing required parameters: id is mandatory", e.getMessage());
    }
}
