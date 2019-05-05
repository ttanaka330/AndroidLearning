# Kotlin coroutines

### 参考サイト

公式
* [kotlinlang Coroutines for asynchronous programming and more](https://kotlinlang.org/docs/reference/coroutines-overview.html)
* [Github Kotlin/kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines)

Google Developers Japan  
* [Android で簡単コルーチン: viewModelScope](https://developers-jp.googleblog.com/2019/04/android-viewmodelscope.html)

Qiita
* [@takahirom Kotlin Coroutineチュートリアル](https://qiita.com/takahirom/items/3e0b7009d2e050e0e56c)
* [@k-kagurazaka@github 入門Kotlin coroutines](https://qiita.com/k-kagurazaka@github/items/8595ca60a5c8d31bbe37)
* [@kawmra 図で理解する Kotlin Coroutine](https://qiita.com/kawmra/items/d024f9ab32ffe0604d39)

### メモ

* `job.cancel()` のタイミングに注意が必要。  
`onDestroyView` で呼ぶと BackStack からの復帰時に job キャンセル済みで launch が実行されない。
* 今回は `CoroutineScope` を実装して使用しているが、プロパティとして保持して使用することも可能。
```kotlin
// implements CoroutineScope
class XXXFragment : Fragment(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        launch { /* 非同期処理 */ }
    }
        
    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}

// Properties CoroutineScope
class XXXFragment : Fragment() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        scope.launch { /* 非同期処理 */ }
    }
        
    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
```