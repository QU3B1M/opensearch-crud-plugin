/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.provider;

import com.wazuh.common.transport.CommandRequest;
import com.wazuh.common.transport.CommandRequestAction;
import com.wazuh.common.transport.CommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opensearch.action.ActionRequest;
import org.opensearch.cluster.metadata.IndexNameExpressionResolver;
import org.opensearch.cluster.node.DiscoveryNode;
import org.opensearch.common.settings.ClusterSettings;
import org.opensearch.common.settings.IndexScopedSettings;
import org.opensearch.common.settings.Settings;
import org.opensearch.common.settings.SettingsFilter;
import org.opensearch.core.action.ActionResponse;
import org.opensearch.plugins.ActionPlugin;
import org.opensearch.plugins.ClusterPlugin;
import org.opensearch.plugins.Plugin;
import org.opensearch.provider.action.TransportCommandRequestAction;
import org.opensearch.rest.RestController;
import org.opensearch.rest.RestHandler;
import org.opensearch.provider.handler.RestTaskHandler;

import java.util.List;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;

/**
 * The TasksPlugin class registers REST endpoints for OpenSearch.
 */
public class TasksPlugin extends Plugin implements ActionPlugin, ClusterPlugin {

    private static final Logger log = LogManager.getLogger(TasksPlugin.class);
    /**
     * Registers REST handlers.
     *
     * @param settings OpenSearch settings
     * @param restController REST controller
     * @param clusterSettings Cluster settings
     * @param indexScopedSettings Index scoped settings
     * @param settingsFilter Settings filter
     * @param indexNameExpressionResolver Index name expression resolver
     * @param nodesInCluster Supplier for nodes in cluster
     * @return List of REST handlers
     */
    @Override
    public List<RestHandler> getRestHandlers(final Settings settings,
                                             final RestController restController,
                                             final ClusterSettings clusterSettings,
                                             final IndexScopedSettings indexScopedSettings,
                                             final SettingsFilter settingsFilter,
                                             final IndexNameExpressionResolver indexNameExpressionResolver,
                                             final Supplier nodesInCluster) {
        return singletonList(new RestTaskHandler());
    }

  @Override
  public List<ActionHandler<? extends ActionRequest, ? extends ActionResponse>> getActions() {
    log.error("CommandRequestAction instance: {}", CommandRequestAction.INSTANCE);
    return List.of(
        new ActionHandler<>(CommandRequestAction.INSTANCE, TransportCommandRequestAction.class)
    );
  }

  @Override
    public void onNodeStarted(DiscoveryNode localNode) {
      CommandRequest test;
      CommandResponse bla;
    }

}
