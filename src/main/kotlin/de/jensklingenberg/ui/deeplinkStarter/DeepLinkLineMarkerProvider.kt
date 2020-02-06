package de.jensklingenberg.ui.deeplinkStarter

import com.android.tools.r8.dex.VirtualFile
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.xml.XmlAttributeImpl
import com.intellij.psi.impl.source.xml.XmlAttributeValueImpl
import com.intellij.psi.impl.source.xml.XmlTokenImpl
import de.jensklingenberg.data.SimpleIcons
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.facet.AndroidRootUtil

import java.awt.event.MouseEvent


class DeepLinkLineMarkerProvider : LineMarkerProvider {

    override fun getLineMarkerInfo(p0: PsiElement): LineMarkerInfo<*>? {
        val element = p0
        if (element is XmlTokenImpl) {
            when (element.text) {
                "deepLink" -> {


                    //ModuleManager.getInstance(ProjectManager.getInstance().openProjects.first())
                    val manifestFile = AndroidFacet.getInstance(p0)?.let { AndroidRootUtil.getPrimaryManifestFile(it) }


                    return LineMarkerInfo(element, element.textRange,
                        SimpleIcons.LINK, { "Start a deepLink" }, { _: MouseEvent, _: XmlTokenImpl ->
                            val appUriElement = getXMLValue(element, "app:uri") ?: ""
                            DeepLinkStarterView(appUriElement, DeepLinkStarterContract.Mode.NAVLIB).showAndGet()
                        }, GutterIconRenderer.Alignment.CENTER
                    )
                }
                "data" -> {
                    return LineMarkerInfo(element, element.textRange,
                        SimpleIcons.LINK, { "Start a deepLink" }, { _: MouseEvent, _: XmlTokenImpl ->
                            val host = getXMLValue(element, "android:host") ?: ""
                            val scheme = getXMLValue(element, "android:scheme") ?: ""
                            val pathPrefix = getXMLValue(element, "android:pathPrefix") ?: ""
//
                            var deeplinktext = ""
                            deeplinktext += if (scheme.isNotEmpty()) {
                                "$scheme://"
                            } else {
                                ""
                            }

                            deeplinktext += if (host.isNotEmpty()) {
                                host
                            } else {
                                ""
                            }

                            deeplinktext += if (pathPrefix.isNotEmpty()) {
                                pathPrefix
                            } else {
                                ""
                            }

                            DeepLinkStarterView(deeplinktext, DeepLinkStarterContract.Mode.MANIFEST).showAndGet()
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

    private fun getXMLValue(element: XmlTokenImpl, s: String): String? {
        //TODO: Find better way to get the value
        return element.parent.children.filterIsInstance<XmlAttributeImpl>()
            .find { it.firstChild.text == s }?.children?.first { it is XmlAttributeValueImpl }
            ?.firstChild?.nextSibling?.text

    }
}
