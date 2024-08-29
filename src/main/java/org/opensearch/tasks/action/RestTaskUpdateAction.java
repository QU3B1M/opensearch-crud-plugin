/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks.action;

import org.opensearch.action.update.UpdateRequest;
import org.opensearch.common.xcontent.XContentFactory;
import org.opensearch.core.xcontent.ToXContent;
import org.opensearch.core.xcontent.XContentParser;
import org.opensearch.rest.RestRequest;
import org.opensearch.tasks.model.Task;

import java.io.IOException;

import static org.opensearch.core.xcontent.XContentParserUtils.ensureExpectedToken;

/**
 * Handles the updating of tasks in OpenSearch.
 */
public class RestTaskUpdateAction {

    /**
     * Creates an UpdateRequest for updating a task.
     *
     * @param request The RestRequest containing the task details.
     * @return The UpdateRequest to update the task.
     * @throws IOException If an I/O error occurs.
     */
    public static UpdateRequest updateRequest(RestRequest request) throws IOException {
        XContentParser parser = request.contentParser();
        ensureExpectedToken(XContentParser.Token.START_OBJECT, parser.nextToken(), parser);
        Task task = Task.fromXContent(parser);

        UpdateRequest updateRequest = new UpdateRequest(Task.TASK_INDEX, request.param("id"));
        updateRequest.doc(task.toXContent(XContentFactory.jsonBuilder(), ToXContent.EMPTY_PARAMS));
        updateRequest.fetchSource(true);

        return updateRequest;
    }
}
