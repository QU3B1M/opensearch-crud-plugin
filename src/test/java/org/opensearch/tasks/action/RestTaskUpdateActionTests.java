/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks.action;

import org.junit.Before;
import org.opensearch.action.update.UpdateRequest;
import org.opensearch.common.xcontent.XContentType;
import org.opensearch.core.common.bytes.BytesArray;
import org.opensearch.tasks.handler.RestTaskHandler;
import org.opensearch.test.OpenSearchTestCase;
import org.opensearch.test.rest.FakeRestRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

import static org.opensearch.tasks.model.Task.TASK_INDEX;

public class RestTaskUpdateActionTests extends OpenSearchTestCase {
    private String path;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.path =  String.format(Locale.ROOT, "%s", RestTaskHandler.BASE_URI);
    }

    public void testUpdateRequest() throws IOException {
        String title = "Task 1";
        String description = "Description of Task 1";
        String status = "PENDING";

        String body = "{\"title\":\""+ title + "\",\"description\":\""+ description +"\",\"status\":\"" + status+ "\"}";

        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path + "/" + title);
        builder.withParams(Collections.singletonMap("id", title));
        builder.withContent(new BytesArray(body), XContentType.JSON);

        FakeRestRequest request = builder.build();
        UpdateRequest updateRequest = RestTaskUpdateAction.updateRequest(request);

        assertEquals(TASK_INDEX, updateRequest.index());
        assertEquals(updateRequest.id(), title);
    }

    public void testUpdateRequestWithEmptyDescription() throws IOException {
        String title = "Task 1";
        String status = "PENDING";

        String body = "{\"title\":\""+ title + "\",\"status\":\"" + status+ "\"}";

        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path + "/" + title);
        builder.withParams(Collections.singletonMap("id", title));
        builder.withContent(new BytesArray(body), XContentType.JSON);

        FakeRestRequest request = builder.build();
        UpdateRequest updateRequest = RestTaskUpdateAction.updateRequest(request);

        assertEquals(TASK_INDEX, updateRequest.index());
        assertEquals(updateRequest.id(), title);
    }

    public void testUpdateRequestWithoutTitle() throws IOException {
        String id = "Task 1";
        String description = "Description of Task 1";
        String status = "PENDING";

        String body = "{\"description\":\""+ description +"\",\"status\":\"" + status+ "\"}";

        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path);
        builder.withParams(Collections.singletonMap("id", id));
        builder.withContent(new BytesArray(body), XContentType.JSON);

        FakeRestRequest request = builder.build();

        UpdateRequest updateRequest = RestTaskUpdateAction.updateRequest(request);
        assertEquals(TASK_INDEX, updateRequest.index());
        assertEquals(updateRequest.id(), id);

    }

    public void testUpdateRequestWithoutStatus() throws IOException {
        String title = "Task 1";
        String description = "Description of Task 1";

        String body = "{\"title\":\""+ title + "\",\"description\":\""+ description +"\"}";

        FakeRestRequest.Builder builder = new FakeRestRequest.Builder(xContentRegistry());
        builder.withPath(this.path + "/" + title);
        builder.withParams(Collections.singletonMap("id", title));
        builder.withContent(new BytesArray(body), XContentType.JSON);

        FakeRestRequest request = builder.build();
        UpdateRequest updateRequest = RestTaskUpdateAction.updateRequest(request);

        assertEquals(TASK_INDEX, updateRequest.index());
        assertEquals(updateRequest.id(), title);
    }

    public void testUpdateRequestWithoutId() {
        FakeRestRequest request = new FakeRestRequest.Builder(xContentRegistry()).build();

        IllegalArgumentException e = expectThrows(IllegalArgumentException.class, () -> RestTaskUpdateAction.updateRequest(request));
        assertEquals("Missing required parameters: id is mandatory", e.getMessage());
    }
}
