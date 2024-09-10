/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks.action;

import org.junit.Before;
import org.opensearch.action.delete.DeleteRequest;
import org.opensearch.tasks.handler.RestTaskHandler;
import org.opensearch.tasks.model.Task;
import org.opensearch.test.OpenSearchTestCase;
import org.opensearch.test.rest.FakeRestRequest;

import java.util.Collections;
import java.util.Locale;

public class RestTaskDeleteActionTests extends OpenSearchTestCase {
    private String path;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.path =  String.format(Locale.ROOT, "%s", RestTaskHandler.BASE_URI);
    }

    public void testDeleteRequest() {
        String taskId = "task_123";

        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path + "/" + taskId);
        builder.withParams(Collections.singletonMap("id", taskId));

        FakeRestRequest request = builder.build();

        DeleteRequest deleteRequest = RestTaskDeleteAction.deleteRequest(request);

        assertEquals(Task.TASK_INDEX, deleteRequest.index());
        assertEquals(taskId, deleteRequest.id());
    }

    public void testDeleteRequestWithoutId() {
        FakeRestRequest request = new FakeRestRequest.Builder(xContentRegistry()).build();

        IllegalArgumentException e = expectThrows(IllegalArgumentException.class, () -> RestTaskDeleteAction.deleteRequest(request));
        assertEquals("Missing required parameters: id is mandatory", e.getMessage());
    }
}
