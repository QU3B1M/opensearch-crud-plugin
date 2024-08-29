/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks.action;

import org.opensearch.action.index.IndexRequest;
import org.opensearch.common.xcontent.XContentFactory;
import org.opensearch.core.xcontent.ToXContent;
import org.opensearch.core.xcontent.XContentParser;
import org.opensearch.rest.RestRequest;
import org.opensearch.tasks.model.Task;

import java.io.IOException;

import static org.opensearch.core.xcontent.XContentParserUtils.ensureExpectedToken;

/**
 * Handles the indexing of tasks in OpenSearch.
 */
public class RestTaskIndexAction {

    /**
     * Creates an IndexRequest for indexing a task.
     *
     * @param request The RestRequest containing the task details.
     * @return The IndexRequest to index the task.
     * @throws IOException If an I/O error occurs.
     */
    public static IndexRequest createIndexRequest(RestRequest request) throws IOException {
        XContentParser parser = request.contentParser();
        ensureExpectedToken(XContentParser.Token.START_OBJECT, parser.nextToken(), parser);
        Task task = Task.fromXContent(parser);

        if (task.getTitle() == null) {
            throw new IllegalArgumentException("Missing required parameters: id is mandatory");
        }

        IndexRequest indexRequest = new IndexRequest(Task.TASK_INDEX);
        indexRequest.id(task.getTitle());
        indexRequest.source(task.toXContent(XContentFactory.jsonBuilder(), ToXContent.EMPTY_PARAMS));
        indexRequest.create(true);

        return indexRequest;
    }
}
