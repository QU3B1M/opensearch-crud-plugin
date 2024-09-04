/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks.model;

import org.junit.Before;
import org.opensearch.common.xcontent.XContentFactory;
import org.opensearch.common.xcontent.XContentType;
import org.opensearch.core.common.bytes.BytesArray;
import org.opensearch.core.xcontent.NamedXContentRegistry;
import org.opensearch.core.xcontent.XContent;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentParser;
import org.opensearch.tasks.handler.RestTaskHandler;
import org.opensearch.test.OpenSearchTestCase;
import org.opensearch.test.rest.FakeRestRequest;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class TaskTests extends OpenSearchTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    public void testTaskDefaultConstructor() {
        Task task = new Task();

        assertNull(task.getTitle());
        assertNull(task.getDescription());
        assertNull(task.getStatus());
    }

    public void testTaskConstructor() {
        String title = "Task 1";
        String description = "Description of Task 1";
        Task.TaskStatus status = Task.TaskStatus.PENDING;

        Task task = new Task(title, description, status);

        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(status, task.getStatus());
    }

    public void testTaskSetters() {
        String title = "Task 1";
        String description = "Description of Task 1";
        Task.TaskStatus status = Task.TaskStatus.PENDING;

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);

        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(status, task.getStatus());
    }

    public void testTaskCopyConstructor() {
        String title = "Task 1";
        String description = "Description of Task 1";
        Task.TaskStatus status = Task.TaskStatus.PENDING;

        Task task = new Task(title, description, status);
        Task taskCopy = new Task(task);

        assertEquals(task.getTitle(), taskCopy.getTitle());
        assertEquals(task.getDescription(), taskCopy.getDescription());
        assertEquals(task.getStatus(), taskCopy.getStatus());
    }

    public void testTaskToXContent() throws Exception {
        String title = "Task 1";
        String description = "Description of Task 1";
        Task.TaskStatus status = Task.TaskStatus.PENDING;

        Task task = new Task(title, description, status);

        XContentBuilder builder = XContentFactory.jsonBuilder();
        task.toXContent(builder, null);

        String expected = "{\"title\":\""+ title + "\",\"description\":\""+ description +"\",\"status\":\"" + status+ "\"}";
        assertEquals(expected, builder.toString());
    }

    public void testTaskFromXContent() throws Exception {
        String title = "Task 1";
        String description = "Description of Task 1";
        Task.TaskStatus status = Task.TaskStatus.PENDING;

        String json = "{\"title\":\""+ title + "\",\"description\":\""+ description +"\",\"status\":\"" + status+ "\"}";

        BytesArray bytes = new BytesArray(json.getBytes(StandardCharsets.UTF_8));
        XContent content = XContentType.JSON.xContent();
        XContentParser parser = content.createParser(NamedXContentRegistry.EMPTY, null, bytes.array());
        parser.nextToken();

        Task task = Task.fromXContent(parser);
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(status, task.getStatus());
    }

    public void testTaskFromXContentWithMissingTitle() throws Exception {
        String description = "Description of Task 1";
        Task.TaskStatus status = Task.TaskStatus.PENDING;

        String json = "{\"description\":\""+ description +"\",\"status\":\"" + status+ "\"}";

        BytesArray bytes = new BytesArray(json.getBytes(StandardCharsets.UTF_8));
        XContent content = XContentType.JSON.xContent();
        XContentParser parser = content.createParser(NamedXContentRegistry.EMPTY, null, bytes.array());
        parser.nextToken();

        Task task = Task.fromXContent(parser);
        assertNull(task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(status, task.getStatus());
    }

    public void testTaskFromXContentWithMissingDescription() throws Exception {
        String title = "Task 1";
        Task.TaskStatus status = Task.TaskStatus.PENDING;

        String json = "{\"title\":\""+ title + "\",\"status\":\"" + status+ "\"}";

        BytesArray bytes = new BytesArray(json.getBytes(StandardCharsets.UTF_8));
        XContent content = XContentType.JSON.xContent();
        XContentParser parser = content.createParser(NamedXContentRegistry.EMPTY, null, bytes.array());
        parser.nextToken();

        Task task = Task.fromXContent(parser);
        assertEquals(title, task.getTitle());
        assertNull(task.getDescription());
        assertEquals(status, task.getStatus());
    }

    public void testTaskFromXContentWithMissingStatus() throws Exception {
        String title = "Task 1";
        String description = "Description of Task 1";

        String json = "{\"title\":\""+ title + "\",\"description\":\""+ description +"\"}";

        BytesArray bytes = new BytesArray(json.getBytes(StandardCharsets.UTF_8));
        XContent content = XContentType.JSON.xContent();
        XContentParser parser = content.createParser(NamedXContentRegistry.EMPTY, null, bytes.array());
        parser.nextToken();

        Task task = Task.fromXContent(parser);
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertNull(task.getStatus());
    }

    public void testTaskFromXContentWithEmptyContent() throws Exception {
        String json = "{}";

        BytesArray bytes = new BytesArray(json.getBytes(StandardCharsets.UTF_8));
        XContent content = XContentType.JSON.xContent();
        XContentParser parser = content.createParser(NamedXContentRegistry.EMPTY, null, bytes.array());
        parser.nextToken();

        Task task = Task.fromXContent(parser);
        assertNull(task.getTitle());
        assertNull(task.getDescription());
        assertNull(task.getStatus());
    }


    public void testEquals() {
        String title = "Task 1";
        String description = "Description of Task 1";
        Task.TaskStatus status = Task.TaskStatus.PENDING;

        Task task1 = new Task(title, description, status);
        Task task2 = new Task(title, description, status);

        assertEquals(task1, task2);
    }

    public void testNotEquals() {
        String title = "Task 1";
        String description = "Description of Task 1";
        Task.TaskStatus status = Task.TaskStatus.PENDING;

        Task task1 = new Task(title, description, status);
        Task task2 = new Task("Task 2", description, status);

        assertNotEquals(task1, task2);
    }

    public void testHashCode() {
        String title = "Task 1";
        String description = "Description of Task 1";
        Task.TaskStatus status = Task.TaskStatus.PENDING;

        Task task1 = new Task(title, description, status);
        Task task2 = new Task(title, description, status);

        assertEquals(task1.hashCode(), task2.hashCode());
    }

    public void testToString() {
        String title = "Task 1";
        String description = "Description of Task 1";
        Task.TaskStatus status = Task.TaskStatus.PENDING;

        Task task = new Task(title, description, status);

        String expected = "Task{title='Task 1', description='Description of Task 1', status=PENDING}";
        assertEquals(expected, task.toString());
    }
}
