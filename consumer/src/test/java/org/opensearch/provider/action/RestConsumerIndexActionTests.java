/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.consumer.action;

import org.junit.Before;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.common.xcontent.XContentType;
import org.opensearch.core.common.bytes.BytesArray;
import org.opensearch.test.OpenSearchTestCase;
import org.opensearch.test.rest.FakeRestRequest;
import org.opensearch.consumer.handler.RestConsumerHandler;

import java.io.IOException;
import java.util.Locale;

import static org.opensearch.consumer.model.Consumer.TASK_INDEX;
import static org.opensearch.consumer.model.Consumer.ConsumerStatus.PENDING;

public class RestConsumerIndexActionTests extends OpenSearchTestCase {
    private String path;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.path =  String.format(Locale.ROOT, "%s", RestConsumerHandler.BASE_URI);
    }

    public void testIndexRequest() throws IOException {
        String title = "Consumer 1";
        String description = "Description of Consumer 1";
        String status = "PENDING";

        String body = "{\"title\":\""+ title + "\",\"description\":\""+ description +"\",\"status\":\"" + status+ "\"}";

        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path);
        builder.withContent(new BytesArray(body), XContentType.JSON);

        FakeRestRequest request = builder.build();
        IndexRequest indexRequest = RestConsumerIndexAction.createIndexRequest(request);

        assertEquals(TASK_INDEX, indexRequest.index());
        assertEquals(title, indexRequest.sourceAsMap().get("title"));
        assertEquals(description, indexRequest.sourceAsMap().get("description"));
        assertEquals(status, indexRequest.sourceAsMap().get("status"));
    }

    public void testIndexRequestWithEmptyDescription() throws IOException {
        String title = "Consumer 1";
        String status = "PENDING";

        String body = "{\"title\":\""+ title + "\",\"status\":\"" + status+ "\"}";

        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path);
        builder.withContent(new BytesArray(body), XContentType.JSON);

        FakeRestRequest request = builder.build();
        IndexRequest indexRequest = RestConsumerIndexAction.createIndexRequest(request);

        assertEquals(TASK_INDEX, indexRequest.index());
        assertEquals(title, indexRequest.sourceAsMap().get("title"));
        assertEquals("" ,indexRequest.sourceAsMap().get("description"));
        assertEquals(status, indexRequest.sourceAsMap().get("status"));
    }

    public void testIndexRequestWithEmptyStatus() throws IOException {
        String title = "Consumer 1";
        String description = "Description of Consumer 1";

        String body = "{\"title\":\""+ title + "\",\"description\":\""+ description + "\"}";

        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path);
        builder.withContent(new BytesArray(body), XContentType.JSON);

        FakeRestRequest request = builder.build();
        IndexRequest indexRequest = RestConsumerIndexAction.createIndexRequest(request);

        assertEquals(TASK_INDEX, indexRequest.index());
        assertEquals(title, indexRequest.sourceAsMap().get("title"));
        assertEquals(description, indexRequest.sourceAsMap().get("description"));
        assertEquals(PENDING.toString(), indexRequest.sourceAsMap().get("status"));
    }

    public void testIndexRequestWithEmptyDescriptionAndStatus() throws IOException {
        String title = "Consumer 1";

        String body = "{\"title\":\""+ title + "\"}";

        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path);
        builder.withContent(new BytesArray(body), XContentType.JSON);

        FakeRestRequest request = builder.build();
        IndexRequest indexRequest = RestConsumerIndexAction.createIndexRequest(request);

        assertEquals(TASK_INDEX, indexRequest.index());
        assertEquals(title, indexRequest.sourceAsMap().get("title"));
        assertEquals("" ,indexRequest.sourceAsMap().get("description"));
        assertEquals(PENDING.toString(), indexRequest.sourceAsMap().get("status"));
    }

    public void testIndexRequestWithEmptyTitle() throws IOException {
        String description = "Description of Consumer 1";
        String status = "PENDING";

        String body = "{\"description\":\""+ description + "\",\"status\":\"" + status+ "\"}";

        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path);
        builder.withContent(new BytesArray(body), XContentType.JSON);

        FakeRestRequest request = builder.build();

        // Error is raised when no title is provided
        IllegalArgumentException e = expectThrows(IllegalArgumentException.class, () -> RestConsumerIndexAction.createIndexRequest(request));
        assertEquals("Missing required parameters: title is mandatory", e.getMessage());
    }
}
