# Drip
An Easy-to-edit Trade Plugin for SpigotMC

## Commands

Dripはコマンドシステムに [CommandAPI](https://commandapi.jorel.dev/) を使用しています。そのため、Vanillaの`/execute`コマンドを介して実行することができます。


---

```
/drip create <TradeID>
```

必要な権限 `drip.command.create`  

トレードを作成します。  
TradeIDは小文字のアルファベット・アンダースコア・算用数字のみ使用できます。(`[a-z_0-9]+`)

---

```
/drip remove <TradeID>
```

必要な権限 `drip.command.remove`

トレードを削除します。

---

```
/drip open <Trade>
```

必要な権限 `drip.command.open`

実行者に対しトレードを開きます。  
このコマンドは一般のユーザーが使用することを目的として作成されておらず、管理者が特定のユーザーに対して`execute`コマンドを用いて使われることを目的としています。  
例: `/execute as @a[tag=Winner] run drip open trade_for_winners`

---

```
/drip edit <TradeID>
```

必要な権限 `drip.command.edit`

実行者に対しトレード編集画面を開きます。

---

```
/drip export harukatrade [all | trade <Trade>]
```

必要な権限 `drip.command.export`

[HarukaTrade](https://github.com/SigureRuri/HarukaTrade) で使用される形式でトレードを出力します。

ファイルは`plugins/Drip/export/HarukaTrade/`に出力されます。

---

## Trade

プレイヤーは、それぞれのトレードに設定されているUUIDに対応するエンティティをクリックするとトレード画面を開くことができます。  

### 必要権限
`drip.trade.*` - すべてのトレードを開くことができます。
`drip.trade.<TradeID>` - トレードIDに対応するトレードを開くことができます。

この権限は、`/drip open`コマンドには適応されません。

### トレード画面

トレードには、

- 取引が可能な「商品」
- トレード画面の装飾として存在する「バックパネル」

が存在します。  
前者にカーソルを合わせ右クリックで商品の情報を確認することができます。
左クリックで購入確認画面を開きます。また、Shift + 左クリックで、購入確認画面なしで商品を購入します。


## 将来的に実装される可能性があるもの

- Vaultと連携した通貨を利用した取引