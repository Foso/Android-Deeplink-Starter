package de.jensklingenberg.ui.deeplinkStarter

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.xml.XmlAttributeImpl
import com.intellij.psi.impl.source.xml.XmlAttributeValueImpl
import com.intellij.psi.impl.source.xml.XmlTokenImpl
import de.jensklingenberg.data.SimpleIcons
import de.jensklingenberg.data.resolveAndroidStringRes

import java.awt.event.MouseEvent


class DeepLinkLineMarkerProvider : LineMarkerProvider {

    val TOOLTIP_TEXT = "Start a deepLink"
    val APP_URI_KEY = "app:uri"
    val ANDROID_HOST_KEY = "android:host"
    val ANDROID_SCHEME_KEY = "android:scheme"
    val ANDROID_PATHPREFIX_KEY = "android:pathPrefix"

    override fun getLineMarkerInfo(p0: PsiElement): LineMarkerInfo<*>? {
        val element = p0
        if (element is XmlTokenImpl) {
            when (element.text) {
                "deepLink" -> {
                    return LineMarkerInfo(element, element.textRange,
                        SimpleIcons.LINK, { TOOLTIP_TEXT }, { _: MouseEvent, _: XmlTokenImpl ->
                            foundDeeplinkTag(element)
                        }, GutterIconRenderer.Alignment.CENTER
                    )
                }
                "data" -> {
                    return LineMarkerInfo(element, element.textRange,
                        SimpleIcons.LINK, { TOOLTIP_TEXT }, { _: MouseEvent, _: XmlTokenImpl ->
                            foundDataTag(element)
                        }, GutterIconRenderer.Alignment.CENTER
                    )
                }
                else -> {
                    return null
                }
            }
        } else {
            return null
        }
    }

    private fun foundDataTag(element: XmlTokenImpl) {
        val host = getXMLValue(element, ANDROID_HOST_KEY) ?: ""
        val scheme = getXMLValue(element, ANDROID_SCHEME_KEY) ?: ""
        val pathPrefix = getXMLValue(element, ANDROID_PATHPREFIX_KEY) ?: ""
        //
        var deeplinktext = ""
        deeplinktext += if (scheme.isNotEmpty()) {
            if(scheme.contains("@string")){
                "${resolveAndroidStringRes(element,scheme)}://"
            }else{
                "$scheme://"
            }


        } else {
            ""
        }

        deeplinktext += if (host.isNotEmpty()) {
            if(host.contains("@string")){
                resolveAndroidStringRes(element,host)
            }else{
                host
            }
        } else {
            ""
        }

        deeplinktext += if (pathPrefix.isNotEmpty()) {
            if(pathPrefix.contains("@string")){
                resolveAndroidStringRes(element,pathPrefix)
            }else{
                pathPrefix
            }
        } else {
            ""
        }

        DeepLinkStarterView(DeepLinkStarterContract.Mode.MANIFEST, deeplinktext).showAndGet()
    }

    private fun foundDeeplinkTag(element: XmlTokenImpl) {
        var appUriElement = getXMLValue(element, APP_URI_KEY) ?: ""
        if (appUriElement.contains("@string")) {
            appUriElement = resolveAndroidStringRes(element, appUriElement)
        }
        DeepLinkStarterView(DeepLinkStarterContract.Mode.NAVLIB, appUriElement).showAndGet()

    }



    private fun getXMLValue(element: XmlTokenImpl, s: String): String? {
        //TODO: Find better way to get the value
        return element.parent.children.filterIsInstance<XmlAttributeImpl>()
            .find { it.firstChild.text == s }?.children?.first { it is XmlAttributeValueImpl }
            ?.firstChild?.nextSibling?.text

    }
}
