/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StaticUtils {

    private StaticUtils() {}

    public static List<Integer> range(int start, int end) {
        return IntStream.range(start, end)
                .boxed()
                .collect(Collectors.toList());
    }// testImplementation 'org.mockito:mockito-junit-jupiter:5.11.0'


    public static String name() {
        return "TEST";
    }
}