/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.consumer.action;

import org.opensearch.action.delete.DeleteRequest;
import org.opensearch.rest.RestRequest;
import org.opensearch.consumer.model.Consumer;

/**
 * Handles the deletion of consumer in OpenSearch.
 */
public class RestConsumerDeleteAction {

    /**
     * Creates a DeleteRequest for deleting a consumer.
     *
     * @param request The RestRequest containing the consumer ID.
     * @return The DeleteRequest to delete the consumer.
     */
    public static DeleteRequest deleteRequest(RestRequest request) {
        String consumerId = request.param("id");
        if (consumerId == null) {
            throw new IllegalArgumentException("Missing required parameters: id is mandatory");
        }

        return new DeleteRequest(Consumer.TASK_INDEX, consumerId);
    }
}
