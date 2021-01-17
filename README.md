# 人狼
![image](https://user-images.githubusercontent.com/55973528/104833061-66c0f000-58d9-11eb-94c2-6d8eb71f448f.png)
## 概要

このゲームはオンラインでできる人狼です。

DiscordやZoomなどのチャットアプリを使って議論を行うことを前提にしています。
![image](https://user-images.githubusercontent.com/55973528/104833061-66c0f000-58d9-11eb-94c2-6d8eb71f448f.png)
## セットアップ

### Gradleを利用したWebアプリケーションの実行

以下のコマンドを実行して、ディレクトリを移動してください。
![image](https://user-images.githubusercontent.com/55973528/104832980-eac6a800-58d8-11eb-8783-7f9f85ea7274.png)
以降のコマンドは jinrou ディレクトリ上で実行してください。

gradlewを実行するために、以下のコマンドを実行して実行権限を与えましょう。
![image](https://user-images.githubusercontent.com/55973528/104833002-07fb7680-58d9-11eb-8bd5-8905d7abb251.png)
実行権限を与えたgradlewを実行するために以下のコマンドを実行してください。
![image](https://user-images.githubusercontent.com/55973528/104833010-15b0fc00-58d9-11eb-899c-366cc683008e.png)
プロジェクトの実行のために以下のコマンドを実行してください。
![image](https://user-images.githubusercontent.com/55973528/104833016-23ff1800-58d9-11eb-92cf-05c819027e6d.png)
実行後ブラウザで以下のURLにアクセスして画像のようなページが表示されていることを確認してください。

[http://150.89.233.209:8080/](http://150.89.233.209:8080/)

停止するときはControl+Cを実行してください。


上記の ./gradlew bootrun を実行する代わりに、以下のコマンドを実行することでアプリケ
ーションを常時実行することができます。
![image](https://user-images.githubusercontent.com/55973528/104833029-31b49d80-58d9-11eb-8fce-47ce67b02e30.png)

停止するときは以下のコマンドを実行してください。
![image](https://user-images.githubusercontent.com/55973528/104833039-3ed18c80-58d9-11eb-80ec-8f2fe9d84e54.png)

![image](https://user-images.githubusercontent.com/55973528/104833052-51e45c80-58d9-11eb-8053-5d21b80e1558.png)


## ゲーム説明
![image](https://user-images.githubusercontent.com/55973528/104833061-66c0f000-58d9-11eb-94c2-6d8eb71f448f.png)
このゲームは人間側と人狼側に分かれ、議論を行う昼の時間と能力を行使する夜の時間を繰り返してお互いの陣営の勝利を目指すゲームです。
5 人から 10 人でゲームを開始することができる。

## 役職の説明
![image](https://user-images.githubusercontent.com/55973528/104833061-66c0f000-58d9-11eb-94c2-6d8eb71f448f.png)
### 人間側

●平民...何の能力も持たない役職です。議論で勝利に貢献しましょう。

●占い師...夜の時間毎に任意のプレイヤーが人狼か人間かを知ることができる役職です。
ただし、狂人の判別はできません。

●霊媒師...夜の時間毎に死んだプレイヤーが人狼か人間かを知ることが出来る役職です。
占い師同様狂人の判別はできません。

●騎士...夜の時間毎に任意のプレイヤーを人狼の襲撃から守ることができます。

### 人狼側

●人狼...夜の時間毎に任意のプレイヤーを襲撃し、騎士に守られていなければ殺害できます。人狼が二人いる場合でも襲撃できるのは夜の時間毎に一人だけです。

●狂人...勝利条件が人狼側の人間です。平民と同様に能力は無く、誰が人狼かを知ることは出来ません。議論の妨害や誘導が主な目的になります。

## 勝利条件
![image](https://user-images.githubusercontent.com/55973528/104833061-66c0f000-58d9-11eb-94c2-6d8eb71f448f.png)
●人間側...人狼を全員殺せば勝利です。 6 人以上で遊ぶときは人狼が 2 人になります。

●人狼側...人間と人狼が同数になれば勝利です。人間の数には狂人も含まれます。

## ゲームの流れ
![image](https://user-images.githubusercontent.com/55973528/104833061-66c0f000-58d9-11eb-94c2-6d8eb71f448f.png)
➀最初は昼の時間から開始されます。自身の役職が確認できます。人狼ならもう一人の人狼が誰であるかも確認できます。（ 6 人以上で開始した場合）

➁タイマーが 0 秒になるまで議論を進めましょう。

➂時間になると投票ページに移動し吊る(処刑する)人を投票による多数決で決定します。
この際同率 1 位が出た場合は再投票が促され、再投票の結果も同率 1 位が出れば誰も吊らずに投票が終了します。

④投票後は夜の時間となり、平民と狂人以外は各々の能力を行使します。

⑤全員の夜の行動が終わり次第昼に移行します。このときに誰が人狼に殺されたかが明らかになります。

⑥以降は昼と夜が繰り返され人間側か人狼側どちらかの勝利条件が達成されるまでゲームが続きます。

## 操作説明
![image](https://user-images.githubusercontent.com/55973528/104833061-66c0f000-58d9-11eb-94c2-6d8eb71f448f.png)
![image](https://user-images.githubusercontent.com/55973528/104833105-8eb05380-58d9-11eb-87a4-4ba02418f444.png)

はじめの画面で「入室」をクリックしてログインしてください。ユーザーIDとパスワードは以下の通りです。

ユーザーID：user1～user
パスワード：a
参加する人数までのユーザーを使ってログインしてください（例： 5 人→user1～user

![image](https://user-images.githubusercontent.com/55973528/104833116-a1c32380-58d9-11eb-936b-684ba75c15cc.png)

この画面では現在ルームに入っている人数を確認することができます。人数がそろったら「ゲームスタート」をクリックしてゲームを開始して下さい。

※この時、最初にスタートするユーザーを決めておき、そのユーザーが移動したことを確認してから残りのユーザーが移動してください。

全員が「ゲームスタート」を押すまでは以下の画像のように「参加者を確認中....」と表示されます。
![image](https://user-images.githubusercontent.com/55973528/104833122-b1db0300-58d9-11eb-8192-e2cbb627029f.png)

![image](https://user-images.githubusercontent.com/55973528/104833173-0d0cf580-58da-11eb-8002-45a641b9820a.png)

全員が移動するとタイマーがスタートします。また、「役職を確認する」をクリックすることで、自分がuser何なのか、役職が何なのかを確認することが出来ます。

タイマーが 0 秒になると「投票ページに移行する」というリンクが表示されます。
これをクリックすると投票ページ移行します。

![image](https://user-images.githubusercontent.com/55973528/104833183-21e98900-58da-11eb-9351-cd9c7193122e.png)

投票ページでは、怪しいと思ったユーザーに投票することができます。

生き残っているユーザー全員が投票し終わったら以下の画像のように「投票結果を表示する」というリンクが表示され、これをクリックすると投票結果を見ることができます。

![image](https://user-images.githubusercontent.com/55973528/104833194-32016880-58da-11eb-92dd-40db51b4988c.png)

![image](https://user-images.githubusercontent.com/55973528/104833196-3594ef80-58da-11eb-8ea3-f97b50f9252e.png)


投票で誰かが選ばれた場合はそのまま夜に移行します。

![image](https://user-images.githubusercontent.com/55973528/104833205-49d8ec80-58da-11eb-8fc3-b6ecc572cc23.png)

同率 1 位によって確定しなかった場合は再投票が行われます。

![image](https://user-images.githubusercontent.com/55973528/104833213-58bf9f00-58da-11eb-9206-7437b5a07c07.png)

再投票でも確定しなかった場合はだれも処刑されずに夜に移行します。

![image](https://user-images.githubusercontent.com/55973528/104833217-670dbb00-58da-11eb-9fd7-0d3bdb3d7bad.png)


処刑されてしまったユーザーは以降何もできません。

夜に移行すると以下のように表示されます。

![image](https://user-images.githubusercontent.com/55973528/104833227-73921380-58da-11eb-8d2d-39ac5eaf2648.png)

夜の行動ができる役職は以下のように表示されます。それぞれの行動を終えて朝に移行します。人狼が 2 人いる場合はチャットアプリなどでだれを襲撃するかを相談して下さい。

![image](https://user-images.githubusercontent.com/55973528/104833235-83a9f300-58da-11eb-983c-749f269e6a3a.png)

![image](https://user-images.githubusercontent.com/55973528/104833236-873d7a00-58da-11eb-8885-046a60ff616d.png)

![image](https://user-images.githubusercontent.com/55973528/104833238-8a386a80-58da-11eb-857c-a6fcf8a30fd8.png)

朝に移行すると、全員が移行するまで画像のように「皆さんの生存を確認中....」と表示されます。

![image](https://user-images.githubusercontent.com/55973528/104833246-9ae8e080-58da-11eb-9873-937fa80bb886.png)

![image](https://user-images.githubusercontent.com/55973528/104833247-9fad9480-58da-11eb-8fcf-2cf4c1a270ec.png)

全員が朝に移行すると、昨夜人狼に襲撃されたユーザーが表示されます。確認出来たら昼に移行して下さい。

人狼が標的としたユーザーが騎士によって守られていた場合は以下のように表示されます。

![image](https://user-images.githubusercontent.com/55973528/104833271-b05e0a80-58da-11eb-8e72-e683674aadd7.png)

以降は勝敗が決まるまで昼の時間と夜の時間繰り返します。

投票によって勝敗が確定した場合は、以下のように表示されます。

![image](https://user-images.githubusercontent.com/55973528/104833278-bce26300-58da-11eb-84b3-67e848da10db.png)

![image](https://user-images.githubusercontent.com/55973528/104833285-c4a20780-58da-11eb-90f6-37d95cc806f9.png)