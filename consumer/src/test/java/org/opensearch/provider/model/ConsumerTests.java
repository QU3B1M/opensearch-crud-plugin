/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.consumer.model;

import org.junit.Before;
import org.opensearch.common.xcontent.XContentFactory;
import org.opensearch.common.xcontent.XContentType;
import org.opensearch.core.common.bytes.BytesArray;
import org.opensearch.core.xcontent.NamedXContentRegistry;
import org.opensearch.core.xcontent.XContent;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentParser;
import org.opensearch.test.OpenSearchTestCase;

import java.nio.charset.StandardCharsets;


public class ConsumerTests extends OpenSearchTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    public void testConsumerDefaultConstructor() {
        Consumer consumer = new Consumer();

        assertNull(consumer.getTitle());
        assertNull(consumer.getDescription());
        assertNull(consumer.getStatus());
    }

    public void testConsumerConstructor() {
        String title = "Consumer 1";
        String description = "Description of Consumer 1";
        Consumer.ConsumerStatus status = Consumer.ConsumerStatus.PENDING;

        Consumer consumer = new Consumer(title, description, status);

        assertEquals(title, consumer.getTitle());
        assertEquals(description, consumer.getDescription());
        assertEquals(status, consumer.getStatus());
    }

    public void testConsumerSetters() {
        String title = "Consumer 1";
        String description = "Description of Consumer 1";
        Consumer.ConsumerStatus status = Consumer.ConsumerStatus.PENDING;

        Consumer consumer = new Consumer();
        consumer.setTitle(title);
        consumer.setDescription(description);
        consumer.setStatus(status);

        assertEquals(title, consumer.getTitle());
        assertEquals(description, consumer.getDescription());
        assertEquals(status, consumer.getStatus());
    }

    public void testConsumerCopyConstructor() {
        String title = "Consumer 1";
        String description = "Description of Consumer 1";
        Consumer.ConsumerStatus status = Consumer.ConsumerStatus.PENDING;

        Consumer consumer = new Consumer(title, description, status);
        Consumer consumerCopy = new Consumer(consumer);

        assertEquals(consumer.getTitle(), consumerCopy.getTitle());
        assertEquals(consumer.getDescription(), consumerCopy.getDescription());
        assertEquals(consumer.getStatus(), consumerCopy.getStatus());
    }

    public void testConsumerToXContent() throws Exception {
        String title = "Consumer 1";
        String description = "Description of Consumer 1";
        Consumer.ConsumerStatus status = Consumer.ConsumerStatus.PENDING;

        Consumer consumer = new Consumer(title, description, status);

        XContentBuilder builder = XContentFactory.jsonBuilder();
        consumer.toXContent(builder, null);

        String expected = "{\"title\":\""+ title + "\",\"description\":\""+ description +"\",\"status\":\"" + status+ "\"}";
        assertEquals(expected, builder.toString());
    }

    public void testConsumerFromXContent() throws Exception {
        String title = "Consumer 1";
        String description = "Description of Consumer 1";
        Consumer.ConsumerStatus status = Consumer.ConsumerStatus.PENDING;

        String json = "{\"title\":\""+ title + "\",\"description\":\""+ description +"\",\"status\":\"" + status+ "\"}";

        BytesArray bytes = new BytesArray(json.getBytes(StandardCharsets.UTF_8));
        XContent content = XContentType.JSON.xContent();
        XContentParser parser = content.createParser(NamedXContentRegistry.EMPTY, null, bytes.array());
        parser.nextToken();

        Consumer consumer = Consumer.fromXContent(parser);
        assertEquals(title, consumer.getTitle());
        assertEquals(description, consumer.getDescription());
        assertEquals(status, consumer.getStatus());
    }

    public void testConsumerFromXContentWithMissingTitle() throws Exception {
        String description = "Description of Consumer 1";
        Consumer.ConsumerStatus status = Consumer.ConsumerStatus.PENDING;

        String json = "{\"description\":\""+ description +"\",\"status\":\"" + status+ "\"}";

        BytesArray bytes = new BytesArray(json.getBytes(StandardCharsets.UTF_8));
        XContent content = XContentType.JSON.xContent();
        XContentParser parser = content.createParser(NamedXContentRegistry.EMPTY, null, bytes.array());
        parser.nextToken();

        Consumer consumer = Consumer.fromXContent(parser);
        assertNull(consumer.getTitle());
        assertEquals(description, consumer.getDescription());
        assertEquals(status, consumer.getStatus());
    }

    public void testConsumerFromXContentWithMissingDescription() throws Exception {
        String title = "Consumer 1";
        Consumer.ConsumerStatus status = Consumer.ConsumerStatus.PENDING;

        String json = "{\"title\":\""+ title + "\",\"status\":\"" + status+ "\"}";

        BytesArray bytes = new BytesArray(json.getBytes(StandardCharsets.UTF_8));
        XContent content = XContentType.JSON.xContent();
        XContentParser parser = content.createParser(NamedXContentRegistry.EMPTY, null, bytes.array());
        parser.nextToken();

        Consumer consumer = Consumer.fromXContent(parser);
        assertEquals(title, consumer.getTitle());
        assertNull(consumer.getDescription());
        assertEquals(status, consumer.getStatus());
    }

    public void testConsumerFromXContentWithMissingStatus() throws Exception {
        String title = "Consumer 1";
        String description = "Description of Consumer 1";

        String json = "{\"title\":\""+ title + "\",\"description\":\""+ description +"\"}";

        BytesArray bytes = new BytesArray(json.getBytes(StandardCharsets.UTF_8));
        XContent content = XContentType.JSON.xContent();
        XContentParser parser = content.createParser(NamedXContentRegistry.EMPTY, null, bytes.array());
        parser.nextToken();

        Consumer consumer = Consumer.fromXContent(parser);
        assertEquals(title, consumer.getTitle());
        assertEquals(description, consumer.getDescription());
        assertNull(consumer.getStatus());
    }

    public void testConsumerFromXContentWithEmptyContent() throws Exception {
        String json = "{}";

        BytesArray bytes = new BytesArray(json.getBytes(StandardCharsets.UTF_8));
        XContent content = XContentType.JSON.xContent();
        XContentParser parser = content.createParser(NamedXContentRegistry.EMPTY, null, bytes.array());
        parser.nextToken();

        Consumer consumer = Consumer.fromXContent(parser);
        assertNull(consumer.getTitle());
        assertNull(consumer.getDescription());
        assertNull(consumer.getStatus());
    }


    public void testEquals() {
        String title = "Consumer 1";
        String description = "Description of Consumer 1";
        Consumer.ConsumerStatus status = Consumer.ConsumerStatus.PENDING;

        Consumer consumer1 = new Consumer(title, description, status);
        Consumer consumer2 = new Consumer(title, description, status);

        assertEquals(consumer1, consumer2);
    }

    public void testNotEquals() {
        String title = "Consumer 1";
        String description = "Description of Consumer 1";
        Consumer.ConsumerStatus status = Consumer.ConsumerStatus.PENDING;

        Consumer consumer1 = new Consumer(title, description, status);
        Consumer consumer2 = new Consumer("Consumer 2", description, status);

        assertNotEquals(consumer1, consumer2);
    }

    public void testHashCode() {
        String title = "Consumer 1";
        String description = "Description of Consumer 1";
        Consumer.ConsumerStatus status = Consumer.ConsumerStatus.PENDING;

        Consumer consumer1 = new Consumer(title, description, status);
        Consumer consumer2 = new Consumer(title, description, status);

        assertEquals(consumer1.hashCode(), consumer2.hashCode());
    }

    public void testToString() {
        String title = "Consumer 1";
        String description = "Description of Consumer 1";
        Consumer.ConsumerStatus status = Consumer.ConsumerStatus.PENDING;

        Consumer consumer = new Consumer(title, description, status);

        String expected = "Consumer{title='Consumer 1', description='Description of Consumer 1', status=PENDING}";
        assertEquals(expected, consumer.toString());
    }
}
