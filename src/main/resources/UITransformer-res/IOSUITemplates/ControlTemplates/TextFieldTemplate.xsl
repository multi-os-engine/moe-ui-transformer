<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
				xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
				exclude-result-prefixes="osxui">
	<xsl:strip-space elements="textField" />
	<xsl:output method="xml" indent="yes"/>

	<xsl:template match="com.android.sdklib.widgets.iOSTextField"
		xmlns:android="http://schemas.android.com/apk/res/android"
		xmlns:xrt="http://schemas.android.com/apk/res/android"
		xmlns:esi="http://www.edge-delivery.org/esi/1.0">
		<!-- android namespase: xmlns:android="http://schemas.android.com/apk/res/android"  -->
		<!-- exclude xmlns atribute: xmlns:esi="http://www.edge-delivery.org/esi/1.0" -->
		
		<xsl:element name="textField">
			<xsl:variable name="styleId" select="@style"/>
			<xsl:variable name="textFieldStyles" select="document($g_stylePath)//style[@name = osxui:StyleIdConverter($styleId)]/item" />
			<xsl:variable name="drawableName" select="@android:background"/>
			<xsl:variable name="drawableDocumentName" select="osxui:ElementDrawableConverter($drawableName)"/>
			<xsl:variable name="textFieldDrawable" select="document('../../AndroidRes/$drawableXmlName')//shape" />
			
			<xsl:attribute name="id">
				<xsl:value-of select="osxui:CreateId()"/>
			</xsl:attribute>

            <xsl:variable name="restId" select="@xrt:restorationIdentifier" />
            <xsl:choose>
                <xsl:when test="$restId">
                    <xsl:attribute name="restorationIdentifier" ><xsl:value-of select="$restId"/></xsl:attribute>
                </xsl:when>
            </xsl:choose>

			<xsl:variable name="segueId" select="@xrt:segue_identifier" />
			<xsl:choose>
				<xsl:when test="$segueId">
					<xsl:attribute name="segue_identifier" ><xsl:value-of select="$segueId"/></xsl:attribute>
				</xsl:when>
			</xsl:choose>

			<xsl:variable name="androidText" select="@android:text" />
			<xsl:attribute name="android_text" ><xsl:value-of select="$androidText"/></xsl:attribute>
			<xsl:variable name="androidTextSize" select="@android:textSize" />
			<xsl:attribute name="android_textSize" ><xsl:value-of select="$androidTextSize"/></xsl:attribute>
			
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

			<xsl:variable name="androidGravity" select="@android:layout_gravity" />
			<xsl:attribute name="android_gravity" ><xsl:value-of select="$androidGravity"/></xsl:attribute>
			
			<xsl:attribute name="opaque">NO</xsl:attribute>
			<xsl:attribute name="clipsSubviews">NO</xsl:attribute>
			<xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
			<xsl:attribute name="fixedFrame">YES</xsl:attribute>
			<xsl:attribute name="contentVerticaleAligment">center</xsl:attribute>
			<xsl:attribute name="contentHorizontalAligment">center</xsl:attribute>
			<!--<xsl:attribute name="text"><xsl:value-of select="$textFieldStyles[@name='android:text']"/></xsl:attribute>-->
			<xsl:attribute name="borderStyle">roundedRect</xsl:attribute>
			<xsl:attribute name="minimumFontSize">17</xsl:attribute>


			<!-- Styles -->
			<xsl:variable name="styleBackground" select="@android:background"/>
			<xsl:variable name="styleTextSize" select="@android:textSize"/>
			<xsl:variable name="styleText" select="@android:text"/>
			<xsl:variable name="styleTextColor" select="@android:textColor"/>
			<xsl:variable name="styleTextStyle" select="@android:textStyle"/>
			<!-- Styles end -->

			<xsl:attribute name="text"><xsl:value-of select="$styleText"/></xsl:attribute>
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
						<xsl:otherwise><xsl:value-of select="osxui:SizeFormatConverter($textFieldStyles[@name='android:textSize'])"/></xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
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
						<xsl:attribute name="red"><xsl:value-of select="osxui:ColorToR($textFieldStyles[@name='android:textColor'])"/></xsl:attribute>
						<xsl:attribute name="green"><xsl:value-of select="osxui:ColorToG($textFieldStyles[@name='android:textColor'])"/></xsl:attribute>
						<xsl:attribute name="blue"><xsl:value-of select="osxui:ColorToB($textFieldStyles[@name='android:textColor'])"/></xsl:attribute>
						<xsl:attribute name="alpha"><xsl:value-of select="osxui:ColorToAlpha($textFieldStyles[@name='android:textColor'])"/></xsl:attribute>
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
				<!--
				<xsl:otherwise>
					<xsl:element name="color">
						<xsl:attribute name="key">backgroundColor</xsl:attribute>
						<xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
						<xsl:attribute name="red"><xsl:value-of select="osxui:ColorToR($textFieldStyles[@name='android:background'])"/></xsl:attribute>
						<xsl:attribute name="green"><xsl:value-of select="osxui:ColorToG($textFieldStyles[@name='android:background'])"/></xsl:attribute>
						<xsl:attribute name="blue"><xsl:value-of select="osxui:ColorToB($textFieldStyles[@name='android:background'])"/></xsl:attribute>
						<xsl:attribute name="alpha"><xsl:value-of select="osxui:ColorToAlpha($textFieldStyles[@name='android:background'])"/></xsl:attribute>
					</xsl:element>
				</xsl:otherwise>
				-->
			</xsl:choose>


			<!--<xsl:element name="textInputTraits">textInputTraits</xsl:element>-->

            <!--
			<xsl:element name="rect">
				<xsl:attribute name="key">frame</xsl:attribute>
				<xsl:attribute name="x">0</xsl:attribute>
				<xsl:attribute name="y">0</xsl:attribute>
				<xsl:attribute name="width"><xsl:value-of select="osxui:GetWidthWrapContent($textFieldStyles[@name='android:text'], $textFieldStyles[@name='android:textSize'])"/></xsl:attribute>
				<xsl:attribute name="height"><xsl:value-of select="osxui:SizeFormatConverter($textFieldStyles[@name='android:textSize']) * 2"/></xsl:attribute>
			</xsl:element>
			-->
			<!--<xsl:element name="color">-->
				<!--<xsl:attribute name="key">backgroundColor</xsl:attribute>-->
				<!--<xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>-->
				<!--<xsl:attribute name="red"><xsl:value-of select="osxui:ColorToR($textFieldStyles[@name='android:background'])"/></xsl:attribute>-->
				<!--<xsl:attribute name="green"><xsl:value-of select="osxui:ColorToG($textFieldStyles[@name='android:background'])"/></xsl:attribute>-->
				<!--<xsl:attribute name="blue"><xsl:value-of select="osxui:ColorToB($textFieldStyles[@name='android:background'])"/></xsl:attribute>-->
				<!--<xsl:attribute name="alpha"><xsl:value-of select="osxui:ColorToAlpha($textFieldStyles[@name='android:background'])"/></xsl:attribute>-->
			<!--</xsl:element>-->
			<!--
			<xsl:element name="color">
				<xsl:attribute name="key">textColor</xsl:attribute>
				<xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
				<xsl:attribute name="red"><xsl:value-of select="osxui:ColorToR($textFieldStyles[@name='android:textColor'])"/></xsl:attribute>
				<xsl:attribute name="green"><xsl:value-of select="osxui:ColorToG($textFieldStyles[@name='android:textColor'])"/></xsl:attribute>
				<xsl:attribute name="blue"><xsl:value-of select="osxui:ColorToB($textFieldStyles[@name='android:textColor'])"/></xsl:attribute>
				<xsl:attribute name="alpha"><xsl:value-of select="osxui:ColorToAlpha($textFieldStyles[@name='android:textColor'])"/></xsl:attribute>
			</xsl:element>
			-->


			<xsl:apply-templates/>
    	</xsl:element>
	</xsl:template>
</xsl:stylesheet>