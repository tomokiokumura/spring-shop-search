Spring Boot ハンズオン
====

このアプリケーションはSpring Bootのための 1dayハンズオン アプリです。  
このページのステップに沿って実装することでSpring Boot Frameworkの概要に触れることができます。

<p id="index"></p>

----
## このページの読み方
上からステップに沿って読むことを想定しています。  
各ステップは説明と実装がセットになっていますので実際に手を動かしながら進めてみましょう。

また、各ステップで以下のようなアイコンとディレクトリ名が記されています。  
<span class="text-muted"><i class="fas fa-folder-open"></i>: 01_model</span>  
これは各ステップ用のサンプルソースが指定されているディレクトリに含まれていることを示しています。
このディレクトリはリポジトリの中にあるexampleディレクトリの中にまとめて入れられていますので、
参考にしてみてください。

## はじめに
Spring Boot Frameworkを使うにあたって 
[MVCモデル](https://www.google.co.jp/search?q=MVC%E3%83%A2%E3%83%87%E3%83%AB) 
の概要を理解することをお勧めします。
また別資料で [Spring Boot Framework の概要]() を把握しておきましょう。

----
## 商品モデルを定義する
<span class="text-muted"><i class="fas fa-folder-open"></i>: 01_model</span>

商品を保持するためのクラスを作成します。
商品データには以下の4つの情報を持たせます。

| | 型 | 内容 |
|---|---|---|
| id | Long | 固有のIDを持ちます。IDは自動付与させます。|
| name | String | 商品名です。 |
| price | int | 商品価格です。保存できる値は0以上とします。 |
| description | String(TEXT) | 商品説明です。長文を保存できるようにDBの型がTEXT型になるように指定します。

#### モデルを作る
これらの情報をクラスに書き換えると以下のようになります。
```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Min(value = 0)
    @Column(name = "price")
    public int price;

    @Column(name = "description", columnDefinition = "TEXT")
    public String description;
}

```
簡単に説明をします。

`@Entity`を付与することにより、このクラスがSpring BootのEntityとして扱えるようになります。
これによって`@OneToOne`や`@OneToMany`なども付与することができるようになり、モデル間の多重度を定義することができます。

`@Id`を付与したメンバはそのモデルの主キーとして扱われます。また各メンバに`＠Column`を指定することによって、
DBのカラム名を明示的に指定します。省略された場合は自動的に変数名と同じものが付きます。
(キャメルケースはスネークケースに変換されます)

----
## テーブルで一覧を見れるようにする
<span class="text-muted"><i class="fas fa-folder-open"></i>: 02_list_items</span>

ブラウザ上で商品一覧を見れるようにします。
URLは`http://localhost:8080/items`とします。
URLにアクセスしてデータを表示するためにはhtmlファイルとControllerクラスが必要になるので作ります。
全体のテンプレートは`src/resource/templates/index.html`をコピーして使用してください。  

#### コントローラーを作る
最初にControllerを作ります。ここでは動作確認をすることが目的なので商品のデータは固定にします。
```java
@Controller
@RequestMapping("/items")
public class ItemController {

    @GetMapping
    public String listItem(Model model) {
        List<Item> list = Arrays.asList(
                new Item(1, "商品1", 100, "説明1"),
                new Item(2, "商品2", 200, "説明2"),
                new Item(3, "商品3", 300, "説明3")
        );
        model.addAttribute("items", list);
        return "list_item";
    }
}
```

クラスに`@Controller`を付与することでコントローラーとして扱うことができます。
またURLのしては`@RequestMapping`を付与することで任意のURLを指定できます。
Mappingごとにメソッドを割り当てることができ、引数として与えられたModelにすきなデータを入れることで、
view(ここではHTMLファイルを指しています)にデータを渡すことができます。
今回は商品のリストを渡したいので`List<Item>`を宣言して確認用に固定のデータを入れています。
返り値には表示したいhtmlファイルの名前を指定します。

#### ビューを作る
次にhtmlファイルを作ります。ベースは`index.html`と同じものを使用するのでコピーして名前を`list_items.html`
にしてください。  
`<div class="container"></div>`の中にHTMLを書いていきます。
```html
<table class="table">
    <thead>
        <tr>
            <td scope="col">ID</td>
            <td scope="col">商品名</td>
            <td scope="col">商品価格</td>
            <td scope="col">説明</td>
            <td scope="col"></td>
        </tr>
    </thead>
    <tbody>
        <tr th:each="item : ${items}" th:object="${item}">
            <td th:text="*{id}"></td>
            <td th:text="*{name}"></td>
            <td th:text="*{price} + ' Yen'"></td>
            <td th:text="*{description}"></td>
        </tr>
    </tbody>
</table>
```
このアプリではテンプレートエンジンにThymeleafを使っています。
そのため、コントローラーでセットした値については`${キー名}`の形でアクセスすることができます。
上のコントローラーでセットした`items`というリストを使ってテーブルを使いたいのでループ構文の、
`th:each`を使っています。また、ループ時にオブジェクトの宣言をすることで`${item.id}`と書くべきものを
`*{id}`と書いてアクセスすることができるようになっています。

----
## データを追加できるようにする
<span class="text-muted"><i class="fas fa-folder-open"></i>: 03_add_item</span>

ここまでで商品の一覧を出すことができたので新規にアイテムを追加できるようにします。

最初に入力フォームを作成します。入力フォームのURLは`http://localhost:8080/items/add`とします。
URLにアクセスしたときは入力フォームのViewを返せばよいのでコントローラクラスにメソッドを一つ追加します
HTMLファイルのファイル名は`item_form.html`とします。

#### コントローラに追加する
```java
public class ItemController {
    
    @GetMapping("add")
    public String getForm() {
        return "item_form";
    }
}
```

#### フォーム用のビューを作る
次に、htmlファイルを作成します。先ほどと同じように`index.html`をコピーして`item_form.html`
を作っておいてください。
入力フォームですが以下の3つの入力欄があればよいので用意します。
```html
<form method="post">
    <div class="form-group">
        <label for="itemName">商品名</label>
        <input type="name" class="form-control" id="itemName" name="name">
    </div>
    <div class="form-group">
        <label for="itemDescription">説明</label>
        <textarea type="text" class="form-control" id="itemDescription" name="description" rows="3"></textarea>
    </div>
    <div class="form-group">
        <label for="itemPrice">商品価格</label>
        <input type="number" class="form-control" id="itemPrice" name="price">
    </div>
    <button class="btn btn-primary" type="submit">決定</button>
</form>
```
基本的には自由に作ってよいですが、入力欄にname属性を必ずつけて受け取るモデルの変数名と同じにすることを忘れないでください。
こうすることで、コントローラでモデルを受け取った際に対応したメンバ変数に自動的に値をセットしてくれます。

#### リポジトリを作る
続いて、DBに保存する処理をするためのリポジトリを作成します。
```java
public interface ItemRepository extends JpaRepository<Item, Long> {
}
```
Itemモデルに対してDBアクセス処理や保存処理を行うためのリポジトリインターフェースです。
ここでは保存を行いますが、JpaRepositoryに保存するメソッドは定義されていますので、メソッドの宣言は何も必要ありません。

#### コントローラに追加する
最後に、フォームが送信されたときに受けるコントローラーを作成します。
```java
public class ItemController {
    @Autowired
    ItemRepository itemRepository;
    
    @PostMapping("/add")
    public String registerItem(Item item) {
        itemRepository.save(item);
        return "redirect:/items";
    }
}
```
フォームから送信された値を受け取ためのメソッドを定義します。
引数にモデルを指定することで送信されたフォームのname属性をもとに自動的に値をセットしてくれます。
またこの時モデルに引数なしのコンストラクタと各メンバ変数のSetterがないとエラーになる為必ず宣言してください。
(例ではlombokプラグインを使用しているので`@NoArgsConstractor`と`@Data`を付与しています。)
受け取ったモデルは対応したリポジトリの`save()`メソッドを呼ぶことでDBに保存することができます。


----
## 詳細ページを作成する
<span class="text-muted"><i class="fas fa-folder-open"></i>: 04_item_detail</span>

#### ビューに追加する
```html
<td>
    <a th:href="@{/items/{id}(id=*{id})}">
        <button class="btn btn-outline-primary btn-small">確認</button>
    </a>
</td>
```
各商品の行に確認ボタンを追加します。URLは`http://localhost:8080/items/{id}`の形にしたいので、
`th:href`を使って動的にURLの指定をしています。

#### コントローラに追加する
```java
public class ItemController {

    @GetMapping("{id:[0-9]+}")
    public String getDetail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("item", itemRepository.getOne(id));
        return "detail";
    }
}
```
コントローラを追加します。ItemController自体に`@RequestMapping("items")`がついているので
{id}の部分を指定しています。また、正規表現を指定して数字の組み合わせの時だけマッチするようにしています。
引数では`@PathValuable`を付けることでURLに含まれている値を受け取ることができます。

#### ビューを作る
```html
<div th:object="${item}">
    <div class="row">
        <div class="col-md-4">
            <img th:src="@{/images/default.png}" alt="IMAGE NOT SET" width="200" height="200" align="center">
        </div>
        <div class="col-md-8">
            <p class="h5">
                商品番号： [[ *{id} ]]
            </p>
            <p class="mt-5 h2">
                [[ *{name} ]]
            </p>
            <p>
                価格： [[ *{price} ]] Yen
            </p>
        </div>
    </div>
    <div class="row mt-5 mr-1 ml-1">
        <p th:text="*{description}"></p>
        <small class="text-muted">テキストは Wikipedia からの抜粋です。</small>
    </div>
</div>
```
ビューは好きなデータを表示することができればなんでもいいので、うえのHTMLはあくまで例として書いています。
ビューでは一覧を作成したときと同様に`th:object`と`th:text="*{変数名}"`を指定してコントローラーで
渡した値を表示しています。


----
## メインページでカード表示できるようにする
<span class="text-muted"><i class="fas fa-folder-open"></i>: 05_index</span>

ここまでで商品の一覧の表示、商品の追加ができるようになりました。  
次のステップとしてトップページにアクセスしたときにカード形式で商品の一覧を見れるようにします。

#### コントローラに追加する
```java
public class HomeController {
    
    @Autowired
    ItemRepository itemRepository;
    
    @GetMapping
    public String index(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "index";
    }
}
```
ここまでと同じようにメソッドを追加します。データはitemRepositoryからすべてのデータを取得するようにします。

#### ビューに追加する
```html
<div class="row">
    <div class="col-md-4" style="padding: 5px;" th:each="item : ${items}" th:object="${item}">
        <div class="card" style="height: 18rem;">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-8" style="width: 10em;">
                        <h5 class="card-title contract" th:text="*{name}"></h5>
                    </div>
                    <div class="col-md-4">
                        <a th:href="@{/items/{id}(id=*{id})}">
                            <button class="btn btn-sm btn-outline-primary">
                                詳細
                            </button>
                        </a>
                    </div>
                </div>
                <h6 class="card-subtitle">[[*{price}]] Yen</h6>
                <p class="card-text mt-2" th:text="*{description}" style="height: 12rem;"></p>
            </div>
        </div>
    </div>
</div>
```
カードの表示はbootstrapのカードを利用します。一覧を表示したときと同様に`th:each`を使ってコントローラで設定した
データをループで表示します。また、ここでも詳細ページに飛べるように詳細ボタンを設定しています。　

ここまで出来たらトップページの`http://localhost:8080`にアクセスしてみましょう。
カード形式で商品の一覧が表示できていれば成功です。

----
## 商品名で検索できるようにする
<span class="text-muted"><i class="fas fa-folder-open"></i>: 06_search</span>

最後のステップとして商品の検索をできるようにします。
商品の検索は検索ワードが商品名に含まれているかで判定します。

#### ビューに追加する
```html
<div class="row">
    <div class="col-md-12">
        <form>
            <div class="form-group">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="検索ワードを入力…" name="keyword">
                    <div class="input-group-append">
                        <button class="btn btn-primary" type="submit">検索</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
```
検索ワードを入力するためのフォームをindex.htmlに追加します。
name属性を指定することでコントローラで受け取りやすくなるので必ず指定しましょう。

#### リポジトリに追加する
```java
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameContainsOrderByIdAsc(String keyword);
}
```
商品の一覧から名前で絞込ができるようにリポジトリクラスにメソッドを定義します。
上の例では名前が含まれているか(findByNameContains)で検索をしていて、
検索でヒットしたものをIDでソート(OrderByIdAsc)しています。

#### コントローラを修正する
```java
public class HomeController {

    @Autowired
    ItemRepository itemRepository;
    
    @GetMapping
    public String index(
            Model model,
            @RequestParam(name = "keyword", required = false) Optional<String> keyword) {
        List<Item> list = keyword.isPresent()
                ? itemRepository.findByNameContainsOrderByIdAsc(keyword.get())
                : itemRepository.findAll();
        model.addAttribute("items", list);
        return "index";
    }
}
```
コントローラを修正します。
`@RequestParam`を付与して引数に値を指定することでフォームで入力した値を取得することができます。
URLに直接した場合はキーワードがないため、require属性をfalseにしています。
requireをfalseにしているので引数の型はOptionalになります。
コントローラの中で引数があればキーワード検索、なければ全検索と言うように書き換えました。

ここまで実装して商品一覧に対して絞込検索ができるようになりました。

----
## 課題

商品の検索で以下の3つの条件から選んで検索ができるようにしてください。

- 商品名または商品説明
- 商品名
- 商品説明

----
## 最後に
ここまでのステップと課題を実装した例はこのリポジトリのanswerブランチにあります。  
課題をこなして余裕がある人はさらにこのアプリケーションを改造してみましょう。

例 )
- 登録済みの商品を編集できるようにする
- 商品をカートに入れて合計額を計算できるようにする
- ログイン機能を実装してみる

また、サンプルコードでは簡略化のため、コントローラーで直接リポジトリの操作を行っています。
この状態では大規模になってくるとコントーラーが肥大化してきてしまうため、扱いにくくなってしまいますので、
サービスクラスを定義して全体の構造を整理してみてください。