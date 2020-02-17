package de.jensklingenberg.ui.deeplinkStarter

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.xml.XmlAttributeImpl
import com.intellij.psi.impl.source.xml.XmlAttributeValueImpl
import com.intellij.psi.impl.source.xml.XmlTextImpl
import com.intellij.psi.impl.source.xml.XmlTokenImpl
import de.jensklingenberg.data.SimpleIcons
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.facet.AndroidRootUtil
import org.jetbrains.android.util.AndroidResourceUtil

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
                "${resolveAndroidString(element,scheme)}://"
            }else{
                "$scheme://"
            }


        } else {
            ""
        }

        deeplinktext += if (host.isNotEmpty()) {
            if(host.contains("@string")){
                resolveAndroidString(element,host)
            }else{
                host
            }
        } else {
            ""
        }

        deeplinktext += if (pathPrefix.isNotEmpty()) {
            if(pathPrefix.contains("@string")){
                resolveAndroidString(element,pathPrefix)
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
            appUriElement = resolveAndroidString(element, appUriElement)
        }
        DeepLinkStarterView(DeepLinkStarterContract.Mode.NAVLIB, appUriElement).showAndGet()

    }

    private fun resolveAndroidString(
        element: XmlTokenImpl,
        appUriElement: String
    ): String {
        val resName = appUriElement.substringAfter("@string/")
        // val manifestFile = AndroidFacet.getInstance(element)?.let { AndroidRootUtil.getPrimaryManifestFile(it) }
        val resPsiFields =
            AndroidResourceUtil.findResourceFields(AndroidFacet.getInstance(element)!!, "string", resName, true)

        if (resPsiFields.isNotEmpty()) {
            val t1 = AndroidResourceUtil.findResourcesByField(resPsiFields.first())
            return t1.first().parent.parent.children.filterIsInstance<XmlTextImpl>().first().text
        } else {
            return appUriElement
        }

    }

    private fun getXMLValue(element: XmlTokenImpl, s: String): String? {
        //TODO: Find better way to get the value
        return element.parent.children.filterIsInstance<XmlAttributeImpl>()
            .find { it.firstChild.text == s }?.children?.first { it is XmlAttributeValueImpl }
            ?.firstChild?.nextSibling?.text

    }
}
