/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks;

import org.opensearch.cluster.metadata.IndexNameExpressionResolver;
import org.opensearch.common.settings.ClusterSettings;
import org.opensearch.common.settings.IndexScopedSettings;
import org.opensearch.common.settings.Settings;
import org.opensearch.common.settings.SettingsFilter;
import org.opensearch.plugins.ActionPlugin;
import org.opensearch.plugins.Plugin;
import org.opensearch.rest.RestController;
import org.opensearch.rest.RestHandler;
import org.opensearch.tasks.handler.RestTaskHandler;

import java.util.List;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;

/**
 * The TasksPlugin class registers REST endpoints for OpenSearch.
 */
public class TasksPlugin extends Plugin implements ActionPlugin {

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
}
