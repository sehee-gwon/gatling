package com.example.gatling.infrastructure.util.tags;

import com.example.gatling.infrastructure.util.RandomSheetUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SvgTag extends SingleTag {
    private Element fillColorMap;
    private Element shadow;
    private Element svgLayer;

    public static final int FILL_COLOR_MAX = 5;

    public SvgTag(Document doc) {
        super(doc, "SVG");
    }

    public void addFillColorMap(Document doc) {
        this.fillColorMap = doc.createElement("fillColorMap");
        for (int i=0; i<FILL_COLOR_MAX; i++) {
            Element fillColor = doc.createElement("fillColor");
            fillColor.setAttribute("originColor", RandomSheetUtil.randColorCode());
            fillColor.setAttribute("color", RandomSheetUtil.randColorCode());
            this.fillColorMap.appendChild(fillColor);
        }
        this.root.appendChild(this.fillColorMap);
    }

    public void addShadow(Document doc) {
        this.shadow = doc.createElement("Shadow");
        this.shadow.setAttribute("IsShadow", String.valueOf(RandomSheetUtil.rand()));
        this.shadow.setAttribute("Color", RandomSheetUtil.randColorCode());
        this.shadow.setAttribute("Angle", "");
        this.shadow.setAttribute("Alpha", "");
        this.shadow.setAttribute("Distance", "");
        this.shadow.setAttribute("Blur", "");
        this.root.appendChild(this.shadow);
    }

    public void addSvgLayer(Document doc) {
        this.svgLayer = doc.createElement("SvgLayer");
        this.svgLayer.setAttribute("layerId", "TESTLAYERS");
        this.svgLayer.setAttribute("left", "");
        this.svgLayer.setAttribute("top", "");
        this.svgLayer.setAttribute("scaleX", String.valueOf(RandomSheetUtil.rand(0, 2)));
        this.svgLayer.setAttribute("scaleY", String.valueOf(RandomSheetUtil.rand(0, 2)));
        this.svgLayer.setAttribute("scaleCommon", String.valueOf(RandomSheetUtil.rand(0, 2)));
        this.svgLayer.setAttribute("layerLeft", String.valueOf(RandomSheetUtil.rand(0, 2)));
        this.svgLayer.setAttribute("layerTop", String.valueOf(RandomSheetUtil.rand(0, 2)));
        this.svgLayer.setAttribute("layerWidth", "");
        this.svgLayer.setAttribute("layerHeight", "");
        this.svgLayer.setAttribute("moveOffsetX", String.valueOf(RandomSheetUtil.rand(0, 2)));
        this.svgLayer.setAttribute("moveOffsetY", String.valueOf(RandomSheetUtil.rand(0, 2)));
        this.svgLayer.setAttribute("extendOffsetX", String.valueOf(RandomSheetUtil.rand(0, 2)));
        this.svgLayer.setAttribute("extendOffsetY", String.valueOf(RandomSheetUtil.rand(0, 2)));
        this.svgLayer.setAttribute("type", "NO_PATTERN");
        this.root.appendChild(this.svgLayer);
    }
}
