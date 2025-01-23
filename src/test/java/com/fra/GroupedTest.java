package com.fra;

import com.fra.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupedTest {

    @Test
    void test1() {

        List<TradeRatioEntity> entities = List.of(
                new TradeRatioEntity("USD", "SELL", "3m", "2025-01-24", "1m", "12", "24"),
                new TradeRatioEntity("USD", "SELL", "3m", "2025-01-24", "2m", "13", "24"),
                new TradeRatioEntity("USD", "SELL", "3m", "2025-01-24", "3m", "14", "24"),
                new TradeRatioEntity("USD", "SELL", "6m", "2025-01-24", "1m", "12", "24"),
                new TradeRatioEntity("USD", "SELL", "6m", "2025-01-24", "2m", "12", "24"),
                new TradeRatioEntity("USD", "SELL", "6m", "2025-01-24", "3m", "12", "24"),
                new TradeRatioEntity("USD", "BUY", "3m", "2025-01-24", "1m", "12", "24"),
                new TradeRatioEntity("USD", "BUY", "3m", "2025-01-24", "2m", "12", "24"),
                new TradeRatioEntity("USD", "BUY", "3m", "2025-01-24", "3m", "12", "24"),
                new TradeRatioEntity("USD", "BUY", "6m", "2025-01-24", "1m", "12", "24"),
                new TradeRatioEntity("USD", "BUY", "6m", "2025-01-24", "2m", "12", "24"),
                new TradeRatioEntity("USD", "BUY", "6m", "2025-01-24", "3m", "12", "24"),
                new TradeRatioEntity("USD", "BUY", "12m", "2025-01-24", "3m", "12", "24")
        );

        TradeRatioDep1 tradeRatioDep1 = TradeRatioDep1.of(entities);


        String json = JsonUtil.getInstance().toJson(tradeRatioDep1);
        System.out.println(json);

    }

    @Data
    @ToString
    @AllArgsConstructor
    static class TradeRatioEntity {
        private String currencyType;
        private String tradeType;
        private String tradePeriod;
        private String baseDate;
        private String targetMonth;
        private String ratio;
        private String rate;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @Builder
    static class TradeRatioDep1 {
        List<TradeRatioDep2> tradeRatioDep2;

        @Data
        @ToString
        @AllArgsConstructor
        @Builder
        static class TradeRatioDep2 {
            private String currencyType;
            private String tradeType;
            private String tradePeriod;
            private String baseDate;
            private List<TradeRatioDep3> tradeRatioDep3;

            @Data
            @ToString
            @AllArgsConstructor
            @Builder
            static class TradeRatioDep3 {
                private String targetMonth;
                private String ratio;
                private String rate;

                public static TradeRatioDep3 of(TradeRatioEntity dto) {
                    return TradeRatioDep3.builder()
                            .targetMonth(dto.targetMonth)
                            .ratio(dto.ratio)
                            .rate(dto.rate)
                            .build();
                }

            }

            public static TradeRatioDep2 of(List<TradeRatioEntity> dto) {
                TradeRatioEntity en = dto.get(0);
                return TradeRatioDep2.builder()
                        .currencyType(en.currencyType)
                        .tradeType(en.tradeType)
                        .tradePeriod(en.tradePeriod)
                        .baseDate(en.baseDate)
                        .tradeRatioDep3(dto.stream().map(TradeRatioDep3::of).toList())
                        .build();
            }


        }

        public static TradeRatioDep1 of(List<TradeRatioEntity> entities) {

//            List<List<TradeRatioEntity>> dtos = new ArrayList<>();
//            Map<String, List<TradeRatioEntity>> groupDep1 = entities.stream().collect(Collectors.groupingBy(s -> s.tradeType));
//            groupDep1.values().forEach(s -> {
//                Map<String, List<TradeRatioEntity>> groupDep2 = s.stream().collect(Collectors.groupingBy(ss -> ss.tradePeriod));
//                dtos.addAll(groupDep2.values());
//            });

            List<List<TradeRatioEntity>> dtos = entities.stream()
                    .collect(Collectors.groupingBy(TradeRatioEntity::getTradeType)) // Group by tradeType
                    .values().stream()
                    .flatMap(tradeTypeGroup ->
                            tradeTypeGroup.stream()
                                    .collect(Collectors.groupingBy(TradeRatioEntity::getTradePeriod)) // Group by tradePeriod
                                    .values().stream()
                    )
                    .toList();

            return TradeRatioDep1.builder()
                    .tradeRatioDep2(dtos.stream().map(TradeRatioDep2::of).toList())
                    .build();
        }

    }



}
