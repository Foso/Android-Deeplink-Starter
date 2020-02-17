package de.jensklingenberg.data

import com.intellij.psi.impl.source.xml.XmlTextImpl
import com.intellij.psi.impl.source.xml.XmlTokenImpl
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.util.AndroidResourceUtil

fun resolveAndroidStringRes(
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