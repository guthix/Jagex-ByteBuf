package io.guthix.proto.builder

import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.defaultImports

object Configuration : ScriptCompilationConfiguration({
    defaultImports(ByteOrder::class, Modification::class)
})