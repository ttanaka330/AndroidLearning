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

```
package
|- common           // 共通機能
|  |- activity
|  |- dialog
|  |- fragment
|  |- util
|  |- view
|
|- di               // Dagger2 の Component/Module
|  |- scope
|
|- library          // 各学習別機能（Activity他）

```

