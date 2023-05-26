package com.example.gatling.infrastructure.utils;

import com.example.gatling.design.domain.DesignMetaData;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.design.domain.enumeration.Action;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DesignMetaDataMaker {
    public static DesignMetaData createDesignMetaData(long designIdx, long teamIdx,
                                                      long accountId, String accountName,
                                                      List<Sheet> sheets) {
        int width = RandomUtils.randNumber(100, 10001);
        int height = RandomUtils.randNumber(100, 10001);
        String sheetKey = "sheetkey" + RandomUtils.randNumber(10000000, 20000000);
        String thumbnailKey = "thumbnailKey" + RandomUtils.randNumber(10000000, 20000000);

        DesignMetaData.DesignPage firstPage = DesignMetaData.DesignPage.builder()
                .idx(RandomUtils.randNumber(10000000, 20000000))
                .sheetKey(sheetKey)
                .thumbnailKey(thumbnailKey)
                .width(width)
                .height(height)
                .designPageNo(0)
                .sheetAbsPath(sheetKey + "/sheet.xml")
                .thumbnailAbsPath(thumbnailKey + "/thumb.png")
                .sheetUrl("")
                .smallThumbnailUrl("")
                .originThumbnailUrl("")
                .build();

        List<DesignMetaData.DesignPageResponse> pageList = new ArrayList<>();

        for (int i=0; i<sheets.size(); i++) {
            DesignMetaData.DesignPageResponse page = DesignMetaData.DesignPageResponse.builder()
                    .idx(i == 0 ? firstPage.getIdx() : RandomUtils.randNumber(10000000, 20000000))
                    .sheetKey(i == 0 ? sheetKey : "sheetkey" + RandomUtils.randNumber(10000000, 20000000))
                    .thumbKey(i == 0 ? thumbnailKey : "thumbnailKey" + RandomUtils.randNumber(10000000, 20000000))
                    .width(i == 0 ? width : RandomUtils.randNumber(100, 10001))
                    .height(i == 0 ? height : RandomUtils.randNumber(100, 10001))
                    .sheetUrl("")
                    .thumb300Url("new_anonymous_thumb.png")
                    .thumbOriginUrl("new_anonymous_thumb.png")
                    .build();

            pageList.add(page);
        }

        DesignMetaData metaData = DesignMetaData.builder()
                .idx(designIdx)
                .title("제목" + RandomUtils.randAlphabet())
                .status("ACTIVE")
                .accountId(accountId)
                .accountName(accountName)
                .profileImageUrl("image url")
                .createDateOffset(RandomUtils.randNumber(500, 10001))
                .createDate(Timestamp.from(Instant.now()))
                .updateDate(Timestamp.from(Instant.now()))
                .sizeOption("{\"width\":" + width + ",\"height\":" + height + "," +
                        "\"unit\":\"PX\",\"dpi\":" + RandomUtils.randNumber(1, 101) + "}")
                .latestDesignHistoryIdx(RandomUtils.randNumber(10000000, 20000000))
                .templateTypeId("custom_size")
                .pageCount(sheets.size())
                .firstPage(firstPage)
                .pageList(pageList)
                .rawData("{\"version\":\"1.10.52hotfix2\",\"launcherParam\":null,\"pageNumberConfig\":{\"usePageNumber\":false,\"alignOption\":\"BOTTOM_RIGHT\",\"jumpFirstPage\":false,\"startNumber\":1,\"shouldApplyStyleToAllPageNumber\":true}}")
                .teamIdx(teamIdx)
                .teamScope("INDIVIDUAL")
                .editable(RandomUtils.randBoolean())
                .updatable(RandomUtils.randBoolean())
                .actions(new ArrayList<>(EnumSet.allOf(Action.class)))
                .designType("USER_DESIGN")
                .designDocument("{\"tooltips\":0,\"editorType\":\"" + (RandomUtils.randBoolean() ? "EDITOR" : "VIDEO") + "\"}")
                .build();

        return metaData;
    }
}
