package pl.ks.profiling.safepoint.analyzer.commons.shared.jit.parser;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CodeCacheSweeperActivity {
    BigDecimal timeStamp;
}