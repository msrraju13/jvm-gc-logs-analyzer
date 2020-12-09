package pl.ks.profiling.safepoint.analyzer.commons.shared.gc.parser;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import pl.ks.profiling.safepoint.analyzer.commons.shared.OneFiledAllStats;

@Getter
@Setter
public class GCAgingSummary {
    private Map<Integer, Double> survivedRatio = new HashMap<>();
    private Map<Integer, OneFiledAllStats> agingSizes = new HashMap<>();
}