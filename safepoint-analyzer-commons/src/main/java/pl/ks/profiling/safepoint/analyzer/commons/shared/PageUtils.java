/*
 * Copyright 2020 Artur Owczarek
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.ks.profiling.safepoint.analyzer.commons.shared;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class PageUtils {
    public static <T> Object[][] toMatrix(Collection<T> elements, List<String> columnNames, List<Function<T, Object>> valueExtractors) {
        int numberOfColumns = columnNames.size();
        if(numberOfColumns != valueExtractors.size()) {
            throw new IllegalArgumentException("Number of columns(" + numberOfColumns + ") is different than number of extracting functions (" + valueExtractors.size() + ")");
        }

        Object[][] stats = new Object[elements.size() + 1][numberOfColumns];
        setHeaders(stats, columnNames);

        int itemIndex = 1;
        for (T status : elements) {
            int columnIndex = 0;
            for (Function<T, Object> valueExtractor : valueExtractors) {
                stats[itemIndex][columnIndex] = valueExtractor.apply(status);
                columnIndex++;
            }
            itemIndex++;
        }
        return stats;
    }

    private static void setHeaders(Object[][] stats, List<String> columnNames) {
        int columnIndex = 0;
        for(String column: columnNames) {
            stats[0][columnIndex] = column;
            columnIndex++;
        }
    }
}