 <h1 align="center">Android Deeplink Starter</h1>

[![jCenter](https://img.shields.io/badge/Kotlin-1.3.61-green.svg
)](https://github.com/Foso/MpApt/blob/master/LICENSE)[![jCenter](https://img.shields.io/badge/Apache-2.0-green.svg)](https://github.com/Foso/MpApt/blob/master/LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
<a href="https://twitter.com/intent/tweet?text=Hey, check out Android_Deeplink_Starter https://github.com/Foso/Android_Deeplink_Starter via @jklingenberg_ #Kotlin 
"><img src="https://img.shields.io/twitter/url/https/github.com/angular-medellin/meetup.svg?style=social" alt="Tweet"></a>



## Introduction 🙋‍♂️ 🙋‍
 
 
 
 <h3>This plugin adds an alternative UI to start Android deeplinks</h3>
    <br>
    <h1>Features</h1>
    <h2>The deeplink starter</h2>
    <p>
        <img src="https://raw.githubusercontent.com/Foso/Android_Deeplink_Starter/master/docs/deeplinkStarter.png"/>
    <p>
    <p><strong>DeeplinkValue</strong>
   Here you can insert your deeplink. When this view gets opened from the Manifest file or navigation navgraph, this gets prefilled.
    For every word that surrounded by curly brackets, an extra textfield will be added where you can insert the value, e.g. see the <strong>{noteId}</strong> above.
</p>
    <p><strong>Reload UI</strong>
        When you change the text in "DeeplinkValue", you can use this to reload the starter view.
    </p>
    <p><strong>Launch Flags</strong>
        Here you can insert additional flags to the deeplink
    </p>
    <h2>How to open the starter</h2>
    <h3>Tools menu</h3>
    <p>
        <img src="https://raw.githubusercontent.com/Foso/Android_Deeplink_Starter/master/docs/toolsmenu.png"/>
    <p>
    <p> You can find the "Android Deeplink Starter" inside the Intellij Tools Menu</p>
    <h3>Android Manifest</h3>
    <p>
        <img src="https://raw.githubusercontent.com/Foso/Android_Deeplink_Starter/master/docs/manifestdeeplink.png" width="400" alt="Autocomplete pub packages screenshot"/>
    <p>
    <p>When you have declared deeplinks  in your AndroidManifest xml, this plugin will add a deeplink icon next to the line with the <strong>data</strong>-Tag.
        When you click on it, the deeplink starter will open filled with the contents of "android:host","android:pathPrefix" and "android:sheme"</p>
    <h3>Android Navigation Component Navgraph </h3>
    <p>
        <img src="https://raw.githubusercontent.com/Foso/Android_Deeplink_Starter/master/docs/navigationdeeplink.png" width="400" alt="Autocomplete pub packages screenshot"/>
    <p>
    <p>When you are using the <a href="https://developer.android.com/guide/navigation/navigation-getting-started">Android Navigation Component</a> and have the deeplinks declared in your navgraph xml, this plugin will add a deeplink icon next to the line.
        When you click on it, the deeplink starter will open filled with the content of "app:uri"</p>
