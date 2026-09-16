package com.example.webviewapp

import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                val filterJs = """
                (function(){
                  document.querySelectorAll('img').forEach(img=>{
                    if(img.width<100) return;
                    // כאן המסנן לפי הגדרתך - מרפק/ברך/צוואר
                    // גרסה 1 - מטשטש עד לבדיקה
                    img.style.filter = 'blur(15px)';
                  });
                })();
                """.trimIndent()
                view?.evaluateJavascript(filterJs, null)
            }
        }
        webView.loadUrl("https://www.google.com")
    }
}
