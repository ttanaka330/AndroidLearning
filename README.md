# AndroidLearning

このリポジトリは Android アプリ開発の学習、および、備忘録のために作成しました。  
主にライブラリなどの動作確認として `java` `kotlin` 問わず雑多に取り扱っていきます。  
ライブラリの使い方などは [Wiki](https://github.com/ttanaka330/AndroidLearning/wiki) に記載していく予定です。  

## 動作確認環境

OS:  Windows 10  
IDE: Android Studio 3.1.3  
target : Android 4.1(API 16) 以上

## Package 構成

```
package
|- common           // 共通機能
|  |- activity
|  |- dialog
|  |- fragment
|  |- view
|
|- di               // Dagger2 の Component/Module
|  |- scope
|
|- presentation     // 各学習別機能（Activity他）
|
|- util             // 本プロジェクトに依存しないユーティリティ

```

