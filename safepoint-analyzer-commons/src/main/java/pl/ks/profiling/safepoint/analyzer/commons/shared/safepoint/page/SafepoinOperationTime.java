/*
 * Copyright 2020 Krzysztof Slusarski
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
package pl.ks.profiling.safepoint.analyzer.commons.shared.safepoint.page;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import pl.ks.profiling.gui.commons.Chart;
import pl.ks.profiling.gui.commons.Page;
import pl.ks.profiling.safepoint.analyzer.commons.shared.JvmLogFile;
import pl.ks.profiling.safepoint.analyzer.commons.shared.PageCreator;
import pl.ks.profiling.safepoint.analyzer.commons.shared.safepoint.parser.SafepointOperationStats;
import pl.ks.profiling.safepoint.analyzer.commons.shared.safepoint.parser.SafepointOperationStatsByName;

public class SafepoinOperationTime implements PageCreator {
    @Override
    public Page create(JvmLogFile jvmLogFile, DecimalFormat decimalFormat) {
        SafepointOperationStats soStats = jvmLogFile.getSafepointLogFile().getSafepointOperationStats();
        return Page.builder()
                .menuName("Safepoint operation time")
                .fullName("Safepoint operation time + avg(TTS)")
                .icon(Page.Icon.CHART)
                .pageContents(List.of(
                        Chart.builder()
                                .title("Safepoint operation time + avg(TTS)")
                                .info("Total time that your JVM wasted on Stop-the-world phases splited by Safepoint operation that caused it.")
                                .chartType(Chart.ChartType.PIE)
                                .data(getChart(soStats))
                                .build()
                ))
                .build();
    }

    private static Object[][] getChart(SafepointOperationStats safepointOperationStats) {
        Set<SafepointOperationStatsByName> statsByName = safepointOperationStats.getStatsByNames();
        Object[][] stats = new Object[statsByName.size() + 1][2];
        stats[0][0] = "Operation name";
        stats[0][1] = "Time with TTS";
        int i = 1;
        for (SafepointOperationStatsByName statByName : statsByName) {
            stats[i][0] = statByName.getOperationName();
            stats[i][1] = statByName.getOperationTime().getTotal()
                    .add(safepointOperationStats.getTts().getAverage().multiply(new BigDecimal(statByName.getCount())));
            i++;
        }
        return stats;
    }
}
