/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.consumer.action;

import org.junit.Before;
import org.opensearch.action.delete.DeleteRequest;
import org.opensearch.consumer.handler.RestConsumerHandler;
import org.opensearch.consumer.model.Consumer;
import org.opensearch.test.OpenSearchTestCase;
import org.opensearch.test.rest.FakeRestRequest;

import java.util.Collections;
import java.util.Locale;

public class RestConsumerDeleteActionTests extends OpenSearchTestCase {
    private String path;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.path =  String.format(Locale.ROOT, "%s", RestConsumerHandler.BASE_URI);
    }

    public void testDeleteRequest() {
        String consumerId = "consumer_123";

        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path + "/" + consumerId);
        builder.withParams(Collections.singletonMap("id", consumerId));

        FakeRestRequest request = builder.build();

        DeleteRequest deleteRequest = RestConsumerDeleteAction.deleteRequest(request);

        assertEquals(Consumer.TASK_INDEX, deleteRequest.index());
        assertEquals(consumerId, deleteRequest.id());
    }

    public void testDeleteRequestWithoutId() {
        FakeRestRequest request = new FakeRestRequest.Builder(xContentRegistry()).build();

        IllegalArgumentException e = expectThrows(IllegalArgumentException.class, () -> RestConsumerDeleteAction.deleteRequest(request));
        assertEquals("Missing required parameters: id is mandatory", e.getMessage());
    }
}
