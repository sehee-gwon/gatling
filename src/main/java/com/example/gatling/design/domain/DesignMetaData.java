package com.example.gatling.design.domain;

import com.example.gatling.design.domain.enumeration.Action;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class DesignMetaData {
    private long idx;
    private String title;
    private String status;
    private long accountId;
    private String accountName;
    private String profileImageUrl;
    private long createDateOffset;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String sizeOption;
    private long latestDesignHistoryIdx;
    private String templateTypeId;
    private int pageCount;
    private DesignPage firstPage;
    private List<DesignPageResponse> pageList;
    private String rawData;
    private Long teamIdx;
    private String teamScope;
    private boolean editable;
    private boolean updatable;
    private List<Action> actions;
    private String designType;
    private String designDocument;

    @Builder
    public DesignMetaData(
            long idx, String title, String status, long accountId, String accountName,
            String profileImageUrl, long createDateOffset, Timestamp createDate, Timestamp updateDate,
            String sizeOption, long latestDesignHistoryIdx, String templateTypeId, int pageCount,
            DesignPage firstPage, List<DesignPageResponse> pageList, String rawData, Long teamIdx, String teamScope,
            boolean editable, boolean updatable, List<Action> actions,
            String designType, String designDocument) {
        this.idx = idx;
        this.title = title;
        this.status = status;
        this.accountId = accountId;
        this.accountName = accountName;
        this.profileImageUrl = profileImageUrl;
        this.createDateOffset = createDateOffset;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.sizeOption = sizeOption;
        this.latestDesignHistoryIdx = latestDesignHistoryIdx;
        this.templateTypeId = templateTypeId;
        this.pageCount = pageCount;
        this.firstPage = firstPage;
        this.pageList = pageList;
        this.rawData = rawData;
        this.teamIdx = teamIdx;
        this.teamScope = teamScope;
        this.editable = editable;
        this.updatable = updatable;
        this.actions = actions;
        this.designType = designType;
        this.designDocument = designDocument;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    public static class DesignPage {
        private long idx;
        private String sheetKey = "";
        private String thumbnailKey = "";
        private int width;
        private int height;
        private int designPageNo;
        private String templateKey;
        private Integer templatePageNo;
        private Timestamp createDate;
        private String sheetAbsPath = "";
        private String thumbnailAbsPath = "";
        private String sheetUrl = "";
        private String smallThumbnailUrl = "";
        private String originThumbnailUrl = "";

        @Builder
        public DesignPage(
                long idx, String sheetKey, String thumbnailKey, int width, int height, int designPageNo,
                String templateKey, Integer templatePageNo, Timestamp createDate, String sheetAbsPath, String thumbnailAbsPath,
                String sheetUrl, String smallThumbnailUrl, String originThumbnailUrl) {
            this.idx = idx;
            this.sheetKey = sheetKey;
            this.thumbnailKey = thumbnailKey;
            this.width = width;
            this.height = height;
            this.designPageNo = designPageNo;
            this.templateKey = templateKey;
            this.templatePageNo = templatePageNo;
            this.createDate = createDate;
            this.sheetAbsPath = sheetAbsPath;
            this.thumbnailAbsPath = thumbnailAbsPath;
            this.sheetUrl = sheetUrl;
            this.smallThumbnailUrl = smallThumbnailUrl;
            this.originThumbnailUrl = originThumbnailUrl;
        }
    }

    @Getter
    @ToString
    @NoArgsConstructor
    public static class DesignPageResponse {
        private long idx;
        private String sheetKey;
        private String thumbKey;
        private int width;
        private int height;
        private String sheetUrl;
        private String thumb300Url;
        private String thumbOriginUrl;

        @Builder
        public DesignPageResponse(
                long idx, String sheetKey, String thumbKey, int width, int height,
                String sheetUrl, String thumb300Url, String thumbOriginUrl) {
            this.idx = idx;
            this.sheetKey = sheetKey;
            this.thumbKey = thumbKey;
            this.width = width;
            this.height = height;
            this.sheetUrl = sheetUrl;
            this.thumb300Url = thumb300Url;
            this.thumbOriginUrl = thumbOriginUrl;
        }
    }
}
