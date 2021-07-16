package com.github.shur.drip.yaml

import com.github.shur.drip.Drip

object TradesYaml : Yaml(Drip.instance, "trades.yml", false)