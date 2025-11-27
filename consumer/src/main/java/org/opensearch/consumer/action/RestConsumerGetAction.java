/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.consumer.action;

import org.opensearch.action.get.GetRequest;
import org.opensearch.rest.RestRequest;
import org.opensearch.search.fetch.subphase.FetchSourceContext;
import org.opensearch.consumer.model.Consumer;

/**
 * Handles the retrieval of consumer in OpenSearch.
 */
public class RestConsumerGetAction {

    /**
     * Creates a GetRequest for retrieving a consumer.
     *
     * @param request The RestRequest containing the consumer ID.
     * @return The GetRequest to retrieve the consumer.
     */
    public static GetRequest getRequest(RestRequest request) {
        String consumerId = request.param("id");
        if (consumerId == null) {
            throw new IllegalArgumentException("Missing required parameters: id is mandatory");
        }
        GetRequest getRequest = new GetRequest(Consumer.TASK_INDEX, consumerId);
        getRequest.fetchSourceContext(FetchSourceContext.parseFromRestRequest(request));

        return getRequest;
    }
}
