/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks.action;

import org.junit.Before;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.common.xcontent.XContentType;
import org.opensearch.core.common.bytes.BytesArray;
import org.opensearch.test.OpenSearchTestCase;
import org.opensearch.test.rest.FakeRestChannel;
import org.opensearch.test.rest.FakeRestRequest;
import org.opensearch.tasks.handler.RestTaskHandler;

import java.io.IOException;
import java.util.Locale;

import static org.opensearch.tasks.model.Task.TASK_INDEX;

public class RestTaskIndexActionTests extends OpenSearchTestCase {
    private String path;
    private String body;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.path =  String.format(Locale.ROOT, "%s", RestTaskHandler.BASE_URI);
        this.body = "{ \"title\": \"Task 1\", \"description\": \"Description of Task 1\", \"status\": \"PENDING\" }";
    }

    public void testCreateIndexRequest() throws IOException {
        // Create and configure Builder object
        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path);
        builder.withContent(new BytesArray(this.body), XContentType.JSON);
        // Create FakeRestRequest and FakeRestChannel objects
        FakeRestRequest request = builder.build();
        final FakeRestChannel channel = new FakeRestChannel(request, true, 1);
        // Call createIndexRequest() method
        IndexRequest indexRequest = RestTaskIndexAction.createIndexRequest(request);
        // Assert the returned IndexRequest object
        assertEquals("Task 1", indexRequest.id());
        assertEquals(TASK_INDEX, indexRequest.index());
    }
}
