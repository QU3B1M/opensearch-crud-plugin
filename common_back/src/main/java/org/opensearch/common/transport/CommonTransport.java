/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.common.transport;

import org.opensearch.action.ActionType;
import org.opensearch.ml.common.transport.MLTaskResponse;

public class CommonTransport extends ActionType<MLTaskResponse> {
  public static final CommonTransport INSTANCE = new CommonTransport();
  public static final String NAME = "cluster:admin/opensearch/common/transport";

  private CommonTransport() {
    super(NAME, MLTaskResponse::new);
  }
}