# AndroidLearning

このリポジトリは Android アプリ開発の学習、および、備忘録のために作成しました。  
主にライブラリなどの動作確認として `java` `kotlin` 問わず雑多に取り扱っていきます。  
ライブラリの使い方などは [Wiki](https://github.com/ttanaka330/AndroidLearning/wiki) に記載していく予定です。  

## 環境

OS:  Windows 10  
IDE: Android Studio 3.0  
gradle: 3.0.0  
target : Android 4.0 以上  

## Package 構成

自身のベストプラクティスとなる Package 構成は思索中。  
`view` と `viewmodel` を分ける必要があるのか...  

```
package
|- api              // ライブラリ等の API 群
|  |- net
|  |- repository
|
|- di               // Dagger2 の Component/Module
|  |- scope
|
|- model            // データモデル
|  |- net
|  |- repository
|
|- receiver         // BroadcastReceiver
|
|- service          // Service/JobScheduler
|
|- ui               // UI/widget 群
|  |- activity
|  |- dialog
|  |- fragment
|  |- view
|
|- viewmodel        // DataBinding で使用するビューモデル
```

