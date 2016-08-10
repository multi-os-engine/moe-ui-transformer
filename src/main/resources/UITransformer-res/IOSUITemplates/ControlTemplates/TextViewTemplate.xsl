<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
				xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
				exclude-result-prefixes="osxui">
	<xsl:strip-space elements="lable" />
	<xsl:output method="xml" indent="yes"/>

	<xsl:template match="com.android.sdklib.widgets.iOSTextView"
		xmlns:android="http://schemas.android.com/apk/res/android"
		xmlns:xrt="http://schemas.android.com/apk/res/android"
		xmlns:esi="http://www.edge-delivery.org/esi/1.0">
		<!-- android namespase: xmlns:android="http://schemas.android.com/apk/res/android"  -->
		<!-- exclude xmlns atribute: xmlns:esi="http://www.edge-delivery.org/esi/1.0" -->
		
		<xsl:element name="label">

			<xsl:variable name="styleId" select="@style" />
			<xsl:variable name="labelStyles" select="document($g_stylePath)//style[@name = osxui:StyleIdConverter($styleId)]/item" />
			<xsl:attribute name="id" >
				<xsl:value-of select="osxui:CreateId()"/>
			</xsl:attribute>

            <xsl:variable name="restId" select="@xrt:restorationIdentifier" />
            <xsl:choose>
                <xsl:when test="$restId">
                    <xsl:attribute name="restorationIdentifier" ><xsl:value-of select="$restId"/></xsl:attribute>
                </xsl:when>
            </xsl:choose>

			<xsl:variable name="androidWidth" select="@android:layout_width" />
            <xsl:choose><xsl:when test="$androidWidth">
                    <xsl:attribute name="android_width" ><xsl:value-of select="$androidWidth"/></xsl:attribute>
            </xsl:when></xsl:choose>


			<xsl:variable name="androidHeight" select="@android:layout_height" />
            <xsl:choose><xsl:when test="$androidHeight">
                <xsl:attribute name="android_height" ><xsl:value-of select="$androidHeight"/></xsl:attribute>
            </xsl:when></xsl:choose>

			<xsl:variable name="androidMarginTop" select="@android:layout_marginTop"/>
            <xsl:choose><xsl:when test="$androidMarginTop">
                <xsl:attribute name="android_marginTop"><xsl:value-of select="$androidMarginTop"/></xsl:attribute>
            </xsl:when></xsl:choose>

			<xsl:variable name="androidMarginBottom" select="@android:layout_marginBottom"/>
            <xsl:choose><xsl:when test="$androidMarginBottom">
                <xsl:attribute name="android_marginBottom"><xsl:value-of select="$androidMarginBottom"/></xsl:attribute>
            </xsl:when></xsl:choose>

			<xsl:variable name="androidMarginLeft" select="@android:layout_marginLeft"/>
            <xsl:choose><xsl:when test="$androidMarginLeft">
                <xsl:attribute name="android_marginLeft"><xsl:value-of select="$androidMarginLeft"/></xsl:attribute>
            </xsl:when></xsl:choose>

			<xsl:variable name="androidMarginRight" select="@android:layout_marginRight"/>
            <xsl:choose><xsl:when test="$androidMarginRight">
                <xsl:attribute name="android_marginRight"><xsl:value-of select="$androidMarginRight"/></xsl:attribute>
            </xsl:when></xsl:choose>


            <xsl:variable name="varIBOutlet" select="@xrt:iboutlet" />
            <xsl:choose>
                <xsl:when test="$varIBOutlet">
                    <xsl:attribute name="iboutlet"><xsl:value-of select="@xrt:iboutlet"/></xsl:attribute>
                </xsl:when>
            </xsl:choose>
            <xsl:variable name="varIBAction" select="@xrt:ibaction" />
            <xsl:choose>
                <xsl:when test="$varIBAction">
                    <xsl:attribute name="ibaction"><xsl:value-of select="@xrt:ibaction"/></xsl:attribute>
                </xsl:when>
            </xsl:choose>


            <xsl:variable name="androidGravity" select="@android:layout_gravity" />
            <xsl:choose><xsl:when test="$androidGravity">
                <xsl:attribute name="android_gravity" ><xsl:value-of select="$androidGravity"/></xsl:attribute>
            </xsl:when></xsl:choose>

            <!-- Styles -->
            <xsl:variable name="styleBackground" select="@android:background"/>
            <xsl:variable name="styleTextSize" select="@android:textSize"/>
            <xsl:variable name="styleText" select="@android:text"/>
            <xsl:variable name="styleTextColor" select="@android:textColor"/>
            <xsl:variable name="styleTextStyle" select="@android:textStyle"/>
            <!-- Styles end -->



            <xsl:choose><xsl:when test="$styleText">
                <xsl:attribute name="android_text" ><xsl:value-of select="$styleText"/></xsl:attribute>
            </xsl:when></xsl:choose>

            <xsl:choose><xsl:when test="$styleTextSize">
                <xsl:attribute name="android_textSize" ><xsl:value-of select="$styleTextSize"/></xsl:attribute>
            </xsl:when></xsl:choose>



			<xsl:attribute name="opaque">NO</xsl:attribute>
			<xsl:attribute name="text">
				<xsl:choose>
					<xsl:when test="$styleText">
						<xsl:value-of select="$styleText"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$labelStyles[@name='android:text']"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>

			<xsl:element name="fontDescription">
				<xsl:attribute name="key">fontDescription</xsl:attribute>

                <xsl:choose>
                    <xsl:when test="$styleTextStyle">
                        <xsl:choose>
                            <xsl:when test="$styleTextStyle = 'bold'">
                                <xsl:attribute name="type">boldSystem</xsl:attribute>
                            </xsl:when>
                            <xsl:when test="$styleTextStyle = 'italic'">
                                <xsl:attribute name="type">italicSystem</xsl:attribute>
                            </xsl:when>
                            <xsl:when test="$styleTextStyle = 'normal'">
                                <xsl:attribute name="type">system</xsl:attribute>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:attribute name="type">system</xsl:attribute>
                    </xsl:otherwise>
                </xsl:choose>

				<xsl:attribute name="pointSize">
                    <xsl:choose>
                        <xsl:when test="$styleTextSize"><xsl:value-of select="osxui:SizeFormatConverter($styleTextSize)"/></xsl:when>
                        <xsl:otherwise><xsl:value-of select="osxui:SizeFormatConverter($labelStyles[@name='android:textSize'])"/></xsl:otherwise>
                    </xsl:choose>
                </xsl:attribute>
			</xsl:element>
			<xsl:element name="nil">
				<xsl:attribute name="key">highlightedColor</xsl:attribute>
			</xsl:element>
			<xsl:element name="color">
				<xsl:attribute name="key">textColor</xsl:attribute>
				<xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                <xsl:choose>
                    <xsl:when test="$styleTextColor">
                        <xsl:attribute name="red"><xsl:value-of select="osxui:ColorToR($styleTextColor)"/></xsl:attribute>
                        <xsl:attribute name="green"><xsl:value-of select="osxui:ColorToG($styleTextColor)"/></xsl:attribute>
                        <xsl:attribute name="blue"><xsl:value-of select="osxui:ColorToB($styleTextColor)"/></xsl:attribute>
                        <xsl:attribute name="alpha"><xsl:value-of select="osxui:ColorToAlpha($styleTextColor)"/></xsl:attribute>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:attribute name="red"><xsl:value-of select="osxui:ColorToR($labelStyles[@name='android:textColor'])"/></xsl:attribute>
                        <xsl:attribute name="green"><xsl:value-of select="osxui:ColorToG($labelStyles[@name='android:textColor'])"/></xsl:attribute>
                        <xsl:attribute name="blue"><xsl:value-of select="osxui:ColorToB($labelStyles[@name='android:textColor'])"/></xsl:attribute>
                        <xsl:attribute name="alpha"><xsl:value-of select="osxui:ColorToAlpha($labelStyles[@name='android:textColor'])"/></xsl:attribute>
                    </xsl:otherwise>
                </xsl:choose>
			</xsl:element>

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
            </xsl:choose>
			<xsl:apply-templates/>
    	</xsl:element>
	</xsl:template>
</xsl:stylesheet>