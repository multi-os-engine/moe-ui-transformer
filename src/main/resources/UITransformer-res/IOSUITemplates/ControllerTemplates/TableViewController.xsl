<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
    exclude-result-prefixes="osxui" version="2.0">

    <xsl:strip-space elements="tableViewController" />
    <xsl:output method="xml" indent="yes" />
    <xsl:template match="com.android.sdklib.widgets.iOSTableViewController"
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:esi="http://www.edge-delivery.org/esi/1.0"
        xmlns:xrt="http://schemas.android.com/apk/res/android">

        <xsl:element name="tableViewController">
            <xsl:variable name="tableViewControllerId" select="osxui:CreateId()" />
            <xsl:attribute name="id"><xsl:value-of select="$tableViewControllerId"/></xsl:attribute>
            <xsl:attribute name="sceneMemberID">viewController</xsl:attribute>

            <xsl:variable name="storyboardId" select="@xrt:storyboardIdentifier" />
            <xsl:choose>
                <xsl:when test="$storyboardId">
                    <xsl:attribute name="storyboardIdentifier" ><xsl:value-of select="$storyboardId"/></xsl:attribute>
                    <xsl:attribute name="useStoryboardIdentifierAsRestorationIdentifier">YES</xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:variable name="restId" select="@xrt:restorationIdentifier" />
                    <xsl:choose>
                        <xsl:when test="$restId">
                            <xsl:attribute name="restorationIdentifier" ><xsl:value-of select="$restId"/></xsl:attribute>
                        </xsl:when>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>

            <xsl:variable name="viewController" select="@xrt:viewController" />
            <xsl:variable name="initialViewController" select="@xrt:initialViewController" />
            <xsl:if test="@xrt:initialViewController">
                <xsl:attribute name="initialViewController"><xsl:value-of select="$initialViewController"/></xsl:attribute>
            </xsl:if>
            <xsl:if test="@xrt:viewController">
                <xsl:attribute name="viewController"><xsl:value-of select="$viewController"/></xsl:attribute>
            </xsl:if>

            <xsl:apply-templates />

<!--
            <xsl:element name="tableView">
                <xsl:attribute name="id"><xsl:value-of select="osxui:CreateId()"/></xsl:attribute>
                <xsl:attribute name="key">view</xsl:attribute>
                <xsl:attribute name="clipsSubviews">YES</xsl:attribute>
                <xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
                <xsl:attribute name="alwaysBounceVertical">YES</xsl:attribute>
                <xsl:attribute name="dataMode">prototypes</xsl:attribute>
                <xsl:attribute name="style">plain</xsl:attribute>
                <xsl:attribute name="separatorStyle">default</xsl:attribute>
                <xsl:attribute name="rowHeight">44</xsl:attribute>
                <xsl:attribute name="sectionHeaderHeight">22</xsl:attribute>
                <xsl:attribute name="sectionFooterHeight">22</xsl:attribute>

                <xsl:element name="autoresizingMask">
                    <xsl:attribute name="key">frame</xsl:attribute>
                    <xsl:attribute name="widthSizable">YES</xsl:attribute>
                    <xsl:attribute name="heightSizable">YES</xsl:attribute>
                </xsl:element>

                <xsl:element name="color">
                    <xsl:attribute name="key">backgroundColor</xsl:attribute>
                    <xsl:attribute name="white">1</xsl:attribute>
                    <xsl:attribute name="alpha">1</xsl:attribute>
                    <xsl:attribute name="colorSpace">calibratedWhite</xsl:attribute>
                </xsl:element>

                <xsl:element name="prototypes">
                    <xsl:element name="tableViewCell">
                        <xsl:variable name="tableViewCellId" select="osxui:CreateId()" />
                        <xsl:attribute name="id"><xsl:value-of select="$tableViewCellId"/></xsl:attribute>
                        <xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
                        <xsl:attribute name="selectionStyle">default</xsl:attribute>
                        <xsl:attribute name="indentationWidth">scaleToFill</xsl:attribute>

                        <xsl:element name="autoresizingMask">
                            <xsl:attribute name="key">autoresizingMask</xsl:attribute>
                        </xsl:element>

                        <xsl:element name="tableViewCellContentView">
                            <xsl:attribute name="key">contentView</xsl:attribute>
                            <xsl:attribute name="opaque">NO</xsl:attribute>
                            <xsl:attribute name="clipsSubviews">YES</xsl:attribute>
                            <xsl:attribute name="multipleTouchEnabled">YES</xsl:attribute>
                            <xsl:attribute name="contentMode">center</xsl:attribute>
                            <xsl:attribute name="tableViewCell"><xsl:value-of select="$tableViewCellId"/></xsl:attribute>
                            <xsl:attribute name="id"><xsl:value-of select="osxui:CreateId()"/></xsl:attribute>
                            <xsl:element name="autoresizingMask">
                                <xsl:attribute name="key">autoresizingMask</xsl:attribute>
                            </xsl:element>
                        </xsl:element>
                    </xsl:element>
                </xsl:element>

                <xsl:element name="connections">
                    <xsl:element name="outlet">
                        <xsl:attribute name="property">dataSource</xsl:attribute>
                        <xsl:attribute name="destination"><xsl:value-of select="$tableViewControllerId"/></xsl:attribute>
                        <xsl:attribute name="id"><xsl:value-of select="osxui:CreateId()"/></xsl:attribute>
                    </xsl:element>

                    <xsl:element name="outlet">
                        <xsl:attribute name="property">delegate</xsl:attribute>
                        <xsl:attribute name="destination"><xsl:value-of select="$tableViewControllerId"/></xsl:attribute>
                        <xsl:attribute name="id"><xsl:value-of select="osxui:CreateId()"/></xsl:attribute>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
-->

        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
