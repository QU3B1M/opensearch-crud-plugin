/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.consumer.action;

import org.opensearch.action.index.IndexRequest;
import org.opensearch.common.xcontent.XContentFactory;
import org.opensearch.core.xcontent.ToXContent;
import org.opensearch.core.xcontent.XContentParser;
import org.opensearch.rest.RestRequest;
import org.opensearch.consumer.model.Consumer;

import java.io.IOException;

import static org.opensearch.core.xcontent.XContentParserUtils.ensureExpectedToken;

/**
 * Handles the indexing of consumer in OpenSearch.
 */
public class RestConsumerIndexAction {

    /**
     * Creates an IndexRequest for indexing a consumer.
     *
     * @param request The RestRequest containing the consumer details.
     * @return The IndexRequest to index the consumer.
     * @throws IOException If an I/O error occurs.
     */
    public static IndexRequest createIndexRequest(RestRequest request) throws IOException {
        XContentParser parser = request.contentParser();
        ensureExpectedToken(XContentParser.Token.START_OBJECT, parser.nextToken(), parser);
        Consumer consumer = Consumer.fromXContent(parser);

        if (consumer.getTitle() == null) {
            throw new IllegalArgumentException("Missing required parameters: title is mandatory");
        }
        if (consumer.getDescription() == null) {
            consumer.setDescription("");
        }
        if (consumer.getStatus() == null) {
            consumer.setStatus(Consumer.ConsumerStatus.PENDING);
        }

        IndexRequest indexRequest = new IndexRequest(Consumer.TASK_INDEX);
        indexRequest.id(consumer.getTitle());
        indexRequest.source(consumer.toXContent(XContentFactory.jsonBuilder(), ToXContent.EMPTY_PARAMS));
        indexRequest.create(true);

        return indexRequest;
    }
}
