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
package pl.ks.profiling.safepoint.analyzer.commons.shared.stringdedup.page;

import java.text.DecimalFormat;
import java.util.List;
import pl.ks.profiling.gui.commons.Chart;
import pl.ks.profiling.gui.commons.Page;
import pl.ks.profiling.safepoint.analyzer.commons.shared.JvmLogFile;
import pl.ks.profiling.safepoint.analyzer.commons.shared.PageCreator;
import pl.ks.profiling.safepoint.analyzer.commons.shared.stringdedup.parser.StringDedupLogEntry;

public class StringDedupTotal implements PageCreator {
    @Override
    public Page create(JvmLogFile jvmLogFile, DecimalFormat decimalFormat) {
        return Page.builder()
                .menuName("Str. Dedup. total stats")
                .fullName("String Deduplication total stats")
                .info("Following charts show how much memory your application saves due to String Deduplication")
                .icon(Page.Icon.CHART)
                .pageContents(
                        List.of(
                                Chart.builder()
                                        .chartType(Chart.ChartType.LINE)
                                        .title("Total size of deduplicated strings (in Kb)")
                                        .data(getSizeChart(jvmLogFile.getStringDedupLogFile().getEntries()))
                                        .build(),
                                Chart.builder()
                                        .chartType(Chart.ChartType.LINE)
                                        .title("Count of deduplicated strings")
                                        .data(getCountChart(jvmLogFile.getStringDedupLogFile().getEntries()))
                                        .build(),
                                Chart.builder()
                                        .chartType(Chart.ChartType.LINE)
                                        .title("Size (in Kb) of all scanned strings")
                                        .data(getNewSizeChart(jvmLogFile.getStringDedupLogFile().getEntries()))
                                        .build(),
                                Chart.builder()
                                        .chartType(Chart.ChartType.LINE)
                                        .title("Count of all scanned strings")
                                        .data(getNewCountChart(jvmLogFile.getStringDedupLogFile().getEntries()))
                                        .build()
                        )
                )
                .build();
    }

    private static Object[][] getSizeChart(List<StringDedupLogEntry> entries) {
        Object[][] stats = new Object[entries.size() + 1][4];
        stats[0][0] = "Time";
        stats[0][1] = "Total size";
        stats[0][2] = "Young size";
        stats[0][3] = "Old size";
        int i = 1;
        for (StringDedupLogEntry status : entries) {
            stats[i][0] = status.getTimeStamp();
            stats[i][1] = status.getTotalSize();
            stats[i][2] = status.getTotalSizeYoung();
            stats[i][3] = status.getTotalSizeOld();
            i++;
        }
        return stats;
    }

    private static Object[][] getCountChart(List<StringDedupLogEntry> entries) {
        Object[][] stats = new Object[entries.size() + 1][4];
        stats[0][0] = "Time";
        stats[0][1] = "Total count";
        stats[0][2] = "Young count";
        stats[0][3] = "Old count";
        int i = 1;
        for (StringDedupLogEntry status : entries) {
            stats[i][0] = status.getTimeStamp();
            stats[i][1] = status.getTotalCount();
            stats[i][2] = status.getTotalCountYoung();
            stats[i][3] = status.getTotalCountOld();
            i++;
        }
        return stats;
    }

    private static Object[][] getNewCountChart(List<StringDedupLogEntry> entries) {
        Object[][] stats = new Object[entries.size() + 1][2];
        stats[0][0] = "Time";
        stats[0][1] = "New strings count";
        int i = 1;
        for (StringDedupLogEntry status : entries) {
            stats[i][0] = status.getTimeStamp();
            stats[i][1] = status.getTotalCountNew();
            i++;
        }
        return stats;
    }

    private static Object[][] getNewSizeChart(List<StringDedupLogEntry> entries) {
        Object[][] stats = new Object[entries.size() + 1][2];
        stats[0][0] = "Time";
        stats[0][1] = "New strings size";
        int i = 1;
        for (StringDedupLogEntry status : entries) {
            stats[i][0] = status.getTimeStamp();
            stats[i][1] = status.getTotalSizeNew();
            i++;
        }
        return stats;
    }
}
