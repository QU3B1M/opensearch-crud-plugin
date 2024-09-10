/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks.action;

import org.junit.Before;
import org.opensearch.action.get.GetRequest;
import org.opensearch.tasks.handler.RestTaskHandler;
import org.opensearch.tasks.model.Task;
import org.opensearch.test.OpenSearchTestCase;
import org.opensearch.test.rest.FakeRestRequest;

import java.util.Collections;
import java.util.Locale;

public class RestTaskGetActionTests extends OpenSearchTestCase {
    private String path;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.path =  String.format(Locale.ROOT, "%s", RestTaskHandler.BASE_URI);
    }

    public void testGetRequest() {
        String taskId = "task_123";

        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path + "/" + taskId);
        builder.withParams(Collections.singletonMap("id", taskId));

        FakeRestRequest request = builder.build();

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
