/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.consumer.handler;

import com.wazuh.common.transport.CommandRequest;
import com.wazuh.common.transport.CommandRequestAction;
import org.opensearch.action.ActionRequest;
import org.opensearch.transport.client.node.NodeClient;
import org.opensearch.rest.BaseRestHandler;
import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestRequest;
import org.opensearch.core.rest.RestStatus;

import java.util.List;

import static org.opensearch.rest.RestRequest.Method.*;

/**
 * Handles REST actions for consumer in OpenSearch.
 */
public class RestConsumerHandler extends BaseRestHandler {

    public static final String BASE_URI = "/_plugins/consumer";

    /**
     * Returns the name of the handler.
     *
     * @return The name of the handler.
     */
    @Override
    public String getName() {
        return "rest_consumer_action";
    }

    /**
     * Defines the routes handled by this handler.
     *
     * @return A list of routes.
     */
    @Override
    public List<Route> routes() {
        return List.of(new Route(POST, BASE_URI));
    }

    /**
     * Prepares the request for execution based on the HTTP method.
     *
     * @param request The RestRequest containing the details of the request.
     * @param client The NodeClient to execute the request.
     * @return A RestChannelConsumer to handle the request.
     */
    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) {
        try {
            switch (request.method()) {
                case POST:
                    String jsonBody = "{\"field\": \"value\"}";
                    ActionRequest actionRequest = new CommandRequest(jsonBody);
                    logger.error("CommandRequestAction instance: {}", CommandRequestAction.INSTANCE);
                    client.execute(CommandRequestAction.INSTANCE, actionRequest);
                    return channel -> channel.sendResponse(new BytesRestResponse(RestStatus.OK, "Command executed"));
                default:
                    throw new IllegalArgumentException("Unsupported method: " + request.method());
            }
        } catch (Exception e) {
            return channel -> channel.sendResponse(new BytesRestResponse(channel, e));
        }
    }
}
