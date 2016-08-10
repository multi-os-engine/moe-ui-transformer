<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
                exclude-result-prefixes="osxui">
    <xsl:strip-space elements="tableView" />
    <xsl:output method="xml" indent="yes"/>


    <xsl:template match="com.android.sdklib.widgets.iOSTableView"
                  xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:xrt="http://schemas.android.com/apk/res/android"
                  xmlns:esi="http://www.edge-delivery.org/esi/1.0">
        <!-- android namespace: xmlns:android="http://schemas.android.com/apk/res/android"  -->
        <!-- exclude xmlns attribute: xmlns:esi="http://www.edge-delivery.org/esi/1.0" -->

        <xsl:element name="tableView">

            <xsl:variable name="styleId" select="@style" />
            <xsl:variable name="tabBarStyles" select="document($g_stylePath)//style[@name = osxui:StyleIdConverter($styleId)]/item" />
            <xsl:attribute name="id">
                <xsl:value-of select="osxui:CreateId()"/>
            </xsl:attribute>

            <xsl:variable name="restId" select="@xrt:restorationIdentifier" />
            <xsl:choose>
                <xsl:when test="$restId">
                    <xsl:attribute name="restorationIdentifier" ><xsl:value-of select="$restId"/></xsl:attribute>
                </xsl:when>
            </xsl:choose>

            <xsl:variable name="androidWidth" select="@android:layout_width" />
            <xsl:attribute name="android_width" ><xsl:value-of select="$androidWidth"/></xsl:attribute>
            <xsl:variable name="androidHeight" select="@android:layout_height" />
            <xsl:attribute name="android_height" ><xsl:value-of select="$androidHeight"/></xsl:attribute>

            <xsl:variable name="androidMarginTop" select="@android:layout_marginTop"/>
            <xsl:attribute name="android_marginTop"><xsl:value-of select="$androidMarginTop"/></xsl:attribute>
            <xsl:variable name="androidMarginBottom" select="@android:layout_marginBottom"/>
            <xsl:attribute name="android_marginBottom"><xsl:value-of select="$androidMarginBottom"/></xsl:attribute>
            <xsl:variable name="androidMarginLeft" select="@android:layout_marginLeft"/>
            <xsl:attribute name="android_marginLeft"><xsl:value-of select="$androidMarginLeft"/></xsl:attribute>
            <xsl:variable name="androidMarginRight" select="@android:layout_marginRight"/>
            <xsl:attribute name="android_marginRight"><xsl:value-of select="$androidMarginRight"/></xsl:attribute>

            <xsl:attribute name="iboutlet"><xsl:value-of select="@xrt:iboutlet"/></xsl:attribute>
            <xsl:attribute name="ibaction"><xsl:value-of select="@xrt:ibaction"/></xsl:attribute>

            <xsl:variable name="androidGravity" select="@android:layout_gravity" />
            <xsl:attribute name="android_gravity" ><xsl:value-of select="$androidGravity"/></xsl:attribute>

            <xsl:attribute name="clipsSubviews">YES</xsl:attribute>
            <xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
            <xsl:attribute name="alwaysBounceVertical">YES</xsl:attribute>
            <xsl:attribute name="dataMode">prototypes</xsl:attribute>
            <xsl:attribute name="style">plain</xsl:attribute>
            <xsl:attribute name="separatorStyle">default</xsl:attribute>
            <xsl:attribute name="rowHeight">44</xsl:attribute>
            <xsl:attribute name="sectionHeaderHeight">22</xsl:attribute>
            <xsl:attribute name="sectionFooterHeight">22</xsl:attribute>
            <xsl:attribute name="translatesAutoresizingMaskIntoConstraints">NO</xsl:attribute>

            <xsl:element name="color">
                <xsl:attribute name="key">backgroundColor</xsl:attribute>
                <xsl:attribute name="white">1</xsl:attribute>
                <xsl:attribute name="alpha">1</xsl:attribute>
                <xsl:attribute name="colorSpace">calibratedWhite</xsl:attribute>
            </xsl:element>

            <xsl:apply-templates select="*[name() != 'com.android.sdklib.widgets.iOSTableViewCell']" />
            <xsl:element name="prototypes">
                <xsl:apply-templates select="com.android.sdklib.widgets.iOSTableViewCell" />
            </xsl:element>

        </xsl:element>
    </xsl:template>

    <xsl:template match="com.android.sdklib.widgets.iOSTableViewCell"
                  xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:esi="http://www.edge-delivery.org/esi/1.0"
                  xmlns:xrt="http://schemas.android.com/apk/res/android">

        <xsl:element name="tableViewCell">
            <xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
            <xsl:attribute name="selectionStyle">default</xsl:attribute>
            <xsl:attribute name="indentationWidth">10</xsl:attribute>
            <xsl:attribute name="segue_type"><xsl:value-of select="@xrt:segue_type"/></xsl:attribute>
            <xsl:attribute name="segue_destination"><xsl:value-of select="@xrt:segue_destination"/></xsl:attribute>

            <xsl:variable name="androidWidth" select="@android:layout_width" />
            <xsl:attribute name="android_width" ><xsl:value-of select="$androidWidth"/></xsl:attribute>
            <xsl:variable name="androidHeight" select="@android:layout_height" />
            <xsl:attribute name="android_height" ><xsl:value-of select="$androidHeight"/></xsl:attribute>

            <xsl:variable name="tableCellID" select="osxui:CreateId()"/>
            <xsl:attribute name="id" >
                <xsl:value-of select="$tableCellID"/>
            </xsl:attribute>


            <xsl:variable name="reuseId" select="@xrt:reuseIdentifier" />
            <xsl:choose>
                <xsl:when test="$reuseId">
                    <xsl:attribute name="reuseIdentifier" ><xsl:value-of select="$reuseId"/></xsl:attribute>
                </xsl:when>
            </xsl:choose>

            <xsl:variable name="restId" select="@xrt:restorationIdentifier" />
            <xsl:choose>
                <xsl:when test="$restId">
                    <xsl:attribute name="restorationIdentifier"><xsl:value-of select="$restId"/></xsl:attribute>
                </xsl:when>
            </xsl:choose>

            <xsl:variable name="segueId" select="@xrt:segue_identifier" />
            <xsl:choose>
                <xsl:when test="$segueId">
                    <xsl:attribute name="segue_identifier" ><xsl:value-of select="$segueId"/></xsl:attribute>
                </xsl:when>
            </xsl:choose>

            <xsl:variable name="styleType" select="@xrt:style" />

            <xsl:variable name="custom" select="'Custom'" />
            <xsl:variable name="basic" select="'Basic'" />
            <xsl:variable name="rightDetail" select="'RightDetail'" />
            <xsl:variable name="leftDetail" select="'LeftDetail'" />
            <xsl:variable name="subtitle" select="'Subtitle'" />
            <xsl:variable name="textLabelId"><xsl:value-of select="osxui:CreateId()"/></xsl:variable>
            <xsl:variable name="detailTextLabelId"><xsl:value-of select="osxui:CreateId()"/></xsl:variable>

            <xsl:choose>
                <xsl:when test="$styleType = $custom">

                </xsl:when>

                <xsl:when test="$styleType = $basic">
                    <xsl:attribute name="style">IBUITableViewCellStyleDefault</xsl:attribute>
                    <xsl:attribute name="textLabel"><xsl:value-of select="$textLabelId"/></xsl:attribute>
                </xsl:when>

                <xsl:when test="$styleType = $rightDetail">
                    <xsl:attribute name="style">IBUITableViewCellStyleValue1</xsl:attribute>
                    <xsl:attribute name="textLabel"><xsl:value-of select="$textLabelId"/></xsl:attribute>
                    <xsl:attribute name="detailTextLabel"><xsl:value-of select="$detailTextLabelId"/></xsl:attribute>
                </xsl:when>

                <xsl:when test="$styleType = $leftDetail">
                    <xsl:attribute name="style">IBUITableViewCellStyleValue2</xsl:attribute>
                    <xsl:attribute name="textLabel"><xsl:value-of select="$textLabelId"/></xsl:attribute>
                    <xsl:attribute name="detailTextLabel"><xsl:value-of select="$detailTextLabelId"/></xsl:attribute>
                </xsl:when>

                <xsl:when test="$styleType = $subtitle">
                    <xsl:attribute name="style">IBUITableViewCellStyleSubtitle</xsl:attribute>
                    <xsl:attribute name="textLabel"><xsl:value-of select="$textLabelId"/></xsl:attribute>
                    <xsl:attribute name="detailTextLabel"><xsl:value-of select="$detailTextLabelId"/></xsl:attribute>
                </xsl:when>
            </xsl:choose>


            <xsl:element name="autoresizingMask">
                <xsl:attribute name="key">autoresizingMask</xsl:attribute>
            </xsl:element>

            <xsl:element name="tableViewCellContentView">
                <xsl:attribute name="key">contentView</xsl:attribute>
                <xsl:attribute name="opaque">NO</xsl:attribute>
                <xsl:attribute name="clipsSubviews">YES</xsl:attribute>
                <xsl:attribute name="multipleTouchEnabled">YES</xsl:attribute>
                <xsl:attribute name="contentMode">center</xsl:attribute>
                <xsl:attribute name="tableViewCell"><xsl:value-of select="$tableCellID"/></xsl:attribute>
                <xsl:attribute name="id" >
                    <xsl:value-of select="osxui:CreateId()"/>
                </xsl:attribute>

                <xsl:element name="autoresizingMask">
                    <xsl:attribute name="key">autoresizingMask</xsl:attribute>
                </xsl:element>

                <xsl:element name="subviews">
                    <xsl:apply-templates />

                    <xsl:choose>
                        <xsl:when test="$styleType = $custom">

                        </xsl:when>

                        <xsl:when test="$styleType = $basic">
                            <xsl:element name="label">
                                <xsl:attribute name="id"><xsl:value-of select="$textLabelId"/></xsl:attribute>
                                <xsl:attribute name="multipleTouchEnabled">YES</xsl:attribute>
                                <xsl:attribute name="contentMode">left</xsl:attribute>
                                <xsl:attribute name="text">Title</xsl:attribute>
                                <xsl:attribute name="lineBreakMode">tailTruncation</xsl:attribute>
                                <xsl:attribute name="baselineAdjustment">alignBaselines</xsl:attribute>
                                <xsl:attribute name="adjustsFontSizeToFit">NO</xsl:attribute>
                                <xsl:element name="autoresizingMask">
                                    <xsl:attribute name="key">autoresizingMask</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxX">YES</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxY">YES</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="fontDescription">
                                    <xsl:attribute name="key">fontDescription</xsl:attribute>
                                    <xsl:attribute name="type">system</xsl:attribute>
                                    <xsl:attribute name="pointSize">16</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="color">
                                    <xsl:attribute name="key">textColor</xsl:attribute>
                                    <xsl:attribute name="cocoaTouchSystemColor">darkTextColor</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="color">
                                    <xsl:attribute name="key">backgroundColor</xsl:attribute>
                                    <xsl:attribute name="red">1</xsl:attribute>
                                    <xsl:attribute name="green">1</xsl:attribute>
                                    <xsl:attribute name="blue">1</xsl:attribute>
                                    <xsl:attribute name="alpha">1</xsl:attribute>
                                    <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="nil">
                                    <xsl:attribute name="key">highlightedColor</xsl:attribute>
                                </xsl:element>
                            </xsl:element>
                        </xsl:when>

                        <xsl:when test="$styleType = $rightDetail">
                            <xsl:element name="label">
                                <xsl:attribute name="id"><xsl:value-of select="$textLabelId"/></xsl:attribute>
                                <xsl:attribute name="multipleTouchEnabled">YES</xsl:attribute>
                                <xsl:attribute name="contentMode">left</xsl:attribute>
                                <xsl:attribute name="text">Title</xsl:attribute>
                                <xsl:attribute name="lineBreakMode">tailTruncation</xsl:attribute>
                                <xsl:attribute name="baselineAdjustment">alignBaselines</xsl:attribute>
                                <xsl:attribute name="adjustsFontSizeToFit">NO</xsl:attribute>
                                <xsl:element name="autoresizingMask">
                                    <xsl:attribute name="key">autoresizingMask</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxX">YES</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxY">YES</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="fontDescription">
                                    <xsl:attribute name="key">fontDescription</xsl:attribute>
                                    <xsl:attribute name="type">system</xsl:attribute>
                                    <xsl:attribute name="pointSize">16</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="color">
                                    <xsl:attribute name="key">textColor</xsl:attribute>
                                    <xsl:attribute name="cocoaTouchSystemColor">darkTextColor</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="color">
                                    <xsl:attribute name="key">backgroundColor</xsl:attribute>
                                    <xsl:attribute name="red">1</xsl:attribute>
                                    <xsl:attribute name="green">1</xsl:attribute>
                                    <xsl:attribute name="blue">1</xsl:attribute>
                                    <xsl:attribute name="alpha">1</xsl:attribute>
                                    <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="nil">
                                    <xsl:attribute name="key">highlightedColor</xsl:attribute>
                                </xsl:element>
                            </xsl:element>
                            <xsl:element name="label">
                                <xsl:attribute name="id"><xsl:value-of select="$detailTextLabelId"/></xsl:attribute>
                                <xsl:attribute name="opaque">NO</xsl:attribute>
                                <xsl:attribute name="multipleTouchEnabled">YES</xsl:attribute>
                                <xsl:attribute name="contentMode">left</xsl:attribute>
                                <xsl:attribute name="text">Detail</xsl:attribute>
                                <xsl:attribute name="textAlignment">right</xsl:attribute>
                                <xsl:attribute name="lineBreakMode">tailTruncation</xsl:attribute>
                                <xsl:attribute name="baselineAdjustment">alignBaselines</xsl:attribute>
                                <xsl:attribute name="adjustsFontSizeToFit">NO</xsl:attribute>

                                <xsl:element name="autoresizingMask">
                                    <xsl:attribute name="key">autoresizingMask</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxX">YES</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxY">YES</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="fontDescription">
                                    <xsl:attribute name="key">fontDescription</xsl:attribute>
                                    <xsl:attribute name="type">system</xsl:attribute>
                                    <xsl:attribute name="pointSize">16</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="color">
                                    <xsl:attribute name="key">textColor</xsl:attribute>
                                    <xsl:attribute name="red">0.55686274509803924</xsl:attribute>
                                    <xsl:attribute name="green">0.55686274509803924</xsl:attribute>
                                    <xsl:attribute name="blue">0.57647058823529407</xsl:attribute>
                                    <xsl:attribute name="alpha">1</xsl:attribute>
                                    <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="nil">
                                    <xsl:attribute name="key">highlightedColor</xsl:attribute>
                                </xsl:element>
                            </xsl:element>
                        </xsl:when>

                        <xsl:when test="$styleType = $leftDetail">
                            <xsl:element name="label">
                                <xsl:attribute name="id"><xsl:value-of select="$textLabelId"/></xsl:attribute>
                                <xsl:attribute name="multipleTouchEnabled">YES</xsl:attribute>
                                <xsl:attribute name="contentMode">left</xsl:attribute>
                                <xsl:attribute name="text">Title</xsl:attribute>
                                <xsl:attribute name="textAlignment">right</xsl:attribute>
                                <xsl:attribute name="lineBreakMode">tailTruncation</xsl:attribute>
                                <xsl:attribute name="baselineAdjustment">alignBaselines</xsl:attribute>
                                <xsl:attribute name="adjustsFontSizeToFit">NO</xsl:attribute>
                                <xsl:element name="autoresizingMask">
                                    <xsl:attribute name="key">autoresizingMask</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxX">YES</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxY">YES</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="fontDescription">
                                    <xsl:attribute name="key">fontDescription</xsl:attribute>
                                    <xsl:attribute name="type">system</xsl:attribute>
                                    <xsl:attribute name="pointSize">12</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="color">
                                    <xsl:attribute name="key">textColor</xsl:attribute>
                                    <xsl:attribute name="red">0.0</xsl:attribute>
                                    <xsl:attribute name="green">0.47843137254901963</xsl:attribute>
                                    <xsl:attribute name="blue">1</xsl:attribute>
                                    <xsl:attribute name="alpha">1</xsl:attribute>
                                    <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="color">
                                    <xsl:attribute name="key">backgroundColor</xsl:attribute>
                                    <xsl:attribute name="red">1</xsl:attribute>
                                    <xsl:attribute name="green">1</xsl:attribute>
                                    <xsl:attribute name="blue">1</xsl:attribute>
                                    <xsl:attribute name="alpha">1</xsl:attribute>
                                    <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="nil">
                                    <xsl:attribute name="key">highlightedColor</xsl:attribute>
                                </xsl:element>
                            </xsl:element>
                            <xsl:element name="label">
                                <xsl:attribute name="id"><xsl:value-of select="$detailTextLabelId"/></xsl:attribute>
                                <xsl:attribute name="opaque">NO</xsl:attribute>
                                <xsl:attribute name="multipleTouchEnabled">YES</xsl:attribute>
                                <xsl:attribute name="contentMode">left</xsl:attribute>
                                <xsl:attribute name="text">Detail</xsl:attribute>
                                <xsl:attribute name="lineBreakMode">tailTruncation</xsl:attribute>
                                <xsl:attribute name="baselineAdjustment">alignBaselines</xsl:attribute>
                                <xsl:attribute name="adjustsFontSizeToFit">NO</xsl:attribute>
                                <xsl:element name="autoresizingMask">
                                    <xsl:attribute name="key">autoresizingMask</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxX">YES</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxY">YES</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="fontDescription">
                                    <xsl:attribute name="key">fontDescription</xsl:attribute>
                                    <xsl:attribute name="type">system</xsl:attribute>
                                    <xsl:attribute name="pointSize">12</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="color">
                                    <xsl:attribute name="key">textColor</xsl:attribute>
                                    <xsl:attribute name="cocoaTouchSystemColor">darkTextColor</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="nil">
                                    <xsl:attribute name="key">highlightedColor</xsl:attribute>
                                </xsl:element>
                            </xsl:element>
                        </xsl:when>

                        <xsl:when test="$styleType = $subtitle">
                            <xsl:element name="label">
                                <xsl:attribute name="id"><xsl:value-of select="$textLabelId"/></xsl:attribute>
                                <xsl:attribute name="opaque">NO</xsl:attribute>
                                <xsl:attribute name="multipleTouchEnabled">YES</xsl:attribute>
                                <xsl:attribute name="contentMode">left</xsl:attribute>
                                <xsl:attribute name="text">Title</xsl:attribute>
                                <xsl:attribute name="textAlignment">right</xsl:attribute>
                                <xsl:attribute name="lineBreakMode">tailTruncation</xsl:attribute>
                                <xsl:attribute name="baselineAdjustment">alignBaselines</xsl:attribute>
                                <xsl:attribute name="adjustsFontSizeToFit">NO</xsl:attribute>
                                <xsl:element name="autoresizingMask">
                                    <xsl:attribute name="key">autoresizingMask</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxX">YES</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxY">YES</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="fontDescription">
                                    <xsl:attribute name="key">fontDescription</xsl:attribute>
                                    <xsl:attribute name="type">system</xsl:attribute>
                                    <xsl:attribute name="pointSize">16</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="color">
                                    <xsl:attribute name="key">textColor</xsl:attribute>
                                    <xsl:attribute name="cocoaTouchSystemColor">darkTextColor</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="color">
                                    <xsl:attribute name="key">backgroundColor</xsl:attribute>
                                    <xsl:attribute name="red">1</xsl:attribute>
                                    <xsl:attribute name="green">1</xsl:attribute>
                                    <xsl:attribute name="blue">1</xsl:attribute>
                                    <xsl:attribute name="alpha">1</xsl:attribute>
                                    <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="nil">
                                    <xsl:attribute name="key">highlightedColor</xsl:attribute>
                                </xsl:element>
                            </xsl:element>
                            <xsl:element name="label">
                                <xsl:attribute name="id"><xsl:value-of select="$detailTextLabelId"/></xsl:attribute>
                                <xsl:attribute name="opaque">NO</xsl:attribute>
                                <xsl:attribute name="multipleTouchEnabled">YES</xsl:attribute>
                                <xsl:attribute name="contentMode">left</xsl:attribute>
                                <xsl:attribute name="text">Detail</xsl:attribute>
                                <xsl:attribute name="lineBreakMode">tailTruncation</xsl:attribute>
                                <xsl:attribute name="baselineAdjustment">alignBaselines</xsl:attribute>
                                <xsl:attribute name="adjustsFontSizeToFit">NO</xsl:attribute>
                                <xsl:element name="autoresizingMask">
                                    <xsl:attribute name="key">autoresizingMask</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxX">YES</xsl:attribute>
                                    <xsl:attribute name="flexibleMaxY">YES</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="fontDescription">
                                    <xsl:attribute name="key">fontDescription</xsl:attribute>
                                    <xsl:attribute name="type">system</xsl:attribute>
                                    <xsl:attribute name="pointSize">11</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="color">
                                    <xsl:attribute name="key">textColor</xsl:attribute>
                                    <xsl:attribute name="cocoaTouchSystemColor">darkTextColor</xsl:attribute>
                                </xsl:element>
                                <xsl:element name="nil">
                                    <xsl:attribute name="key">highlightedColor</xsl:attribute>
                                </xsl:element>
                            </xsl:element>
                        </xsl:when>
                    </xsl:choose>
                </xsl:element>

            </xsl:element>

        </xsl:element>

    </xsl:template>

</xsl:stylesheet>