# PaidCommand

[![](https://img.shields.io/circleci/build/github/gdenga/PaidCommand?style=flat-square)](https://github.com/gdenga/PaidCommand)
[![](https://img.shields.io/github/downloads/gdenga/PaidCommand/total?style=flat-square)](https://github.com/gdenga/PaidCommand)
[![](https://img.shields.io/github/license/gdenga/PaidCommand?style=flat-square)](https://github.com/gdenga/PaidCommand)
[![](https://img.shields.io/badge/MCBBS-PaidCommand-ff69b4?style=flat-square)](https://www.mcbbs.net/thread-889083-1-1.html)

## 介绍

1. 让玩家执行某些指令需要花钱
2. 简单两个字“氪金”
3. 让不支持付费执行指令的插件支持付费
4. 多一个服务器资金赞助来源
5. 支持金币和点券
6. 为其他插件提供专用付费指令
7. 支持付费执行op权限指令
8. 指令配置文件采用更加简洁的Json

## 插件指令：

| 指令 | 说明 |
| ------------ | ------------ |
| /pc add paid [Cost] [coin/point] [Command] | 添加新的付费指令 |
| /pc add player [PlayerName] [Command] | 为付费指令添加免费玩家 |
| /pc list command | 列出所有付费指令 |
| /pc list player [Command] | 查询某个付费指令的免费玩家列表 |
| /pc op [Command] | 设指令执行时以op权限执行 |
| /pc runcommand [Command] | 为其他插件提供执行付费插件的指令 |
| /pc del paid [Command] | 删除付费指令 |
| /pc del player [Player] [Command] | 删除付费指令的免费玩家 |

## 权限

| 权限 | 说明 |
| ------------ | ------------ |
| pc.admin | 插件操作权限 |
| pc.free | 全部付费指令免费权限 |

以上权限OP默认拥有

## 配置文件

config.yml

```yaml
paidcommand:
  #是否开启付费指令功能
  enable: true
  #货币名
  coin: "金币"
  point: "点券"
language:
  # 变量：${MONEY}为指令所需支付的“金币”
  #       ${NOW}为玩家拥有的“金币”
  coin:
    #金钱不足
    notEnough: "[PaidCommand] > §a§b金币不足，指令售价${MONEY}枚金币，您的金币：${NOW}"
    #成功购买使用指令
    success: "[PaidCommand] > §b成功使用指令，花费${MONEY}枚金币，你现有金币：${NOW}枚"
  point:
    #点券不足
    notEnough: "[PaidCommand] > §a§b点券不足，指令售价${MONEY}个点券，您的点券：${NOW}"
    #成功购买使用指令
    success: "[PaidCommand] > §b成功使用指令，花费${MONEY}枚点券，你现有点券：${NOW}枚"

```

paid.json

```json
[
  {
    "name": "gamemode",
    "type": "coin",
    "cost": 100,
    "isOp": true,
    "ignore": [
      "ice-cold"
    ]
  },
  {
    "name": "back",
    "type": "point",
    "cost": 100,
    "isOp": false,
    "ignore": [
      "ice-cold"
    ]
  },
  {
    "name": "home",
    "type": "coin",
    "cost": 1000,
    "isOp": false,
    "ignore": []
  }
]
```
