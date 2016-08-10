<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
	exclude-result-prefixes="osxui" version="2.0">

	<xsl:strip-space elements="view" />
    <xsl:strip-space elements="*" />
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="com.android.sdklib.widgets.base_elements.iOSBaseLinearLayout"
		xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:esi="http://www.edge-delivery.org/esi/1.0"
        xmlns:xrt="http://schemas.android.com/apk/res/android">
		<xsl:element name="view">
			<xsl:variable name="layoutOrientation" select="@android:orientation" />
			<!--<xsl:attribute name="key">view</xsl:attribute>-->
			<xsl:attribute name="id"><xsl:value-of select="osxui:CreateId()" /></xsl:attribute>
			<xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
			<xsl:attribute name="android_orientation"><xsl:value-of
				select="$layoutOrientation" /></xsl:attribute>

            <xsl:variable name="androidCustomClass" select="@android:customClass" />
            <xsl:attribute name="customClass" ><xsl:value-of select="$androidCustomClass"/></xsl:attribute>

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
            <xsl:attribute name="segue_type"><xsl:value-of select="@xrt:segue_type"/></xsl:attribute>
            <xsl:attribute name="segue_destination"><xsl:value-of select="@xrt:segue_destination"/></xsl:attribute>

            <xsl:variable name="viewController" select="@xrt:viewController" />
            <xsl:variable name="initialViewController" select="@xrt:initialViewController" />
            <xsl:if test="@xrt:initialViewController">
                <xsl:attribute name="initialViewController"><xsl:value-of select="$initialViewController"/></xsl:attribute>
            </xsl:if>

            <xsl:if test="@xrt:viewController">
                <xsl:attribute name="viewController"><xsl:value-of select="$viewController"/></xsl:attribute>
            </xsl:if>

            <!-- Styles -->
            <xsl:variable name="styleBackground" select="@android:background"/>
            <!-- Styles end -->

            <!--
			<xsl:element name="rect">
				<xsl:attribute name="key">frame</xsl:attribute>
				<xsl:attribute name="x">0.0</xsl:attribute>
				<xsl:attribute name="y">0.0</xsl:attribute>
				<xsl:attribute name="width">480</xsl:attribute>
				<xsl:attribute name="height">480</xsl:attribute>
			</xsl:element>

			<xsl:element name="autoresizingMask">
				<xsl:attribute name="key">autoresizingMask</xsl:attribute>
				<xsl:attribute name="widthSizable">YES</xsl:attribute>
				<xsl:attribute name="heightSizable">YES</xsl:attribute>
			</xsl:element>
			-->

            <!--
			<xsl:element name="color">
				<xsl:attribute name="key">backgroundColor</xsl:attribute>
				<xsl:attribute name="white">1</xsl:attribute>
				<xsl:attribute name="alpha">1</xsl:attribute>
				<xsl:attribute name="colorSpace">custom</xsl:attribute>
				<xsl:attribute name="customColorSpace">calibratedWhite</xsl:attribute>
			</xsl:element>
			-->

            <xsl:choose>
                <xsl:when test="$styleBackground">
                    <xsl:element name="color">
                        <xsl:attribute name="key">backgroundColor</xsl:attribute>
                        <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                        <xsl:attribute name="red"><xsl:value-of select="osxui:ColorToR($styleBackground)"/></xsl:attribute>
                        <xsl:attribute name="green"><xsl:value-of select="osxui:ColorToG($styleBackground)"/></xsl:attribute>
                        <xsl:attribute name="blue"><xsl:value-of select="osxui:ColorToB($styleBackground)"/></xsl:attribute>
                        <xsl:attribute name="alpha"><xsl:value-of select="osxui:ColorToAlpha($styleBackground)"/></xsl:attribute>
                    </xsl:element>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:element name="color">
                        <xsl:attribute name="key">backgroundColor</xsl:attribute>
                        <xsl:attribute name="white">1</xsl:attribute>
                        <xsl:attribute name="alpha">1</xsl:attribute>
                        <xsl:attribute name="colorSpace">custom</xsl:attribute>
                        <xsl:attribute name="customColorSpace">calibratedWhite</xsl:attribute>
                    </xsl:element>
                </xsl:otherwise>
            </xsl:choose>


			<xsl:element name="subviews">
				<xsl:apply-templates />
			</xsl:element>
			
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>